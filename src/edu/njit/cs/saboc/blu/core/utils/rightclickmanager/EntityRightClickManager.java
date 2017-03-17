package edu.njit.cs.saboc.blu.core.utils.rightclickmanager;

import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author Kevyn
 * @param <T>
 */
public class EntityRightClickManager<T> {
            
    private final JLabel nameLabel;
    
    private final JPopupMenu popup = new JPopupMenu();
    
    private final Map<EntityRightClickMenuItem, JMenuItem> menuItems = new HashMap<>();
        
    private Optional<T> rightClickedItem;
    
    public EntityRightClickManager(){
        this.rightClickedItem = Optional.empty();
        
        this.nameLabel = new JLabel("(no selection)");
        
        this.popup.add(nameLabel);
        this.popup.addSeparator();
    }

    public void setRightClickedItem(T item) {
        rightClickedItem = Optional.of(item);

        menuItems.forEach((action, menuItem) -> {
            menuItem.setEnabled(action.isEnabledFor(item));
        });
        
        nameLabel.setText(item.toString());
    }
    
    public void clearRightClickedItem() {
        this.rightClickedItem = Optional.empty();
        
        menuItems.forEach((action, menuItem) -> {
            menuItem.setEnabled(action.enabledWhenNoSelection());
        });
        
        this.nameLabel.setText("(no selection)");
    }
    
    public void addMenuItem(EntityRightClickMenuItem item) {
        
        JMenuItem menuItem = new JMenuItem(item.getItemName());
        menuItem.setFont(menuItem.getFont().deriveFont(14.0f));
        
        menuItem.addActionListener((ae) -> {
            if(rightClickedItem.isPresent()) {
                item.doActionFor(rightClickedItem.get());
            } else {
                if(item.enabledWhenNoSelection()) {
                    item.doEmptyAction();
                }
            }
        });

        popup.add(menuItem);
        
        menuItems.put(item, menuItem);
    }

    public void removeMenuItem(EntityRightClickMenuItem item) {
        
        if(menuItems.containsKey(item)) {
            popup.remove(menuItems.get(item));
            menuItems.remove(item);
        }
        
    }
    
    public void showPopup(MouseEvent e) {
        if(menuItems.isEmpty()) {
            return;
        }
        
        popup.show(e.getComponent(), e.getX(), e.getY());
    }
}
