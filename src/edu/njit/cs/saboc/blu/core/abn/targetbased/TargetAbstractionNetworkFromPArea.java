package edu.njit.cs.saboc.blu.core.abn.targetbased;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class TargetAbstractionNetworkFromPArea<T extends TargetGroup> extends TargetAbstractionNetwork<T> {
    
    private final PAreaTaxonomy sourceTaxonomy;
    private final PArea sourcePArea;
    
    public TargetAbstractionNetworkFromPArea(
            TargetAbstractionNetwork targetAbN, 
            PAreaTaxonomy sourceTaxonomy, 
            PArea sourcePArea, 
            InheritableProperty property) {
        
        super(targetAbN);
        
        this.sourceTaxonomy = sourceTaxonomy;
        this.sourcePArea = sourcePArea;
        
    }
}
