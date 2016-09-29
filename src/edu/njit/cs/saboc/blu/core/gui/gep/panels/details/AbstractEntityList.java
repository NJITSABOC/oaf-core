package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionListener;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.iconmanager.IconManager;
import edu.njit.cs.saboc.blu.core.gui.utils.renderers.MultiLineTextRenderer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.PatternSyntaxException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Chris O
 */
public abstract class AbstractEntityList<T> extends JPanel {

    private final JTable entityTable;
    private final OAFAbstractTableModel<T> tableModel;

    private final ArrayList<EntitySelectionListener<T>> selectionListeners = new ArrayList<>();

    private final JTextField filterField = new JTextField();
    private final JButton closeButton = new JButton();

    private final TableRowSorter<TableModel> sorter;

    private final JPanel filterPanel;

    protected AbstractEntityList(OAFAbstractTableModel<T> tableModel) {

        super(new BorderLayout());

        this.tableModel = tableModel;
        this.entityTable = new JTable(tableModel);
        this.entityTable.setFont(entityTable.getFont().deriveFont(Font.PLAIN, 14));
        setDefaultTableStringRenderer(new MultiLineTextRenderer());
        
        this.entityTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (entityTable.getSelectedRow() >= 0) {

                    T entity = tableModel.getItemAtRow(sorter.convertRowIndexToModel(entityTable.getSelectedRow()));

                    
                    if (e.getClickCount() == 1) {
                        selectionListeners.forEach((listener) -> {
                            listener.entityClicked(entity);
                        });
                    } else if (e.getClickCount() == 2) {
                        selectionListeners.forEach((listener) -> {
                            listener.entityDoubleClicked(entity);
                        });
                    }

                } else {
                    selectionListeners.forEach((listener) -> {
                        listener.noEntitySelected();
                    });
                }
            }
        });

        this.add(new JScrollPane(entityTable), BorderLayout.CENTER);

        ///////////////////////////
        //Container for filtering//
        ///////////////////////////
        closeButton.setIcon(IconManager.getIconManager().getIcon("cross.png"));
        closeButton.setToolTipText("Close");

        filterPanel = new JPanel();
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
        sorter = new TableRowSorter<>(tableModel);

        entityTable.setRowSorter(sorter);
        this.entityTable.getRowSorter().toggleSortOrder(0);

        /////////////////////////
        //Filters for Listening//
        /////////////////////////
        filterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    filterField.setText("");

                    setFilterPanelOpen(false, null);

                    entityTable.requestFocus();
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

        entityTable.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {

                if (!e.isControlDown() && !e.isAltDown() && isPrintable(e.getKeyChar())) {
                    if (!filterPanel.isVisible()) {
                        setFilterPanelOpen(true, e);
                    } else {
                        filterField.requestFocus();
                        filterField.setText(filterField.getText() + e.getKeyChar());
                    }
                }
            }
        });

        RightClickMenu rMenu = new RightClickMenu();

        rMenu.addMenuItem("Print Name", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("hi");
            }
        });

        entityTable.addMouseListener(rMenu.getListener());

        /*
        optionsPanel = new JPanel();
        this.add(optionsPanel, BorderLayout.SOUTH);
         */
        //setBorderText(getBorderText(Optional.empty()));
    }

    public OAFAbstractTableModel<T> getTableModel() {
        return tableModel;
    }

    public void setSelectionMode(int selectionMode) {
        entityTable.setSelectionMode(selectionMode);
    }

    private void newFilter() {

        RowFilter<TableModel, Object> rf;

        try {
            rf = RowFilter.regexFilter("(?i)" + filterField.getText());
        } catch (PatternSyntaxException e) {
            return;
        }

        sorter.setRowFilter(rf);
    }

    /* opens (open = true) or closes the filter panel */
    public void toggleFilterPanel() {
        if (!filterPanel.isVisible()) {
            setFilterPanelOpen(true, null);
        } else {
            filterField.setText("");
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
            filterField.setText("");
            filterPanel.setVisible(false);
        }
    }

    public void addEntitySelectionListener(EntitySelectionListener<T> listener) {
        selectionListeners.add(listener);
    }

    public void removeEntitySelectionListener(EntitySelectionListener<T> listener) {
        selectionListeners.remove(listener);
    }

    public void setContents(ArrayList<T> entities) {
        tableModel.setContents(entities);

        setBorderText(getBorderText(Optional.of(entities)));
    }

    public void clearContents() {
        tableModel.setContents(new ArrayList<>());
    }
    
    public final void setDefaultTableRenderer(Class clazz, TableCellRenderer renderer) {
        this.entityTable.setDefaultRenderer(clazz, renderer);
    }
    
    public final void setDefaultTableStringRenderer(MultiLineTextRenderer renderer) {
        setDefaultTableRenderer(String.class, renderer);
    }

    protected void addOptionButton(JButton btn) {
        //optionsPanel.add(btn);
    }

    protected abstract String getBorderText(Optional<ArrayList<T>> entities);

    private final void setBorderText(String text) {
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), text));
    }

    private boolean isPrintable(char c) {
        return (c >= 32 && c < 127);
    }
}
