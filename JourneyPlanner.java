import java.util.*;
import java.util.stream.Collectors;

public class JourneyPlanner {

    public static List<JourneyOption> planJourney(JourneyRequest request) {
        System.out.println("🔍 Planning your journey...");
        System.out.println("📋 " + request);
        System.out.println();

        // Get all available trains
        List<TrainInfo> allTrains = RealTimeDataManager.getAllTrains();

        // Filter trains that serve the route
        List<TrainInfo> routeTrains = filterTrainsForRoute(allTrains, request);

        if (routeTrains.isEmpty()) {
            System.out.println("❌ No direct trains found for this route.");
            return new ArrayList<>();
        }

        // Create journey options
        List<JourneyOption> options = new ArrayList<>();
        for (TrainInfo train : routeTrains) {
            JourneyOption option = new JourneyOption(train, request);
            options.add(option);
        }

        // Sort by match score (best matches first)
        options.sort((a, b) -> Double.compare(b.matchScore, a.matchScore));

        return options;
    }

    private static List<TrainInfo> filterTrainsForRoute(List<TrainInfo> trains, JourneyRequest request) {
        String from = request.fromStation.toLowerCase();
        String to = request.toStation.toLowerCase();

        return trains.stream()
                .filter(train -> {
                    String route = train.route.toLowerCase();
                    return route.contains(from) && route.contains(to);
                })
                .collect(Collectors.toList());
    }

    public static void displayJourneyOptions(List<JourneyOption> options, JourneyRequest request) {
        if (options.isEmpty()) {
            System.out.println("❌ No suitable journey options found.");
            System.out.println("💡 Try different stations or timing.");
            return;
        }

        System.out.println("🎯 ═══════════════════════════════════════════════════════════════════════════════");
        System.out.println("                        YOUR JOURNEY OPTIONS");
        System.out.println("                   " + request.fromStation + " → " + request.toStation);
        System.out.println("═══════════════════════════════════════════════════════════════════════════════");

        // Show top recommendations
        System.out.println("🏆 TOP RECOMMENDATIONS:");
        System.out.printf("%-4s %-15s %-15s %-15s %-4s %-10s %-8s %-20s %s\n",
                "#", "Train", "Departure", "Arrival", "Time", "Status", "Fare", "Match", "Notes");
        System.out.println("─".repeat(115));

        for (int i = 0; i < Math.min(options.size(), 5); i++) {
            JourneyOption option = options.get(i);
            System.out.printf("%-4d %-15s %-15s %-15s %2dh %-10s PKR %-6d %.0f%% %-20s\n",
                    (i + 1),
                    option.train.number,
                    option.getFormattedDeparture(),
                    option.getFormattedArrival(),
                    option.totalJourneyHours,
                    option.status,
                    option.fare,
                    option.matchScore,
                    option.getTimeDifferenceString());
        }

        System.out.println("─".repeat(115));

        // Show best option details
        if (!options.isEmpty()) {
            JourneyOption best = options.get(0);
            System.out.println("\n🎖️ RECOMMENDED CHOICE:");
            System.out.println("───────────────────────────────────────────────────────────────────────────");
            System.out.printf("🚂 Train: %s - %s\n", best.train.number, best.train.name);
            System.out.printf("🕐 Departure: %s\n", best.getFormattedDeparture());
            System.out.printf("🏁 Arrival: %s\n", best.getFormattedArrival());
            System.out.printf("⏱️ Journey Time: %d hours\n", best.totalJourneyHours);
            System.out.printf("💰 Fare: PKR %,d (%s class)\n", best.fare, best.travelClass);
            System.out.printf("🎯 Match Score: %.0f/100\n", best.matchScore);
            System.out.printf("💺 Available Seats: %d\n", best.availableSeats);
            System.out.printf("🎁 Amenities: %s\n", String.join(", ", best.amenities));
            System.out.println("───────────────────────────────────────────────────────────────────────────");
        }

        // Show priority-based analysis
        showPriorityAnalysis(options, request);
    }

    private static void showPriorityAnalysis(List<JourneyOption> options, JourneyRequest request) {
        System.out.println("\n📊 ANALYSIS BY YOUR PRIORITY (" + request.priority + "):");
        System.out.println("─".repeat(60));

        switch (request.priority.toUpperCase()) {
            case "TIME":
                JourneyOption fastest = options.stream()
                        .min(Comparator.comparingInt(o -> o.totalJourneyHours))
                        .orElse(null);
                if (fastest != null) {
                    System.out.printf("⚡ Fastest: %s (%d hours)\n",
                            fastest.train.name, fastest.totalJourneyHours);
                }
                break;

            case "COST":
                JourneyOption cheapest = options.stream()
                        .min(Comparator.comparingInt(o -> o.fare))
                        .orElse(null);
                if (cheapest != null) {
                    System.out.printf("💰 Cheapest: %s (PKR %,d)\n",
                            cheapest.train.name, cheapest.fare);
                }
                break;

            case "COMFORT":
                JourneyOption mostComfortable = options.stream()
                        .max(Comparator.comparingInt(o -> o.amenities.length))
                        .orElse(null);
                if (mostComfortable != null) {
                    System.out.printf("🛋️ Most Comfortable: %s (%d amenities)\n",
                            mostComfortable.train.name, mostComfortable.amenities.length);
                }
                break;
        }

        // Show timing analysis
        long perfectTimingCount = options.stream()
                .filter(o -> Math.abs(o.timeDifferenceFromPreferred) <= 30)
                .count();

        System.out.printf("🎯 Options close to your preferred time: %d/%d\n",
                perfectTimingCount, options.size());

        System.out.println("─".repeat(60));
    }

    public static void showQuickBookingPrompt(List<JourneyOption> options) {
        if (options.isEmpty()) return;

        System.out.println("\n💳 QUICK ACTIONS:");
        System.out.println("─".repeat(40));
        System.out.println("1️⃣ Book recommended option");
        System.out.println("2️⃣ Compare all options");
        System.out.println("3️⃣ Check live status");
        System.out.println("4️⃣ Modify search criteria");
        System.out.println("5️⃣ Return to main menu");
        System.out.println("─".repeat(40));
    }
}