package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.DiffNode;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class DiffArea extends Area {

    private final DiffNode diffNode;

    public DiffArea(DiffNode diffNode, Set<InheritableProperty> rels, Set<DiffPArea> diffPAreas) {

        super( (Set<PArea>)(Set<?>) diffPAreas, rels);

        this.diffNode = diffNode;
    }
    
    public DiffNode getDiffNode() {
        return diffNode;
    }
}
