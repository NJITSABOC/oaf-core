package edu.njit.cs.saboc.nat.generic.gui.filterablelist.renderer;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.data.NATConceptSearchResult;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class SearchResultRenderer<T extends Concept> extends BaseFilterableRenderer<NATConceptSearchResult<T>> {
    
    private final JLabel conceptNameLabel;
    private final JLabel conceptIdLabel;
    private final JLabel matchedTermsLabel;
    
    public SearchResultRenderer() {
        
        this.setLayout(new BorderLayout());
        
        this.conceptNameLabel = new JLabel();
        this.conceptIdLabel = new JLabel();
        this.matchedTermsLabel = new JLabel();
        
        this.conceptNameLabel.setFont(this.conceptNameLabel.getFont().deriveFont(Font.PLAIN, 16));
        this.conceptIdLabel.setFont(this.conceptIdLabel.getFont().deriveFont(Font.PLAIN, 10));
        this.matchedTermsLabel.setFont(this.matchedTermsLabel.getFont().deriveFont(Font.ITALIC, 12));
        
        this.conceptIdLabel.setForeground(Color.BLUE);
        this.matchedTermsLabel.setForeground(Color.RED);
        
        this.conceptNameLabel.setOpaque(false);
        this.conceptIdLabel.setOpaque(false);
        this.matchedTermsLabel.setOpaque(false);
        
        JPanel conceptPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        conceptPanel.setOpaque(false);
        
        conceptPanel.add(conceptNameLabel);
        conceptPanel.add(Box.createHorizontalStrut(8));
        conceptPanel.add(conceptIdLabel);

        
        JPanel matchedTermsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        matchedTermsPanel.setOpaque(false);
        
        JLabel matchedTermsTitleLabel = new JLabel("Matched terms: ");
        matchedTermsTitleLabel.setFont(this.matchedTermsLabel.getFont().deriveFont(Font.BOLD, 12));
        matchedTermsTitleLabel.setOpaque(false);
        
        matchedTermsPanel.add(matchedTermsTitleLabel);
        matchedTermsPanel.add(Box.createHorizontalStrut(4));
        matchedTermsPanel.add(matchedTermsLabel);
        
        JPanel leftPanel = new JPanel(new GridLayout(2, 1));
        leftPanel.setOpaque(false);
        
        leftPanel.add(conceptPanel);
        leftPanel.add(matchedTermsPanel);
        
        JPanel spacerPanel = new JPanel();
        spacerPanel.setOpaque(false);
        
        spacerPanel.add(Box.createHorizontalStrut(4));
        spacerPanel.add(leftPanel);
        
        this.add(spacerPanel, BorderLayout.WEST);
        
        this.setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(
            JList<? extends Filterable<NATConceptSearchResult<T>>> list, 
            Filterable<NATConceptSearchResult<T>> value, 
            int index, 
            boolean isSelected, 
            boolean cellHasFocus) {
        
        super.getListCellRendererComponent(
                list, 
                value, 
                index, 
                isSelected, 
                cellHasFocus);

        NATConceptSearchResult<T> searchResult = value.getObject();
        
        String conceptNameStr = searchResult.getConcept().getName();
        String conceptIdStr = searchResult.getConcept().getIDAsString();
        
        ArrayList<String> matchedTerms = new ArrayList<>(searchResult.getMatchedTerms());
        Collections.sort(matchedTerms);
        
        String matchedTermsStr = matchedTerms.toString();
        matchedTermsStr = matchedTermsStr.substring(1, matchedTermsStr.length() - 1);
        
        if(value.getCurrentFilter().isPresent()) {
            String filter = value.getCurrentFilter().get();
            
            conceptNameStr = Filterable.filter(conceptNameStr, filter);
            conceptIdStr = Filterable.filter(conceptIdStr, filter);
            matchedTermsStr = Filterable.filter(matchedTermsStr, filter);
        }
        
        this.conceptNameLabel.setText(conceptNameStr);
        this.conceptIdLabel.setText(conceptIdStr);
        this.matchedTermsLabel.setText(matchedTermsStr);
        
        return this;
    }
}