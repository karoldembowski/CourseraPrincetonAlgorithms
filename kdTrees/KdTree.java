import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class KdTree {
    private Node root;
    private int size;

    private class Node {
        private Point2D point;
        private boolean horizontal;

        private Node right;
        private Node left;
        private RectHV nodeRectangle;

        public Node(Point2D p, boolean isHorizontal, RectHV nodeRectangle) {
            if (p == null) throw new IllegalArgumentException("argument" + p + "is null");
            this.horizontal = isHorizontal;
            this.point = p;
            this.nodeRectangle = nodeRectangle;
            this.right = null;
            this.left = null;
        }
    }


    public KdTree() {
        root = null;
        size = 0;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("argument" + p + "is null");
        if (this.contains(p)) {
            return;
        }
        size++;
        if (root == null) root = new Node(p, false, new RectHV(0.0, 0.0, 1.0, 1.0));
        else insert(root, p);
    }

    private void insert(Node parent, Point2D point) {
        boolean goesLeft;

        if (parent.horizontal)
            goesLeft = Double.compare(point.y(), parent.point.y()) < 0;
        else
            goesLeft = Double.compare(point.x(), parent.point.x()) < 0;


        if (goesLeft) {
            if (parent.left == null)
                parent.left = new Node(point, !parent.horizontal, createNodeRectangle(parent, goesLeft));
            else insert(parent.left, point);
        } else {
            if (parent.right == null)
                parent.right = new Node(point, !parent.horizontal, createNodeRectangle(parent, goesLeft));
            else insert(parent.right, point);
        }
    }

    private RectHV createNodeRectangle(Node parent, boolean goesLeft) {
        RectHV newNodeRectangle;

        if (parent.horizontal) {
            if (goesLeft)
                newNodeRectangle = new RectHV(parent.nodeRectangle.xmin(), parent.nodeRectangle.ymin(), parent.nodeRectangle.xmax(), parent.point.y());
            else
                newNodeRectangle = new RectHV(parent.nodeRectangle.xmin(), parent.point.y(), parent.nodeRectangle.xmax(), parent.nodeRectangle.ymax());
        } else {
            if (goesLeft)
                newNodeRectangle = new RectHV(parent.nodeRectangle.xmin(), parent.nodeRectangle.ymin(), parent.point.x(), parent.nodeRectangle.ymax());
            else
                newNodeRectangle = new RectHV(parent.point.x(), parent.nodeRectangle.ymin(), parent.nodeRectangle.xmax(), parent.nodeRectangle.ymax());
        }
        return newNodeRectangle;
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("argument" + p + "is null");
        return !(search(p) == null);
    }

    private Node search(Point2D p) {
        if (root == null) return null;
        Node searchedNode = root;
        while (searchedNode != null) {

            int compare;
            if (searchedNode.horizontal) compare = Double.compare(p.y(), searchedNode.point.y());
            else compare = Double.compare(p.x(), searchedNode.point.x());

            if (compare == 0 && (p.y() == searchedNode.point.y() && p.x() == searchedNode.point.x())) {
                return searchedNode;
            } else if (compare < 0) searchedNode = searchedNode.left;
            else searchedNode = searchedNode.right;
        }
        return null;
    }

    public void draw() {
        draw(root, root.nodeRectangle);
    }

    private void draw(Node node, RectHV nodeRect) {
        StdDraw.setPenColor(StdDraw.BLACK);
        node.point.draw();

        if (node.horizontal) {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(node.nodeRectangle.xmin(), node.point.y(), node.nodeRectangle.xmax(), node.point.y());
        } else {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.point.x(), node.nodeRectangle.ymin(), node.point.x(), node.nodeRectangle.ymax());
        }

        if (node.left != null) draw(node.left, node.left.nodeRectangle);
        if (node.right != null) draw(node.right, node.right.nodeRectangle);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("argument" + rect + "is null");
        if (root == null) return null;
        ArrayList<Point2D> res = new ArrayList<>();
        if (rect.intersects(root.nodeRectangle)) {
            if (rect.contains(root.point)) res.add(root.point);
            range(rect, root, res);
        }
        return res;
    }

    private void range(RectHV rect, Node node, ArrayList<Point2D> array) {
        //left
        if (node.left != null && rect.intersects(node.left.nodeRectangle)) {
            if (rect.contains(node.left.point)) array.add(node.left.point);
            range(rect, node.left, array);
        }
        //right
        if (node.right != null && rect.intersects(node.right.nodeRectangle)) {
            if (rect.contains(node.right.point)) array.add(node.right.point);
            range(rect, node.right, array);
        }
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("argument" + p + "is null");
        if (root == null) return null;
        Point2D nearestPoint = root.point;
        nearest(p, root, nearestPoint);
        return nearestPoint;
    }

    private void nearest(Point2D p, Node node, Point2D nearest) {
        double distanceToNearest = p.distanceTo(nearest);
        //left
        if (node.left != null && node.left.nodeRectangle.distanceTo(p) < distanceToNearest) {
            if (p.distanceTo(node.left.point) < distanceToNearest) nearest = node.left.point;
            nearest(p, node.left, nearest);
        }
        //right
        if (node.right != null && node.right.nodeRectangle.distanceTo(p) < distanceToNearest) {
            if (p.distanceTo(node.right.point) < distanceToNearest) nearest = node.right.point;
            nearest(p, node.right, nearest);
        }
    }

    public static void main(String[] args) {
    }
}
