import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class JourneyRequest {
    public String fromStation;
    public String toStation;
    public LocalDateTime preferredDepartureTime;
    public String priority; // "TIME", "COST", "COMFORT", "FLEXIBLE"
    public int maxJourneyHours; // Maximum acceptable journey time
    public boolean flexibleTiming; // Can depart +/- 3 hours from preferred time
    public String travelClass; // "ECONOMY", "AC", "SLEEPER"

    public JourneyRequest(String from, String to, String departureTime, String priority) {
        this.fromStation = from;
        this.toStation = to;
        this.priority = priority;
        this.maxJourneyHours = 24; // Default max 24 hours
        this.flexibleTiming = true; // Default flexible
        this.travelClass = "ECONOMY"; // Default economy

        // Parse departure time (format: "15:30" or "3:30 PM")
        try {
            this.preferredDepartureTime = parseTime(departureTime);
        } catch (Exception e) {
            this.preferredDepartureTime = LocalDateTime.now().plusHours(2);
        }
    }

    private LocalDateTime parseTime(String timeStr) {
        LocalDateTime today = LocalDateTime.now();

        try {
            // Try 24-hour format first (15:30)
            if (timeStr.matches("\\d{1,2}:\\d{2}")) {
                LocalTime time = LocalTime.parse(timeStr);
                return today.with(time);
            }

            // Try 12-hour format (3:30 PM)
            if (timeStr.toUpperCase().contains("PM") || timeStr.toUpperCase().contains("AM")) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
                LocalTime time = LocalTime.parse(timeStr.toUpperCase(), formatter);
                return today.with(time);
            }

            // Default: parse as hour only
            int hour = Integer.parseInt(timeStr.replaceAll("[^0-9]", ""));
            return today.withHour(hour).withMinute(0);

        } catch (Exception e) {
            return today.plusHours(2); // Default 2 hours from now
        }
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' h:mm a");
        return String.format("Journey: %s → %s | Departure: %s | Priority: %s",
                fromStation, toStation,
                preferredDepartureTime.format(formatter),
                priority);
    }
}