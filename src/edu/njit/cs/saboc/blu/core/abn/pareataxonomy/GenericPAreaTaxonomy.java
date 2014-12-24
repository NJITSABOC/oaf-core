package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public abstract class GenericPAreaTaxonomy<
        P extends GenericPArea<T, R>,
        A extends GenericArea<T, R, P, REGION_T>,
        REGION_T extends GenericRegion<T, R, P>,
        T,
        R>  extends AbstractionNetwork {
    
    protected P rootPArea;

    protected SingleRootedHierarchy<T> conceptHierarchy;
    
    protected GenericPAreaTaxonomy(
            SingleRootedHierarchy<T> conceptHierarchy,
            P rootPArea,
            ArrayList<A> areas,
            HashMap<Integer, P> pareas,
            HashMap<Integer, HashSet<Integer>> pareaHierarchy) {

        super(areas, pareas, pareaHierarchy);

        this.conceptHierarchy = conceptHierarchy;
                
        this.rootPArea = rootPArea;
    }
    
    public SingleRootedHierarchy<T> getConceptHierarchy() {
        return conceptHierarchy;
    }
    
    public GenericConceptGroup getRootGroup() {
        return this.getRootPArea();
    }
    
    public P getRootPArea() {
        return rootPArea;
    }
}
