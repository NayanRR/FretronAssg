import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

// Step 1: Check for intersection between all pairs of flight paths.

// For instance, check if any segment of Flight 1 intersects with any segment of Flight 2 or Flight 3.

// Step 2: If an intersection is found, adjust the paths slightly to avoid the intersection, For example, shift one of the coordinates slightly.Reiterate use recusrison again again to check for intersection.

// Step 3: Re-check for intersections after adjustment.

// Google Search List :How to check intersection of two common points in java

// https://www.geeksforgeeks.org/check-if-two-given-line-segments-intersect/

//2. Logic for slightly adjusting the path & checking for intersections using recursion
//https://stackoverflow.com/questions/20677795/checking-for-intersections-of-two-line-segments-recursion-offset

//3. Re-check for intersections after adjustment.
//https://stackoverflow.com/questions/20677/recursion    

//Tried for solution no full proof solution just for reference & approach purpose


public class NoaccidentPlease {
    
    // A class to represent a point (x, y)
    static class Point {
        double x, y;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }

    // A method to check if two line segments (p1q1) and (p2q2) intersect
    static boolean doIntersect(Point p1, Point q1, Point p2, Point q2) {
        return Line2D.linesIntersect(p1.x, p1.y, q1.x, q1.y, p2.x, p2.y, q2.x, q2.y);
    }

    // A method to adjust the path slightly to avoid intersections
    static void adjustPath(List<Point> path) {
        double epsilon = 1;  // Small adjustment value
        for (Point p : path) {
            p.x += epsilon;
            p.y += epsilon;
            epsilon += 0.1;  // Increment to ensure separation
        }
    }

    // A method to check and adjust paths for all flights
    static List<List<Point>> adjustPaths(List<List<Point>> allFlights) {
        boolean adjusted = true;

        while (adjusted) {
            adjusted = false;
            // Check all pairs of flights
            for (int i = 0; i < allFlights.size(); i++) {
                for (int j = i + 1; j < allFlights.size(); j++) {
                    List<Point> flight1 = allFlights.get(i);
                    List<Point> flight2 = allFlights.get(j);
                    // Check all pairs of segments
                    for (int k = 0; k < flight1.size() - 1; k++) {
                        for (int l = 0; l < flight2.size() - 1; l++) {
                            if (doIntersect(flight1.get(k), flight1.get(k+1), flight2.get(l), flight2.get(l+1))) {
                                adjustPath(flight2);  // Adjust the second path to avoid intersection
                                adjusted = true;
                            }
                        }
                    }
                }
            }
        }
        return allFlights;
    }

    public static void main(String[] args) {
        // Define flights with their respective paths
        //Input
        List<Point> flight1 = new ArrayList<>();
        flight1.add(new Point(1, 1));
        flight1.add(new Point(2, 2));
        flight1.add(new Point(3, 3));

        List<Point> flight2 = new ArrayList<>();
        flight2.add(new Point(1, 1));
        flight2.add(new Point(2, 4));
        flight2.add(new Point(3, 2));

        List<Point> flight3 = new ArrayList<>();
        flight3.add(new Point(1, 1));
        flight3.add(new Point(4, 2));
        flight3.add(new Point(3, 4));

        // List of all flights
        List<List<Point>> allFlights = new ArrayList<>();
        allFlights.add(flight1);
        allFlights.add(flight2);
        allFlights.add(flight3);

        // Adjust paths to avoid intersections
        List<List<Point>> adjustedFlights = adjustPaths(allFlights);

        // Print the adjusted coordinates
        for (int i = 0; i < adjustedFlights.size(); i++) {
            System.out.println("Flight " + (i + 1) + " Path: " + adjustedFlights.get(i));
        }
    }
}



