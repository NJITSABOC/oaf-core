package edu.njit.cs.saboc.blu.core.graph.disjointabn;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.graph.options.GraphOptions;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.GroupEntryLabelCreator;
import javax.swing.JFrame;

/**
 *
 * @author Chris O
 */
public class GenericDisjointAbNBluGraph<DISJOINTABN_T extends DisjointAbstractionNetwork> extends BluGraph {
    
    public GenericDisjointAbNBluGraph(final JFrame parentFrame, final DISJOINTABN_T taxonomy, boolean hideRegions, 
            boolean showConceptCountLabels, GraphOptions options, GroupEntryLabelCreator labelCreator) {
        
        super(taxonomy, hideRegions, showConceptCountLabels, labelCreator);
    }
}
