package edu.njit.cs.saboc.blu.core.graph.tan;

import edu.njit.cs.saboc.blu.core.abn.tan.TribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.Band;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphGroupLevel;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphLevel;
import edu.njit.cs.saboc.blu.core.graph.layout.BluGraphLayout;
import edu.njit.cs.saboc.blu.core.graph.layout.GraphLayoutConstants;
import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.BLUGenericTANConfiguration;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import javax.swing.JLabel;

/**
 *
 * @author Chris O
 */
public abstract class TribalAbstractionNetworkLayout extends BluGraphLayout {

    private final TribalAbstractionNetwork tan;

    private final BLUGenericTANConfiguration config;

    public TribalAbstractionNetworkLayout(BluGraph graph, TribalAbstractionNetwork tan, BLUGenericTANConfiguration config) {
        super(graph);

        this.tan = tan;

        this.config = config;
    }

    public TribalAbstractionNetwork getTAN() {
        return tan;
    }

    public BLUGenericTANConfiguration getConfiguration() {
        return config;
    }

    public void doLayout() {
        ArrayList<Band> sortedSets = new ArrayList<>();    // Used for generating the graph
        ArrayList<Band> levelSets = new ArrayList<>();     // Used for generating the graph

        ArrayList<Band> tempSets = new ArrayList<>(tan.getBands());

        Band lastSet = null;

        Collections.sort(tempSets, new Comparator<Band>() {    // Sort the areas based on the number of their relationships.

            public int compare(Band a, Band b) {
                return a.getPatriarchs().size() - b.getPatriarchs().size();
            }
        });

        for (Band set : tempSets) {

            if (lastSet != null && lastSet.getPatriarchs().size() != set.getPatriarchs().size()) {
                Collections.sort(levelSets, new Comparator<Band>() {    // Sort the areas based on the number of their relationships.

                    public int compare(Band a, Band b) {

                        int aClusterSize = a.getClusters().size();
                        int bClusterSize = b.getClusters().size();

                        if (aClusterSize == bClusterSize) {
                            return a.getConceptCount() - b.getConceptCount();
                        } else {
                            return aClusterSize - bClusterSize;
                        }
                    }
                });

                int c = 0;

                for (c = 0; c < levelSets.size(); c += 2) {
                    sortedSets.add(levelSets.get(c));
                }

                if (levelSets.size() % 2 == 0) {
                    c = levelSets.size() - 1;
                } else {
                    c = levelSets.size() - 2;
                }

                for (; c >= 1; c -= 2) {
                    sortedSets.add(levelSets.get(c));
                }

                levelSets.clear();
            }

            levelSets.add(set);

            lastSet = set;
        }

        Collections.sort(levelSets, new Comparator<Band>() {
            public int compare(Band a, Band b) {
                int aClusterSize = a.getClusters().size();
                int bClusterSize = b.getClusters().size();

                if (aClusterSize == bClusterSize) {
                    return a.getConceptCount() - b.getConceptCount();
                } else {
                    return aClusterSize - bClusterSize;
                }
            }
        });

        int c = 0;

        for (c = 0;
                c < levelSets.size();
                c += 2) {
            sortedSets.add(levelSets.get(c));
        }

        if (levelSets.size()
                % 2 == 0) {
            c = levelSets.size() - 1;
        } else {
            c = levelSets.size() - 2;
        }

        for (; c >= 1; c -= 2) {
            sortedSets.add(levelSets.get(c));
        }

        lastSet = null;
        layoutGroupContainers = sortedSets;
    }

    public ArrayList<Band> getBands() {
        return layoutGroupContainers;
    }

    private ClusterEntry createClusterPanel(Cluster p, GenericBluBandPartition parent, int x, int y, int pAreaX, GraphGroupLevel clusterLevel) {
        
        ClusterEntry clusterPanel = makeClusterNode(p, getGraph(), parent, pAreaX, clusterLevel, new ArrayList<>());

        //Make sure this panel dimensions will fit on the graph, stretch the graph if necessary
        getGraph().stretchGraphToFitPanel(x, y, SinglyRootedNodeEntry.ENTRY_WIDTH, SinglyRootedNodeEntry.ENTRY_HEIGHT);

        //Setup the panel's dimensions, etc.
        clusterPanel.setBounds(x, y, SinglyRootedNodeEntry.ENTRY_WIDTH, SinglyRootedNodeEntry.ENTRY_HEIGHT);

        parent.add(clusterPanel, 0);

        return clusterPanel;
    }

    private BandEntry createBandPanel(Band set, int x, int y, int width, int height, Color c, int areaX, GraphLevel parentLevel) {
        BandEntry setPanel = makeBandNode(set, getGraph(), areaX, parentLevel, new Rectangle(x, y, width, height));

        getGraph().stretchGraphToFitPanel(x, y, width, height);

        setPanel.setBounds(x, y, width, height);
        setPanel.setBackground(c);

        getGraph().add(setPanel, 0);

        return setPanel;
    }

    protected GenericBluBandPartition<BandEntry> createBandPartitionPanel(GenericBandPartition partition, String regionName,
            BANDNODE_T set, int x, int y, int width, int height, Color c, boolean treatPartitonAsOverlapSet, JLabel partitionLabel) {

        GenericBluBandPartition<BandEntry> overlapPanel = new GenericBluBandPartition<>(partition, regionName,
                width, height, getGraph(), set, c, treatPartitonAsOverlapSet, partitionLabel);

        getGraph().stretchGraphToFitPanel(x, y, width, height);

        overlapPanel.setBounds(x, y, width, height);

        set.add(overlapPanel, 0);

        return overlapPanel;
    }

    public BandEntry getBand(int level, int setX) {
        return (BandEntry) getConainterAt(level, setX);
    }

    public GenericBluBandPartition<BandEntry> getBandPartition(int level, int setX, int partitionX) {
        return (GenericBluBandPartition<BandEntry>) getContainerPartitionAt(level, setX, partitionX);
    }

    public ClusterEntry getCluster(int level, int setX, int partitionX, int clusterY, int clusterX) {
        return (ClusterEntry) getGroupEntry(level, setX, partitionX, clusterY, clusterX);
    }

    protected JLabel createBandPartitionLabel(TribalAbstractionNetwork tan, HashSet patriarchs, String countString, int width, boolean treatAsBand) {

        Canvas canvas = new Canvas();
        FontMetrics fontMetrics = canvas.getFontMetrics(new Font("SansSerif", Font.BOLD, 14));

        ArrayList<String> bandPatriarchLabels = new ArrayList<>();

        for (Object patriarch : patriarchs) {
            bandPatriarchLabels.add(config.getTextConfiguration().getConceptName(patriarch));
        }

        Collections.sort(bandPatriarchLabels);

        int longestPatriarch = -1;

        for (String patriarchLabel : bandPatriarchLabels) {

            int relNameWidth = fontMetrics.stringWidth(patriarchLabel);

            if (relNameWidth > longestPatriarch) {
                longestPatriarch = relNameWidth;
            }
        }

        bandPatriarchLabels.add(countString);

        if (fontMetrics.stringWidth(countString) > longestPatriarch) {
            longestPatriarch = fontMetrics.stringWidth(countString);
        }

        if (patriarchs.size() > 1) {
            longestPatriarch += fontMetrics.charWidth(',');
        }

        if (treatAsBand) {
            longestPatriarch += fontMetrics.charWidth('+');
        }

        if (longestPatriarch > width) {
            width = longestPatriarch + 4;
        }

        return this.createFittedPartitionLabel(bandPatriarchLabels.toArray(new String[0]), width, fontMetrics);
    }

    public JLabel createPartitionLabel(GenericContainerPartition partition, int width) {

        GenericBandPartition<Cluster> bandPartition = (GenericBandPartition<Cluster>) partition;

        HashSet conceptsInPartition = new HashSet();

        bandPartition.getClusters().forEach( (cluster) -> {
            conceptsInPartition.addAll(cluster.getConcepts());
        });

        int clusterCount = bandPartition.getClusters().size();

        int conceptCount = conceptsInPartition.size();

        String conceptName = config.getTextConfiguration().getConceptTypeName(conceptCount != 1);

        String clusterName = config.getTextConfiguration().getGroupTypeName(clusterCount != 1);

        String countStr = String.format("(%d %s, %d %s", conceptCount, conceptName, clusterCount, clusterName);

        // TODO: Currently we do not partition bands base on inheritance type, so last Arg is always true.
        return this.createBandPartitionLabel(tan, bandPartition.getClusters().get(0).getPatriarchs(), countStr, width, true);
    }

    public void resetLayout() {
         ArrayList<Band> bands = this.getBands();
         
         ArrayList<ArrayList<Band>> bandsByLevel = new ArrayList<>();
         
         ArrayList<Band> level = new ArrayList<>();
         
         Band lastBand = null;
   
         for(Band band : bands) {
             if (lastBand != null && lastBand.getPatriarchs().size() != band.getPatriarchs().size()) {
                 bandsByLevel.add(level);
                 
                 level = new ArrayList<>();
             }
             
             lastBand = band;
             level.add(band);
         }
         
         bandsByLevel.add(level);
         
         int y = 40;
         
         for(ArrayList<Band> levelBands : bandsByLevel) {
             int maxHeight = 0;
             
             for(Band band : levelBands) {
                 BandEntry entry = this.getContainerEntries().get(band.getId());
                 
                 entry.setLocation(entry.getX(), y);
                 
                 if(entry.getHeight() > maxHeight) {
                     maxHeight = entry.getHeight();
                 }
             }
             
             y += maxHeight + GraphLayoutConstants.CONTAINER_ROW_HEIGHT;
         }
    }
}
