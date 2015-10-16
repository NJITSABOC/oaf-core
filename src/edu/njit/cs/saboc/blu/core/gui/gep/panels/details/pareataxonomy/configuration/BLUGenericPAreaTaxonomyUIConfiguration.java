package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericPArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.ui.BLUPartitionedAbNUIConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.BLUGenericPAreaTaxonomyConfiguration;

/**
 *
 * @author Den
 */
public abstract class BLUGenericPAreaTaxonomyUIConfiguration<
        TAXONOMY_T extends GenericPAreaTaxonomy, 
        AREA_T extends GenericArea,
        PAREA_T extends GenericPArea,
        CONCEPT_T,
        REL_T,
        T extends BLUGenericPAreaTaxonomyListenerConfiguration<TAXONOMY_T, AREA_T, PAREA_T, CONCEPT_T, REL_T>> extends 
            BLUPartitionedAbNUIConfiguration<TAXONOMY_T, AREA_T, PAREA_T, CONCEPT_T, 
                BLUGenericPAreaTaxonomyConfiguration, T> {
    
    public BLUGenericPAreaTaxonomyUIConfiguration(T listenerConfig) {
        super(listenerConfig);
    }
    
    public T getListenerConfiguration() {
        return super.getListenerConfiguration();
    }
}
