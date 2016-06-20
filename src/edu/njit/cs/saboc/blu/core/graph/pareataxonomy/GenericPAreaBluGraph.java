package edu.njit.cs.saboc.blu.core.graph.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.GroupEntryLabelCreator;

/**
 *
 * @author Chris O
 */
public class GenericPAreaBluGraph extends BluGraph {
    
    public GenericPAreaBluGraph(final PAreaTaxonomy taxonomy, GroupEntryLabelCreator labelCreator) {
        super(taxonomy, labelCreator);
    }
}
