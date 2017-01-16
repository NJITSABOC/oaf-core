package edu.njit.cs.saboc.blu.core.abn.tan.provenance;

import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.TANFactory;
import edu.njit.cs.saboc.blu.core.abn.tan.TribalAbstractionNetworkGenerator;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public abstract class DerivedClusterTAN extends AbNDerivation<ClusterTribalAbstractionNetwork> {
    
    private final TANFactory factory;
    
    public DerivedClusterTAN(
            Ontology sourceOntology, 
            TANFactory factory) {
        
        super(sourceOntology);

        this.factory = factory;
    }
    
    public DerivedClusterTAN(DerivedClusterTAN base) {
        this(base.getSourceOntology(), 
                base.getFactory());
    }

    public TANFactory getFactory() {
        return factory;
    }
}
