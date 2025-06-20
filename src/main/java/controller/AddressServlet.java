package controller;

import dal.AddressDAO;
import model.Address;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/address")
public class AddressServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) action = "list";

        AddressDAO addressDAO = new AddressDAO();

        switch (action) {
            case "list":
                handleListAddresses(request, response, addressDAO, user.getId());
                break;
            case "delete":
                handleDeleteAddress(request, response, addressDAO, user.getId());
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        String action = request.getParameter("action");

        AddressDAO addressDAO = new AddressDAO();

        switch (action) {
            case "add":
                handleAddAddress(request, response, addressDAO, user.getId());
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                break;
        }
    }

    private void handleListAddresses(HttpServletRequest request, HttpServletResponse response,
                                     AddressDAO addressDAO, int userId)
            throws IOException {

        List<Address> addresses = addressDAO.getAddressesByUserId(userId);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        StringBuilder json = new StringBuilder();
        json.append("{\"addresses\":[");

        for (int i = 0; i < addresses.size(); i++) {
            Address addr = addresses.get(i);
            json.append("{");
            json.append("\"addressId\":").append(addr.getAddressId()).append(",");
            json.append("\"provinceCode\":\"").append(addr.getProvinceCode()).append("\",");
            json.append("\"districtCode\":\"").append(addr.getDistrictCode()).append("\",");
            json.append("\"wardCode\":\"").append(addr.getWardCode()).append("\",");
            json.append("\"detail\":\"").append(escapeJson(addr.getDetail())).append("\",");
            json.append("\"distanceKm\":").append(addr.getDistanceKm());
            json.append("}");

            if (i < addresses.size() - 1) {
                json.append(",");
            }
        }

        json.append("]}");

        response.getWriter().write(json.toString());
    }

    private void handleAddAddress(HttpServletRequest request, HttpServletResponse response,
                                  AddressDAO addressDAO, int userId)
            throws IOException {

        String provinceCode = request.getParameter("provinceCode");
        String districtCode = request.getParameter("districtCode");
        String wardCode = request.getParameter("wardCode");
        String detail = request.getParameter("detail");

        Address newAddress = new Address();
        newAddress.setProvinceCode(provinceCode);
        newAddress.setDistrictCode(districtCode);
        newAddress.setWardCode(wardCode);
        newAddress.setDetail(detail);

        boolean success = addressDAO.addAddress(newAddress, userId);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (success) {
            response.getWriter().write("{\"success\":true,\"message\":\"Address added successfully\"}");
        } else {
            response.getWriter().write("{\"success\":false,\"message\":\"Failed to add address\"}");
        }
    }

    private void handleDeleteAddress(HttpServletRequest request, HttpServletResponse response,
                                     AddressDAO addressDAO, int userId)
            throws IOException {

        int addressId = Integer.parseInt(request.getParameter("addressId"));

        boolean success = addressDAO.deleteAddress(addressId, userId);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (success) {
            response.getWriter().write("{\"success\":true,\"message\":\"Address deleted successfully\"}");
        } else {
            response.getWriter().write("{\"success\":false,\"message\":\"Failed to delete address\"}");
        }
    }

    private String escapeJson(String input) {
        if (input == null) return "";
        return input.replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
