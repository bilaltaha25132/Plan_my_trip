import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Graph {

    LinkedList<Vertex> adjList; //Adjacency List Implementation of a Graph
    private int noOfEdges; //counts the total noOfEdges
    private int noOfVertices; //Counts the total number of vertices
    private String[] arr;//To print the path for shortest distance

    private String[] timePath; //To print the path of the shortest time


    private String[] costPath; //To print the path of the shortest cost


    private final String[] recentDestinations;// To store the recent destinations

    int i=-1; //Index for circular array to store recent destinations



    public Graph(){
        recentDestinations=new String[5];
        adjList=new LinkedList<>();
        noOfEdges=0;
    }
    // Adding vertex to the Graph,Parameter is a name of the vertex
    public void addVertex(String n){
        Vertex newData = new Vertex(n);
        for (int i = 0; i < adjList.size(); i++) {
            if (adjList.get(i).name.equals(n)) {
                return;
            }
        }
        newData.index=noOfVertices;
        adjList.add(newData);

        noOfVertices++;
    }

    // This is a helper method,that enables us to find the stored index of a specific vertex in the adjList.
    public int getVertexIndex(String name) {
        if(!vertexExist(name)) return -1;
        for (int i = 0; i < noOfVertices; i++) {
            if (adjList.get(i).name.equals(name)) {
                return i;
            }
        }
        return -1;
    }
    // Adding an edge between two vertices,a edge contains the Distance,Time and cost
    public void addEdge(String n1,String n2,int weight,int time,int cost) {// add an edge between two Vertices
        int i = getVertexIndex(n1);
        int j = getVertexIndex(n2);
        Edge edge= new Edge(this,n1,n2,weight,time,cost);
        adjList.get(i).edges.add(edge);
        adjList.get(j).edges.add(edge);
        noOfEdges++;
    }
    // Finding vertex based on the name provided by User
    public Vertex findVertex(String name){
        for (int i = 0; i < noOfVertices; i++) {
            if (adjList.get(i).name.equals(name)) {
                return adjList.get(i);
            }
        }
        return null;
    }

    // To check whether a vertex exist in the map
    public boolean vertexExist(String name){
        for (int i = 0; i < noOfVertices; i++) {
            if (adjList.get(i).name.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void deleteVertex(String name){
        if (!vertexExist(name)) return;
        Vertex found = findVertex(name);
        for (int i = 0; i < found.vertices.size(); i++) {
            found.vertices.get(i).vertices.delete(found);
        }
        adjList.delete(found);
    }

    //**********************************************To calculate the shortest Path in terms of Distance*************************************//
    // Dijkstra Algorithm to calculate the shortest path
    //This method calculates the shortest distance from source to all the
    // other vertices and store them in an array based on their index
    // Priority Queue is based on the shorter distance, smaller distance will be dequeued first
    private void dijkstraDistance(String sourceName) {
        arr=new String[noOfVertices];//Storing the shortest path from source to each vertex
        Vertex source = findVertex(sourceName);//Finding vertex
        PriorityQueue<Pair> pq = new PriorityQueue<>();//Priority Queue to decide the shorter path
        pq.enqueue(new Pair(source,sourceName+"",0));//Use of Pair class to keep track of total distance and vertices travelled
        boolean[] visited = new boolean[noOfVertices];//Array to keep track of visited Vertices
        while (!pq.isEmpty()) {
            Pair rem = pq.dequeue(); //Dequeues the source vertex
            if (visited[getVertexIndex(rem.v.name)]) {
                continue;
            }
            visited[getVertexIndex(rem.v.name)] = true; // Setting the visited vertex to true
            //Storing the path and the distance from source to every possible destination,in an array
            //using the destination vertex index
            arr[rem.v.index]="The Shortest route in terms of distance from "+sourceName+" to "+ rem.v.name + " is: " +"\n"+ rem.psf + "\nwhich makes up a total distance of " + rem.wsf+" Kilometers.";
            for (int i = 0; i < rem.v.vertices.size(); i++) {
                //For loop to traverse every adjacent vertex of the dequeued vertex
                Vertex v = rem.v.vertices.get(i);
                Edge e = rem.v.edges.get(i); //Getting the edge info of that vertex connected
                if (!visited[getVertexIndex(v.name)]) {
                    pq.enqueue(new Pair(v, "" + rem.psf +"->"+ v.name, rem.wsf + e.weight));
                    //The new path is now source+new vertex
                    //The new distance is now first edge distance+new edge distance
                }

            }
        }
    }
    //Displays Path and total distance

    /**
     *
     * @param sourceName User enters the sourceName
     * @param destination User enters the destination
     * Since every possible destination from source is saved in the array
     * We simply just display the string stored in the destination index
     */
    public void shortestDistance(String sourceName, String destination){
        try {
            dijkstraDistance(sourceName);
            Vertex v = findVertex(destination);
            System.out.println(arr[v.index] + "\n");
            i = (i + 1) % recentDestinations.length;
            recentDestinations[i] = destination;
        }catch (Exception e){
            System.out.println("No such Vertex exist, Please enter the correct name of the Vertex from the map");
        }
    }





    //**********************************************To calculate the shortest Path in terms of Time*************************************//
    // Same as that of distance, except the weight now here is edge's time
    private void dijkstraTime(String sourceName) {
        timePath=new String[noOfVertices];
        Vertex source = findVertex(sourceName);
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        pq.enqueue(new Pair(source,sourceName+"",0));
        boolean[] visited = new boolean[noOfVertices];
        while (!pq.isEmpty()) {
            Pair rem = pq.dequeue();
            if (visited[getVertexIndex(rem.v.name)]) {
                continue;
            }
            visited[getVertexIndex(rem.v.name)] = true;
            timePath[rem.v.index]="The Shortest route in terms of time from  "+sourceName+" to "+ rem.v.name + " is: " +"\n"+ rem.psf + "\nwhich takes a total time of " + rem.wsf+" hours.";
            for (int i = 0; i < rem.v.vertices.size(); i++) {
                Vertex v = rem.v.vertices.get(i);
                Edge e = rem.v.edges.get(i);
                if (!visited[getVertexIndex(v.name)]) {
                    pq.enqueue(new Pair(v, "" + rem.psf +"->"+ v.name, (rem.wsf + e.time)));
                }

            }
        }
    }
    //Displays the shortest Path in terms of Time
    public void shortestTime(String sourceName, String destination){
        try {
            dijkstraTime(sourceName);
            Vertex v = findVertex(destination);
            System.out.println(timePath[v.index] + "\n");
            i = (i + 1) % recentDestinations.length;
            recentDestinations[i] = destination;
        }catch (Exception e){
            System.out.println("No such Vertex exist, Please enter the correct name of the Vertex from the map");
        }
    }




    //**********************************************To calculate the shortest Path in terms of cost*************************************//
    //Same as that of Distance, except the weight now here is edge's cost
    private void dijkstraCost(String sourceName) {
        costPath=new String[noOfVertices];
        Vertex source = findVertex(sourceName);
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        pq.enqueue(new Pair(source,sourceName+"",0));
        boolean[] visited = new boolean[noOfVertices];
        while (!pq.isEmpty()) {
            Pair rem = pq.dequeue();
            if (visited[getVertexIndex(rem.v.name)]) {
                continue;
            }
            visited[getVertexIndex(rem.v.name)] = true;
            costPath[rem.v.index]="The cheapest route from "+sourceName+" to "+ rem.v.name + " is: " +"\n"+ rem.psf + "\nwhich costs a total of PKR. " + rem.wsf+"/=";
            for (int i = 0; i < rem.v.vertices.size(); i++) {
                Vertex v = rem.v.vertices.get(i);
                Edge e = rem.v.edges.get(i);
                if (!visited[getVertexIndex(v.name)]) {
                    pq.enqueue(new Pair(v, "" + rem.psf +"->"+ v.name, (rem.wsf + e.cost)));
                }

            }
        }
    }
    //Displays the shortest Path in terms of cost
    public void shortestCost(String sourceName, String destination){
        try {
            dijkstraCost(sourceName);
            Vertex v = findVertex(destination);
            System.out.println(costPath[v.index] + "\n");
            i = (i + 1) % recentDestinations.length;
            recentDestinations[i] = destination;
        }catch (Exception e){
            System.out.println("No such Vertex exist, Please enter the correct name of the Vertex from the map");
        }
    }

    //**********************************************END OF ALGORITHMS***************************************************//


    // Printing out all the stations in the map
    public void displayAllStations(){
        for(int i=0; i<adjList.size(); i++){
            System.out.println(""+(i+1)+")"+adjList.get(i).name);
        }
        System.out.println();
    }

    // Printing out the array of recent destinations
    public void getRecentDestinations(){
        System.out.print("Your recently searched destinations are: ");
        for(int i=0; i<recentDestinations.length; i++){
            if (recentDestinations[i]==null) recentDestinations[i]=" ";
            else{
                System.out.print(recentDestinations[i]+" ");
            }
        }
    }

    // Displays Map
    // Displays a station and all the other connected stations along their distance,time and cost.
    public void displayMap(){
        String str = "";

        for (int i=0; i<adjList.size(); i++) {
            Vertex person=adjList.get(i);
            String p = adjList.get(i).name + " -> [";

            for (int j=0; j<person.vertices.size(); j++) {
                p += "("+person.vertices.get(j).name + " -> "+ person.edges.get(j).weight+ " kms, "+person.edges.get(j).time+" hrs, PKR. "+person.edges.get(j).cost+"/=) ";
            }

            if (person.vertices.size() != 0) {
                p = p.substring(0,p.length() - 1);
            }

            p += "]\n";
            str += p;
        }

        System.out.println(str);
    }

    // Creating the train Graph
    public static Graph createGraph(){
        Graph graph=new Graph();
        graph.addVertex("Karachi");
        graph.addVertex("Islamabad");
        graph.addVertex("Quetta");
        graph.addVertex("Peshawar");
        graph.addVertex("Lahore");
        graph.addVertex("Larkana");
        graph.addVertex("Chitral");
        graph.addVertex("Sialkot");
        graph.addVertex("Chiniot");
        graph.addVertex("Jacobabad");
        graph.addVertex("Faisalabad");
        graph.addVertex("Chakwal");
        graph.addVertex("Multan");
        graph.addVertex("Sukkur");
        graph.addVertex("Rawalpindi");
        graph.addVertex("Kasur");
        graph.addVertex("Hyderabad");
        graph.addVertex("Naran");

        graph.addEdge("Lahore","Chiniot",180,3,500);
        graph.addEdge("Chitral","Chiniot",650,12,1200);
        graph.addEdge("Lahore","Islamabad",280,5,750);
        graph.addEdge("Islamabad","Quetta",910,10,1200);
        graph.addEdge("Quetta","Faisalabad",830,14,2000);
        graph.addEdge("Chakwal","Kasur",350,5,850);
        graph.addEdge("Peshawar","Kasur",590,7,1350);
        graph.addEdge("Naran","Multan",780,11,1750);
        graph.addEdge("Chitral","Peshawar",350,8,1500);
        graph.addEdge("Chitral","Kasur",780,13,1900);
        graph.addEdge("Sialkot","Chakwal",200,4,650);
        graph.addEdge("Sialkot","Kasur",180,3,550);
        graph.addEdge("Sialkot","Larkana",940,11,600);
        graph.addEdge("Larkana","Jacobabad",100,2,700);
        graph.addEdge("Islamabad","Faisalabad",250,4,850);
        graph.addEdge("Lahore","Quetta",980,15,2250);
        graph.addEdge("Lahore","Rawalpindi",370,5,1150);
        graph.addEdge("Peshawar","Naran",350,7,1800);
        graph.addEdge("Lahore","Multan",340,4,700);
        graph.addEdge("Sialkot","Lahore",130,2,750);
        graph.addEdge("Karachi","Multan",900,12,2400);
        graph.addEdge("Karachi","Hyderabad",150,3,600);
        graph.addEdge("Karachi","Sukkur",350,8,900);
        graph.addEdge("Karachi","Rawalpindi",1400,18,3300);
        graph.addEdge("Karachi","Lahore",1200,16,4500);
        graph.addEdge("Karachi","Quetta",690,11,5000);
        graph.addEdge("Multan","Larkana",510,6,700);
        graph.addEdge("Karachi","Jacobabad",550,8,1500);
        graph.addEdge("Quetta","Larkana",400,7,1400);
        graph.addEdge("Peshawar","Multan",680,8,400);
        graph.addEdge("Peshawar","Chiniot",420,6,1350);
        return graph;
    }

    // NEW: Display real-time train database
    public void displayRealTimeTrains() {
        RealTimeDataManager.displayAllTrains();
    }

    // NEW: Get live train status
    public void getLiveTrainStatus(String trainIdentifier) {
        System.out.println("🔍 Searching for train: " + trainIdentifier);

        LiveTrainStatus status = RealTimeDataManager.getLiveStatus(trainIdentifier);

        if (status != null) {
            System.out.println(status);
        } else {
            System.out.println("❌ Train not found. Please check train number or name.");
            System.out.println("💡 Tip: Try searching with train number (e.g., 1UP) or name (e.g., Khyber Mail)");
        }
    }

    // NEW: Search trains between stations with real-time data
    public void searchRealTimeTrains(String from, String to) {
        System.out.println("🔍 Searching real-time trains from " + from + " to " + to + "...\n");

        List<TrainInfo> trains = RealTimeDataManager.getTrainsBetweenStations(from, to);

        if (trains.isEmpty()) {
            System.out.println("❌ No direct trains found between " + from + " and " + to);
            System.out.println("💡 Try checking major stations or connecting routes.");
        } else {
            System.out.println("🚂 ═══════════════════════════════════════════════════════════════");
            System.out.println("     AVAILABLE TRAINS: " + from.toUpperCase() + " → " + to.toUpperCase());
            System.out.println("═══════════════════════════════════════════════════════════════");
            System.out.printf("%-12s %-25s %-30s %s\n", "Train #", "Train Name", "Route", "Status");
            System.out.println("───────────────────────────────────────────────────────────────");

            for (TrainInfo train : trains) {
                System.out.println(train);
            }

            System.out.println("═══════════════════════════════════════════════════════════════");
            System.out.println("Found " + trains.size() + " train(s)");
            System.out.println("💡 Use option 9 to get live status of any train");
        }
        System.out.println();
    }

    // NEW: Display live departures (simulated with real train data)
    public void getLiveDepartures(String stationName) {
        System.out.println("🚂 ═══════════════════════════════════════════════════════════════");
        System.out.println("     LIVE DEPARTURES FROM " + stationName.toUpperCase());
        System.out.println("═══════════════════════════════════════════════════════════════");
        System.out.println("🔄 Fetching real-time departure information...\n");

        List<TrainInfo> allTrains = RealTimeDataManager.getAllTrains();

        // Filter trains that might depart from this station
        List<TrainInfo> departingTrains = new ArrayList<>();
        for (TrainInfo train : allTrains) {
            if (train.route.toLowerCase().contains(stationName.toLowerCase())) {
                departingTrains.add(train);
            }
        }

        if (departingTrains.isEmpty()) {
            System.out.println("❌ No trains found departing from " + stationName);
            System.out.println("💡 Try major stations like Karachi, Lahore, Islamabad, Rawalpindi");
        } else {
            System.out.printf("%-12s %-25s %-15s %-15s %-10s\n",
                    "Train #", "Train Name", "Departure", "Status", "Platform");
            System.out.println("─────────────────────────────────────────────────────────────────────────");

            // Simulate departure times and status
            Random random = new Random();
            String[] times = {"08:30", "14:15", "18:45", "22:00", "09:15", "16:30"};
            String[] statuses = {"On Time", "Delayed 10m", "Boarding", "Departed", "On Time"};

            for (int i = 0; i < Math.min(departingTrains.size(), 6); i++) {
                TrainInfo train = departingTrains.get(i);
                String time = times[i % times.length];
                String status = statuses[random.nextInt(statuses.length)];
                String platform = String.valueOf(1 + random.nextInt(5));

                System.out.printf("%-12s %-25s %-15s %-15s %-10s\n",
                        train.number, train.name, time, status, "Plat " + platform);
            }

            System.out.println("─────────────────────────────────────────────────────────────────────────");
            System.out.println("🕐 Last updated: " + new Date());
        }
        System.out.println();
    }

}
