package edu.njit.cs.saboc.blu.core.abn;

import SnomedShared.generic.GenericConceptGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Stack;

/**
 *
 * @author Chris
 */
public abstract class AbstractionNetwork<GROUP_T extends GenericConceptGroup> {

    protected HashMap<Integer, GROUP_T> groups;
    protected HashMap<Integer, HashSet<Integer>> groupHierarchy;
    
    protected AbstractionNetwork(
            HashMap<Integer, GROUP_T> groups,
            HashMap<Integer, HashSet<Integer>> groupHierarchy) {

        this.groups = groups;
        this.groupHierarchy = groupHierarchy;
    }

    protected int getGroupCount() {
        return groups.keySet().size();
    }

    public HashSet<Integer> getGroupChildren(int groupId) {
        HashSet<Integer> children = groupHierarchy.get(groupId);
        
        if(children == null) {
            return new HashSet<Integer>();
        }
        
        return children;
    }

    public HashSet<Integer> getGroupDescendants(int groupId) {
        HashSet<Integer> descendents = new HashSet<Integer>();

        Stack<Integer> stack = new Stack<Integer>();

        if (groupHierarchy.containsKey(groupId) && groupHierarchy.get(groupId) != null) {
            stack.addAll(groupHierarchy.get(groupId));
        }

        while (!stack.isEmpty()) {
            int descendentId = stack.pop();

            descendents.add(descendentId);

            HashSet<Integer> children = groupHierarchy.get(descendentId);

            if (children != null) {
                for (int child : children) {
                    if (!descendents.contains(child) && !stack.contains(child)) {
                        stack.add(child);
                    }
                }
            }

        }

        return descendents;
    }
    
    public GROUP_T getGroupFromRootConceptId(long rootConceptId) {
        for (GROUP_T group : groups.values()) {
            if (group.getRoot().getId() == rootConceptId) {
                return group;
            }
        }

        return null;
    }

    public List<GROUP_T> searchAnywhereInGroupRoots(String term) {
        List<GROUP_T> results = new ArrayList<>();
        String[] terms = term.trim().split("\\s*,\\s*");

        for (Entry<Integer, GROUP_T> entry : groups.entrySet()) {
            for (String s : terms) {
                if (entry.getValue().getRoot().getName().toLowerCase().contains(s.toLowerCase())) {
                    results.add(entry.getValue());
                    break;
                }
            }
        }

        Collections.sort(results, new Comparator<GROUP_T>() {
            public int compare(GROUP_T a, GROUP_T b) {
                return a.getRoot().getName().compareToIgnoreCase(b.getRoot().getName());
            }
        });

        return results;
    }
    
    public abstract GROUP_T getRootGroup();
    
    public HashMap<Integer, GROUP_T> getGroups() {
        return groups;
    }
    
    public HashSet<GROUP_T> getChildGroups(GROUP_T group) {
        HashSet<GROUP_T> childGroups = new HashSet<>();
        
        HashSet<Integer> childIds = groupHierarchy.get(group.getId());
        
        childIds.forEach( (Integer childId) -> {
            childGroups.add(groups.get(childId));
        });
        
        return childGroups;
    }
    
    public HashSet<GROUP_T> getParentGroups(GROUP_T group) {
        HashSet<GROUP_T> parentGroups = new HashSet<>();

        HashSet<Integer> parentIds = group.getParentIds();

        parentIds.forEach((Integer childId) -> {
            parentGroups.add(groups.get(childId));
        });

        return parentGroups;
    }
}
