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

            // Logic to add a new product variant
            String productId = request.getParameter("productId");

            ProductVariation tempProductVariant = createProductVariantFromRequest(request, false);

            // If validation fails, the method handles the error and forwards to the appropriate page
            if (validateProductVariantInput(request, response, tempProductVariant, false)) {
                // If validation passes, proceed to save the product variant
                // Xử lý upload ảnh tại đây vì validate đã thành công
                Part file = (Part) request.getAttribute("imageFile");
                String imageFileName = (String) request.getAttribute("imageFileName");

                InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties");
                Properties prop = new Properties();
                prop.load(input);
                String uploadPath = prop.getProperty("upload.path");

                if (uploadPath == null || uploadPath.isEmpty()) {
                    throw new ServletException("Upload path is not configured in properties file.");
                }
                String filePath = uploadPath + File.separator + imageFileName;

                try (FileOutputStream fos = new FileOutputStream(filePath);
                     InputStream inputStream = file.getInputStream()) {
                    byte[] buffer = new byte[inputStream.available()];
                    inputStream.read(buffer);
                    fos.write(buffer);
                }

                tempProductVariant.setImageUrl(filePath);
                return;
            }
            productDao.addProductVariation(tempProductVariant, Integer.parseInt(productId));
        } else if ("update".equals(action)) {
            // Logic to update a product variant
            // Retrieve parameters and call a service to update the variant
        } else if ("delete".equals(action)) {
            // Logic to delete a product variant
            // Retrieve parameters and call a service to delete the variant
        }
        // Redirect or forward to a view page after processing
        response.sendRedirect(request.getContextPath() + "/productManage");
    }

    private ProductVariation createProductVariantFromRequest(HttpServletRequest request, boolean isUpdate) throws IOException, ServletException {
        ProductVariation tempProductVariation = new ProductVariation();

        // Chưa lưu file tại đây
        Part file = request.getPart("image");
        String imageFileName = file.getSubmittedFileName();

        // Gán tạm tên file, chưa lưu
        String filePath = imageFileName; // hoặc null nếu muốn kiểm tra sau
        tempProductVariation.setImageUrl(filePath);

        // Các thông tin khác
        String color = request.getParameter("color");
        String size = request.getParameter("size");
        int price = Integer.parseInt(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        if (color!= null) color = color.trim();
        if (size != null) size = size.trim();

        tempProductVariation.setColor(color);
        tempProductVariation.setSize(size);
        tempProductVariation.setPrice(price);
        tempProductVariation.setQtyInStock(quantity);

        // Gắn Part để xử lý sau
        request.setAttribute("imageFile", file);
        request.setAttribute("imageFileName", imageFileName);

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
            handleValidationError("Quantity cannot be negative", request, response, tempProductVariation, isUpdate);
            return false; // Validation failed
        }

        return true; // Validation passed
    }

    private void handleValidationError(String errorMessage, HttpServletRequest request, HttpServletResponse response, ProductVariation tempProductVariation, boolean isUpdate) throws ServletException, IOException {
        request.setAttribute("error", errorMessage);
        request.setAttribute("tempProductVariation", tempProductVariation);
        request.setAttribute("products", productDao.getAllProducts());
        if (isUpdate) {
            request.getRequestDispatcher("/view/manage/productvariant-edit.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/view/manage/productvariant-add.jsp").forward(request, response);
        }
    }
}
