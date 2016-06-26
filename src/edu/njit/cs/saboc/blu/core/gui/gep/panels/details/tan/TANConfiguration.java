
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan;

import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.tan.Band;
import edu.njit.cs.saboc.blu.core.abn.tan.TribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;

/**
 *
 * @author Chris O
 */
public class TANConfiguration extends PartitionedAbNConfiguration {
    
    public TANConfiguration(TribalAbstractionNetwork tan, TANUIConfiguration uiConfig, TANTextConfiguration textConfig) {
        super(tan, uiConfig, textConfig);
    }
    
    public TribalAbstractionNetwork getAbstractionNetwork() {
        return (TribalAbstractionNetwork)super.getAbstractionNetwork();
    }

    public TANUIConfiguration getUIConfiguration() {
        return (TANUIConfiguration)super.getUIConfiguration();
    }

    public TANTextConfiguration getTextConfiguration() {
        return (TANTextConfiguration)super.getTextConfiguration();
    }

    @Override
    public int getPartitionedNodeLevel(PartitionedNode node) {
        Band band = (Band)node;
        
        return band.getPatriarchs().size();
    }
}
