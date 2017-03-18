package edu.njit.cs.saboc.nat.generic.gui.filterable.list.renderer;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.history.FocusConceptHistoryEntry;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

/**
 * Renderer for focus concept history entries
 * 
 * @author Chris O
 * @param <T>
 */
public class HistoryEntryRenderer<T extends Concept> extends BaseFilterableRenderer<FocusConceptHistoryEntry<T>> {
    
    private final JLabel conceptNameLabel;
    private final JLabel conceptIdLabel;
    
    private final JLabel timeViewedLabel;
    
    private final NATBrowserPanel<T> mainPanel;
    
    public HistoryEntryRenderer(NATBrowserPanel<T> mainPanel) {
        this.mainPanel = mainPanel;
        
        this.setLayout(new BorderLayout());
        
        this.conceptNameLabel = new JLabel();
        this.conceptIdLabel = new JLabel();
        this.timeViewedLabel = new JLabel();
        
        this.conceptNameLabel.setFont(this.conceptNameLabel.getFont().deriveFont(Font.PLAIN, 16));
        this.conceptIdLabel.setFont(this.conceptIdLabel.getFont().deriveFont(Font.PLAIN, 10));
        this.timeViewedLabel.setFont(this.timeViewedLabel.getFont().deriveFont(Font.ITALIC, 12));
        
        this.conceptIdLabel.setForeground(Color.BLUE);
        
        this.conceptNameLabel.setOpaque(false);
        this.conceptIdLabel.setOpaque(false);
        this.timeViewedLabel.setOpaque(false);
        
        JPanel conceptPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        conceptPanel.setOpaque(false);
        
        conceptPanel.add(conceptNameLabel);
        conceptPanel.add(Box.createHorizontalStrut(8));
        conceptPanel.add(conceptIdLabel);
        
        JPanel matchedTermsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        matchedTermsPanel.setOpaque(false);
        
        JLabel matchedTermsTitleLabel = new JLabel("Viewed at: ");
        matchedTermsTitleLabel.setFont(this.timeViewedLabel.getFont().deriveFont(Font.BOLD, 12));
        matchedTermsTitleLabel.setOpaque(false);
        
        matchedTermsPanel.add(matchedTermsTitleLabel);
        matchedTermsPanel.add(Box.createHorizontalStrut(4));
        matchedTermsPanel.add(timeViewedLabel);
        
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
            JList<? extends Filterable<FocusConceptHistoryEntry<T>>> list, 
            Filterable<FocusConceptHistoryEntry<T>> value, 
            int index, 
            boolean isSelected, 
            boolean cellHasFocus) {
        
        super.getListCellRendererComponent(
                list, 
                value, 
                index, 
                isSelected, 
                cellHasFocus);

        FocusConceptHistoryEntry<T> historyEntry = value.getObject();
        
        String conceptNameStr = historyEntry.getConcept().getName();
        String conceptIdStr = historyEntry.getConcept().getIDAsString();
        
        if(value.getCurrentFilter().isPresent()) {
            String filter = value.getCurrentFilter().get();
            
            conceptNameStr = Filterable.filter(conceptNameStr, filter);
            conceptIdStr = Filterable.filter(conceptIdStr, filter);
        }
        
        SimpleDateFormat entryTimeFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
        
        this.conceptNameLabel.setText(conceptNameStr);
        this.conceptIdLabel.setText(conceptIdStr);
        this.timeViewedLabel.setText(entryTimeFormat.format(historyEntry.getTimeViewed()));
        
        int fontStyle = Font.PLAIN;
        
        if (historyEntry.getPosition() == mainPanel.getFocusConceptManager().getHistory().getPosition()) {
            fontStyle = Font.BOLD;
        }
        
        this.conceptNameLabel.setFont(conceptNameLabel.getFont().deriveFont(fontStyle));
        this.conceptIdLabel.setFont(conceptIdLabel.getFont().deriveFont(fontStyle));
        this.timeViewedLabel.setFont(timeViewedLabel.getFont().deriveFont(fontStyle));

        return this;
    }

    @Override
    public void showDetailsFor(Filterable<FocusConceptHistoryEntry<T>> element) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}