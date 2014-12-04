package edu.njit.cs.saboc.blu.core.abn.targetbased;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author Chris O
 */
public abstract class TargetAbstractionNetworkGenerator<T, V extends TargetGroup, U extends TargetAbstractionNetwork<V>, W> {
    
    public U deriveTargetAbstractionNetwork(HashSet<T> sourceConcepts, W relationshipType, T targetHierarchyRoot) {
        
        HashMap<T, HashSet<T>> relationshipTargets = new HashMap<T, HashSet<T>>();
           
        for(T concept : sourceConcepts) {
            relationshipTargets.put(concept, getTargetsOfRelationships(
                    getConceptRelationships(concept), relationshipType)
            );
        }
        
        HashSet<T> uniqueTargets = new HashSet<T>();
        
        for(HashSet<T> targets : relationshipTargets.values()) {
            uniqueTargets.addAll(targets);
        }
        
        SingleRootedHierarchy<T> targetHierarchy = getTargetHierarchy(targetHierarchyRoot);
        
        HashMap<T, HashSet<T>> lowestNontargetAncestors = getLowestNontargetIngredients(uniqueTargets, targetHierarchy);
        
        HashSet<T> targetGroupRoots = new HashSet<T>();
        
        for(HashSet<T> lowestNontargetConcepts : lowestNontargetAncestors.values()) {
            targetGroupRoots.addAll(lowestNontargetConcepts);
        }
        
        return generateTargetAbstractionNetwork(targetHierarchy, targetGroupRoots, relationshipTargets);
    }
    
    private HashMap<T, HashSet<T>> getLowestNontargetIngredients(HashSet<T> targets, SingleRootedHierarchy<T> hierarchy) {
        
        HashMap<T, HashSet<T>> lowestNontargetConcepts = new HashMap<T, HashSet<T>>();
        
        for(T target : targets) {
            HashSet<T> lowestAncestorsWithoutIncoming = new HashSet<T>();
            
            Stack<T> stack = new Stack<T>();
            
            stack.addAll(hierarchy.getParents(target));
            
            while(!stack.isEmpty()) {
                T concept = stack.pop();
                
                if(targets.contains(concept)) {
                    for(T parent : hierarchy.getParents(concept)) {
                        if(!stack.contains(parent) && !targets.contains(parent)) {
                            stack.add(parent);
                        }
                    }
                } else {
                    lowestAncestorsWithoutIncoming.add(concept);
                }
            }
            
            lowestNontargetConcepts.put(target, lowestAncestorsWithoutIncoming);
        }
        
        return lowestNontargetConcepts;
    }
    
    private U generateTargetAbstractionNetwork(
            SingleRootedHierarchy<T> targetHierarchy, 
            HashSet<T> roots, 
            HashMap<T, HashSet<T>> sourceConceptTargets) {
       
        HashMap<T, HashSet<T>> conceptsGroups = new HashMap<T, HashSet<T>>();

        HashMap<T, SingleRootedHierarchy<T>> conceptsInGroup = new HashMap<T, SingleRootedHierarchy<T>>();
        
        HashMap<T, HashSet<Integer>> parentIds = new HashMap<T, HashSet<Integer>>();
        
        HashMap<T, Integer> groupIds = new HashMap<T, Integer>();

        HashMap<Integer, HashSet<Integer>> groupHierarchy = new HashMap<Integer, HashSet<Integer>>();

        HashMap<T, Integer> parentCounts = new HashMap<T, Integer>();

        HashSet<T> concepts = targetHierarchy.getNodesInHierarchy();
        
        HashMap<T, HashSet<T>> incomingRelSourceConcepts = new HashMap<T, HashSet<T>>();
                
        int nextGroupId = 0;

        for (T concept : concepts) {
            conceptsGroups.put(concept, new HashSet<T>());
            
            parentCounts.put(concept, targetHierarchy.getParents(concept).size());
            
            incomingRelSourceConcepts.put(concept, new HashSet<T>());

            if (roots.contains(concept)) {
                conceptsGroups.get(concept).add(concept);

                conceptsInGroup.put(concept, createGroupHierarchy(concept));
                
                int groupId = nextGroupId++;
                groupIds.put(concept, groupId);
                groupHierarchy.put(groupId, new HashSet<Integer>());
                parentIds.put(concept, new HashSet<Integer>());
            }
        }
        
        for (Entry<T, HashSet<T>> sourceConceptEntry : sourceConceptTargets.entrySet()) {
            for(T target : sourceConceptEntry.getValue()) {
                incomingRelSourceConcepts.get(target).add(sourceConceptEntry.getKey());
            }
        }

        Queue<T> queue = new LinkedList<T>();
        roots.add(targetHierarchy.getRoot()); // Root of hierarchy is always a root
        
        queue.add(targetHierarchy.getRoot()); // Start from the root of the hierarchy...

        while (!queue.isEmpty()) {
            T concept = queue.remove();

            HashSet<T> parents = targetHierarchy.getParents(concept);
            
            if (!roots.contains(concept)) {
                for (T parent : parents) {
                    HashSet<T> parentGroups = conceptsGroups.get(parent);

                    conceptsGroups.get(concept).addAll(parentGroups);

                    for (T parentGroupRoot : parentGroups) {
                        conceptsInGroup.get(parentGroupRoot).addIsA(concept, parent);
                    }
                }
                
            } else {
                int groupId = groupIds.get(concept);

                for (T parent : parents) {
                    HashSet<T> parentGroups = conceptsGroups.get(parent);
                    
                    for (T parentGroupRoot : parentGroups) {
                        int parentGroupId = groupIds.get(parentGroupRoot);

                        parentIds.get(concept).add(parentGroupId);

                        groupHierarchy.get(parentGroupId).add(groupId);
                    }
                }
            }

            HashSet<T> children = targetHierarchy.getChildren(concept);

            for (T child : children) {
                int childParentCount = parentCounts.get(child) - 1;

                if (childParentCount == 0) {
                    queue.add(child);
                } else {
                    parentCounts.put(child, childParentCount);
                }
            }
        }
        
        HashMap<Integer, V> targetGroups = new HashMap<Integer, V>();
        
        // Create the target groups...
        for (T root : roots) {
            int groupId = groupIds.get(root);
            HashSet<Integer> groupParentIds = parentIds.get(root);
            SingleRootedHierarchy<T> groupConceptHierarchy = conceptsInGroup.get(root);

            targetGroups.put(groupId, createGroup(groupId, root, groupParentIds, groupConceptHierarchy, incomingRelSourceConcepts));
        }

        V rootGroup = targetGroups.get(groupIds.get(targetHierarchy.getRoot()));
        
        return createTargetAbstractionNetwork(rootGroup, targetGroups, groupHierarchy);
    }
    
    private HashSet<T> getTargetsOfRelationships(HashSet<GenericRelationship<W, T>> relationships, W type) {
        HashSet<T> targetConcepts = new HashSet<T>();
        
        for(GenericRelationship<W,T> relationship : relationships) {
            if(relationship.getType().equals(type)) {
                targetConcepts.add(relationship.getTarget());
            }
        }
        
        return targetConcepts;
    }
    
    protected abstract HashSet<GenericRelationship<W, T>> getConceptRelationships(T concept);
    
    protected abstract SingleRootedHierarchy<T> getTargetHierarchy(T root);
    
    protected abstract V createGroup(int id, T root, HashSet<Integer> parentIds, 
            SingleRootedHierarchy<T> groupHierarchy, HashMap<T, HashSet<T>> incomingRelSources);
    
    protected abstract U createTargetAbstractionNetwork(V rootGroup, HashMap<Integer, V> groups, HashMap<Integer, HashSet<Integer>> groupHierarchy);
    
    protected abstract SingleRootedHierarchy<T> createGroupHierarchy(T root);
}
