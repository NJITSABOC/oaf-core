package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyTextConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyUIConfiguration;

/**
 *
 * @author Chris O
 */
public abstract class PAreaTaxonomyConfiguration extends PartitionedAbNConfiguration {
    
    public PAreaTaxonomyConfiguration(PAreaTaxonomy taxonomy, PAreaTaxonomyUIConfiguration uiConfig, PAreaTaxonomyTextConfiguration textConfig) {
        super(taxonomy, uiConfig, textConfig);
    }
    
    public PAreaTaxonomy getPAreaTaxonomy() {
        return (PAreaTaxonomy)super.getAbstractionNetwork();
    }
    
    public PAreaTaxonomyUIConfiguration getUIConfiguration() {
        return (PAreaTaxonomyUIConfiguration)super.getUIConfiguration();
    }

    public PAreaTaxonomyTextConfiguration getTextConfiguration() {
        return (PAreaTaxonomyTextConfiguration)super.getTextConfiguration();
    }

    @Override
    public int getPartitionedNodeLevel(PartitionedNode node) {
        Area area = (Area)node;
        
        return area.getRelationships().size();
    }
}
