package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.explain;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public abstract class PropertyChangeDetailsFactory {
    
    public abstract Set<InheritableProperty> getFromOntProperties();
    public abstract Set<InheritableProperty> getToOntProperties();
    
    public abstract Set<InheritableProperty> getFromPropertiesFor(Concept c);
    public abstract Set<InheritableProperty> getToPropertiesFor(Concept c);

    public abstract Map<Concept, Set<InheritablePropertyChange>> getPropertyChanges();
}
