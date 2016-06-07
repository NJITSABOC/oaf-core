package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.ui.listener.BLUPartitionedAbNListenerConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionListener;

/**
 *
 * @author Chris O
 */
public interface BLUGenericPAreaTaxonomyListenerConfiguration<
        TAXONOMY_T extends PAreaTaxonomy, 
        AREA_T extends Area,
        PAREA_T extends PArea,
        CONCEPT_T,
        REL_T> extends BLUPartitionedAbNListenerConfiguration<TAXONOMY_T, AREA_T, PAREA_T, CONCEPT_T> {
    
    
    public EntitySelectionListener<REL_T> getGroupRelationshipSelectedListener();

    public EntitySelectionListener<REL_T> getContainerRelationshipSelectedListener();
}
