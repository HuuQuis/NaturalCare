package controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import dal.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Product;

@WebServlet(name = "HomeServlet", value = "/home")
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductDAO dao = new ProductDAO();
        List<Product> topProducts = dao.getTop10BestSellingProducts();
        request.setAttribute("topProducts", topProducts);
        List<Product> newProducts = dao.getTop10NewArriveProducts();
        request.setAttribute("newProducts", newProducts);
        Map<String, List<Product>> productsByCategory = dao.getAllProductsGroupedByCategory();
        request.setAttribute("productsByCategory", productsByCategory);
        request.getRequestDispatcher("view/home/home.jsp").forward(request, response);
    }
}