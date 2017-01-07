package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.buttons;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaSubtaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.CreateAndDisplayAbNThread;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.NodeOptionButton;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNAction;

/**
 *
 * @author Chris O
 */
public abstract class CreateSubtaxonomyButton extends NodeOptionButton {
    
    private final DisplayAbNAction<PAreaTaxonomy> displayTaxonomyListener;
    
    public CreateSubtaxonomyButton(
            String iconName,
            String toolTip,
            DisplayAbNAction<PAreaTaxonomy> displayTaxonomyListener) {
        
        super(iconName, toolTip);
        
        this.displayTaxonomyListener = displayTaxonomyListener;
        
        this.addActionListener((ae) -> {
            createAndDisplaySubtaxonomy();
        });
    }
    
    public final void createAndDisplaySubtaxonomy() {
        if (getCurrentNode().isPresent()) {
            CreateAndDisplayAbNThread display = new CreateAndDisplayAbNThread("Creating Subtaxonomy", displayTaxonomyListener) {

                @Override
                public AbstractionNetwork getAbN() {
                    return createSubtaxonomy();
                }
            };
                    
            display.startThread();
        }
    }
    
    public abstract PAreaTaxonomy createSubtaxonomy();
}
