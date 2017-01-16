
package edu.njit.cs.saboc.blu.core.abn.tan.provenance;

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
public class DerivedSimpleClusterTAN extends DerivedClusterTAN {
    
    private final Set<Concept> patriarchs;
    
    public DerivedSimpleClusterTAN(Set<Concept> patriarchs, Ontology sourceOntology, TANFactory factory) {
        super(sourceOntology, factory);
        
        this.patriarchs = patriarchs;
    }
            
    public Set<Concept> getPatriarchs() {
        return patriarchs;
    }
    
    @Override
    public ClusterTribalAbstractionNetwork getAbstractionNetwork() {
        TribalAbstractionNetworkGenerator generator = new TribalAbstractionNetworkGenerator();

        ClusterTribalAbstractionNetwork tan = generator.deriveTANFromMultiRootedHierarchy(
                super.getSourceOntology().getConceptHierarchy().getSubhierarchyRootedAt(patriarchs), super.getFactory());

        return tan;
    }

    @Override
    public String getDescription() {
        return String.format("Derived tribal abstraction network");
    }
}
