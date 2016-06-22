package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.band;

import edu.njit.cs.saboc.blu.core.abn.tan.Band;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractNodeOptionsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry.PartitionedNodeConceptEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.BLUGenericTANConfiguration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public class BandDetailsPanel<
        BAND_T extends Band, 
        CLUSTER_T extends Cluster, 
        CONCEPT_T> extends NodeDetailsPanel<BAND_T, PartitionedNodeConceptEntry<CONCEPT_T, CLUSTER_T>> {
    
    private final BLUGenericTANConfiguration config;

    public BandDetailsPanel(AbstractNodeOptionsPanel<BAND_T> bandOptionsPanel, BLUGenericTANConfiguration config) {

        super(new BandSummaryPanel<BAND_T, CLUSTER_T>(config), 
                bandOptionsPanel, 
                new BandConceptList(config));
        
        this.config = config;
    }

    @Override
    protected ArrayList<PartitionedNodeConceptEntry<CONCEPT_T, CLUSTER_T>> getSortedConceptList(BAND_T band) {
        
        HashMap<CONCEPT_T, HashSet<CLUSTER_T>> conceptClusters = new HashMap<>();
        
        ArrayList<CLUSTER_T> clusters = band.getAllClusters();
        
        clusters.forEach((CLUSTER_T cluster) -> {
            HashSet<CONCEPT_T> pareaClses = cluster.getConcepts();
            
            pareaClses.forEach((CONCEPT_T c) -> {
                if(!conceptClusters.containsKey(c)) {
                    conceptClusters.put(c, new HashSet<>());
                }
                
                conceptClusters.get(c).add(cluster);
            });
        });
        
        ArrayList<PartitionedNodeConceptEntry<CONCEPT_T, CLUSTER_T>> areaEntries = new ArrayList<>();
        
        conceptClusters.forEach((CONCEPT_T c, HashSet<CLUSTER_T> conceptsClusters) -> {
            areaEntries.add(new PartitionedNodeConceptEntry<>(c, conceptsClusters));
        });
        
        Collections.sort(areaEntries, new Comparator<PartitionedNodeConceptEntry<CONCEPT_T, CLUSTER_T>>() {
            public int compare(PartitionedNodeConceptEntry<CONCEPT_T, CLUSTER_T> a, PartitionedNodeConceptEntry<CONCEPT_T, CLUSTER_T> b) {
                String aName = config.getTextConfiguration().getConceptName(a.getConcept());
                String bName = config.getTextConfiguration().getConceptName(b.getConcept());
                
                return aName.compareTo(bName);
            }
        });
        
        return areaEntries;
    }
}
