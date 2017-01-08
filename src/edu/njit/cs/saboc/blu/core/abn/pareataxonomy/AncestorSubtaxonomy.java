package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.RootedSubAbstractionNetwork;

/**
 *
 * @author Chris O
 */
public class AncestorSubtaxonomy<T extends PArea> 
        extends PAreaTaxonomy<T> implements RootedSubAbstractionNetwork<T, PAreaTaxonomy> {
    
    private final PAreaTaxonomy superTaxonomy;
    private final T sourcePArea;
    
    public AncestorSubtaxonomy(
            PAreaTaxonomy superTaxonomy,
            T sourcePArea,
            PAreaTaxonomy subTaxonomy) {

        super(subTaxonomy);
        
        this.superTaxonomy = superTaxonomy;
        this.sourcePArea = sourcePArea;
    }

    @Override
    public T getSelectedRoot() {
        return sourcePArea;
    }

    @Override
    public PAreaTaxonomy getSuperAbN() {
        return superTaxonomy;
    }
}