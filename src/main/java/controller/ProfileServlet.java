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

        try {
            // Không tạo session mới nếu chưa tồn tại
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("user") == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            User user = (User) session.getAttribute("user");

            // Load danh mục và danh mục con
            ProductCategoryDAO categoryDAO = new ProductCategoryDAO();
            SubProductCategoryDAO subCategoryDAO = new SubProductCategoryDAO();

            List<ProductCategory> categories = categoryDAO.getAllProductCategories();
            List<SubProductCategory> subCategories = subCategoryDAO.getAllSubProductCategories();

            // Kiểm tra nếu không load được dữ liệu
            if (categories == null || subCategories == null) {
                request.setAttribute("error", "Không thể tải danh mục sản phẩm. Vui lòng thử lại sau.");
            }

            // Đẩy dữ liệu sang JSP
            request.setAttribute("categories", categories);
            request.setAttribute("subCategories", subCategories);
            request.setAttribute("user", user);

            request.getRequestDispatcher("/view/common/profile.jsp").forward(request, response);

        } catch (Exception e) {
            // Trả lỗi nội bộ (500) nếu có exception
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Đã xảy ra lỗi khi tải trang hồ sơ.");
        }
    }
}
