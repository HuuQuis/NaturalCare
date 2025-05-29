package controller;


import dal.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Product;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "SearchServlet", value = "/search")
public class SearchServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String txt = request.getParameter("txt");
        ProductDAO productDAO = new ProductDAO();
        List<Product> products = productDAO.searchProductsByText(txt);

        PrintWriter out = response.getWriter();
        for (Product p : products) {
            out.println("<div class='search-item' onclick='selectProduct(" + p.getId() + ")'>");
            out.println("<span class='product-name'>" + p.getName() + "</span> - ");
            out.println("<span class='product-desc'>" + p.getDescription() + "</span>");
            out.println("</div>");
        }
    }
}


