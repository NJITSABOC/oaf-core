package edu.njit.cs.saboc.blu.core.graph.nodes;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphEdge;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphGroupLevel;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphLane;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphLevel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;

/**
 *
 * @author
 */
public class GenericGroupEntry<GROUP_T extends GenericConceptGroup> extends AbNNodeEntry {
    
    public final static int ENTRY_WIDTH = 128;
    public final static int ENTRY_HEIGHT = 40;

    protected GROUP_T group;

    private final String labelText;

    private final JLabel panelLabel;
        
    protected final BluGraph graph;
    
    /**
     * Index of this group in the <i>group</i> arrayList from GraphPAreaLevel
     */
    private int groupX;
    
    /**
     * The pAreaLevel object this is contained in.
     */
    private GraphGroupLevel parentGroupLevel;

    private ArrayList<GraphEdge> incidentEdges;

    private Point labelOffset;
        
    public GenericGroupEntry(GROUP_T group, 
            BluGraph g, 
            GenericPartitionEntry partitionEntry,
            int pX, 
            GraphGroupLevel parent, 
            ArrayList<GraphEdge> ie) {
        
        this.groupX = pX;
        this.parentGroupLevel = parent;
        this.group = group;
        this.graph = g;
        this.incidentEdges = ie;
        
        this.labelOffset = new Point(0, 0);

        setFocusable(true);

        String rootName = group.getRoot().getName();
        
        labelText = rootName;

        this.panelLabel = new JLabel(rootName);

        //Setup the panel's dimensions, etc.
        setLayout(null);
        setBorder(BorderFactory.createLineBorder(Color.black));

        //Setup the panel's label
        panelLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panelLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panelLabel.setBounds(3, 0, ENTRY_WIDTH - 6, ENTRY_HEIGHT);

        panelLabel.setToolTipText(group.getRoot().getName() + " (" + group.getConceptCount() + " concepts)");

        setBackground(Color.WHITE);

        add(panelLabel);
        
        panelLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    
                    JPopupMenu pAreaMenu = graph.getGroupEntryMenuFor(group);

                    if (pAreaMenu.isVisible()) {
                        pAreaMenu.setVisible(false);
                    }

                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    requestFocusInWindow();
                    final JPopupMenu pAreaMenu = graph.getGroupEntryMenuFor(group);

                    if (!pAreaMenu.isVisible()) {
                        pAreaMenu.setVisible(true);
                    }

                    pAreaMenu.setLocation(e.getLocationOnScreen());
                }
            }
        });
    }
    
    public GenericGroupEntry labelOffset(Point offset) {
        this.labelOffset = offset;
        
        return this;
    }
    
    public Point getLabelOffset() {
        return labelOffset;
    }
    
    public GROUP_T getGroup() {
        return group;
    }

    public JLabel getLabel() {
        return panelLabel;
    }

    public String getLabelText() {
        return labelText;
    }

    public int getGroupX() {
        return groupX;
    }

    public void setGroupX(int x) {
        groupX = x;
    }

    public int getAbsoluteX() {
        return parentGroupLevel.getParentPartition().getAbsoluteX() + this.getX();
    }

    public int getAbsoluteY() {
        return parentGroupLevel.getParentPartition().getAbsoluteY() + this.getY();
    }

    public int getEntryWidth() {
        return this.getWidth();
    }

    public int getEntryHeight() {
        return this.getHeight();
    }

    public GraphGroupLevel getGroupLevelParent() {
        return parentGroupLevel;
    }

    public void setGroupLevelParent(GraphGroupLevel l) {
        parentGroupLevel = l;
    }

    public GenericContainerEntry getParentContainer() {
        return parentGroupLevel.getParentPartition().getParentContainer();
    }

    public GenericPartitionEntry getParentPartition() {
        return parentGroupLevel.getParentPartition();
    }

    public GraphLevel getParentLevel() {
        return parentGroupLevel.getParentPartition().getParentContainer().getParentLevel();
    }

    public ArrayList<GraphLane> getColumnLeft() {
        return parentGroupLevel.getParentPartition().getColumn(groupX);
    }

    @Override
    public String toString() {
        return "Partial-area" + ": pAreaX = " + groupX + ", pAreaY = " + getGroupLevelParent().getGroupLevelY() + ", x = " + getX() + ", y = " + getY() + "" + ", isVisible = " + this.isVisible();
    }

    public void addIncidentEdge(GraphEdge e) {
        incidentEdges.add(e);
    }

    public void removeIncidentEdge(GraphEdge e) {
        incidentEdges.remove(e);
    }

    public void removeIncidentEdges() {
        ArrayList<GraphEdge> incidentCopy = (ArrayList<GraphEdge>) incidentEdges.clone();
        incidentEdges.clear();

        for (GraphEdge e : incidentCopy) {
            if (graph.getEdges().contains(e)) {
                graph.removeEdge(e);
            }
        }

    }

    public void hideIncidentEdges() {

        for (GraphEdge e : incidentEdges) {
            if (graph.getEdges().contains(e)) {
                for (JPanel segment : e.getSegments()) {
                    graph.remove(segment);
                }
                graph.getEdges().remove(e);
            }
        }

        graph.repaint();
    }

    public void showIncidentEdges() {
        ArrayList<GraphEdge> incidentCopy = (ArrayList<GraphEdge>) incidentEdges.clone();
        incidentEdges.clear();

        for (GraphEdge e : incidentCopy) {
            if (!graph.getEdges().contains(e)) {
                graph.drawRoutedEdge(e.getSourceID(), e.getTargetID());
            }
        }
    }

    public ArrayList<GraphEdge> getIncidentEdges() {
        return incidentEdges;
    }
}
