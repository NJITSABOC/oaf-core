package edu.njit.cs.saboc.blu.core.abn.disjoint.nodes;

import SnomedShared.Concept;
import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.GenericParentGroupInfo;
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
public abstract class DisjointGenericConceptGroup<
        U extends GenericConceptGroup, 
        CONCEPT_T, 
        HIERARCHY_T extends SingleRootedHierarchy<CONCEPT_T, HIERARCHY_T>,
        T extends DisjointGenericConceptGroup> extends GenericConceptGroup {
    
    private final HashSet<U> overlapsIn;
    
    private final HIERARCHY_T conceptHierarchy;

    private HashSet<GenericParentGroupInfo<CONCEPT_T, T>> parentGroups;
    
    public DisjointGenericConceptGroup(
            int id, 
            Concept root, 
            HIERARCHY_T conceptHierarchy, 
            HashSet<Integer> parentIds,
            HashSet<U> overlapsIn) {
        
        super(id, root, conceptHierarchy.getNodesInHierarchy().size(), parentIds);

        this.conceptHierarchy = conceptHierarchy;
        this.overlapsIn = overlapsIn;
    }
        
    protected abstract Comparator<CONCEPT_T> getConceptComparator();


    /**
     * Returns the total number of concepts summarized by the disjoint partial-area
     * @return 
     */
    public int getConceptCount() {
        return conceptHierarchy.getNodesInHierarchy().size();
    }

    /**
     * Returns which partial-areas the concepts of this disjoint partial-area overlap between
     * @return 
     */
    public HashSet<U> getOverlaps() {
        return overlapsIn;
    }

    /**
     * Adds disjoint partial-area information about a parent of the root concept

     */
    public void setParentGroups(HashSet<GenericParentGroupInfo<CONCEPT_T, T>> parentGroups) {
        this.parentGroups = parentGroups;
    }

    /**
     * Returns this disjoint partial-area's parents and the disjoint partial-areas they belong to.
     * @return 
     */
    public HashSet<GenericParentGroupInfo<CONCEPT_T, T>> getParentGroups() {
        return parentGroups;
    }

    /**
     * Returns the hierarchy of concepts summarized by this disjoint partial-area
     * @return 
     */
    public HIERARCHY_T getConceptHierarchy() {
        return conceptHierarchy;
    }

    /**
     * Returns the concepts summarized by this disjoint partial-area as a list, sorted by FSN
     * @return 
     */
    public ArrayList<CONCEPT_T> getConceptsAsList() {
        ArrayList<CONCEPT_T> concepts = new ArrayList<CONCEPT_T>(conceptHierarchy.getNodesInHierarchy());

        Collections.sort(concepts, getConceptComparator());

        return concepts;
    }

    public boolean equals(Object o) {
        if (o instanceof DisjointGenericConceptGroup) {
            return ((DisjointGenericConceptGroup) o).getRoot().equals(this.getRoot());
        }

        return false;
    }
    
    public String toString() {
        return getRoot() + " (" + getConceptCount() + ")" + "{" + overlapsIn + "}";
    }
}
