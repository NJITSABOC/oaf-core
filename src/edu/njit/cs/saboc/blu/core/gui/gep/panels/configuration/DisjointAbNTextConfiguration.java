package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;

/**
 *
 * @author Chris
 */
public interface DisjointAbNTextConfiguration<T extends DisjointNode> extends AbNTextConfiguration<T> {
    
     public String getOverlappingNodeTypeName(boolean plural);
}
