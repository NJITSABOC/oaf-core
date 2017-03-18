package edu.njit.cs.saboc.nat.generic.errorreport;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;

/**
 *
 * @author Chris O
 * @param <T>
 */
public interface AuditSetChangedListener<T extends Concept> {

    public void conceptAddedToAuditSet(T concept);

    public void conceptRemovedFromAuditSet(T concept);

    public void auditStateChanged(T concept, AuditResult.State previous, AuditResult.State current);

    public void commentChanged(T concept, String comment);

    public void errorAdded(T concept, OntologyError<T> error);

    public void errorRemoved(T concept, OntologyError<T> error);

    public void auditResultRemoved(T concept);

    public void errorUpdated(T concept, OntologyError<T> before, OntologyError<T> current);

    public void auditSetChanged();
}
