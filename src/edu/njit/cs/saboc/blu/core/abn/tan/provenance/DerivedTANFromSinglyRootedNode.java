package edu.njit.cs.saboc.blu.core.abn.tan.provenance;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.TANFactory;
import edu.njit.cs.saboc.blu.core.abn.tan.TribalAbstractionNetworkGenerator;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 *
 * @author Chris O
 * @param <T>
 * @param <V>
 */
public class DerivedTANFromSinglyRootedNode <
        T extends AbNDerivation, 
        V extends SinglyRootedNode> extends DerivedClusterTAN {
    
    private final T parentAbNDerivation;
    private final Concept nodeRoot;
    
    public DerivedTANFromSinglyRootedNode(
            T parentAbNDerivation, 
            TANFactory factory,
            Concept nodeRoot) {
        
        super(parentAbNDerivation.getSourceOntology(), 
                factory);
        
        this.parentAbNDerivation = parentAbNDerivation;
        this.nodeRoot = nodeRoot;
    }
    
    public T getParentAbNDerivation() {
        return parentAbNDerivation;
    }
    
    public Concept getRootConcept() {
        return nodeRoot;
    }

    @Override
    public String getDescription() {
        return String.format("Derived TAN from %s", nodeRoot.getName());
    }

    @Override
    public ClusterTribalAbstractionNetwork getAbstractionNetwork() {
        AbstractionNetwork<V> sourceAbN = parentAbNDerivation.getAbstractionNetwork();
        
        Set<V> nodes = sourceAbN.getNodesWith(nodeRoot);
        
        TribalAbstractionNetworkGenerator generator = new TribalAbstractionNetworkGenerator();

        return generator.createTANFromSinglyRootedNode(sourceAbN, nodes.iterator().next(), super.getFactory());
    }
}