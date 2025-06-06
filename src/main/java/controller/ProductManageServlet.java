package controller;

import dal.ProductDAO;
import dal.SubProductCategoryDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Product;
import model.ProductVariation;
import model.SubProductCategory;
import model.User;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductManageServlet", urlPatterns = {"/productManage"})
public class ProductManageServlet extends HttpServlet {
    ProductDAO productDAO = new ProductDAO();
    SubProductCategoryDAO subProductCategoryDAO = new SubProductCategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Object userObj = request.getSession().getAttribute("user");
//        if (userObj == null || !(userObj instanceof User) || ((User) userObj).getRole() != 4) {
//            response.sendRedirect(request.getContextPath() + "/login"); // Redirect to login if user is not logged in or not an admin
//            return;
//        }

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login"); // Redirect to login if user is not logged in or not an admin
            return;
        }

        String sessionIp = (String) session.getAttribute("ip");
        String sessionAgent = (String) session.getAttribute("agent");

        String currentIp = request.getRemoteAddr();
        String currentAgent = request.getHeader("User-Agent");

        if (!currentIp.equals(sessionIp) || !currentAgent.equals(sessionAgent)) {
            session.invalidate();
            response.sendRedirect(request.getContextPath() + "/login"); // Redirect to login if session IP or agent does not match
            return;
        }

        String action = request.getParameter("action");
        List<SubProductCategory> subCategories = subProductCategoryDAO.getAllSubProductCategories();
        request.setAttribute("subCategories", subCategories);
        if ("edit".equals(action)) {
            int productId = Integer.parseInt(request.getParameter("id"));
            Product product = productDAO.getProductById(productId);
            request.setAttribute("product", product);
            request.getRequestDispatcher("/view/manage/product-edit.jsp").forward(request, response);
        } else if ("add".equals(action)) {
            request.getRequestDispatcher("/view/manage/product-add.jsp").forward(request, response);
        } else {
            List<Product> products = productDAO.getAllProducts();

            java.util.Map<Integer, List<ProductVariation>> productVariantsMap = new java.util.HashMap<>();
            for (Product p : products) {
                productVariantsMap.put(p.getId(), productDAO.getProductVariationsByProductId(p.getId()));
            }
            request.setAttribute("products", products);
            request.setAttribute("productVariantsMap", productVariantsMap);
            request.getRequestDispatcher("/view/manage/product-manage.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login"); // Redirect to login if user is not logged in or not an admin
            return;
        }

        String sessionIp = (String) session.getAttribute("ip");
        String sessionAgent = (String) session.getAttribute("agent");

        String currentIp = request.getRemoteAddr();
        String currentAgent = request.getHeader("User-Agent");

        if (!currentIp.equals(sessionIp) || !currentAgent.equals(sessionAgent)) {
            session.invalidate();
            response.sendRedirect(request.getContextPath() + "/login"); // Redirect to login if session IP or agent does not match
            return;
        }

        String action = request.getParameter("action");
        if ("add".equals(action)) {
            Product tempProduct = createProductFromRequest(request, false);

            // If validation fails, the method handles the error and forwards to the appropriate page
            if (validateProductInput(request, response, tempProduct, false)) {
                return; // Return when validation FAILS (method returns false on failure)
            }

            productDAO.addProduct(tempProduct);
        } else if ("update".equals(action)) {
            Product tempProduct = createProductFromRequest(request, true);

            // If validation fails, the method handles the error and forwards to the appropriate page
            if (validateProductInput(request, response, tempProduct, true)) {
                return; // Return when validation FAILS (method returns false on failure)
            }

            productDAO.updateProduct(tempProduct);
        } else if ("delete".equals(action)) {
            int productId = Integer.parseInt(request.getParameter("id"));
            productDAO.deleteProduct(productId);
        }
        response.sendRedirect(request.getContextPath() + "/productManage");
    }

    // Extract product creation logic into a separate method
    private Product createProductFromRequest(HttpServletRequest request, boolean isUpdate) {
        Product tempProduct = new Product();

        // Set ID for update operations
        if (isUpdate) {
            int productId = Integer.parseInt(request.getParameter("id"));
            tempProduct.setId(productId);

            // For updates with validation errors, preserve the original subcategory if new one is invalid
            Product originalProduct = productDAO.getProductById(productId);
            if (originalProduct != null) {
                tempProduct.setSubProductCategoryId(originalProduct.getSubProductCategoryId());
            }
        }

        // Get and trim parameters
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String information = request.getParameter("information");
        String guideline = request.getParameter("guideline");

        if (name != null) name = name.trim();
        if (description != null) description = description.trim();
        if (information != null) information = information.trim();
        if (guideline != null) guideline = guideline.trim();

        // Set string fields
        tempProduct.setName(name);
        tempProduct.setDescription(description);
        tempProduct.setInformation(information);
        tempProduct.setGuideline(guideline);

        // Parse and set category (only if valid, otherwise keep original for updates)
        try {
            int subProductCategoryId = Integer.parseInt(request.getParameter("subProductCategoryId"));
            if (subProductCategoryId > 0) {
                tempProduct.setSubProductCategoryId(subProductCategoryId);
            }
            // If subProductCategoryId <= 0 and this is an update, the original category is already set above
        } catch (Exception e) {
            // For updates, keep the original subcategory that was already set
            // For adds, this will remain 0 and trigger validation error
            if (!isUpdate) {
                tempProduct.setSubProductCategoryId(0);
            }
        }

        return tempProduct;
    }

    private boolean validateProductInput(HttpServletRequest request, HttpServletResponse response, Product tempProduct, boolean isUpdate) throws ServletException, IOException {
        String name = tempProduct.getName();
        String description = tempProduct.getDescription();
        String information = tempProduct.getInformation();
        String guideline = tempProduct.getGuideline();
        int subProductCategoryId = tempProduct.getSubProductCategoryId();

        // Validate name
        if (name == null || name.isEmpty()) {
            handleValidationError("Product name cannot be empty", request, response, tempProduct, isUpdate);
            return false; // Return false when validation FAILS
        }
        if (name.length() >= 255) {
            handleValidationError("Product name must be less than 255 characters", request, response, tempProduct, isUpdate);
            return false; // Return false when validation FAILS
        }

        // Validate description
        if (description == null || description.isEmpty()) {
            handleValidationError("Product description cannot be empty", request, response, tempProduct, isUpdate);
            return false; // Return false when validation FAILS
        }
        if (description.length() >= 255) {
            handleValidationError("Product description must be less than 255 characters", request, response, tempProduct, isUpdate);
            return false; // Return false when validation FAILS
        }

        // Validate information
        if (information == null || information.isEmpty()) {
            handleValidationError("Product information cannot be empty", request, response, tempProduct, isUpdate);
            return false; // Return false when validation FAILS
        }

        // Validate guideline
        if (guideline == null || guideline.isEmpty()) {
            handleValidationError("Product guideline cannot be empty", request, response, tempProduct, isUpdate);
            return false; // Return false when validation FAILS
        }

        // Validate category
        if (subProductCategoryId <= 0) {
            handleValidationError("Please select a valid category", request, response, tempProduct, isUpdate);
            return false; // Return false when validation FAILS
        }

        return true; // Return true when validation PASSES
    }

    private void handleValidationError(String errorMessage, HttpServletRequest request, HttpServletResponse response, Product tempProduct, boolean isUpdate)
            throws ServletException, IOException {
        request.setAttribute("error", errorMessage);
        request.setAttribute("product", tempProduct);
        request.setAttribute("subCategories", subProductCategoryDAO.getAllSubProductCategories());

        if (isUpdate) {
            request.getRequestDispatcher("/view/manage/product-edit.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/view/manage/product-add.jsp").forward(request, response);
        }
    }
}