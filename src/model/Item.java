package model;

/**
 * Abstract base class representing an item in the auction system.
 * This class defines the common properties and behaviors for all auction items.
 */
public abstract class Item {
    private String itemId;
    private String name;
    private String description;
    private double startingPrice;
    private String sellerId;

    /**
     * Constructor for Item
     * @param itemId Unique identifier for the item
     * @param name Name of the item
     * @param description Description of the item
     * @param startingPrice Starting price for auction
     * @param sellerId ID of the seller
     */
    public Item(String itemId, String name, String description, double startingPrice, String sellerId) {
        this.itemId = itemId;
        this.name = name;
        this.description = description;
        this.startingPrice = startingPrice;
        this.sellerId = sellerId;
    }

    // Getters
    public String getItemId() { return itemId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getStartingPrice() { return startingPrice; }
    public String getSellerId() { return sellerId; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setStartingPrice(double startingPrice) { this.startingPrice = startingPrice; }

    /**
     * Abstract method to get the category of the item
     * @return Category string
     */
    public abstract String getCategory();

    /**
     * Abstract method to get shipping information
     * @return Shipping info string
     */
    public abstract String getShippingInfo();

    @Override
    public String toString() {
        return String.format("Item{id='%s', name='%s', startingPrice=%.2f, seller='%s'}",
                           itemId, name, startingPrice, sellerId);
    }
}