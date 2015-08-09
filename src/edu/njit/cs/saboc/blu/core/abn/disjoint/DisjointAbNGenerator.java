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
 * @author Chris O
 */
public abstract class DisjointAbNGenerator<
        PARENTABN_T extends AbstractionNetwork,
        GROUP_T extends GenericConceptGroup, 
        CONCEPT_T, 
        HIERARCHY_T extends SingleRootedHierarchy<CONCEPT_T, HIERARCHY_T>,
        DISJOINTGROUP_T extends DisjointGenericConceptGroup<GROUP_T, CONCEPT_T, HIERARCHY_T, DISJOINTGROUP_T>,
        DISJOINTABN_T extends DisjointAbstractionNetwork<PARENTABN_T, GROUP_T, CONCEPT_T, HIERARCHY_T, DISJOINTGROUP_T>> {
    
    public DISJOINTABN_T generateDisjointAbstractionNetwork(PARENTABN_T parentAbN, HashSet<GROUP_T> groups, 
            MultiRootedHierarchy<CONCEPT_T> conceptHierarchy) {
        
        HashSet<CONCEPT_T> roots = new HashSet<>();
        
        // A mapping of concepts to the group(s) they belong to.
        HashMap<CONCEPT_T, HashSet<GROUP_T>> conceptGroupMap = new HashMap<>();
        
        HashSet<GROUP_T> identifiedOverlappingGroups = new HashSet<>();
        
        for (GROUP_T group : groups) {
            CONCEPT_T root = this.getGroupRoot(group);

            roots.add(root);

            Stack<CONCEPT_T> stack = new Stack<>();
            stack.push(root);

            Set<CONCEPT_T> processedConcepts = new HashSet<>();

            // Traverse this partial-area's hierarchy, mark which concepts belong to the partial-area
            while (!stack.isEmpty()) {
                CONCEPT_T c = stack.pop();

                if (!conceptGroupMap.containsKey(c)) {
                    conceptGroupMap.put(c, new HashSet<>());
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
        
        int maxOverlap = 0;

        for (Map.Entry<CONCEPT_T, HashSet<GROUP_T>> entry : conceptGroupMap.entrySet()) {
            if (entry.getValue().size() > maxOverlap) {
                maxOverlap = entry.getValue().size();
            }
        }

        HashSet<CONCEPT_T> allArticulationPoints = new HashSet<>();
        
        for (int c = 2; c <= maxOverlap; c++) {
            
            HashMap<CONCEPT_T, HashSet<GROUP_T>> overlappingConcepts = new HashMap<>();

            for (Map.Entry<CONCEPT_T, HashSet<GROUP_T>> entry : conceptGroupMap.entrySet()) {
                if (entry.getValue().size() == c) {
                    overlappingConcepts.put(entry.getKey(), entry.getValue());
                }
            }

            HashSet<CONCEPT_T> conceptSet = new HashSet<>(overlappingConcepts.keySet());

            HashSet<CONCEPT_T> copyConceptSet;

            HashSet<CONCEPT_T> articulationPoints = new HashSet<>();

            for (CONCEPT_T concept : overlappingConcepts.keySet()) {
                HashSet<CONCEPT_T> parents = conceptHierarchy.getParents(concept);

                copyConceptSet = new HashSet<>(conceptSet);
                copyConceptSet.retainAll(parents);

                if (copyConceptSet.isEmpty()) {
                    articulationPoints.add(concept);
                } else {
                    continue;
                }

                allArticulationPoints.addAll(articulationPoints);
            }
        }
        
        HashSet<CONCEPT_T> allRoots = new HashSet<>();

        for (CONCEPT_T root : allArticulationPoints) {
            HashSet<GROUP_T> conceptGroups = conceptGroupMap.get(root);

            for (GROUP_T group : conceptGroups) {
                allRoots.add(this.getGroupRoot(group));
            }
        }

        allRoots.addAll(allArticulationPoints);
        
        HashMap<CONCEPT_T, Integer> parentCounts = new HashMap<>();
        
        for(CONCEPT_T node : conceptHierarchy.getNodesInHierarchy()) {
            
            if(allArticulationPoints.contains(node)) {
                parentCounts.put(node, 0);
            } else {
                parentCounts.put(node, conceptHierarchy.getParents(node).size());
            }
        }
        
        Queue<CONCEPT_T> queue = new LinkedList<>();
        
        queue.addAll(allRoots);
        
        HashMap<CONCEPT_T, HashSet<CONCEPT_T>> reachableFrom = new HashMap<>();
        
        while(!queue.isEmpty()) {
            CONCEPT_T node = queue.remove();
            
            HashSet<CONCEPT_T> conceptRoots = new HashSet<>();
            
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
        
        HashMap<CONCEPT_T, Integer> disjointGroupIds = new HashMap<>();
        HashMap<CONCEPT_T, HIERARCHY_T> disjointGroupConceptHierarchy = new HashMap<>();
        HashMap<CONCEPT_T, Set<CONCEPT_T>> disjointGroupParents = new HashMap<>();
        HashMap<CONCEPT_T, Set<CONCEPT_T>> disjointGroupChildren = new HashMap<>();
        
        int groupId = 0;
        
        for (GROUP_T group : identifiedOverlappingGroups) {
            CONCEPT_T root = getGroupRoot(group);
            
            disjointGroupIds.put(root, groupId++);
            disjointGroupConceptHierarchy.put(root, createConceptHierarchy(root));

            disjointGroupParents.put(root, new HashSet<>());
            disjointGroupChildren.put(root, new HashSet<>());
        }

        for (CONCEPT_T root : allRoots) {
            if (!disjointGroupIds.containsKey(root)) {
                disjointGroupIds.put(root, groupId++);
                disjointGroupConceptHierarchy.put(root, createConceptHierarchy(root));

                disjointGroupParents.put(root, new HashSet<>());
                disjointGroupChildren.put(root, new HashSet<>());
            }
        }

        // Add all concepts to their respective disjoint partial areas
        for (CONCEPT_T root : disjointGroupIds.keySet()) {

            Stack<CONCEPT_T> stack = new Stack<>();
            stack.add(root);

            Set<CONCEPT_T> processedConcepts = new HashSet<>();

            while (!stack.isEmpty()) {
                CONCEPT_T concept = stack.pop();

                processedConcepts.add(concept);

                HashSet<CONCEPT_T> children = conceptHierarchy.getChildren(concept);

                for (CONCEPT_T child : children) {
                    if (allRoots.contains(child)) {
                        disjointGroupParents.get(child).add(root);
                        disjointGroupChildren.get(root).add(child);
                    } else {
                        if (!stack.contains(child) && !processedConcepts.contains(child)) {
                            stack.add(child);
                            
                            disjointGroupConceptHierarchy.get(root).addIsA(child, concept);
                        }
                    }
                }
            }
        }
        
        HashMap<Integer, DISJOINTGROUP_T> disjointGroups = new HashMap<>();
        HashMap<Integer, HashSet<Integer>> disjointGroupHierarchy = new HashMap<>();
        
        for(int disjointGroupId : disjointGroupIds.values()) {
            disjointGroupHierarchy.put(disjointGroupId, new HashSet<>());
        }
        
        for(CONCEPT_T disjointGroupRoot : disjointGroupIds.keySet()) {
            
            int currentGroupId = disjointGroupIds.get(disjointGroupRoot);
            HIERARCHY_T groupHierarchy = disjointGroupConceptHierarchy.get(disjointGroupRoot);
            HashSet<GROUP_T> overlapsIn = conceptGroupMap.get(disjointGroupRoot);
            
            HashSet<Integer> parentIds = new HashSet<>();
            
            Set<CONCEPT_T> parentGroupRoots = disjointGroupParents.get(disjointGroupRoot);
            
            parentGroupRoots.forEach((CONCEPT_T parentGroupRoot) -> {
                int parentGroupId = disjointGroupIds.get(parentGroupRoot);
                
                disjointGroupHierarchy.get(parentGroupId).add(currentGroupId);
                
                parentIds.add(parentGroupId);
            });

            disjointGroups.put(currentGroupId, createDisjointGroup(currentGroupId, groupHierarchy, parentIds, overlapsIn));
        }
        
        return createDisjointAbN(parentAbN, disjointGroups, disjointGroupHierarchy, maxOverlap, groups, identifiedOverlappingGroups);
    }
    
    protected abstract HIERARCHY_T createConceptHierarchy(CONCEPT_T root);
    
    protected abstract CONCEPT_T getDisjointGroupRoot(DISJOINTGROUP_T group);
    
    protected abstract CONCEPT_T getGroupRoot(GROUP_T group);
    
    protected abstract DISJOINTGROUP_T createDisjointGroup(int id, 
            HIERARCHY_T conceptHierarchy, 
            HashSet<Integer> parentIds,
            HashSet<GROUP_T> overlapsIn);
    
    protected abstract DISJOINTABN_T createDisjointAbN(
            PARENTABN_T abstractionNetwork, 
            HashMap<Integer, DISJOINTGROUP_T> disjointGroups, 
            HashMap<Integer, HashSet<Integer>> groupHierarchy,
            int levels,
            HashSet<GROUP_T> allGroups,
            HashSet<GROUP_T> overlappingGroups);
    
}
