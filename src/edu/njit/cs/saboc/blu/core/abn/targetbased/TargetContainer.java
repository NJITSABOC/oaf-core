package edu.njit.cs.saboc.blu.core.abn.targetbased;

import SnomedShared.generic.GenericGroupContainer;

/**
 *
 * @author Chris O
 */
public class TargetContainer<T extends TargetGroup> extends GenericGroupContainer {
    public TargetContainer(int id) {
        super(id);
    }
}
