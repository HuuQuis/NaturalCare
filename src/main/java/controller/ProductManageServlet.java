package controller;


import dal.ProductDAO;
import dal.SubProductCategoryDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Product;
import model.ProductVariation;
import model.SubProductCategory;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductManageServlet", urlPatterns = {"/productManage"})
public class ProductManageServlet extends HttpServlet {
    ProductDAO productDAO = new ProductDAO();
    SubProductCategoryDAO subProductCategoryDAO = new SubProductCategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            String name = request.getParameter("name").trim();
            String description = request.getParameter("description").trim();
            String information = request.getParameter("information").trim();
            String guideline = request.getParameter("guideline").trim();
            int subProductCategoryId = 0;
            if (name == null || name.isEmpty()) {
                request.setAttribute("error", "Product name cannot be empty");
                request.getRequestDispatcher("/view/manage/product-add.jsp").forward(request, response);
                return;
            }
            if (description == null || description.isEmpty()) {
                request.setAttribute("error", "Product description cannot be empty");
                request.getRequestDispatcher("/view/manage/product-add.jsp").forward(request, response);
                return;
            }
            if (information == null || information.isEmpty()) {

                request.setAttribute("error", "Product information cannot be empty");
                request.getRequestDispatcher("/view/manage/product-add.jsp").forward(request, response);
                return;
            }
            if (guideline == null || guideline.isEmpty()) {
                request.setAttribute("error", "Product guideline cannot be empty");
                request.getRequestDispatcher("/view/manage/product-add.jsp").forward(request, response);
                return;
            }
            try {
                subProductCategoryId = Integer.parseInt(request.getParameter("subProductCategoryId"));
                if (subProductCategoryId <= 0) {
                    throw new NumberFormatException();
                }
            } catch (Exception e) {
                request.setAttribute("error", "Please select a valid category");
                request.getRequestDispatcher("/view/manage/product-add.jsp").forward(request, response);
                return;
            }

            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setInformation(information);
            product.setGuideline(guideline);
            product.setSubProductCategoryId(subProductCategoryId);

            productDAO.addProduct(product);
        } else if ("update".equals(action)) {
            int productId = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String information = request.getParameter("information");
            String guideline = request.getParameter("guideline");
            int subProductCategoryId = 0;
            try {
                subProductCategoryId = Integer.parseInt(request.getParameter("subProductCategoryId"));
            } catch (Exception e) {
                // ignore, keep as 0
            }
            Product product = new Product();
            product.setId(productId);
            product.setName(name);
            product.setDescription(description);
            product.setInformation(information);
            product.setGuideline(guideline);
            product.setSubProductCategoryId(subProductCategoryId);

            productDAO.updateProduct(product);
        }else if ("delete".equals(action)) {
            int productId = Integer.parseInt(request.getParameter("id"));
            productDAO.deleteProduct(productId);
        }

        response.sendRedirect(request.getContextPath() + "/productManage");
    }
}
