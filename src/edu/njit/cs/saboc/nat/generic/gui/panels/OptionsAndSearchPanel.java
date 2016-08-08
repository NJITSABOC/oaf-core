package edu.njit.cs.saboc.nat.generic.gui.panels;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.DefaultListModelEx;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.GenericNATBrowser;
import edu.njit.cs.saboc.nat.generic.History;
import edu.njit.cs.saboc.nat.generic.gui.listeners.SearchResultListNavigateSelectionAction;
import edu.njit.cs.saboc.nat.generic.gui.panels.options.OptionPanel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;


/**
 * NOTES: This code is more or less copied from the UMLS NAT, and is probably
 * from 2006.
 */
public class OptionsAndSearchPanel extends NATLayoutPanel implements ActionListener {
    
    private JTabbedPane tpane = new JTabbedPane();
       
    // History Panel
    private BaseNavPanel pnlHistory;
    private JButton btnBack;
    private JButton btnForward;
    private DefaultListModelEx mdlHistory;
    private JList lstHistory;
    
    private OptionsPanel optionsPanel;
    
    // Other stuff
    private History history;
        
    private ConceptBrowserDataSource dataSource;

    // Construtor!
    public OptionsAndSearchPanel(GenericNATBrowser mainPanel, ConceptBrowserDataSource dataSource) {
        super(mainPanel);

        this.dataSource = dataSource;

        this.history = mainPanel.getFocusConcept().getHistory();
        this.setLayout(new BorderLayout());
        this.setBackground(mainPanel.getNeighborhoodBGColor());

        // History Panel
        JPanel historyPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel();

        historyPanel.add(buttonPanel, BorderLayout.SOUTH);

        btnBack = new JButton("<< Previous");
        btnForward = new JButton("Next >>");
        btnBack.addActionListener(this);
        btnForward.addActionListener(this);
        buttonPanel.add(btnBack);
        buttonPanel.add(btnForward);

        pnlHistory = new BaseNavPanel(mainPanel, dataSource) {
            protected void setFontSize(int fontSize) {
                lstHistory.setFont(lstHistory.getFont().deriveFont(Font.BOLD, fontSize));
            }
            
            public void dataEmpty() {

            }

            public void dataReady() {

            }

            public void dataPending() {

            }

            public void focusConceptChanged() {
                updateHistory();
            }
        };      
        
        pnlHistory.setLayout(new BorderLayout());
        mainPanel.getFocusConcept().addDisplayPanel(mainPanel.getFocusConcept().COMMON_DATA_FIELDS.HISTORY, pnlHistory);
        
        mdlHistory = new DefaultListModelEx();
        lstHistory = new JList(mdlHistory);

        lstHistory.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if(evt.getClickCount() == 2 && lstHistory.getSelectedIndex() >= 0) {
                    history.setPosition(history.getHistoryList().size() - 1
                            - lstHistory.getSelectedIndex());
                    
                    mainPanel.getFocusConcept().navigate(history.getHistoryList().get(history.getPosition()));
                }
            }
        });

        lstHistory.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                if(evt.getKeyCode() == KeyEvent.VK_ENTER && lstHistory.getSelectedIndex() >= 0) {
                    lstHistory.setSelectedIndex(lstHistory.getSelectedIndex());
                    history.setPosition(history.getHistoryList().size() - 1
                            - lstHistory.getSelectedIndex());
                    
                    mainPanel.getFocusConcept().navigate(history.getHistoryList().get(history.getPosition()));
                }
            }
        });

        pnlHistory.add(historyPanel, BorderLayout.SOUTH);
        pnlHistory.add(new JScrollPane(lstHistory), BorderLayout.CENTER);

        tpane.addTab("Search", new SearchPanel(mainPanel, dataSource, new SearchResultListNavigateSelectionAction(mainPanel.getFocusConcept())));
        tpane.addTab("History", pnlHistory);
        tpane.addTab("Options", optionsPanel = new OptionsPanel(mainPanel));
        
        add(tpane, BorderLayout.CENTER);

        updateHistory();
    }
    
    protected void setFontSize(int fontSize) {
        tpane.setFont(tpane.getFont().deriveFont(Font.BOLD, fontSize));
    }

    public void updateHistory() {
        mdlHistory.clear();
        ArrayList<Concept> historyList = history.getHistoryList();

        for(int i = historyList.size() - 1; i >= 0; --i) {
            Concept c = historyList.get(i);

            String cs = c.getName().replaceAll("<", "&lt;");

            String entry = String.format("<html> <FONT color=%s><b>%s</b></FONT>",
                    i == history.getPosition() ? "black" : "gray", cs);
            
            mdlHistory.addElement(entry);
        }
        
        updateHistoryButtons();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == btnBack) {
            if(history.getPosition() > 0) {
                history.minusPosition();
                getMainPanel().getFocusConcept().navigate(history.getHistoryList().get(history.getPosition()));
            }
        }
        else if(ae.getSource() == btnForward) {
            if(history.getPosition() < (history.getHistoryList().size() - 1)) {
                history.plusPosition();
                getMainPanel().getFocusConcept().navigate(history.getHistoryList().get(history.getPosition()));
            }
        }
    }

    public void updateHistoryButtons() {
        btnBack.setEnabled(history.getPosition() > 0);
        btnForward.setEnabled(history.getPosition() < (history.getHistoryList().size() - 1));
    }
    
    public void addOptionPanel(OptionPanel panel) {
        optionsPanel.addOptionsPanel(panel);
    }
    
    public void addOptionTab(String tabName, JPanel tabContents) {
        tpane.addTab(tabName, tabContents);
    }
}
