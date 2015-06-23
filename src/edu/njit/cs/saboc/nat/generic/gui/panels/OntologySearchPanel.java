package edu.njit.cs.saboc.nat.generic.gui.panels;

import edu.njit.cs.saboc.blu.core.gui.iconmanager.IconManager;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.FilterableList;
import edu.njit.cs.saboc.nat.generic.GenericNATBrowser;
import edu.njit.cs.saboc.nat.generic.Options;
import edu.njit.cs.saboc.nat.generic.data.BrowserSearchResult;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.gui.filterablelist.BrowserNavigableFilterableList;
import edu.njit.cs.saboc.nat.generic.gui.filterablelist.FilterableSearchEntry;
import edu.njit.cs.saboc.nat.generic.gui.listeners.FilterableListSelectionAction;
import edu.njit.cs.saboc.nat.generic.gui.listeners.NATOptionsAdapter;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author Den
 */
public class OntologySearchPanel<T> extends JPanel {

    private JRadioButton optAnywhere;
    private JRadioButton optStarting;
    private JRadioButton optExact;
    private SpinnerTextField txtSearchBox;
    private JButton btnDoSearch;
    private JButton btnCancelSearch;

    private FilterableList searchList;
    
    private volatile int searchID = 0;

    private Thread searchThread = null;


    // The special textbox with the spinner in it
    private class SpinnerTextField extends JPanel {

        private JTextField textField;
        private JLabel spinner;

        public SpinnerTextField() {
            super(new BorderLayout());
            JPanel p = new JPanel(new BorderLayout());
            
            textField = new JTextField();
            spinner = new JLabel(IconManager.getIconManager().getIcon("spinner.gif"));
            spinner.setOpaque(true);
            spinner.setBackground(textField.getBackground());
            
            setBorder(textField.getBorder());
            
            textField.setBorder(null);
            
            add(spinner, BorderLayout.EAST);
            add(textField, BorderLayout.CENTER);

            spinner.setVisible(false);
        }

        public JTextField getTextField() {
            return textField;
        }

        public void setSpinnerVisible(boolean v) {
            spinner.setVisible(v);
        }
    }

    private final GenericNATBrowser<T> mainPanel;
    private final ConceptBrowserDataSource<T> dataSource;

    public OntologySearchPanel(GenericNATBrowser<T> mainPanel, ConceptBrowserDataSource<T> dataSource, 
            FilterableListSelectionAction<T> resultSelectionAction) {
        
        super(new GridBagLayout());

        Options options = mainPanel.getOptions();
        
        options.addOptionsListener(new NATOptionsAdapter() {
            public void fontSizeChanged(int fontSize) {
                searchList.setListFontSize(fontSize);
                
                optStarting.setFont(optStarting.getFont().deriveFont(Font.BOLD, fontSize));
                optAnywhere.setFont(optAnywhere.getFont().deriveFont(Font.BOLD, fontSize));
                optExact.setFont(optExact.getFont().deriveFont(Font.BOLD, fontSize));
            }
        });

        this.mainPanel = mainPanel;
        this.dataSource = dataSource;

        JPanel buttonPanel = new JPanel();
        ButtonGroup group = new ButtonGroup();
        optStarting = makeRadioButton("Starts with", group, buttonPanel);
        optStarting.setSelected(true);
        optAnywhere = makeRadioButton("Anywhere", group, buttonPanel);
        optExact = makeRadioButton("Exact", group, buttonPanel);

        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        this.add(buttonPanel, c);

        c.weightx = 1;
        c.fill = GridBagConstraints.BOTH;
        c.gridy = 1;
        c.gridwidth = 1;
        txtSearchBox = new SpinnerTextField();
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
        btnDoSearch = new JButton(IconManager.getIconManager().getIcon("search.png"));
        btnDoSearch.addActionListener((ActionEvent e) -> {
            doSearch();
        });

        this.add(btnDoSearch, c);

        btnCancelSearch = new JButton(IconManager.getIconManager().getIcon("cancel.png"));
        btnCancelSearch.setToolTipText("Cancel serach");
        btnCancelSearch.addActionListener((ActionEvent ae) -> {
            if (searchThread != null) {
                searchThread.interrupt();
            }
        });

        this.add(btnCancelSearch, c);
        btnCancelSearch.setVisible(false);
        btnCancelSearch.setEnabled(false);

        c.gridx = 2;
        c.weightx = 0;

        JButton filterButton = BaseNavPanel.createFilterButton(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                searchList.toggleFilterPanel();
            }
        });

        this.add(filterButton, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 3;
        c.weightx = c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;

        searchList = new BrowserNavigableFilterableList(resultSelectionAction);
        searchList.setListFontSize(options.getFontSize());

        searchList.setFilterPanelOpen(false, null);

        this.add(searchList, c);
    }
    
    private JRadioButton makeRadioButton(String text, ButtonGroup group, Container panel) {
        JRadioButton rb = new JRadioButton(text);
        
        group.add(rb);
        panel.add(rb);

        return rb;
    }

    private void doSearch() {
        String searchText = txtSearchBox.getTextField().getText().trim();

        if (searchText.isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "Please enter a search term.",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        } else {
            if (this.optAnywhere.isSelected() && searchText.length() < 3) {
                JOptionPane.showMessageDialog(mainPanel, "When searching anywhere please enter at least three characters.",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        Optional<T> c = dataSource.getConceptFromId(searchText);

        if (c.isPresent()) {
            BrowserSearchResult<T> sr = new BrowserSearchResult<>(dataSource.getConceptName(c.get()),
                    dataSource.getConceptId(c.get()), c.get());

            ArrayList<Filterable> entry = new ArrayList<>();
            entry.add(new FilterableSearchEntry(sr));

            searchList.setContents(entry);
            txtSearchBox.getTextField().selectAll();

            return;
        }

        searchList.setContents(new ArrayList<>());

        // Gray everything out, set up the spinner.
        txtSearchBox.getTextField().setEnabled(false);

        btnDoSearch.setEnabled(false);
        btnDoSearch.setVisible(false);

        btnCancelSearch.setEnabled(true);
        btnCancelSearch.setVisible(true);

        optAnywhere.setEnabled(false);
        optExact.setEnabled(false);
        optStarting.setEnabled(false);

        txtSearchBox.setSpinnerVisible(true);

        // Interupt the old search thread if it's still going
        if (searchThread != null) {
            searchThread.interrupt();
        }

        // Create a new thread and get the results.
        searchThread = new Thread(new SearchRunner(searchText, ++searchID));
        searchThread.start();
    }

    private class SearchRunner implements Runnable {

        private String term;
        int id;

        public SearchRunner(String term, int id) {
            this.term = term;
            this.id = id;
        }

        @Override
        public void run() {
            ArrayList<BrowserSearchResult<T>> results = new ArrayList<>();

            if (optExact.isSelected()) {
                results = dataSource.searchExact(term);
            } else if (optAnywhere.isSelected()) {
                results = dataSource.searchAnywhere(term);
            } else if (optStarting.isSelected()) {
                results = dataSource.searchStarting(term);
            }

            // Send one last one to indicate it's finished
            SwingUtilities.invokeLater(new ResultSender(results, id));
        }
    };

    // This will be used to send the results back through the main thread
    private class ResultSender implements Runnable {

        private ArrayList<BrowserSearchResult<T>> results;
        private int id;

        public ResultSender(ArrayList<BrowserSearchResult<T>> results, int id) {
            this.results = results;
            this.id = id;
        }

        @Override
        public void run() {
            displaySearchResults(results, id);
        }
    }

    private void displaySearchResults(ArrayList<BrowserSearchResult<T>> results, int id) {
        if (id != searchID) {
            return;
        }

        if (!btnDoSearch.isEnabled()) {
            txtSearchBox.getTextField().setEnabled(true);
            btnCancelSearch.setEnabled(false);
            btnCancelSearch.setVisible(false);
            btnDoSearch.setEnabled(true);
            btnDoSearch.setVisible(true);
            optAnywhere.setEnabled(true);
            optExact.setEnabled(true);
            optStarting.setEnabled(true);
        }

        ArrayList<Filterable> searchEntries = new ArrayList<>();

        for (BrowserSearchResult sr : results) {
            searchEntries.add(new FilterableSearchEntry(sr));
        }

        searchList.setContents(searchEntries);

        txtSearchBox.setSpinnerVisible(false);
    }
    
    public Optional<T> getSelectedResultConcept() {
        
        if(searchList.getSelectedIndex() >= 0) {
            List<Filterable> selected = searchList.getSelectedValues();
            
            FilterableSearchEntry selectedEntry = (FilterableSearchEntry)selected.get(0);
            
            BrowserSearchResult<T> searchResult = (BrowserSearchResult<T>)selectedEntry.getObject();
            
            return Optional.of(searchResult.getConcept());
        }
        
        return Optional.empty();
    }
}
