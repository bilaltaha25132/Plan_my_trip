import java.util.*;

public class RealTimeDataManager {
    private static List<TrainInfo> cachedTrains = null;
    private static Map<String, LiveTrainStatus> statusCache = new HashMap<>();
    private static long lastCacheUpdate = 0;
    private static final long CACHE_DURATION = 300000; // 5 minutes

    // Get all trains with real-time data
    public static List<TrainInfo> getAllTrains() {
        if (cachedTrains == null || isCacheExpired()) {
            System.out.println("🔄 Refreshing train database...");
            cachedTrains = WebScraper.scrapeTrainList();
            lastCacheUpdate = System.currentTimeMillis();
        }
        return cachedTrains;
    }

    // Get live status for a specific train
    public static LiveTrainStatus getLiveStatus(String searchTerm) {
        // Check cache first
        String cacheKey = searchTerm.toLowerCase();
        if (statusCache.containsKey(cacheKey) && !isStatusCacheExpired(cacheKey)) {
            return statusCache.get(cacheKey);
        }

        // Find train by number or name
        List<TrainInfo> trains = getAllTrains();
        TrainInfo foundTrain = null;

        for (TrainInfo train : trains) {
            if (train.number.toLowerCase().contains(searchTerm.toLowerCase()) ||
                    train.name.toLowerCase().contains(searchTerm.toLowerCase())) {
                foundTrain = train;
                break;
            }
        }

        if (foundTrain == null) {
            return null;
        }

        // Scrape live status
        LiveTrainStatus status = WebScraper.scrapeLiveStatus(foundTrain.number, foundTrain.name);

        // Cache the result
        statusCache.put(cacheKey, status);

        return status;
    }

    // Get trains between two stations
    public static List<TrainInfo> getTrainsBetweenStations(String from, String to) {
        List<TrainInfo> allTrains = getAllTrains();
        List<TrainInfo> matchingTrains = new ArrayList<>();

        for (TrainInfo train : allTrains) {
            // Simple route matching (can be enhanced)
            String route = train.route.toLowerCase();
            if (route.contains(from.toLowerCase()) && route.contains(to.toLowerCase())) {
                matchingTrains.add(train);
            }
        }

        return matchingTrains;
    }

    private static boolean isCacheExpired() {
        return System.currentTimeMillis() - lastCacheUpdate > CACHE_DURATION;
    }

    private static boolean isStatusCacheExpired(String key) {
        LiveTrainStatus status = statusCache.get(key);
        return System.currentTimeMillis() - status.lastUpdated.getTime() > 60000; // 1 minute
    }

    // Display all available trains
    public static void displayAllTrains() {
        List<TrainInfo> trains = getAllTrains();

        System.out.println("\n🚂 ═══════════════════════════════════════════════════════════════════════════");
        System.out.println("                           PAKISTAN RAILWAYS - LIVE TRAIN DATABASE");
        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.printf("%-12s %-25s %-30s %s\n", "Train #", "Train Name", "Route", "Status");
        System.out.println("───────────────────────────────────────────────────────────────────────────");

        for (TrainInfo train : trains) {
            System.out.println(train);
        }

        System.out.println("═══════════════════════════════════════════════════════════════════════════");
        System.out.println("Total trains: " + trains.size() + " | Data updated: " + new Date(lastCacheUpdate));
        System.out.println();
    }
}