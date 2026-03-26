package auction;

import exceptions.AuctionClosedException;
import model.Bid;
import model.Item;

public class LiveAuction extends Auction implements Runnable {
    private int durationSeconds;
    private int timeRemaining;
    private Thread timerThread;

    public LiveAuction(int auctionId, Item item, int durationSeconds) {
        super(auctionId, item);
        this.durationSeconds = durationSeconds;
        this.timeRemaining = durationSeconds;
    }

    @Override
    public void startAuction() {
        auctionOpen = true;
        setCurrentPrice(getItem().getStartingPrice());
        System.out.println("\n🔔 LIVE AUCTION #" + getAuctionId() + " STARTED!");
        System.out.println("Item: " + getItem().getName());
        System.out.println("Duration: " + durationSeconds + " seconds");
        timerThread = new Thread(this, "AuctionTimer-" + getAuctionId());
        timerThread.start();
    }

    @Override
    public void run() {
        while (timeRemaining > 0 && auctionOpen) {
            try {
                Thread.sleep(1000);
                timeRemaining--;

                if (timeRemaining % 10 == 0 || timeRemaining <= 5) {
                    System.out.println("⏱ Time remaining: " + timeRemaining + "s");
                }

            } catch (InterruptedException e) {
                break;
            }
        }

        if (auctionOpen) {
            closeAuction();
        }
    }

    @Override
    public void closeAuction() {
        auctionOpen = false;
        if (timerThread != null && timerThread.isAlive()) {
            timerThread.interrupt();
        }

        System.out.println("\n🛑 AUCTION #" + getAuctionId() + " CLOSED!");

        Bid winner = getWinner();
        if (winner != null) {
            System.out.println("✅ WINNER: " + winner.getBidderName() + " with $" +
                    String.format("%.2f", winner.getAmount()));
        } else {
            System.out.println("❌ No bids placed.");
        }

        notifyObservers("AUCTION_ENDED");
    }

    @Override
    public boolean isValidBid(double amount) {
        return amount > getCurrentPrice() && auctionOpen;
    }

    @Override
    public String getAuctionType() {
        return "Live Timed Auction";
    }

    @Override
    public Bid submitBid(model.Bidder bidder, double amount) throws exceptions.InvalidBidException {
        if (!auctionOpen) {
            throw new AuctionClosedException(getAuctionId());
        }
        return super.submitBid(bidder, amount);
    }

    public int getTimeRemaining() {
        return timeRemaining;
    }
}
