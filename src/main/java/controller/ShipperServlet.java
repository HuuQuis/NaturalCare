package controller;

//import dao.ProvinceDAO;
//import dao.ShipperAreaDAO;
//import dao.UserDAO;
//import dao.DistrictDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.District;
import model.User;

import java.io.IOException;
import java.util.List;

@WebServlet("/shipper")
public class ShipperServlet extends HttpServlet {
//    private final UserDAO userDAO = new UserDAO();
//    private final DistrictDAO districtDAO = new DistrictDAO();
//    private final ShipperAreaDAO shipperAreaDAO = new ShipperAreaDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String pageStr = req.getParameter("page");
        int page = pageStr != null ? Integer.parseInt(pageStr) : 1;
        int pageSize = 10;
        String keyword = req.getParameter("keyword");
        int offset = (page - 1) * pageSize;

//        List<User> shippers = userDAO.getAllShippersWithPagination(keyword, offset, pageSize);
//        int totalShippers = userDAO.countShippers(keyword);
//        int totalPage = (int) Math.ceil((double) totalShippers / pageSize);
//        List<District> districtList = districtDAO.getAllDistricts();
//
//        req.setAttribute("list", shippers);
//        req.setAttribute("districtList", districtList);
//        req.setAttribute("page", page);
//        req.setAttribute("totalPage", totalPage);
//        req.getRequestDispatcher("view/shipper/shipper-manage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String action = req.getParameter("action");
        String idStr = req.getParameter("id");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String phone = req.getParameter("phone");
        String districtCode = req.getParameter("districtCode");

//        if ("add".equals(action)) {
//            boolean created = userDAO.createShipper(username, password, email, firstName, lastName, phone);
//            if (created) {
//                int shipperId = userDAO.getUserIdByUsername(username);
//                shipperAreaDAO.assignDistrict(shipperId, districtCode);
//                req.getSession().setAttribute("message", "Shipper created successfully");
//                req.getSession().setAttribute("messageType", "success");
//            } else {
//                req.getSession().setAttribute("message", "Failed to create shipper");
//                req.getSession().setAttribute("messageType", "danger");
//            }
//        } else if ("update".equals(action) && idStr != null) {
//            int shipperId = Integer.parseInt(idStr);
//            boolean updated = userDAO.updateShipper(shipperId, username, email, firstName, lastName, phone);
//            if (updated) {
//                shipperAreaDAO.deleteAssignment(shipperId);
//                shipperAreaDAO.assignDistrict(shipperId, districtCode);
//                req.getSession().setAttribute("message", "Shipper updated successfully");
//                req.getSession().setAttribute("messageType", "success");
//            } else {
//                req.getSession().setAttribute("message", "Failed to update shipper");
//                req.getSession().setAttribute("messageType", "danger");
//            }
//        } else if ("delete".equals(action) && idStr != null) {
//            int shipperId = Integer.parseInt(idStr);
//            boolean deleted = userDAO.deleteShipper(shipperId);
//            if (deleted) {
//                shipperAreaDAO.deleteAssignment(shipperId);
//                req.getSession().setAttribute("message", "Shipper deleted successfully");
//                req.getSession().setAttribute("messageType", "success");
//            } else {
//                req.getSession().setAttribute("message", "Failed to delete shipper");
//                req.getSession().setAttribute("messageType", "danger");
//            }
//        }
        resp.sendRedirect("shipper");
    }
}
