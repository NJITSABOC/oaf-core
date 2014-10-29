package edu.njit.cs.saboc.blu.core.graph.nodes;

import SnomedShared.generic.GenericGroupContainer;
import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphGroupLevel;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphLane;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphLevel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

/**
 *
 * @author Chris
 */
public class GenericContainerEntry extends JPanel implements ActionListener, MouseInputListener {

    protected GenericGroupContainer container;

    private JButton collapseExpand;

    protected BluGraph graph;
    
    /**
     * Index of this area in the ArrayList <i>areas</i> in the GraphLevel class.
     */
    private int containerX;

    /**
     * The level object this is part of.
     */
    private GraphLevel parentLevel;
    
    /**
     * The GraphRegion objects that represent the regions within this Area.
     */
    protected ArrayList<GenericPartitionEntry> partitions = new ArrayList<GenericPartitionEntry>();

    /**
     * Initial bounds of the container entry (e.g. not collapsed)
     */
    private Rectangle preferredBounds;

    /**
     * Keeps track of the rows (horizontal lanes) running between pAreas.
     */
    private ArrayList<GraphLane>[] rows = new ArrayList[50];

    public GenericContainerEntry(GenericGroupContainer container, BluGraph g,
            int aX, GraphLevel parent, Rectangle prefBounds) {

        this.containerX = aX;
        this.parentLevel = parent;
        this.preferredBounds = prefBounds;
        this.container = container;
        this.graph = g;

        //Setup the panel's dimensions, etc.
        setLayout(null);
        setBorder(BorderFactory.createLineBorder(Color.black));

        collapseExpand = new JButton();
        collapseExpand.setBounds(2, 2, 12, 12);
        collapseExpand.setBackground(Color.WHITE);
        collapseExpand.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        collapseExpand.setFont(new Font("Tahoma", Font.BOLD, 12));
        collapseExpand.setText("-");
        collapseExpand.addActionListener(this);

        this.addMouseListener(this);
        add(collapseExpand);
    }

    public void actionPerformed(ActionEvent e) {
        if (collapseExpand.getText().equals("+")) {

            this.expandContainer();

            try {
                graph.getParentInternalFrame().setAllGroupsHidden(false);
            } catch (Exception exc) {
                System.err.println("ERROR: BluGraph does not belong to an internal graph frame.");
            }
        } else {
            this.collapseContainer();

            Collection<? extends GenericContainerEntry> areas = graph.getContainerEntries().values();

            for (GenericContainerEntry containerEntry : areas) {
                if (!containerEntry.isCollapsed()) {
                    return;
                }
            }

            try {
                graph.getParentInternalFrame().setAllGroupsHidden(true);
            } catch (Exception exc) {
                System.err.println("ERROR: BluGraph does not belong to an internal graph frame.");
            }
        }
    }

    public void expandContainer() {

        if (graph.getSelectedEdge() != null) {
            graph.deactivateSelectedEdge();
        }

        GraphLevel currentLevel = this.getLevelParent();

        int shift = 0;

        requestFocusInWindow();

        collapseExpand.setText("-");

        for (Component y : getComponents()) {
            if (y instanceof JPanel || y instanceof JButton) {
                y.setVisible(true);
            } else if (y instanceof JLabel) {
                remove(y);
            }
        }

        shift = (int) preferredBounds.getWidth() - getWidth();
        setBounds(getX() - (shift / 2), getY(), (int) preferredBounds.getWidth(), (int) preferredBounds.getHeight());
        rippleExpand(shift / 2);

        currentLevel.cascadeHeightChange(currentLevel.getTallestContainerEntry().getHeight());

        for (GenericPartitionEntry region : partitions) {
            for (GraphGroupLevel groupLevel : region.getGroupLevels()) {
                if (groupLevel.getIsVisible()) {
                    for (GenericGroupEntry group : groupLevel.getGroupEntries()) {
                        if (group.isVisible()) {
                            group.showIncidentEdges();
                        }
                    }
                }
            }
        }

        //Inflate the lane between this area and the area directly to the right
        graph.redrawEdges();
    }

    public void collapseContainer() {
        if (graph.getSelectedEdge() != null) {
            graph.deactivateSelectedEdge();
        }

        GraphLevel currentLevel = this.getLevelParent();

        ArrayList<JLabel> collapsedContainerLabels = new ArrayList<JLabel>();
        
        final int MIN_COLLAPSED_WIDTH = 200;
        
        int collapsedContainerHeight = 8;
        
        int shift = 0;
        
        int maxLabelWidth = MIN_COLLAPSED_WIDTH;

        requestFocusInWindow();

        preferredBounds = this.getBounds();
        collapseExpand.setText("+");

        for (Component containerComponent : this.getComponents()) {
            if (containerComponent instanceof GenericPartitionEntry) {
                GenericPartitionEntry partitionEntry = (GenericPartitionEntry)containerComponent;

                JLabel regionLabel = partitionEntry.getLabel();
                
                graph.getLabelManager().removeLabel(regionLabel);
                
                JLabel partitionLabel = graph.getGraphLayout().createPartitionLabel(partitionEntry.getPartition(), maxLabelWidth);
                maxLabelWidth = Math.max(maxLabelWidth, partitionLabel.getWidth() + 8);
                
                partitionLabel.setBounds(0, collapsedContainerHeight, partitionLabel.getWidth(), partitionLabel.getHeight());
                
                collapsedContainerLabels.add(partitionLabel);
                collapsedContainerHeight += (partitionLabel.getHeight() + 8);
                
                containerComponent.setVisible(false);
            }
        }
        
        collapsedContainerHeight -= 8;
        
        for(JLabel label : collapsedContainerLabels) {
            label.setBounds((maxLabelWidth - label.getWidth()) / 2, label.getY(), label.getWidth(), label.getHeight());
            
            this.add(label);
        }
        
        int preferredWidth = maxLabelWidth;

        //Collapse the area and pull areas to the right and left towards it
        shift = (int) preferredBounds.getWidth() - preferredWidth;
        this.setBounds(getX() + (shift / 2), getY(), preferredWidth, collapsedContainerHeight + 20);
        
        rippleCollapse(shift / 2);

        currentLevel.cascadeHeightChange(currentLevel.getTallestContainerEntry().getHeight());

        for (GenericPartitionEntry region : partitions) {
            for (GraphGroupLevel groupLevel : region.getGroupLevels()) {
                if (groupLevel.getIsVisible()) {
                    for (GenericGroupEntry group : groupLevel.getGroupEntries()) {
                        if (group.isVisible()) {
                            group.hideIncidentEdges();
                        }
                    }
                }
            }
        }

        graph.redrawEdges();
    }

    public void rippleExpand(double dx) {
        ArrayList<GenericContainerEntry> containerEntries = parentLevel.getContainerEntries();
        int areaIndex = containerEntries.indexOf(this);

        //Shift areas to the right
        for (int i = areaIndex; i < containerEntries.size(); ++i) {
            containerEntries.get(i).setBounds((int) (containerEntries.get(i).getX() + dx), containerEntries.get(i).getY(),
                    containerEntries.get(i).getWidth(), containerEntries.get(i).getHeight());
        }

        //Shift areas to the left
        for (int j = areaIndex; j >= 0; --j) {
            containerEntries.get(j).setBounds((int) (containerEntries.get(j).getX() - dx), containerEntries.get(j).getY(),
                    containerEntries.get(j).getWidth(), containerEntries.get(j).getHeight());
        }
    }

    public void rippleCollapse(double dx) {
        ArrayList<GenericContainerEntry> containerEntries = parentLevel.getContainerEntries();
        int areaIndex = containerEntries.indexOf(this);

        //Shift areas to the right
        for (int i = areaIndex; i < containerEntries.size(); ++i) {
            containerEntries.get(i).setBounds((int) (containerEntries.get(i).getX() - dx), containerEntries.get(i).getY(),
                    containerEntries.get(i).getWidth(), containerEntries.get(i).getHeight());
        }

        //Shift areas to the left
        for (int j = areaIndex; j >= 0; --j) {
            containerEntries.get(j).setBounds((int) (containerEntries.get(j).getX() + dx), containerEntries.get(j).getY(),
                    containerEntries.get(j).getWidth(), containerEntries.get(j).getHeight());
        }
    }

    public void cascadeExpandToRight(int dx) {
        GenericContainerEntry containerEntryToRight = this.getContainerEntryRightOf();

        while (containerEntryToRight != null) {
            containerEntryToRight.setBounds(containerEntryToRight.getX() + dx, containerEntryToRight.getY(), containerEntryToRight.getWidth(), containerEntryToRight.getHeight());
            containerEntryToRight = containerEntryToRight.getContainerEntryRightOf();
        }
    }

    public void cascadeCollapseToRight(int dx) {
        GenericContainerEntry containerEntryToRight = this.getContainerEntryRightOf();

        while (containerEntryToRight != null) {
            containerEntryToRight.setBounds(containerEntryToRight.getX() - dx, containerEntryToRight.getY(), containerEntryToRight.getWidth(), containerEntryToRight.getHeight());
            containerEntryToRight = containerEntryToRight.getContainerEntryRightOf();
        }
    }

    public void mouseClicked(MouseEvent e) {
        requestFocusInWindow();
    }

    public void mousePressed(MouseEvent e) {

        if (graph.getSelectedEdge() != null) {
            graph.deactivateSelectedEdge();
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

    public ArrayList<GenericPartitionEntry> getContainerPartitions() {
        return partitions;
    }

    protected GenericGroupContainer getGroupContainer() {
        return container;
    }

    public int getContainerX() {
        return containerX;
    }

    public int getContainerEntryWidth() {
        return this.getWidth();
    }

    public int getContainerEntryHeight() {
        return this.getHeight();
    }

    public int getPosX() {
        return this.getX();
    }

    public int getPosY() {
        return this.getY();
    }

    public GraphLevel getLevelParent() {
        return parentLevel;
    }

    public GenericPartitionEntry addPartitionEntry(GenericPartitionEntry region) {
        partitions.add(region);
        return region;
    }

    public ArrayList<GraphLane> getColumnLeft() {
        return parentLevel.getParentGraph().getGraphLayout().getColumn(containerX, parentLevel.getLevelY());
    }

    public GraphLevel getParentLevel() {
        return parentLevel;
    }

    public ArrayList<GraphLane> getRow(int y) {
        if (rows.length <= y) {
            return null;
        }

        return rows[y];
    }

    public void setRow(int y, ArrayList<GraphLane> r) {
        if (rows.length <= y) {
            return;
        }

        rows[y] = r;
    }

    public void clearRows() {
        rows = new ArrayList[50]; // Although this is a hard-coded array, there are safety features built in to expand it, if necessary.
    }

    public void addRow(int y, ArrayList<GraphLane> r) {

        if (rows.length <= y) {
            ArrayList<GraphLane>[] rows2 = new ArrayList[y + 50];


            for (int i = 0; i < rows.length; i++) {
                rows2[i] = rows[i];
            }

            rows = rows2;
        }

        rows[y] = r;
    }

    @Override
    public String toString() {
        return "Container: ContainerX = " + containerX + ", ContainerY = " + getLevelParent().getLevelY() + ", x = " + getPosX() + ", y = " + getPosY() + ", width = " + getWidth() + ", height = " + getHeight() + "\n"
                + getGroupContainer().toString();

    }

    public GenericContainerEntry getContainerEntryRightOf() {
        if (containerX + 1 < parentLevel.getContainerEntries().size()) {
            return parentLevel.getContainerEntries().get(containerX + 1);
        }

        return null;
    }

    public boolean isLargestPartition(GenericPartitionEntry testPartitionEntry) {
        for (GenericPartitionEntry region : partitions) {
            if (testPartitionEntry.getVisibleGroupLevelCount() <= region.getVisibleGroupLevelCount()
                    && testPartitionEntry != region) {
                return false;
            }
        }

        return true;
    }

    public ArrayList<GenericPartitionEntry> getPartitionEntries() {
        return partitions;
    }

    public int getPartitionCount() {
        return partitions.size();
    }

    public boolean isCollapsed() {
        return (collapseExpand.getText()).equals("+");
    }
}
