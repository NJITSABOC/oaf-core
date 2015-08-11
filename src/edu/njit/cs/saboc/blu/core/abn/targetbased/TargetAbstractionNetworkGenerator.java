package edu.njit.cs.saboc.blu.core.abn.targetbased;

import edu.njit.cs.saboc.blu.core.abn.GroupHierarchy;
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
public abstract class TargetAbstractionNetworkGenerator<
        CONCEPT_T, 
        REL_T,
        HIERARCHY_T extends SingleRootedHierarchy<CONCEPT_T, HIERARCHY_T>,
        GROUP_T extends TargetGroup, 
        TARGETABN_T extends TargetAbstractionNetwork<GROUP_T, TARGETABN_T>> {
    
    public TARGETABN_T deriveTargetAbstractionNetwork(HashSet<CONCEPT_T> sourceConcepts, REL_T relationshipType, CONCEPT_T targetHierarchyRoot) {
        
        HashMap<CONCEPT_T, HashSet<CONCEPT_T>> relationshipTargets = new HashMap<CONCEPT_T, HashSet<CONCEPT_T>>();
           
        for(CONCEPT_T concept : sourceConcepts) {
            relationshipTargets.put(concept, getTargetsOfRelationships(
                    getConceptRelationships(concept), relationshipType)
            );
        }
        
        HashSet<CONCEPT_T> uniqueTargets = new HashSet<CONCEPT_T>();
        
        for(HashSet<CONCEPT_T> targets : relationshipTargets.values()) {
            uniqueTargets.addAll(targets);
        }
        
        HIERARCHY_T targetHierarchy = getTargetHierarchy(targetHierarchyRoot);
        
        HashMap<CONCEPT_T, HashSet<CONCEPT_T>> lowestNontargetAncestors = getLowestNontargetIngredients(uniqueTargets, targetHierarchy);
        
        HashSet<CONCEPT_T> targetGroupRoots = new HashSet<CONCEPT_T>();
        
        for(HashSet<CONCEPT_T> lowestNontargetConcepts : lowestNontargetAncestors.values()) {
            targetGroupRoots.addAll(lowestNontargetConcepts);
        }
        
        return generateTargetAbstractionNetwork(targetHierarchy, targetGroupRoots, relationshipTargets);
    }
    
    private HashMap<CONCEPT_T, HashSet<CONCEPT_T>> getLowestNontargetIngredients(HashSet<CONCEPT_T> targets, HIERARCHY_T hierarchy) {
        
        HashMap<CONCEPT_T, HashSet<CONCEPT_T>> lowestNontargetConcepts = new HashMap<CONCEPT_T, HashSet<CONCEPT_T>>();
        
        for(CONCEPT_T target : targets) {
            HashSet<CONCEPT_T> lowestAncestorsWithoutIncoming = new HashSet<CONCEPT_T>();
            
            Stack<CONCEPT_T> stack = new Stack<CONCEPT_T>();
            
            stack.addAll(hierarchy.getParents(target));
            
            while(!stack.isEmpty()) {
                CONCEPT_T concept = stack.pop();
                
                if(targets.contains(concept)) {
                    for(CONCEPT_T parent : hierarchy.getParents(concept)) {
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
    
    private TARGETABN_T generateTargetAbstractionNetwork(
            HIERARCHY_T targetHierarchy, 
            HashSet<CONCEPT_T> roots, 
            HashMap<CONCEPT_T, HashSet<CONCEPT_T>> sourceConceptTargets) {
       
        HashMap<CONCEPT_T, HashSet<CONCEPT_T>> conceptsGroups = new HashMap<CONCEPT_T, HashSet<CONCEPT_T>>();

        HashMap<CONCEPT_T, HIERARCHY_T> conceptsInGroup = new HashMap<CONCEPT_T, HIERARCHY_T>();
        
        HashMap<CONCEPT_T, HashSet<Integer>> parentIds = new HashMap<CONCEPT_T, HashSet<Integer>>();
        
        HashMap<CONCEPT_T, Integer> groupIds = new HashMap<CONCEPT_T, Integer>();

        HashMap<CONCEPT_T, Integer> parentCounts = new HashMap<CONCEPT_T, Integer>();

        HashSet<CONCEPT_T> concepts = targetHierarchy.getNodesInHierarchy();
        
        HashMap<CONCEPT_T, HashSet<CONCEPT_T>> incomingRelSourceConcepts = new HashMap<CONCEPT_T, HashSet<CONCEPT_T>>();
                
        roots.add(targetHierarchy.getRoot());
        
        int nextGroupId = 0;

        for (CONCEPT_T concept : concepts) {
            conceptsGroups.put(concept, new HashSet<CONCEPT_T>());
            
            parentCounts.put(concept, targetHierarchy.getParents(concept).size());
            
            incomingRelSourceConcepts.put(concept, new HashSet<CONCEPT_T>());

            if (roots.contains(concept)) {
                conceptsGroups.get(concept).add(concept);

                conceptsInGroup.put(concept, createGroupHierarchy(concept));
                
                int groupId = nextGroupId++;
                groupIds.put(concept, groupId);
                parentIds.put(concept, new HashSet<Integer>());
            }
        }
        
        for (Entry<CONCEPT_T, HashSet<CONCEPT_T>> sourceConceptEntry : sourceConceptTargets.entrySet()) {
            for(CONCEPT_T target : sourceConceptEntry.getValue()) {
                incomingRelSourceConcepts.get(target).add(sourceConceptEntry.getKey());
            }
        }

        Queue<CONCEPT_T> queue = new LinkedList<CONCEPT_T>();
        roots.add(targetHierarchy.getRoot()); // Root of hierarchy is always a root
        
        queue.add(targetHierarchy.getRoot()); // Start from the root of the hierarchy...

        while (!queue.isEmpty()) {
            CONCEPT_T concept = queue.remove();

            HashSet<CONCEPT_T> parents = targetHierarchy.getParents(concept);
            
            if (!roots.contains(concept)) {
                for (CONCEPT_T parent : parents) {
                    HashSet<CONCEPT_T> parentGroups = conceptsGroups.get(parent);

                    conceptsGroups.get(concept).addAll(parentGroups);

                    for (CONCEPT_T parentGroupRoot : parentGroups) {
                        conceptsInGroup.get(parentGroupRoot).addIsA(concept, parent);
                    }
                }
                
            } else {
                int groupId = groupIds.get(concept);

                for (CONCEPT_T parent : parents) {
                    HashSet<CONCEPT_T> parentGroups = conceptsGroups.get(parent);
                    
                    for (CONCEPT_T parentGroupRoot : parentGroups) {
                        int parentGroupId = groupIds.get(parentGroupRoot);

                        parentIds.get(concept).add(parentGroupId);
                    }
                }
            }

            HashSet<CONCEPT_T> children = targetHierarchy.getChildren(concept);

            for (CONCEPT_T child : children) {
                int childParentCount = parentCounts.get(child) - 1;

                if (childParentCount == 0) {
                    queue.add(child);
                } else {
                    parentCounts.put(child, childParentCount);
                }
            }
        }
        
        HashMap<Integer, GROUP_T> targetGroups = new HashMap<Integer, GROUP_T>();
        
        // Create the target groups...
        for (CONCEPT_T root : roots) {
            int groupId = groupIds.get(root);
            HashSet<Integer> groupParentIds = parentIds.get(root);
            HIERARCHY_T groupConceptHierarchy = conceptsInGroup.get(root);

            targetGroups.put(groupId, createGroup(groupId, root, groupParentIds, groupConceptHierarchy, incomingRelSourceConcepts));
        }

        GROUP_T rootGroup = targetGroups.get(groupIds.get(targetHierarchy.getRoot()));
        
        GroupHierarchy<GROUP_T> groupHierarchy = new GroupHierarchy<>(rootGroup);
        
        targetGroups.values().forEach((GROUP_T group) -> {
            HashSet<Integer> parentGroupIds = group.getParentIds();
            
            parentGroupIds.forEach((Integer parentId) -> {
                groupHierarchy.addIsA(group, targetGroups.get(parentId));
            });
        });
        
        return createTargetAbstractionNetwork(rootGroup, targetGroups, groupHierarchy);
    }
    
    private HashSet<CONCEPT_T> getTargetsOfRelationships(HashSet<GenericRelationship<REL_T, CONCEPT_T>> relationships, REL_T type) {
        HashSet<CONCEPT_T> targetConcepts = new HashSet<CONCEPT_T>();
        
        for(GenericRelationship<REL_T,CONCEPT_T> relationship : relationships) {
            if(relationship.getType().equals(type)) {
                targetConcepts.add(relationship.getTarget());
            }
        }
        
        return targetConcepts;
    }
    
    protected abstract HashSet<GenericRelationship<REL_T, CONCEPT_T>> getConceptRelationships(CONCEPT_T concept);
    
    protected abstract HIERARCHY_T getTargetHierarchy(CONCEPT_T root);
    
    protected abstract GROUP_T createGroup(int id, CONCEPT_T root, HashSet<Integer> parentIds, 
            HIERARCHY_T groupHierarchy, HashMap<CONCEPT_T, HashSet<CONCEPT_T>> incomingRelSources);
    
    protected abstract TARGETABN_T createTargetAbstractionNetwork(GROUP_T rootGroup, HashMap<Integer, GROUP_T> groups, GroupHierarchy<GROUP_T> groupHierarchy);
    
    protected abstract HIERARCHY_T createGroupHierarchy(CONCEPT_T root);
}
