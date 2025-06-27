<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div class="content-wrapper">
    <div class="ms-4" style="margin-top: -50px;">
        <h4 class="mb-1 mt-3">Manage Blog</h4>
        <p class="text-muted mb-4">${editBlog != null ? 'Edit Blog' : 'Add Blog'}</p>

        <div class="bg-white p-4 rounded border">
            <form method="post" action="blog-manage" enctype="multipart/form-data" onsubmit="return validateForm();">
                <input type="hidden" name="action" value="${editBlog != null ? 'update' : 'add'}" />
                <input type="hidden" name="id" value="${editBlog != null ? editBlog.blogId : ''}" />
                <input type="hidden" name="page" value="${page}" />

                <!-- Title -->
                <div class="mb-3">
                    <label for="title" class="form-label">Title</label>
                    <input type="text" name="title" id="title" class="form-control" maxlength="255"
                           value="${editBlog != null ? editBlog.blogTitle : ''}" />
                    <small class="text-danger d-none" id="titleError">Title is required</small>
                </div>

                <!-- Category -->
                <div class="mb-3">
                    <label for="categoryId" class="form-label">Category</label>
                    <select name="categoryId" id="categoryId" class="form-control">
                        <option value="">-- Select category --</option>
                        <c:forEach var="cat" items="${categories}">
                            <option value="${cat.id}"
                                    <c:if test="${not empty editBlog and not empty editBlog.blogCategory and cat.id eq editBlog.blogCategory.id}">
                                        selected
                                    </c:if>>
                                    ${cat.name}
                            </option>
                        </c:forEach>
                    </select>
                    <small class="text-danger d-none" id="categoryError">Category is required</small>
                </div>

                <!-- Description -->
                <div class="mb-3">
                    <label for="description" class="form-label">Description</label>
                    <textarea name="description" id="description" class="form-control" rows="4" maxlength="1000">${editBlog != null ? editBlog.blogDescription : ''}</textarea>
                    <small class="text-danger d-none" id="descriptionError">Description is required</small>
                </div>

                <!-- Image -->
                <div class="mb-3">
                    <label class="form-label">Image</label>
                    <input type="file" name="image" class="form-control" accept="image/*" />
                    <c:if test="${editBlog != null && editBlog.imageUrl != null}">
                        <div class="mt-2">
                            <img src="${pageContext.request.contextPath}/${editBlog.imageUrl}" width="150" class="img-thumbnail" />
                        </div>
                    </c:if>
                </div>

                <!-- Buttons -->
                <div class="mt-4 d-flex gap-2">
                    <button type="submit" class="btn btn-primary">${editBlog != null ? 'Update' : 'Add'} Blog</button>
                    <a href="blog-manage?page=${page}" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        </div>
    </div>
</div>



<script>
    function validateForm() {
        let isValid = true;

        // Reset lỗi
        document.getElementById("titleError").classList.add("d-none");
        document.getElementById("categoryError").classList.add("d-none");
        document.getElementById("descriptionError").classList.add("d-none");

        const title = document.getElementById("title").value.trim();
        const category = document.getElementById("categoryId").value;
        const description = document.getElementById("description").value.trim();

        // Helper kiểm tra toàn số
        function isAllDigits(str) {
            return /^\d+$/.test(str);
        }

        // Validate Title
        if (title === "") {
            document.getElementById("titleError").innerText = "Title is required";
            document.getElementById("titleError").classList.remove("d-none");
            isValid = false;
        } else if (title.length > 100) {
            document.getElementById("titleError").innerText = "Title must be 100 characters or less";
            document.getElementById("titleError").classList.remove("d-none");
            isValid = false;
        } else if (isAllDigits(title)) {
            document.getElementById("titleError").innerText = "Title must not be all numbers";
            document.getElementById("titleError").classList.remove("d-none");
            isValid = false;
        }

        // Validate Category
        if (category === "") {
            document.getElementById("categoryError").classList.remove("d-none");
            isValid = false;
        }

        // Validate Description
        if (description === "") {
            document.getElementById("descriptionError").innerText = "Description is required";
            document.getElementById("descriptionError").classList.remove("d-none");
            isValid = false;
        } else if (isAllDigits(description)) {
            document.getElementById("descriptionError").innerText = "Description must not be all numbers";
            document.getElementById("descriptionError").classList.remove("d-none");
            isValid = false;
        }

        return isValid;
    }
</script>


