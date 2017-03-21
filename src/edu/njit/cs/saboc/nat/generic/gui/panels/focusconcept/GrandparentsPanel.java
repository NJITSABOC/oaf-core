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
import edu.njit.cs.saboc.nat.generic.gui.filterable.nestedlist.concept.GrandparentEntryPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.ResultPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.dataretrievers.CommonBrowserDataRetrievers;
import edu.njit.cs.saboc.nat.generic.gui.panels.dataretrievers.GrandparentResult;
import edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.rightclickmenu.GrandparentsRightClickMenu;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.swing.BorderFactory;

/**
 * Panel for displaying the grandparents of the focus concept
 * 
 * @author Chris O
 * 
 * @param <T>
 */
public class GrandparentsPanel<T extends Concept> extends ResultPanel<T, ArrayList<GrandparentResult<T>>> {
    
    private final NestedFilterableList<GrandparentResult<T>, T> grandParentList;
    
    public GrandparentsPanel(NATBrowserPanel<T> mainPanel, ConceptBrowserDataSource<T> dataSource) {
        
        super(mainPanel, 
                dataSource, 
                CommonBrowserDataRetrievers.getGrandparentsRetriever(dataSource));

        grandParentList = new NestedFilterableList<GrandparentResult<T>, T>() {

            @Override
            public FilterableNestedEntryPanel<FilterableNestedEntry<GrandparentResult<T>, T>> 
                    getEntry(FilterableNestedEntry<GrandparentResult<T>, T> entry, Optional<String> filter) {
                        
                if (filter.isPresent()) {
                    entry.setCurrentFilter(filter.get());
                }
                
                FilterableExtendedNeighborhoodEntry<T, GrandparentResult<T>> groupEntry = 
                        (FilterableExtendedNeighborhoodEntry<T, GrandparentResult<T>>)entry;
                
                GrandparentEntryPanel<T, GrandparentResult<T>> relGroupPanel = 
                        new GrandparentEntryPanel<>(mainPanel, groupEntry, dataSource);
                
                return(FilterableNestedEntryPanel<FilterableNestedEntry<GrandparentResult<T>, T>>)
                        (FilterableNestedEntryPanel<?>)relGroupPanel;
            }
        };
        
        this.grandParentList.setRightClickMenuGenerator(new GrandparentsRightClickMenu<>(mainPanel, dataSource));
                
        this.grandParentList.addEntrySelectionListener(new EntrySelectionListener<T>() {

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
        
        this.add(grandParentList, BorderLayout.CENTER);
    }

    @Override
    public void dataPending() {
        this.grandParentList.clearContents();
    }

    @Override
    public void displayResults(ArrayList<GrandparentResult<T>> data) {
        
        ArrayList<FilterableNestedEntry<GrandparentResult<T>, T>> entries = new ArrayList<>();
        
        Set<T> grandparents = new HashSet<>();
        
        data.forEach( (result) -> {
            
            FilterableConceptEntry<T> parentEntry = new FilterableConceptEntry<>(result.getParent(), getDataSource());
            
            ArrayList<Filterable<T>> grandParentEntries = new ArrayList<>();
            
            result.getGrandparents().forEach((grandchild) -> {
                grandParentEntries.add(new FilterableConceptEntry<>(grandchild, getDataSource()));
            });
            
            grandparents.addAll(result.getGrandparents());
            
            entries.add(new FilterableExtendedNeighborhoodEntry<>(result, parentEntry, grandParentEntries));
        });
        
        this.grandParentList.displayContents(entries);
        
        this.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                String.format("Parents (%d) and Grandparents (%d)", data.size(), grandparents.size()))
        );
    }
}
