package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.buttons;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.AggregatePArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.AggregatePAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaSubtaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNListener;

/**
 *
 * @author Chris O
 */
public class CreateExpandedSubtaxonomyButton extends CreateSubtaxonomyButton {
    
    private final PAreaTaxonomyConfiguration config;
    
    public CreateExpandedSubtaxonomyButton(PAreaTaxonomyConfiguration config, 
            DisplayAbNListener<PAreaTaxonomy> displayTaxonomyListener) {
        
        super("BluExpandedSubtaxonomy.png", 
                "Created expanded subtaxonomy from aggregate partial-area", 
                displayTaxonomyListener);
        
        this.config = config;
    }

    @Override
    public PAreaSubtaxonomy createSubtaxonomy() {
        AggregatePArea parea = (AggregatePArea)super.getCurrentNode().get();
        
        AggregatePAreaTaxonomy taxonomy = (AggregatePAreaTaxonomy)config.getPAreaTaxonomy();
        
        return taxonomy.createExpandedSubtaxonomy(parea);
    }

    @Override
    public void setEnabledFor(Node node) {
        AggregatePArea parea = (AggregatePArea)node;
        
        if(!parea.getAggregatedNodes().isEmpty()) {
            this.setEnabled(true);
        } else {
            this.setEnabled(false);
        }
    }
}