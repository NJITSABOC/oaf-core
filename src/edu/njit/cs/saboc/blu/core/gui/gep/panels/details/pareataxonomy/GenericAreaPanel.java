package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericArea;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractContainerPanel;

/**
 *
 * @author Chris O
 */
public abstract class GenericAreaPanel<AREA_T extends GenericArea, CONCEPT_T> extends AbstractContainerPanel<AREA_T, CONCEPT_T> {

    public GenericAreaPanel() {
        
    }
    
    @Override
    protected String getNodeType() {
       return "Area";
    }
}
