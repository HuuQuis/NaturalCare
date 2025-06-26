package controller;

import dal.AddressDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Address;
import model.User;

import java.io.IOException;
import java.util.List;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    AddressDAO addressDAO = new AddressDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser == null) {
            response.sendRedirect("login");
            return;
        }

        List<Address> addressList = addressDAO.getAddressesByUserId(currentUser.getId());
        request.setAttribute("addressList", addressList);
        request.getRequestDispatcher("/view/checkout/checkout.jsp").forward(request, response);
    }
}
