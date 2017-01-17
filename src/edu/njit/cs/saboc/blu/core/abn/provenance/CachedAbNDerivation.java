
package edu.njit.cs.saboc.blu.core.abn.provenance;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class CachedAbNDerivation<T extends AbstractionNetwork> extends AbNDerivation<T> {
    private final T abn;
    
    public CachedAbNDerivation(T abn) {
        super(abn.getDerivation().getSourceOntology());
        
        this.abn = abn;
    }

    @Override
    public String getDescription() {
        return String.format("%s (cached)", abn.getDerivation().getDescription());
    }

    @Override
    public T getAbstractionNetwork() {
        return abn;
    }
}
