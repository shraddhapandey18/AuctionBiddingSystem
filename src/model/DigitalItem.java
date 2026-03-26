package model;

public class DigitalItem extends Item {
    private String fileFormat;
    private String downloadLink;

    public DigitalItem(int itemId, String itemName, String description, double startingPrice,
                       String fileFormat, String downloadLink) {
        super(itemId, itemName, description, startingPrice);
        this.fileFormat = fileFormat;
        this.downloadLink = downloadLink;
    }

    @Override
    public String getCategory() {
        return "Digital Goods";
    }

    @Override
    public String getShippingInfo() {
        return "Instant digital delivery via: " + downloadLink;
    }

    @Override
    public void displayDetails() {
        System.out.println("\n=== ITEM DETAILS ===");
        System.out.println("ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Description: " + getDescription());
        System.out.println("Starting Price: $" + String.format("%.2f", getStartingPrice()));
        System.out.println("Category: " + getCategory());
        System.out.println("File Format: " + fileFormat);
        System.out.println("Download: " + downloadLink);
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public String getDownloadLink() {
        return downloadLink;
    }
}
