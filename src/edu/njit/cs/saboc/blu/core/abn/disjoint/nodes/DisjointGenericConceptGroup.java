package edu.njit.cs.saboc.blu.core.abn.disjoint.nodes;

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
public abstract class DisjointGenericConceptGroup<U extends GenericConceptGroup, V, T extends DisjointGenericConceptGroup> {
    private HashSet<U> overlapsIn;
    
    private SingleRootedHierarchy<V> conceptHierarchy;

    private final HashMap<V, T> parents = new HashMap<V, T>();
    
    public DisjointGenericConceptGroup(V root, HashSet<U> overlapsIn) {
        this.conceptHierarchy = this.createGroupHierarchy(root);
        this.overlapsIn = overlapsIn;
    }
    
    protected abstract SingleRootedHierarchy<V> createGroupHierarchy(V root);
    
    protected abstract Comparator<V> getConceptComparator();

     /**
     * Returns the root of the disjoint partial-area
     * @return 
     */
    public V getRoot() {
        return conceptHierarchy.getRoot();
    }

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
     * @param parent
     * @param parentDisjointGroup 
     */
    public void registerParent(V parent, T parentDisjointGroup) {
        parents.put(parent, parentDisjointGroup);
    }

    /**
     * Returns this disjoint partial-area's parents and the disjoint partial-areas they belong to.
     * @return 
     */
    public HashMap<V, T> getParents() {
        return parents;
    }

    /**
     * Adds a concept to this disjoint partial-area
     * @param c
     * @param parent 
     */
    public void addConcept(V c, V parent) {
        conceptHierarchy.addIsA(c, parent);
    }

    /**
     * Returns the hierarchy of concepts summarized by this disjoint partial-area
     * @return 
     */
    public SingleRootedHierarchy<V> getConceptHierarchy() {
        return conceptHierarchy;
    }

    
    /**
     * Returns the concepts summarized by this disjoint partial-area as a list, sorted by FSN
     * @return 
     */
    public ArrayList<V> getConceptsAsList() {
        ArrayList<V> concepts = new ArrayList<V>(conceptHierarchy.getNodesInHierarchy());

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
