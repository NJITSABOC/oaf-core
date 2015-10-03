package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUDisjointableConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.BLUGenericPAreaTaxonomyDataConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.BLUGenericPAreaTaxonomyTextConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.BLUGenericPAreaTaxonomyUIConfiguration;

/**
 *
 * @author Chris O
 */
public abstract class BLUGenericPAreaTaxonomyConfiguration<
        T extends BLUGenericPAreaTaxonomyDataConfiguration,
        V extends BLUGenericPAreaTaxonomyUIConfiguration,
        U extends BLUGenericPAreaTaxonomyTextConfiguration> extends BLUDisjointableConfiguration<T, V, U> {
    
    public BLUGenericPAreaTaxonomyConfiguration() {
        
    }

    public BLUGenericPAreaTaxonomyConfiguration(T dataConfig, V uiConfig, U textConfig) {
        super(dataConfig, uiConfig, textConfig);
    }
    
    public T getDataConfiguration() {
        return super.getDataConfiguration();
    }

    public V getUIConfiguration() {
        return super.getUIConfiguration();
    }

    public U getTextConfiguration() {
        return super.getTextConfiguration();
    }
}
