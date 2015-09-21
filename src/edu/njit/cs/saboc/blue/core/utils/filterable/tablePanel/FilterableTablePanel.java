/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.njit.cs.saboc.blue.core.utils.filterable.tablePanel;

import edu.njit.cs.saboc.blu.core.gui.iconmanager.IconManager;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Vladimir V
 */
public class FilterableTablePanel extends JPanel {

    private final JTextField filterField = new JTextField();
    private final JButton closeButton = new JButton();

    private TableRowSorter<TableModel> sorter;
    
    private JPanel filterPanel = new JPanel();

    public FilterableTablePanel() {
        this.setLayout(new BorderLayout());
    }

    public void add(JScrollPane comp) {
        add(comp, null);
    }

    public void add(JScrollPane comp, Object constraints) {
        super.add(comp, constraints);

        JViewport viewport = comp.getViewport();

        JTable table = (JTable) viewport.getView();

        ///////////////////////////
        //Container for filtering//
        ///////////////////////////
        closeButton.setIcon(IconManager.getIconManager().getIcon("cross.png"));
        closeButton.setToolTipText("Close");

        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.X_AXIS));
        filterPanel.add(closeButton);
        filterPanel.add(Box.createHorizontalStrut(10));
        filterPanel.add(new JLabel("Filter:  "));
        filterPanel.add(filterField);
        filterPanel.setVisible(false);

        this.add(filterPanel, BorderLayout.SOUTH);

        ///////////////////////////
        //End Filtering Container//
        //  Start Sorter Model   //
        ///////////////////////////
        sorter = new TableRowSorter<TableModel>(table.getModel());
        
        table.setRowSorter(sorter);
        table.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent e) {
                        int viewRow = table.getSelectedRow();
                        
                        if (viewRow >= 0) {
                            
                            //Selection did not got filtered away.
                            int modelRow = table.convertRowIndexToModel(viewRow);
                            
                            
                        }
                    }
                });

        /////////////////////////
        //Filters for Listening//
        /////////////////////////
        filterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    filterField.setText("");
                    setFilterPanelOpen(false, null);
                    table.requestFocus();

                }
            }
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setFilterPanelOpen(false, null);
            }
        });

        filterField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                newFilter();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                newFilter();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                newFilter();
            }

        });

        table.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {

                if (!e.isControlDown() && !e.isAltDown()) {
                    if (!filterPanel.isVisible()) {
                        setFilterPanelOpen(true, e);
                    } else {
                        filterField.requestFocus();
                        filterField.setText(filterField.getText() + e.getKeyChar());
                    }
                }
            }
        });
    }

    private void newFilter() {

        RowFilter<TableModel, Object> rf = null;
        
        try {
            rf = RowFilter.regexFilter("(?i)" + filterField.getText());
        } catch (java.util.regex.PatternSyntaxException e) {
            
            System.out.println(e.toString());
            return;
        }
        
        sorter.setRowFilter(rf);
    }

    /* opens (open = true) or closes the filter panel */
    public void toggleFilterPanel() {
        if (!filterPanel.isVisible()) {
            setFilterPanelOpen(true, null);
        } else {
            setFilterPanelOpen(false, null);
        }
    }

    /*opens the filter panell and uses a KeyEvent if openned by typing */
    public void setFilterPanelOpen(boolean open, KeyEvent e) {
        if (open) {
            if (!filterPanel.isVisible()) {
                filterPanel.setVisible(true);

                if (e != null) {
                    filterField.setText(Character.toString(e.getKeyChar()));
                } else {
                    filterField.setText("");
                }

                filterField.requestFocus();
            }
        } else {
            filterPanel.setVisible(false);
        }
    }
}
