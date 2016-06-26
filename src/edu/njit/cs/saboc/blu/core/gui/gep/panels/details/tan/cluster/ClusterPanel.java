
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.cluster;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.ConceptHierarchyPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeHierarchyPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.SinglyRootedNodePanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.TANConfiguration;

/**
 *
 * @author Chris O
 */
public class ClusterPanel extends SinglyRootedNodePanel {
    
    public ClusterPanel(TANConfiguration config) {
        super(new ClusterDetailsPanel(config), 
                new NodeHierarchyPanel(config),
                new ConceptHierarchyPanel(config), 
                config);
    }
}