package edu.njit.cs.saboc.nat.generic.errorreport;

/**
 * Exception that occurs when loading an audit set
 * 
 * @author Chris O
 */
public class AuditSetLoaderException extends Exception {
    public AuditSetLoaderException(String message) {
        super(message);
    }
}
