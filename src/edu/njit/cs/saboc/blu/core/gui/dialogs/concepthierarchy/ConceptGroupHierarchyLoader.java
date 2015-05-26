package edu.njit.cs.saboc.blu.core.gui.dialogs.concepthierarchy;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Chris
 */
public abstract class ConceptGroupHierarchyLoader<T, V extends GenericConceptGroup> implements Runnable {

    private V group;
    
    private ConceptGroupHierarchicalViewPanel hierarchyViewPanel;
    
    private HashMap<T, ConceptEntry<T>> conceptEntryMap = new HashMap<T, ConceptEntry<T>>();

    public ConceptGroupHierarchyLoader(V group, ConceptGroupHierarchicalViewPanel<T> hierarchyViewPanel) {
        this.group = group;
        this.hierarchyViewPanel = hierarchyViewPanel;
    }

    public void run() {

        SingleRootedHierarchy<T> hierarchy = getGroupHierarchy(group);

        ArrayList<ArrayList<T>> levels = new ArrayList<ArrayList<T>>();

        T root = hierarchy.getRoot();
        
        HashMap<T, Integer> parentCount = new HashMap<T, Integer>();
        
        HashSet<T> concepts = hierarchy.getNodesInHierarchy();
        
        for(T concept : concepts) {
            parentCount.put(concept, hierarchy.getParents(concept).size());
        }
        
        Queue<T> queue = new LinkedList<T>();
        queue.add(root);
        
        HashMap<T, Integer> conceptDepth = new HashMap<T, Integer>();

        while(!queue.isEmpty()) {
            T concept = queue.remove();
            
            HashSet<T> parents = hierarchy.getParents(concept);
            
            int maxParentDepth = -1;
            
            for(T parent : parents) {
                int parentDepth = conceptDepth.get(parent);
                
                if(parentDepth > maxParentDepth) {
                    maxParentDepth = parentDepth;
                }
            }
            
            int depth = maxParentDepth + 1;
            
            conceptDepth.put(concept, depth);
            
            if(levels.size() < depth + 1) {
                levels.add(new ArrayList<T>());
            }
            
            levels.get(depth).add(concept);

            HashSet<T> children = hierarchy.getChildren(concept);
            
            for(T child : children) {
                int childParentCount = parentCount.get(child) - 1;
                
                if(childParentCount == 0) {
                    queue.add(child);
                } else {
                    parentCount.put(child, childParentCount);
                }
            }
        }
        
        ArrayList<ArrayList<ConceptEntry<T>>> levelEntries = new ArrayList<ArrayList<ConceptEntry<T>>>();

        for (ArrayList<T> level : levels) {
            Collections.sort(level, getComparator());

            ArrayList<ConceptEntry<T>> conceptEntries = new ArrayList<ConceptEntry<T>>();

            for (T c : level) {
                ConceptEntry<T> entry = createConceptEntry(c);

                conceptEntries.add(entry);
                conceptEntryMap.put(c, entry);
            }
            
            levelEntries.add(conceptEntries);
        }
        
        hierarchyViewPanel.initialize(hierarchy, levelEntries, conceptEntryMap);
    }
    
    public abstract SingleRootedHierarchy<T> getGroupHierarchy(V group);
    
    public abstract ConceptEntry<T> createConceptEntry(T concept);
    
    public abstract Comparator<T> getComparator();
    
    public abstract T getGroupRoot(V group);
}
