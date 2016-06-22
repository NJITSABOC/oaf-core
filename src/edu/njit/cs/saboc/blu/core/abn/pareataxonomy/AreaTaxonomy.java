package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.AbstractionNetworkUtils;
import edu.njit.cs.saboc.blu.core.abn.node.NodeHierarchy;
import edu.njit.cs.saboc.blu.core.abn.node.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.ontology.ConceptHierarchy;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class AreaTaxonomy extends AbstractionNetwork<Area> {
    
    public AreaTaxonomy(NodeHierarchy<Area> areaHierarchy, 
            ConceptHierarchy sourceHierarchy) {
        
        super(areaHierarchy, sourceHierarchy);
    }
    
    public NodeHierarchy<Area> getAreaHierarchy(){
        return super.getNodeHierarchy();
    }
    
    public Set<Area> getAreas() {
        return super.getNodes();
    }

    @Override
    public Set<ParentNodeDetails> getParentNodeDetails(Area area) {
        return AbstractionNetworkUtils.getMultiRootedNodeParentNodeDetails(
                area, 
                this.getSourceHierarchy(), 
                (Set<PartitionedNode>)(Set<?>)this.getAreas());
    }
}
