package edu.njit.cs.saboc.nat.generic.gui.filterable.nestedlist.concept;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.gui.filterable.nestedlist.FilterableEntryPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Optional;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class ConceptEntryPanel<T extends Concept> extends FilterableEntryPanel<Filterable<T>> {

    private final JLabel conceptNameLabel;
    private final JLabel conceptIdLabel;

    public ConceptEntryPanel(Filterable<T> entry, Optional<String> filter) {
        super(entry, filter);

        this.setLayout(new BorderLayout());

        this.conceptNameLabel = new JLabel();
        this.conceptIdLabel = new JLabel();

        this.conceptNameLabel.setFont(this.conceptNameLabel.getFont().deriveFont(Font.PLAIN, 16));
        this.conceptIdLabel.setFont(this.conceptIdLabel.getFont().deriveFont(Font.PLAIN, 10));

        this.conceptIdLabel.setForeground(Color.BLUE);

        this.conceptNameLabel.setOpaque(false);
        this.conceptIdLabel.setOpaque(false);

        JPanel leftPanel = new JPanel(new GridLayout(2, 1));
        leftPanel.add(conceptNameLabel);
        leftPanel.add(conceptIdLabel);

        leftPanel.setOpaque(false);

        JPanel spacerPanel = new JPanel();
        spacerPanel.setOpaque(false);

        spacerPanel.add(Box.createHorizontalStrut(4));
        spacerPanel.add(leftPanel);

        this.add(spacerPanel, BorderLayout.WEST);

        T concept = entry.getObject();
        
        String conceptNameStr = concept.getName();
        String conceptIdStr = concept.getIDAsString();
        
        if(entry.getCurrentFilter().isPresent()) {
            conceptNameStr = Filterable.filter(conceptNameStr, entry.getCurrentFilter().get());
            conceptIdStr = Filterable.filter(conceptIdStr, entry.getCurrentFilter().get());
        }
        
        this.conceptNameLabel.setText(conceptNameStr);
        this.conceptIdLabel.setText(conceptIdStr);
    }
}
