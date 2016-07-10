package edu.njit.cs.saboc.blu.core.abn.tan;

import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class Cluster extends SinglyRootedNode {

    private final Set<Concept> patriarchs;
    
    public Cluster(Hierarchy<Concept> hierarchy, 
            Set<Concept> patriarchs) {
        
        super(hierarchy);

        this.patriarchs = patriarchs;
    }

    public Set<Concept> getPatriarchs() {
        return patriarchs;
    }
    
    
}
