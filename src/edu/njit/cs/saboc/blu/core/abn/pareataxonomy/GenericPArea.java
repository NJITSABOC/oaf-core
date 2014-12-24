package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import SnomedShared.Concept;
import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public abstract class GenericPArea<T, R> extends GenericConceptGroup {
    
    protected SingleRootedHierarchy<T> conceptHierarchy;
    
    protected HashSet<R> relationships;
    
    protected GenericPArea(int id, 
            Concept root, 
            SingleRootedHierarchy<T> conceptHierarchy, 
            HashSet<Integer> parentIds,
            HashSet<R> relationships) {
        
        super(id, root, conceptHierarchy.getNodesInHierarchy().size(), parentIds);
        
        this.conceptHierarchy = conceptHierarchy;
        
        this.relationships = relationships;
    }
    
    public SingleRootedHierarchy<T> getHierarchy() {
        return conceptHierarchy;
    }
    
    public HashSet<R> getRelationships() {
        return relationships;
    }
    
    public abstract HashSet<R> getRelsWithoutInheritanceInfo();
}
