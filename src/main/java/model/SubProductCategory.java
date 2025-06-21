package model;

public class SubProductCategory {
    private int id;
    private String name;
    private int productCategoryId;
    private String categoryName;

    public SubProductCategory(int id, String name, int productCategoryId) {
        this.id = id;
        this.name = name;
        this.productCategoryId = productCategoryId;
    }

    public SubProductCategory() {
        this.id = id;
        this.name = name;
        this.productCategoryId = productCategoryId;
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getProductCategoryId() {
        return productCategoryId;
    }
    public void setProductCategoryId(int productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}