package edu.njit.cs.saboc.blu.core.utils.rightclickmanager;

import java.util.Objects;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 */
public abstract class EntityRightClickMenuItem<T> {

    private final String itemName;

    public EntityRightClickMenuItem(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

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

        final EntityRightClickMenuItem other = (EntityRightClickMenuItem) obj;

        if (!Objects.equals(this.itemName, other.itemName)) {
            return false;
        }

        return true;
    }
    
    public abstract boolean isEnabledFor(T item);
    public abstract void doActionFor(T item);
    
    public abstract boolean enabledWhenNoSelection();
    public abstract void doEmptyAction();
}
