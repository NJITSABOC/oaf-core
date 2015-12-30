package edu.njit.cs.saboc.blu.core.abn.tan;

import edu.njit.cs.saboc.blu.core.abn.GenericParentGroupInfo;
import edu.njit.cs.saboc.blu.core.abn.GroupHierarchy;
import edu.njit.cs.saboc.blu.core.abn.tan.nodes.GenericBand;
import edu.njit.cs.saboc.blu.core.abn.tan.nodes.GenericCluster;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.MultiRootedHierarchy;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Stack;

/**
 *
 * @author Chris O
 */
public abstract class TribalAbstractionNetworkGenerator<
        CONCEPT_T,
        HIERARCHY_T extends SingleRootedHierarchy<CONCEPT_T, HIERARCHY_T>,
        TAN_T extends TribalAbstractionNetwork<CONCEPT_T, BAND_T, CLUSTER_T>,
        BAND_T extends GenericBand<CONCEPT_T, CLUSTER_T>,
        CLUSTER_T extends GenericCluster<CONCEPT_T, HIERARCHY_T, CLUSTER_T>> {

    public TAN_T createTANFromConceptHierarchy(
            HIERARCHY_T hierarchy) {

        HashSet<CONCEPT_T> patriarchs = hierarchy.getChildren(hierarchy.getRoot());

        MultiRootedHierarchy<CONCEPT_T> multiRootedHierarchy = new MultiRootedHierarchy<CONCEPT_T>(patriarchs) {

            @Override
            public SingleRootedHierarchy<CONCEPT_T, ? extends SingleRootedHierarchy> getSubhierarchyRootedAt(CONCEPT_T root) {
                HIERARCHY_T hierarchy = createHierarchy(root);
                hierarchy.addAllHierarchicalRelationships(hierarchy);
                
                return hierarchy.getSubhierarchyRootedAt(root);
            }
        };
        
        multiRootedHierarchy.addAllHierarchicalRelationships(hierarchy);

        return deriveTANFromMultiRootedHierarchy(multiRootedHierarchy);
    }

    public TAN_T deriveTANFromMultiRootedHierarchy(
            MultiRootedHierarchy<CONCEPT_T> hierarchy) {
        
        System.out.println(" --------- TAN DEBUG ------------");
        System.out.println("Total Nodes: " + hierarchy.getNodesInHierarchy().size());

        HashSet<CONCEPT_T> patriarchs = hierarchy.getRoots();
        
        System.out.println("Patriachs: " + patriarchs.size());

        // The set of tribes a given concept belongs to
        HashMap<CONCEPT_T, HashSet<CONCEPT_T>> conceptTribes = new HashMap<>();

        HashMap<CONCEPT_T, Integer> remainingParentCount = new HashMap<>();

        Stack<CONCEPT_T> pendingConcepts = new Stack<>();

        pendingConcepts.addAll(patriarchs);

        for (CONCEPT_T c : hierarchy.getNodesInHierarchy()) {
            if (patriarchs.contains(c)) {
                remainingParentCount.put(c, 0);
            } else {
                remainingParentCount.put(c, hierarchy.getParents(c).size());
            }
        }

        while (!pendingConcepts.isEmpty()) {
            CONCEPT_T c = pendingConcepts.pop();

            if (patriarchs.contains(c)) {
                conceptTribes.put(c, new HashSet<>(Arrays.asList(c)));
            } else {
                HashSet<CONCEPT_T> parents = hierarchy.getParents(c);

                HashSet<CONCEPT_T> tribalSet = new HashSet<>();

                for (CONCEPT_T parent : parents) {
                    tribalSet.addAll(conceptTribes.get(parent));
                }

                conceptTribes.put(c, tribalSet);
            }

            HashSet<CONCEPT_T> children = hierarchy.getChildren(c);

            for (CONCEPT_T child : children) {
                int parentCount = remainingParentCount.get(child) - 1;

                if (parentCount == 0) {
                    pendingConcepts.push(child);
                } else {
                    remainingParentCount.put(child, parentCount);
                }
            }
        }

        // The set of cluster root concepts in the hierarchy
        HashSet<CONCEPT_T> roots = new HashSet<>();
        roots.addAll(patriarchs);

        // Based on the tribes a given concept's parent(s) belong to, 
        // determine if the concept is a root
        for (Map.Entry<CONCEPT_T, HashSet<CONCEPT_T>> entry : conceptTribes.entrySet()) {
            if (roots.contains(entry.getKey())) {
                continue;
            }

            HashSet<CONCEPT_T> myCluster = entry.getValue(); // The patriarchs that have this concept as a descendent

            HashSet<CONCEPT_T> parents = hierarchy.getParents(entry.getKey()); // Get parents of this concept

            boolean isRoot = true;

            for (CONCEPT_T parent : parents) { // For each parent
                HashSet<CONCEPT_T> parentTribes = conceptTribes.get(parent);

                if (parentTribes.equals(myCluster)) { // If a parent has the same group of tribes
                    isRoot = false; // Then this is not a root concept
                    break;
                }
            }

            if (isRoot) { // If concept is a root
                roots.add(entry.getKey()); // Add it to roots
            }
        }

        int id = 1;

        // Stores the hierarchy of concepts summarized by each cluster
        final HashMap<CONCEPT_T, HIERARCHY_T> clusters = new HashMap<>();

        // Stores the list of clusters each concept belongs to
        final HashMap<CONCEPT_T, HashSet<CONCEPT_T>> conceptClusters = new HashMap<>();

        // Integer ID for cluster (legacy support)
        final HashMap<CONCEPT_T, Integer> clusterIds = new HashMap<>();

        for (CONCEPT_T root : roots) { // For each root
            clusters.put(root, createHierarchy(root)); // Create a new cluster

            clusterIds.put(root, id++);

            HashSet<CONCEPT_T> rootTribalSet = conceptTribes.get(root);

            Stack<CONCEPT_T> stack = new Stack<>();

            stack.add(root);

            while (!stack.isEmpty()) { // Traverse down DAG and add concepts to cluster
                CONCEPT_T concept = stack.pop();

                HashSet<CONCEPT_T> conceptTribalSet = conceptTribes.get(concept);

                if (rootTribalSet.equals(conceptTribalSet)) {
                    if (!conceptClusters.containsKey(concept)) {
                        conceptClusters.put(concept, new HashSet<>());
                    }

                    conceptClusters.get(concept).add(root); // Set concept as belonging to current header

                    HashSet<CONCEPT_T> children = hierarchy.getChildren(concept);

                    for (CONCEPT_T child : children) { // Process all children
                        if (stack.contains(child) || roots.contains(child) || !conceptTribes.get(child).equals(rootTribalSet)) {
                            continue;
                        }

                        clusters.get(root).addIsA(child, concept);

                        stack.add(child);
                    }
                }
            }
        }

        HashMap<Integer, CLUSTER_T> hierarchyClusters = new HashMap<>();
        HashMap<Integer, HashSet<Integer>> clusterHierarchy = new HashMap<>();

        ArrayList<CLUSTER_T> patriarchClusters = new ArrayList<>();
        
        System.out.println("Roots: " + roots.size());

        for (CONCEPT_T root : roots) {
            HashSet<CONCEPT_T> rootsTribes = conceptTribes.get(root);
            HashSet<CONCEPT_T> parents = hierarchy.getParents(root);

            HashSet<Integer> parentClusters = new HashSet<>();

            for (CONCEPT_T parent : parents) {
                // When a single rooted hierarchy is used the IS As to the root are still there. Skip them.
                if (!conceptTribes.containsKey(parent)) {
                    continue;
                }

                HashSet<CONCEPT_T> rootParentClusters = conceptClusters.get(parent);

                for (CONCEPT_T parentCluster : rootParentClusters) {
                    int parentClusterId = clusterIds.get(parentCluster);

                    parentClusters.add(parentClusterId);

                    if (!clusterHierarchy.containsKey(parentClusterId)) {
                        clusterHierarchy.put(parentClusterId, new HashSet<Integer>());
                    }

                    clusterHierarchy.get(parentClusterId).add(clusterIds.get(root));
                }
            }

            CLUSTER_T cluster = createCluster(
                    clusterIds.get(root),
                    clusters.get(root),
                    parentClusters,
                    rootsTribes);

            hierarchyClusters.put(clusterIds.get(root), cluster);

            if (rootsTribes.size() == 1) {
                patriarchClusters.add(cluster);
            }
        }

        HashMap<HashSet<CONCEPT_T>, BAND_T> bands = new HashMap<>();

        int bandId = 1;

        for (CONCEPT_T root : roots) { // For every root
            HashSet<CONCEPT_T> rootTribes = conceptTribes.get(root); // Get tribes of this root

            if (!bands.containsKey(rootTribes)) { // Create a new band if one does not exist
                bands.put(rootTribes, createBand(bandId++, rootTribes));
            }

            bands.get(rootTribes).addCluster(hierarchyClusters.get(clusterIds.get(root)));
        }


        GroupHierarchy<CLUSTER_T> convertedHierarchy = new GroupHierarchy<>(new HashSet<>(patriarchClusters));

        hierarchyClusters.values().forEach((CLUSTER_T cluster) -> {
            HashSet<CONCEPT_T> parents = hierarchy.getParents(cluster.getHierarchy().getRoot());

            HashSet<GenericParentGroupInfo<CONCEPT_T, CLUSTER_T>> parentInformation = new HashSet<>();

            parents.forEach((CONCEPT_T parent) -> {

                if (conceptClusters.containsKey(parent)) {
                    HashSet<CONCEPT_T> parentClusterRoots = conceptClusters.get(parent);

                    parentClusterRoots.forEach((CONCEPT_T parentClusterRoot) -> {
                        CLUSTER_T parentCluster = hierarchyClusters.get(clusterIds.get(parentClusterRoot));

                        parentInformation.add(new GenericParentGroupInfo<>(parent, parentCluster));
                    });
                }
            });

            cluster.setParentClusterInfo(parentInformation);

            HashSet<Integer> parentIds = cluster.getParentIds();

            parentIds.forEach((Integer parentId) -> {
                convertedHierarchy.addIsA(cluster, hierarchyClusters.get(parentId));
            });
        });

        return createTribalAbstractionNetwork(new ArrayList<>(bands.values()), hierarchyClusters, convertedHierarchy, patriarchClusters, hierarchy);
    }
    
    protected abstract HIERARCHY_T createHierarchy(CONCEPT_T root);
    
    protected abstract TAN_T createTribalAbstractionNetwork(
            ArrayList<BAND_T> bands,
            HashMap<Integer, CLUSTER_T> clusters,
            GroupHierarchy<CLUSTER_T> clusterHierarchy,
            ArrayList<CLUSTER_T> patriarchs,
            MultiRootedHierarchy<CONCEPT_T> sourceHierarchy);
    
    protected abstract BAND_T createBand(int id, HashSet<CONCEPT_T> patriarchs);
    
    protected abstract CLUSTER_T createCluster(
            int id, 
            HIERARCHY_T conceptHierarchy,
            HashSet<Integer> parentClusters, 
            HashSet<CONCEPT_T> patriarchs);
}
