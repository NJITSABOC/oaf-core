package edu.njit.cs.saboc.blu.core.abn;

import SnomedShared.generic.GenericConceptGroup;
import SnomedShared.generic.GenericGroupContainer;

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
public abstract class AbstractionNetwork {

    protected ArrayList<? extends GenericGroupContainer> containers;
    protected HashMap<Integer, ? extends GenericConceptGroup> groups;
    protected HashMap<Integer, HashSet<Integer>> groupHierarchy;
    
    protected AbstractionNetwork(
            ArrayList<? extends GenericGroupContainer> containers,
            HashMap<Integer, ? extends GenericConceptGroup> groups,
            HashMap<Integer, HashSet<Integer>> groupHierarchy) {

        this.containers = containers;
        this.groups = groups;
        this.groupHierarchy = groupHierarchy;
    }

    protected int getGroupCount() {
        return groups.keySet().size();
    }

    protected int getContainerCount() {
        return containers.size();
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
    
    public GenericConceptGroup getGroupFromRootConceptId(long rootConceptId) {
        for (GenericConceptGroup group : groups.values()) {
            if (group.getRoot().getId() == rootConceptId) {
                return group;
            }
        }

        return null;
    }

    public List<GenericConceptGroup> searchAnywhereInGroupRoots(String term) {
        List<GenericConceptGroup> results = new ArrayList<GenericConceptGroup>();
        String[] terms = term.trim().split("\\s*,\\s*");

        for (Entry<Integer, ? extends GenericConceptGroup> entry : groups.entrySet()) {
            for (String s : terms) {
                if (entry.getValue().getRoot().getName().toLowerCase().contains(s.toLowerCase())) {
                    results.add(entry.getValue());
                    break;
                }
            }
        }

        Collections.sort(results, new Comparator<GenericConceptGroup>() {
            public int compare(GenericConceptGroup a, GenericConceptGroup b) {
                return a.getRoot().getName().compareToIgnoreCase(b.getRoot().getName());
            }
        });

        return results;
    }
    
    public abstract GenericConceptGroup getRootGroup();
    
    public HashMap<Integer, ? extends GenericConceptGroup> getGroups() {
        return groups;
    }
    
    public ArrayList<? extends GenericGroupContainer> getContainers() {
        return containers;
    }
}
