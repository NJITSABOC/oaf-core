package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class OverlappingNodeEntry {
    
    private final Node overlappingGroup;
    private final Set<Concept> overlappingConcepts;
    
    public OverlappingNodeEntry(Node overlappingGroup, Set<Concept> overlappingConcepts) {
        this.overlappingGroup = overlappingGroup;
        this.overlappingConcepts = overlappingConcepts;
    }
    
    public Node getOverlappingGroup() {
        return overlappingGroup;
    }
    
    public Set<Concept> getOverlappingConcepts() {
        return overlappingConcepts;
    }
}
