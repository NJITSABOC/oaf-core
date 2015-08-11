package edu.njit.cs.saboc.blu.core.abn.reduced;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.GroupHierarchy;
import edu.njit.cs.saboc.blu.core.abn.SingleRootedGroupHierarchy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Chris O
 */
public abstract class ReducedAbNGenerator<GROUP_T extends GenericConceptGroup> {
    
    public ReducedAbNHierarchy<GROUP_T> createReducedAbN(GROUP_T rootGroup, HashMap<Integer, GROUP_T> groups, 
            GroupHierarchy<GROUP_T> groupHierarchy, int minGroupSize, int maxGroupSize) {
        
        HashMap<GROUP_T, HashSet<Integer>> reducedParents = new HashMap<GROUP_T, HashSet<Integer>>();
        
        HashMap<GROUP_T, GroupHierarchy<GROUP_T>> reducedGroupMembers = new HashMap<GROUP_T, GroupHierarchy<GROUP_T>>();
        
        GroupHierarchy<GROUP_T> reducedGroupHierarchy = new GroupHierarchy(rootGroup);
        
        HashMap<Integer, Integer> groupParentCounts = new HashMap<Integer, Integer>();
        
        HashSet<GROUP_T> remainingGroups = new HashSet<GROUP_T>();
        remainingGroups.add(rootGroup); // The root parea is always included
        
        HashMap<GROUP_T, HashSet<GROUP_T>> groupSet = new HashMap<>();
        
        for(GROUP_T group : groups.values()) {
            groupParentCounts.put(group.getId(), group.getParentIds().size());
            
            if (group.getConceptCount() >= minGroupSize && group.getConceptCount() <= maxGroupSize) {
                remainingGroups.add(group);
            }

            groupSet.put(group, new HashSet<GROUP_T>());
        }
        
        for (GROUP_T group : remainingGroups) {
            reducedGroupMembers.put(group, new GroupHierarchy<GROUP_T>(group));

            reducedParents.put(group, new HashSet<Integer>());
        }
        
        Queue<GROUP_T> queue = new LinkedList<GROUP_T>();
        queue.add(rootGroup);
        
        while(!queue.isEmpty()) {
            GROUP_T group = queue.remove();
            
            HashSet<Integer> parentGroupIds = group.getParentIds();

            if (remainingGroups.contains(group)) {
                groupSet.get(group).add(group);
                
                for (int parentGroupId : parentGroupIds) {
                    GROUP_T parentGroup = groups.get(parentGroupId);
                    
                    HashSet<GROUP_T> parentReducedGroups = groupSet.get(parentGroup);
                    
                    for(GROUP_T reducedGroup : parentReducedGroups) {
                        reducedParents.get(group).add(reducedGroup.getId());
                        
                        reducedGroupHierarchy.addIsA(group, parentGroup);
                    }
                }
                
            } else {
                if (parentGroupIds != null) {
                    for (int parentGroupId : parentGroupIds) {
                        // Get the parent group
                        GROUP_T parentGroup = groups.get(parentGroupId); 
                        
                        // Mark that this group belongs to the same reduced group as its parents
                        groupSet.get(group).addAll(groupSet.get(parentGroup)); 
                        
                        // Add this group to that reducing group too
                        for (GROUP_T reducedGroup : groupSet.get(parentGroup)) {
                            reducedGroupMembers.get(reducedGroup).addIsA(group, parentGroup);
                        }
                    }
                }
            }

            HashSet<GROUP_T> childGroups = groupHierarchy.getChildren(group);

            if (!childGroups.isEmpty()) {
                for (GROUP_T childGroup : childGroups) {
                    int childParentCount = groupParentCounts.get(childGroup.getId());
                    
                    if(childParentCount - 1 == 0) {
                        queue.add(childGroup);
                    } else {
                        groupParentCounts.put(childGroup.getId(), childParentCount - 1);
                    }
                }
            }
        }
        
        HashMap<Integer, GROUP_T> reducedGroups = new HashMap<Integer, GROUP_T>();
        
        for(GROUP_T reducedGroup : remainingGroups) {
            reducedGroups.put(reducedGroup.getId(), createReducedGroup(reducedGroup, 
                    reducedParents.get(reducedGroup), reducedGroupMembers.get(reducedGroup)));
        }
        
        return new ReducedAbNHierarchy<GROUP_T>(reducedGroups, reducedGroupHierarchy);
    }
    
    protected abstract GROUP_T createReducedGroup(GROUP_T group, HashSet<Integer> parentIds, GroupHierarchy<GROUP_T> reducedGroupHierarchy);
}
