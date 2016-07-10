package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.buttons;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaSubtaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNListener;

/**
 *
 * @author Chris O
 */
public class CreateAncestorSubtaxonomyButton extends CreateSubtaxonomyButton {
    
    private final PAreaTaxonomyConfiguration config;

    public CreateAncestorSubtaxonomyButton(PAreaTaxonomyConfiguration config, DisplayAbNListener<PAreaTaxonomy> displayTaxonomyListener) {
        super("BluAncestorSubtaxonomy.png", "Create ancestor subtaxonomy", displayTaxonomyListener);
        
        this.config = config;
    }
    
    @Override
    public PAreaSubtaxonomy createSubtaxonomy() {
        PArea parea = (PArea)super.getCurrentNode().get();
        
        return config.getPAreaTaxonomy().createAncestorSubtaxonomy(parea);
    }

    @Override
    public void setEnabledFor(Node node) {
        PArea parea = (PArea)node;
        PAreaTaxonomy taxonomy = config.getPAreaTaxonomy();
        
        if(taxonomy.getPAreaHierarchy().getParents(parea).isEmpty()) {
            this.setEnabled(false);
        } else {
            this.setEnabled(true);
        }
    }
    
}
