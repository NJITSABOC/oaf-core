package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public interface RelationshipEquality<REL_T> {
    public boolean equalsNoInheritance(HashSet<REL_T> a, HashSet<REL_T> b);
    
    public boolean equalsInheritance(HashSet<REL_T> a, HashSet<REL_T> b);
}
