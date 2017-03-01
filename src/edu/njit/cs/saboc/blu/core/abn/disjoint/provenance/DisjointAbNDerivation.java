package edu.njit.cs.saboc.blu.core.abn.disjoint.provenance;

import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbNFactory;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivation;

/**
 * Stores the arguments needed to create a disjoint abstraction network.
 * 
 * @author Chris O
 * @param <T>
 */
public abstract class DisjointAbNDerivation<T extends SinglyRootedNode> extends AbNDerivation<DisjointAbstractionNetwork> {
    
    private final DisjointAbNFactory factory;
    private final AbNDerivation<PartitionedAbstractionNetwork> parentAbNDerivation;
    
    public DisjointAbNDerivation(
            DisjointAbNFactory factory,
            AbNDerivation<PartitionedAbstractionNetwork> parentAbNDerivation) {
        
        super(parentAbNDerivation.getSourceOntology());
        
        this.factory = factory;
        this.parentAbNDerivation = parentAbNDerivation;
    }
    
    public DisjointAbNDerivation(DisjointAbNDerivation derivedAbN) {
        this(derivedAbN.getFactory(), derivedAbN.getParentAbNDerivation());
    }
    
    public DisjointAbNFactory getFactory() {
        return factory;
    }
    
    public AbNDerivation<PartitionedAbstractionNetwork> getParentAbNDerivation() {
        return parentAbNDerivation;
    }
}
