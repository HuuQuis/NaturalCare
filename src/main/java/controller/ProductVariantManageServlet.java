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

            boolean hasColorVariant = false;
            if (productId != null) {
                try {
                    int pid = Integer.parseInt(productId);
                    List<ProductVariation> variations = productDao.getProductVariationsByProductId(pid);
                    for (ProductVariation v : variations) {
                        if (v.getColorId() > 0) {
                            hasColorVariant = true;
                            break;
                        }
                    }
                } catch (Exception ignored) {}
            }
            request.setAttribute("hasColorVariant", hasColorVariant);

            request.getRequestDispatcher("/view/manage/productvariant-add.jsp").forward(request, response);
        }else if("edit".equals(action)){
            String variantIdStr = request.getParameter("variantId");
            if (variantIdStr == null || variantIdStr.trim().isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Variant ID is required for edit operation");
                return;
            }
            try {
                int productVariantId = Integer.parseInt(variantIdStr.trim());
                ProductVariation productVariation = productVariationDao.getProductVariationById(productVariantId);
                if (productVariation == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product variation not found");
                    return;
                }
                request.setAttribute("tempProductVariation", productVariation);
                request.setAttribute("previousImageUrl", productVariation.getImageUrl());
                request.setAttribute("productId", productVariation.getProductId());

                // --- Check if product has color-based variants ---
                boolean hasColorVariant = false;
                int pid = productVariation.getProductId();
                List<ProductVariation> variations = productDao.getProductVariationsByProductId(pid);
                for (ProductVariation v : variations) {
                    if (v.getColorId() > 0) {
                        hasColorVariant = true;
                        break;
                    }
                }
                request.setAttribute("hasColorVariant", hasColorVariant);

                request.getRequestDispatcher("/view/manage/productvariant-edit.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid variant ID format");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action specified");
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

                File tempFolder = new File(realTempPath).getParentFile();
                if (!tempFolder.exists()) tempFolder.mkdirs();

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

                ProductVariation pv = productVariationDao.getProductVariationById(productVariantId);
                if (pv != null && pv.getImageUrl() != null && pv.getImageUrl().startsWith("/images/product/")) {
                    String imagePath = getServletContext().getRealPath(pv.getImageUrl());
                    File imageFile = new File(imagePath);
                    if (imageFile.exists()) {
                        boolean deleted = imageFile.delete();
                        System.out.println("Deleted image from disk? " + deleted);
                    }
                }

                productVariationDao.deleteProductVariation(productVariantId);
                response.sendRedirect(request.getContextPath() + "/productManage");

            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid variant ID format");
            }
        }
        else if ("update".equals(action)) {
            String productId = request.getParameter("productId");
            String variantIdStr = request.getParameter("variantId");

            if (variantIdStr == null || productId == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required parameters.");
                return;
            }

            int variantId = Integer.parseInt(variantIdStr);
            ProductVariation tempProductVariant = createProductVariantFromRequest(request, true);
            tempProductVariant.setVariationId(variantId);
            tempProductVariant.setProductId(Integer.parseInt(productId));

            Part file = request.getPart("image");
            String imageFileName = null;
            String tempImagePath = null;

            if (file != null && file.getSize() > 0) {
                String contentType = file.getContentType();

                if (!isImageFile(contentType)) {
                    handleValidationError("Uploaded file must be an image (JPEG, PNG, GIF).", request, response, tempProductVariant, true);
                    return;
                }

                BufferedImage image = ImageIO.read(file.getInputStream());
                if (image == null) {
                    handleValidationError("The uploaded file is not a valid image.", request, response, tempProductVariant, true);
                    return;
                }

                int width = image.getWidth();
                int height = image.getHeight();
                if (width < 400 || width > 1000 || height < 400 || height > 1000) {
                    handleValidationError("Image dimensions must be between 400x400 and 1000x1000 pixels.", request, response, tempProductVariant, true);
                    return;
                }

                String previousImageUrl = request.getParameter("previousImageUrl");
                if (previousImageUrl != null && !previousImageUrl.isEmpty() && previousImageUrl.contains("images/product/")) {
                    String oldImagePath = getServletContext().getRealPath("/" + previousImageUrl.replaceFirst("^/+", ""));
                    File oldFile = new File(oldImagePath);
                    if (oldFile.exists()) {
                        boolean deleted = oldFile.delete();
                        System.out.println("Deleted old image? " + deleted);
                    }
                }


                imageFileName = UUID.randomUUID() + "_" + Paths.get(file.getSubmittedFileName()).getFileName().toString();
                tempImagePath = "/images/temp/" + imageFileName;
                String realTempPath = getServletContext().getRealPath(tempImagePath);

                try (InputStream input = file.getInputStream(); FileOutputStream output = new FileOutputStream(realTempPath)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = input.read(buffer)) != -1) {
                        output.write(buffer, 0, bytesRead);
                    }
                }

                tempProductVariant.setImageUrl(tempImagePath);
                request.setAttribute("previousImageUrl", tempImagePath);
            } else {
                String previousImageUrl = request.getParameter("previousImageUrl");
                if (previousImageUrl != null && !previousImageUrl.isEmpty()) {
                    tempProductVariant.setImageUrl(previousImageUrl);
                }
            }

            if (!validateProductVariantInput(request, response, tempProductVariant, true)) {
                return;
            }

            if (tempProductVariant.getImageUrl() != null && tempProductVariant.getImageUrl().startsWith("/images/temp/")) {
                String tempPath = getServletContext().getRealPath(tempProductVariant.getImageUrl());
                String fileName = Paths.get(tempProductVariant.getImageUrl()).getFileName().toString();
                String finalImageUrl = "/images/product/" + fileName;
                String finalPath = getServletContext().getRealPath(finalImageUrl);

                File finalDir = new File(finalPath).getParentFile();
                if (!finalDir.exists()) finalDir.mkdirs();

                Files.move(Paths.get(tempPath), Paths.get(finalPath), StandardCopyOption.REPLACE_EXISTING);
                tempProductVariant.setImageUrl(finalImageUrl);
            }

            productVariationDao.updateProductVariation(tempProductVariant);
            response.sendRedirect(request.getContextPath() + "/productManage");
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
        String sellPriceStr = request.getParameter("sell_price");
        String priceStr = request.getParameter("price");
        String quantityStr = request.getParameter("quantity");

        int price = 0;
        int quantity = 0;
        int sellPrice = 0;
        try {
            if (priceStr != null && !priceStr.trim().isEmpty()) {
                price = Integer.parseInt(priceStr.trim());
            }
            if (sellPriceStr != null && !sellPriceStr.trim().isEmpty()) {
                sellPrice = Integer.parseInt(sellPriceStr.trim());
            }
            if (quantityStr != null && !quantityStr.trim().isEmpty()) {
                quantity = Integer.parseInt(quantityStr.trim());
            }
        } catch (NumberFormatException e) {
            throw new ServletException("Invalid number format for price or quantity", e);
        }


        tempProductVariation.setPrice(price);
        tempProductVariation.setSell_price(sellPrice);
        tempProductVariation.setQtyInStock(quantity);
        tempProductVariation.setProductId(Integer.parseInt(request.getParameter("productId")));
        return tempProductVariation;
    }

    private boolean validateProductVariantInput(HttpServletRequest request, HttpServletResponse response,
                                                ProductVariation tempProductVariant, boolean isUpdate) throws ServletException, IOException {

        int colorId = tempProductVariant.getColorId();
        int sizeId = tempProductVariant.getSizeId();
        int price = tempProductVariant.getPrice();
        int sellPrice = tempProductVariant.getSell_price();
        int quantity = tempProductVariant.getQtyInStock();
        int productId = tempProductVariant.getProductId();
        int variationId = tempProductVariant.getVariationId();

        if (tempProductVariant.getImageUrl() == null || tempProductVariant.getImageUrl().isEmpty()) {
            handleValidationError("Please upload an image for the product variant.", request, response, tempProductVariant, isUpdate);
            return false;
        }

        // --- Enforce color-only rule if product has color-based variants ---
        String variantType = null;
        List<ProductVariation> variations = productDao.getProductVariationsByProductId(productId);

        for (ProductVariation v : variations) {
            if (!isUpdate || v.getVariationId() != variationId) {
                if (v.getColorId() > 0 && v.getSizeId() <= 0) {
                    variantType = "color";
                    break;
                } else if (v.getSizeId() > 0 && v.getColorId() <= 0) {
                    variantType = "size";
                    break;
                }
            }
        }

        request.setAttribute("variantType", variantType);

        if ("color".equals(variantType)) {
            if (colorId <= 0 || sizeId > 0) {
                handleValidationError("This product already uses color variants. You can only select a color (not size).", request, response, tempProductVariant, isUpdate);
                return false;
            }
        } else if ("size".equals(variantType)) {
            if (sizeId <= 0 || colorId > 0) {
                handleValidationError("This product already uses size variants. You can only select a size (not color).", request, response, tempProductVariant, isUpdate);
                return false;
            }
        } else {
            // First variation being added
            if ((colorId > 0 && sizeId > 0) || (colorId <= 0 && sizeId <= 0)) {
                handleValidationError("Please select either a color or a size, but not both.", request, response, tempProductVariant, isUpdate);
                return false;
            }
        }

        // --- Check for duplicate colors and sizes ---
        if (colorId > 0 && productVariationDao.isDuplicateColor(productId, colorId, isUpdate ? variationId : null)) {
            handleValidationError("This color already exists for the product.", request, response, tempProductVariant, isUpdate);
            return false;
        }

        if (sizeId > 0 && productVariationDao.isDuplicateSize(productId, sizeId, isUpdate ? variationId : null)) {
            handleValidationError("This size already exists for the product.", request, response, tempProductVariant, isUpdate);
            return false;
        }

        if (price <= 0) {
            handleValidationError("Price must be greater than 0.", request, response, tempProductVariant, isUpdate);
            return false;
        }

        if (sellPrice <= 0) {
            handleValidationError("Sell price must be greater than 0.", request, response, tempProductVariant, isUpdate);
            return false;
        }

        if (sellPrice < price) {
            handleValidationError("Sell price must be greater than or equal to the price.", request, response, tempProductVariant, isUpdate);
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

        if (errorMessage.contains("image")) {
            request.setAttribute("errorField", "image");
        } else if (errorMessage.contains("either a color or a size")) {
            // Ưu tiên focus vào colorId nếu cả hai sai
            request.setAttribute("errorField", "colorId");
        } else if (errorMessage.contains("color already exists")) {
            request.setAttribute("errorField", "colorId");
        } else if (errorMessage.contains("size already exists")) {
            request.setAttribute("errorField", "sizeId");
        } else if (errorMessage.contains("Price must be greater than 0")) {
            request.setAttribute("errorField", "price");
        } else if (errorMessage.contains("Sell price must be greater than 0")) {
            request.setAttribute("errorField", "sell_price");
        } else if (errorMessage.contains("Sell price must be greater than or equal to the price")) {
            request.setAttribute("errorField", "sell_price");
        } else if (errorMessage.contains("Quantity must be greater than 0")) {
            request.setAttribute("errorField", "quantity");
        }

        if (isUpdate) {
            request.getRequestDispatcher("/view/manage/productvariant-edit.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/view/manage/productvariant-add.jsp").forward(request, response);
        }
    }
}