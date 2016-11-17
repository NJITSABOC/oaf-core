package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.pravenance;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class DerivedRootSubtaxonomy extends DerivedPAreaTaxonomy{
    
    private final Concept pareaRoot;
    
    public DerivedRootSubtaxonomy(DerivedPAreaTaxonomy source, Concept pareaRoot) {
        super(source);
        
        this.pareaRoot = pareaRoot;
    }

    @Override
    public String getDescription() {
        return String.format("Derived root subtaxonomy with root: " + pareaRoot.getName());
    }

    @Override
    public PAreaTaxonomy getAbstractionNetwork() {
        PAreaTaxonomy taxonomy = super.getAbstractionNetwork();
        
        Set<PArea> pareas = taxonomy.getNodesWith(pareaRoot);
        
        return taxonomy.createRootSubtaxonomy(pareas.iterator().next());
    }
}
