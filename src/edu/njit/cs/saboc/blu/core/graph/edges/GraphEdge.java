package edu.njit.cs.saboc.blu.core.graph.edges;

import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 * A data class to represent a visual edge in the graph
 * @author David Daudelin
 */
public class GraphEdge {

    /**
     * This is an integer value containing the Concept ID for the source pArea
     */
    private int sourceID;
    /**
     * This is an integer value containing the Concept ID for the target pArea
     */
    private int targetID;
    /**
     * This contains the jPanel segments representing the edge's visual line
     */
    private ArrayList<JPanel> segments;
    
    /**
     * A list of the points that correspond to each segment.
     */
    private ArrayList<Point> points;

    public GraphEdge(int s, int t, ArrayList<Point> p) {
        sourceID = s;
        targetID = t;
        segments = new ArrayList<JPanel>();
        points = p;
    }

    public int getSourceID() {
        return sourceID;
    }

    public int getTargetID() {
        return targetID;
    }

    public void addSegment(JPanel p) {
        segments.add(p);
    }

    public ArrayList<JPanel> getSegments() {
        return segments;
    }

    public void setPoint(int i, Point p) {
        points.get(i).setLocation(p);
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public void updateEdge() {
        for (int i = 0; i < points.size() - 1; i++) {
            int width = (int) (points.get(i + 1).getX() - points.get(i).getX());
            int x = 0;

            if (width == 0) {
                width = segments.get(i).getWidth();
                x = (int) points.get(i).getX();
            } else if (width > 0) {
                x = (int) points.get(i).getX();
                width = width + segments.get(i).getHeight();
            } else {
                x = (int) points.get(i).getX() + width;
                width = width * -1 + segments.get(i).getHeight();
            }

            int height = (int) (points.get(i + 1).getY() - points.get(i).getY());
            int y = 0;

            if (height == 0) {
                height = segments.get(i).getHeight();
                y = (int) points.get(i).getY();
            } else if (height > 0) {
                y = (int) points.get(i).getY();
                height = height + 1;
            } else {
                y = (int) points.get(i).getY() + height;
                height = height * -1 + 1;
            }

            segments.get(i).setBounds(x, y, width, height);
        }
    }

    public String toString() {
        return "Source ID: " + sourceID + "\nTarget ID: " + targetID + "\n";
    }
}
