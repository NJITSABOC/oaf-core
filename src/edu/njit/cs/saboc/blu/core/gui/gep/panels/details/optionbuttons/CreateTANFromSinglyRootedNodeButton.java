package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.TribalAbstractionNetworkGenerator;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNListener;

/**
 *
 * @author Chris O
 */
public class CreateTANFromSinglyRootedNodeButton extends CreateTANButton {
    
    public CreateTANFromSinglyRootedNodeButton(AbNConfiguration config, DisplayAbNListener listener) {
        super(config.getTextConfiguration().getNodeTypeName(false).toLowerCase(), listener);
    }

    @Override
    public ClusterTribalAbstractionNetwork deriveTAN() {
        SinglyRootedNode currentNode = (SinglyRootedNode)super.getCurrentNode().get();
        
        TribalAbstractionNetworkGenerator generator = new TribalAbstractionNetworkGenerator();
        ClusterTribalAbstractionNetwork tan = generator.deriveTANFromSingleRootedHierarchy(currentNode.getHierarchy());
        
        return tan;
    }

    @Override
    public void setEnabledFor(Node node) {
        SinglyRootedNode singlyRootedNode = (SinglyRootedNode)node;
        
        if(singlyRootedNode.getHierarchy().getChildren(singlyRootedNode.getHierarchy().getRoot()).size() > 1) {
            this.setEnabled(true);
        } else {
            this.setEnabled(false);
        }
    }
}
