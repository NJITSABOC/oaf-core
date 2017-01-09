package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetabn;

import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetGroup;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.ConceptHierarchyPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeHierarchyPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.SinglyRootedNodePanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetbased.configuration.TargetAbNConfiguration;

/**
 *
 * @author Chris O
 */
public class TargetGroupPanel extends SinglyRootedNodePanel<TargetGroup> {

    public TargetGroupPanel(TargetAbNConfiguration configuration) {
        
        super(new TargetGroupDetailsPanel(configuration),
                new NodeHierarchyPanel(configuration), 
                new ConceptHierarchyPanel(configuration), 
                configuration);
    }
}
