package controller;

import dal.ProductDAO;
import dal.ProductVariationDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Product;
import model.ProductVariation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@MultipartConfig
@WebServlet(name = "ProductVariantManageServlet", urlPatterns = {"/productVariantManage"})
public class ProductVariantManageServlet extends HttpServlet {
    ProductDAO productDao = new ProductDAO();
    ProductVariationDAO productVariationDao = new ProductVariationDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        List<Product> products = productDao.getAllProducts();
        request.setAttribute("products", products);
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

            ProductVariation tempProductVariant = createProductVariantFromRequest(request, false);

            // ===== Handle Image Upload & Save Immediately If Valid =====
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
                if (width < 400 || width > 600 || height < 400 || height > 600) {
                    handleValidationError("Image dimensions must be between 400x400 and 600x600 pixels.", request, response, tempProductVariant, false);
                    return;
                }

                // Save image to disk immediately
                imageFileName = file.getSubmittedFileName();
                tempProductVariant.setImageUrl(imageFileName);

                String webappPath = getServletContext().getRealPath("/");
                String uploadPath = webappPath + "images" + File.separator + "product";
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdirs();

                String filePath = uploadPath + File.separator + imageFileName;

                try (FileOutputStream fos = new FileOutputStream(filePath);
                     InputStream uploadStream = file.getInputStream()) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = uploadStream.read(buffer)) != -1) {
                        fos.write(buffer, 0, bytesRead);
                    }
                }
            } else {
                // Nếu không chọn file mới, lấy ảnh cũ nếu có
                String previousImageUrl = request.getParameter("previousImageUrl");
                if (previousImageUrl != null && !previousImageUrl.isEmpty()) {
                    tempProductVariant.setImageUrl(previousImageUrl);
                }
            }

            // ===== Validate Other Fields =====
            if (!validateProductVariantInput(request, response, tempProductVariant, false)) {
                return;
            }

            // ===== Save to Database =====
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

        String color = request.getParameter("color");
        String size = request.getParameter("size");
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

        if (color != null) color = color.trim();
        if (size != null) size = size.trim();

        tempProductVariation.setColor(color);
        tempProductVariation.setSize(size);
        tempProductVariation.setPrice(price);
        tempProductVariation.setQtyInStock(quantity);

        return tempProductVariation;
    }

    private boolean validateProductVariantInput(HttpServletRequest request, HttpServletResponse response, ProductVariation tempProductVariant, boolean isUpdate) throws ServletException, IOException {
        String color = tempProductVariant.getColor();
        String size = tempProductVariant.getSize();
        int price = tempProductVariant.getPrice();
        int quantity = tempProductVariant.getQtyInStock();

        if (color == null || color.isEmpty()) {
            handleValidationError("Color cannot be empty.", request, response, tempProductVariant, isUpdate);
            return false;
        }
        if (size == null || size.isEmpty()) {
            handleValidationError("Size cannot be empty.", request, response, tempProductVariant, isUpdate);
            return false;
        }

        if (!size.matches("\\d+ml")) {
            handleValidationError("Size must be in the format of number followed by 'ml' (e.g., 100ml).", request, response, tempProductVariant, isUpdate);
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