package edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing;

import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;

/**
 *
 * @author Chris O
 */
public class SinglyRootedNodeLabelCreator {
    
    public String getRootNameStr(SinglyRootedNode node) {
        return node.getRoot().getName();
    }
    
    public String getCountStr(SinglyRootedNode node) {
        return String.format("(%d)", node.getConceptCount());
    }
    
}
