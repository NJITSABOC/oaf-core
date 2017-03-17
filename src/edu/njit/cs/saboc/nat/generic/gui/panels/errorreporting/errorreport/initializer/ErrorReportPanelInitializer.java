package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer;

import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;

/**
 *
 * @author Chris O
 * 
 * @param <V>
 */
public interface ErrorReportPanelInitializer<V extends OntologyError> {
    public String getErrorTypeName();
    
    public String getStyledErrorDescriptionText();
    public OntologyError.Severity getDefaultSeverity();
    
    public V generateError(String comment, OntologyError.Severity severity);
}
