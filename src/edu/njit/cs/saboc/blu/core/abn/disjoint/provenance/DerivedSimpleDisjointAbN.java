
package edu.njit.cs.saboc.blu.core.abn.disjoint.provenance;

import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbNFactory;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbNGenerator;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivation;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class DerivedSimpleDisjointAbN<T extends SinglyRootedNode> extends DerivedDisjointAbN<T> {
    
    private final DisjointAbNFactory factory;
    private final AbNDerivation<PartitionedAbstractionNetwork> parentAbNDerivation;
    private final Set<Concept> sourceNodeRoots;
    
    public DerivedSimpleDisjointAbN(
            DisjointAbNFactory factory,
            AbNDerivation<PartitionedAbstractionNetwork> parentAbNDerivation, 
            Set<Concept> sourceNodeRoots) {
        
        super(factory, parentAbNDerivation);
        
        this.factory = factory;
        this.parentAbNDerivation = parentAbNDerivation;
        this.sourceNodeRoots = sourceNodeRoots;
    }
    
    public DerivedSimpleDisjointAbN(DerivedSimpleDisjointAbN derivedAbN) {
        this(derivedAbN.getFactory(), derivedAbN.getParentAbNDerivation(), derivedAbN.getSourceNodeRoots());
    }
    
    public Set<Concept> getSourceNodeRoots() {
        return sourceNodeRoots;
    }

    @Override
    public String getDescription() {
        return String.format("Disjointed %d", sourceNodeRoots.size());
    }

    @Override
    public DisjointAbstractionNetwork getAbstractionNetwork() {
        PartitionedAbstractionNetwork partitionedAbN = parentAbNDerivation.getAbstractionNetwork();
        
        Set<T> overlappingNodes = new HashSet<>();
        
        this.sourceNodeRoots.forEach( (concept) -> {
            overlappingNodes.addAll(partitionedAbN.getNodesWith(concept));
        });

        DisjointAbNGenerator generator = new DisjointAbNGenerator<>();

        return generator.generateDisjointAbstractionNetwork(
                factory,
                partitionedAbN,
                overlappingNodes);
    }
}
