package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.RootedSubAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class RootSubtaxonomy<T extends PArea> 
        extends PAreaTaxonomy<T> implements RootedSubAbstractionNetwork<T, PAreaTaxonomy> {

    private final PAreaTaxonomy superTaxonomy;
    
    public RootSubtaxonomy(
            PAreaTaxonomy superTaxonomy,
            PAreaTaxonomy subTaxonomy) {

        super(subTaxonomy);
        
        this.superTaxonomy = superTaxonomy;
    }

    @Override
    public PAreaTaxonomy getSuperAbN() {
        return superTaxonomy;
    }

    @Override
    public T getSelectedRoot() {
        return this.getPAreaHierarchy().getRoot();
    }
}
