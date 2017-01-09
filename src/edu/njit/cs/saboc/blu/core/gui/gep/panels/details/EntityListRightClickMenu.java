package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author Kevyn
 * @param <T>
 */
public class EntityListRightClickMenu<T> {
    
    public class EntityListPopupMenuMouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getButton() == MouseEvent.BUTTON3) {
                showPopup(e);
            }
        }
   
        private void showPopup(MouseEvent e) {
            
            int row = entityList.getEntityTable().rowAtPoint(e.getPoint());

            if (row >= 0) {
                if (e.isPopupTrigger()) {
                    T selectedValue = entityList.getTableModel().getItemAtRow(row);
                    
                    rightClickedItem = Optional.of(selectedValue);
                    
                    menuItems.forEach((action, menuItem) -> {
                        menuItem.setEnabled(action.isEnabledFor(selectedValue));
                    });
                    
                    popup.show(e.getComponent(), e.getX(), e.getY());
                    
                    return;
                }
            }
            
            menuItems.values().forEach((menuItem) -> {
                menuItem.setEnabled(false);
            });
            
            rightClickedItem = Optional.empty();
        }
    }
    
    public abstract class EntityListRightClickMenuItem {
        private final String itemName;
        
        public EntityListRightClickMenuItem(String itemName) {
            this.itemName = itemName;
        }
        
        public String getItemName() {
            return itemName;
        }
        
        public abstract boolean isEnabledFor(T item);
        
        public abstract void doActionFor(T item);

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 53 * hash + Objects.hashCode(this.itemName);
            
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            
            if (getClass() != obj.getClass()) {
                return false;
            }
            
            final EntityListRightClickMenuItem other = (EntityListRightClickMenuItem) obj;
            
            if (!Objects.equals(this.itemName, other.itemName)) {
                return false;
            }
            
            return true;
        }
    }
    
    private Optional<T> rightClickedItem = Optional.empty();
    
    private final AbstractEntityList<T> entityList;
        
    private final Map<EntityListRightClickMenuItem, JMenuItem> menuItems = new HashMap<>();
    
    private final JPopupMenu popup = new JPopupMenu();
    
    private final EntityListPopupMenuMouseListener popupListener = new EntityListPopupMenuMouseListener();
    
    public EntityListRightClickMenu(AbstractEntityList<T> entityList){
        this.entityList = entityList;
    }

    public EntityListPopupMenuMouseListener getListener() {
        return popupListener;
    }

    public void addMenuItem(EntityListRightClickMenuItem item) {
                
        JMenuItem menuItem = new JMenuItem(item.getItemName());
        menuItem.addActionListener( (ae) -> {
            
            if(rightClickedItem.isPresent()) {
                item.doActionFor(rightClickedItem.get());
            }

        });

        popup.add(menuItem);
        
        menuItems.put(item, menuItem);
    }

    public void removeMenuItem(EntityListRightClickMenuItem item) {
        if(menuItems.containsKey(item)) {
            popup.remove(menuItems.get(item));
            menuItems.remove(item);
        }
    }
}
