package edu.njit.cs.saboc.blu.core.utils.filterable.list;

import edu.njit.cs.saboc.blu.core.gui.iconmanager.ImageManager;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListCellRenderer;
import javax.swing.ToolTipManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class FilterableList<T> extends JPanel {
    
    private final JTextField filterField = new JTextField();
    private final JButton closeButton = new JButton();
    
    private final FilterableListModel entryModel = new FilterableListModel();
    
    private final JList<Filterable<T>> list;
    
    private final JPanel filterPanel = new JPanel();

    public FilterableList() {

        setLayout(new BorderLayout());

        this.list = new JList<Filterable<T>>() {
            
            @Override
            public String getToolTipText(MouseEvent evt) {
                if(getModel() != entryModel) {
                    return null;
                }

                int index = locationToIndex(evt.getPoint());

                if(getCellBounds(index, index) == null
                        || !getCellBounds(index, index).contains(evt.getPoint())) {
                    
                    return null;
                }

                if(index > -1) {
                    Filterable obj = entryModel.get(index);
                    
                    return obj.getToolTipText(); 
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
        
        this.list.setModel(entryModel);
        
        Action copyAction = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (list.getModel() == entryModel) {
                    StringBuilder selectionBuilder = new StringBuilder();
                    
                    entryModel.modelledVector.forEach( (filterable) -> {
                        selectionBuilder.append( String.format("%s\n", filterable.getClipboardText()));
                    });

                    StringSelection selection = new StringSelection(selectionBuilder.toString());
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(selection, selection);
                }

            }
        };

        ActionMap listMap = list.getActionMap();
        listMap.put("Copy", copyAction);
        
        InputMap inputMap = list.getInputMap();
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK), "Copy");

        JScrollPane scrollpane = new JScrollPane(list);
        add(scrollpane, BorderLayout.CENTER);

        closeButton.setIcon(ImageManager.getImageManager().getIcon("cross.png"));
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

        closeButton.addActionListener((e) -> {
            setFilterPanelOpen(false, null);
        });

        filterField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                entryModel.changeFilter(filterField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                entryModel.changeFilter(filterField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                entryModel.changeFilter(filterField.getText());
            }
        });
        
        this.list.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                if (!e.isControlDown() && !e.isAltDown()) {
                    setFilterPanelOpen(true, e);
                }
            }
        });

        this.list.addMouseListener(new MouseAdapter() {
            private final int defaultDismissTimeout = ToolTipManager.sharedInstance().getDismissDelay();

            @Override
            public void mouseEntered(MouseEvent me) {
                
                ToolTipManager.sharedInstance().setDismissDelay(60000);
            }

            @Override
            public void mouseExited(MouseEvent me) {
                ToolTipManager.sharedInstance().setDismissDelay(defaultDismissTimeout);
            }
        });

        this.toggleFilterPanel();
    }
    
    public void setListCellRenderer(ListCellRenderer<Filterable<T>> renderer) {
        this.list.setCellRenderer(renderer);
    }
    
    public void showPleaseWait() {
        entryModel.changeFilter("");
        filterPanel.setVisible(false);
    }

    public void showDataEmpty() {
        entryModel.changeFilter("");
        filterPanel.setVisible(false);
    }
    
    public void showNoResults() {
        entryModel.changeFilter("");
        filterPanel.setVisible(false);
    }

    public void setContents(ArrayList<? extends Filterable> content) {
        entryModel.changeFilter("");
        
        filterPanel.setVisible(false);
        
        entryModel.clear();
        entryModel.addAll(content);
        
        list.setModel(entryModel);
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
    public final void setFilterPanelOpen(boolean open, KeyEvent e) {
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
            entryModel.changeFilter("");
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
    
    public List<Filterable<T>> getSelectedValues() {
        
        if(list.getSelectedIndices().length == 0) {
            return Collections.EMPTY_LIST;
        }
        
        ArrayList<Filterable<T>> selectedItems = new ArrayList<>();
        
        int [] selectedIndices = list.getSelectedIndices();
        
        for(int index : selectedIndices) {
            selectedItems.add(entryModel.getFilterableAtModelIndex(index));
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
    }
}
