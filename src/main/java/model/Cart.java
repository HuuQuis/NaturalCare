package model;

public class Cart {
    private ProductVariation variation;
    private int quantity;

    public Cart(ProductVariation variation, int quantity) {
        this.variation = variation;
        this.quantity = quantity;
    }

    public ProductVariation getVariation() {
        return variation;
    }

    public void setVariation(ProductVariation variation) {
        this.variation = variation;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}