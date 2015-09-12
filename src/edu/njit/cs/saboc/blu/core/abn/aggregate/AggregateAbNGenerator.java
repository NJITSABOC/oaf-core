package edu.njit.cs.saboc.blu.core.abn.aggregate;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.GroupHierarchy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Chris O
 */
public abstract class AggregateAbNGenerator<GROUP_T extends GenericConceptGroup, AGGREGATEGROUP_T extends GenericConceptGroup & AggregateableConceptGroup> {
    
    public AggregateAbNResult<GROUP_T, AGGREGATEGROUP_T> createReducedAbN(
            GROUP_T rootGroup, 
            HashMap<Integer, GROUP_T> groups, 
            GroupHierarchy<GROUP_T> groupHierarchy, 
            int minGroupSize) {
        
        HashMap<GROUP_T, HashSet<Integer>> reducedParents = new HashMap<>();
        
        HashMap<GROUP_T, GroupHierarchy<GROUP_T>> reducedGroupMembers = new HashMap<>();

        HashMap<Integer, Integer> groupParentCounts = new HashMap<>();
        
        HashSet<GROUP_T> remainingGroups = new HashSet<>();
        remainingGroups.add(rootGroup); // The root parea is always included
        
        HashMap<GROUP_T, HashSet<GROUP_T>> groupSet = new HashMap<>();
        
        for(GROUP_T group : groups.values()) {
            groupParentCounts.put(group.getId(), groupHierarchy.getParents(group).size());
            
            if (group.getConceptCount() >= minGroupSize) {
                remainingGroups.add(group);
            }

            groupSet.put(group, new HashSet<>());
        }
        
        for (GROUP_T group : remainingGroups) {
            reducedGroupMembers.put(group, new GroupHierarchy<>(group));

            reducedParents.put(group, new HashSet<>());
        }
        
        Queue<GROUP_T> queue = new LinkedList<GROUP_T>();
        queue.add(rootGroup);
        
        while(!queue.isEmpty()) {
            
            GROUP_T group = queue.remove();
            
            HashSet<GROUP_T> parentGroups = groupHierarchy.getParents(group);

            if (remainingGroups.contains(group)) {
                
                groupSet.get(group).add(group);

                for (GROUP_T parentGroup : parentGroups) {

                    HashSet<GROUP_T> parentReducedGroups = groupSet.get(parentGroup);

                    for (GROUP_T reducedGroup : parentReducedGroups) {
                        reducedParents.get(group).add(reducedGroup.getId());
                    }
                }

            } else {

                for (GROUP_T parentGroup : parentGroups) {

                    // Mark that this group belongs to the same reduced group as its parents
                    groupSet.get(group).addAll(groupSet.get(parentGroup));

                    // Add this group to that reducing group too
                    for (GROUP_T reducedGroup : groupSet.get(parentGroup)) {
                        reducedGroupMembers.get(reducedGroup).addIsA(group, parentGroup);
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
        
        
        HashMap<Integer, AGGREGATEGROUP_T> reducedGroups = new HashMap<>();
        
        for(GROUP_T reducedGroup : remainingGroups) {
            reducedGroups.put(reducedGroup.getId(), createReducedGroup(reducedGroup, 
                    reducedParents.get(reducedGroup), reducedGroupMembers.get(reducedGroup)));
        }
        
        GroupHierarchy<AGGREGATEGROUP_T> reducedGroupHierarchy = new GroupHierarchy(reducedGroups.get(rootGroup.getId()));
        
        reducedGroups.values().forEach((AGGREGATEGROUP_T aggregateGroup) -> {
            GROUP_T group = (GROUP_T)aggregateGroup;
            
            HashSet<Integer> parentIds = group.getParentIds();
            
            parentIds.forEach((Integer parentId) -> {
                reducedGroupHierarchy.addIsA(aggregateGroup, reducedGroups.get(parentId));
            });
        });
        
        return new AggregateAbNResult<GROUP_T, AGGREGATEGROUP_T>(reducedGroups, reducedGroupHierarchy);
    }
    
    protected abstract AGGREGATEGROUP_T createReducedGroup(GROUP_T group, HashSet<Integer> parentIds, GroupHierarchy<GROUP_T> reducedGroupHierarchy);
}
