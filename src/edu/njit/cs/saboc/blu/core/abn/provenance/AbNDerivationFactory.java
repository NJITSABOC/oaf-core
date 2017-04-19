package edu.njit.cs.saboc.blu.core.abn.provenance;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbNFactory;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomyFactory;
import edu.njit.cs.saboc.blu.core.abn.tan.TANFactory;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetworkFactory;

/**
 *
 * @author Hao Liu
 */
public interface AbNDerivationFactory {
    public PAreaTaxonomyFactory getPAreaTaxonomyFactory();
    
    public DisjointAbNFactory getDisjointPAreaAbNFactory();
    
    public DisjointAbNFactory getDisjointTANbNFactory();
    
    public TANFactory getTANFactory();
    
    public TargetAbstractionNetworkFactory getTargetAbNFactory();
}
