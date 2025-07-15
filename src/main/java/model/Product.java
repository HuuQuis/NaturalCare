package model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Product {
    private int id;
    private String name;
    private String description;
    private String information;
    private String guideline;
    private String imageUrl;
    private int subProductCategoryId;
    private int minPrice;
    private int totalSold;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private boolean isActive = true;

    private List<String> imageUrls;
    private List<ProductVariation> variations;

    public Product() {
        this.imageUrls = new ArrayList<>();
        this.variations = new ArrayList<>();
    }

    public Product(int id, String name, String description, String information, String guideline, int subProductCategoryId) {
        this();
        this.id = id;
        this.name = name;
        this.description = description;
        this.information = information;
        this.guideline = guideline;
        this.subProductCategoryId = subProductCategoryId;
    }

    public Product(int id, String name, String description, String information, String guideline, String imageUrl, int subProductCategoryId) {
        this();
        this.id = id;
        this.name = name;
        this.description = description;
        this.information = information;
        this.guideline = guideline;
        this.imageUrl = imageUrl; // Fixed: was ImageUrl parameter but imageUrl field
        this.subProductCategoryId = subProductCategoryId;
    }

    public Product(int id, String name, String description, String information, String guideline, String imageUrl, int subProductCategoryId, int minPrice) {
        this();
        this.id = id;
        this.name = name;
        this.description = description;
        this.information = information;
        this.guideline = guideline;
        this.imageUrl = imageUrl;
        this.subProductCategoryId = subProductCategoryId;
        this.minPrice = minPrice;
    }

    // All getters and setters
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public int getTotalSold() {
        return totalSold;
    }

    public void setTotalSold(int totalSold) {
        this.totalSold = totalSold;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }


    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void addImageUrl(String imageUrl) {
        if (imageUrl != null && !imageUrls.contains(imageUrl)) {
            this.imageUrls.add(imageUrl);
        }
    }

    public List<ProductVariation> getVariations() {
        return variations;
    }

    public void setVariations(List<ProductVariation> variations) {
        this.variations = variations;
    }

    public void addVariation(ProductVariation variation) {
        if (this.variations == null) {
            this.variations = new ArrayList<>();
        }
        this.variations.add(variation);
    }

    public String getCreatedAtFormatted() {
        if (createdAt == null) return "";
        return new SimpleDateFormat("HH.mm dd/MM/yyyy").format(createdAt);
    }

    public String getUpdatedAtFormatted() {
        if (updatedAt == null) return "";
        return new SimpleDateFormat("HH.mm dd/MM/yyyy").format(updatedAt);
    }

}