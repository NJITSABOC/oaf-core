package edu.njit.cs.saboc.blu.core.abn;

import edu.njit.cs.saboc.blu.core.abn.node.Node;

/**
 *
 * @author Chris O
 */
public interface RootedSubAbstractionNetwork<T extends Node, ABN_T extends AbstractionNetwork> 
        extends SubAbstractionNetwork<ABN_T> {
    
    public T getSelectedRoot();
}
