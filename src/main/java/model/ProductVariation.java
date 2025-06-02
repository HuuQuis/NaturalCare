package model;

public class ProductVariation {
    private int variationId;
    private String imageUrl;
    private String color;
    private String size;
    private int price;
    private int qtyInStock;
    private int sold;

    public ProductVariation(int variationId, String imageUrl, String color, String size, int price, int qtyInStock, int sold) {
        this.variationId = variationId;
        this.imageUrl = imageUrl;
        this.color = color;
        this.size = size;
        this.price = price;
        this.qtyInStock = qtyInStock;
        this.sold = sold;
    }

    public ProductVariation(String imageUrl, String color, String size, int price, int qtyInStock, int sold) {
        this.imageUrl = imageUrl;
        this.color = color;
        this.size = size;
        this.price = price;
        this.qtyInStock = qtyInStock;
        this.sold = sold;
    }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
    public int getQtyInStock() { return qtyInStock; }
    public void setQtyInStock(int qtyInStock) { this.qtyInStock = qtyInStock; }
    public int getSold() { return sold; }
    public void setSold(int sold) { this.sold = sold; }
}
