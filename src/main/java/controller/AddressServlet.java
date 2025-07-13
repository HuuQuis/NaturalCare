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
                case "setDefault":
                    handleSetDefaultAddress(request, response, addressDAO, userId);
                    break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    private void handleListAddresses(HttpServletRequest request, HttpServletResponse response,
                                     AddressDAO addressDAO, int userId) throws IOException {

        List<Address> addresses;
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

        StringBuilder json = new StringBuilder("{\"addresses\":[");
        for (int i = 0; i < addresses.size(); i++) {
            Address addr = addresses.get(i);

            String provinceName = (addr.getProvince() != null) ? addr.getProvince().getName() : "";
            String districtName = (addr.getDistrict() != null) ? addr.getDistrict().getName() : "";
            String wardName = (addr.getWard() != null) ? addr.getWard().getName() : "";

            json.append("{")
                    .append("\"addressId\":").append(addr.getAddressId()).append(",")
                    .append("\"provinceCode\":\"").append(addr.getProvinceCode()).append("\",")
                    .append("\"provinceName\":\"").append(escapeJson(provinceName)).append("\",")
                    .append("\"districtCode\":\"").append(addr.getDistrictCode()).append("\",")
                    .append("\"districtName\":\"").append(escapeJson(districtName)).append("\",")
                    .append("\"wardCode\":\"").append(addr.getWardCode()).append("\",")
                    .append("\"wardName\":\"").append(escapeJson(wardName)).append("\",")
                    .append("\"detail\":\"").append(escapeJson(addr.getDetail())).append("\",")
                    .append("\"firstName\":\"").append(escapeJson(addr.getFirstName())).append("\",")
                    .append("\"lastName\":\"").append(escapeJson(addr.getLastName())).append("\",")
                    .append("\"email\":\"").append(escapeJson(addr.getEmail())).append("\",")
                    .append("\"phoneNumber\":\"").append(escapeJson(addr.getPhoneNumber())).append("\",")
                    .append("\"distanceKm\":").append(addr.getDistanceKm()).append(",")
                    .append("\"isDefault\":").append(addr.isDefaultAddress())
                    .append("}");

            if (i < addresses.size() - 1) {
                json.append(",");
            }
        }
        json.append("]}");

        response.getWriter().write(json.toString());
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

            JSONObject addressJson = getJsonObject(address);

            JSONObject responseJson = new JSONObject();
            responseJson.put("success", true);
            responseJson.put("address", addressJson);

            response.getWriter().write(responseJson.toString());

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"success\":false,\"message\":\"Failed to retrieve address\"}");
        }
    }

    private static JSONObject getJsonObject(Address address) {
        JSONObject addressJson = new JSONObject();
        addressJson.put("addressId", address.getAddressId());
        addressJson.put("provinceCode", address.getProvinceCode());
        addressJson.put("provinceName", address.getProvince() != null ? address.getProvince().getName() : "");
        addressJson.put("districtCode", address.getDistrictCode());
        addressJson.put("districtName", address.getDistrict() != null ? address.getDistrict().getName() : "");
        addressJson.put("wardCode", address.getWardCode());
        addressJson.put("wardName", address.getWard() != null ? address.getWard().getName() : "");
        addressJson.put("detail", address.getDetail());
        addressJson.put("firstName", address.getFirstName());
        addressJson.put("lastName", address.getLastName());
        addressJson.put("email", address.getEmail());
        addressJson.put("phoneNumber", address.getPhoneNumber());
        addressJson.put("distanceKm", address.getDistanceKm());
        return addressJson;
    }

    private void handleAddAddress(HttpServletRequest request, HttpServletResponse response,
                                  AddressDAO dao, int userId) throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Address address = new Address();
        String error = readAndValidateAddress(request, address, dao, null);
        if (error != null) {
            response.getWriter().write("{\"success\":false,\"message\":\"" + escapeJson(error) + "\"}");
            return;
        }

        boolean success = dao.addAddress(address, userId);
        response.getWriter().write("{\"success\":" + success + ",\"message\":\"" +
                (success ? "Address added successfully" : "Failed to add address") + "\"}");
    }

    private void handleUpdateAddress(HttpServletRequest request, HttpServletResponse response,
                                     AddressDAO dao, int userId) throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String addressIdStr = request.getParameter("addressId");
        if (addressIdStr == null) {
            response.getWriter().write("{\"success\":false,\"message\":\"Missing address ID\"}");
            return;
        }

        try {
            int addressId = Integer.parseInt(addressIdStr);
            Address address = new Address();
            address.setAddressId(addressId);

            String error = readAndValidateAddress(request, address, dao, addressId);
            if (error != null) {
                response.getWriter().write("{\"success\":false,\"message\":\"" + escapeJson(error) + "\"}");
                return;
            }

            boolean updated = dao.updateAddress(address, userId);
            response.getWriter().write("{\"success\":" + updated + ",\"message\":\"" +
                    (updated ? "Address updated successfully" : "Failed to update address") + "\"}");

        } catch (NumberFormatException e) {
            response.getWriter().write("{\"success\":false,\"message\":\"Invalid address ID\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"success\":false,\"message\":\"Internal error\"}");
        }
    }

    private void handleDeleteAddress(HttpServletRequest request, HttpServletResponse response,
                                     AddressDAO addressDAO, int userId) throws IOException {

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

    private void handleSetDefaultAddress(HttpServletRequest request, HttpServletResponse response,
                                  AddressDAO addressDAO, int userId) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            int addressId = Integer.parseInt(request.getParameter("addressId"));

            boolean result = addressDAO.setDefaultAddress(userId, addressId);

            if (result) {
                response.getWriter().write("{\"success\":true,\"message\":\"Set as default successfully\"}");
            } else {
                response.getWriter().write("{\"success\":false,\"message\":\"Failed to set default address\"}");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"success\":false,\"message\":\"Invalid input or internal error\"}");
        }
    }


    private String validateDetail(String detail) {
        if (detail == null || detail.trim().isEmpty()) {
            return "Detail must not be empty or whitespace only.";
        }
        if (detail.trim().length() > 100) {
            return "Detail must not exceed 100 characters.";
        }
        if (!detail.matches("^[a-zA-Z0-9À-ỹ\\s]+$")) {
            return "Detail can only contain letters, numbers, and spaces.";
        }
        return null;
    }

    private String escapeJson(String input) {
        if (input == null) return "";
        return input.replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    private String validateInput(String firstName, String lastName, String email, String phoneNumber, String detail) {
        if (isBlank(firstName)) return "First name must not be empty.";
        if (!firstName.matches("^[A-Za-zÀ-ỹ]+( [A-Za-zÀ-ỹ]+)*$")) return "First name must contain only letters and single spaces between words.";

        if (isBlank(lastName)) return "Last name must not be empty.";
        if (!lastName.matches("^[A-Za-zÀ-ỹ]+( [A-Za-zÀ-ỹ]+)*$")) return "Last name must contain only letters and single spaces between words.";

        if (isBlank(email)) return "Email must not be empty.";
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.com$")) return "Email must be in a valid format (e.g. name@example.com).";

        if (isBlank(phoneNumber)) return "Phone number must not be empty.";
        if (!phoneNumber.matches("^0\\d{9}$")) return "Phone number must be exactly 10 digits and start with 0.";

        String detailError = validateDetail(detail);
        if (detailError != null) return detailError;

        return null; // OK
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private String readAndValidateAddress(HttpServletRequest request, Address address, AddressDAO dao, Integer excludeAddressId) {
        String provinceCode = request.getParameter("provinceCode");
        String districtCode = request.getParameter("districtCode");
        String wardCode = request.getParameter("wardCode");
        String detail = request.getParameter("detail");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");

        // Gán vào object
        address.setProvinceCode(provinceCode);
        address.setDistrictCode(districtCode);
        address.setWardCode(wardCode);
        address.setDetail(detail != null ? detail.trim() : "");
        address.setFirstName(firstName != null ? firstName.trim() : "");
        address.setLastName(lastName != null ? lastName.trim() : "");
        address.setEmail(email != null ? email.trim() : "");
        address.setPhoneNumber(phoneNumber != null ? phoneNumber.trim() : "");

        // Validate format
        String validationError = validateInput(firstName, lastName, email, phoneNumber, detail);
        if (validationError != null) return validationError;

        // Validate uniqueness
        if (dao.isEmailUsed(email.trim(), excludeAddressId)) return "Email is already in use.";
        if (dao.isPhoneUsed(phoneNumber.trim(), excludeAddressId)) return "Phone number is already in use.";

        return null;
    }

}
