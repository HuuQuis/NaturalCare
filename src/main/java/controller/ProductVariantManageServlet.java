package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Product;
import model.ProductVariation;
import dal.ProductDAO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import java.io.IOException;
import java.util.Properties;

@MultipartConfig
@WebServlet(name = "ProductVariantManageServlet", urlPatterns = {"/productVariantManage"})
public class ProductVariantManageServlet extends HttpServlet {
    ProductDAO productDao = new ProductDAO();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        List<Product> products = productDao.getAllProducts();
        request.setAttribute("products", products);
        if ("edit".equals(action)) {

        }else if ("add".equals(action)) {
                String productId = request.getParameter("productId");
                request.setAttribute("productId", productId);
                request.getRequestDispatcher("/view/manage/productvariant-add.jsp").forward(request, response);
        }else {

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

            if (file != null && file.getSize() > 0) {
                imageFileName = file.getSubmittedFileName();

                InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties");
                Properties prop = new Properties();
                prop.load(input);
                String uploadPath = prop.getProperty("upload.path");

                if (uploadPath == null || uploadPath.isEmpty()) {
                    throw new ServletException("Upload path is not configured in properties file.");
                }

                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdirs();

                String filePath = uploadPath + File.separator + imageFileName;

                try (FileOutputStream fos = new FileOutputStream(filePath);
                     InputStream inputStream = file.getInputStream()) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        fos.write(buffer, 0, bytesRead);
                    }
                }

                tempProductVariant.setImageUrl("/images/product/" + imageFileName); // NEW
            } else {
                String previousImageUrl = request.getParameter("previousImageUrl");
                if (previousImageUrl != null && !previousImageUrl.isEmpty()) {
                    tempProductVariant.setImageUrl(previousImageUrl);
                }
            }

            if (!validateProductVariantInput(request, response, tempProductVariant, false)) {
                return;
            }

            productDao.addProductVariation(tempProductVariant, Integer.parseInt(productId));
            response.sendRedirect(request.getContextPath() + "/productManage");
            return;

        } else if ("update".equals(action)) {
            //
        } else if ("delete".equals(action)) {
            int productVariantId = Integer.parseInt(request.getParameter("variantId"));
            productDao.deleteProductVariation(productVariantId);
        }
        response.sendRedirect(request.getContextPath() + "/productManage");
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

    private boolean validateProductVariantInput(HttpServletRequest request, HttpServletResponse response, ProductVariation tempProductVariation, boolean isUpdate) throws ServletException, IOException {
        String color = tempProductVariation.getColor();
        String size = tempProductVariation.getSize();
        int price = tempProductVariation.getPrice();
        int quantity = tempProductVariation.getQtyInStock();

        if (color== null || color.isEmpty()) {
            handleValidationError("Color cannot be empty",request, response, tempProductVariation, isUpdate);
            return false; // Validation failed
        }
        if (size == null || size.isEmpty()) {
            handleValidationError("Size cannot be empty", request, response, tempProductVariation, isUpdate);
            return false; // Validation failed
        }

        if (!size.matches("\\d+ml")) {
            handleValidationError("Size must be a number and in milliliters (e.g., 100ml)", request, response, tempProductVariation, isUpdate);
            return false;
        }

        if (price <= 0) {
            handleValidationError("Price must be greater than 0", request, response, tempProductVariation, isUpdate);
            return false; // Validation failed
        }
        if (quantity <= 0) {
            handleValidationError("Quantity must be greater than 0", request, response, tempProductVariation, isUpdate);
            return false; // Validation failed
        }

        return true; // Validation passed
    }

    private void handleValidationError(String errorMessage, HttpServletRequest request, HttpServletResponse response, ProductVariation tempProductVariation, boolean isUpdate) throws ServletException, IOException {
        request.setAttribute("error", errorMessage);
        request.setAttribute("tempProductVariation", tempProductVariation);
        request.setAttribute("products", productDao.getAllProducts());
        request.setAttribute("productId", request.getParameter("productId"));
        
        // Store the current image URL to preserve it
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