import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {
        validateConstructor(points);
        ArrayList<LineSegment> segmentsList = new ArrayList<>();
        ArrayList<Point[]> duplicateSegments = new ArrayList<>();
        Point[] tempPoints = points.clone();
        Arrays.sort(tempPoints);
        if (points.length > 3) {
            for (int i = 0; i < points.length; i++) {
                Point p = points[i];
                Arrays.sort(tempPoints, p.slopeOrder());

                //sorted by slope array
                // p is at index 0 now
                for (int j = 1; j < tempPoints.length - 1; j++) {
                    ArrayList<Point> tempLine = new ArrayList<>();
                    tempLine.add(p);
                    tempLine.add(tempPoints[j]);

                    double ogSlope = p.slopeTo(tempPoints[j]);

                    int next = j + 1;
                    int limit = tempPoints.length - 1;
                    while (p.slopeTo(tempPoints[next]) == ogSlope) {
                        tempLine.add(tempPoints[next]);
                        if (next >= limit) break;
                        next++;
                    }

                    int lineLength = tempLine.size();
                    if (lineLength > 3) {

                        Point[] line = tempLine.toArray(new Point[lineLength]);
                        Arrays.sort(line);
                        LineSegment createdLineSegment = new LineSegment(line[0], line[lineLength - 1]);

                        boolean exists = false;
                        if (segmentsList.isEmpty()) exists = false;
                        else {
                            for (Point[] segment : duplicateSegments) {
                                if (segment[0].compareTo(line[0]) == 0 && segment[1].compareTo(line[lineLength - 1]) == 0) {
                                    exists = true;
                                }
                            }
                        }
                        if (!exists) {
                            segmentsList.add(createdLineSegment);
                            Point[] lineArr = {line[0], line[lineLength - 1]};
                            duplicateSegments.add(lineArr);
                        }
                        
                    }
                    j = next - 1;
                }
            }
        }
        this.segments = segmentsList.toArray(new LineSegment[segmentsList.size()]);
    }    // finds all line segments containing 4 or more points

    public int numberOfSegments() {
        return this.segments.length;
    }    // the number of line segments

    public LineSegment[] segments() {
        return this.segments.clone();
    }

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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
