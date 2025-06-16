package controller;

import dal.ProductCategoryDAO;
import dal.SubProductCategoryDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.ProductCategory;
import model.SubProductCategory;
import model.User;

import java.io.IOException;
import java.util.List;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // Nếu chưa đăng nhập, chuyển về trang login
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Load danh mục để header-bottom.jsp hiển thị đúng menu
        ProductCategoryDAO categoryDAO = new ProductCategoryDAO();
        SubProductCategoryDAO subCategoryDAO = new SubProductCategoryDAO();

        List<ProductCategory> categories = categoryDAO.getAllProductCategories();
        List<SubProductCategory> subCategories = subCategoryDAO.getAllSubProductCategories();

        // Truyền dữ liệu vào request
        request.setAttribute("categories", categories);
        request.setAttribute("subCategories", subCategories);
        request.setAttribute("user", user);

        // Forward đến JSP hiển thị hồ sơ
        request.getRequestDispatcher("/view/common/profile.jsp").forward(request, response);
    }
}
