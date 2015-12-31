package edu.njit.cs.saboc.blu.core.abn.tan;

import edu.njit.cs.saboc.blu.core.abn.GroupHierarchy;
import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.nodes.GenericBand;
import edu.njit.cs.saboc.blu.core.abn.tan.nodes.GenericCluster;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.MultiRootedHierarchy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Chris
 */
public class TribalAbstractionNetwork<CONCEPT_T, BAND_T extends GenericBand<CONCEPT_T, CLUSTER_T>, CLUSTER_T extends GenericCluster>
        extends PartitionedAbstractionNetwork<BAND_T, CLUSTER_T> {
    
    private final String tanName;

    private final ArrayList<CLUSTER_T> patriarchClusters;
    
    private final MultiRootedHierarchy<CONCEPT_T> sourceHierarchy;

    public TribalAbstractionNetwork(
            String tanName,
            ArrayList<BAND_T> bands,
            HashMap<Integer, CLUSTER_T> clusters,
            GroupHierarchy<CLUSTER_T> clusterHierarchy,
            ArrayList<CLUSTER_T> patriarchs,
            MultiRootedHierarchy<CONCEPT_T> sourceHierarchy) {

        super(bands, clusters, clusterHierarchy);
        
        this.tanName = tanName;

        this.patriarchClusters = patriarchs;
        
        this.sourceHierarchy = sourceHierarchy;
    }
    
    public String getTANName() {
        return tanName;
    }
    
    public ArrayList<CLUSTER_T> getPatriarchClusters() {
        return patriarchClusters;
    }
    
    public MultiRootedHierarchy<CONCEPT_T> getSourceConceptHierarchy() {
        return sourceHierarchy;
    }

    public ArrayList<BAND_T> getBands() {
        return super.getContainers();
    }

    public int getClusterCount() {
        return getGroupCount();
    }

    public int getBandCount() {
        return getContainerCount();
    }

    public HashMap<Integer, CLUSTER_T> getClusters() {
        return super.getGroups();
    }

    public CLUSTER_T getRootCluster() {
        return patriarchClusters.get(0);
    }
    
    public CLUSTER_T getRootGroup(){
        return getRootCluster();
    }
    
    public CLUSTER_T getClusterFromRootConceptId(long rootConceptId) {
        return getGroupFromRootConceptId(rootConceptId);
    }
    
    public HashSet<CLUSTER_T> getNonOverlappingPatriarchClusters() {
        HashSet<CLUSTER_T> nonoverlappingPatriarchClusters = new HashSet<>();
                
        patriarchClusters.forEach( (CLUSTER_T patriarchCluster) -> {
            boolean found = false;
            
            CONCEPT_T root = (CONCEPT_T)patriarchCluster.getHierarchy().getRoot();
            
            for(CLUSTER_T cluster : groups.values()) {
                HashSet<CONCEPT_T> patriarchs = cluster.getPatriarchs();
                
                for(CONCEPT_T patriarch : patriarchs) {
                    if(patriarch.equals(root)) {
                        found = true;
                        break;
                    }
                }
                
                if(found) {
                    break;
                }
            }
            
            if(!found) {
                nonoverlappingPatriarchClusters.add(patriarchCluster);
            }
        });
        
        return nonoverlappingPatriarchClusters;
        
    }
}