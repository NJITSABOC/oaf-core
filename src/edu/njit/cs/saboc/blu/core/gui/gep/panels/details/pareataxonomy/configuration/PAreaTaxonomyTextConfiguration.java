package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNTextConfiguration;

/**
 *
 * @author Chris O
 */
public abstract class PAreaTaxonomyTextConfiguration implements PartitionedAbNTextConfiguration {

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
