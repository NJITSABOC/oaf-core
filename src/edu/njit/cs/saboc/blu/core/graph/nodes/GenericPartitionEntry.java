package edu.njit.cs.saboc.blu.core.graph.nodes;

import SnomedShared.generic.GenericContainerPartition;
import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphGroupLevel;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphLane;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;

/**
 *
 * @author Chris
 */
public class GenericPartitionEntry extends AbNNodeEntry {

    /**
     * List of the levels of pAreas in this area
     */
    private ArrayList<GraphGroupLevel> groupLevels = new ArrayList<GraphGroupLevel>(0);

    /**
     * The GraphArea object representing the area this region is within.
     */
    private GenericContainerEntry parent;

    /**
     * Keeps track of the columns (vertical lanes) running between pAreas.
     */
    private ArrayList<GraphLane>[] columns = new ArrayList[100];

    protected JLabel partitionLabel;

    protected BluGraph graph;

    private String partitionName;

    protected GenericContainerPartition partition;

    private ArrayList<GenericGroupEntry> visibleGroups = new ArrayList<>();
    private ArrayList<GenericGroupEntry> hiddenGroups = new ArrayList<>();

    private Color originalColor;
    
    protected boolean treatAsContainer = false; // When regions are disabled, Region objects are used as areas
  
    public GenericPartitionEntry(
            GenericContainerPartition partition, 
            String regionName,
            int width, 
            int height, 
            BluGraph g, 
            GenericContainerEntry p, 
            Color c, 
            boolean treatAsContainer) {

        this.setFocusable(true);

        this.setBackground(c);

        this.originalColor = c;
        this.partitionName = regionName;
        this.partition = partition;
        this.parent = p;

        this.partitionLabel = new JLabel("<HTML><center>" + regionName + "</center></HTML>");

        this.graph = g;

        this.treatAsContainer = treatAsContainer;
        
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (graph.getSelectedEdge() != null) {
                    graph.deactivateSelectedEdge();
                }
                
                graph.setCurrentPartitionEntry(GenericPartitionEntry.this);

                //Show/Hide Partial-Areas within the region dialogue
                JPopupMenu partitionMenu = graph.getPartitionMenu();

                if (e.getButton() == MouseEvent.BUTTON3) {
                    requestFocusInWindow();
                    partitionMenu.setLocation(e.getLocationOnScreen());

                    partitionMenu.setVisible(true);
                } else {
                    graph.requestFocusInWindow();
                }
            }
        });

        //Setup the panel's dimensions, etc.
        setLayout(null);
        setBorder(BorderFactory.createLineBorder(Color.black));

        //Setup the panel's label
        partitionLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        partitionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        partitionLabel.setBounds(10, 2, width - 15, 25);

        add(partitionLabel);
    }
    
    public void setLabel(JLabel label) {
        this.remove(partitionLabel);
        this.add(label);
        
        this.partitionLabel = label;
    }
    
    public GenericContainerPartition getPartition() {
        return partition;
    }

    public ArrayList<GenericGroupEntry> getVisibleGroups() {
        return visibleGroups;
    }

    public ArrayList<GenericGroupEntry> getHiddenGroups() {
        return hiddenGroups;
    }

    public JLabel getLabel() {
        return partitionLabel;
    }

    public String getPartitionName() {
        return partitionName;
    }

    public ArrayList<GenericGroupEntry> getPAreas() {
        ArrayList<GenericGroupEntry> result = new ArrayList<GenericGroupEntry>();

        for (GraphGroupLevel l : getGroupLevels()) {
            for (GenericGroupEntry p : l.getGroupEntries()) {
                result.add(p);
            }
        }

        Collections.sort(result, new Comparator<GenericGroupEntry>() {

            public int compare(GenericGroupEntry a, GenericGroupEntry b) {
                return a.getGroup().getConceptCount() > b.getGroup().getConceptCount() ? -1 : 1;
            }
        });

        return result;
    }

    public int getPartitionWidth() {
        return this.getWidth();
    }

    public int getPartitionHeight() {
        return this.getHeight();
    }

    public int getAbsoluteX() {
        return parent.getPosX() + this.getX();
    }

    public int getAbsoluteY() {
        return parent.getPosY() + this.getY();
    }

    public GenericContainerEntry getParentContainer() {
        return parent;
    }

    public ArrayList<GraphGroupLevel> getGroupLevels() {
        return groupLevels;
    }

    public void clearGroupLevels() {
        groupLevels = new ArrayList<GraphGroupLevel>();
    }

    public void clearColumns() {
        columns = new ArrayList[100];
    }

    public ArrayList<GraphLane> getColumn(int x) {
        if (columns.length <= x) {
            return null;
        }

        return columns[x];
    }

    public void addColumn(int x, ArrayList<GraphLane> col) {

        if (columns.length <= x) {
            ArrayList<GraphLane>[] columns2 = new ArrayList[x + 100];

            for (int i = 0; i < columns.length; i++) {
                columns2[i] = columns[i];
            }

            columns = columns2;
        }

        columns[x] = col;
    }

    /**
     * Adds the given pAreaLevel object to the list this object holds of the pArea levels contained in the Area.
     * @param l
     */
    public void addGroupLevel(GraphGroupLevel l) {
        groupLevels.add(l);
    }

    public int getVisibleGroupCount() {
        int result = 0;

        for (GraphGroupLevel x : groupLevels) {
            for (GenericGroupEntry y : x.getGroupEntries()) {
                if (y.isVisible()) {
                    ++result;
                }
            }
        }

        return result;
    }

    public int getVisibleGroupLevelCount() {
        int result = 0;

        for (GraphGroupLevel x : groupLevels) {
            if (x.getIsVisible()) {
                ++result;
            }
        }

        return result;
    }

    public boolean getPartitionTreatedAsContainer() {
        return treatAsContainer;
    }

    public void resizePartition() {

        int numVisibleGroups = getVisibleGroupCount();
        int horizontalGroups = (int) Math.ceil(Math.sqrt(numVisibleGroups));

        //Expand the region to accomodate added partial-areas
        if (horizontalGroups > this.groupLevels.get(0).getGroupEntryCount()) {

        } //Collapse the region to accomodate removed partial-areas
        else if (horizontalGroups < this.groupLevels.get(0).getGroupEntryCount()) {
            ArrayList<GenericGroupEntry> toBeMoved = new ArrayList<GenericGroupEntry>();

            for (GraphGroupLevel x : groupLevels) {
                for (int k = horizontalGroups; k < x.getVisibleGroupCount(); ++k) {
                    //Save the partial-areas to be moved...
                    toBeMoved.add(x.getGroupEntries().get(k));
                    //remove those partial-areas from their current partial-area level.
                    x.removeGroupEntry(x.getGroupEntries().get(k));
                }
            }
        }
    }


}
