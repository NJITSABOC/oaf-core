package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.cluster;

import SnomedShared.Concept;
import edu.njit.cs.saboc.blu.core.abn.tan.TribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractEntityList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeSummaryPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.BLUGenericTANConfiguration;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;

/**
 *
 * @author Chris O
 */
public class ClusterSummaryPanel<CONCEPT_T, CLUSTER_T extends Cluster> extends NodeSummaryPanel<CLUSTER_T> {

    private final BLUGenericTANConfiguration config;
    
    private final AbstractEntityList<CONCEPT_T> patriarchList;
    
    public ClusterSummaryPanel(BLUGenericTANConfiguration config) {
        this.config = config;
        
        patriarchList = new AbstractEntityList<CONCEPT_T>(new ClusterPatriarchsTableModel<>(config)) {

            @Override
            protected String getBorderText(Optional<ArrayList<CONCEPT_T>> entities) {
                if(entities.isPresent()) {
                    return String.format("Tribes (%d)", entities.get().size());
                } else {
                    return "Tribes";
                }
            }
        };
        
        this.patriarchList.setMinimumSize(new Dimension(-1, 100));
        this.patriarchList.setPreferredSize(new Dimension(-1, 100));
        
        this.add(this.patriarchList);
    }
    
     public void setContents(CLUSTER_T cluster) {
        super.setContents(cluster);
        
        TribalAbstractionNetwork tan = config.getDataConfiguration().getTribalAbstractionNetwork();
        
        ArrayList<CONCEPT_T> patriarchs = new ArrayList<>(cluster.getPatriarchs());
        
        Collections.sort(patriarchs, new Comparator<CONCEPT_T>() {
            public int compare(CONCEPT_T a, CONCEPT_T b) {
                String aName = config.getTextConfiguration().getConceptName(a);
                String bName = config.getTextConfiguration().getConceptName(b);
                
                return aName.compareTo(bName);
            }
        });
        
        this.patriarchList.setContents(patriarchs);
    }
    
    public void clearContents() {
        super.clearContents();
        
        this.patriarchList.clearContents();
    }

    protected String createDescriptionStr(CLUSTER_T cluster) {
        TribalAbstractionNetwork tan = config.getDataConfiguration().getTribalAbstractionNetwork();
        
        String conceptType = config.getTextConfiguration().getConceptTypeName(true).toLowerCase();
        
        String rootName = config.getTextConfiguration().getGroupName(cluster);
        int classCount = cluster.getConceptCount();

        int parentCount = tan.getParentGroups(cluster).size();
        int childCount = tan.getChildGroups(cluster).size();

        ArrayList<CLUSTER_T> descendantClusters = new ArrayList<>(tan.getDescendantGroups(cluster));

        HashSet<Concept> descendantClasses = new HashSet<>();

        descendantClusters.forEach((CLUSTER_T c) -> {
            descendantClasses.addAll(c.getConcepts());
        });
        
        String result = String.format("<html><b>%s</b> is a cluster that summarizes %d %s. It has %d parent clusters and %d child clusters. "
                + "There are a total of %d descendant clusters that summarize %d %s",
                rootName, classCount, conceptType, parentCount, childCount, descendantClusters.size(), descendantClasses.size(), conceptType);
        
        result += "<p><b>Help / Description:</b><br>";
        result += config.getTextConfiguration().getGroupHelpDescriptions(cluster);

        return result;
    }
}
