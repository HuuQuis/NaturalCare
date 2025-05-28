package model;

public class subProductCategory {
    private int id;
    private String name;
    private int productCategoryId;

    public subProductCategory(int id, String name, int productCategoryId) {
        this.id = id;
        this.name = name;
        this.productCategoryId = productCategoryId;
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
}
