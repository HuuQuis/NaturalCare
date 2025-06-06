package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class ProductCategory {
    private int id;
    private String name;
    private List<SubProductCategory> subList;

    // Constructors
    public ProductCategory() {
    }

    public ProductCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public ProductCategory(int id, String name, List<SubProductCategory> subList) {
        this.id = id;
        this.name = name;
        this.subList = subList;
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
}
