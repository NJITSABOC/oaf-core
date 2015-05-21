package edu.njit.cs.saboc.blu.core.utils.filterable.list;

import edu.njit.cs.saboc.blu.core.gui.iconmanager.IconManager;
import java.awt.BorderLayout;
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
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Chris O
 */
public class FilterableList extends JPanel {
    
    private JTextField filterField = new JTextField();
    private JButton closeButton = new JButton();
    
    private DefaultListModel pleaseWaitModel = new DefaultListModel();
    private DefaultListModel dataEmptyModel = new DefaultListModel();
    
    protected FilterableListModel conceptModel;
    
    protected JList list;
    private JPanel filterPanel = new JPanel();

    public FilterableList() {

        setLayout(new BorderLayout());

        conceptModel = new FilterableListModel();

        list = new JList() {
            // This method is called as the cursor moves within the list.
            @Override
            public String getToolTipText(MouseEvent evt) {
                if(getModel() != conceptModel) {
                    return null;
                }

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
        
        list.setModel(conceptModel);

        pleaseWaitModel.addElement("Please wait...");
        dataEmptyModel.addElement(" ");

        // Sets list members to fixed size for faster refresh.
        // For more info read Yakup's journal on 08 august 07.
        list.setFixedCellHeight(17);
        list.setFixedCellWidth(1536); // Larger arbitary number to fix wrapping issue - Chris 8/18/09
        
        
        JScrollPane scrollpane = new JScrollPane(list);
        add(scrollpane, BorderLayout.CENTER);

        closeButton.setIcon(IconManager.getIconManager().getIcon("cross.png"));
        closeButton.setToolTipText("Close");

        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.X_AXIS));
        filterPanel.add(closeButton);
        filterPanel.add(Box.createHorizontalStrut(10));
        filterPanel.add(new JLabel("Filter:  "));
        filterPanel.add(filterField);

        add(filterPanel, BorderLayout.SOUTH);

        filterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                if(ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    setFilterPanelOpen(false, null);
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
    }

    public void showPleaseWait() {
        list.setModel(pleaseWaitModel);
        conceptModel.changeFilter("");
        filterPanel.setVisible(false);
    }

    public void showDataEmpty() {
        list.setModel(dataEmptyModel);
        conceptModel.changeFilter("");
        filterPanel.setVisible(false);
    }

    public void setContents(Collection<? extends Filterable> content) {
        conceptModel.changeFilter("");
        filterPanel.setVisible(false);
        conceptModel.clear();
        conceptModel.addAll(content);
        list.setModel(conceptModel);
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
            list.grabFocus();
        }
    }
    
    public void addListMouseListener(MouseListener listener) {
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
            selectedItems.add(conceptModel.getFilterableAtModelIndex(index));
        }

        return selectedItems;
    }
    
    public int getSelectedIndex() {
        return list.getSelectedIndex();
    }
}
