package edu.njit.cs.saboc.blu.core.graph;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphEdge;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphEdgeHandle;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphLane;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphLevel;
import edu.njit.cs.saboc.blu.core.graph.layout.BluGraphLayout;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericContainerEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericGroupEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericPartitionEntry;
import edu.njit.cs.saboc.blu.core.gui.dialogs.GenericGroupEditMenu;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.AbNLabelManager;
import edu.njit.cs.saboc.blu.core.gui.graphframe.GenericInternalGraphFrame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.event.MouseInputListener;


/**
 * This is the panel on which the graph is drawn. It takes as a parameter the
 * information for the desired hierarchy and generates the corresponding graph
 * layout. It also handles edge drawing within the graph.
 *
 * @author David Daudelin and Chris Ochs
 */
public class BluGraph extends JLayeredPane implements MouseInputListener, FocusListener {

    
    /**
     * Defines the thickness of the edges.
     */
    protected static final int EDGE_WIDTH = 2;
    
    /**
     * Defines a set of colors to be used for the lines.
     */
    protected static final Color[][] lineColors = {
        {Color.black,
            Color.blue,
            new Color(180, 4, 4),
            new Color(138, 8, 134),
            new Color(223, 1, 116)},
        {new Color(180, 95, 4),
            new Color(255, 0, 255),
            new Color(8, 138, 133),
            new Color(8, 75, 138),
            new Color(134, 138, 8)}};
    
    /**
     * This is a list of all edges currently drawn.
     */
    private ArrayList<GraphEdge> edges = new ArrayList<GraphEdge>();
    /**
     * This is a list of all edges which have been manually adjusted.
     */
    private ArrayList<GraphEdge> manualEdges = new ArrayList<GraphEdge>();
    /**
     * This keeps track of which edge "lanes" are currently filled with an edge.
     */
    private ArrayList<GraphLane> occupiedLanes = new ArrayList<GraphLane>();
    /**
     * This stores the currently selected edge, if any.
     */
    private GraphEdge selectedEdge;
    /**
     * This stores the edge currently being hovered over, if any.
     */
    private GraphEdge hoveredEdge;
    
    /**
     * Stores the JPanel for the handle that is currently being dragged. This is
     * used to preserve the grip on the handle even when the mouse moves off it.
     */
    private GraphEdgeHandle lastDragged;
    
    /**
     * Used to store the width of the graph.
     */
    private int panelWidth = 0;
    /**
     * Used to store the height of the graph.
     */
    private int panelHeight = 0;

    /*
     * Used to map each JPanel segment of a line to the data structure for it's entire line.
     */
    private HashMap<JPanel, GraphEdge> segmentToEdge = new HashMap<JPanel, GraphEdge>();
    /**
     * Used to keep track of the currently visible handles
     */
    private ArrayList<GraphEdgeHandle> activeHandles = new ArrayList<GraphEdgeHandle>();

    /**
     * Popup menu displayed when a pArea is right-clicked
     */
    protected GenericGroupEditMenu groupMenu;
    
    /**
     * Popup menu displayed when an edge is right-clicked
     */
    private JPopupMenu edgeMenu = new JPopupMenu();
    
    /**
     * Popup menu displayed when a region is right-clicked
     */
    protected JPopupMenu partitionMenu;

    /**
     * Stores the data for the hierarchy this graph represents.
     */
    protected AbstractionNetwork abstractionNetwork;

    /**
     * Keeps track of the currently selected region
     */
    protected GenericPartitionEntry currentPartition;
    
    /**
     * Keeps track of the currently selected pArea
     */
    protected GenericGroupEntry currentGroup;

    
    protected BluGraphLayout layout;

    /**
     * Indicates if this graph just contains Areas, not regions
     */
    private boolean isAreaGraph;
    
    protected boolean showConceptCountLabels;
    
    private final AbNLabelManager labelManager;

    /**
     * Sets up the graph based on the hierarchy information passed in.
     * @param hierarchyData An object containing the information for the hierarchy this graph represents.
     */
    public BluGraph(final AbstractionNetwork hierarchyData, boolean areaGraph, boolean conceptLabels) {
        this.abstractionNetwork = hierarchyData;
        this.isAreaGraph = areaGraph;
        this.showConceptCountLabels = conceptLabels;
        
        this.groupMenu = new GenericGroupEditMenu(this);
        this.labelManager = new AbNLabelManager(hierarchyData);

        setOpaque(true);
        setBackground(Color.white);
        setLayout(null);
        setPreferredSize(new Dimension(panelWidth, panelHeight));

        setVisible(true);
        setFocusable(true);

        initializeEdgeMenu();
        
        this.addMouseListener(this);
    }
    
    /**
     * This updates the edges which have been manually adjusted in the past. It only updates the first and last points connecting to the pAreas so as to preserve the manual changes which were made.
     * @param e The edge to adjust.
     */
    public void updateManualEdge(GraphEdge e) {
        GenericGroupEntry a1 = (GenericGroupEntry) getGroupEntries().get(e.getSourceID());
        GenericGroupEntry a2 = (GenericGroupEntry) getGroupEntries().get(e.getTargetID());

        e.setPoint(0, new Point(a1.getAbsoluteX() + a1.getWidth() / 2, a1.getAbsoluteY()));
        e.setPoint(1, new Point(a1.getAbsoluteX() + a1.getWidth() / 2, (int) e.getPoints().get(1).getY()));

        e.setPoint(e.getPoints().size() - 2, new Point(a2.getAbsoluteX() + a2.getWidth() / 2, (int) e.getPoints().get(e.getPoints().size() - 2).getY()));
        e.setPoint(e.getPoints().size() - 1, new Point(a2.getAbsoluteX() + a2.getWidth() / 2, a2.getAbsoluteY() + a2.getHeight()));

        e.updateEdge();
    }

    /**
     * Draws an orthogonally routed edge between two pAreas - avoiding overlap with other objects.
     * @param conceptID1 The concept ID for the source pArea
     * @param conceptID2 The concept ID for the target pArea
     */
    public void drawRoutedEdge(int conceptID1, int conceptID2) {
        GenericGroupEntry a1 = (GenericGroupEntry) getGroupEntries().get(conceptID1);
        GenericGroupEntry a2 = (GenericGroupEntry) getGroupEntries().get(conceptID2);

        GraphLane l;
        int tempX;
        int tempY;

        int currentLevel = a1.getParentLevel().getLevelY();
        int targetLevel = a2.getParentLevel().getLevelY();

        ArrayList<GenericContainerEntry> tempAreas;
        GenericContainerEntry tempArea;

        ArrayList<Point> pList = new ArrayList<Point>();

        l = nextLane(a1.getGroupLevelParent().getRowAbove());     // Get the next available lane above the pArea if there is one

        if (l == null) {    // If there isn't an available lane...
            GraphEdge newEdge = new GraphEdge(conceptID1, conceptID2, null);    // Create an object for this edge,
            edges.add(newEdge); // Add it to the list of current edges,
            layout.resizeGroupRow(a1.getGroupLevelParent().getRowAbove(), a1.getParentLevel().getLevelY(), 5, a1); // Add lanes to the row you want to enter
            return; // Abort this method because resizePAreaRow takes care of drawing the edge from here.
        }

        l.setEmpty(false);  // Mark the lane we're going to occupy as full.
        occupiedLanes.add(l);
        tempY = a1.getAbsoluteY() + l.getPosY();
        tempX = a1.getAbsoluteX() + a1.getWidth() / 2;
        pList.add(new Point(tempX, a1.getAbsoluteY()));  // This is the point in the middle of the upper edge of the pArea.
        pList.add(new Point(tempX, tempY));    // This is the point directly above the center of the upper edge of the pArea.

        // If the edge is on the right half of an area and is not in the rightmost area of the level, 
        // it should be routed to the right side of this Area.
        if (a1.getAbsoluteX() - a1.getParentContainer().getPosX()
                > a1.getParentContainer().getWidth() / 2
                && getLevels().get(a1.getParentLevel().getLevelY()).getContainerEntries().size()
                > a1.getGroupLevelParent().getParentPartition().getParentContainer().getContainerX() + 1) {

            GenericContainerEntry nextOver = getLevels().get(a1.getParentLevel().getLevelY()).getContainerEntries().get(a1.getParentContainer().getContainerX() + 1);

            l = nextLane(nextOver.getColumnLeft());

            if (l == null) {
                GraphEdge newEdge = new GraphEdge(conceptID1, conceptID2, null);
                edges.add(newEdge);
                layout.resizeColumn(nextOver.getColumnLeft(), 5, nextOver);
                return;
            }

            tempX = nextOver.getPosX() + l.getPosX();

        } //Otherwise, the edge should be routed to the left side of this area.
        else {
            l = nextLane(a1.getParentContainer().getColumnLeft()); // Get the next available lane to the left of this pArea's parent Area if there is one.

            if (l == null) {
                GraphEdge newEdge = new GraphEdge(conceptID1, conceptID2, null);
                edges.add(newEdge);
                layout.resizeColumn(a1.getParentContainer().getColumnLeft(), 5, a1.getParentContainer());
                return;
            }

            tempX = a1.getParentContainer().getPosX() + l.getPosX();
        }


        l.setEmpty(false);
        occupiedLanes.add(l);
        pList.add(new Point(tempX, tempY));

        currentLevel--; // Now we're moving up one level...

        l = nextLane(a1.getParentLevel().getRowAbove());   // Get the next available lane above this pArea's parent Area if there is one.
        if (l == null) {
            GraphEdge newEdge = new GraphEdge(conceptID1, conceptID2, null);
            edges.add(newEdge);
            layout.resizeRow(a1.getParentLevel().getRowAbove(), a1.getParentLevel().getLevelY(), 5, a1.getParentContainer());
            return;
        }
        
        l.setEmpty(false);
        occupiedLanes.add(l);
        tempY = l.getPosY() + a1.getParentLevel().getY();
        pList.add(new Point(tempX, tempY));

        while (currentLevel > targetLevel) // While we're still not in the row directly below our target pArea's level...
        {
            tempAreas = getLevels().get(currentLevel).getContainerEntries();

            tempArea = nearestColumnContainerEntry(tempAreas, tempX); // Gets the area with the left column nearest to the current position.
            
            l = nextLane(tempArea.getColumnLeft());

            if (l == null) {
                GraphEdge newEdge = new GraphEdge(conceptID1, conceptID2, null);
                edges.add(newEdge);
                layout.resizeColumn(tempArea.getColumnLeft(), 5, tempArea);
                return;
            }

            l.setEmpty(false);
            occupiedLanes.add(l);
            tempX = tempArea.getPosX() + l.getPosX();
            pList.add(new Point(tempX, tempY));

            currentLevel--; // Moving up one level...

            l = nextLane(tempAreas.get(0).getLevelParent().getRowAbove());   // Get the next available lane above this level.

            if (l == null) {
                GraphEdge newEdge = new GraphEdge(conceptID1, conceptID2, null);
                edges.add(newEdge);
                layout.resizeRow(tempAreas.get(0).getLevelParent().getRowAbove(), tempAreas.get(0).getLevelParent().getLevelY(), 5, tempAreas.get(0));
                return;
            }

            l.setEmpty(false);
            occupiedLanes.add(l);
            tempY = l.getPosY() + tempAreas.get(0).getLevelParent().getY();
            pList.add(new Point(tempX, tempY));
        }

        l = nextLane(a2.getColumnLeft());   // Get the next available lane to the left of the target pArea.

        if (l == null) {
            GraphEdge newEdge = new GraphEdge(conceptID1, conceptID2, null);
            edges.add(newEdge);
            layout.resizeGroupColumn(a2.getColumnLeft(), 5, a2);
            return;
        }

        l.setEmpty(false);
        occupiedLanes.add(l);
        tempX = a2.getAbsoluteX() + l.getPosX();
        pList.add(new Point(tempX, tempY));

        tempY = a2.getAbsoluteY() + a2.getEntryHeight() + 3;   // The point to the left and below the target pArea.
        pList.add(new Point(tempX, tempY));

        tempX = a2.getAbsoluteX() + a2.getEntryWidth() / 2;    // Now directly below the target pArea.
        pList.add(new Point(tempX, tempY));

        tempY = a2.getAbsoluteY() + a2.getEntryHeight();       // Final destination, touching the center of the lower edge of the target pArea.

        pList.add(new Point(tempX, tempY));

        GraphEdge dataEdge = new GraphEdge(conceptID1, conceptID2, pList);
        addEdge(pList, lineColors[a1.getGroupLevelParent().getGroupLevelY() % 2][a1.getGroupX() % 5], dataEdge, a1, a2);
        edges.add(dataEdge);

    }

    /**
     * This passes back the next available GraphLane object in an ArrayList of such objects that is closest to the middle of the "road". 
     * @param road The ArrayList of GraphLane objects that make up a column or row.
     * @return The GraphLane closest to the middle of the given road.
     */
    private GraphLane nextLane(ArrayList<GraphLane> road) {

        int m = road.size() / 2;
        int i = 0;

        while (i <= m) {
            if ((i + m) < road.size() && road.get(i + m).isEmpty()) {
                return road.get(i + m);
            }

            if ((m - i) >= 0 && road.get(m - i).isEmpty()) {
                return road.get(m - i);
            }

            i++;
        }

        return null;
    }


    /**
     * Redraws all automatically routed edges and updates those which have been manually adjusted.
     */
    public void redrawEdges() {

        for (GraphLane l2 : occupiedLanes) {
            l2.setEmpty(true);
        }

        ArrayList<GraphEdge> edgesCopy = (ArrayList<GraphEdge>) edges.clone();
        removeEdges(edgesCopy);
        edges.clear();
        
        for (GraphEdge e : edgesCopy) {
            if (manualEdges.contains(e)) {
                updateManualEdge(e);
            } else {
                drawRoutedEdge(e.getSourceID(), e.getTargetID());
            }
        }

        repaint();
    }

    /**
     * Returns the Area which has a left column closest to the given x-coordinate.
     * @param areas The areas to search through.
     * @param x The x-coordinate.
     * @return The Area which has a left column closest to the given x-coordinate.
     */
    private GenericContainerEntry nearestColumnContainerEntry(ArrayList<GenericContainerEntry> containers, int x) {
        GenericContainerEntry result = null;
        int closestDist = Integer.MAX_VALUE;

        for (GenericContainerEntry a : containers) {
            if (Math.abs(a.getPosX() - x) < closestDist) {
                closestDist = Math.abs(a.getPosX() - x);
                result = a;
            }
        }

        return result;
    }

    /**
     * Adds an edge to the graph
     * @param points A list of the points in the edge.
     * @param c The color of the edge.
     * @param e The GraphEdge object for this edge.
     * @param source The BluPArea object representing the source pArea for this edge.
     * @param destination The BluPArea object representing the target pArea for this edge.
     */
    public void addEdge(ArrayList<Point> points, Color c, GraphEdge e,
            GenericGroupEntry source, GenericGroupEntry destination) {

        for (int i = 0; i < points.size() - 1; i++) {
            JPanel s = new JPanel();

            int width = (int) (points.get(i + 1).getX() - points.get(i).getX());
            int x = 0;

            if (width == 0) {
                s.setName("Vertical");
                width = EDGE_WIDTH;
                x = (int) points.get(i).getX();
            } else if (width > 0) {
                x = (int) points.get(i).getX();
                width = width + EDGE_WIDTH - 1;
            } else {
                x = (int) points.get(i).getX() + width;
                width = width * -1 + EDGE_WIDTH - 1;
            }

            int height = (int) (points.get(i + 1).getY() - points.get(i).getY());
            int y = 0;

            if (height == 0) {
                s.setName("Horizontal");
                height = EDGE_WIDTH;
                y = (int) points.get(i).getY();
                width++;
            } else if (height > 0) {
                y = (int) points.get(i).getY();
                height = height + EDGE_WIDTH - 1;
            } else {
                y = (int) points.get(i).getY() + height;
                height = height * -1 + EDGE_WIDTH - 1;
            }

            s.setBounds(x, y, width, height);
            s.setBackground(c);

            if (x + width > panelWidth - 10) {
                panelWidth = x + width + 10;
                updateSize();
            }

            if (y + height > panelHeight - 10) {
                panelHeight = y + height + 10;
                updateSize();
            }

            add(s, new Integer(2));

            e.addSegment(s);
            segmentToEdge.put(s, e);
            s.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseEntered(MouseEvent e) {
                    GraphEdge edge = segmentToEdge.get((JPanel) e.getComponent());

                    if (selectedEdge == null && hoveredEdge != edge) {
                        if (hoveredEdge != null) {
                            for (JPanel s : hoveredEdge.getSegments()) {
                                if (s.getName().equals("Vertical")) {
                                    s.setBounds(s.getX(), s.getY(), EDGE_WIDTH, s.getHeight());
                                } else if (s.getName().equals("Horizontal")) {
                                    s.setBounds(s.getX(), s.getY(), s.getWidth() - 2, EDGE_WIDTH);
                                } else {
                                    System.out.println("**ERROR - Edge layout not specified**");
                                }
                            }
                        }


                        hoveredEdge = edge;

                        for (int i = 0; i < edge.getSegments().size(); i++) {
                            JPanel s = edge.getSegments().get(i);

                            if (s.getName().equals("Vertical")) {
                                s.setBounds(s.getX(), s.getY(), EDGE_WIDTH + 2, s.getHeight());
                            } else if (s.getName().equals("Horizontal")) {
                                s.setBounds(s.getX(), s.getY(), s.getWidth() + 2, EDGE_WIDTH + 2);
                            } else {
                                System.out.println("**ERROR - Edge layout not specified**");
                            }
                        }
                    }
                }

                public void mouseExited(MouseEvent e) {
                    if (selectedEdge == null && hoveredEdge != null && hoveredEdge == segmentToEdge.get((JPanel) e.getComponent())) {
                        for (JPanel s : hoveredEdge.getSegments()) {
                            if (s.getName().equals("Vertical")) {
                                s.setBounds(s.getX(), s.getY(), EDGE_WIDTH, s.getHeight());
                            } else if (s.getName().equals("Horizontal")) {
                                s.setBounds(s.getX(), s.getY(), s.getWidth() - 2, EDGE_WIDTH);
                            } else {
                                System.out.println("**ERROR - Edge layout not specified**");
                            }
                        }

                        hoveredEdge = null;
                    }
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    GraphEdge edge = segmentToEdge.get((JPanel) e.getComponent());

                    if (selectedEdge != edge) {
                        if (selectedEdge != null) {
                            deactivateSelectedEdge();

                            for (GraphEdgeHandle h : activeHandles) {
                                remove(h);
                            }

                            activeHandles.clear();
                        }

                        selectedEdge = edge;

                        int widthBump = -1;

                        if (hoveredEdge != null) {
                            hoveredEdge = null;
                        } else {
                            widthBump = 1;
                        }

                        GraphEdgeHandle handle = null;

                        for (int i = 0; i < edge.getSegments().size(); i++) {
                            JPanel s = edge.getSegments().get(i);

                            if (s.getName().equals("Vertical")) {
                                s.setBounds(s.getX(), s.getY(), EDGE_WIDTH + 1, s.getHeight());
                                handle = new GraphEdgeHandle(s.getX() + EDGE_WIDTH / 2 - 5, s.getY() + s.getHeight() / 2 - 6, s, BluGraph.this);
                            } else if (s.getName().equals("Horizontal")) {
                                s.setBounds(s.getX(), s.getY(), s.getWidth() + widthBump, EDGE_WIDTH + 1);
                                handle = new GraphEdgeHandle(s.getX() + s.getWidth() / 2 - 6, s.getY() + EDGE_WIDTH / 2 - 5, s, BluGraph.this);
                            } else {
                                System.out.println("**ERROR - Edge layout not specified**");
                            }

                            if (i != 0 && i != edge.getSegments().size() - 1) {
                                BluGraph.this.add(handle, new Integer(5));
                                activeHandles.add(handle);
                            }
                        }

                        repaint();

                    } else {
                        if (e.getClickCount() == 2) {
                            try {
                                GenericInternalGraphFrame igf = BluGraph.this.getParentInternalFrame();

                                if (e.isControlDown()) {
                                    igf.focusOnComponent(getGroupEntries().get(edge.getSourceID()));
                                } else {
                                    igf.focusOnComponent(getGroupEntries().get(edge.getTargetID()));
                                }

                            } catch (Exception exception) {
                                System.err.println(exception);
                            }
                        }
                    }

                    if (e.getButton() == MouseEvent.BUTTON3) {
                        edgeMenu.show(e.getComponent(), e.getX(), e.getY());
                        edgeMenu.requestFocus();
                    }
                }
            });
        }
        source.addIncidentEdge(e);
        destination.addIncidentEdge(e);
        repaint();
    }

    public void setGraphWidth(int width) {
        this.panelWidth = width;
    }

    public void setGraphHeight(int height) {
        this.panelHeight = height;
    }
    
     /**
     * Checks to see if the graph is large enough to contain a new object with 10 pixels padding and if not, makes it larger.
     * @param x The x coordinate where this object is to be positioned.
     * @param y The y coordinate where this object is to be positioned.
     * @param width The width of this new object.
     * @param height The height of this new object.
     */
    public void stretchGraphToFitPanel(int x, int y, int width, int height) {

        if (x + width > panelWidth - 10) {
            panelWidth = x + width + 10;
            updateSize();
        }

        if (y + height > panelHeight - 10) {
            panelHeight = y + height + 10;
            updateSize();
        }
    }

    /**
     * Removes this edge from the graph.
     * @param e The edge to be removed.
     */
    public void removeEdge(GraphEdge e) {
        if (e != null) {
            edges.remove(e);
            getGroupEntries().get(e.getSourceID()).removeIncidentEdge(e);
            getGroupEntries().get(e.getTargetID()).removeIncidentEdge(e);
            for (JPanel s : e.getSegments()) {
                remove(s);
            }

            if (selectedEdge != null && selectedEdge.equals(e)) {
                for (GraphEdgeHandle h : activeHandles) {
                    remove(h);
                }

                activeHandles.clear();
                selectedEdge = null;
            }
        }
        repaint();
    }

    public void clearAllEdges() {

        ArrayList<GraphEdge> removeEdges = new ArrayList<GraphEdge>();

        for (GraphEdge temp : edges) {
            removeEdges.add(temp);
        }

        removeEdges(removeEdges);
        edges.removeAll(removeEdges);

        redrawEdges();
    }

    /**
     * Deletes the edges specified in the ArrayList "objects".
     * @param objects The edges to be deleted.
     */
    public void removeEdges(ArrayList<GraphEdge> objects) {
        for (GraphEdge e : objects) {
            if (e != null) {
                removeEdge(e);
            }
        }

        repaint();
    }

    /**
     * Sets the background and foreground colors for the objects in the ArrayList "objects"
     * @param c The new background color.
     * @param t The new foreground color.
     * @param objects The objects to recolor.
     */
    public void setColors(Color c, Color t, ArrayList<JPanel> objects) {
        for (JPanel o : objects) {
            setColor(c, t, o);
        }
    }

    /**
     * Set the background and foreground color for a single object
     * @param c The new background color.
     * @param t The new foreground color.
     * @param o The object to recolor.
     */
    public void setColor(Color c, Color t, JPanel o) {
        JPanel j = o;
        j.setBackground(c);
        JLabel l = (JLabel) j.getComponent(0);
        l.setForeground(t);
    }

    /**
     * Updates the size of the graph.
     */
    public void updateSize() {
        setPreferredSize(new Dimension(panelWidth, panelHeight));

        if (getParent() != null) {
            getParent().validate();
        }
    }

    /**
     * Unselects the currently clicked on edge.
     */
    public void deactivateSelectedEdge() {
        if (selectedEdge != null) {
            for (JPanel s : selectedEdge.getSegments()) {
                if (s.getName().equals("Vertical")) {
                    s.setBounds(s.getX(), s.getY(), EDGE_WIDTH, s.getHeight());
                } else if (s.getName().equals("Horizontal")) {
                    s.setBounds(s.getX(), s.getY(), s.getWidth() - 1, EDGE_WIDTH);
                } else {
                    System.err.println("**ERROR - Edge layout not specified**");
                }
            }

            for (GraphEdgeHandle h : activeHandles) {
                remove(h);
            }

            activeHandles.clear();
            repaint();

            selectedEdge = null;
        }
    }

    public GenericInternalGraphFrame getParentInternalFrame() throws Exception {
        Component c = getParent();

        while (!(c instanceof GenericInternalGraphFrame)) {
            c = c.getParent();
        }

        if (c == null) {
            throw new Exception("BluGraph not in an internal frame");
        }

        return ((GenericInternalGraphFrame) c);
    }

    public void mouseClicked(MouseEvent e) {
        requestFocusInWindow();
        deactivateSelectedEdge();
    }

    public void mousePressed(MouseEvent e) {

        if (selectedEdge != null) {
            deactivateSelectedEdge();
        }

    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    /**
     * Returns true if a line has already been drawn between the given source and target.
     * @param source Concept id of the source pArea
     * @param target Concept id of the target pArea
     * @return A boolean variable that is true if a line has already been drawn between the given source and target and false otherwise.
     */
    public boolean edgeAlreadyDrawn(int source, int target) {
        for (GraphEdge e : edges) {
            if (e.getSourceID() == source && e.getTargetID() == target) {
                return true;
            }
        }

        return false;
    }

    public GraphEdge getSelectedEdge() {
        return selectedEdge;
    }

    public ArrayList<GraphEdgeHandle> getActiveHandles() {
        return activeHandles;
    }

    public GraphEdgeHandle getLastDragged() {
        return lastDragged;
    }

    public void setLastDragged(GraphEdgeHandle h) {
        lastDragged = h;
    }

    public HashMap<JPanel, GraphEdge> getSegmentToEdge() {
        return segmentToEdge;
    }

    public JPopupMenu getGroupEntryMenuFor(GenericConceptGroup group) {
        groupMenu.setCurrentGroup(group);
        
        return groupMenu;
    }

    public AbstractionNetwork getAbstractionNetwork() {
        return abstractionNetwork;
    }

    public ArrayList<GraphEdge> getEdges() {
        return edges;
    }

    public void addToManualEdges(GraphEdge e) {
        manualEdges.add(e);
    }

    public JPopupMenu getPartitionMenu() {
        return partitionMenu;
    }

    public ArrayList<GraphLevel> getLevels() {
        return layout.getGraphLevels();
    }
    
    public void setCurrentPartitionEntry(GenericPartitionEntry r) {
        currentPartition = r;
    }

    public GenericPartitionEntry getCurrentPartitionEntry() {
        return currentPartition;
    }

    public void setCurrentGroup(GenericGroupEntry group) {
        currentGroup = group;
    }

    public GenericGroupEntry getCurrentGroup() {
        return currentGroup;
    }

    public HashMap<Integer, ? extends GenericContainerEntry> getContainerEntries() {

        return layout.getContainerEntries();
    }

    public HashMap<Integer, ? extends GenericGroupEntry> getGroupEntries() {
        return layout.getGroupEntries();
    }

    public int getEdgeWidth() {
        return EDGE_WIDTH;
    }

    public boolean getIsAreaGraph() {
        return isAreaGraph;
    }

    public boolean showingConceptCountLabels() {
        return showConceptCountLabels;
    }

    public void focusGained(FocusEvent e) {
    }

    public void focusLost(FocusEvent e) {
    }

    /**
     * Populates the right-click menu for edges.
     */
    private void initializeEdgeMenu() {

        JMenuItem goToParentMenuItem = new JMenuItem("Go To Parent");
        goToParentMenuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    GenericInternalGraphFrame igf = BluGraph.this.getParentInternalFrame();
                    igf.focusOnComponent(getGroupEntries().get(selectedEdge.getTargetID()));
                } catch (Exception exception) {
                    System.err.println(exception);
                }
            }
        });

        edgeMenu.add(goToParentMenuItem);

        JMenuItem goToChildMenuItem = new JMenuItem("Go To Child");
        goToChildMenuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    GenericInternalGraphFrame igf = BluGraph.this.getParentInternalFrame();
                    igf.focusOnComponent(getGroupEntries().get(selectedEdge.getSourceID()));
                } catch (Exception exception) {
                    System.err.println(exception);
                }
            }
        });

        edgeMenu.add(goToChildMenuItem);

        edgeMenu.add(new JPopupMenu.Separator());

        JMenuItem removeEdgeMenuItem = new JMenuItem("Delete Edge");

        removeEdgeMenuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                removeEdge(selectedEdge);
                redrawEdges();
            }
        });

        edgeMenu.add(removeEdgeMenuItem);
    }

    public BluGraphLayout getGraphLayout() {
        return layout;
    }
    
    public AbNLabelManager getLabelManager() {
        return labelManager;
    }
}
