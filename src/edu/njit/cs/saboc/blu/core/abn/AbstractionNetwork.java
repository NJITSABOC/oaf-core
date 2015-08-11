package edu.njit.cs.saboc.blu.core.abn;

import SnomedShared.generic.GenericConceptGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

/**
 *
 * @author Chris
 */
public abstract class AbstractionNetwork<GROUP_T extends GenericConceptGroup> {

    protected HashMap<Integer, GROUP_T> groups;

    protected GroupHierarchy<GROUP_T> groupHierarchy;
    
    protected AbstractionNetwork(
            HashMap<Integer, GROUP_T> groups,
            GroupHierarchy<GROUP_T> groupHierarchy) {

        this.groups = groups;
        this.groupHierarchy = groupHierarchy;
    }

    protected int getGroupCount() {
        return groups.keySet().size();
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
        return groupHierarchy.getChildren(group);
    }
    
    public HashSet<GROUP_T> getParentGroups(GROUP_T group) {
        return groupHierarchy.getParents(group);
    }
    
    public HashSet<GROUP_T> getDescendantGroups(GROUP_T group) {
        return groupHierarchy.getSubhierarchyRootedAt(group).getDescendants(group);
    }
}
