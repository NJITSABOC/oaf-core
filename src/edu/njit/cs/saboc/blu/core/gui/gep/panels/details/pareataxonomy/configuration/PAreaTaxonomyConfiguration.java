package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration;

import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;

/**
 *
 * @author Chris O
 */
public abstract class PAreaTaxonomyConfiguration extends PartitionedAbNConfiguration {
    
    public PAreaTaxonomyConfiguration(PAreaTaxonomy taxonomy) {
        super(taxonomy);
    }
    
    public PAreaTaxonomy getPAreaTaxonomy() {
        return (PAreaTaxonomy)super.getAbstractionNetwork();
    }
    
    public void setUIConfiguration(PAreaTaxonomyUIConfiguration uiConfig) {
        super.setUIConfiguration(uiConfig);
    }
    
    public void setTextConfiguration(PAreaTaxonomyTextConfiguration textConfig) {
        super.setTextConfiguration(textConfig);
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
