package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.factory;

import edu.njit.cs.saboc.blu.core.abn.node.Node;

/**
 *
 * @author Chris O
 */
public interface NodeSummaryTextFactory<T extends Node> {
    public String createNodeSummaryText(T node);
}
