
package edu.njit.cs.saboc.blu.core.abn.tan.nodes;

import SnomedShared.generic.GenericGroupContainer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public class GenericBand<CONCEPT_T, CLUSTER_T extends GenericCluster> extends GenericGroupContainer<GenericBandPartition<CLUSTER_T>> {

    private final HashSet<CONCEPT_T> patriarchs;

    public GenericBand(int id, HashSet<CONCEPT_T> patriarchs) {
        super(id);
        
        this.patriarchs = patriarchs;
    }

    public HashSet<CONCEPT_T> getPatriarchs() {
        return patriarchs;
    }

    public ArrayList<GenericBandPartition<CLUSTER_T>> getPartitions() {
        return partitions;
    }

    public boolean clusterBelongsIn(CLUSTER_T cluster) {
        return this.patriarchs.equals(cluster.getPatriarchs());
    }

    public void addCluster(CLUSTER_T cluster) {
        if(this.getPartitions().isEmpty()) {
            this.getPartitions().add(new GenericBandPartition<>());
        }
        
        this.getPartitions().get(0).getClusters().add(cluster);
    }

    public ArrayList<CLUSTER_T> getAllClusters() {
        ArrayList<CLUSTER_T> sortedClusters = new ArrayList<>();
        
        this.getPartitions().forEach( (GenericBandPartition<CLUSTER_T> partition) -> { 
            sortedClusters.addAll(partition.getGroups());
        });

        Collections.sort(sortedClusters, new Comparator<CLUSTER_T>() {
            public int compare(CLUSTER_T a, CLUSTER_T b) {
                if (a.getConceptCount() == b.getConceptCount()) {
                    return a.getRoot().getName().compareToIgnoreCase(b.getRoot().getName());
                }

                return a.getConceptCount() > b.getConceptCount() ? -1 : 1;
            }
        });

        return sortedClusters;
    }

    public int getConceptCount() {
        HashSet<CONCEPT_T> allConcepts = new HashSet<>();

        this.getPartitions().forEach((GenericBandPartition<CLUSTER_T> partition) -> {
            partition.getGroups().forEach((CLUSTER_T cluster) -> {
                
                allConcepts.addAll(cluster.getHierarchy().getNodesInHierarchy());
            });
        });

        return allConcepts.size();
    }

    public boolean equals(Object o) {
        if(o instanceof GenericBand) {
            return ((GenericBand)o).patriarchs.equals(this.patriarchs);
        }

        return false;
    }
}