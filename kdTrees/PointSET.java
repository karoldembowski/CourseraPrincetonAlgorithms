import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> tree;

    public PointSET() {
        tree = new TreeSet<>();
    }                             // construct an empty set of points

    public boolean isEmpty() {
        return tree.isEmpty();
    }                    // is the set empty?

    public int size() {
        return tree.size();
    }                 // number of points in the set

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("argument" + p + "is null");
        tree.add(p);
    }           // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("argument" + p + "is null");
        return tree.contains(p);
    }        // does the set contain point p?

    public void draw() {
        for (Point2D point : tree) {
            point.draw();
        }
    }                 // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("argument" + rect + "is null");
        ArrayList<Point2D> rangeArr = new ArrayList<>();
        for (Point2D point : tree) {
            if (rect.contains(point)) rangeArr.add(point);
        }
        return rangeArr;
    }           // all points that are inside the rectangle (or on the boundary)

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("argument" + p + "is null");
        Point2D nearest = null;
        double nearestDist = 0;
        for (Point2D point : tree) {
            double distance = p.distanceTo(point);
            if (nearest == null || nearestDist > distance) {
                nearest = point;
                nearestDist = distance;
            }
        }
        return nearest;
    }            // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {
    }                // unit testing of the methods (optional)
}
