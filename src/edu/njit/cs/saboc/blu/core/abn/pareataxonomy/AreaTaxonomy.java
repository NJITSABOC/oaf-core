package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.NodeHierarchy;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class AreaTaxonomy extends AbstractionNetwork<Area> {
    public AreaTaxonomy(NodeHierarchy<Area> areaHierarchy) {
        super(areaHierarchy);
    }
    
    public NodeHierarchy<Area> getAreaHierarchy(){
        return super.getNodeHierarchy();
    }
    
    public Set<Area> getAreas() {
        return super.getNodes();
    }
}
