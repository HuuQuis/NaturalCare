package controller;

import dal.ColorDAO;
import dal.ProductDAO;
import dal.ProductVariationDAO;
import dal.SizeDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Color;
import model.Product;
import model.ProductVariation;
import model.Size;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@MultipartConfig
@WebServlet(name = "ProductVariantManageServlet", urlPatterns = {"/productVariantManage"})
public class ProductVariantManageServlet extends HttpServlet {
    ProductDAO productDao = new ProductDAO();
    ProductVariationDAO productVariationDao = new ProductVariationDAO();
    ColorDAO colorDao = new ColorDAO();
    SizeDAO sizeDao = new SizeDAO();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        List<Product> products = productDao.getAllProducts();
        request.setAttribute("products", products);
        List<Color> colors = colorDao.getAllColors();
        request.setAttribute("colors", colors);
        List<Size> sizes = sizeDao.getAllSize();
        request.setAttribute("sizes", sizes);
        if ("add".equals(action)) {
            String productId = request.getParameter("productId");
            request.setAttribute("productId", productId);
            request.getRequestDispatcher("/view/manage/productvariant-add.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            String productId = request.getParameter("productId");
            Part file = request.getPart("image");
            String imageFileName = null;
            String tempImagePath = null;

            ProductVariation tempProductVariant = createProductVariantFromRequest(request, false);

            if (file != null && file.getSize() > 0) {
                String contentType = file.getContentType();

                if (!isImageFile(contentType)) {
                    handleValidationError("Uploaded file must be an image (JPEG, PNG, GIF).", request, response, tempProductVariant, false);
                    return;
                }

                BufferedImage image = ImageIO.read(file.getInputStream());
                if (image == null) {
                    handleValidationError("The uploaded file is not a valid image.", request, response, tempProductVariant, false);
                    return;
                }

                int width = image.getWidth();
                int height = image.getHeight();
                if (width < 400 || width > 1000 || height < 400 || height > 1000) {
                    handleValidationError("Image dimensions must be between 400x400 and 1000x1000 pixels.", request, response, tempProductVariant, false);
                    return;
                }


                imageFileName = UUID.randomUUID() + "_" + Paths.get(file.getSubmittedFileName()).getFileName().toString();
                tempImagePath = "/images/temp/" + imageFileName;
                String realTempPath = getServletContext().getRealPath(tempImagePath);

//                File tempFolder = new File(realTempPath).getParentFile();
//                if (!tempFolder.exists()) tempFolder.mkdirs();

                // Lưu ảnh tạm
                try (InputStream input = file.getInputStream(); FileOutputStream output = new FileOutputStream(realTempPath)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = input.read(buffer)) != -1) {
                        output.write(buffer, 0, bytesRead);
                    }
                }

                tempProductVariant.setImageUrl(tempImagePath);
                request.setAttribute("previousImageUrl", tempImagePath); // Gửi lại khi reload form
            } else {
                // Không chọn lại ảnh → dùng previousImageUrl
                String previousImageUrl = request.getParameter("previousImageUrl");
                if (previousImageUrl != null && !previousImageUrl.isEmpty()) {
                    tempProductVariant.setImageUrl(previousImageUrl);
                }
            }

            if (!validateProductVariantInput(request, response, tempProductVariant, false)) {
                return;
            }

            String finalImageUrl = null;
            if (tempProductVariant.getImageUrl() != null && tempProductVariant.getImageUrl().startsWith("/images/temp/")) {
                String tempPath = getServletContext().getRealPath(tempProductVariant.getImageUrl());
                String fileName = Paths.get(tempProductVariant.getImageUrl()).getFileName().toString();
                finalImageUrl = "/images/product/" + fileName;
                String finalPath = getServletContext().getRealPath(finalImageUrl);

                File finalDir = new File(finalPath).getParentFile();
                if (!finalDir.exists()) finalDir.mkdirs();

                Files.move(Paths.get(tempPath), Paths.get(finalPath), StandardCopyOption.REPLACE_EXISTING);
                tempProductVariant.setImageUrl(finalImageUrl);
            }

            productVariationDao.addProductVariation(tempProductVariant, Integer.parseInt(productId));
            response.sendRedirect(request.getContextPath() + "/productManage");
        }
        else if ("delete".equals(action)) {
            String variantIdStr = request.getParameter("variantId");
            if (variantIdStr == null || variantIdStr.trim().isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Variant ID is required for delete operation");
                return;
            }

            try {
                int productVariantId = Integer.parseInt(variantIdStr.trim());
                productVariationDao.deleteProductVariation(productVariantId);
                response.sendRedirect(request.getContextPath() + "/productManage");
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid variant ID format");
            }
        }
    }

    private boolean isImageFile(String contentType) {
        return contentType.equals("image/jpeg") ||
                contentType.equals("image/png") ||
                contentType.equals("image/gif") ||
                contentType.equals("image/jpg");
    }

    private ProductVariation createProductVariantFromRequest(HttpServletRequest request, boolean isUpdate) throws ServletException {
        ProductVariation tempProductVariation = new ProductVariation();


        if(isUpdate){

        }

        try{
            int colorId = Integer.parseInt(request.getParameter("colorId"));
            if (colorId > 0){
                tempProductVariation.setColorId(colorId);
            }
        }catch (Exception e) {
            if(!isUpdate) {
                tempProductVariation.setColorId(0);
            }
        }

        try{
            int sizeId = Integer.parseInt(request.getParameter("sizeId"));
            if (sizeId > 0){
                tempProductVariation.setSizeId(sizeId);
            }
        }catch (Exception e) {
            if(!isUpdate) {
                tempProductVariation.setSizeId(0);
            }
        }
        String priceStr = request.getParameter("price");
        String quantityStr = request.getParameter("quantity");

        int price = 0;
        int quantity = 0;
        try {
            if (priceStr != null && !priceStr.trim().isEmpty()) {
                price = Integer.parseInt(priceStr.trim());
            }
            if (quantityStr != null && !quantityStr.trim().isEmpty()) {
                quantity = Integer.parseInt(quantityStr.trim());
            }
        } catch (NumberFormatException e) {
            throw new ServletException("Invalid number format for price or quantity", e);
        }


        tempProductVariation.setPrice(price);
        tempProductVariation.setQtyInStock(quantity);

        return tempProductVariation;
    }

    private boolean validateProductVariantInput(HttpServletRequest request, HttpServletResponse response, ProductVariation tempProductVariant, boolean isUpdate) throws ServletException, IOException {
        int colorId = tempProductVariant.getColorId();
        int sizeId = tempProductVariant.getSizeId();
        int price = tempProductVariant.getPrice();
        int quantity = tempProductVariant.getQtyInStock();

        if (tempProductVariant.getImageUrl() == null || tempProductVariant.getImageUrl().isEmpty()) {
            handleValidationError("Please upload an image for the product variant.", request, response, tempProductVariant, isUpdate);
            return false;
        }
        if (colorId <= 0) {
            handleValidationError("Please select a color for this product variant.", request, response, tempProductVariant, isUpdate);
            return false;
        }

        if (sizeId <= 0) {
            handleValidationError("Please select a size for this product variant", request, response, tempProductVariant, isUpdate);
            return false;
        }

        if (price <= 0) {
            handleValidationError("Price must be greater than 0.", request, response, tempProductVariant, isUpdate);
            return false;
        }
        if (quantity <= 0) {
            handleValidationError("Quantity must be greater than 0.", request, response, tempProductVariant, isUpdate);
            return false;
        }

        return true;
    }

    private void handleValidationError(String errorMessage, HttpServletRequest request, HttpServletResponse response, ProductVariation tempProductVariation, boolean isUpdate) throws ServletException, IOException {
        request.setAttribute("error", errorMessage);
        request.setAttribute("tempProductVariation", tempProductVariation);
        request.setAttribute("products", productDao.getAllProducts());
        request.setAttribute("productId", request.getParameter("productId"));
        request.setAttribute("colors", colorDao.getAllColors());
        request.setAttribute("sizes", sizeDao.getAllSize());

        if (tempProductVariation.getImageUrl() != null) {
            request.setAttribute("previousImageUrl", tempProductVariation.getImageUrl());
        }

        if (isUpdate) {
            request.getRequestDispatcher("/view/manage/productvariant-edit.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/view/manage/productvariant-add.jsp").forward(request, response);
        }
    }
}