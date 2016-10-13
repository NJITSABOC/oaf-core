/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

/**
 *
 * @author Kevyn
 */
public class RightClickMenu {

    private JTable table;
    private HashMap<String, JMenuItem> menuItems = new HashMap<>();
    private JPopupMenu popup = new JPopupMenu();
    private PopupListener popupListener = new PopupListener();
    
    public RightClickMenu(JTable table){
        this.table = table;
    }

    private class PopupListener extends MouseAdapter {
             
        public void mousePressed(MouseEvent e) {
            showPopup(e);
        }

        public void mouseReleased(MouseEvent e) {
            showPopup(e);
        }

        private void showPopup(MouseEvent e) {
            //disable options
            int row = table.getSelectedRow();
            String data = table.getModel().getValueAt(row, 0).toString();           
            if (data.length() > 10){
                toggleMenuItem("Print Name", false);
            }
            else{
                toggleMenuItem("Print Name", true);
            }
            
            if (e.isPopupTrigger()) {
                popup.show(e.getComponent(),
                        e.getX(), e.getY());
            }
        }
    }

    public PopupListener getListener() {
        return popupListener;
    }

    public void addMenuItem(String name, ActionListener action) {
        JMenuItem menuItem = new JMenuItem(name);
        menuItems.put(name, menuItem);
        menuItem.addActionListener(action);

        popup.add(menuItem);
    }

    public void removeMenuItem(int index) {
        popup.remove(index);
    }
    
    public void toggleMenuItem(String name, boolean enabled){
        JMenuItem menuItem = menuItems.get(name);
        menuItem.setEnabled(enabled);
    }

}
