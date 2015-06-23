package edu.njit.cs.saboc.nat.generic.gui.panels;

import edu.njit.cs.saboc.blu.core.gui.iconmanager.IconManager;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.DefaultListModelEx;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.FilterableList;
import edu.njit.cs.saboc.nat.generic.data.BrowserSearchResult;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.GenericNATBrowser;
import edu.njit.cs.saboc.nat.generic.History;
import edu.njit.cs.saboc.nat.generic.Options;
import edu.njit.cs.saboc.nat.generic.gui.filterablelist.BrowserNavigableFilterableList;
import edu.njit.cs.saboc.nat.generic.gui.filterablelist.FilterableSearchEntry;
import edu.njit.cs.saboc.nat.generic.gui.listeners.NATOptionsAdapter;
import edu.njit.cs.saboc.nat.generic.gui.listeners.SearchResultListNavigateSelectionAction;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Optional;


import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


/**
 * The upper right panel of the NAT.  Contains tabs for the search bar, the
 * history list, and user-specified options.
 * 
 * NOTES: This code is more or less copied from the UMLS NAT, and is probably
 * from 2006.
 */
public class SearchPanel<T> extends NATLayoutPanel implements ActionListener {
    
    private JTabbedPane tpane = new JTabbedPane();
       
    
    // History Panel
    private BaseNavPanel<T> pnlHistory;
    private JButton btnBack;
    private JButton btnForward;
    private DefaultListModelEx mdlHistory;
    private JList lstHistory;
    
    // Options Panel
    private JPanel pnlOptions;
        
    private JCheckBox chkID;
    
    // Other stuff
    private History<T> history;

    // Convenience methods for control creation
    private JRadioButton makeRadioButton(String text, ButtonGroup group, Container panel) {
        return makeRadioButton(text, group, panel, null);
    }

    private JRadioButton makeRadioButton(String text, ButtonGroup group, Container panel, GridBagConstraints c) {
        JRadioButton rb = new JRadioButton(text);
        group.add(rb);
        
        if(c == null) {
            panel.add(rb);
        }
        else {
            panel.add(rb, c);
        }
        
        rb.addActionListener(this);
        return rb;
    }

    private JCheckBox makeCheckBox(String text, Container panel, boolean sel, GridBagConstraints c) {
        JCheckBox cb = new JCheckBox(text);
        
        panel.add(cb, c);
        cb.setSelected(sel);
        cb.addActionListener(this);
        
        return cb;
    }
    
    private GenericNATBrowser<T> mainPanel;
    
    private ConceptBrowserDataSource<T> dataSource;

    // Construtor!
    public SearchPanel(final GenericNATBrowser<T> mainPanel, ConceptBrowserDataSource<T> dataSource) {       
        this.mainPanel = mainPanel;
        this.dataSource = dataSource;

        this.history = mainPanel.getFocusConcept().getHistory();
        
        Options options = mainPanel.getOptions();
        
        this.tpane.setFont(tpane.getFont().deriveFont(Font.BOLD, options.getFontSize()));
        
        options.addOptionsListener(new NATOptionsAdapter() {
            public void fontSizeChanged(int fontSize) {
                lstHistory.setFont(lstHistory.getFont().deriveFont(Font.BOLD, fontSize));
                
                tpane.setFont(tpane.getFont().deriveFont(Font.BOLD, options.getFontSize()));
            }
        });
        
        setLayout(new BorderLayout());

        // Search Panel
       

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
        lstHistory.setFont(lstHistory.getFont().deriveFont(Font.BOLD, options.getFontSize()));

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


        tpane.addTab("Search", new OntologySearchPanel(mainPanel, dataSource, new SearchResultListNavigateSelectionAction(mainPanel.getFocusConcept())));
        tpane.addTab("History", pnlHistory);
        tpane.addTab("Options", new OptionsPanel());
        add(tpane, BorderLayout.CENTER);

        updateHistory();
    }

    public void updateHistory() {
        mdlHistory.clear();
        ArrayList<T> historyVector = history.getHistoryList();

        for(int i = historyVector.size() - 1; i >= 0; --i) {
            T c = historyVector.get(i);

            String cs = dataSource.getConceptName(c).replaceAll("<", "&lt;");

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
                mainPanel.getFocusConcept().navigate(history.getHistoryList().get(history.getPosition()));
            }
        }
        else if(ae.getSource() == btnForward) {
            if(history.getPosition() < (history.getHistoryList().size() - 1)) {
                history.plusPosition();
                mainPanel.getFocusConcept().navigate(history.getHistoryList().get(history.getPosition()));
            }
        }
    }

    public void updateHistoryButtons() {
        btnBack.setEnabled(history.getPosition() > 0);
        btnForward.setEnabled(history.getPosition() < (history.getHistoryList().size() - 1));
    }

    private class OptionsPanel extends JPanel {
        public OptionsPanel() {
            JPanel fontSizePanel = new JPanel(new GridLayout(1, 2));
            
            fontSizePanel.add(new JLabel("Font Size: "));
            
            ButtonGroup fontSizeGroup = new ButtonGroup();
            JPanel fontSizeOptionsPanel = new JPanel();
            
            JRadioButton tinyBtn = makeRadioButton("Tiny", fontSizeGroup, fontSizeOptionsPanel);
            JRadioButton smallBtn = makeRadioButton("Small", fontSizeGroup, fontSizeOptionsPanel);
            JRadioButton normalBtn = makeRadioButton("Normal", fontSizeGroup, fontSizeOptionsPanel);
            JRadioButton largeBtn = makeRadioButton("Large", fontSizeGroup, fontSizeOptionsPanel);
            JRadioButton hugeBtn = makeRadioButton("Huge", fontSizeGroup, fontSizeOptionsPanel);
            
            tinyBtn.addActionListener((ActionEvent ae) -> {
                mainPanel.getOptions().setFontSize(10);
            });
            
            smallBtn.addActionListener((ActionEvent ae) -> {
                mainPanel.getOptions().setFontSize(12);
            });
            
            normalBtn.addActionListener((ActionEvent ae) -> {
                mainPanel.getOptions().setFontSize(14);
            });
            
            largeBtn.addActionListener((ActionEvent ae) -> {
                mainPanel.getOptions().setFontSize(16);
            });

            hugeBtn.addActionListener((ActionEvent ae) -> {
                mainPanel.getOptions().setFontSize(22);
            });

            normalBtn.setSelected(true);
            
            fontSizePanel.add(fontSizeOptionsPanel);
            
            this.setLayout(new BorderLayout());
            
            this.add(fontSizePanel, BorderLayout.NORTH);
        }
    }

}
