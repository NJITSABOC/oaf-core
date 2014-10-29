package edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.search;

import edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.PopupToggleButton;
import edu.njit.cs.saboc.blu.core.gui.iconmanager.IconManager;
import edu.njit.cs.saboc.blu.core.utils.filterable.entry.FilterableStringEntry;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.FilterableListModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Chris
 */
public class GenericInternalSearchButton extends PopupToggleButton {

    private List<SearchButtonResult> searchResultsList;
    
    private JList resultsList;
    
    protected FilterableListModel conceptModel;
    protected FilterableListModel searchResultModel;
    
    private final JTextField searchText = new JTextField();
    
    private JPanel filterPanel = new JPanel();
    private JTextField filterField = new JTextField();
    private JButton closeButton = new JButton();
    
    private JPanel searchTypePanel = new JPanel();
    private ButtonGroup searchTypeGroup = new ButtonGroup();
    
    private class SearchActionEntry {
        public JRadioButton radioButton;
        public SearchAction searchAction;
        
        public SearchActionEntry(JRadioButton button, SearchAction searchAction) {
            this.radioButton = button;
            this.searchAction = searchAction;
        }
    }
    
    private ArrayList<SearchActionEntry> searchActionList = new ArrayList<SearchActionEntry>();

    public GenericInternalSearchButton(Frame parent) {
        super(parent, "Search");
        
        conceptModel = new FilterableListModel(true);
        searchResultModel = new FilterableListModel(true);

        JPanel popupPanel  = new JPanel(new BorderLayout());

        popupPanel.setBorder(BorderFactory.createEtchedBorder());
        
        initializeResultList();

        final JButton searchButton = new JButton(IconManager.getIconManager().getIcon("search.png"));

        final JPanel resultsPanel = new JPanel(new BorderLayout());
        resultsPanel.setBorder(new TitledBorder("Search Results"));
        
        JButton filterButton = new JButton();
        filterButton.setIcon(IconManager.getIconManager().getIcon("filter.png"));
        filterButton.setToolTipText("Filter these entries");
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                toggleFilterPanel();
            }
        });
        
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new GridBagLayout());
        northPanel.setOpaque(false);
        
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = GridBagConstraints.RELATIVE;
        c.weightx = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        northPanel.add(Box.createHorizontalBox(), c);
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        c.weightx = 0;
        northPanel.add(filterButton, c);

        resultsPanel.add(northPanel, BorderLayout.NORTH);
        

        closeButton.setIcon(IconManager.getIconManager().getIcon("cross.png"));
        closeButton.setToolTipText("Close");

        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.X_AXIS));
        filterPanel.add(closeButton);
        filterPanel.add(Box.createHorizontalStrut(10));
        filterPanel.add(new JLabel("Filter:  "));
        filterPanel.add(filterField);
        filterPanel.setVisible(false);

        resultsPanel.add(filterPanel, BorderLayout.SOUTH);

        filterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                if(ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    setFilterPanelOpen(false, null);
                }
            }
        });
        
        filterField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                conceptModel.changeFilter(filterField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                conceptModel.changeFilter(filterField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                conceptModel.changeFilter(filterField.getText());
            }
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setFilterPanelOpen(false, null);
            }
        });

        resultsPanel.add(new JScrollPane(resultsList));
        
        resultsPanel.setPreferredSize(new Dimension(500, 300));

        searchText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                if(ke.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchButton.doClick();
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (searchText.getText().trim().isEmpty()) {
                    return;
                }

                final SearchAction searchAction = getSelectedAction();

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        ArrayList<Filterable> filterableResults = new ArrayList<Filterable>();

                        ArrayList<SearchButtonResult> results = searchAction.doSearch(searchText.getText().trim());

                        searchResultsList = results;

                        for (Object result : results) {
                            filterableResults.add(new FilterableStringEntry(result.toString()));
                        }

                        filterPanel.setVisible(false);

                        conceptModel.changeFilter("");
                        conceptModel.clear();
                        searchResultModel.clear();

                        conceptModel.addAll(filterableResults);     // used to populate FilterableListModel object instead of its constructor
                        searchResultModel.addAll(filterableResults);

                        resultsList.setModel(conceptModel);
                    }
                });
            }
     
        });
        
        JPanel searchEntryPanel = new JPanel(new BorderLayout());

        searchEntryPanel.setBorder(BorderFactory.createTitledBorder("Search Anywhere"));
        searchEntryPanel.add(searchText, BorderLayout.CENTER);
        searchEntryPanel.add(searchButton, BorderLayout.EAST);
        searchText.setPreferredSize(new Dimension(450, 50));

        popupPanel.add(searchTypePanel, BorderLayout.NORTH);
        popupPanel.add(searchEntryPanel, BorderLayout.CENTER);
        popupPanel.add(resultsPanel, BorderLayout.SOUTH);

        this.setPopupContent(popupPanel);
    }
    
    protected void addSearchAction(SearchAction searchAction) {
        JRadioButton actionButton = new JRadioButton(searchAction.getSearchActionName());
        
        actionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                resultsList.setListData(new String[0]);
                searchText.setText("");
            }
        });
        
        searchActionList.add(new SearchActionEntry(actionButton, searchAction));
        searchTypeGroup.add(actionButton);
        
        searchTypePanel.add(actionButton);
        
        if(searchActionList.size() == 1) {
            actionButton.setSelected(true);
        }
        
        searchTypePanel.validate();
    }
    
    private SearchAction getSelectedAction() {
        for (SearchActionEntry searchAction : searchActionList) {
            if (searchAction.radioButton.isSelected()) {
                return searchAction.searchAction;
            }
        }
        
        return null;
    }

    private void initializeResultList() {
         resultsList = new JList() {
            // This method is called as the cursor moves within the list.
            @Override
            public String getToolTipText(MouseEvent evt) {

                int index = locationToIndex(evt.getPoint());

                if(getCellBounds(index, index) == null
                        || !getCellBounds(index, index).contains(evt.getPoint())) {
                    return null;
                }

                if(index > -1) {
                    return getModel().getElementAt(index).toString();
                }

                return null;
            }

            @Override
            public Point getToolTipLocation(MouseEvent evt) {
                if(getToolTipText(evt) == null) {
                    return null;
                }
                return new Point(evt.getX(), evt.getY() + 21);
            }
        };

        resultsList.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar() != KeyEvent.CHAR_UNDEFINED
                        && (e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != KeyEvent.CTRL_DOWN_MASK) {

                    if(!filterPanel.isVisible()) { // Panel is closed
                        setFilterPanelOpen(true, e);
                    }
                    else { // Panel is open, return focus to it
                        filterField.setText(filterField.getText() + e.getKeyChar());
                        filterField.requestFocus();
                    }
                }
            }
        });
        
        resultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        resultsList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent lse) {
                int index = resultsList.getSelectedIndex();
                int originalIndex = index;

                if(index < 0) {
                    return;
                }

                FilterableStringEntry entry1 = (FilterableStringEntry) conceptModel.getFilterableAtModelIndex(index);
                String filteredString = entry1.getInitialText();

                for (int i = 0; i < searchResultModel.getSize(); i++) {
                    FilterableStringEntry entry2 = (FilterableStringEntry) searchResultModel.getFilterableAtModelIndex(i);
                    String originalString = entry2.getInitialText();

                    if (filteredString.equals(originalString)) {
                        originalIndex = i;
                        break;
                    }
                }

                final SearchButtonResult result = searchResultsList.get(originalIndex);
                final SearchAction searchAction = getSelectedAction();
                
                 SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        searchAction.resultSelected(result);
                    }
                 });
            }
        });
    }
    
    /* opens (open = true) or closes the filter panel */
    public void toggleFilterPanel() {
        if(!filterPanel.isVisible()) {
            setFilterPanelOpen(true, null);
        }
        else {
            setFilterPanelOpen(false, null);
        }
    }
    
    /*opens the filter panell and uses a KeyEvent if openned by typing */
    public void setFilterPanelOpen(boolean open, KeyEvent e) {
        if(open) {
            if(!filterPanel.isVisible()) {
                filterPanel.setVisible(true);
                if(e != null) {
                    filterField.setText("" + e.getKeyChar());
                }
                else {
                    filterField.setText("");
                }
                filterField.requestFocus();
            }
        }
        else {
            conceptModel.changeFilter("");
            filterPanel.setVisible(false);
            resultsList.grabFocus();
        }
    }
}
