package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.configuration;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyTextConfiguration;

/**
 *
 * @author Chris O
 */
public abstract class DiffPAreaTaxonomyTextConfiguration extends PAreaTaxonomyTextConfiguration {
    
    public DiffPAreaTaxonomyTextConfiguration(DiffPAreaTaxonomy taxonomy) {
        super(taxonomy);
    }
    
    public DiffPAreaTaxonomy getPAreaTaxonomy() {
        return (DiffPAreaTaxonomy)super.getPAreaTaxonomy();
    }

    @Override
    public String getAbNName() {
        return "Diff Partial-area Taxonomy";
    }

    @Override
    public String getAbNSummary() {
       return "*** DIFF PARTIAL-AREA TAXONOMY SUMMARY TEXT ***";
    }
    
    @Override
    public String getAbNHelpDescription() {
        return "*** DIFF PARTIAL-AREA HELP DESCRIPTION ***";
    }

    
    @Override
    public String getAbNTypeName(boolean plural) {
        if(plural) {
            return "Partial-area Taxonomies";
        } else {
            return "Partial-area Taxonomy";
        }
    }
    
    @Override
    public String getDisjointNodeTypeName(boolean plural) {
        if(plural) {
            return "Disjoint partial-areas";
        } else {
            return "Disjoint partial-area";
        }
    }

    @Override
    public String getContainerTypeName(boolean plural) {
        if(plural) {
            return "Areas";
        } else {
            return "Area";
        }
    }

    @Override
    public String getNodeTypeName(boolean plural) {
        if(plural) {
            return "Partial-areas";
        } else {
            return "Partial-area";
        }
    }
}
