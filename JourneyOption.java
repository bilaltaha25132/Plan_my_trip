import java.time.LocalDateTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

public class JourneyOption {
    public TrainInfo train;
    public LocalDateTime actualDepartureTime;
    public LocalDateTime arrivalTime;
    public int totalJourneyHours;
    public int fare;
    public String travelClass;
    public int timeDifferenceFromPreferred; // minutes difference from user's preferred time
    public boolean hasDirectConnection;
    public String connectionDetails; // if not direct
    public String status; // "AVAILABLE", "RAC", "WAITING_LIST"
    public int availableSeats;
    public String[] amenities;
    public double matchScore; // How well this matches user preferences (0-100)

    public JourneyOption(TrainInfo train, JourneyRequest request) {
        this.train = train;
        this.hasDirectConnection = true; // Assume direct for now
        this.status = "AVAILABLE";

        // Generate realistic schedule based on train and route
        generateSchedule(request);
        calculateMatchScore(request);
    }

    private void generateSchedule(JourneyRequest request) {
        // Generate realistic departure times based on train type and route
        LocalDateTime preferredTime = request.preferredDepartureTime;

        // Adjust departure time based on train type
        if (train.name.contains("Express") || train.name.contains("Mail")) {
            // Express trains usually have fixed schedules
            this.actualDepartureTime = adjustToNearestTrainSchedule(preferredTime, train.name);
        } else {
            // Regular trains more flexible
            this.actualDepartureTime = preferredTime.plusMinutes((int)(Math.random() * 120 - 60));
        }

        // Calculate journey time based on route
        this.totalJourneyHours = calculateJourneyTime(request.fromStation, request.toStation);
        this.arrivalTime = actualDepartureTime.plusHours(totalJourneyHours);

        // Calculate time difference from preferred
        this.timeDifferenceFromPreferred = (int)Duration.between(
                preferredTime, actualDepartureTime).toMinutes();

        // Generate fare based on class and distance
        this.fare = calculateFare(request.travelClass, totalJourneyHours);
        this.travelClass = request.travelClass;

        // Random availability
        this.availableSeats = 5 + (int)(Math.random() * 45);

        // Set amenities based on train
        setAmenities();
    }

    private LocalDateTime adjustToNearestTrainSchedule(LocalDateTime preferred, String trainName) {
        // Real train schedules (simplified)
        if (trainName.contains("Khyber Mail")) {
            return preferred.withHour(21).withMinute(30); // 9:30 PM
        } else if (trainName.contains("Green Line")) {
            return preferred.withHour(22).withMinute(0);  // 10:00 PM
        } else if (trainName.contains("Tezgam")) {
            return preferred.withHour(17).withMinute(30); // 5:30 PM
        } else if (trainName.contains("Karachi Express")) {
            return preferred.withHour(18).withMinute(0);  // 6:00 PM
        }

        // Default: nearest reasonable departure time
        return preferred.plusMinutes((int)(Math.random() * 240 - 120));
    }

    private int calculateJourneyTime(String from, String to) {
        // Simplified journey time calculation based on major routes
        String route = (from + "-" + to).toLowerCase();

        if (route.contains("karachi") && route.contains("peshawar")) return 22;
        if (route.contains("karachi") && route.contains("lahore")) return 18;
        if (route.contains("karachi") && route.contains("islamabad")) return 22;
        if (route.contains("karachi") && route.contains("rawalpindi")) return 25;
        if (route.contains("karachi") && route.contains("quetta")) return 15;
        if (route.contains("lahore") && route.contains("islamabad")) return 5;
        if (route.contains("lahore") && route.contains("rawalpindi")) return 5;
        if (route.contains("peshawar") && route.contains("lahore")) return 8;

        // Default calculation
        return 12 + (int)(Math.random() * 10);
    }

    private int calculateFare(String travelClass, int journeyHours) {
        int baseFare = journeyHours * 100; // Base: 100 PKR per hour

        switch (travelClass.toUpperCase()) {
            case "ECONOMY": return baseFare;
            case "AC": return (int)(baseFare * 2.5);
            case "SLEEPER": return (int)(baseFare * 1.8);
            default: return baseFare;
        }
    }

    private void setAmenities() {
        if (train.name.contains("Green Line") || train.name.contains("Business")) {
            amenities = new String[]{"WiFi", "Meals", "AC", "Reclining Seats"};
        } else if (train.name.contains("Express") || train.name.contains("Mail")) {
            amenities = new String[]{"Meals", "Washroom", "Charging Points"};
        } else {
            amenities = new String[]{"Basic Seating", "Washroom"};
        }
    }

    private void calculateMatchScore(JourneyRequest request) {
        double score = 0;

        // Time preference matching (40% weight)
        int timeDiffHours = Math.abs(timeDifferenceFromPreferred) / 60;
        double timeScore = Math.max(0, 100 - (timeDiffHours * 10));
        score += timeScore * 0.4;

        // Priority matching (30% weight)
        switch (request.priority.toUpperCase()) {
            case "TIME":
                score += (100 - totalJourneyHours * 2) * 0.3;
                break;
            case "COST":
                score += Math.max(0, 100 - (fare / 50)) * 0.3;
                break;
            case "COMFORT":
                score += (amenities.length * 20) * 0.3;
                break;
        }

        // Availability bonus (20% weight)
        if (status.equals("AVAILABLE")) score += 20;
        else if (status.equals("RAC")) score += 10;

        // Direct connection bonus (10% weight)
        if (hasDirectConnection) score += 10;

        this.matchScore = Math.min(100, score);
    }

    public String getFormattedDeparture() {
        return actualDepartureTime.format(DateTimeFormatter.ofPattern("MMM dd, h:mm a"));
    }

    public String getFormattedArrival() {
        return arrivalTime.format(DateTimeFormatter.ofPattern("MMM dd, h:mm a"));
    }

    public String getTimeDifferenceString() {
        if (timeDifferenceFromPreferred == 0) return "Perfect timing!";

        int hours = Math.abs(timeDifferenceFromPreferred) / 60;
        int minutes = Math.abs(timeDifferenceFromPreferred) % 60;

        String diff = "";
        if (hours > 0) diff += hours + "h ";
        if (minutes > 0) diff += minutes + "m ";

        return (timeDifferenceFromPreferred > 0 ? "+" : "-") + diff + "from preferred";
    }

    @Override
    public String toString() {
        return String.format("%-15s %-15s %-15s %2dh %-10s PKR %-6d (%s)",
                train.number,
                getFormattedDeparture(),
                getFormattedArrival(),
                totalJourneyHours,
                status,
                fare,
                getTimeDifferenceString());
    }
}