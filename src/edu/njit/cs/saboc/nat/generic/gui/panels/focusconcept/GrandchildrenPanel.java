package edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.gui.filterable.list.FilterableConceptEntry;
import edu.njit.cs.saboc.nat.generic.gui.filterable.nestedlist.FilterableNestedEntry;
import edu.njit.cs.saboc.nat.generic.gui.filterable.nestedlist.FilterableNestedEntryPanel;
import edu.njit.cs.saboc.nat.generic.gui.filterable.nestedlist.NestedFilterableList;
import edu.njit.cs.saboc.nat.generic.gui.filterable.nestedlist.NestedFilterableList.EntrySelectionListener;
import edu.njit.cs.saboc.nat.generic.gui.filterable.nestedlist.concept.FilterableExtendedNeighborhoodEntry;
import edu.njit.cs.saboc.nat.generic.gui.filterable.nestedlist.concept.GrandchildEntryPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.ResultPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.dataretrievers.CommonBrowserDataRetrievers;
import edu.njit.cs.saboc.nat.generic.gui.panels.dataretrievers.GrandchildResult;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.swing.BorderFactory;

/**
 * Panel for displaying the grandchildren of the focus concept
 * 
 * @author Chris O
 * 
 * @param <T>
 */
public class GrandchildrenPanel<T extends Concept> extends ResultPanel<T, ArrayList<GrandchildResult<T>>> {
    
    private final NestedFilterableList<GrandchildResult<T>, T> grandchildList;
    
    public GrandchildrenPanel(NATBrowserPanel<T> mainPanel) {
        super(mainPanel, 
                CommonBrowserDataRetrievers.getGrandchildrenRetriever(mainPanel));

        grandchildList = new NestedFilterableList<GrandchildResult<T>, T>() {

            @Override
            public FilterableNestedEntryPanel<FilterableNestedEntry<GrandchildResult<T>, T>> 
                    getEntry(FilterableNestedEntry<GrandchildResult<T>, T> entry, Optional<String> filter) {
                        
                if (filter.isPresent()) {
                    entry.setCurrentFilter(filter.get());
                }
                
                FilterableExtendedNeighborhoodEntry<T, GrandchildResult<T>> groupEntry = 
                        (FilterableExtendedNeighborhoodEntry<T, GrandchildResult<T>>)entry;
                
                GrandchildEntryPanel<T, GrandchildResult<T>> relGroupPanel = 
                        new GrandchildEntryPanel<>(mainPanel, groupEntry);
                
                return(FilterableNestedEntryPanel<FilterableNestedEntry<GrandchildResult<T>, T>>)
                        (FilterableNestedEntryPanel<?>)relGroupPanel;
            }
        };      

 
        
        this.grandchildList.addEntrySelectionListener(new EntrySelectionListener<T>() {

            @Override
            public void entryClicked(T entry) {
                
            }

            @Override
            public void entryDoubleClicked(T entry) {
                mainPanel.getFocusConceptManager().navigateTo(entry);
            }

            @Override
            public void noEntrySelected() {
                
            }
        });
        
        this.setLayout(new BorderLayout());
        
        this.add(grandchildList, BorderLayout.CENTER);
    }

    @Override
    public void dataPending() {
        this.grandchildList.clearContents();
    }

    @Override
    public void displayResults(ArrayList<GrandchildResult<T>> data) {
        ArrayList<FilterableNestedEntry<GrandchildResult<T>, T>> entries = new ArrayList<>();
        
        Set<T> grandchildren = new HashSet<>();
        
        data.forEach( (result) -> {
            
            FilterableConceptEntry<T> childEntry = new FilterableConceptEntry<>(getMainPanel(), result.getChild());
            
            ArrayList<Filterable<T>> grandchildEntries = new ArrayList<>();
            
            result.getGrandChildren().forEach( (grandchild) -> {
                grandchildEntries.add(new FilterableConceptEntry<>(getMainPanel(), grandchild));
            });
            
            entries.add(new FilterableExtendedNeighborhoodEntry<>(result, childEntry, grandchildEntries));
            
            grandchildren.addAll(result.getGrandChildren());
        });
        
        this.grandchildList.displayContents(entries);
        
        this.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                String.format("Children (%d) and Grandchildren (%d)", data.size(), grandchildren.size()))
        );
    }
}
