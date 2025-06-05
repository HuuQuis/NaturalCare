package controller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import dal.BlogCategoryDAO;
import dal.ProductCategoryDAO;
import dal.SubProductCategoryDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.BlogCategory;
import model.ProductCategory;
import model.SubProductCategory;

@WebServlet(name = "StaffHomeServlet", value = "/staffHome")
public class StaffHomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("view/staff/home.jsp").forward(request, response);
    }
}
