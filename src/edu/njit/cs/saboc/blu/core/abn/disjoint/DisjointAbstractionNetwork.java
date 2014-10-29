package edu.njit.cs.saboc.blu.core.abn.disjoint;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.nodes.DisjointGenericConceptGroup;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.MultiRootedHierarchy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 *
 * @author Chris
 */
public abstract class DisjointAbstractionNetwork <T extends AbstractionNetwork, V extends GenericConceptGroup, U, 
        Y extends DisjointGenericConceptGroup<V, U, Y>> {
    
    protected HashSet<V> allGroups;
    
    protected HashSet<V> overlappingGroups;
    
    protected T abstractionNetwork;
    
    protected MultiRootedHierarchy<Y> disjointGroupHierarchy;
    
    protected int levels;
    
    public DisjointAbstractionNetwork(T abstractionNetwork, HashSet<V> groups, MultiRootedHierarchy<U> conceptHierarchy) {
        
        this.abstractionNetwork = abstractionNetwork;
        this.allGroups = groups;
        
        HashSet<U> roots = new HashSet<U>();
        
        // A mapping of concepts to the group(s) they belong to.
        HashMap<U, HashSet<V>> conceptGroupMap = new HashMap<U, HashSet<V>>();
        
        HashSet<V> identifiedOverlappingGroups = new HashSet<V>();
        
        for (V group : groups) {
            U root = this.getGroupRoot(group);

            roots.add(root);

            Stack<U> stack = new Stack<U>();
            stack.push(root);

            Set<U> processedConcepts = new HashSet<U>();

            // Traverse this partial-area's hierarchy, mark which concepts belong to the partial-area
            while (!stack.isEmpty()) {
                U c = stack.pop();

                if (!conceptGroupMap.containsKey(c)) {
                    conceptGroupMap.put(c, new HashSet<V>());
                }

                conceptGroupMap.get(c).add(group);
                
                if(conceptGroupMap.get(c).size() > 1) {
                    identifiedOverlappingGroups.addAll(conceptGroupMap.get(c));
                }

                processedConcepts.add(c);

                HashSet<U> children = conceptHierarchy.getChildren(c);

                for (U child : children) {
                    if (!stack.contains(child) && !processedConcepts.contains(child)) {
                        stack.push(child);
                    }
                }
            }
        }
        
        this.overlappingGroups = identifiedOverlappingGroups;
        
        int maxOverlap = 0;

        for (Map.Entry<U, HashSet<V>> entry : conceptGroupMap.entrySet()) {
            if (entry.getValue().size() > maxOverlap) {
                maxOverlap = entry.getValue().size();
            }
        }
        
        this.levels = maxOverlap;

        HashSet<U> allArticulationPoints = new HashSet<U>();
        
        for (int c = 2; c <= maxOverlap; c++) {
            
            HashMap<U, HashSet<V>> overlappingConcepts = new HashMap<U, HashSet<V>>();

            for (Map.Entry<U, HashSet<V>> entry : conceptGroupMap.entrySet()) {
                if (entry.getValue().size() == c) {
                    overlappingConcepts.put(entry.getKey(), entry.getValue());
                }
            }

            HashSet<U> conceptSet = new HashSet<U>(overlappingConcepts.keySet());

            HashSet<U> copyConceptSet;

            HashSet<U> articulationPoints = new HashSet<U>();

            for (U concept : overlappingConcepts.keySet()) {
                HashSet<U> parents = conceptHierarchy.getParents(concept);

                copyConceptSet = new HashSet<U>(conceptSet);
                copyConceptSet.retainAll(parents);

                if (copyConceptSet.isEmpty()) {
                    articulationPoints.add(concept);
                } else {
                    continue;
                }

                allArticulationPoints.addAll(articulationPoints);
            }
        }
        
        HashSet<U> allRoots = new HashSet<U>();

        for (U root : allArticulationPoints) {
            HashSet<V> conceptGroups = conceptGroupMap.get(root);

            for (V group : conceptGroups) {
                allRoots.add(this.getGroupRoot(group));
            }
        }

        allRoots.addAll(allArticulationPoints);
        
        // Used to find complex overlapping roots
        HashMap<U, HashSet<U>> reachableFromRoots = new HashMap<U, HashSet<U>>();

        for (U root : allRoots) {
            Stack<U> stack = new Stack<U>();
            stack.add(root);

            Set<U> processedConcepts = new HashSet<U>();

            while (!stack.isEmpty()) {
                U concept = stack.pop();

                if (!reachableFromRoots.containsKey(concept)) {
                    reachableFromRoots.put(concept, new HashSet<U>());
                }

                reachableFromRoots.get(concept).add(root);
                processedConcepts.add(concept);

                HashSet<U> children = conceptHierarchy.getChildren(concept);

                for (U child : children) {
                    if (!allRoots.contains(child)
                            && !stack.contains(child)
                            && !processedConcepts.contains(child)) {

                        stack.add(child);
                    }
                }
            }
        }
        
        for (Map.Entry<U, HashSet<U>> entry : reachableFromRoots.entrySet()) {
            if (!allRoots.contains(entry.getKey()) && !roots.contains(entry.getKey())) {
                
                boolean parentsMatch = true;

                HashSet<U> disjointRoots = entry.getValue();
                HashSet<U> parents = conceptHierarchy.getParents(entry.getKey());

                for (U parent : parents) {
                    HashSet<U> parentRoots = reachableFromRoots.get(parent);

                    if (!parentRoots.equals(disjointRoots)) {
                        parentsMatch = false;
                        break;
                    }
                }

                if (!parentsMatch) {
                    allRoots.add(entry.getKey());
                }
            }
        }
        
         // The list of Disjoint Groups Created
        HashSet<Y> disjointGroups = new HashSet<Y>();

        // A map from the root of Disjoint Group to the Disjoint Group Itself
        HashMap<U, Y> rootMap = new HashMap<U, Y>();

        // HashMap containing mapping from a concept to the disjoint pareas it exists in
        HashMap<U, Y> conceptToDisjointGroup = new HashMap<U, Y>();

        HashSet<Y> disjointGroupHierarchyRoots = new HashSet<Y>();
        
        for(V group : identifiedOverlappingGroups) {
            Y disjointGroup = createDisjointGroup(getGroupRoot(group), conceptGroupMap.get(getGroupRoot(group)));
            
            disjointGroupHierarchyRoots.add(disjointGroup);
            disjointGroups.add(disjointGroup);
            rootMap.put(this.getGroupRoot(group), disjointGroup);
        }
        
        disjointGroupHierarchy = createDisjointGroupHierarchy(disjointGroupHierarchyRoots);
        
        // For all of the roots, create a disjoint partial area
        for (U root : allRoots) {
            if (!rootMap.containsKey(root)) {
                Y disjointGroup = createDisjointGroup(root, conceptGroupMap.get(root));
                disjointGroups.add(disjointGroup);
                rootMap.put(root, disjointGroup);
            }
        }

        // Add all concepts to their respective disjoint partial areas
        for (Y disjointGroup : disjointGroups) {
            U root = disjointGroup.getRoot();

            disjointGroups.add(disjointGroup);

            Stack<U> stack = new Stack<U>();
            stack.add(root);

            Set<U> processedConcepts = new HashSet<U>();

            while (!stack.isEmpty()) {
                U concept = stack.pop();
                
                conceptToDisjointGroup.put(concept, disjointGroup);

                processedConcepts.add(concept);

                HashSet<U> children = conceptHierarchy.getChildren(concept);

                for (U child : children) {
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
            HashSet<U> parents = conceptHierarchy.getParents(disjointGroup.getRoot());
            
            for(U parent : parents) {
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
    
    protected abstract U getGroupRoot(V group);
    
    protected abstract Y createDisjointGroup(U root, HashSet<V> overlaps);
    
    protected abstract MultiRootedHierarchy<Y> createDisjointGroupHierarchy(HashSet<Y> roots);
}
