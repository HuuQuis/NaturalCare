package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
                response.sendRedirect("/login");
                return;
            }

            User user = (User) session.getAttribute("user");

            request.setAttribute("user", user);

            request.getRequestDispatcher("/view/common/profile.jsp").forward(request, response);

        } catch (Exception e) {
            // Trả lỗi nội bộ (500) nếu có exception
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Đã xảy ra lỗi khi tải trang hồ sơ.");
        }
    }
}
