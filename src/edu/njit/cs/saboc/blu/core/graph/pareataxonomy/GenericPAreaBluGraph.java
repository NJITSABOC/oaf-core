package edu.njit.cs.saboc.blu.core.graph.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.graph.options.GraphOptions;
import javax.swing.JFrame;

/**
 *
 * @author Chris O
 */
public class GenericPAreaBluGraph <TAXONOMY_T extends GenericPAreaTaxonomy> extends BluGraph {
    
    public GenericPAreaBluGraph(final JFrame parentFrame, final TAXONOMY_T taxonomy, boolean hideRegions, 
            boolean showConceptCountLabels, GraphOptions options) {
        
        super(taxonomy, hideRegions, showConceptCountLabels);
        
        
    }
}
