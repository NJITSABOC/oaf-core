package testing;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbNFactory;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomyFactory;
import edu.njit.cs.saboc.blu.core.abn.tan.TANFactory;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hao Liu
 */
public interface AbNDerivationFactoryTesting {
    
    public <T extends PAreaTaxonomyFactory> T getPAreaTaxonomyFactory();
    
    public <V extends DisjointAbNFactory> V getDisjointPAreaAbNFactory();
    
    public <U extends DisjointAbNFactory> U getDisjointTANbNFactory();
    
    public <N extends TANFactory> N getTANFactory();
    
}
