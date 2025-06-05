package controller;

import dal.ProductCategoryDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import model.ProductCategory;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category")
public class CategoryServlet extends HttpServlet {
    ProductCategoryDAO dao = new ProductCategoryDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            ProductCategory category = dao.getById(id);
            request.setAttribute("category", category);
            request.getRequestDispatcher("view/category/form.jsp").forward(request, response);
        } else {
            List<ProductCategory> list = dao.getAllProductCategories();
            request.setAttribute("view", "category");
            request.setAttribute("list", list);
            request.getRequestDispatcher("/view/home/manager.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String name = request.getParameter("name");
        String idRaw = request.getParameter("id");

        if ("add".equals(action)) {
            dao.addProductCategory(name);
        } else if ("update".equals(action)) {
            int id = Integer.parseInt(idRaw);
            dao.updateProductCategory(id, name);
        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(idRaw);
            dao.deleteProductCategory(id);
        }
        response.sendRedirect("category");
    }
}
