package controller;

import dal.BlogCategoryDAO;
import dal.ProductCategoryDAO;
import dal.ProductDAO;
import dal.SubProductCategoryDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.BlogCategory;
import model.Product;
import model.ProductCategory;
import model.SubProductCategory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ProductServlet", value = "/products")
public class ProductServlet extends HttpServlet {
    private ProductDAO productDAO;
    private ProductCategoryDAO categoryDAO;
    private BlogCategoryDAO blogCategoryDAO;
    private SubProductCategoryDAO subProductCategoryDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
        categoryDAO = new ProductCategoryDAO();
        blogCategoryDAO = new BlogCategoryDAO();
        subProductCategoryDAO = new SubProductCategoryDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int pageSize = 6;
        String indexPage = request.getParameter("index");
        int index = (indexPage == null || indexPage.isEmpty()) ? 1 : Integer.parseInt(indexPage);

        String categoryId = request.getParameter("category");
        String subCategoryId = request.getParameter("subcategory");
        String sort = request.getParameter("sort");

        String minPriceStr = request.getParameter("minPrice");
        String maxPriceStr = request.getParameter("maxPrice");
        Double minPrice = (minPriceStr != null && !minPriceStr.isEmpty()) ? Double.parseDouble(minPriceStr) : null;
        Double maxPrice = (maxPriceStr != null && !maxPriceStr.isEmpty()) ? Double.parseDouble(maxPriceStr) : null;

        String[] colorFilter = request.getParameterValues("color");
        String[] sizeFilter = request.getParameterValues("size");
        List<Integer> colorIds = convertToIntList(colorFilter);
        List<Integer> sizeIds = convertToIntList(sizeFilter);

        request.setAttribute("minPrice", minPriceStr);
        request.setAttribute("maxPrice", maxPriceStr);
        request.setAttribute("selectedColors", colorIds);
        request.setAttribute("selectedSizes", sizeIds);

        boolean hasCategory = categoryId != null && !categoryId.isEmpty();
        boolean hasSubCategory = subCategoryId != null && !subCategoryId.isEmpty();

        boolean filteringVariation = isFilteringByVariation(minPrice, maxPrice, colorIds, sizeIds);
        List<Product> products;

        if (hasSubCategory) {
            int subId = Integer.parseInt(subCategoryId);
            if (filteringVariation) {
                products = productDAO.getProductsWithFilters(index, pageSize, sort, minPrice, maxPrice, null, subId, colorIds, sizeIds);
            } else {
                products = productDAO.getAllProducts(index, pageSize, sort, null, subId);
            }
            request.setAttribute("selectedSubCategoryId", subCategoryId);

            SubProductCategory sub = subProductCategoryDAO.getSubCategoryById(subId);
            if (sub != null) {
                request.setAttribute("selectedCategoryId", sub.getProductCategoryId());
            }

        } else if (hasCategory) {
            int catId = Integer.parseInt(categoryId);
            if (filteringVariation) {
                products = productDAO.getProductsWithFilters(index, pageSize, sort, minPrice, maxPrice, catId, null, colorIds, sizeIds);
            } else {
                products = productDAO.getAllProducts(index, pageSize, sort, catId, null);
            }
            request.setAttribute("selectedCategoryId", categoryId);

        } else {
            if (filteringVariation) {
                products = productDAO.getProductsWithFilters(index, pageSize, sort, minPrice, maxPrice, null, null, colorIds, sizeIds);
            } else {
                products = productDAO.getAllProducts(index, pageSize, sort, null, null);
            }
        }

        request.setAttribute("products", products);

        List<ProductCategory> categories = categoryDAO.getAllProductCategories();
        List<BlogCategory> blogCategories = blogCategoryDAO.getAllBlogCategories();
        List<SubProductCategory> subCategories = new ArrayList<>();
        for (ProductCategory category : categories) {
            subCategories.addAll(subProductCategoryDAO.getSubCategoriesByCategoryId(category.getId()));
        }
        Integer catId = (categoryId != null && !categoryId.isEmpty()) ? Integer.parseInt(categoryId) : null;
        Integer subCatId = (subCategoryId != null && !subCategoryId.isEmpty()) ? Integer.parseInt(subCategoryId) : null;

        int totalCount = filteringVariation
                ? productDAO.countProductsWithFilters(minPrice, maxPrice, categoryId, subCategoryId, colorIds, sizeIds)
                : productDAO.countAllProducts(catId, subCatId);
        int endPage = totalCount / pageSize + (totalCount % pageSize == 0 ? 0 : 1);

        System.out.println("Total products: " + totalCount);
        request.setAttribute("endPage", endPage);
        request.setAttribute("categories", categories);
        request.setAttribute("blogCategories", blogCategories);
        request.setAttribute("subCategories", subCategories);
        request.setAttribute("sort", sort);

        request.getRequestDispatcher("/view/product/product.jsp").forward(request, response);
    }

    private List<Integer> convertToIntList(String[] arr) {
        List<Integer> list = new ArrayList<>();
        if (arr != null) {
            for (String val : arr) {
                try {
                    list.add(Integer.parseInt(val));
                } catch (NumberFormatException ignored) {}
            }
        }
        return list;
    }

    private boolean isFilteringByVariation(Double minPrice, Double maxPrice,
                                           List<Integer> colorIds, List<Integer> sizeIds) {
        return (minPrice != null || maxPrice != null) ||
                (colorIds != null && !colorIds.isEmpty()) ||
                (sizeIds != null && !sizeIds.isEmpty());
    }
}
