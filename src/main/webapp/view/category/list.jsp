<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.ProductCategory" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    List<ProductCategory> list = (List<ProductCategory>) request.getAttribute("list");
%>

<div class="container" style="margin-left: 240px; padding: 100px 20px 20px 20px; max-width: 1000px;">
    <h2 style="color: #2e7d32; text-align:center;">Category Management</h2>
    <a href="view/category/form.jsp" class="btn btn-success mb-3">
        <i class="fa fa-plus"></i> Add New Category
    </a>

    <table class="table table-bordered table-hover">
        <thead class="table-light">
        <tr>
            <th>#</th>
            <th>Category Name</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="c" items="${list}" varStatus="loop">
            <tr>
                <td>${loop.index + 1}</td>
                <td>${c.name}</td>
                <td>
                    <a class="btn btn-warning btn-sm" href="category?action=edit&id=${c.id}">
                        <i class="fa fa-pencil-alt"></i> Edit
                    </a>
                    <form action="category" method="post" style="display:inline;" onsubmit="return confirm('Are you sure to delete this category?');">
                        <input type="hidden" name="action" value="delete"/>
                        <input type="hidden" name="id" value="${c.id}"/>
                        <button type="submit" class="btn btn-danger btn-sm">
                            <i class="fa fa-trash"></i> Delete
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
