package edu.njit.cs.saboc.nat.generic.gui.filterable.nestedlist;

import edu.njit.cs.saboc.blu.core.gui.iconmanager.ImageManager;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.FilterPanel;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.FilterPanel.FilterPanelListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Optional;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import edu.njit.cs.saboc.blu.core.utils.rightclickmanager.EntityRightClickManager;
import edu.njit.cs.saboc.blu.core.utils.rightclickmanager.EntityRightClickMenuGenerator;

/**
 * A custom fitlerable list where filterable entries are nested under other
 * filterable entries (e.g., grandchildren grouped under children). 
 * 
 * @author Chris O
 * 
 * @param <T>
 * @param <V>
 */
public abstract class NestedFilterableList<T, V> extends JPanel {
    
    /**
     * Listener for selecting entities for handling selection of entries
     * from the list
     * 
     * @param <V> 
     */
    public interface EntrySelectionListener<V> {
        public void entryClicked(V entry);
        public void entryDoubleClicked(V entry);
        public void noEntrySelected();
    }
    
    private final JPanel contentPanel;
    
    private final FilterPanel filterPanel;
    
    private final ArrayList<FilterableNestedEntry<T, V>> entries = new ArrayList<>();
    private final ArrayList<FilterableNestedEntryPanel<FilterableNestedEntry<T, V>>> entryPanels = new ArrayList<>();
    
    private final ArrayList<EntrySelectionListener<V>> selectionListeners = new ArrayList<>();
    
    private final JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private final EntityRightClickManager<V> rightClickManager = new EntityRightClickManager<>();
    
    public NestedFilterableList() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        
        this.contentPanel = new JPanel();
        this.contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        this.contentPanel.setOpaque(false);

        JPanel internalPanel = new JPanel(new BorderLayout());

        internalPanel.add(contentPanel, BorderLayout.NORTH);
        internalPanel.add(Box.createGlue(), BorderLayout.CENTER);
        internalPanel.setBackground(Color.WHITE);
        
        this.add(new JScrollPane(internalPanel), BorderLayout.CENTER);
        
        this.filterPanel = new FilterPanel();
        
        JButton filterButton = new JButton();

        filterButton.setPreferredSize(new Dimension(24, 24));
        filterButton.setIcon(ImageManager.getImageManager().getIcon("filter.png"));
        filterButton.setToolTipText("Filter these entries");
        filterButton.addActionListener((ae) -> {
            showFilterPanel(!filterPanel.isShowing());
        });

        JPanel northPanel = new JPanel(new BorderLayout());
        
        northPanel.add(optionsPanel, BorderLayout.CENTER);
        northPanel.add(filterButton, BorderLayout.EAST);
        
        this.filterPanel.addFilterPanelListener(new FilterPanelListener() {

            @Override
            public void filterChanged(String filter) {
                filter(filter);
            }

            @Override
            public void filterClosed() {
                showFilterPanel(false);
            }
            
        });
        
        this.add(northPanel, BorderLayout.NORTH);
        
        this.add(filterPanel, BorderLayout.SOUTH);
        
        this.showFilterPanel(false);
    }
    
    public void addEntrySelectionListener(EntrySelectionListener<V> listener) {
        this.selectionListeners.add(listener);
    }
    
    public void removeEntrySelectionListener(EntrySelectionListener<V> listener) {
        this.selectionListeners.remove(listener);
    }
    
    public void addOptionPanelComponent(JComponent component) {
        this.optionsPanel.add(component);
    }
    
    public final void showFilterPanel(boolean value) {
        
        if(value) {
            filterPanel.reset();
        } else {
            filter("");
        }
        
        filterPanel.setVisible(value);
    }

    public void displayContents(ArrayList<FilterableNestedEntry<T, V>> contents) {
        displayContents(contents, Optional.empty());

        filterPanel.reset();
    }
    
    public void clearContents() {
        contentPanel.removeAll();
        contentPanel.repaint();
        contentPanel.revalidate();
        
        entries.clear();

        entryPanels.clear();

        this.revalidate();
    }
    
    public void filter(String filter) {
        entries.forEach( (entry) -> {
            entry.clearFilter();
        });
        
        Optional<String> optFilter;
        
        if(filter.isEmpty()) {
            optFilter = Optional.empty();
        } else {
            optFilter = Optional.of(filter);
        }
        
        displayContents(new ArrayList<>(this.entries), optFilter);
    }
    
    private void displayContents(ArrayList<FilterableNestedEntry<T, V>> entries, Optional<String> filter) {
        clearContents();
        
        this.entries.addAll(entries);

        entries.stream().filter( (entry) -> {
            return !filter.isPresent() || entry.containsFilter(filter.get());
            
        }).forEach( (entry) -> {
            FilterableNestedEntryPanel<FilterableNestedEntry<T, V>> entryPanel = getEntry(entry, filter);
            
            this.entryPanels.add(entryPanel);
            
            this.contentPanel.add(entryPanel);
            
            /**
             * Initialize nested filterable list listeners for each 
             * filterable entry in the list
             */
            entryPanel.getSubPanels().forEach( (panel) -> {
                
                panel.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            
                            if(e.getClickCount() > 1) {
                                selectionListeners.forEach((listener) -> {
                                    listener.entryDoubleClicked((V)panel.getEntry().getObject());
                                });
                            } else {
                                if (e.isControlDown()) {
                                    if (panel.isSelected()) {
                                        clearSelection();

                                        selectionListeners.forEach((listener) -> {
                                            listener.noEntrySelected();
                                        });

                                        return;
                                    }
                                }
                                
                                entrySelected(panel);
                                
                                selectionListeners.forEach( (listener) -> {
                                    listener.entryClicked((V)panel.getEntry().getObject());
                                });
                            }
                        }
                        
                        if (e.getButton() == MouseEvent.BUTTON3){
                            rightClickManager.setRightClickedItem((V)panel.getEntry().getObject());
                            rightClickManager.showPopup(e);
                        }                     
                        
                        panel.requestFocusInWindow();
                    }
                });
                
                panel.setFocusable(true);
                
                panel.addKeyListener(new KeyAdapter() {

                    @Override
                    public void keyReleased(KeyEvent e) {
                        
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                        
                    }
                    
                    @Override
                    public void keyTyped(KeyEvent e) {
                        if (panel.isSelected()) {
                            showFilterPanel(true);
                            
                            filterPanel.filteringStarted(Character.toString(e.getKeyChar()));
                        }
                    }
                });
            });
        });
        
        this.revalidate();
        this.repaint();
    }
    
    public void clearSelection() {
        this.entryPanels.forEach( (panel) -> {
            panel.getSubPanels().forEach( (subPanel) -> {
               subPanel.setSelected(false);
            });
        });
    }
    
    public void entrySelected(FilterableEntryPanel panel) {
        clearSelection();
        
        panel.setSelected(true);
    }
    public final void setRightClickMenuGenerator(EntityRightClickMenuGenerator<V> generator) {
        this.rightClickManager.setMenuGenerator(generator);
    }
    
    public abstract FilterableNestedEntryPanel<FilterableNestedEntry<T, V>> 
        getEntry(FilterableNestedEntry<T, V> entry, Optional<String> filter);
}
