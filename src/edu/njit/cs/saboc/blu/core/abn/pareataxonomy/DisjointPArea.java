package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class DisjointPArea extends DisjointNode<PArea> {
    
    public DisjointPArea(Hierarchy<Concept> concepts, Set<PArea> overlaps) {
        super(concepts, overlaps);
    }
    
}
