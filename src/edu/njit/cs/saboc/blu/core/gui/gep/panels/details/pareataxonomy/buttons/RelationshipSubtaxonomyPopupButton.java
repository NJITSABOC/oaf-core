package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.buttons;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.RelationshipSubtaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.RelationshipSubtaxonomyDerivationPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.RelationshipSubtaxonomyDerivationPanel.RelationshipSubtaxonomyDerivationAction;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.PopupToggleButton;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author Chris O
 */
public class RelationshipSubtaxonomyPopupButton extends PopupToggleButton {
    
    private final RelationshipSubtaxonomyDerivationPanel relationshipSelectionPanel;
        
    public RelationshipSubtaxonomyPopupButton(final JFrame parentFrame,
            RelationshipSubtaxonomyDerivationAction deriveAction) {
        
        super(parentFrame, "Relationship Subtaxonomy");
        
        relationshipSelectionPanel = new RelationshipSubtaxonomyDerivationPanel(deriveAction);
        
        relationshipSelectionPanel.setPreferredSize(new Dimension(250, 300));
        
        this.setPopupContent(relationshipSelectionPanel);
    }

    public void initialize(PAreaTaxonomyConfiguration config, PAreaTaxonomy taxonomy) {
        
        if(taxonomy instanceof RelationshipSubtaxonomy) {
            relationshipSelectionPanel.initializeSubtaxonomy( config, (RelationshipSubtaxonomy) taxonomy);
        } else {
            relationshipSelectionPanel.initialize(config, taxonomy);
        }
    }
}