package controller;

import java.io.*;
import java.util.List;

import dal.ProductCategoryDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.productCategory;

@WebServlet(name = "CategoryServlet", value = "/category")
public class CategoryServlet extends  HttpServlet{
    private ProductCategoryDAO categoryDAO;

    @Override
    public void init() {
        categoryDAO = new ProductCategoryDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<productCategory> categories = categoryDAO.getAllProductCategories();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("view/home/home.jsp").forward(request, response);
    }
}
