package model;

public class PhysicalItem extends Item {
    private double weightKg;
    private String shippingAddress;

    public PhysicalItem(int itemId, String itemName, String description, double startingPrice,
                        double weightKg, String shippingAddress) {
        super(itemId, itemName, description, startingPrice);
        this.weightKg = weightKg;
        this.shippingAddress = shippingAddress;
    }

    @Override
    public String getCategory() {
        return "Physical Goods";
    }

    @Override
    public String getShippingInfo() {
        return "Weight: " + String.format("%.2f", weightKg) + " kg | Shipping Address: " + shippingAddress;
    }

    @Override
    public void displayDetails() {
        System.out.println("\n=== ITEM DETAILS ===");
        System.out.println("ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Description: " + getDescription());
        System.out.println("Starting Price: $" + String.format("%.2f", getStartingPrice()));
        System.out.println("Category: " + getCategory());
        System.out.println("Shipping Info: " + getShippingInfo());
    }

    public double getWeightKg() {
        return weightKg;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }
}
