import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
        validateConstructor(points);
        ArrayList<LineSegment> segmentsList = new ArrayList<>();
        Point[] tempPoints = points.clone();
        Arrays.sort(tempPoints);
        if (tempPoints.length > 3) {
            for (int i = 0; i < tempPoints.length - 3; i++) {
                for (int j = i + 1; j < tempPoints.length - 2; j++) {
                    for (int k = j + 1; k < tempPoints.length - 1; k++) {
                        for (int l = k + 1; l < tempPoints.length; l++) {
                            if (isCollinear(
                                    tempPoints[i],
                                    tempPoints[j],
                                    tempPoints[k],
                                    tempPoints[l]
                            )) {
                                segmentsList.add(new LineSegment(tempPoints[i], tempPoints[l]));
                            }
                        }
                    }
                }
            }
        }

        this.segments = segmentsList.toArray(new LineSegment[segmentsList.size()]);

    }    // finds all line segments containing 4 points

    public int numberOfSegments() {
        return this.segments.length;
    }         // the number of line segments

    public LineSegment[] segments() {
        return this.segments.clone();
    }             // the line segments

    private void validateConstructor(Point[] points) {
        if (points == null) throw new IllegalArgumentException("Constructor cannot be null");
        Point[] validatedPoints;
        validatedPoints = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException("A point cannot be null");
            for (Point validatedPoint : validatedPoints) {
                if (validatedPoint != null && points[i].compareTo(validatedPoint) == 0)
                    throw new IllegalArgumentException("Points cannot be the same");
            }
            validatedPoints[i] = points[i];
        }
    }

    private boolean isCollinear(Point p1, Point p2, Point p3, Point p4) {
        return p1.slopeTo(p2) == p1.slopeTo(p3) && p1.slopeTo(p2) == p1.slopeTo(p4);
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
