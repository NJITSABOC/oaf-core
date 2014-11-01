package edu.njit.cs.saboc.blu.core.gui.dialogs.concepthierarchy;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

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

        HashSet<T> visitedConcepts = new HashSet<T>();

        ArrayList<ArrayList<T>> levels = new ArrayList<ArrayList<T>>();

        HashSet<T> levelConcepts = new HashSet<T>();

        levelConcepts.add(getGroupRoot(group));

        while (!levelConcepts.isEmpty()) {

            levels.add(new ArrayList<T>(levelConcepts));

            HashSet<T> nextLevel = new HashSet<T>();

            for (T c : levelConcepts) {
                HashSet<T> conceptChildren = hierarchy.getChildren(c);
                visitedConcepts.add(c);

                if (conceptChildren != null) {
                    for (T child : conceptChildren) {
                        if (!visitedConcepts.contains(child)) {
                            nextLevel.add(child);
                        }
                    }
                }
            }

            levelConcepts = nextLevel;
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
