package edu.njit.cs.saboc.blu.core.abn.diff.utils;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris
 */
public class SetUtilities {
    public static <T> Set<T> getSetIntersection(Set<T> a, Set<T> b) {
        
        if(a == null || b == null) {
            return new HashSet<T>();
        }
        
        Set<T> intersection = new HashSet<T>(a);
        intersection.retainAll(b);
        
        return intersection;
    }
    
    public static <T> Set<T> getSetDifference(Set<T> a, Set<T> b) {
        Set<T> subtraction = new HashSet<T>(a);
        subtraction.removeAll(b);
        
        return subtraction;
    }
    
    public static <T> boolean setsEqual(Set<T> a, Set<T> b) {
        return a.equals(b);
    }
}
