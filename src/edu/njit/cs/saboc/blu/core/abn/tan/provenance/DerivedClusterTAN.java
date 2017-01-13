package edu.njit.cs.saboc.blu.core.abn.tan.provenance;

import edu.njit.cs.saboc.blu.core.abn.provenance.DerivedAbstractionNetwork;
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
public class DerivedClusterTAN extends DerivedAbstractionNetwork<ClusterTribalAbstractionNetwork> {
    
    private final Set<Concept> patriarchs;
    private final TANFactory factory;
    
    public DerivedClusterTAN(
            Ontology sourceOntology, 
            Set<Concept> roots, 
            TANFactory factory) {
        
        super(sourceOntology);
        
        this.patriarchs = roots;
        this.factory = factory;
    }
    
    public DerivedClusterTAN(DerivedClusterTAN base) {
        
        this(base.getSourceOntology(), 
                base.getPatriarchs(), 
                base.getFactory());
    }

    public Set<Concept> getPatriarchs() {
        return patriarchs;
    }
    
    public TANFactory getFactory() {
        return factory;
    }
    
    @Override
    public ClusterTribalAbstractionNetwork getAbstractionNetwork() {
        TribalAbstractionNetworkGenerator generator = new TribalAbstractionNetworkGenerator();
        
        ClusterTribalAbstractionNetwork tan = generator.deriveTANFromMultiRootedHierarchy(super.getSourceOntology().getConceptHierarchy().getSubhierarchyRootedAt(patriarchs), 
                factory);
        
        return tan;
    }

    @Override
    public String getDescription() {
        return String.format("Derived tribal abstraction network");
    }
}
