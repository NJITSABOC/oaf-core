package edu.njit.cs.saboc.nat.generic.gui.panels;

import edu.njit.cs.saboc.blu.core.gui.iconmanager.IconManager;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.FilterableList;
import edu.njit.cs.saboc.nat.generic.GenericNATBrowser;
import edu.njit.cs.saboc.nat.generic.Options;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.fields.NATDataField;
import edu.njit.cs.saboc.nat.generic.gui.listeners.DataLoadedListener;
import edu.njit.cs.saboc.nat.generic.gui.listeners.NATOptionsAdapter;
import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Optional;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public abstract class GenericResultListPanel<T, V> extends BaseNavPanel<T> implements Toggleable {

    private FilterableList list;
    
    private NATDataField<T, ArrayList<V>> field;
    
    private Optional<DataLoadedListener<ArrayList<V>>> dataLoadedListener;
    
    private JPanel optionsPanel;

    public GenericResultListPanel(
            final GenericNATBrowser<T> mainPanel, 
            FilterableList list,
            NATDataField<T, ArrayList<V>> field, 
            ConceptBrowserDataSource<T> dataSource, 
            DataLoadedListener<ArrayList<V>> dataLoadedListener, 
            boolean showFilter) {
        
        super(mainPanel, dataSource);
        
        Options options = mainPanel.getOptions();
        
        options.addOptionsListener(new NATOptionsAdapter() {
            public void fontSizeChanged(int fontSize) {
                list.setListFontSize(fontSize);
            }
        });
        
        this.list = list;
        this.list.setListFontSize(options.getFontSize());
        
        this.field = field;
        this.dataLoadedListener = Optional.ofNullable(dataLoadedListener);

        focusConcept.addDisplayPanel(field, this);

        setBackground(mainPanel.getNeighborhoodBGColor());
        setLayout(new BorderLayout());
        add(list, BorderLayout.CENTER);
        
        FlowLayout buttonLayout = new FlowLayout(FlowLayout.TRAILING);
        buttonLayout.setHgap(0);

        this.optionsPanel = new JPanel(buttonLayout);
        this.optionsPanel.setBackground(mainPanel.getNeighborhoodBGColor());
        this.optionsPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        
        this.optionsPanel.setVisible(false);

        add(optionsPanel, BorderLayout.NORTH);

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
            
            this.addOptionsPanelItem(filterButton);
        }
    }
    
    public void addOptionsPanelItem(JComponent component) {
        optionsPanel.setVisible(true);
        optionsPanel.add(component);
        optionsPanel.add(Box.createHorizontalStrut(16));
    }

    public GenericResultListPanel (
            final GenericNATBrowser mainPanel, 
            NATDataField<T, ArrayList<V>> field,
            ConceptBrowserDataSource<T> dataSource, 
            DataLoadedListener<ArrayList<V>> dataLoadedListener, 
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
        ArrayList<V> results = (ArrayList<V>)focusConcept.getConceptList(field);
        
        ArrayList<Filterable<V>> entries = new ArrayList<>();

        results.forEach((V result) -> {
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
    
    protected abstract Filterable<V> createFilterableEntry(V item);
}