import java.util.ArrayList;
import java.util.List;

public class AuctionLogger implements AuctionObserver {
    private List<String> log = new ArrayList<>();

    @Override
    public void update(String message) {
        log.add(message);
        System.out.println("Log: " + message);
    }
}