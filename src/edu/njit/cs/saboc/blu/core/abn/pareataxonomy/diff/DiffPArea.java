
package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.DiffNode;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class DiffPArea extends PArea {

    private final DiffNode diffNode;
    
    public DiffPArea(
            DiffNode diffNode,
            Hierarchy<Concept> conceptHierarchy, 
            Set<InheritableProperty> relationships) {

        super(conceptHierarchy, relationships);
        
        this.diffNode = diffNode;
    }
    
    public DiffNode getDiffNode() {
        return diffNode;
    }
}
