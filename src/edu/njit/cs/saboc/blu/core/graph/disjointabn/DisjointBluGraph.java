package edu.njit.cs.saboc.blu.core.graph.disjointabn;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.SinglyRootedNodeLabelCreator;
import javax.swing.JFrame;

/**
 *
 * @author Chris O
 */
public class DisjointBluGraph extends BluGraph {

    public DisjointBluGraph(
            final JFrame parentFrame, 
            final DisjointAbstractionNetwork disjointAbN, 
            final SinglyRootedNodeLabelCreator labelCreator) {
        
        super(disjointAbN, labelCreator);
        
        this.layout = new GenericDisjointAbNLayout(this, disjointAbN);

        ((GenericDisjointAbNLayout)layout).doLayout();
    }

    public DisjointAbstractionNetwork getDisjointPAreaTaxonomy() {
        return (DisjointAbstractionNetwork)getAbstractionNetwork();
    }
}
