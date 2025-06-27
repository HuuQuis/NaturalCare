package controller;

import dal.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User sessionUser = (User) session.getAttribute("user");
        User updatedUser = userDAO.getUserById(sessionUser.getId());

        session.setAttribute("user", updatedUser); // cập nhật session
        request.setAttribute("user", updatedUser); // gửi sang JSP

        request.getRequestDispatcher("/view/common/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        // Lấy dữ liệu từ form
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");

        try {
            // Cập nhật user object
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPhone(phone);
            user.setEmail(email);

            // Gọi DAO để cập nhật DB
            UserDAO dao = new UserDAO();
            dao.updateUserProfile(user);

            // Cập nhật lại session
            session.setAttribute("user", user);

            // Gửi thông báo thành công
            request.setAttribute("message", "Cập nhật thành công!");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Cập nhật thất bại. Vui lòng thử lại!");
        }

        request.getRequestDispatcher("/view/common/profile.jsp").forward(request, response);
    }
}
