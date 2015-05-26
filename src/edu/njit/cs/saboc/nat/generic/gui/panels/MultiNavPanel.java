package edu.njit.cs.saboc.nat.generic.gui.panels;

import edu.njit.cs.saboc.blu.core.gui.iconmanager.IconManager;
import edu.njit.cs.saboc.nat.generic.FocusConcept;
import edu.njit.cs.saboc.nat.generic.GenericNATBrowser;
import edu.njit.cs.saboc.nat.generic.fields.NATDataField;
import edu.njit.cs.saboc.nat.generic.gui.utils.ButtonTabbedPaneUI;
import java.awt.BorderLayout;
import java.awt.Dimension;
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
public class MultiNavPanel extends JPanel {
    private HashMap<NATDataField, BaseNavPanel> navPanels = new HashMap<>();
    
    private HashMap<NATDataField, Integer> panelIndexes = new HashMap<>();
    
    private FocusConcept focusConcept;
    
    private JTabbedPane tabbedPane = new JTabbedPane();
    
    public MultiNavPanel(GenericNATBrowser mainPanel) {
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
                        for(Entry<NATDataField, Integer> indexEntry : panelIndexes.entrySet()) {
                            if(indexEntry.getValue() == tabbedPane.getSelectedIndex()) {
                                BaseNavPanel navPanel = navPanels.get(indexEntry.getKey());
                                
                                if(navPanel instanceof Toggleable) {
                                    ((Toggleable)navPanel).toggle();
                                }
                            }
                        }
                    }
                });

                return button;
            }
        };
        
        tabbedPane.setUI(tabbedUI);

        tabbedPane.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

            }
        });
        
        this.add(tabbedPane, BorderLayout.CENTER);
        
        this.focusConcept = mainPanel.getFocusConcept();
    }
    
    public void addNavPanel(NATDataField field, BaseNavPanel panel, String panelTitle) {              
        navPanels.put(field, panel);
        
        panelIndexes.put(field, tabbedPane.getTabCount());
        
        focusConcept.addDisplayPanel(field, panel);
        
        tabbedPane.add(panelTitle, panel);
    } 
    
    public void updateTabTitle(NATDataField field, String title) {
        tabbedPane.setTitleAt(panelIndexes.get(field), title);
    }
}
