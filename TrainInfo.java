public class TrainInfo {
    public String number;
    public String name;
    public String route;
    public String status;
    public String operator = "Pakistan Railways";
    public String type = "Express";

    @Override
    public String toString() {
        return String.format("%-12s %-25s %-30s %s", number, name, route, status);
    }
}