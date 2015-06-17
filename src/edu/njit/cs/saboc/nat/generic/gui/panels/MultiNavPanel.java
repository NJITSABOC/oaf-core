package edu.njit.cs.saboc.nat.generic.gui.panels;

import edu.njit.cs.saboc.blu.core.gui.iconmanager.IconManager;
import edu.njit.cs.saboc.nat.generic.FocusConcept;
import edu.njit.cs.saboc.nat.generic.GenericNATBrowser;
import edu.njit.cs.saboc.nat.generic.Options;
import edu.njit.cs.saboc.nat.generic.fields.NATDataField;
import edu.njit.cs.saboc.nat.generic.gui.listeners.NATOptionsAdapter;
import edu.njit.cs.saboc.nat.generic.gui.utils.ButtonTabbedPaneUI;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map.Entry;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Chris O
 */
public class MultiNavPanel<T> extends JPanel {
    private HashMap<String, NATLayoutPanel> navPanels = new HashMap<>();
    
    private HashMap<String, Integer> panelIndexes = new HashMap<>();
    
    private FocusConcept<T> focusConcept;
    
    private JTabbedPane tabbedPane = new JTabbedPane();
    
    public MultiNavPanel(GenericNATBrowser<T> mainPanel) {
        super(new BorderLayout());
        
        focusConcept = mainPanel.getFocusConcept();
        
        final ButtonTabbedPaneUI tabbedUI = new ButtonTabbedPaneUI() {

            protected TabButton createFilterTabButton(int tabIndex) {
                TabButton button = new TabButton(tabIndex);
                button.setIcon(IconManager.getIconManager().getIcon("filter.png"));
                button.setPreferredSize(new Dimension(16, 16));
                button.setPadding(new Insets(0, 2, 3, 0));
                button.setBorder(null);
                button.setContentAreaFilled(false);
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        ((TabButton)e.getSource()).setContentAreaFilled(true);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        ((TabButton)e.getSource()).setContentAreaFilled(false);
                    }
                });

                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        for(Entry<String, Integer> indexEntry : panelIndexes.entrySet()) {
                            if(indexEntry.getValue() == tabbedPane.getSelectedIndex()) {
                                NATLayoutPanel layoutPanel = navPanels.get(indexEntry.getKey());
                                
                                if(layoutPanel instanceof Toggleable) {
                                    ((Toggleable)layoutPanel).toggle();
                                }
                            }
                        }
                    }
                });

                return button;
            }
        };
        
        Options options = mainPanel.getOptions();
        
        options.addOptionsListener(new NATOptionsAdapter() {
            public void fontSizeChanged(int fontSize) {
                tabbedPane.setFont(tabbedPane.getFont().deriveFont(Font.BOLD, fontSize));
            }
        });
        
        tabbedPane.setUI(tabbedUI);
        tabbedPane.setFont(tabbedPane.getFont().deriveFont(Font.BOLD, options.getFontSize()));
               
        tabbedPane.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

            }
        });
        
        this.add(tabbedPane, BorderLayout.CENTER);
        
        this.focusConcept = mainPanel.getFocusConcept();
    }
    
    public void addNavPanel(NATDataField field, BaseNavPanel<T> panel, String panelTitle) {
        focusConcept.addDisplayPanel(field, panel);
        
        addLayoutPanel(field.getFieldName(), panel);
    }
    
    public void addLayoutPanel(String panelName, NATLayoutPanel panel) {
        navPanels.put(panelName, panel);
        panelIndexes.put(panelName, tabbedPane.getTabCount());
        tabbedPane.add(panelName, panel);
    }

    public void updateTabTitle(String fieldName, String title) {
        tabbedPane.setTitleAt(panelIndexes.get(fieldName), title);
    }
}
