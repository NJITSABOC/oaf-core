package edu.njit.cs.saboc.nat.generic.gui.panels;

import edu.njit.cs.saboc.blu.core.utils.filterable.list.DefaultListModelEx;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.GenericNATBrowser;
import edu.njit.cs.saboc.nat.generic.History;
import edu.njit.cs.saboc.nat.generic.gui.listeners.SearchResultListNavigateSelectionAction;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;


/**
 * The upper right panel of the NAT.  Contains tabs for the search bar, the
 * history list, and user-specified options.
 * 
 * NOTES: This code is more or less copied from the UMLS NAT, and is probably
 * from 2006.
 */
public class OptionsAndSearchPanel<T> extends NATLayoutPanel<T> implements ActionListener {
    
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
        
    private ConceptBrowserDataSource<T> dataSource;

    // Construtor!
    public OptionsAndSearchPanel(final GenericNATBrowser<T> mainPanel, ConceptBrowserDataSource<T> dataSource) {
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
        tpane.addTab("Options", new OptionsPanel(mainPanel));
        
        add(tpane, BorderLayout.CENTER);

        updateHistory();
    }
    
    protected void setFontSize(int fontSize) {
        tpane.setFont(tpane.getFont().deriveFont(Font.BOLD, fontSize));
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

    private class OptionsPanel extends NATLayoutPanel<T> {
        
        private final JRadioButton tinyBtn = new JRadioButton("Tiny");
        private final JRadioButton smallBtn = new JRadioButton("Small");
        private final JRadioButton normalBtn = new JRadioButton("Normal");
        private final JRadioButton largeBtn = new JRadioButton("Large");
        private final JRadioButton hugeBtn = new JRadioButton("Huge");
        
        private final JPanel fontSizePanel = new JPanel();
        
        public OptionsPanel(GenericNATBrowser<T> mainPanel) {
            super(mainPanel);
                        
            this.setLayout(new BorderLayout());
            
            fontSizePanel.setBorder(BaseNavPanel.createTitledLineBorder("Font Size", mainPanel.getOptions().getFontSize()));

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

            ButtonGroup fontSizeGroup = new ButtonGroup();
            fontSizeGroup.add(tinyBtn);
            fontSizeGroup.add(smallBtn);
            fontSizeGroup.add(normalBtn);
            fontSizeGroup.add(largeBtn);
            fontSizeGroup.add(hugeBtn);
            
            fontSizePanel.add(tinyBtn);
            fontSizePanel.add(smallBtn);
            fontSizePanel.add(normalBtn);
            fontSizePanel.add(largeBtn);
            fontSizePanel.add(hugeBtn);

            normalBtn.setSelected(true);

            this.add(fontSizePanel, BorderLayout.NORTH);
        }

        protected void setFontSize(int fontSize) {
            Font optFont = tinyBtn.getFont().deriveFont(Font.BOLD, fontSize);
            
            tinyBtn.setFont(optFont);
            smallBtn.setFont(optFont);
            normalBtn.setFont(optFont);
            largeBtn.setFont(optFont);
            hugeBtn.setFont(optFont);
            
            fontSizePanel.setBorder(BaseNavPanel.createTitledLineBorder("Font Size", mainPanel.getOptions().getFontSize()));
        }
    }


}
