package model;

/**
 * Represents a physical item that requires shipping.
 * Extends the abstract Item class with shipping-specific properties.
 */
public class PhysicalItem extends Item {
    private double weight; // in kg
    private String dimensions; // e.g., "10x20x30 cm"
    private String shippingAddress;

    /**
     * Constructor for PhysicalItem
     * @param itemId Unique identifier
     * @param name Item name
     * @param description Item description
     * @param startingPrice Starting auction price
     * @param sellerId Seller identifier
     * @param weight Weight in kilograms
     * @param dimensions Dimensions as string (e.g., "10x20x30 cm")
     * @param shippingAddress Shipping destination
     */
    public PhysicalItem(String itemId, String name, String description, double startingPrice,
                       String sellerId, double weight, String dimensions, String shippingAddress) {
        super(itemId, name, description, startingPrice, sellerId);
        this.weight = weight;
        this.dimensions = dimensions;
        this.shippingAddress = shippingAddress;
    }

    // Getters
    public double getWeight() { return weight; }
    public String getDimensions() { return dimensions; }
    public String getShippingAddress() { return shippingAddress; }

    // Setters
    public void setWeight(double weight) { this.weight = weight; }
    public void setDimensions(String dimensions) { this.dimensions = dimensions; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }

    @Override
    public String getCategory() {
        return "Physical";
    }

    @Override
    public String getShippingInfo() {
        return String.format("Weight: %.2f kg, Dimensions: %s, Address: %s",
                           weight, dimensions, shippingAddress);
    }

    @Override
    public String toString() {
        return String.format("PhysicalItem{%s, weight=%.2f kg, dimensions='%s'}",
                           super.toString(), weight, dimensions);
    }
}