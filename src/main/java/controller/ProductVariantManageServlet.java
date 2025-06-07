package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Product;
import model.ProductVariation;
import dal.ProductDAO;
import java.util.List;

import java.io.IOException;

@WebServlet(name = "ProductVariantManageServlet", urlPatterns = {"/productVariantManage"})
public class ProductVariantManageServlet extends HttpServlet {
    ProductDAO productDao = new ProductDAO();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        List<Product> products = productDao.getAllProducts();
        request.setAttribute("products", products);
        if ("edit".equals(action)) {

        } else if ("add".equals(action)) {
            request.getRequestDispatcher("/view/manage/productvariant-add.jsp").forward(request, response);
        }else {

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        if ("add".equals(action)) {

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
}
