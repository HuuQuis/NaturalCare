<%--
  Created by IntelliJ IDEA.
  User: phung
  Date: 5/27/2025
  Time: 12:12 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Category Management - Cosmetics</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap & FontAwesome -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/font-awesome.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            padding-top: 30px;
        }
        .category-table th, .category-table td {
            vertical-align: middle !important;
        }
        .container h2 {
            margin-bottom: 30px;
        }
    </style>
</head>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>

<body>
<div class="container">
    <h2>Category Management</h2>
    <table class="table table-bordered table-striped category-table">
        <thead class="thead-dark">
        <tr>
            <th>#</th>
            <th>Category Name</th>
            <th>Description</th>
            <th>Parent Category</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>1</td>
            <td>Skincare</td>
            <td>Products for facial and body care</td>
            <td>None</td>
            <td>
                <a href="#" class="btn btn-sm btn-warning editCategoryBtn"
                   data-id="1"
                   data-name="Skincare"
                   data-description="Products for facial and body care"
                   data-parent="None">
                    <i class="fa fa-pencil"></i> Edit
                </a>
                <a href="#" class="btn btn-sm btn-danger"><i class="fa fa-trash"></i> Delete</a>
            </td>
        </tr>
        <tr>
            <td>2</td>
            <td>Makeup</td>
            <td>Cosmetic products for facial makeup</td>
            <td>Beauty</td>
            <td>
                <a href="#" class="btn btn-sm btn-warning editCategoryBtn"
                   data-id="2"
                   data-name="Makeup"
                   data-description="Cosmetic products for facial makeup"
                   data-parent="Beauty">
                    <i class="fa fa-pencil"></i> Edit
                </a>
                <a href="#" class="btn btn-sm btn-danger"><i class="fa fa-trash"></i> Delete</a>
            </td>
        </tr>
        <tr>
            <td>3</td>
            <td>Hair Care</td>
            <td>Shampoo, conditioner and treatments</td>
            <td>Personal Care</td>
            <td>
                <a href="#" class="btn btn-sm btn-warning editCategoryBtn"
                   data-id="3"
                   data-name="Hair Care"
                   data-description="Shampoo, conditioner and treatments"
                   data-parent="Personal Care">
                    <i class="fa fa-pencil"></i> Edit
                </a>
                <a href="#" class="btn btn-sm btn-danger"><i class="fa fa-trash"></i> Delete</a>
            </td>
        </tr>
        </tbody>
    </table>

    <a href="#" class="btn btn-primary" id="addCategoryBtn"><i class="fa fa-plus"></i> Add New Category</a>
</div>

<!-- Modal Add/Edit Category -->
<div class="modal fade" id="categoryModal" tabindex="-1" role="dialog" aria-labelledby="categoryModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <form id="categoryForm">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="categoryModalLabel">Add / Edit Category</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <input type="hidden" id="categoryId" name="categoryId">
                    <div class="form-group">
                        <label for="categoryName">Category Name</label>
                        <input type="text" class="form-control" id="categoryName" name="categoryName" required>
                    </div>
                    <div class="form-group">
                        <label for="categoryDescription">Description</label>
                        <textarea class="form-control" id="categoryDescription" name="description" rows="3"></textarea>
                    </div>
                    <div class="form-group">
                        <label for="parentCategory">Parent Category</label>
                        <select class="form-control" id="parentCategory" name="parentCategory">
                            <option value="">None</option>
                            <option value="Beauty">Beauty</option>
                            <option value="Personal Care">Personal Care</option>
                            <option value="Health">Health</option>
                        </select>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-success">Save</button>
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                </div>
            </div>
        </form>
    </div>
</div>

<!-- JS Libraries -->
<script src="js/jquery.js"></script>
<script src="js/bootstrap.min.js"></script>
<script>
    $(document).ready(function () {
        // Add button click
        $('#addCategoryBtn').click(function (e) {
            e.preventDefault();
            $('#categoryModalLabel').text('Add New Category');
            $('#categoryForm')[0].reset();
            $('#categoryId').val('');
            $('#categoryModal').modal('show');
        });

        // Edit button click
        $('.editCategoryBtn').click(function (e) {
            e.preventDefault();
            const btn = $(this);
            $('#categoryModalLabel').text('Edit Category');
            $('#categoryId').val(btn.data('id'));
            $('#categoryName').val(btn.data('name'));
            $('#categoryDescription').val(btn.data('description'));
            $('#parentCategory').val(btn.data('parent'));
            $('#categoryModal').modal('show');
        });

        // Submit form
        $('#categoryForm').submit(function (e) {
            e.preventDefault();
            const id = $('#categoryId').val();
            const name = $('#categoryName').val();
            const desc = $('#categoryDescription').val();
            const parent = $('#parentCategory').val();

            // TODO: Gửi dữ liệu về server bằng AJAX hoặc submit tới controller
            console.log('Saving category:', { id, name, desc, parent });

            $('#categoryModal').modal('hide');
        });
    });
</script>
</body>
</html>



