package model;

import java.util.List;

public class ProductCategory {
    private int id;
    private String name;
    private boolean status;
    private List<SubProductCategory> subList;

    // Constructors

    public ProductCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public ProductCategory(int id, String name, boolean status, List<SubProductCategory> subList) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.subList = subList;
    }

    public ProductCategory(int id, String name, boolean status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    // Getters and Setters
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

    public List<SubProductCategory> getSubList() {
        return subList;
    }

    public void setSubList(List<SubProductCategory> subList) {
        this.subList = subList;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
