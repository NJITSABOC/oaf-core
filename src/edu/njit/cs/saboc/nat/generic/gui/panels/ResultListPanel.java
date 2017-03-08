package edu.njit.cs.saboc.nat.generic.gui.panels;

import edu.njit.cs.saboc.blu.core.gui.iconmanager.ImageManager;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.FilterableList;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.gui.listeners.DataLoadedListener;
import edu.njit.cs.saboc.nat.generic.gui.panels.ResultPanel.DataRetriever;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Chris O
 * @param <T>
 * @param <V>
 */
public abstract class ResultListPanel<T extends Concept, V> extends ResultPanel<T, ArrayList<V>> {
    
    public interface ResultSelectedListener<V> {
        public void resultSelected(V result);
        public void noResultSelected();
    }
 
    private final FilterableList<V> list;
    
    private final JPanel optionsPanel;
    
    private final ArrayList<ResultSelectedListener<V>> resultSelectedListeners = new ArrayList<>();
    
    private final ArrayList<DataLoadedListener<ArrayList<V>>> dataLoadedListeners = new ArrayList<>();
    
    private final boolean showBorder;
    
    public ResultListPanel(
            NATBrowserPanel<T> mainPanel,
            ConceptBrowserDataSource<T> dataSource,
            DataRetriever<T, ArrayList<V>> dataRetriever,
            ListCellRenderer<Filterable<V>> renderer,
            boolean showFilter,
            boolean showBorder) {
        
        super(mainPanel, dataSource, dataRetriever);
        
        this.showBorder = showBorder;

        this.list = new FilterableList<>();
        
        this.list.setListCellRenderer(renderer);
        
        this.list.addListMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                
                if (e.getClickCount() == 2) {
                    List<Filterable<V>> selectedValues = list.getSelectedValues();

                    if (!selectedValues.isEmpty()) {
                        Filterable<V> value = selectedValues.get(0);

                        resultSelectedListeners.forEach((listener) -> {
                            listener.resultSelected(value.getObject());
                        });
                    }
                }
            }
        });
        
        this.optionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        setLayout(new BorderLayout());
        
        this.add(optionsPanel, BorderLayout.NORTH);
        this.add(list, BorderLayout.CENTER);
        
        FlowLayout buttonLayout = new FlowLayout(FlowLayout.TRAILING);
        buttonLayout.setHgap(0);

        if (showFilter) {
            JButton filterButton = new JButton();
            
            filterButton.setPreferredSize(new Dimension(24, 24));
            filterButton.setIcon(ImageManager.getImageManager().getIcon("filter.png"));
            filterButton.setToolTipText("Filter these entries");
            filterButton.addActionListener( (ae) -> {
                list.setFilterPanelOpen(!list.filterShowing());
            });
            
            this.addOptionsComponent(filterButton);
        }
        
        updateBorder("Initializing...");
    }
    
    public final void addOptionsComponent(JComponent component) {
        optionsPanel.setVisible(true);
        optionsPanel.add(component, 0);
        optionsPanel.add(Box.createHorizontalStrut(16), 0);
    }

    public void addDataLoadedListener(DataLoadedListener<ArrayList<V>> listener) {
        this.dataLoadedListeners.add(listener);
    }
    
    public void removeDataLoadedListener(DataLoadedListener<ArrayList<V>> listener) {
        this.dataLoadedListeners.remove(listener);
    }
    
    public void addResultSelectedListener(ResultSelectedListener<V> listener) {
        this.resultSelectedListeners.add(listener);
    }

    public void removeResultSelectedListener(ResultSelectedListener<V> listener) {
        this.resultSelectedListeners.remove(listener);
    }
    
    @Override
    public void dataPending() {
        list.showPleaseWait();
        
        optionsPanel.setEnabled(false);
        
        updateBorder("Loading...");
    }

    @Override
    public void displayResults(ArrayList<V> data) {
        ArrayList<Filterable<V>> entries = new ArrayList<>();

        data.forEach( (result) -> {
            entries.add(createFilterableEntry(result));
        });
        
        list.setContents(entries);
        
        dataLoadedListeners.forEach( (listener) -> {
            listener.dataLoaded(data);
        });
        
        optionsPanel.setEnabled(true);
        
        updateBorder(Integer.toString(data.size()));
    }
    
    private void updateBorder(String status) {
        if(showBorder) {
            this.setBorder(this.createTitltedBorder(getCurrentDataRetriever().getDataType(), status));
        }
    }
    
    protected abstract Filterable<V> createFilterableEntry(V entry);
    
    private TitledBorder createTitltedBorder(String type, String countOrStatus) {
        
        return BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), 
                String.format("%s (%s)", type, countOrStatus));
    }
}
