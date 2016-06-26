package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.ArrayList;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public interface ConceptLocationDataFactory {
    public Set<Concept> getConceptsFromIds(ArrayList<String> ids);
}
