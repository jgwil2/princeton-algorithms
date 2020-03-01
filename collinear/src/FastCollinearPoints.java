import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FastCollinearPoints {

    private LineSegment[] segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        int len = points.length;
        // sort points before iterating so that p->s always
        // represents the farthest spread of 4 collinear points
        Arrays.sort(points);
        ArrayList<LineSegment> segmentList = new ArrayList<>();
        for (Point p : points) {
            // clone points so we don't sort the array we are iterating
            Point[] ps = points.clone();
            Arrays.sort(ps, p.slopeOrder());
            for (int i = 1; i < len - 2; i++) {
                // look for any three adjacent points whose slopes with
                // respect to p are equal: then we have four collinear
                Point q = ps[i];
                Point r = ps[i + 1];
                Point s = ps[i + 2];
                double slope1 = p.slopeTo(q);
                double slope2 = p.slopeTo(r);
                double slope3 = p.slopeTo(s);
                boolean areCollinear = slope1 == slope2 && slope1 == slope3;
                if (areCollinear) {
                    // here we know we have four collinear
                    // but we need to see if we have more
                    // so we filter by the target slope and
                    // add a line segment for the first and last
                    // only if the first by natural order == p
                    ArrayList<Point> collinearPoints = new ArrayList<>();
                    collinearPoints.add(p);
                    for (int j = 1; j < len; j++) {
                        if (p.slopeTo(ps[j]) == slope1) {
                            collinearPoints.add(ps[j]);
                        }
                    }
                    Collections.sort(collinearPoints);
                    if (p == collinearPoints.get(0)) {
                        segmentList.add(new LineSegment(collinearPoints.get(0), collinearPoints.get(collinearPoints.size() - 1)));
                    }
                    // IMPORTANT: break out of loop after this
                    // because we have already added the maximal
                    // line segment for this slope; if we continue
                    // loop and there are more than four collinear
                    // points, we will filter by the same slope
                    // and add the same line once again for each
                    // additional collinear point
                    break;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}