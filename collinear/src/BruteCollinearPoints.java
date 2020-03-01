import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private LineSegment[] segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        int len = points.length;
        // sort points before iterating so that p->s always
        // represents the farthest spread of 4 collinear points
        Arrays.sort(points);
        ArrayList<LineSegment> segmentList = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            Point p = points[i];
            for (int j = i + 1; j < len; j++) {
                Point q = points[j];
                for (int k = j + 1; k < len; k++) {
                    Point r = points[k];
                    for (int l = k + 1; l < len; l++) {
                        Point s = points[l];
                        if (p.slopeTo(q) == q.slopeTo(r) && q.slopeTo(r) == r.slopeTo(s)) {
                            segmentList.add(new LineSegment(p, s));
                        }
                    }
                }
            }
        }
        segments = segmentList.toArray(new LineSegment[0]);
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments;
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