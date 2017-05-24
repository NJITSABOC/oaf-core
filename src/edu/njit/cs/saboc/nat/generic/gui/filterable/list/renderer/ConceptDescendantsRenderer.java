package edu.njit.cs.saboc.nat.generic.gui.filterable.list.renderer;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class ConceptDescendantsRenderer<T extends Concept> extends DetailsConceptRenderer<T> {
    
    private final JLabel descendantCountLabel;
    
    public ConceptDescendantsRenderer(NATBrowserPanel<T> mainPanel) {
        
        super(mainPanel);
        
        this.descendantCountLabel = new JLabel();
        this.descendantCountLabel.setFont(this.descendantCountLabel.getFont().deriveFont(Font.PLAIN, 10));
        this.descendantCountLabel.setOpaque(false);
        
        super.addDetailsLabel(descendantCountLabel);
    }

    @Override
    public void showDetailsFor(Filterable<T> filterableEntry) {
        super.showDetailsFor(filterableEntry);
        
        if(!getMainPanel().getDataSource().isPresent()) {
            return;
        }

        int descendantCount = getMainPanel().getDataSource().get().getDescendantCount(filterableEntry.getObject());

        if (descendantCount == 0) {
            this.descendantCountLabel.setForeground(new Color(64, 200, 64));
            this.descendantCountLabel.setText("Leaf");
        } else {
            this.descendantCountLabel.setForeground(Color.BLACK);
            this.descendantCountLabel.setText(String.format("Descendants: %d", descendantCount));
        }
    }
}
