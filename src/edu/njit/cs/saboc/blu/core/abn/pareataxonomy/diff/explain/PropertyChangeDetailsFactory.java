package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.explain;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public interface PropertyChangeDetailsFactory {
    
    public Set<InheritableProperty> getFromProperties(Concept c);
    public Set<InheritableProperty> getToProperties(Concept c);

    public Map<Concept, Set<InheritablePropertyChange>> getPropertyChangesFor(
            Concept concept, 
            Set<Concept> descendants);
}
