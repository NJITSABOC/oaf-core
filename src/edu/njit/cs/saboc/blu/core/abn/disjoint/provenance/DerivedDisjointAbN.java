package edu.njit.cs.saboc.blu.core.abn.disjoint.provenance;

import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbNFactory;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbNGenerator;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.DisjointPAreaTaxonomyFactory;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.provenance.DerivedAbstractionNetwork;

/**
 *
 * @author Chris O
 */
public class DerivedDisjointAbN extends DerivedAbstractionNetwork<DisjointAbstractionNetwork> {
    
    private final DisjointAbNFactory factory;
    private final DerivedAbstractionNetwork<PartitionedAbstractionNetwork> parentAbNDerivation;
    private final PartitionedNode sourceNode;
    
    public DerivedDisjointAbN(
            DisjointAbNFactory factory,
            DerivedAbstractionNetwork<PartitionedAbstractionNetwork> parentAbNDerivation, 
            PartitionedNode sourceNode) {
        
        super(parentAbNDerivation.getSourceOntology());
        
        this.factory = factory;
        this.parentAbNDerivation = parentAbNDerivation;
        this.sourceNode = sourceNode;
    }
    
    public DerivedDisjointAbN(DerivedDisjointAbN derivedAbN) {
        this(derivedAbN.getFactory(), derivedAbN.getParentAbNDerivation(), derivedAbN.getSourceNode());
    }
    
    public DisjointAbNFactory getFactory() {
        return factory;
    }
    
    public DerivedAbstractionNetwork<PartitionedAbstractionNetwork> getParentAbNDerivation() {
        return parentAbNDerivation;
    }
    
    public PartitionedNode getSourceNode() {
        return sourceNode;
    }

    @Override
    public String getDescription() {
        return String.format("Disjointed %s", sourceNode.getName());
    }

    @Override
    public DisjointAbstractionNetwork getAbstractionNetwork() {
        PartitionedAbstractionNetwork partitionedAbN = parentAbNDerivation.getAbstractionNetwork();

        DisjointAbNGenerator generator = new DisjointAbNGenerator<>();

        return generator.generateDisjointAbstractionNetwork(
                factory,
                partitionedAbN,
                sourceNode.getInternalNodes());
    }
}
