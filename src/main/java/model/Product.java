package model;

public class Product {
    private int id;
    private String name;
    private String description;
    private String information;
    private String guideline;
    private int subProductCategoryId;


    public Product() {
    }
    public Product(int id, String name, String description, String information, String guideline, int subProductCategoryId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.information = information;
        this.guideline = guideline;
        this.subProductCategoryId = subProductCategoryId;
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
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getInformation() {
        return information;
    }
    public void setInformation(String information) {
        this.information = information;
    }
    public String getGuideline() {
        return guideline;
    }
    public void setGuideline(String guideline) {
        this.guideline = guideline;
    }
    public int getSubProductCategoryId() {
        return subProductCategoryId;
    }
    public void setSubProductCategoryId(int subProductCategoryId) {
        this.subProductCategoryId = subProductCategoryId;
    }
}
