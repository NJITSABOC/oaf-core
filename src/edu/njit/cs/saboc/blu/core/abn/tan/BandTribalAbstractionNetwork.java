package edu.njit.cs.saboc.blu.core.abn.tan;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.AbstractionNetworkUtils;
import edu.njit.cs.saboc.blu.core.abn.node.NodeHierarchy;
import edu.njit.cs.saboc.blu.core.abn.node.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.ontology.ConceptHierarchy;
import java.util.Set;

/**
 *
 * @author Den
 */
public class BandTribalAbstractionNetwork extends AbstractionNetwork<Band> {
    public BandTribalAbstractionNetwork(NodeHierarchy<Band> bandHierarchy, 
            ConceptHierarchy sourceHierarchy) {
        
        super(bandHierarchy, sourceHierarchy);
    }
    
    public NodeHierarchy<Band> getBandHierarchy(){
        return super.getNodeHierarchy();
    }
    
    public Set<Band> getBands() {
        return super.getNodes();
    }
    
    @Override
    public Set<ParentNodeDetails> getParentNodeDetails(Band band) {
        return AbstractionNetworkUtils.getMultiRootedNodeParentNodeDetails(
                band,
                this.getSourceHierarchy(),
                (Set<PartitionedNode>) (Set<?>) this.getBands());
    }
}
