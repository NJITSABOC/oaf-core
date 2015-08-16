package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericPArea;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractContainerGroupListPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractContainerPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractNodeDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.ContainerConceptEntry;

/**
 *
 * @author Chris O
 */
public class GenericAreaPanel<AREA_T extends GenericArea, 
        PAREA_T extends GenericPArea, CONCEPT_T> extends AbstractContainerPanel<AREA_T, PAREA_T, CONCEPT_T, ContainerConceptEntry<CONCEPT_T, PAREA_T>> {
    
    public GenericAreaPanel(
           AbstractNodeDetailsPanel<AREA_T, ContainerConceptEntry<CONCEPT_T, PAREA_T>> containerDetailsPanel, 
           AbstractContainerGroupListPanel<AREA_T, PAREA_T, CONCEPT_T> groupListPanel, 
           PAreaTaxonomyConfiguration configuration) {
        
        super(containerDetailsPanel, groupListPanel, configuration);
    }
}
