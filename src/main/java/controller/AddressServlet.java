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
import jakarta.servlet.annotation.MultipartConfig;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@MultipartConfig
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

        int userId = user.getId();

        String action = request.getParameter("action");
        if (action == null) action = "list";

        AddressDAO addressDAO = new AddressDAO();

        switch (action) {
            case "list":
                handleListAddresses(request, response, addressDAO, userId);
                break;
            case "get":
                handleGetAddress(request, response, addressDAO, userId);
                break;
            case "delete":
                handleDeleteAddress(request, response, addressDAO, userId);
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

        // Debug: Print user and ID
        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        int userId = user.getId();

        String action = request.getParameter("action");

        AddressDAO addressDAO = new AddressDAO();

        switch (action) {
            case "add":
                handleAddAddress(request, response, addressDAO, userId);
                break;
            case "update":
                handleUpdateAddress(request, response, addressDAO, userId);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                break;
        }
    }

    private void handleGetAddress(HttpServletRequest request, HttpServletResponse response,
                                  AddressDAO addressDAO, int userId) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            int addressId = Integer.parseInt(request.getParameter("addressId"));
            Address address = addressDAO.getAddressById(addressId, userId);

            if (address == null) {
                response.getWriter().write("{\"success\":false,\"message\":\"Address not found\"}");
                return;
            }

            JSONObject addressJson = new JSONObject();
            addressJson.put("addressId", address.getAddressId());
            addressJson.put("provinceCode", address.getProvinceCode());
            addressJson.put("provinceName", address.getProvince() != null ? address.getProvince().getName() : "");
            addressJson.put("districtCode", address.getDistrictCode());
            addressJson.put("districtName", address.getDistrict() != null ? address.getDistrict().getName() : "");
            addressJson.put("wardCode", address.getWardCode());
            addressJson.put("wardName", address.getWard() != null ? address.getWard().getName() : "");
            addressJson.put("detail", address.getDetail());
            addressJson.put("distanceKm", address.getDistanceKm());

            JSONObject responseJson = new JSONObject();
            responseJson.put("success", true);
            responseJson.put("address", addressJson);

            response.getWriter().write(responseJson.toString());

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"success\":false,\"message\":\"Failed to retrieve address\"}");
        }
    }

    private void handleListAddresses(HttpServletRequest request, HttpServletResponse response,
                                     AddressDAO addressDAO, int userId)
            throws IOException {

        List<Address> addresses = new ArrayList<>();
        try {
            addresses = addressDAO.getAddressesByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"success\":false,\"message\":\"Error retrieving addresses\"}");
            return;
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        StringBuilder json = new StringBuilder();
        json.append("{\"addresses\":[");

        for (int i = 0; i < addresses.size(); i++) {
            Address addr = addresses.get(i);

            String provinceName = (addr.getProvince() != null) ? addr.getProvince().getName() : "";
            String districtName = (addr.getDistrict() != null) ? addr.getDistrict().getName() : "";
            String wardName = (addr.getWard() != null) ? addr.getWard().getName() : "";

            json.append("{");
            json.append("\"addressId\":").append(addr.getAddressId()).append(",");
            json.append("\"provinceCode\":\"").append(addr.getProvinceCode()).append("\",");
            json.append("\"provinceName\":\"").append(escapeJson(provinceName)).append("\",");
            json.append("\"districtCode\":\"").append(addr.getDistrictCode()).append("\",");
            json.append("\"districtName\":\"").append(escapeJson(districtName)).append("\",");
            json.append("\"wardCode\":\"").append(addr.getWardCode()).append("\",");
            json.append("\"wardName\":\"").append(escapeJson(wardName)).append("\",");
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

    private void handleUpdateAddress(HttpServletRequest request, HttpServletResponse response,
                                     AddressDAO addressDAO, int userId) throws IOException {

        String addressIdStr = request.getParameter("addressId");
        String provinceCode = request.getParameter("provinceCode");
        String districtCode = request.getParameter("districtCode");
        String wardCode = request.getParameter("wardCode");
        String detail = request.getParameter("detail");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (addressIdStr == null) {
            response.getWriter().write("{\"success\":false,\"message\":\"Missing address ID\"}");
            return;
        }

        try {
            int addressId = Integer.parseInt(addressIdStr);

            Address address = new Address();
            address.setAddressId(addressId);
            address.setProvinceCode(provinceCode);
            address.setDistrictCode(districtCode);
            address.setWardCode(wardCode);
            address.setDetail(detail);

            boolean updated = addressDAO.updateAddress(address, userId);

            if (updated) {
                response.getWriter().write("{\"success\":true,\"message\":\"Address updated successfully\"}");
            } else {
                response.getWriter().write("{\"success\":false,\"message\":\"Failed to update address\"}");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"success\":false,\"message\":\"Invalid input or internal error\"}");
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
