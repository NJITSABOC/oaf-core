package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.DiffNode;
import edu.njit.cs.saboc.blu.core.abn.diff.DiffNodeInstance;
import edu.njit.cs.saboc.blu.core.abn.diff.change.ChangeState;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class DiffArea extends Area implements DiffNodeInstance {

    private final DiffNode diffNode;

    public DiffArea(DiffNode diffNode, Set<InheritableProperty> rels, Set<DiffPArea> diffPAreas) {

        super( (Set<PArea>)(Set<?>) diffPAreas, rels);

        this.diffNode = diffNode;
    }
    
    public Set<DiffPArea> getDiffPAreas() {
        return (Set<DiffPArea>)(Set<?>)super.getPAreas();
    }
    
    public DiffNode getDiffNode() {
        return diffNode;
    }
    
    public ChangeState getAreaState() {
        return diffNode.getChangeDetails().getNodeState();
    }
}
