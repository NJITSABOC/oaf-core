package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons;

import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.TANFactory;
import edu.njit.cs.saboc.blu.core.abn.tan.TribalAbstractionNetworkGenerator;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNAction;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class CreateTANFromPartitionedNodeButton<T extends PartitionedNode> extends CreateTANButton<T> {

    private final TANFactory factory;
    
    public CreateTANFromPartitionedNodeButton(
            TANFactory factory,
            PartitionedAbNConfiguration config,
            DisplayAbNAction listener) {
        
        super(config.getTextConfiguration().getContainerTypeName(false).toLowerCase(), listener);
        
        this.factory = factory;
    }

    @Override
    public ClusterTribalAbstractionNetwork deriveTAN() {
        T partitionedNode = super.getCurrentNode().get();
        
        Hierarchy<Concept> hierarchy = partitionedNode.getHierarchy();

        TribalAbstractionNetworkGenerator generator = new TribalAbstractionNetworkGenerator();
        ClusterTribalAbstractionNetwork tan = generator.deriveTANFromMultiRootedHierarchy(hierarchy, factory);
        
        return tan;
    }

    @Override
    public void setEnabledFor(T node) {
        this.setEnabled(node.hasOverlappingConcepts());
    }
}
