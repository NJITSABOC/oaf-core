package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.node.SimilarityNode;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a region of an area in a partial-area taxonomy
 * @author Chris O
 */
public abstract class Region extends SimilarityNode {
    
    public Region(Set<PArea> pareas) {
        super(Collections.emptySet(), pareas);
    }

    public HashSet<PArea> getPAreas() { 
        return (HashSet<PArea>)super.getInternalNodes();
    }
    
    public boolean isPAreaInRegion(PArea parea) {        
        return getPAreas().contains(parea);
    }
}
