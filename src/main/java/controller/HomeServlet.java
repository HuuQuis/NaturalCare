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
import model.blogCategory;
import model.productCategory;
import model.subProductCategory;

@WebServlet(name = "HomeServlet", value = "/home")
public class HomeServlet extends  HttpServlet{
    private ProductCategoryDAO categoryDAO;
    private BlogCategoryDAO blogCategoryDAO;
    private SubProductCategoryDAO subProductCategoryDAO;

    @Override
    public void init() {
        categoryDAO = new ProductCategoryDAO();
        blogCategoryDAO = new BlogCategoryDAO();
        subProductCategoryDAO = new SubProductCategoryDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<productCategory> categories = categoryDAO.getAllProductCategories();
        List<blogCategory> blogCategories = blogCategoryDAO.getAllBlogCategories();
        List<subProductCategory> subCategories = new ArrayList<>();
        for (productCategory category : categories) {
            subCategories.addAll(subProductCategoryDAO.getSubCategoriesByProductId(category.getId()));
        }

        request.setAttribute("categories", categories);
        request.setAttribute("blogCategories", blogCategories);
        request.setAttribute("subCategories", subCategories);
        request.getRequestDispatcher("view/home/home.jsp").forward(request, response);
    }
}
