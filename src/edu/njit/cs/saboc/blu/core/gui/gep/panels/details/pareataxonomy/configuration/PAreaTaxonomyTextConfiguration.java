package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNTextConfiguration;

/**
 *
 * @author Chris O
 */
public abstract class PAreaTaxonomyTextConfiguration implements PartitionedAbNTextConfiguration<PArea, Area> {
    
    private final PAreaTaxonomy taxonomy;
    
    public PAreaTaxonomyTextConfiguration(PAreaTaxonomy taxonomy) {
        this.taxonomy = taxonomy;
    }
    
    public PAreaTaxonomy getPAreaTaxonomy() {
        return taxonomy;
    }

    @Override
    public String getAbNName() {
        return "Partial-area Taxonomy";
    }

    @Override
    public String getAbNSummary() {
       return "*** PARTIAL-AREA TAXONOMY SUMMARY TEXT ***";
    }
    
    @Override
    public String getAbNHelpDescription() {
        return "*** PARTIAL-AREA HELP DESCRIPTION ***";
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
