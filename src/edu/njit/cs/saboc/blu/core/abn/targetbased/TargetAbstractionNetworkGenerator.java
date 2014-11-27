package edu.njit.cs.saboc.blu.core.abn.targetbased;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
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
       
        HashMap<Integer, V> targetGroups = new HashMap<Integer, V>();

        HashMap<T, HashSet<T>> conceptGroups = new HashMap<T, HashSet<T>>();

        HashMap<T, HashSet<T>> groupConcepts = new HashMap<T, HashSet<T>>();
        
        HashMap<T, Integer> groupIds = new HashMap<T, Integer>();

        HashMap<T, Integer> parentCounts = new HashMap<T, Integer>();

        HashSet<T> concepts = targetHierarchy.getNodesInHierarchy();
        
        int nextGroupId = 0;

        for (T concept : concepts) {
            conceptGroups.put(concept, new HashSet<T>());

            if (!roots.contains(concept)) {
                parentCounts.put(concept, targetHierarchy.getParents(concept).size());
            } else {
                parentCounts.put(concept, 0);
                conceptGroups.get(concept).add(concept);

                groupConcepts.put(concept, new HashSet<T>());
                groupConcepts.get(concept).add(concept);
                
                groupIds.put(concept, nextGroupId++);
            }
        }

        Queue<T> queue = new LinkedList<T>();
        queue.addAll(roots);

        while (!queue.isEmpty()) {
            T concept = queue.remove();

            if (!roots.contains(concept)) {
                HashSet<T> parents = targetHierarchy.getParents(concept);

                for (T parent : parents) {
                    HashSet<T> parentGroups = conceptGroups.get(parent);

                    conceptGroups.get(concept).addAll(parentGroups);

                    for (T parentGroupRoot : parentGroups) {
                        groupConcepts.get(parentGroupRoot).add(concept);
                    }

                }
            }

            HashSet<T> children = targetHierarchy.getChildren(concept);

            for (T child : children) {
                if (!roots.contains(child)) {
                    int childParentCount = parentCounts.get(child) - 1;

                    if (childParentCount == 0) {
                        queue.add(child);
                    } else {
                        parentCounts.put(child, childParentCount);
                    }
                }
            }
        }
        
        HashMap<Integer, HashSet<Integer>> groupHierarchy = new HashMap<Integer, HashSet<Integer>>();
        
        for (T root : roots) {
            HashSet<T> parents = targetHierarchy.getParents(root);
            
            int groupId = groupIds.get(root);

            HashSet<Integer> parentGroupIds = new HashSet<Integer>();

            for (T parent : parents) {
                for (T parentGroup : conceptGroups.get(parent)) {
                    int parentGroupId = groupIds.get(parentGroup);
                    
                    parentGroupIds.add(parentGroupId);
                    
                    if(!groupHierarchy.containsKey(parentGroupId)) {
                        groupHierarchy.put(parentGroupId, new HashSet<Integer>());
                    }
                    
                    groupHierarchy.get(parentGroupId).add(groupId);
                }
            }
            
            targetGroups.put(groupId, createGroup(groupId, root, groupConcepts.get(root).size(), parentGroupIds));
        }

        
        for (Map.Entry<T, HashSet<T>> entries : sourceConceptTargets.entrySet()) {
            T source = entries.getKey();

            HashSet<T> targets = entries.getValue();

            for (T target : targets) {
                for (T groupRoot : conceptGroups.get(target)) {
                    //targetGroups.get(groupRoot).addDrug(target);
                }
            }
        }
        
        return createTargetAbstractionNetwork(targetGroups, groupHierarchy);

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
    
    public abstract HashSet<GenericRelationship<W, T>> getConceptRelationships(T concept);
    
    public abstract SingleRootedHierarchy<T> getTargetHierarchy(T root);
    
    public abstract V createGroup(int id, T root, int conceptCount, HashSet<Integer> parentIds);
    
    protected abstract U createTargetAbstractionNetwork(HashMap<Integer, V> groups, HashMap<Integer, HashSet<Integer>> groupHierarchy);
}
