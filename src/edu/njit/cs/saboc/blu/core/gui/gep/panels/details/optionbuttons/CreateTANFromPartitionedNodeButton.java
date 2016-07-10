package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.TribalAbstractionNetworkGenerator;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.CreateTANButton;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNListener;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class CreateTANFromPartitionedNodeButton extends CreateTANButton {

    public CreateTANFromPartitionedNodeButton(PartitionedAbNConfiguration config, DisplayAbNListener listener) {
        super(config.getTextConfiguration().getContainerTypeName(false).toLowerCase(), listener);
    }

    @Override
    public ClusterTribalAbstractionNetwork deriveTAN() {
        PartitionedNode partitionedNode = (PartitionedNode)super.getCurrentNode().get();
        
        Hierarchy<Concept> hierarchy = partitionedNode.getHierarchy();

        TribalAbstractionNetworkGenerator generator = new TribalAbstractionNetworkGenerator();
        ClusterTribalAbstractionNetwork tan = generator.deriveTANFromMultiRootedHierarchy(hierarchy);
        
        return tan;
    }

    @Override
    public void setEnabledFor(Node node) {
        PartitionedNode partitionedNode = (PartitionedNode)node;
        
        this.setEnabled(partitionedNode.hasOverlappingConcepts());
    }
}
