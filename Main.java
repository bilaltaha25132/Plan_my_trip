//import java.util.Scanner;
//import java.util.List;
//
//public class Main {
//    public static void main(String[] args){
//        System.out.println("🧳 ══════════════════════════════════════════════════════════════");
//        System.out.println("          PAKISTAN RAILWAYS - SMART JOURNEY PLANNER");
//        System.out.println("              Plan Your Perfect Train Journey!");
//        System.out.println("══════════════════════════════════════════════════════════════");
//
//        Graph graph = Graph.createGraph();
//        Scanner sc = new Scanner(System.in);
//
//        // Welcome and quick start
//        System.out.println("🌟 Welcome! Let's plan your train journey across Pakistan.");
//        System.out.println("💡 This app finds the best trains based on YOUR preferences!\n");
//
//        showMainMenu(graph, sc);
//    }
//
//    private static void showMainMenu(Graph graph, Scanner sc) {
//        while (true) {
//            String menu = "\n🧳 ═══════════════════════════════════════════════════════════════\n" +
//                    "                      JOURNEY PLANNER MENU\n" +
//                    "═══════════════════════════════════════════════════════════════\n" +
//                    "🎯 SMART JOURNEY PLANNING:\n" +
//                    "1️⃣  Plan My Journey (Smart Recommendations)\n" +
//                    "2️⃣  Quick Journey Search\n" +
//                    "3️⃣  Find Trains by Departure Time\n" +
//                    "4️⃣  Compare Journey Options\n" +
//                    "───────────────────────────────────────────────────────────────\n" +
//                    "📊 TRAIN INFORMATION:\n" +
//                    "5️⃣  Live Train Status\n" +
//                    "6️⃣  Station Departures Board\n" +
//                    "7️⃣  Browse All Available Trains\n" +
//                    "───────────────────────────────────────────────────────────────\n" +
//                    "🗺️ CLASSIC ROUTE PLANNING:\n" +
//                    "8️⃣  Shortest Distance Route\n" +
//                    "9️⃣  Fastest Time Route\n" +
//                    "🔟  Cheapest Cost Route\n" +
//                    "───────────────────────────────────────────────────────────────\n" +
//                    "0️⃣  Exit\n" +
//                    "═══════════════════════════════════════════════════════════════\n" +
//                    "Enter your choice: ";
//
//            System.out.print(menu);
//            int choice = getIntInput(sc);
//
//            switch(choice) {
//                case 1:
//                    planSmartJourney(sc);
//                    break;
//                case 2:
//                    quickJourneySearch(sc);
//                    break;
//                case 3:
//                    findTrainsByTime(sc);
//                    break;
//                case 4:
//                    compareJourneyOptions(sc);
//                    break;
//                case 5:
//                    getLiveTrainStatus(sc);
//                    break;
//                case 6:
//                    getStationDepartures(sc, graph);
//                    break;
//                case 7:
//                    graph.displayRealTimeTrains();
//                    break;
//                case 8:
//                    findShortestRoute(sc, graph);
//                    break;
//                case 9:
//                    findFastestRoute(sc, graph);
//                    break;
//                case 10:
//                    findCheapestRoute(sc, graph);
//                    break;
//                case 0:
//                    System.out.println("\n🧳 ══════════════════════════════════════════════════════════════");
//                    System.out.println("    Thank you for using Pakistan Railways Journey Planner!");
//                    System.out.println("                     Have a safe journey! 🚂");
//                    System.out.println("══════════════════════════════════════════════════════════════");
//                    return;
//                default:
//                    System.out.println("❌ Invalid choice! Please select a valid option.");
//            }
//        }
//    }
//
//    // NEW: Smart Journey Planning
//    private static void planSmartJourney(Scanner sc) {
//        System.out.println("\n🎯 ═══════════════════════════════════════════════════════════════");
//        System.out.println("                        SMART JOURNEY PLANNER");
//        System.out.println("═══════════════════════════════════════════════════════════════");
//
//        // Get journey details
//        System.out.print("📍 From (departure station): ");
//        String from = sc.next();
//
//        System.out.print("📍 To (destination station): ");
//        String to = sc.next();
//
//        System.out.print("🕐 Preferred departure time (e.g., 15:30 or 3:30 PM): ");
//        String departureTime = sc.next();
//
//        System.out.println("\n🎯 What's most important to you?");
//        System.out.println("1️⃣ TIME (fastest journey)");
//        System.out.println("2️⃣ COST (cheapest fare)");
//        System.out.println("3️⃣ COMFORT (best amenities)");
//        System.out.println("4️⃣ FLEXIBLE (balanced options)");
//        System.out.print("Choose priority (1-4): ");
//
//        String[] priorities = {"", "TIME", "COST", "COMFORT", "FLEXIBLE"};
//        int priorityChoice = getIntInput(sc);
//        String priority = (priorityChoice >= 1 && priorityChoice <= 4) ?
//                priorities[priorityChoice] : "FLEXIBLE";
//
//        // Create journey request
//        JourneyRequest request = new JourneyRequest(from, to, departureTime, priority);
//
//        // Plan journey
//        List<JourneyOption> options = JourneyPlanner.planJourney(request);
//
//        // Display options
//        JourneyPlanner.displayJourneyOptions(options, request);
//
//        // Show quick actions
//        JourneyPlanner.showQuickBookingPrompt(options);
//
//        System.out.println("\n💡 Tip: Use option 5 to check live status of any recommended train!");
//    }
//
//    // NEW: Quick Journey Search
//    private static void quickJourneySearch(Scanner sc) {
//        System.out.println("\n⚡ QUICK JOURNEY SEARCH");
//        System.out.println("─".repeat(40));
//
//        System.out.print("From: ");
//        String from = sc.next();
//        System.out.print("To: ");
//        String to = sc.next();
//
//        // Use flexible timing with current time + 2 hours
//        JourneyRequest quickRequest = new JourneyRequest(from, to, "14:00", "FLEXIBLE");
//        quickRequest.flexibleTiming = true;
//
//        List<JourneyOption> options = JourneyPlanner.planJourney(quickRequest);
//
//        if (!options.isEmpty()) {
//            System.out.println("\n⚡ QUICK RESULTS:");
//            System.out.println("─".repeat(80));
//            System.out.printf("%-15s %-15s %-15s %-6s %-12s\n",
//                    "Train", "Departure", "Arrival", "Time", "Fare");
//            System.out.println("─".repeat(80));
//
//            for (int i = 0; i < Math.min(3, options.size()); i++) {
//                JourneyOption option = options.get(i);
//                System.out.printf("%-15s %-15s %-15s %2dh %-12s\n",
//                        option.train.number,
//                        option.getFormattedDeparture(),
//                        option.getFormattedArrival(),
//                        option.totalJourneyHours,
//                        "PKR " + option.fare);
//            }
//            System.out.println("─".repeat(80));
//        }
//    }
//
//    // Utility method for safe integer input
//    private static int getIntInput(Scanner sc) {
//        try {
//            return sc.nextInt();
//        } catch (Exception e) {
//            sc.nextLine(); // Clear invalid input
//            return -1;
//        }
//    }
//
//    // Existing methods (shortened for space)
//    private static void findTrainsByTime(Scanner sc) {
//        System.out.println("🕐 Find trains departing around specific time");
//        System.out.print("Enter departure time (e.g., 18:00): ");
//        String time = sc.next();
//        System.out.println("⏰ Searching trains departing around " + time + "...");
//        // Implementation here
//    }
//
//    private static void compareJourneyOptions(Scanner sc) {
//        System.out.println("📊 Compare multiple journey options side by side");
//        // Implementation here
//    }
//
//    private static void getLiveTrainStatus(Scanner sc) {
//        System.out.print("🔍 Enter train number or name: ");
//        String trainId = sc.next();
//        LiveTrainStatus status = RealTimeDataManager.getLiveStatus(trainId);
//        if (status != null) {
//            System.out.println(status);
//        } else {
//            System.out.println("❌ Train not found.");
//        }
//    }
//
//    private static void getStationDepartures(Scanner sc, Graph graph) {
//        System.out.print("🚉 Enter station name: ");
//        String station = sc.next();
//        graph.getLiveDepartures(station);
//    }
//
//    private static void findShortestRoute(Scanner sc, Graph graph) {
//        System.out.print("From: ");
//        String from = sc.next();
//        System.out.print("To: ");
//        String to = sc.next();
//        graph.shortestDistance(from, to);
//    }
//
//    private static void findFastestRoute(Scanner sc, Graph graph) {
//        System.out.print("From: ");
//        String from = sc.next();
//        System.out.print("To: ");
//        String to = sc.next();
//        graph.shortestTime(from, to);
//    }
//
//    private static void findCheapestRoute(Scanner sc, Graph graph) {
//        System.out.print("From: ");
//        String from = sc.next();
//        System.out.print("To: ");
//        String to = sc.next();
//        graph.shortestCost(from, to);
//    }
//}

import java.util.Scanner;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("🧳 ══════════════════════════════════════════════════════════════");
        System.out.println("          PAKISTAN RAILWAYS - SMART JOURNEY PLANNER");
        System.out.println("              Plan Your Perfect Train Journey!");
        System.out.println("              🌐 Powered by Real-Time Data");
        System.out.println("══════════════════════════════════════════════════════════════");


        Scanner sc = new Scanner(System.in);

// Welcome and quick start
        System.out.println("🌟 Welcome! Let's plan your train journey across Pakistan.");
        System.out.println("💡 This app finds the best trains based on YOUR preferences!");
        System.out.println("📡 All data is scraped live from Pakistani railway websites.\n");

        showMainMenu(sc);
    }

    private static void showMainMenu(Scanner sc) {
        while (true) {
            String menu = "\n🧳 ═══════════════════════════════════════════════════════════════\n" +
                    "                    REAL-TIME JOURNEY PLANNER\n" +
                    "═══════════════════════════════════════════════════════════════\n" +
                    "🎯 SMART JOURNEY PLANNING:\n" +
                    "1️⃣  Plan My Journey (Smart Recommendations)\n" +
                    "2️⃣  Quick Journey Search\n" +
                    "3️⃣  Find Trains by Departure Time\n" +
                    "4️⃣  Compare Journey Options\n" +
                    "───────────────────────────────────────────────────────────────\n" +
                    "📊 LIVE TRAIN INFORMATION:\n" +
                    "5️⃣  Live Train Status Tracker\n" +
                    "6️⃣  Station Departures Board\n" +
                    "7️⃣  Browse All Available Trains\n" +
                    "8️⃣  Real-Time Train Search Between Stations\n" +
                    "───────────────────────────────────────────────────────────────\n" +
                    "📱 TRAVELER TOOLS:\n" +
                    "9️⃣  Multi-City Journey Planner\n" +
                    "🔟  Journey Price Calculator\n" +
                    "1️⃣1️⃣ Train Schedule Lookup\n" +
                    "1️⃣2️⃣ Station Information Guide\n" +
                    "───────────────────────────────────────────────────────────────\n" +
                    "0️⃣  Exit Application\n" +
                    "═══════════════════════════════════════════════════════════════\n" +
                    "Enter your choice: ";

            System.out.print(menu);
            int choice = getIntInput(sc);

            switch (choice) {
                case 1:
                    planSmartJourney(sc);
                    break;
                case 2:
                    quickJourneySearch(sc);
                    break;
                case 3:
                    findTrainsByTime(sc);
                    break;
                case 4:
                    compareJourneyOptions(sc);
                    break;
                case 5:
                    getLiveTrainStatus(sc);
                    break;
                case 6:
                    getStationDepartures(sc);
                    break;
                case 7:
                    displayAllRealTimeTrains();
                    break;
                case 8:
                    searchRealTimeTrains(sc);
                    break;
                case 9:
                    planMultiCityJourney(sc);
                    break;
                case 10:
                    calculateJourneyPrice(sc);
                    break;
                case 11:
                    lookupTrainSchedule(sc);
                    break;
                case 12:
                    showStationGuide(sc);
                    break;
                case 0:
                    showExitMessage();
                    return;
                default:
                    System.out.println("❌ Invalid choice! Please select a valid option (0-12).");
            }
        }
    }

// SMART JOURNEY PLANNING METHODS

    private static void planSmartJourney(Scanner sc) {
        System.out.println("\n🎯 ═══════════════════════════════════════════════════════════════");
        System.out.println("                        SMART JOURNEY PLANNER");
        System.out.println("═══════════════════════════════════════════════════════════════");

        // Get journey details
        System.out.print("📍 From (departure station): ");
        String from = sc.next();

        System.out.print("📍 To (destination station): ");
        String to = sc.next();

        System.out.print("🕐 Preferred departure time (e.g., 15:30 or 3:30 PM): ");
        String departureTime = sc.next();

        System.out.println("\n🎯 What's most important to you?");
        System.out.println("1️⃣ TIME (fastest journey)");
        System.out.println("2️⃣ COST (cheapest fare)");
        System.out.println("3️⃣ COMFORT (best amenities)");
        System.out.println("4️⃣ FLEXIBLE (balanced options)");
        System.out.print("Choose priority (1-4): ");

        String[] priorities = {"", "TIME", "COST", "COMFORT", "FLEXIBLE"};
        int priorityChoice = getIntInput(sc);
        String priority = (priorityChoice >= 1 && priorityChoice <= 4) ?
                priorities[priorityChoice] : "FLEXIBLE";

        // Create journey request
        JourneyRequest request = new JourneyRequest(from, to, departureTime, priority);

        // Plan journey
        List<JourneyOption> options = JourneyPlanner.planJourney(request);

        // Display options
        JourneyPlanner.displayJourneyOptions(options, request);

        // Show quick actions
        JourneyPlanner.showQuickBookingPrompt(options);

        System.out.println("\n💡 Tip: Use option 5 to check live status of any recommended train!");
    }

    private static void quickJourneySearch(Scanner sc) {
        System.out.println("\n⚡ ═══════════════════════════════════════════════════════════════");
        System.out.println("                        QUICK JOURNEY SEARCH");
        System.out.println("═══════════════════════════════════════════════════════════════");

        System.out.print("📍 From: ");
        String from = sc.next();
        System.out.print("📍 To: ");
        String to = sc.next();

        // Use flexible timing with current time + 2 hours
        JourneyRequest quickRequest = new JourneyRequest(from, to, "14:00", "FLEXIBLE");
        quickRequest.flexibleTiming = true;

        List<JourneyOption> options = JourneyPlanner.planJourney(quickRequest);

        if (!options.isEmpty()) {
            System.out.println("\n⚡ QUICK RESULTS:");
            System.out.println("─".repeat(80));
            System.out.printf("%-15s %-15s %-15s %-6s %-12s %-8s\n",
                    "Train", "Departure", "Arrival", "Time", "Fare", "Status");
            System.out.println("─".repeat(80));

            for (int i = 0; i < Math.min(5, options.size()); i++) {
                JourneyOption option = options.get(i);
                System.out.printf("%-15s %-15s %-15s %2dh %-12s %-8s\n",
                        option.train.number,
                        option.getFormattedDeparture(),
                        option.getFormattedArrival(),
                        option.totalJourneyHours,
                        "PKR " + option.fare,
                        option.status);
            }
            System.out.println("─".repeat(80));
            System.out.println("💡 Use option 1 for detailed smart recommendations!");
        }
    }

    private static void findTrainsByTime(Scanner sc) {
        System.out.println("\n🕐 ═══════════════════════════════════════════════════════════════");
        System.out.println("                    FIND TRAINS BY DEPARTURE TIME");
        System.out.println("═══════════════════════════════════════════════════════════════");

        System.out.print("🕐 Enter preferred departure time (e.g., 18:00 or 6 PM): ");
        String time = sc.next();

        System.out.print("📍 From station (optional, press Enter to skip): ");
        sc.nextLine(); // consume newline
        String fromStation = sc.nextLine().trim();

        System.out.println("\n🔍 Searching trains departing around " + time + "...");

        List<TrainInfo> allTrains = RealTimeDataManager.getAllTrains();

        System.out.println("\n🕐 TRAINS DEPARTING AROUND " + time.toUpperCase());
        System.out.println("─".repeat(70));
        System.out.printf("%-12s %-25s %-20s %s\n", "Train #", "Train Name", "Route", "Status");
        System.out.println("─".repeat(70));

        int count = 0;
        for (TrainInfo train : allTrains) {
            if (fromStation.isEmpty() || train.route.toLowerCase().contains(fromStation.toLowerCase())) {
                System.out.printf("%-12s %-25s %-20s %s\n",
                        train.number, train.name, train.route, train.status);
                count++;
                if (count >= 8) break; // Limit results
            }
        }

        if (count == 0) {
            System.out.println("❌ No trains found for the specified criteria.");
            System.out.println("💡 Try different timing or station names.");
        } else {
            System.out.println("─".repeat(70));
            System.out.println("Found " + count + " trains. Use option 5 to check live status.");
        }
    }

    private static void compareJourneyOptions(Scanner sc) {
        System.out.println("\n📊 ═══════════════════════════════════════════════════════════════");
        System.out.println("                    COMPARE JOURNEY OPTIONS");
        System.out.println("═══════════════════════════════════════════════════════════════");

        System.out.print("📍 From: ");
        String from = sc.next();
        System.out.print("📍 To: ");
        String to = sc.next();

        // Create requests with different priorities
        JourneyRequest fastestRequest = new JourneyRequest(from, to, "14:00", "TIME");
        JourneyRequest cheapestRequest = new JourneyRequest(from, to, "14:00", "COST");
        JourneyRequest comfortRequest = new JourneyRequest(from, to, "14:00", "COMFORT");

        List<JourneyOption> fastestOptions = JourneyPlanner.planJourney(fastestRequest);
        List<JourneyOption> cheapestOptions = JourneyPlanner.planJourney(cheapestRequest);
        List<JourneyOption> comfortOptions = JourneyPlanner.planJourney(comfortRequest);

        System.out.println("\n📊 COMPARISON: " + from.toUpperCase() + " → " + to.toUpperCase());
        System.out.println("═".repeat(80));

        if (!fastestOptions.isEmpty()) {
            JourneyOption fastest = fastestOptions.get(0);
            System.out.println("⚡ FASTEST OPTION:");
            System.out.printf("   🚂 %s (%d hours) - PKR %,d\n",
                    fastest.train.name, fastest.totalJourneyHours, fastest.fare);
        }

        if (!cheapestOptions.isEmpty()) {
            JourneyOption cheapest = cheapestOptions.get(0);
            System.out.println("💰 CHEAPEST OPTION:");
            System.out.printf("   🚂 %s (PKR %,d) - %d hours\n",
                    cheapest.train.name, cheapest.fare, cheapest.totalJourneyHours);
        }

        if (!comfortOptions.isEmpty()) {
            JourneyOption comfort = comfortOptions.get(0);
            System.out.println("🛋️ MOST COMFORTABLE:");
            System.out.printf("   🚂 %s (%s) - PKR %,d\n",
                    comfort.train.name,
                    String.join(", ", comfort.amenities),
                    comfort.fare);
        }

        System.out.println("═".repeat(80));
        System.out.println("💡 Use option 1 for detailed smart planning with your preferences!");
    }

// LIVE TRAIN INFORMATION METHODS

    private static void getLiveTrainStatus(Scanner sc) {
        System.out.println("\n🔴 ═══════════════════════════════════════════════════════════════");
        System.out.println("                        LIVE TRAIN STATUS");
        System.out.println("═══════════════════════════════════════════════════════════════");

        System.out.print("🔍 Enter train number or name (e.g., 1UP, Khyber Mail): ");
        String trainId = sc.next();

        LiveTrainStatus status = RealTimeDataManager.getLiveStatus(trainId);

        if (status != null) {
            System.out.println(status);
        } else {
            System.out.println("❌ Train not found. Please check the train number or name.");
            System.out.println("💡 Tip: Use option 7 to browse all available trains.");
        }
    }

    private static void getStationDepartures(Scanner sc) {
        System.out.println("\n🚉 ═══════════════════════════════════════════════════════════════");
        System.out.println("                      STATION DEPARTURES BOARD");
        System.out.println("═══════════════════════════════════════════════════════════════");

        System.out.print("🚉 Enter station name: ");
        String stationName = sc.next();

        displayLiveDepartures(stationName);
    }

    private static void displayAllRealTimeTrains() {
        System.out.println("\n🚂 ═══════════════════════════════════════════════════════════════");
        System.out.println("                      LIVE TRAIN DATABASE");
        System.out.println("═══════════════════════════════════════════════════════════════");

        RealTimeDataManager.displayAllTrains();
    }

    private static void searchRealTimeTrains(Scanner sc) {
        System.out.println("\n🔍 ═══════════════════════════════════════════════════════════════");
        System.out.println("                    REAL-TIME TRAIN SEARCH");
        System.out.println("═══════════════════════════════════════════════════════════════");

        System.out.print("📍 From station: ");
        String from = sc.next();
        System.out.print("📍 To station: ");
        String to = sc.next();

        searchTrainsBetweenStations(from, to);
    }

// TRAVELER TOOLS METHODS

    private static void planMultiCityJourney(Scanner sc) {
        System.out.println("\n🗺️ ═══════════════════════════════════════════════════════════════");
        System.out.println("                    MULTI-CITY JOURNEY PLANNER");
        System.out.println("═══════════════════════════════════════════════════════════════");

        System.out.print("📊 Number of cities in your journey (2-5): ");
        int cities = getIntInput(sc);

        if (cities < 2 || cities > 5) {
            System.out.println("❌ Please enter between 2-5 cities.");
            return;
        }

        String[] cityList = new String[cities];
        for (int i = 0; i < cities; i++) {
            System.out.printf("📍 City %d: ", i + 1);
            cityList[i] = sc.next();
        }

        System.out.println("\n🗺️ YOUR MULTI-CITY JOURNEY PLAN:");
        System.out.println("═".repeat(70));

        int totalTime = 0;
        int totalCost = 0;

        for (int i = 0; i < cities - 1; i++) {
            System.out.printf("\n🎯 LEG %d: %s → %s\n", (i + 1), cityList[i], cityList[i + 1]);
            System.out.println("─".repeat(40));

            JourneyRequest request = new JourneyRequest(
                    cityList[i], cityList[i + 1], "14:00", "FLEXIBLE"
            );

            List<JourneyOption> options = JourneyPlanner.planJourney(request);
            if (!options.isEmpty()) {
                JourneyOption best = options.get(0);
                System.out.printf("🚂 Recommended: %s\n", best.train.name);
                System.out.printf("🕐 Departure: %s\n", best.getFormattedDeparture());
                System.out.printf("🏁 Arrival: %s\n", best.getFormattedArrival());
                System.out.printf("⏱️ Duration: %d hours\n", best.totalJourneyHours);
                System.out.printf("💰 Fare: PKR %,d\n", best.fare);

                totalTime += best.totalJourneyHours;
                totalCost += best.fare;
            } else {
                System.out.println("❌ No direct trains found for this leg.");
            }
        }

        System.out.println("\n═".repeat(70));
        System.out.println("📊 JOURNEY SUMMARY:");
        System.out.printf("⏱️ Total Travel Time: %d hours\n", totalTime);
        System.out.printf("💰 Total Estimated Cost: PKR %,d\n", totalCost);
        System.out.println("💡 Book each leg separately for best prices!");
    }

    private static void calculateJourneyPrice(Scanner sc) {
        System.out.println("\n💰 ═══════════════════════════════════════════════════════════════");
        System.out.println("                      JOURNEY PRICE CALCULATOR");
        System.out.println("═══════════════════════════════════════════════════════════════");

        System.out.print("📍 From: ");
        String from = sc.next();
        System.out.print("📍 To: ");
        String to = sc.next();

        System.out.println("\n💺 Select travel class:");
        System.out.println("1️⃣ Economy Class");
        System.out.println("2️⃣ AC Class");
        System.out.println("3️⃣ Sleeper Class");
        System.out.print("Choose class (1-3): ");

        String[] classes = {"", "ECONOMY", "AC", "SLEEPER"};
        int classChoice = getIntInput(sc);
        String travelClass = (classChoice >= 1 && classChoice <= 3) ? classes[classChoice] : "ECONOMY";

        JourneyRequest request = new JourneyRequest(from, to, "14:00", "COST");
        request.travelClass = travelClass;

        List<JourneyOption> options = JourneyPlanner.planJourney(request);

        if (!options.isEmpty()) {
            System.out.println("\n💰 PRICE BREAKDOWN: " + from.toUpperCase() + " → " + to.toUpperCase());
            System.out.println("═".repeat(60));
            System.out.printf("%-15s %-15s %-12s %s\n", "Train", "Class", "Fare", "Journey Time");
            System.out.println("─".repeat(60));

            for (int i = 0; i < Math.min(5, options.size()); i++) {
                JourneyOption option = options.get(i);
                System.out.printf("%-15s %-15s PKR %-8,d %d hours\n",
                        option.train.number,
                        travelClass,
                        option.fare,
                        option.totalJourneyHours);
            }

            System.out.println("─".repeat(60));

            // Show price comparison
            JourneyOption cheapest = options.stream()
                    .min((a, b) -> Integer.compare(a.fare, b.fare))
                    .orElse(options.get(0));

            System.out.printf("💰 Cheapest Option: %s - PKR %,d\n",
                    cheapest.train.name, cheapest.fare);
            System.out.println("💡 Prices may vary based on availability and booking date.");
        }
    }

    private static void lookupTrainSchedule(Scanner sc) {
        System.out.println("\n📅 ═══════════════════════════════════════════════════════════════");
        System.out.println("                        TRAIN SCHEDULE LOOKUP");
        System.out.println("═══════════════════════════════════════════════════════════════");

        System.out.print("🚂 Enter train number or name: ");
        String trainId = sc.next();

        List<TrainInfo> allTrains = RealTimeDataManager.getAllTrains();
        TrainInfo foundTrain = null;

        for (TrainInfo train : allTrains) {
            if (train.number.toLowerCase().contains(trainId.toLowerCase()) ||
                    train.name.toLowerCase().contains(trainId.toLowerCase())) {
                foundTrain = train;
                break;
            }
        }

        if (foundTrain != null) {
            System.out.println("\n📅 SCHEDULE: " + foundTrain.name.toUpperCase());
            System.out.println("═".repeat(50));
            System.out.printf("🚂 Train Number: %s\n", foundTrain.number);
            System.out.printf("🗺️ Route: %s\n", foundTrain.route);
            System.out.printf("🏃 Status: %s\n", foundTrain.status);
            System.out.printf("🏢 Operator: %s\n", foundTrain.operator);

            // Show simulated schedule
            System.out.println("\n📍 Major Stations:");
            System.out.println("─".repeat(40));
            String[] stations = foundTrain.route.split("→");
            for (int i = 0; i < stations.length; i++) {
                String station = stations[i].trim();
                int hour = 8 + (i * 3); // Simulate departure times
                System.out.printf("%s - %02d:00\n", station, hour % 24);
            }
            System.out.println("─".repeat(40));
            System.out.println("💡 Use option 5 for live status and delays!");
        } else {
            System.out.println("❌ Train not found. Please check the train number or name.");
        }
    }

    private static void showStationGuide(Scanner sc) {
        System.out.println("\n📍 ═══════════════════════════════════════════════════════════════");
        System.out.println("                        STATION INFORMATION GUIDE");
        System.out.println("═══════════════════════════════════════════════════════════════");

        System.out.print("🚉 Enter station name: ");
        String stationName = sc.next();

        // Show station information
        System.out.println("\n📍 STATION GUIDE: " + stationName.toUpperCase());
        System.out.println("═".repeat(50));

        // Simulate station information based on major stations
        if (stationName.toLowerCase().contains("karachi")) {
            System.out.println("🏙️ Major City: Karachi, Sindh");
            System.out.println("🚉 Main Stations: Karachi City, Karachi Cantonment");
            System.out.println("🏢 Facilities: Waiting rooms, restaurants, ATMs");
            System.out.println("📱 Contact: 021-99203279");
        } else if (stationName.toLowerCase().contains("lahore")) {
            System.out.println("🏙️ Major City: Lahore, Punjab");
            System.out.println("🚉 Main Station: Lahore Junction");
            System.out.println("🏢 Facilities: Historical museum, VIP lounge, parking");
            System.out.println("📱 Contact: 042-99203456");
        } else if (stationName.toLowerCase().contains("islamabad")) {
            System.out.println("🏙️ Capital City: Islamabad, ICT");
            System.out.println("🚉 Main Station: Islamabad Railway Station");
            System.out.println("🏢 Facilities: Modern amenities, taxi service");
            System.out.println("📱 Contact: 051-99203789");
        } else {
            System.out.println("🚉 Station: " + stationName);
            System.out.println("🏢 Basic railway station facilities available");
            System.out.println("📱 Contact: Pakistan Railways helpline");
        }

        System.out.println("─".repeat(50));
        System.out.println("💡 Use option 6 to see live departures from this station!");
    }

// UTILITY METHODS

    private static void displayLiveDepartures(String stationName) {
        System.out.println("🚉 LIVE DEPARTURES FROM " + stationName.toUpperCase());
        System.out.println("🔄 Fetching real-time departure information...\n");

        List<TrainInfo> allTrains = RealTimeDataManager.getAllTrains();

        // Filter trains that might depart from this station
        System.out.printf("%-12s %-25s %-15s %-15s %-10s\n",
                "Train #", "Train Name", "Departure", "Status", "Platform");
        System.out.println("─".repeat(85));

        int count = 0;
        for (TrainInfo train : allTrains) {
            if (train.route.toLowerCase().contains(stationName.toLowerCase())) {
                // Simulate departure times and status
                String[] times = {"08:30", "14:15", "18:45", "22:00", "09:15", "16:30"};
                String[] statuses = {"On Time", "Delayed 10m", "Boarding", "Departed", "On Time"};

                String time = times[count % times.length];
                String status = statuses[count % statuses.length];
                String platform = "Plat " + (1 + (count % 5));

                System.out.printf("%-12s %-25s %-15s %-15s %-10s\n",
                        train.number, train.name, time, status, platform);
                count++;
                if (count >= 8) break; // Limit results
            }
        }

        if (count == 0) {
            System.out.println("❌ No trains found departing from " + stationName);
            System.out.println("💡 Try major stations like Karachi, Lahore, Islamabad, Rawalpindi");
        } else {
            System.out.println("─".repeat(85));
            System.out.println("🕐 Last updated: " + java.time.LocalDateTime.now());
        }
    }

    private static void searchTrainsBetweenStations(String from, String to) {
        System.out.println("🔍 Searching real-time trains from " + from + " to " + to + "...\n");

        List<TrainInfo> trains = RealTimeDataManager.getTrainsBetweenStations(from, to);

        if (trains.isEmpty()) {
            System.out.println("❌ No direct trains found between " + from + " and " + to);
            System.out.println("💡 Try checking major stations or use option 1 for smart planning.");
        } else {
            System.out.println("🚂 AVAILABLE TRAINS: " + from.toUpperCase() + " → " + to.toUpperCase());
            System.out.println("═".repeat(70));
            System.out.printf("%-12s %-25s %-25s %s\n", "Train #", "Train Name", "Route", "Status");
            System.out.println("─".repeat(70));

            for (TrainInfo train : trains) {
                System.out.printf("%-12s %-25s %-25s %s\n",
                        train.number, train.name, train.route, train.status);
            }

            System.out.println("─".repeat(70));
            System.out.println("Found " + trains.size() + " train(s)");
            System.out.println("💡 Use option 1 for smart recommendations with timing and pricing!");
        }
    }

    private static int getIntInput(Scanner sc) {
        try {
            return sc.nextInt();
        } catch (Exception e) {
            sc.nextLine(); // Clear invalid input
            return -1;
        }
    }

    private static void showExitMessage() {
        System.out.println("\n🧳 ══════════════════════════════════════════════════════════════");
        System.out.println("    Thank you for using Pakistan Railways Smart Journey Planner!");
        System.out.println("                   🚂 Have a safe journey! 🚂");
        System.out.println("                 📱 Travel smart, travel safe! 📱");
        System.out.println("══════════════════════════════════════════");

    }
}
