package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.RootedSubAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.DerivedAncestorSubtaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.DerivedPAreaTaxonomy;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class AncestorSubtaxonomy<T extends PArea> extends PAreaTaxonomy<T> 
        implements RootedSubAbstractionNetwork<T, PAreaTaxonomy> {
    
    private final PAreaTaxonomy superTaxonomy;
    private final T sourcePArea;
    
    public AncestorSubtaxonomy(
            PAreaTaxonomy superTaxonomy,
            T sourcePArea,
            PAreaTaxonomy subTaxonomy,
            DerivedPAreaTaxonomy derivation) {

        super(subTaxonomy, derivation);
        
        this.superTaxonomy = superTaxonomy;
        this.sourcePArea = sourcePArea;
    }
    
    public AncestorSubtaxonomy(
            PAreaTaxonomy superTaxonomy,
            T sourcePArea,
            PAreaTaxonomy subTaxonomy) {

        this(superTaxonomy, 
                sourcePArea, 
                subTaxonomy, 
                new DerivedAncestorSubtaxonomy(superTaxonomy.getDerivation(), sourcePArea.getRoot()));
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