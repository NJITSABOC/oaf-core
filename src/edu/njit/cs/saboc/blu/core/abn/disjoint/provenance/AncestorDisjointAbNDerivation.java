package edu.njit.cs.saboc.blu.core.abn.disjoint.provenance;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.provenance.RootedSubAbNDerivation;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class AncestorDisjointAbNDerivation<T extends SinglyRootedNode> extends DisjointAbNDerivation<T> 
        implements RootedSubAbNDerivation<DisjointAbNDerivation> {
    
    private final DisjointAbNDerivation sourceDisjointAbNDerivation;
    private final Concept disjointNodeRoot;
    
    public AncestorDisjointAbNDerivation(
            DisjointAbNDerivation sourceDisjointAbNDerivation, 
            Concept disjointNodeRoot) {
        
        super(sourceDisjointAbNDerivation);
        
        this.sourceDisjointAbNDerivation = sourceDisjointAbNDerivation;
        this.disjointNodeRoot = disjointNodeRoot;
    }

    @Override
    public Concept getSelectedRoot() {
        return disjointNodeRoot;
    }

    @Override
    public DisjointAbNDerivation<T> getSuperAbNDerivation() {
        return sourceDisjointAbNDerivation;
    }
    
    @Override
    public DisjointAbstractionNetwork getAbstractionNetwork() {
        
        DisjointAbstractionNetwork sourceAbN = getSuperAbNDerivation().getAbstractionNetwork();
     
        Set<DisjointNode> nodes = sourceAbN.getNodesWith(disjointNodeRoot);
        
        return sourceAbN.getAncestorDisjointAbN(nodes.iterator().next());
    }

    @Override
    public String getDescription() {
        return String.format("%s (subset)", sourceDisjointAbNDerivation.getDescription());
    }
}