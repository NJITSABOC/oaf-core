package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;

/**
 *
 * @author Chris O
 */
public class DisjointAbNConfiguration extends AbNConfiguration {

    public DisjointAbNConfiguration(
            DisjointAbstractionNetwork abstractionNetwork,
            AbNUIConfiguration uiConfiguration,
            DisjointAbNTextConfiguration textConfiguration) {

        super(abstractionNetwork, uiConfiguration, textConfiguration);
    }
    
    public DisjointAbstractionNetwork getAbstractionNetwork() {
        return (DisjointAbstractionNetwork)super.getAbstractionNetwork();
    }

    @Override
    public DisjointAbNTextConfiguration getTextConfiguration() {
        return (DisjointAbNTextConfiguration) super.getTextConfiguration();
    }
}
