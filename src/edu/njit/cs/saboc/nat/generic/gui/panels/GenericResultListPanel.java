package edu.njit.cs.saboc.nat.generic.gui.panels;

import edu.njit.cs.saboc.blu.core.gui.iconmanager.IconManager;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.FilterableList;
import edu.njit.cs.saboc.nat.generic.GenericNATBrowser;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.fields.NATDataField;
import edu.njit.cs.saboc.nat.generic.gui.listeners.DataLoadedListener;
import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Optional;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public abstract class GenericResultListPanel<T> extends BaseNavPanel implements Toggleable {

    private FilterableList list;
    
    private NATDataField<ArrayList<T>> field;
    
    private Optional<DataLoadedListener<ArrayList<T>>> dataLoadedListener;

    public GenericResultListPanel(
            final GenericNATBrowser mainPanel, 
            FilterableList list,
            NATDataField<ArrayList<T>> field, 
            ConceptBrowserDataSource dataSource, 
            DataLoadedListener<ArrayList<T>> dataLoadedListener, 
            boolean showFilter) {
        
        super(mainPanel, dataSource);
        
        this.list = list;
        this.field = field;
        this.dataLoadedListener = Optional.ofNullable(dataLoadedListener);

        focusConcept.addDisplayPanel(field, this);

        setBackground(mainPanel.getNeighborhoodBGColor());
        setLayout(new BorderLayout());
        add(list, BorderLayout.CENTER);

        if (showFilter) {

            JButton filterButton = new JButton();
            filterButton.setBackground(mainPanel.getNeighborhoodBGColor());
            filterButton.setPreferredSize(new Dimension(24, 24));
            filterButton.setIcon(IconManager.getIconManager().getIcon("filter.png"));
            filterButton.setToolTipText("Filter these entries");
            filterButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    list.toggleFilterPanel();
                }
            });

            FlowLayout buttonLayout = new FlowLayout(FlowLayout.TRAILING);
            buttonLayout.setHgap(0);
            JPanel northPanel = new JPanel(buttonLayout);
            northPanel.setBackground(mainPanel.getNeighborhoodBGColor());
            northPanel.add(filterButton);
            northPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

            add(northPanel, BorderLayout.NORTH);
        }
    }

    public GenericResultListPanel (
            final GenericNATBrowser mainPanel, 
            NATDataField<ArrayList<T>> field,
            ConceptBrowserDataSource dataSource, 
            DataLoadedListener<ArrayList<T>> dataLoadedListener, 
            boolean showFilter) {
        
        this(mainPanel, new FilterableList(), field, dataSource, dataLoadedListener, showFilter);
    }
    
    @Override
    public void dataPending() {
        list.showPleaseWait();
    }

    @Override
    public void dataEmpty() {
        list.showDataEmpty();
    }

    @Override
    public void dataReady() {
        ArrayList<T> results = field.getData(focusConcept.getConcept());
        
        ArrayList<Filterable<T>> entries = new ArrayList<>();

        results.forEach((T result) -> {
            entries.add(createFilterableEntry(result));
        });
        
        list.setContents(entries);
        
        if(dataLoadedListener.isPresent()) {
            dataLoadedListener.get().dataLoaded(results);
        }
    }
    
    public void focusConceptChanged() {
        
    }

    public void toggle() {
        list.toggleFilterPanel();
    }
    
    protected abstract Filterable<T> createFilterableEntry(T item);
}