package controller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import dal.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.*;

@WebServlet(name = "ExpertListManagementServlet", value = "/expertListManage")
public class ExpertListManagementServlet extends HttpServlet {
    private static final int PAGE_SIZE = 10;

    @Override                            
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String search = request.getParameter("search");
        String skill = request.getParameter("skill");
        String pageStr = request.getParameter("page");
        int page = pageStr != null ? Integer.parseInt(pageStr) : 1;

        ExpertDAO expertDAO = new ExpertDAO();

        int totalOrders = expertDAO.countExpert(search, skill);
        int totalPages = (int) Math.ceil((double) totalOrders / PAGE_SIZE);

        List<ExpertSkill> orders = expertDAO.getExpertsWithPagination(search, skill, page, PAGE_SIZE);

        System.out.println(orders);
                          
        request.setAttribute("experts", orders);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("search", search);
        request.setAttribute("skill", skill);

        request.setAttribute("view", "expert-skill");
        request.getRequestDispatcher("/view/home/manager.jsp").forward(request, response);

    }
}
