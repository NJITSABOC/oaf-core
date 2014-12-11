package edu.njit.cs.saboc.blu.core.gui.utils.renderers;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

public class MultiLineTextRenderer extends JTextArea implements TableCellRenderer {

    public MultiLineTextRenderer() {
        super();
    }

    public Component getTableCellRendererComponent(
            JTable table, Object value,
            boolean isSelected, boolean hasFocus,
            int row, int column) {

        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(table.getBackground());
        }
        
        setFont(table.getFont());
        
        if (hasFocus) {
            setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
            
            if (table.isCellEditable(row, column)) {
                setForeground(UIManager.getColor("Table.focusCellForeground"));
                setBackground(UIManager.getColor("Table.focusCellBackground"));
            }
        } else {
            setBorder(new EmptyBorder(1, 2, 1, 2));
        }
        
        if (value != null) {
            setText(value.toString());
            
            String [] lines = value.toString().split("\n");
            
            int height = 0 ;
            
            for(String line : lines) {
                height += this.getFontMetrics(this.getFont()).getLineMetrics(line, null).getHeight();
            }
           
            table.setRowHeight(row, height + 10);

        } else {
            setText("");
        }

        return this;
    }
}
