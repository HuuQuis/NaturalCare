package model;

public class ProductVariation {
    private int variationId;
    private String imageUrl;
    private int colorId;
    private int sizeId;
    private int price;
    private int qtyInStock;
    private int sold;
    private int productId;
    private String colorName;
    private String sizeName;

    public ProductVariation() {
    }

    public ProductVariation(int variationId, int productId, String imageUrl, int colorId, int sizeId, int price, int qtyInStock) {
        this.variationId = variationId;
        this.productId = productId;
        this.imageUrl = imageUrl;
        this.colorId = colorId;
        this.sizeId = sizeId;
        this.price = price;
        this.qtyInStock = qtyInStock;
    }

    public ProductVariation(int variationId, String imageUrl, int colorId, int sizeId, int price, int qtyInStock, int sold) {
        this.variationId = variationId;
        this.imageUrl = imageUrl;
        this.colorId = colorId;
        this.sizeId = sizeId;
        this.price = price;
        this.qtyInStock = qtyInStock;
        this.sold = sold;
    }

    public ProductVariation(int variationId, int productId, String imageUrl, int colorId, int sizeId, int price, int qtyInStock, int sold) {
        this.variationId = variationId;
        this.productId = productId;
        this.imageUrl = imageUrl;
        this.colorId = colorId;
        this.sizeId = sizeId;
        this.price = price;
        this.qtyInStock = qtyInStock;
        this.sold = sold;
    }

    public ProductVariation(String imageUrl, int colorId, int sizeId, int price, int qtyInStock, int sold) {
        this.imageUrl = imageUrl;
        this.colorId = colorId;
        this.sizeId = sizeId;
        this.price = price;
        this.qtyInStock = qtyInStock;
        this.sold = sold;
    }

    public int getVariationId() { return variationId; }
    public void setVariationId(int variationId) { this.variationId = variationId; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public int getColorId() { return colorId; }
    public void setColorId(int colorId) { this.colorId = colorId; }
    public int getSizeId() { return sizeId; }
    public void setSizeId(int sizeId) { this.sizeId = sizeId; }
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
    public int getQtyInStock() { return qtyInStock; }
    public void setQtyInStock(int qtyInStock) { this.qtyInStock = qtyInStock; }
    public int getSold() { return sold; }
    public void setSold(int sold) { this.sold = sold; }
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }
    public String getColorName() {
        return colorName;
    }
    public void setColorName(String colorName) {
        this.colorName = colorName;
    }
    public String getSizeName() {
        return sizeName;
    }
    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }
}
