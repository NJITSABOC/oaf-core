package edu.njit.cs.saboc.blu.core.abn.tan;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 *
 * @author Chris O
 */
public class TribalAbstractionNetworkGenerator {
        
    public ClusterTribalAbstractionNetwork deriveTANFromSingleRootedHierarchy(
            Hierarchy<? extends Concept> sourceHierarchy,
            TANFactory factory) {
        
        Hierarchy<Concept> hierarchy = (Hierarchy<Concept>)(Hierarchy<?>)sourceHierarchy;
        
        hierarchy = new Hierarchy<>(
                hierarchy.getChildren(sourceHierarchy.getRoot()), 
                hierarchy);
        
        return deriveTANFromMultiRootedHierarchy(hierarchy, factory);
    }

    public ClusterTribalAbstractionNetwork deriveTANFromMultiRootedHierarchy(
            Hierarchy<? extends Concept> sourceHierarchy,
            TANFactory factory) {
        
        Hierarchy<Concept> hierarchy = (Hierarchy<Concept>)(Hierarchy<?>)sourceHierarchy;

        Set<Concept> patriarchs = hierarchy.getRoots();

        // The set of tribes a given concept belongs to
        HashMap<Concept, Set<Concept>> conceptTribes = new HashMap<>();

        HashMap<Concept, Integer> remainingParentCount = new HashMap<>();

        Stack<Concept> pendingConcepts = new Stack<>();

        pendingConcepts.addAll(patriarchs);

        sourceHierarchy.getNodes().forEach((c) -> {
            if (patriarchs.contains(c)) {
                remainingParentCount.put(c, 0);
            } else {
                remainingParentCount.put(c, hierarchy.getParents(c).size());
            }
        });

        while (!pendingConcepts.isEmpty()) {
            Concept c = pendingConcepts.pop();

            if (patriarchs.contains(c)) {
                conceptTribes.put(c, new HashSet<>(Arrays.asList(c)));
            } else {
                Set<Concept> parents = hierarchy.getParents(c);

                Set<Concept> tribalSet = new HashSet<>();

                parents.forEach((parent) -> {
                    tribalSet.addAll(conceptTribes.get(parent));
                });

                conceptTribes.put(c, tribalSet);
            }

            Set<Concept> children = hierarchy.getChildren(c);

            children.forEach((child) -> {
                int parentCount = remainingParentCount.get(child) - 1;

                if (parentCount == 0) {
                    pendingConcepts.push(child);
                } else {
                    remainingParentCount.put(child, parentCount);
                }
            });
        }

        // The set of cluster root concepts in the hierarchy
        HashSet<Concept> roots = new HashSet<>();
        roots.addAll(patriarchs);

        // Based on the tribes a given concept's parent(s) belong to, 
        // determine if the concept is a root
        for (Map.Entry<Concept, Set<Concept>> entry : conceptTribes.entrySet()) {
            if (roots.contains(entry.getKey())) {
                continue;
            }

            Set<Concept> myCluster = entry.getValue(); // The patriarchs that have this concept as a descendent

            Set<Concept> parents = hierarchy.getParents(entry.getKey()); // Get parents of this concept

            boolean isRoot = true;

            for (Concept parent : parents) { // For each parent
                Set<Concept> parentTribes = conceptTribes.get(parent);

                if (parentTribes.equals(myCluster)) { // If a parent has the same group of tribes
                    isRoot = false; // Then this is not a root concept
                    break;
                }
            }

            if (isRoot) { // If concept is a root
                roots.add(entry.getKey()); // Add it to roots
            }
        }

        // Stores the hierarchy of concepts summarized by each cluster
        final HashMap<Concept, Hierarchy<Concept>> clusterConceptHierarchy = new HashMap<>();

        // Stores the list of clusters each concept belongs to
        final HashMap<Concept, Set<Concept>> conceptClusters = new HashMap<>();


        for (Concept root : roots) { // For each root
            clusterConceptHierarchy.put(root, new Hierarchy<>(root)); // Create a new cluster

            Set<Concept> rootTribalSet = conceptTribes.get(root);

            Stack<Concept> stack = new Stack<>();

            stack.add(root);

            while (!stack.isEmpty()) { // Traverse down DAG and add concepts to cluster
                Concept concept = stack.pop();

                Set<Concept> conceptTribalSet = conceptTribes.get(concept);

                if (rootTribalSet.equals(conceptTribalSet)) {
                    if (!conceptClusters.containsKey(concept)) {
                        conceptClusters.put(concept, new HashSet<>());
                    }

                    conceptClusters.get(concept).add(root); // Set concept as belonging to current header

                    Set<Concept> children = hierarchy.getChildren(concept);

                    for (Concept child : children) { // Process all children
                        if (stack.contains(child) || roots.contains(child) || !conceptTribes.get(child).equals(rootTribalSet)) {
                            continue;
                        }

                        clusterConceptHierarchy.get(root).addEdge(child, concept);

                        stack.add(child);
                    }
                }
            }
        }

        Set<Cluster> patriarchClusters = new HashSet<>();
        
        // Create Cluster objects
        
        Map<Set<Concept>, Set<Cluster>> clustersByPatriarchs = new HashMap<>();
        Map<Concept, Cluster> clustersByRoot = new HashMap<>();

        roots.forEach((root) -> {
            Set<Concept> rootTribes = conceptTribes.get(root);

            Cluster cluster = new Cluster(clusterConceptHierarchy.get(root), rootTribes);
            
            if(rootTribes.size() == 1) {
                patriarchClusters.add(cluster);
            }
            
            if(!clustersByPatriarchs.containsKey(rootTribes)) {
                clustersByPatriarchs.put(rootTribes, new HashSet<>());
            }
            
            clustersByPatriarchs.get(rootTribes).add(cluster);
            clustersByRoot.put(root, cluster);
        });
        
        // Build Cluster Hierarchy
        
        Hierarchy<Cluster> clusterHierarchy = new Hierarchy<>(patriarchClusters);
        
        clustersByRoot.values().forEach( (cluster) -> {
            if (!clusterHierarchy.getRoots().contains(cluster)) {
                Set<Concept> rootParents = hierarchy.getParents(cluster.getRoot());
                
                rootParents.forEach( (parent) -> {
                    
                   Set<Concept> parentClusterRoots = conceptClusters.get(parent);
                   
                   parentClusterRoots.forEach( (clusterRoot) -> {
                       Cluster parentCluster = clustersByRoot.get(clusterRoot);
                       clusterHierarchy.addEdge(cluster, parentCluster);
                   });
                 
                });
            }
        });

        // Build bands
        
        Map<Set<Concept>, Band> bandsByPatriarchs = new HashMap<>();
        
        Set<Band> patriarchBands = new HashSet<>();
        
        clustersByPatriarchs.forEach( (bandPatriarchs, clusters) -> {
            Band band = new Band(clusters, bandPatriarchs);
            
            bandsByPatriarchs.put(band.getPatriarchs(), band);
            
            if(band.getPatriarchs().size() == 1) {
                patriarchBands.add(band);
            }
        });
        
        // Build Band hierarchy
        
        Hierarchy<Band> bandHierarchy = new Hierarchy<>(patriarchBands);
        
        bandsByPatriarchs.values().forEach( (band) -> {
            if(!bandHierarchy.getRoots().contains(band)) {
                band.getClusters().forEach( (cluster) -> {
                   Set<Cluster> parentClusters = clusterHierarchy.getParents(cluster);
                   
                   parentClusters.forEach( (parentCluster) -> {
                       Band parentBand = bandsByPatriarchs.get(parentCluster.getPatriarchs());
                       
                       bandHierarchy.addEdge(band, parentBand);
                   });
                });
            }
        });
        
        BandTribalAbstractionNetwork bandTAN = factory.createBandTAN(bandHierarchy, hierarchy);
        ClusterTribalAbstractionNetwork tan = factory.createClusterTAN(bandTAN, clusterHierarchy, hierarchy);

        return tan;
    }
    
    
    public <T extends Cluster> ClusterTribalAbstractionNetwork<T> createTANFromClusters(
            Hierarchy<T> clusterHierarchy,
            Hierarchy<Concept> sourceHierarchy,
            TANFactory factory) {
                
        // For now assuming only one cluster is picked as a root
        Hierarchy<Concept> conceptHierarchy = new Hierarchy<>(clusterHierarchy.getRoot().getRoot());
        
        Map<Set<Concept>, Set<Cluster>> clusterBands = new HashMap<>();
        
        clusterHierarchy.getNodes().forEach((cluster) -> {
            conceptHierarchy.addAllHierarchicalRelationships(cluster.getHierarchy());
            
            if(!clusterBands.containsKey(cluster.getPatriarchs())) {
                clusterBands.put(cluster.getPatriarchs(), new HashSet<>());
            }
            
            clusterBands.get(cluster.getPatriarchs()).add(cluster);
        });
        
        clusterHierarchy.getNodes().forEach( (cluster) -> {
            Concept root = cluster.getRoot();
            
            sourceHierarchy.getParents(root).forEach( (parent) -> {
               if(conceptHierarchy.contains(parent)) {
                   conceptHierarchy.addEdge(root, parent);
               } 
            });
        });

        Map<Set<Concept>, Band> bandsByPatriarchs = new HashMap<>();
        
        clusterBands.forEach( (patriarchs, clusters) -> {
            Band band = new Band(clusters, patriarchs);
            bandsByPatriarchs.put(patriarchs, band);
        });
        
        Set<Band> rootBands = new HashSet<>();
        
        clusterHierarchy.getRoots().forEach((cluster) -> {
            rootBands.add(bandsByPatriarchs.get(cluster.getPatriarchs()));
        });
        
        Hierarchy<Band> bandHierarchy = new Hierarchy<>(rootBands);
        
        bandsByPatriarchs.values().forEach((band) -> {
            
            band.getClusters().forEach( (cluster) -> {
                
                Set<T> parentClusters = clusterHierarchy.getParents((T)cluster);
                
                parentClusters.forEach((parentCluster) -> {
                    bandHierarchy.addEdge(band, bandsByPatriarchs.get(parentCluster.getPatriarchs()));
                });
            });
        });
        
        
        BandTribalAbstractionNetwork bandTAN = factory.createBandTAN(bandHierarchy, conceptHierarchy);
        ClusterTribalAbstractionNetwork<T> tan = factory.createClusterTAN(bandTAN, clusterHierarchy, conceptHierarchy);
        
        return tan;
    }
}
