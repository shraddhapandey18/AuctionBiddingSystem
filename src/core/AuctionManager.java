package core;

import auction.Auction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuctionManager {
    private Map<Integer, Auction> activeAuctions;

    public AuctionManager() {
        this.activeAuctions = new HashMap<>();
    }

    public void registerAuction(Auction a) {
        activeAuctions.put(a.getAuctionId(), a);
        System.out.println("✓ Auction #" + a.getAuctionId() + " registered (" + a.getAuctionType() + ")");
    }

    public void removeAuction(int id) {
        activeAuctions.remove(id);
        System.out.println("✓ Auction #" + id + " removed");
    }

    public Auction getAuction(int id) {
        Auction a = activeAuctions.get(id);
        if (a == null) {
            throw new IllegalArgumentException("Auction #" + id + " not found");
        }
        return a;
    }

    public void listAllAuctions() {
        if (activeAuctions.isEmpty()) {
            System.out.println("\n(No auctions registered)");
            return;
        }
        System.out.println("\n=== ALL AUCTIONS ===");
        for (Auction a : activeAuctions.values()) {
            System.out.println("Auction #" + a.getAuctionId() + " - " + a.getItem().getName() +
                    " [" + (a.isOpen() ? "OPEN" : "CLOSED") + "] " + a.getAuctionType());
        }
    }

    public List<Auction> getOpenAuctions() {
        List<Auction> openList = new ArrayList<>();
        for (Auction a : activeAuctions.values()) {
            if (a.isOpen()) {
                openList.add(a);
            }
        }
        return openList;
    }

    public void closeAllAuctions() {
        for (Auction a : activeAuctions.values()) {
            if (a.isOpen()) {
                try {
                    a.closeAuction();
                } catch (Exception e) {
                }
            }
        }
    }

    public Map<Integer, Auction> getAllAuctions() {
        return new HashMap<>(activeAuctions);
    }
}
