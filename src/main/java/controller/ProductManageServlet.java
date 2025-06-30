package controller;

import dal.ProductCategoryDAO;
import dal.ProductDAO;
import dal.SubProductCategoryDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Product;
import model.ProductCategory;
import model.ProductVariation;
import model.SubProductCategory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ProductManageServlet", urlPatterns = {"/productManage"})
public class ProductManageServlet extends HttpServlet {
    ProductDAO productDAO = new ProductDAO();
    SubProductCategoryDAO subProductCategoryDAO = new SubProductCategoryDAO();
    ProductCategoryDAO categoryDAO = new ProductCategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        List<ProductCategory> categories = categoryDAO.getAllProductCategories();
        request.setAttribute("categories", categories);
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
            String keyword = request.getParameter("search");
            String pageRaw = request.getParameter("page");
            String sort = request.getParameter("sort");

            int page = (pageRaw == null || pageRaw.isEmpty()) ? 1 : Integer.parseInt(pageRaw);
            int pageSize = 10;

            String categoryIdRaw = request.getParameter("categoryId");
            String subCategoryIdRaw = request.getParameter("subCategoryId");
            Integer categoryId = (categoryIdRaw != null && !categoryIdRaw.trim().isEmpty()) ? Integer.parseInt(categoryIdRaw.trim()) : null;
            Integer subCategoryId = (subCategoryIdRaw != null && !subCategoryIdRaw.trim().isEmpty()) ? Integer.parseInt(subCategoryIdRaw.trim()) : null;

            List<Product> products;
            int total;

            if (keyword != null && !keyword.trim().isEmpty()) {
                products = productDAO.searchProductsAdvanced(keyword.trim(), categoryId, subCategoryId, page, pageSize, sort);
                total = productDAO.countSearchProductsAdvanced(keyword.trim(), categoryId, subCategoryId);
                request.setAttribute("searchKeyword", keyword);
            } else if (subCategoryId != null) {
                products = productDAO.getProductsBySubCategoryIdSorted(subCategoryId, page, pageSize, sort);
                total = productDAO.getTotalProductsCountBySubCategory(subCategoryId);
                request.setAttribute("selectedSubCategoryId", subCategoryId);
            } else if (categoryId != null) {
                products = productDAO.getProductsByCategoryIdSorted(categoryId, page, pageSize, sort);
                total = productDAO.getTotalProductsCountByCategory(categoryId);
                request.setAttribute("selectedCategoryId", categoryId);
            } else {
                products = productDAO.getProductsByPage(page, pageSize);
                total = productDAO.countTotalProducts();
            }

            int totalPage = (int) Math.ceil((double) total / pageSize);

            // --- Variant paging ---
            int variantPageSize = 5;
            Map<Integer, Integer> variantPageMap = new HashMap<>();
            Map<Integer, Integer> variantTotalMap = new HashMap<>();
            Map<Integer, Integer> variantTotalPageMap = new HashMap<>();
            Map<Integer, List<ProductVariation>> productVariantsMap = new HashMap<>();

            for (Product p : products) {
                String param = request.getParameter("variantPage" + p.getId());
                int variantPage = 1;
                try {
                    if (param != null) variantPage = Integer.parseInt(param);
                } catch (Exception ignored) {}

                int variantTotal = productDAO.countProductVariants(p.getId());
                int variantTotalPage = (int) Math.ceil((double) variantTotal / variantPageSize);
                List<ProductVariation> pagedVariants = productDAO.getProductVariationsByProductIdPaged(p.getId(), variantPage, variantPageSize);

                variantPageMap.put(p.getId(), variantPage);
                variantTotalMap.put(p.getId(), variantTotal);
                variantTotalPageMap.put(p.getId(), variantTotalPage);
                productVariantsMap.put(p.getId(), pagedVariants);
            }

            request.setAttribute("view", "product");
            request.setAttribute("products", products);
            request.setAttribute("productVariantsMap", productVariantsMap);
            request.setAttribute("variantPageMap", variantPageMap);
            request.setAttribute("variantTotalMap", variantTotalMap);
            request.setAttribute("variantTotalPageMap", variantTotalPageMap);
            request.setAttribute("variantPageSize", variantPageSize);
            request.setAttribute("page", page);
            request.setAttribute("pageSize", pageSize);
            request.setAttribute("totalPage", totalPage);
            request.setAttribute("sort", sort);
            request.getRequestDispatcher("/view/home/manager.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("add".equals(action)) {
            Product tempProduct = createProductFromRequest(request, false);

            // If validation fails, the method handles the error and forwards to the appropriate page
            if (!validateProductInput(request, response, tempProduct, false)) {
                return; // Return when validation FAILS (method returns false on failure)
            }

            productDAO.addProduct(tempProduct);
        } else if ("update".equals(action)) {
            Product tempProduct = createProductFromRequest(request, true);

            // If validation fails, the method handles the error and forwards to the appropriate page
            if (!validateProductInput(request, response, tempProduct, true)) {
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
        } catch (Exception e) {
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
        if (name.length() >= 50) {
            handleValidationError("Product name must be less than 50 characters", request, response, tempProduct, isUpdate);
            return false; // Return false when validation FAILS
        }
        if (!name.matches("^[a-zA-Z ]+$")) {
            handleValidationError("Product name can only contain letters and spaces", request, response, tempProduct, isUpdate);
            return false; // Return false when validation FAILS
        }

        if (productDAO.isProductNameExists(name, tempProduct.getId())) {
            handleValidationError("Product name already exists", request, response, tempProduct, isUpdate);
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

        if (errorMessage.contains("name")) {
            request.setAttribute("errorField", "name");
        } else if (errorMessage.contains("description")) {
            request.setAttribute("errorField", "description");
        } else if (errorMessage.contains("information")) {
            request.setAttribute("errorField", "information");
        } else if (errorMessage.contains("guideline")) {
            request.setAttribute("errorField", "guideline");
        } else if (errorMessage.contains("category")) {
            request.setAttribute("errorField", "subProductCategoryId");
        }

        if (isUpdate) {
            request.getRequestDispatcher("/view/manage/product-edit.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/view/manage/product-add.jsp").forward(request, response);
        }
    }

}