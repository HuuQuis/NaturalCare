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
            case "getDefault":
                handleGetDefaultAddress(request, response, addressDAO, user.getId());
                break;
            case "delete":
                handleDeleteAddress(request, response, addressDAO, user.getId());
                break;
            default:
                handleListAddresses(request, response, addressDAO, user.getId());
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
            case "setDefault":
                handleSetDefaultAddress(request, response, addressDAO, user.getId());
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                break;
        }
    }

    private void handleListAddresses(HttpServletRequest request, HttpServletResponse response,
                                     AddressDAO addressDAO, int userId)
            throws ServletException, IOException {

        List<Address> addresses = addressDAO.getAddressesByUserId(userId);

        // Set response type for AJAX request
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Convert to JSON manually or use a JSON library
        StringBuilder json = new StringBuilder();
        json.append("{\"addresses\":[");

        for (int i = 0; i < addresses.size(); i++) {
            Address addr = addresses.get(i);
            json.append("{");
            json.append("\"addressId\":").append(addr.getAddressId()).append(",");
            json.append("\"fullAddress\":\"").append(escapeJson(addr.getFullAddress())).append("\",");
            json.append("\"detail\":\"").append(escapeJson(addr.getDetail())).append("\",");
            json.append("\"wardName\":\"").append(escapeJson(addr.getWardName())).append("\",");
            json.append("\"districtName\":\"").append(escapeJson(addr.getDistrictName())).append("\",");
            json.append("\"provinceName\":\"").append(escapeJson(addr.getProvinceName())).append("\",");
            json.append("\"addressType\":\"").append(addr.getAddressType()).append("\"");
            json.append("}");

            if (i < addresses.size() - 1) {
                json.append(",");
            }
        }

        json.append("]}");

        response.getWriter().write(json.toString());
    }

    private void handleGetDefaultAddress(HttpServletRequest request, HttpServletResponse response,
                                         AddressDAO addressDAO, int userId)
            throws ServletException, IOException {

        Address defaultAddress = addressDAO.getDefaultAddress(userId);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (defaultAddress != null) {
            StringBuilder json = new StringBuilder();
            json.append("{");
            json.append("\"addressId\":").append(defaultAddress.getAddressId()).append(",");
            json.append("\"fullAddress\":\"").append(escapeJson(defaultAddress.getFullAddress())).append("\",");
            json.append("\"detail\":\"").append(escapeJson(defaultAddress.getDetail())).append("\"");
            json.append("}");

            response.getWriter().write(json.toString());
        } else {
            response.getWriter().write("{\"error\":\"No default address found\"}");
        }
    }

    private void handleAddAddress(HttpServletRequest request, HttpServletResponse response,
                                  AddressDAO addressDAO, int userId)
            throws ServletException, IOException {

        // Lấy parameters từ form
        int provinceCode = Integer.parseInt(request.getParameter("provinceCode"));
        String provinceName = request.getParameter("provinceName");
        int districtCode = Integer.parseInt(request.getParameter("districtCode"));
        String districtName = request.getParameter("districtName");
        int wardCode = Integer.parseInt(request.getParameter("wardCode"));
        String wardName = request.getParameter("wardName");
        String detail = request.getParameter("detail");

        Address newAddress = new Address();
        newAddress.setProvinceCode(provinceCode);
        newAddress.setProvinceName(provinceName);
        newAddress.setDistrictCode(districtCode);
        newAddress.setDistrictName(districtName);
        newAddress.setWardCode(wardCode);
        newAddress.setWardName(wardName);
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

    private void handleSetDefaultAddress(HttpServletRequest request, HttpServletResponse response,
                                         AddressDAO addressDAO, int userId)
            throws ServletException, IOException {

        int addressId = Integer.parseInt(request.getParameter("addressId"));

        boolean success = addressDAO.setDefaultAddress(userId, addressId);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (success) {
            response.getWriter().write("{\"success\":true,\"message\":\"Default address updated\"}");
        } else {
            response.getWriter().write("{\"success\":false,\"message\":\"Failed to update default address\"}");
        }
    }

    private void handleDeleteAddress(HttpServletRequest request, HttpServletResponse response,
                                     AddressDAO addressDAO, int userId)
            throws ServletException, IOException {

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