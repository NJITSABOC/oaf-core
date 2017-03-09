package edu.njit.cs.saboc.nat.generic.gui.panels.search;

import edu.njit.cs.saboc.blu.core.gui.iconmanager.ImageManager;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.FilterableList;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.NATConceptSearchResult;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.gui.filterable.list.FilterableSearchResultEntry;
import edu.njit.cs.saboc.nat.generic.gui.filterable.list.renderer.SearchResultRenderer;
import edu.njit.cs.saboc.nat.generic.gui.panels.BaseNATPanel;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;

/**
 * A panel that enables searching for concepts by name or id
 * 
 * @author Chris O
 * @param <T>
 */
public class SearchPanel<T extends Concept> extends BaseNATPanel<T> {
    
    public interface SearchResultSelectedListener<T extends Concept> {
        public void searchResultSelected(NATConceptSearchResult<T> result);
    }
    
    private class SearchResultsList<T extends Concept> extends FilterableList<NATConceptSearchResult<T>> {

        private final ArrayList<SearchResultSelectedListener<T>> resultSelectedListeners = new ArrayList<>();
        
        private final NATBrowserPanel<T> mainPanel;
        private final ConceptBrowserDataSource<T> dataSource;
        
        public SearchResultsList(
            NATBrowserPanel<T> mainPanel, 
            ConceptBrowserDataSource<T> dataSource) {
            
            this.mainPanel = mainPanel;
            this.dataSource = dataSource;
            
            super.addListMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    if(e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
                        if(!getSelectedValues().isEmpty()) {
                            resultSelectedListeners.forEach((listener) -> {
                                NATConceptSearchResult<T> result = getSelectedValues().get(0).getObject();
                                listener.searchResultSelected(result);
                            });
                        }
                    }
                }
            });
            
            super.setListCellRenderer(new SearchResultRenderer());
        }
        
        public void displayResults(ArrayList<NATConceptSearchResult<T>> searchResults) {
            ArrayList<FilterableSearchResultEntry<T>> filterableEntries  = new ArrayList<>();
            
            searchResults.forEach( (result) -> {
                filterableEntries.add(new FilterableSearchResultEntry<>(result, dataSource));
            });
            
            super.setContents(filterableEntries);
        }
        
        public void addSearchResultSelectedListener(SearchResultSelectedListener<T> listener) {
            this.resultSelectedListeners.add(listener);
        }
        
        public void removeSearchResultSelectedListener(SearchResultSelectedListener<T> listener) {
            this.resultSelectedListeners.remove(listener);
        }
    }

    private final JRadioButton optAnywhere;
    private final JRadioButton optStarting;
    private final JRadioButton optExact;
    private final JRadioButton optId;
    
    private final SpinnerTextField txtSearchBox;
    
    private final JButton btnDoSearch;

    private final SearchResultsList<T> searchResultList;
        
    private volatile int searchID = 0;
    private Thread searchThread = null;

    public SearchPanel(
            NATBrowserPanel<T> mainPanel, 
            ConceptBrowserDataSource<T> dataSource) {
        
        super(mainPanel, dataSource);
        
        this.setLayout(new GridBagLayout());

        JPanel buttonPanel = new JPanel();
        
        ButtonGroup group = new ButtonGroup();
        
        optStarting = makeSearchRadioButton("Starts with", group, buttonPanel);
        optAnywhere = makeSearchRadioButton("Anywhere", group, buttonPanel);
        optExact = makeSearchRadioButton("Exact", group, buttonPanel);
        optId = makeSearchRadioButton("ID", group, buttonPanel);
        
        optStarting.setSelected(true);

        GridBagConstraints c = new GridBagConstraints();
        
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        
        this.add(buttonPanel, c);

        c.weightx = 1;
        c.fill = GridBagConstraints.BOTH;
        c.gridy = 1;
        c.gridwidth = 1;
        
        this.txtSearchBox = new SpinnerTextField();
        
        this.add(txtSearchBox, c);

        txtSearchBox.getTextField().addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    doSearch();
                }
            }
        });

        c.gridx = 1;
        c.weightx = 0;
        
        btnDoSearch = new JButton(ImageManager.getImageManager().getIcon("search.png"));
        btnDoSearch.addActionListener((ae) -> {
            doSearch();
        });

        this.add(btnDoSearch, c);

        c.gridx = 2;
        c.weightx = 0;

        
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 3;
        c.weightx = c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;


        this.searchResultList = new SearchResultsList<>(mainPanel, dataSource);

        this.add(searchResultList, c);
    }
    
    public void addSearchResultSelectedListener(SearchResultSelectedListener<T> listener) {
        this.searchResultList.addSearchResultSelectedListener(listener);
    }
    
    public void removeSearchResultSelectedListener(SearchResultSelectedListener<T> listener) {
        this.searchResultList.removeSearchResultSelectedListener(listener);
    }
    
    @Override
    protected void setFontSize(int fontSize) {
        optStarting.setFont(optStarting.getFont().deriveFont(Font.BOLD, fontSize));
        optAnywhere.setFont(optAnywhere.getFont().deriveFont(Font.BOLD, fontSize));
        optExact.setFont(optExact.getFont().deriveFont(Font.BOLD, fontSize));
        optId.setFont(optId.getFont().deriveFont(Font.BOLD, fontSize));
    }
        
    private JRadioButton makeSearchRadioButton(String text, ButtonGroup group, Container panel) {
        JRadioButton rb = new JRadioButton(text);
        
        group.add(rb);
        panel.add(rb);

        return rb;
    }

    private void doSearch() {
        String searchText = txtSearchBox.getTextField().getText().trim();

        if (searchText.isEmpty()) {
            
            JOptionPane.showMessageDialog(getMainPanel(), 
                    "Please enter a search term.",
                    "Invalid Input", 
                    JOptionPane.ERROR_MESSAGE);
            
            return;
        } else {
            if (this.optAnywhere.isSelected() && searchText.length() < 3) {
                
                JOptionPane.showMessageDialog(getMainPanel(), 
                        "Please enter at least three characters.",
                        "Invalid Input", 
                        JOptionPane.ERROR_MESSAGE);
                
                return;
            }
        }

        searchResultList.showPleaseWait();

        // Gray everything out, set up the spinner.
        txtSearchBox.getTextField().setEnabled(false);

        btnDoSearch.setEnabled(false);
        btnDoSearch.setVisible(false);

        setButtonsEnabled(false);

        txtSearchBox.setSpinnerVisible(true);

        // Interupt the old search thread if it's still going
        if (searchThread != null) {
            searchThread.interrupt();
        }

        // Create a new thread and get the results.
        searchThread = new Thread(new SearchRunner(searchText, ++searchID));
        searchThread.start();
    }
    
    private void setButtonsEnabled(boolean value) {
        optAnywhere.setEnabled(value);
        optExact.setEnabled(value);
        optStarting.setEnabled(value);
        optId.setEnabled(value);
    }

    private class SearchRunner implements Runnable {

        private final String term;
        private final int id;

        public SearchRunner(String term, int id) {
            this.term = term;
            this.id = id;
        }

        @Override
        public void run() {
            ArrayList<NATConceptSearchResult<T>> results;

            ConceptBrowserDataSource<T> dataSource = getDataSource();
            
            if (optExact.isSelected()) {
                results = dataSource.searchExact(term);
            } else if (optAnywhere.isSelected()) {
                results = dataSource.searchAnywhere(term);
            } else if (optStarting.isSelected()) {
                results = dataSource.searchStarting(term);
            } else {
                results = dataSource.searchID(term);
            }

            SwingUtilities.invokeLater(() -> {
                displaySearchResults(results, id);
            });
        }
    };

    private void displaySearchResults(ArrayList<NATConceptSearchResult<T>> results, int id) {
        if (id != searchID) {
            return;
        }

        if (!btnDoSearch.isEnabled()) {
            txtSearchBox.getTextField().setEnabled(true);
            
            btnDoSearch.setEnabled(true);
            btnDoSearch.setVisible(true);
            
            setButtonsEnabled(true);
        }

        searchResultList.displayResults(results);

        txtSearchBox.setSpinnerVisible(false);
    }
}
