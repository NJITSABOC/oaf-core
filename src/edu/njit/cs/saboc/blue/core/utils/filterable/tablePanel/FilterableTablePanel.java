/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.njit.cs.saboc.blue.core.utils.filterable.tablePanel;

import edu.njit.cs.saboc.blu.core.gui.iconmanager.IconManager;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.FilterableListModel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
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
    
    private JTextField filterField = new JTextField();
    private JButton closeButton = new JButton();
    TableRowSorter<TableModel> sorter;
//    private DefaultListModel pleaseWaitModel = new DefaultListModel();
//    private DefaultListModel dataEmptyModel = new DefaultListModel();
//    
    //protected FilterableListModel entryModel;
    
    protected JList list;
    private JPanel filterPanel = new JPanel();

    public FilterableTablePanel() {
        this.setLayout(new BorderLayout());        
    }

    public void add(JScrollPane comp)
    {
        add(comp, null);
    }
    public void add(JScrollPane comp, Object constraints) {
        super.add(comp, constraints);
        
//
//        pleaseWaitModel.addElement("Please wait...");
//        dataEmptyModel.addElement(" ");

        
        JViewport viewport = comp.getViewport();
       
        JTable table = (JTable)viewport.getView();
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
                if(ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
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

                if (!e.isControlDown() && !e.isAltDown() && !e.isShiftDown() && !e.isActionKey()) {
                    if(!filterPanel.isVisible())
                        setFilterPanelOpen(true, e);
                    else {
                        filterField.requestFocus();
                        filterField.setText(filterField.getText() + e.getKeyChar());
                        
                    }
                }
            }
        });
        /////////////////
        //End Listeners//
        /////////////////
            
        
        
    
    }
    
    private void newFilter() {
        
        RowFilter<TableModel, Object> rf = null;
        try {
            String s = filterField.getText();
            s = Pattern.quote(s);
            rf = RowFilter.regexFilter("(?i)" + s);
        }   catch (java.util.regex.PatternSyntaxException e) {
            System.out.println(e.toString());
            return;
        }
        sorter.setRowFilter(rf);
    }
    /*
    public void updateTable(String text, JTable table) {
        text = text.toLowerCase();
        if(text.isEmpty()) {
//            (table.getModel())
        }
    }
    public void showPleaseWait() {
        list.setModel(pleaseWaitModel);
//        entryModel.changeFilter("");
        filterPanel.setVisible(false);
    }

    public void showDataEmpty() {
        list.setModel(dataEmptyModel);
//        entryModel.changeFilter("");
        filterPanel.setVisible(false);
    }

    public void setContents(Collection<? extends Filterable> content) {
//        entryModel.changeFilter("");
//        filterPanel.setVisible(false);
//        entryModel.clear();
//        entryModel.addAll(content);
//        list.setModel(entryModel);
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
                    filterField.setText(Character.toString(e.getKeyChar()));
                } else {
                    filterField.setText("");
                }
                
                filterField.requestFocus();
            }
        } else {
            //entryModel.changeFilter("");
            filterPanel.setVisible(false);
            //list.grabFocus();
        }
    }
    
    /*public void addListMouseListener(MouseListener listener) {
        list.addMouseListener(listener);
    }
    
    public void removeListMouseListener(MouseListener listener) {
        list.removeMouseListener(listener);
    }
    
    public List<Filterable> getSelectedValues() {
        
        if(list.getSelectedIndices().length == 0) {
            return (List<Filterable>)Collections.EMPTY_LIST;
        }
        
        ArrayList<Filterable> selectedItems = new ArrayList<Filterable>();
        
        int [] selectedIndices = list.getSelectedIndices();
        
        for(int index : selectedIndices) {
//            selectedItems.add(entryModel.getFilterableAtModelIndex(index));
        }

        return selectedItems;
    }
    
    public int getSelectedIndex() {
        return list.getSelectedIndex();
    }
    
    public void setListFontSize(int size) {
        if(size > 0) {
            list.setFont(list.getFont().deriveFont(Font.PLAIN, (float)size));
        }
    }*/
}
