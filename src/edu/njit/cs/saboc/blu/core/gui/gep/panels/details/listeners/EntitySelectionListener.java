package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners;

/**
 *
 * @author Chris O
 */
public interface EntitySelectionListener<ENTITY_T> {
    public void entitySelected(ENTITY_T entity);
    
    public void noEntitySelected();
}
