package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.TribalAbstractionNetworkGenerator;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNAction;

/**
 *
 * @author Chris O
 */
public class CreateTANFromSinglyRootedNodeButton<T extends SinglyRootedNode> extends CreateTANButton<T> {
    
    public CreateTANFromSinglyRootedNodeButton(AbNConfiguration config, DisplayAbNAction displayTAN) {
        super(config.getTextConfiguration().getNodeTypeName(false).toLowerCase(), displayTAN);
    }

    @Override
    public ClusterTribalAbstractionNetwork deriveTAN() {
        SinglyRootedNode currentNode = (SinglyRootedNode)super.getCurrentNode().get();
        
        TribalAbstractionNetworkGenerator generator = new TribalAbstractionNetworkGenerator();
        ClusterTribalAbstractionNetwork tan = generator.deriveTANFromSingleRootedHierarchy(currentNode.getHierarchy());
        
        return tan;
    }

    @Override
    public void setEnabledFor(T node) {
        if(node.getHierarchy().getChildren(node.getHierarchy().getRoot()).size() > 1) {
            this.setEnabled(true);
        } else {
            this.setEnabled(false);
        }
    }
}
