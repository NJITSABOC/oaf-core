package edu.njit.cs.saboc.blu.core.graph.tan;

import edu.njit.cs.saboc.blu.core.abn.tan.TribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.nodes.GenericBand;
import edu.njit.cs.saboc.blu.core.abn.tan.nodes.GenericBandPartition;
import edu.njit.cs.saboc.blu.core.abn.tan.nodes.GenericCluster;
import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphGroupLevel;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphLevel;
import edu.njit.cs.saboc.blu.core.graph.layout.GraphLayoutConstants;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericGroupEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.BLUGenericTANConfiguration;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.JLabel;

/**
 *
 * @author Chris O
 */
public abstract class GenericTANNoPartitionLayout<
        CONCEPT_T,
        BAND_T extends GenericBand, 
        CLUSTER_T extends GenericCluster,
        BANDNODE_T extends GenericBluBand, 
        CLUSTERNODE_T extends GenericBluCluster> extends GenericTANLayout<BAND_T, CLUSTER_T, BANDNODE_T, CLUSTERNODE_T> {
    
    public GenericTANNoPartitionLayout(BluGraph graph, TribalAbstractionNetwork tan, BLUGenericTANConfiguration config) {
        super(graph, tan, config);
    }
    
    public void doLayout() {

        super.doLayout();

        BAND_T lastSet = null;   // Used for generating the graph - this is the data version of an area
        BANDNODE_T currentSet = null;    // Used for generating the graph - this is the graphical representation of an area
        GenericBluBandPartition<BANDNODE_T> currentPartitionEntry = null;
        
        GraphLevel currentLevel = null; // This is used as a temporary variable in this method to hold the current level.
        GraphGroupLevel currentClusterLevel = null; // Used for generating the graph

        // These are a set of styles such that each new row is given a different color.
        Color[] background = {
            new Color(250, 250, 250),
            new Color(55, 213, 102),
            new Color(121, 212, 250),
            new Color(242, 103, 103),
            new Color(232, 255, 114),
            Color.cyan,
            Color.orange,
            Color.pink,
            Color.green,
            Color.yellow
        };

        int areaX = 0;  // The first area on each line is given an areaX value of 0.
        int areaY = 0;  // The first row of areas is given an areaY value of 0.
        int clusterX, clusterY;
        int x = 0, y = 20, width = 0, maxHeight = 0;
        int style = 0;

        addGraphLevel(new GraphLevel(0, graph, new ArrayList<>())); // Add the first level of areas (the single pArea 0-relationship level) to the data representation of the graph.

        for (BAND_T a : layoutGroupContainers) {  // Loop through the areas and generate the diagram for each of them
            BANDNODE_T setEntry;
            
            ArrayList<CLUSTER_T> clusters = a.getAllClusters();

            int maxRows, x2, y2, regionX, partitionBump;

            int[] bandClusterX;

            if (lastSet != null && lastSet.getPatriarchs().size() != a.getPatriarchs().size()) { // If a new row should be created...

                x = 0;  // Reset the x coordinate to the left
                y += maxHeight + GraphLayoutConstants.CONTAINER_ROW_HEIGHT; // Add the height of the tallest area to the y coordinate plus the areaRowHeight variable which defines how
                // much space should be between rows of areas.

                areaY++;    // Update the areaY variable to reflect the new row.
                areaX = 0;  // Reset the areaX variable.

                maxHeight = 0;  // Reset the maxHeight variable since this is a new row.
                style++;    // Update the style variable which is used to display different colors for the different rows.

                addGraphLevel(new GraphLevel(areaY, graph,
                        generateUpperRowLanes(-5, GraphLayoutConstants.CONTAINER_ROW_HEIGHT - 7, 3, null))); // Add a level object to the arrayList in the dataGraph object
            }

            width = 0;
            maxRows = 0;

            int clusterCount = clusters.size();
            
            int clusterEntriesWide;

            if(a.getId() == -1) {
                clusterEntriesWide = clusterCount;
            } else {
                clusterEntriesWide = (int) Math.ceil(Math.sqrt(clusterCount));
            }

            int setWidth = clusterEntriesWide * (GenericGroupEntry.ENTRY_WIDTH + GraphLayoutConstants.GROUP_CHANNEL_WIDTH);

            String regionName = "";

            if(a.getId() >= 0) {
                HashSet<CONCEPT_T> patriarchs = a.getPatriarchs();

                for(CONCEPT_T patriarch : patriarchs) {
                    regionName += (super.getConfiguration().getTextConfiguration().getConceptName(patriarch) + ", ");
                }

                regionName = regionName.substring(0, regionName.length() - 1);
            }
            
            HashSet<CONCEPT_T> concepts = new HashSet<>();
            
            clusters.forEach( (CLUSTER_T cluster) -> {
                concepts.addAll(cluster.getConcepts());
            });

            int conceptCount = concepts.size();
            int clustersInBand = clusters.size();
            
            String conceptTypeName = super.getConfiguration().getTextConfiguration().getConceptTypeName(conceptCount != 1);
            String clusterName = super.getConfiguration().getTextConfiguration().getGroupTypeName(clustersInBand != 1);
            
            String countString = String.format("(%d %s, %d %s)", conceptCount, conceptTypeName, clustersInBand, clusterName);

            regionName += (" " + countString);
            
            JLabel bandLabel;
            
            if(a.getPatriarchs().size() == 1) {
                bandLabel = new JLabel();
                bandLabel.setSize(1, 1);
            } else {
                bandLabel = createBandPartitionLabel(super.getTAN(), a.getPatriarchs(), countString, setWidth, true);
            }

            setWidth = Math.max(setWidth, bandLabel.getWidth() + 8);

            width += setWidth + 20;

            int height = bandLabel.getHeight() + (int) (Math.ceil((double) clusterCount / clusterEntriesWide))
                    * (GenericGroupEntry.ENTRY_HEIGHT + GraphLayoutConstants.GROUP_ROW_HEIGHT);  // Set the height to the greater of (a) the current height or (b) the number of regions in a column times the height of each pArea and the space between them.

            maxRows = Math.max(maxRows, (int) Math.ceil(Math.sqrt(clusterCount))); // Update the maxRows variable.

            width += 20;
            height += 60 + GraphLayoutConstants.GROUP_ROW_HEIGHT;

            // Keeps track of the tallest cell on a row so that it knows how much lower to position the next row to avoid overlap.
            if (height > maxHeight) {
                maxHeight = height;
            }

            currentLevel = levels.get(areaY);

            Color color = background[style % background.length];

            setEntry = createBandPanel(a, x, y, width, height, color, areaX, currentLevel); // Create the area

            containerEntries.put(a.getId(), setEntry);

            // Add a data representation for this new area to the current area Level obj.
            currentLevel.addContainerEntry(setEntry);    
            
            // Generates a column of lanes to the left of this area.
            addColumn(areaX, currentLevel.getLevelY(), generateColumnLanes(-3,
                    GraphLayoutConstants.CONTAINER_CHANNEL_WIDTH - 5, 3, null)); 

            currentSet = (BANDNODE_T) currentLevel.getContainerEntries().get(areaX);

            regionX = GraphLayoutConstants.PARTITION_CHANNEL_WIDTH;

            bandClusterX = new int[maxRows];
            partitionBump = 0;

            x2 = (int) (1.5 * GraphLayoutConstants.GROUP_CHANNEL_WIDTH);
            y2 = bandLabel.getHeight() + 30;
            
            clusterX = 0;
            clusterY = 0;
            
            int labelXPos =  GraphLayoutConstants.PARTITION_CHANNEL_WIDTH + (setWidth - bandLabel.getWidth()) / 2;
            bandLabel.setLocation(labelXPos, 4);

            GenericBluBandPartition<BANDNODE_T> overlapPartition = createOverlapPartitionPanel(
                    (GenericBandPartition)a.getPartitions().get(0), 
                    regionName, 
                    setEntry,
                    regionX - partitionBump, 
                    10, 
                    setWidth + GraphLayoutConstants.GROUP_CHANNEL_WIDTH + 10,
                    height - 20, 
                    color, 
                    true, 
                    bandLabel);

            partitionBump++;
            currentPartitionEntry = (GenericBluBandPartition<BANDNODE_T>)currentSet.addPartitionEntry(overlapPartition);
            
            currentPartitionEntry.addGroupLevel(new GraphGroupLevel(0, currentPartitionEntry)); // Add a new pAreaLevel to the data representation of the current Area object.

            currentSet.addRow(0, generateUpperRowLanes(-4,
                    GraphLayoutConstants.GROUP_ROW_HEIGHT - 5, 3, currentSet));

            int i = 0;

            for (CLUSTER_T p : clusters) { // Draw the pArease inside this region
                CLUSTERNODE_T clusterPanel;

                currentClusterLevel = currentPartitionEntry.getGroupLevels().get(clusterY);

                clusterPanel = createClusterPanel(p, overlapPartition, x2, y2, clusterX, currentClusterLevel);

                overlapPartition.getVisibleGroups().add(clusterPanel);

                currentPartitionEntry.addColumn(bandClusterX[clusterY], generateColumnLanes(-3,
                        GraphLayoutConstants.GROUP_CHANNEL_WIDTH - 2, 3, currentSet));

                groupEntries.put(p.getId(), clusterPanel);    // Store it in a map keyed by its ID...

                currentClusterLevel.addGroupEntry(clusterPanel);

                if ((i + 1) % clusterEntriesWide == 0 && i < clusters.size() - 1) {
                    y2 += GenericGroupEntry.ENTRY_HEIGHT + GraphLayoutConstants.GROUP_ROW_HEIGHT;
                    x2 = (int) (1.5 * GraphLayoutConstants.GROUP_CHANNEL_WIDTH);
                    clusterX = 0;
                    bandClusterX[clusterY]++;
                    clusterY++;

                    if (currentPartitionEntry.getGroupLevels().size() <= clusterY) {
                        currentPartitionEntry.addGroupLevel(new GraphGroupLevel(clusterY, currentPartitionEntry)); // Add a new pAreaLevel to the data representation of the current Area object.
                        currentSet.addRow(clusterY, generateUpperRowLanes(-4,
                                GraphLayoutConstants.GROUP_ROW_HEIGHT - 5, 3, currentSet));
                    }
                } else {
                    x2 += (GenericGroupEntry.ENTRY_WIDTH + GraphLayoutConstants.GROUP_CHANNEL_WIDTH);
                    clusterX++;
                    bandClusterX[clusterY]++;
                }

                i++;
            }

            x += width + 40;  // Set x to a position after the newly created area and the appropriate space after that given the set channel width.
            areaX++;
            lastSet = a;
        }
        
        this.centerGraphLevels(this.getGraphLevels());
    }
    
    private GenericBluBandPartition<BANDNODE_T> createOverlapPartitionPanel(
            GenericBandPartition<CLUSTER_T> partition, 
            String regionName,
            BANDNODE_T set, int x, int y, int width, int height, Color c, boolean treatPartitonAsOverlapSet, JLabel partitionLabel) {

        GenericBluBandPartition<BANDNODE_T> overlapPanel = new GenericBluBandPartition<BANDNODE_T>(partition, regionName,
                width, height, graph, set, c, treatPartitonAsOverlapSet, partitionLabel);

        graph.stretchGraphToFitPanel(x, y, width, height);

        overlapPanel.setBounds(x, y, width, height);

        set.add(overlapPanel, 0);

        return overlapPanel;
    }
}
