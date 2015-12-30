package edu.njit.cs.saboc.blu.core.graph.tan;

import SnomedShared.generic.GenericContainerPartition;
import edu.njit.cs.saboc.blu.core.abn.tan.TribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.nodes.GenericBand;
import edu.njit.cs.saboc.blu.core.abn.tan.nodes.GenericBandPartition;
import edu.njit.cs.saboc.blu.core.abn.tan.nodes.GenericCluster;
import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphEdge;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphGroupLevel;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphLevel;
import edu.njit.cs.saboc.blu.core.graph.layout.BluGraphLayout;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericGroupEntry;
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
public abstract class GenericTANLayout<
        BAND_T extends GenericBand, CLUSTER_T extends GenericCluster, BANDNODE_T extends GenericBluBand, CLUSTERNODE_T extends GenericBluCluster>
        extends BluGraphLayout<BAND_T, BANDNODE_T, CLUSTERNODE_T> {

    private final TribalAbstractionNetwork tan;

    private final BLUGenericTANConfiguration config;

    public GenericTANLayout(BluGraph graph, TribalAbstractionNetwork tan, BLUGenericTANConfiguration config) {
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
        ArrayList<BAND_T> sortedSets = new ArrayList<>();    // Used for generating the graph
        ArrayList<BAND_T> levelSets = new ArrayList<>();     // Used for generating the graph

        ArrayList<BAND_T> tempSets = tan.getBands();

        BAND_T lastSet = null;

        Collections.sort(tempSets, new Comparator<BAND_T>() {    // Sort the areas based on the number of their relationships.

            public int compare(BAND_T a, BAND_T b) {
                return a.getPatriarchs().size() - b.getPatriarchs().size();
            }
        });

        for (BAND_T set : tempSets) {

            if (lastSet != null && lastSet.getPatriarchs().size() != set.getPatriarchs().size()) {
                Collections.sort(levelSets, new Comparator<BAND_T>() {    // Sort the areas based on the number of their relationships.

                    public int compare(BAND_T a, BAND_T b) {

                        int aClusterSize = a.getAllClusters().size();
                        int bClusterSize = b.getAllClusters().size();

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

        Collections.sort(levelSets, new Comparator<BAND_T>() {
            public int compare(BAND_T a, BAND_T b) {
                int aClusterSize = a.getAllClusters().size();
                int bClusterSize = b.getAllClusters().size();

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

    public ArrayList<BAND_T> getBands() {
        return layoutGroupContainers;
    }

    protected CLUSTERNODE_T createClusterPanel(CLUSTER_T p, GenericBluBandPartition parent, int x, int y, int pAreaX, GraphGroupLevel clusterLevel) {
        CLUSTERNODE_T clusterPanel = makeClusterNode(p, graph, parent, pAreaX, clusterLevel, new ArrayList<>());

        //Make sure this panel dimensions will fit on the graph, stretch the graph if necessary
        graph.stretchGraphToFitPanel(x, y, GenericGroupEntry.ENTRY_WIDTH, GenericGroupEntry.ENTRY_HEIGHT);

        //Setup the panel's dimensions, etc.
        clusterPanel.setBounds(x, y, GenericGroupEntry.ENTRY_WIDTH, GenericGroupEntry.ENTRY_HEIGHT);

        parent.add(clusterPanel, 0);

        return clusterPanel;
    }

    protected BANDNODE_T createBandPanel(BAND_T set, int x, int y, int width, int height, Color c, int areaX, GraphLevel parentLevel) {
        BANDNODE_T setPanel = makeBandNode(set, graph, areaX, parentLevel, new Rectangle(x, y, width, height));

        graph.stretchGraphToFitPanel(x, y, width, height);

        setPanel.setBounds(x, y, width, height);
        setPanel.setBackground(c);

        graph.add(setPanel, 0);

        return setPanel;
    }

    protected GenericBluBandPartition<BANDNODE_T> createBandPartitionPanel(GenericBandPartition partition, String regionName,
            BANDNODE_T set, int x, int y, int width, int height, Color c, boolean treatPartitonAsOverlapSet, JLabel partitionLabel) {

        GenericBluBandPartition<BANDNODE_T> overlapPanel = new GenericBluBandPartition<BANDNODE_T>(partition, regionName,
                width, height, graph, set, c, treatPartitonAsOverlapSet, partitionLabel);

        graph.stretchGraphToFitPanel(x, y, width, height);

        overlapPanel.setBounds(x, y, width, height);

        set.add(overlapPanel, 0);

        return overlapPanel;
    }

    public BANDNODE_T getBand(int level, int setX) {
        return (BANDNODE_T) getConainterAt(level, setX);
    }

    public GenericBluBandPartition<BANDNODE_T> getBandPartition(int level, int setX, int partitionX) {
        return (GenericBluBandPartition<BANDNODE_T>) getContainerPartitionAt(level, setX, partitionX);
    }

    public CLUSTERNODE_T getCluster(int level, int setX, int partitionX, int clusterY, int clusterX) {
        return (CLUSTERNODE_T) getGroupEntry(level, setX, partitionX, clusterY, clusterX);
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

        GenericBandPartition<CLUSTER_T> bandPartition = (GenericBandPartition<CLUSTER_T>) partition;

        HashSet conceptsInPartition = new HashSet();

        tan.getClusters().values().forEach((Object o) -> {
            CLUSTER_T cluster = (CLUSTER_T) o;

            conceptsInPartition.addAll(config.getDataConfiguration().getGroupConceptSet(cluster));
        });

        int clusterCount = tan.getClusterCount();

        int conceptCount = conceptsInPartition.size();

        String conceptName = config.getTextConfiguration().getConceptTypeName(conceptCount != 1);

        String clusterName = config.getTextConfiguration().getGroupTypeName(clusterCount != 1);

        String countStr = String.format("(%d %s, %d %s", conceptCount, conceptName, clusterCount, clusterName);

        // TODO: Currently we do not partition bands base on inheritance type, so last Arg is always true.
        return this.createBandPartitionLabel(tan, bandPartition.getClusters().get(0).getPatriarchs(), countStr, width, true);
    }

    protected abstract BANDNODE_T makeBandNode(BAND_T band, BluGraph g, int aX, GraphLevel parent, Rectangle prefBounds);

    protected abstract CLUSTERNODE_T makeClusterNode(CLUSTER_T cluster, BluGraph graph, GenericBluBandPartition r, int pX, GraphGroupLevel parent, ArrayList<GraphEdge> ie);
}
