package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.RootedSubAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.PAreaTaxonomyDerivation;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.RootSubtaxonomyDerivation;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class RootSubtaxonomy<T extends PArea> 
        extends PAreaTaxonomy<T> implements RootedSubAbstractionNetwork<T, PAreaTaxonomy> {

    private final PAreaTaxonomy superTaxonomy;
    
    public RootSubtaxonomy(
            PAreaTaxonomy superTaxonomy,
            PAreaTaxonomy subTaxonomy,
            PAreaTaxonomyDerivation derivation) {

        super(subTaxonomy, derivation);
        
        this.superTaxonomy = superTaxonomy;
    }
    
    public RootSubtaxonomy(
            PAreaTaxonomy superTaxonomy,
            PAreaTaxonomy subTaxonomy) {

        this(superTaxonomy, 
                subTaxonomy, 
                new RootSubtaxonomyDerivation(
                        superTaxonomy.getDerivation(), 
                        subTaxonomy.getRootPArea().getRoot()));
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
