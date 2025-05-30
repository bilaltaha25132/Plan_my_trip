import java.util.Date;

public class LiveTrainStatus {
    public String trainNumber;
    public String trainName;
    public String currentStatus;
    public String currentLocation;
    public int delayMinutes;
    public String platform;
    public String source;
    public Date lastUpdated;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n🚂 ════════════════════════════════════════════════════════\n");
        sb.append("   LIVE TRAIN STATUS\n");
        sb.append("════════════════════════════════════════════════════════\n");
        sb.append(String.format("Train: %s - %s\n", trainNumber, trainName));
        sb.append(String.format("Status: %s", currentStatus));
        if (delayMinutes > 0) {
            sb.append(String.format(" (⏰ Delayed by %d minutes)", delayMinutes));
        }
        sb.append("\n");
        if (currentLocation != null) {
            sb.append(String.format("Location: %s\n", currentLocation));
        }
        if (platform != null) {
            sb.append(String.format("Platform: %s\n", platform));
        }
        sb.append(String.format("Data Source: %s\n", source));
        sb.append(String.format("Last Updated: %s\n", lastUpdated));
        sb.append("════════════════════════════════════════════════════════\n");
        return sb.toString();
    }
}