package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;

/**
 *
 * @author Chris
 * @param <T>
 */
public abstract class DisjointAbNTextConfiguration<T extends DisjointNode> extends AbNTextConfiguration<T> {
    
    public DisjointAbNTextConfiguration(OntologyEntityNameConfiguration ontologyEntityNameConfig) {
        super(ontologyEntityNameConfig);
    }
    
     public abstract String getOverlappingNodeTypeName(boolean plural);
}
