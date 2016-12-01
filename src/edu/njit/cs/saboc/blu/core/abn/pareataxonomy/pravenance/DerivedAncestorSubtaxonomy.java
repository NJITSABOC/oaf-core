
package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.pravenance;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class DerivedAncestorSubtaxonomy extends DerivedPAreaTaxonomy{
    
    private final Concept pareaRoot;
    
    public DerivedAncestorSubtaxonomy(DerivedPAreaTaxonomy source, Concept pareaRoot) {
        super(source);
        
        this.pareaRoot = pareaRoot;
    }

    @Override
    public String getDescription() {
        return String.format("Derived ancestor subtaxonomy with parea: " + pareaRoot.getName());
    }

    @Override
    public PAreaTaxonomy getAbstractionNetwork() {
        PAreaTaxonomy taxonomy = super.getAbstractionNetwork();
        
        Set<PArea> pareas = taxonomy.getNodesWith(pareaRoot);
        
        return taxonomy.createAncestorSubtaxonomy(pareas.iterator().next());
    }
}
