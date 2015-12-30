package edu.njit.cs.saboc.blu.core.graph.tan;

import edu.njit.cs.saboc.blu.core.abn.tan.TribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.graph.options.GraphOptions;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.GroupEntryLabelCreator;
import javax.swing.JFrame;

/**
 *
 * @author Chris O
 */
public class GenericClusterBluGraph <TAN_T extends TribalAbstractionNetwork> extends BluGraph {
    
    public GenericClusterBluGraph(final JFrame parentFrame, final TAN_T taxonomy, boolean hideRegions, 
            boolean showConceptCountLabels, GraphOptions options, GroupEntryLabelCreator labelCreator) {
        
        super(taxonomy, hideRegions, showConceptCountLabels, labelCreator);
    }
}
