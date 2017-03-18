package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.FilterableList;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.gui.filterable.list.FilterableConceptEntry;
import edu.njit.cs.saboc.nat.generic.gui.filterable.list.renderer.SimpleConceptRenderer;
import edu.njit.cs.saboc.nat.generic.gui.panels.BaseNATPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.search.SearchPanel;
import edu.njit.cs.saboc.nat.generic.history.FocusConceptHistoryEntry;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 */
public class SelectConceptPanel<T extends Concept> extends BaseNATPanel<T> {
    
    private final JButton btnClearSelection;
    
    private final JLabel selectedConceptLabel;
    
    private final SearchPanel<T> searchPanel;
    private final FilterableList<T> recentConceptList;
    
    private Optional<T> selectedConcept = Optional.empty();
    
    public SelectConceptPanel(
            NATBrowserPanel<T> mainPanel, 
            ConceptBrowserDataSource<T> dataSource) {
        
        super(mainPanel, dataSource);
                
        this.btnClearSelection = new JButton("Clear Selection");
        this.btnClearSelection.addActionListener( (ae) -> {
            clearSelectedConcept();
        });
        
        JPanel selectedConceptPanel = new JPanel(new BorderLayout());
        selectedConceptPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.RED), 
                "Selected Concept"));
        selectedConceptPanel.setOpaque(false);

        this.selectedConceptLabel = new JLabel();
        this.selectedConceptLabel.setForeground(Color.BLUE);
        this.selectedConceptLabel.setOpaque(false);
        this.selectedConceptLabel.setFont(this.selectedConceptLabel.getFont().deriveFont(14.0f));
        
        selectedConceptPanel.add(selectedConceptLabel, BorderLayout.CENTER);
        
        selectedConceptPanel.add(btnClearSelection, BorderLayout.EAST);
        
        this.searchPanel = new SearchPanel<>(mainPanel, dataSource);
        this.searchPanel.setBorder(
            BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(Color.BLACK), 
                    "Search for Concept"));
        
        
        this.searchPanel.addSearchResultSelectedListener( (result) -> {
            setSelectedConcept(result.getConcept());
        });
        
        this.recentConceptList = new FilterableList<>();
        this.recentConceptList.setListCellRenderer(
                new SimpleConceptRenderer(
                        mainPanel,
                        dataSource));
        
        this.recentConceptList.addListMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                
                if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
                    List<Filterable<T>> selectedValues = recentConceptList.getSelectedValues();

                    if (!selectedValues.isEmpty()) {
                        Filterable<T> value = selectedValues.get(0);
                        
                        setSelectedConcept(value.getObject());
                    }
                }
            }
        });
        
        ArrayList<FocusConceptHistoryEntry<T>> historyEntries  = mainPanel.getFocusConceptManager().getHistory().getHistory();

        int upperBound = historyEntries.size();
        int lowerBound = Math.max(0, upperBound - 15);
        
        ArrayList<T> recentConcepts = new ArrayList<>();
        ArrayList<FilterableConceptEntry<T>> entries = new ArrayList<>();
        
        List<FocusConceptHistoryEntry<T>> subList = historyEntries.subList(lowerBound, upperBound);
        
        for(int c = subList.size() - 1; c >= 0; c--) {
            T concept = subList.get(c).getConcept();
            
            if(!recentConcepts.contains(concept)) {
                recentConcepts.add(concept);
                
                entries.add(new FilterableConceptEntry<>(concept, dataSource));
            }
        }

        this.recentConceptList.setContents(entries);

        
        this.setLayout(new BorderLayout());
        
        this.add(selectedConceptPanel, BorderLayout.NORTH);
        
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Search For Concept", searchPanel);
        tabbedPane.addTab("Select Recently Viewed Concept", recentConceptList);
        
        this.add(tabbedPane, BorderLayout.CENTER);
        
        clearSelectedConcept();
    }
    
    public Optional<T> getSelectedConcept() {
        return selectedConcept;
    }
    
    public void setSelectedConcept(T concept) {
        this.selectedConceptLabel.setText(concept.getName());
        this.selectedConcept = Optional.of(concept);
    }
    
    public void clearSelectedConcept() {
        this.selectedConceptLabel.setText("(not selected)");
        this.selectedConcept = Optional.empty();
    }
}
