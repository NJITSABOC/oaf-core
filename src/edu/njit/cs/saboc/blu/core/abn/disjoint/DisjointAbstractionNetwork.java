package edu.njit.cs.saboc.blu.core.abn.disjoint;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.nodes.DisjointGenericConceptGroup;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.MultiRootedHierarchy;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 *
 * @author Chris
 */
public abstract class DisjointAbstractionNetwork <
        T extends AbstractionNetwork,
        V extends GenericConceptGroup, 
        CONCEPT_T, 
        HIERARCHY_T extends SingleRootedHierarchy<CONCEPT_T, HIERARCHY_T>,
        Y extends DisjointGenericConceptGroup<V, CONCEPT_T, HIERARCHY_T, Y>> {
    
    protected HashSet<V> allGroups;
    
    protected HashSet<V> overlappingGroups;
    
    protected T abstractionNetwork;
    
    protected MultiRootedHierarchy<Y> disjointGroupHierarchy;
    
    protected int levels;
    
    public DisjointAbstractionNetwork(T abstractionNetwork, HashSet<V> groups, MultiRootedHierarchy<CONCEPT_T> conceptHierarchy) {
        
        this.abstractionNetwork = abstractionNetwork;
        this.allGroups = groups;
        
        HashSet<CONCEPT_T> roots = new HashSet<CONCEPT_T>();
        
        // A mapping of concepts to the group(s) they belong to.
        HashMap<CONCEPT_T, HashSet<V>> conceptGroupMap = new HashMap<CONCEPT_T, HashSet<V>>();
        
        HashSet<V> identifiedOverlappingGroups = new HashSet<V>();
        
        for (V group : groups) {
            CONCEPT_T root = this.getGroupRoot(group);

            roots.add(root);

            Stack<CONCEPT_T> stack = new Stack<CONCEPT_T>();
            stack.push(root);

            Set<CONCEPT_T> processedConcepts = new HashSet<CONCEPT_T>();

            // Traverse this partial-area's hierarchy, mark which concepts belong to the partial-area
            while (!stack.isEmpty()) {
                CONCEPT_T c = stack.pop();

                if (!conceptGroupMap.containsKey(c)) {
                    conceptGroupMap.put(c, new HashSet<V>());
                }

                conceptGroupMap.get(c).add(group);
                
                if(conceptGroupMap.get(c).size() > 1) {
                    identifiedOverlappingGroups.addAll(conceptGroupMap.get(c));
                }

                processedConcepts.add(c);

                HashSet<CONCEPT_T> children = conceptHierarchy.getChildren(c);

                for (CONCEPT_T child : children) {
                    if (!stack.contains(child) && !processedConcepts.contains(child)) {
                        stack.push(child);
                    }
                }
            }
        }
        
        this.overlappingGroups = identifiedOverlappingGroups;
        
        int maxOverlap = 0;

        for (Map.Entry<CONCEPT_T, HashSet<V>> entry : conceptGroupMap.entrySet()) {
            if (entry.getValue().size() > maxOverlap) {
                maxOverlap = entry.getValue().size();
            }
        }
        
        this.levels = maxOverlap;

        HashSet<CONCEPT_T> allArticulationPoints = new HashSet<CONCEPT_T>();
        
        for (int c = 2; c <= maxOverlap; c++) {
            
            HashMap<CONCEPT_T, HashSet<V>> overlappingConcepts = new HashMap<CONCEPT_T, HashSet<V>>();

            for (Map.Entry<CONCEPT_T, HashSet<V>> entry : conceptGroupMap.entrySet()) {
                if (entry.getValue().size() == c) {
                    overlappingConcepts.put(entry.getKey(), entry.getValue());
                }
            }

            HashSet<CONCEPT_T> conceptSet = new HashSet<CONCEPT_T>(overlappingConcepts.keySet());

            HashSet<CONCEPT_T> copyConceptSet;

            HashSet<CONCEPT_T> articulationPoints = new HashSet<CONCEPT_T>();

            for (CONCEPT_T concept : overlappingConcepts.keySet()) {
                HashSet<CONCEPT_T> parents = conceptHierarchy.getParents(concept);

                copyConceptSet = new HashSet<CONCEPT_T>(conceptSet);
                copyConceptSet.retainAll(parents);

                if (copyConceptSet.isEmpty()) {
                    articulationPoints.add(concept);
                } else {
                    continue;
                }

                allArticulationPoints.addAll(articulationPoints);
            }
        }
        
        HashSet<CONCEPT_T> allRoots = new HashSet<CONCEPT_T>();

        for (CONCEPT_T root : allArticulationPoints) {
            HashSet<V> conceptGroups = conceptGroupMap.get(root);

            for (V group : conceptGroups) {
                allRoots.add(this.getGroupRoot(group));
            }
        }

        allRoots.addAll(allArticulationPoints);
        
        HashMap<CONCEPT_T, Integer> parentCounts = new HashMap<CONCEPT_T, Integer>();
        
        for(CONCEPT_T node : conceptHierarchy.getNodesInHierarchy()) {
            
            if(allArticulationPoints.contains(node)) {
                parentCounts.put(node, 0);
            } else {
                parentCounts.put(node, conceptHierarchy.getParents(node).size());
            }
        }
        
        Queue<CONCEPT_T> queue = new LinkedList<CONCEPT_T>();
        
        queue.addAll(allRoots);
        
        HashMap<CONCEPT_T, HashSet<CONCEPT_T>> reachableFrom = new HashMap<CONCEPT_T, HashSet<CONCEPT_T>>();
        
        while(!queue.isEmpty()) {
            CONCEPT_T node = queue.remove();
            
            HashSet<CONCEPT_T> conceptRoots = new HashSet<CONCEPT_T>();
            
            HashSet<CONCEPT_T> parents = conceptHierarchy.getParents(node);
            
            if (allArticulationPoints.contains(node)) {
                conceptRoots.add(node);
            } else {
                for (CONCEPT_T parent : parents) {
                    conceptRoots.addAll(reachableFrom.get(parent));
                }
                
                for(CONCEPT_T parent : parents) {
                    if(!conceptRoots.equals(reachableFrom.get(parent))) {
                        conceptRoots.clear();
                        conceptRoots.add(node);
                        
                        allRoots.add(node);
                        
                        break;
                    }
                }
            }
            
            reachableFrom.put(node, conceptRoots);

            HashSet<CONCEPT_T> children = conceptHierarchy.getChildren(node);
                       
            for(CONCEPT_T child : children) {
                int childParentCount = parentCounts.get(child);
                
                if(childParentCount - 1 == 0) {
                    queue.add(child);
                } else {
                    parentCounts.put(child, childParentCount - 1);
                }
            }
        }
        
        
         // The list of Disjoint Groups Created
        HashSet<Y> disjointGroups = new HashSet<Y>();

        // A map from the root of Disjoint Group to the Disjoint Group Itself
        HashMap<CONCEPT_T, Y> rootMap = new HashMap<CONCEPT_T, Y>();

        // HashMap containing mapping from a concept to the disjoint group it exists in
        HashMap<CONCEPT_T, Y> conceptToDisjointGroup = new HashMap<CONCEPT_T, Y>();

        HashSet<Y> disjointGroupHierarchyRoots = new HashSet<Y>();
        
        for(V group : identifiedOverlappingGroups) {
            Y disjointGroup = createDisjointGroup(getGroupRoot(group), conceptGroupMap.get(getGroupRoot(group)));
            
            disjointGroupHierarchyRoots.add(disjointGroup);
            disjointGroups.add(disjointGroup);
            rootMap.put(this.getGroupRoot(group), disjointGroup);
        }
        
        disjointGroupHierarchy = createDisjointGroupHierarchy(disjointGroupHierarchyRoots);
        
        // For all of the roots, create a disjoint partial area
        for (CONCEPT_T root : allRoots) {
            if (!rootMap.containsKey(root)) {
                Y disjointGroup = createDisjointGroup(root, conceptGroupMap.get(root));
                disjointGroups.add(disjointGroup);
                rootMap.put(root, disjointGroup);
            }
        }

        // Add all concepts to their respective disjoint partial areas
        for (Y disjointGroup : disjointGroups) {
            CONCEPT_T root = disjointGroup.getRoot();

            disjointGroups.add(disjointGroup);

            Stack<CONCEPT_T> stack = new Stack<CONCEPT_T>();
            stack.add(root);

            Set<CONCEPT_T> processedConcepts = new HashSet<CONCEPT_T>();

            while (!stack.isEmpty()) {
                CONCEPT_T concept = stack.pop();
                
                if(conceptToDisjointGroup.containsKey(concept)) {
                    System.err.println("ERROR (concept already in group): " + concept);
                }

                conceptToDisjointGroup.put(concept, disjointGroup);

                processedConcepts.add(concept);

                HashSet<CONCEPT_T> children = conceptHierarchy.getChildren(concept);

                for (CONCEPT_T child : children) {
                    if (allRoots.contains(child)) {
                        disjointGroupHierarchy.addIsA(rootMap.get(child), disjointGroup);
                    } else {
                        if (!stack.contains(child) && !processedConcepts.contains(child)) {
                            stack.add(child);
                            disjointGroup.addConcept(child, concept);
                        }
                    }
                }
            }
        }
        
        for(Y disjointGroup : disjointGroups) {
            HashSet<CONCEPT_T> parents = conceptHierarchy.getParents(disjointGroup.getRoot());
            
            for(CONCEPT_T parent : parents) {
                Y parentGroup = conceptToDisjointGroup.get(parent);
                
                disjointGroup.registerParent(parent, parentGroup);
            }
        }
    }
    
    public T getAbstractionNetwork() {
        return abstractionNetwork;
    }
    
    public HashSet<V> getOverlappingGroups() {
        return overlappingGroups;
    }
    
    public HashSet<Y> getDisjointGroups() {
        return disjointGroupHierarchy.getNodesInHierarchy();
    }
    
    public MultiRootedHierarchy<Y> getHierarchy() {
        return disjointGroupHierarchy;
    }
    
    public int getLevelCount() {
        return levels;
    }
    
    protected abstract CONCEPT_T getGroupRoot(V group);
    
    protected abstract Y createDisjointGroup(CONCEPT_T root, HashSet<V> overlaps);
    
    protected abstract MultiRootedHierarchy<Y> createDisjointGroupHierarchy(HashSet<Y> roots);
}
