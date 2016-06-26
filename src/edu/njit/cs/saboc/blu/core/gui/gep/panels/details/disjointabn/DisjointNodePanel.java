package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.DisjointAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.SinglyRootedNodePanel;

/**
 *
 * @author Chris O
 */
public class DisjointNodePanel extends SinglyRootedNodePanel {
    
    public DisjointNodePanel(
            DisjointNodeConceptHierarchyPanel conceptHierarchyPanel,
            DisjointAbNConfiguration configuration) {
        
        super(new DisjointNodeDetailsPanel(configuration), 
                new DisjointNodeHierarchyPanel(configuration), 
                null,
                configuration);
    }
}
