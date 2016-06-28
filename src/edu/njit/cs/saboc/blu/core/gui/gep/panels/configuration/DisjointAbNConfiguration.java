package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;

/**
 *
 * @author Chris O
 */
public class DisjointAbNConfiguration extends AbNConfiguration {

    public DisjointAbNConfiguration(DisjointAbstractionNetwork disjointAbN) {
        super(disjointAbN);
    }
    
    public void setUIConfiguration(DisjointAbNUIConfiguration config) {
        super.setUIConfiguration(config);
    }
    
    public void setTextConfiguration(DisjointAbNTextConfiguration config) {
        super.setTextConfiguration(config);
    }
    
    public DisjointAbstractionNetwork getAbstractionNetwork() {
        return (DisjointAbstractionNetwork)super.getAbstractionNetwork();
    }
    
    public DisjointAbNUIConfiguration getUIConfiguration() {
        return (DisjointAbNUIConfiguration) super.getUIConfiguration();
    }

    @Override
    public DisjointAbNTextConfiguration getTextConfiguration() {
        return (DisjointAbNTextConfiguration) super.getTextConfiguration();
    }
}
