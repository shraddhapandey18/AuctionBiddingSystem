package model;

/**
 * Represents a digital item that can be delivered electronically.
 * Extends the abstract Item class with digital delivery properties.
 */
public class DigitalItem extends Item {
    private String fileFormat; // e.g., "PDF", "MP3", "ZIP"
    private long fileSize; // in bytes
    private String downloadLink;

    /**
     * Constructor for DigitalItem
     * @param itemId Unique identifier
     * @param name Item name
     * @param description Item description
     * @param startingPrice Starting auction price
     * @param sellerId Seller identifier
     * @param fileFormat File format (e.g., "PDF", "MP3")
     * @param fileSize File size in bytes
     * @param downloadLink Download URL or link
     */
    public DigitalItem(String itemId, String name, String description, double startingPrice,
                      String sellerId, String fileFormat, long fileSize, String downloadLink) {
        super(itemId, name, description, startingPrice, sellerId);
        this.fileFormat = fileFormat;
        this.fileSize = fileSize;
        this.downloadLink = downloadLink;
    }

    // Getters
    public String getFileFormat() { return fileFormat; }
    public long getFileSize() { return fileSize; }
    public String getDownloadLink() { return downloadLink; }

    // Setters
    public void setFileFormat(String fileFormat) { this.fileFormat = fileFormat; }
    public void setFileSize(long fileSize) { this.fileSize = fileSize; }
    public void setDownloadLink(String downloadLink) { this.downloadLink = downloadLink; }

    @Override
    public String getCategory() {
        return "Digital";
    }

    @Override
    public String getShippingInfo() {
        return String.format("Digital delivery - Format: %s, Size: %d bytes, Link: %s",
                           fileFormat, fileSize, downloadLink);
    }

    @Override
    public String toString() {
        return String.format("DigitalItem{%s, format='%s', size=%d bytes}",
                           super.toString(), fileFormat, fileSize);
    }
}