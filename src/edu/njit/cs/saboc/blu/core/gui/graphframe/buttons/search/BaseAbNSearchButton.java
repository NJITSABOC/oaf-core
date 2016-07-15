package edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.search;

import edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.PopupToggleButton;
import edu.njit.cs.saboc.blu.core.gui.iconmanager.IconManager;
import edu.njit.cs.saboc.blu.core.utils.filterable.entry.FilterableStringEntry;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.FilterableList;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Chris
 */
public abstract class BaseAbNSearchButton extends PopupToggleButton {

    private List<SearchButtonResult> searchResultsList;
    
    private FilterableList resultList;
    
    private final JTextField searchText = new JTextField();
    
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
    
    private ArrayList<SearchActionEntry> searchActionList = new ArrayList<>();

    protected BaseAbNSearchButton(JFrame parent) {
        super(parent, "Search");
        
        resultList = new FilterableList();
        resultList.showDataEmpty();
        
        resultList.addListMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        SearchAction searchAction = getSelectedAction();
                        String selectedResultStr = (String)resultList.getSelectedValues().get(0).getObject();

                        for (SearchButtonResult result : searchResultsList) {
                            if (result.toString().equals(selectedResultStr)) {

                                searchAction.resultSelected(result);
                                return;
                            }
                        }
                    }
                });
            }
        });

        JPanel popupPanel  = new JPanel(new BorderLayout());

        popupPanel.setBorder(BorderFactory.createEtchedBorder());
        
        final JButton searchButton = new JButton(IconManager.getIconManager().getIcon("search.png"));
        
        final JButton filterButton = new JButton(IconManager.getIconManager().getIcon("filter.png"));

        final JPanel resultsPanel = new JPanel(new BorderLayout());
        resultsPanel.setBorder(new TitledBorder("Search Results"));
        
        resultsPanel.setPreferredSize(new Dimension(500, 300));

        searchText.addKeyListener(new KeyAdapter() {
            
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchButton.doClick();
                }
            }
        });
        
        resultsPanel.add(resultList, BorderLayout.CENTER);
       
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (searchText.getText().trim().isEmpty()) {
                    return;
                }

                final SearchAction searchAction = getSelectedAction();

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        ArrayList<Filterable> filterableResults = new ArrayList<>();

                        ArrayList<SearchButtonResult> results = searchAction.doSearch(searchText.getText().trim());

                        searchResultsList = results;
                        
                        if(results.isEmpty()) {
                            resultList.showNoResults();
                        } else {
                            results.forEach((SearchButtonResult result) -> {
                                filterableResults.add(new FilterableStringEntry(result.toString()));
                            });

                            resultList.setContents(filterableResults);
                        }
                    }
                });
            }
     
        });
        
        filterButton.addActionListener( (ActionEvent ae) -> {
            resultList.toggleFilterPanel();
        });
        
        JPanel searchEntryPanel = new JPanel(new BorderLayout());

        searchEntryPanel.setBorder(BorderFactory.createTitledBorder("Search Anywhere"));
        searchEntryPanel.add(searchText, BorderLayout.CENTER);
        
        JPanel btnPanel = new JPanel();
        
        btnPanel.add(searchButton);
        btnPanel.add(filterButton);
        
        searchEntryPanel.add(btnPanel, BorderLayout.EAST);
        
        searchText.setPreferredSize(new Dimension(450, 55));

        popupPanel.add(searchTypePanel, BorderLayout.NORTH);
        popupPanel.add(searchEntryPanel, BorderLayout.CENTER);
        popupPanel.add(resultsPanel, BorderLayout.SOUTH);

        this.setPopupContent(popupPanel);
    }
    
    protected void addSearchAction(SearchAction searchAction) {
        JRadioButton actionButton = new JRadioButton(searchAction.getSearchActionName());
        
        actionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                resultList.showDataEmpty();
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
}
