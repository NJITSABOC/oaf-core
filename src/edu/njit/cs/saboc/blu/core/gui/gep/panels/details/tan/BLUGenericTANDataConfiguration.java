package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan;

import edu.njit.cs.saboc.blu.core.abn.OverlappingConceptResult;
import edu.njit.cs.saboc.blu.core.abn.tan.TribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.nodes.Band;
import edu.njit.cs.saboc.blu.core.abn.tan.nodes.Cluster;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.data.BLUPartitionedAbNDataConfiguration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public abstract class BLUGenericTANDataConfiguration<
        TAN_T extends TribalAbstractionNetwork,
        BAND_T extends Band,
        CLUSTER_T extends Cluster,
        CONCEPT_T> implements BLUPartitionedAbNDataConfiguration<TAN_T, BAND_T, CLUSTER_T, CONCEPT_T> {

    private final TAN_T tan;

    public BLUGenericTANDataConfiguration(TAN_T tan) {
        this.tan = tan;
    }

    public TAN_T getTribalAbstractionNetwork() {
        return tan;
    }

    @Override
    public ArrayList<CLUSTER_T> getSortedGroupList(BAND_T band) {
        ArrayList<CLUSTER_T> clusters = new ArrayList<>(band.getAllClusters());

        return clusters;
    }

    @Override
    public HashSet<CLUSTER_T> getContainerGroupSet(BAND_T band) {
        return new HashSet<>(band.getAllClusters());
    }

    @Override
    public HashSet<CONCEPT_T> getGroupConceptSet(CLUSTER_T cluster) {
        return cluster.getConcepts();
    }

    @Override
    public HashSet<CONCEPT_T> getContainerOverlappingConcepts(BAND_T band) {
        HashSet<CONCEPT_T> concepts = new HashSet<>();

        HashSet<OverlappingConceptResult<CONCEPT_T, CLUSTER_T>> overlappingResults = this.getContainerOverlappingResults(band);

        overlappingResults.forEach((OverlappingConceptResult<CONCEPT_T, CLUSTER_T> result) -> {
            concepts.add(result.getConcept());
        });

        return concepts;
    }

    @Override
    public HashSet<OverlappingConceptResult<CONCEPT_T, CLUSTER_T>> getContainerOverlappingResults(BAND_T band) {
        HashMap<CONCEPT_T, HashSet<CLUSTER_T>> conceptClusters = new HashMap<>();

        ArrayList<CLUSTER_T> clusters = band.getAllClusters();

        clusters.forEach((CLUSTER_T cluster) -> {
            HashSet<CONCEPT_T> concepts = cluster.getConcepts();

            concepts.forEach((CONCEPT_T c) -> {
                if (!conceptClusters.containsKey(c)) {
                    conceptClusters.put(c, new HashSet<>());
                }

                conceptClusters.get(c).add(cluster);
            });
        });

        HashSet<OverlappingConceptResult<CONCEPT_T, CLUSTER_T>> results = new HashSet<>();

        conceptClusters.forEach((CONCEPT_T c, HashSet<CLUSTER_T> overlaps) -> {
            if (overlaps.size() > 1) {
                results.add(new OverlappingConceptResult<>(c, overlaps));
            }
        });

        return results;
    }

    @Override
    public int getContainerLevel(BAND_T band) {
        return band.getPatriarchs().size();
    }

    @Override
    public ArrayList<CONCEPT_T> getSortedConceptList(CLUSTER_T cluster) {
        ArrayList<CONCEPT_T> clusterConcepts = new ArrayList<>(cluster.getConcepts());

        Collections.sort(clusterConcepts, getConceptNameComparator());

        return clusterConcepts;
    }
    
    protected abstract Comparator<CONCEPT_T> getConceptNameComparator();
}
