package edu.njit.cs.saboc.blu.core.abn.diff.utils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris
 */
public class SetUtilities {
    public static <T> Set<T> getSetIntersection(Set<T> a, Set<T> b) {
        
        if(a == null || b == null) {
            return Collections.emptySet();
        }
        
        Set<T> intersection = new HashSet<>(a);
        intersection.retainAll(b);
        
        return intersection;
    }
    
    public static <T> Set<T> getSetUnion(Set<T> a, Set<T> b) {
        Set<T> union = new HashSet<>(a);
        union.addAll(b);
        
        return union;
    }
    
    public static <T> Set<T> getSetDifference(Set<T> a, Set<T> b) {
        Set<T> subtraction = new HashSet<>(a);
        subtraction.removeAll(b);
        
        return subtraction;
    }
    
    public static <T> boolean setsEqual(Set<T> a, Set<T> b) {
        return a.equals(b);
    }
}
