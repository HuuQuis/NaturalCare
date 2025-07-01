package model;

public class SubProductCategory {
    private int id;
    private String name;
    private int productCategoryId;
    private String categoryName;
    private boolean status;

    public SubProductCategory() {
    }

    public SubProductCategory(int id, String name, int productCategoryId) {
        this.id = id;
        this.name = name;
        this.productCategoryId = productCategoryId;
    }

    public SubProductCategory(int id,  String name, int productCategoryId, String categoryName, boolean status) {
        this.id = id;
        this.name = name;
        this.productCategoryId = productCategoryId;
        this.categoryName = categoryName;
        this.status = status;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}