package model;

public abstract class Item {
    private int itemId;
    private String itemName;
    private String description;
    private double startingPrice;

    public Item(int itemId, String itemName, String description, double startingPrice) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.description = description;
        this.startingPrice = startingPrice;
    }

    public abstract String getCategory();

    public abstract String getShippingInfo();

    public abstract void displayDetails();

    public int getId() {
        return itemId;
    }

    public String getName() {
        return itemName;
    }

    public String getDescription() {
        return description;
    }

    public double getStartingPrice() {
        return startingPrice;
    }
}
