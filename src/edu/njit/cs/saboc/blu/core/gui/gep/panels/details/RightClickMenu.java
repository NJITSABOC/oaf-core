/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author Kevyn
 */
public class RightClickMenu {

    private JPopupMenu popup = new JPopupMenu();
    private PopupListener popupListener = new PopupListener();

    private class PopupListener extends MouseAdapter {

        public void mousePressed(MouseEvent e) {
            showPopup(e);
        }

        public void mouseReleased(MouseEvent e) {
            showPopup(e);
        }

        private void showPopup(MouseEvent e) {
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
        menuItem.addActionListener(action);

        popup.add(menuItem);
    }

    public void removeMenuItem(int index) {
        popup.remove(index);
    }

}
