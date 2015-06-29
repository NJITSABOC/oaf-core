package edu.njit.cs.saboc.nat.generic.gui.panels;

import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.FilterableList;
import edu.njit.cs.saboc.nat.generic.GenericNATBrowser;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.fields.NATDataField;
import edu.njit.cs.saboc.nat.generic.gui.listeners.DataLoadedListener;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public abstract class DualGenericResultListPanel<T, V, U> extends NATLayoutPanel implements Toggleable {
    
    private final GenericResultListPanel<T, V> primaryResultList;
    private final GenericResultListPanel<T, U> secondaryResultList;
    
    private final DualNavPanel<T> dualNavPanel;
    
    public DualGenericResultListPanel(final GenericNATBrowser<T> mainPanel, 
            ConceptBrowserDataSource<T> dataSource, 
            String secondaryFieldName,
            NATDataField<T, ArrayList<V>> primaryField,
            NATDataField<T, ArrayList<U>> secondaryField,
            FilterableList primaryFilterableList,
            FilterableList secondaryFilterableList,
            boolean showFilterButton) {
                
        primaryResultList = new GenericResultListPanel<T, V>(mainPanel, primaryFilterableList, primaryField, dataSource, false) {
            public Filterable<V> createFilterableEntry(V entry) {
                return createPrimaryFilterableEntry(entry);
            }
        };

        secondaryResultList = new GenericResultListPanel<T, U>(mainPanel, secondaryFilterableList, secondaryField, dataSource, false) {
            public Filterable<U> createFilterableEntry(U entry) {
                return createSecondaryFilterableEntry(entry);
            }
        };
        
        dualNavPanel = new DualNavPanel(mainPanel, secondaryFieldName, primaryResultList, secondaryResultList);
        
        if (showFilterButton) {
            JButton filterButton = BaseNavPanel.createFilterButton(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    if (dualNavPanel.isSecondarySelected()) {
                        secondaryResultList.toggle();
                    } else {
                        primaryResultList.toggle();
                    }
                }
            });
            
            filterButton.setBackground(mainPanel.getNeighborhoodBGColor());
            
            JPanel menuPanel = new JPanel();
            menuPanel.setOpaque(false);
            menuPanel.add(filterButton);
            
            dualNavPanel.setOptionsMenuPanel(menuPanel);
        }

        this.setLayout(new BorderLayout());
        
        this.add(dualNavPanel, BorderLayout.CENTER);
    }
    
    public void addPrimaryDataLoadedListener(DataLoadedListener<ArrayList<V>> listener) {
        primaryResultList.addDataLoadedListener(listener);
    }
    
    public void addSecondaryDataLoadedListener(DataLoadedListener<ArrayList<U>> listener) {
        secondaryResultList.addDataLoadedListener(listener);
    }
    
    public void toggle() {
        if(dualNavPanel.isSecondarySelected()) {           
            secondaryResultList.toggle();
        } else {
            primaryResultList.toggle();
        }
    }
    
    public boolean isSecondarySelected() {
        return dualNavPanel.isSecondarySelected();
    }
    
    public void setSecondaryCount(int count) {
        dualNavPanel.setSecondaryCount(count);
    }

    protected abstract Filterable<V> createPrimaryFilterableEntry(V entry);
    
    protected abstract Filterable<U> createSecondaryFilterableEntry(U entry);
}
