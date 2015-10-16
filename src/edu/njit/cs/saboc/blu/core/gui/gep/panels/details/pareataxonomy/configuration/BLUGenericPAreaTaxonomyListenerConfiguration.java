package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericPArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.ui.listener.BLUPartitionedAbNListenerConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionListener;

/**
 *
 * @author Chris O
 */
public interface BLUGenericPAreaTaxonomyListenerConfiguration<
        TAXONOMY_T extends GenericPAreaTaxonomy, 
        AREA_T extends GenericArea,
        PAREA_T extends GenericPArea,
        CONCEPT_T,
        REL_T> extends BLUPartitionedAbNListenerConfiguration<TAXONOMY_T, AREA_T, PAREA_T, CONCEPT_T> {
    
    
    public EntitySelectionListener<REL_T> getGroupRelationshipSelectedListener();

    public EntitySelectionListener<REL_T> getContainerRelationshipSelectedListener();
}
