package edu.njit.cs.saboc.nat.generic.errorreport;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class AuditSetChangedAdapter<T extends Concept> implements AuditSetChangedListener<T> {

        @Override
        public void conceptAddedToAuditSet(T concept) {
            
        }

        @Override
        public void conceptRemovedFromAuditSet(T concept) {
            
        }

        @Override
        public void auditStateChanged(T concept, AuditResult.State previous, AuditResult.State current) {
            
        }

        @Override
        public void commentChanged(T concept, String comment) {
            
        }

        @Override
        public void errorAdded(T concept, OntologyError<T> error) {
            
        }

        @Override
        public void errorRemoved(T concept, OntologyError<T> error) {
            
        }

        @Override
        public void auditResultRemoved(T concept) {
            
        }

        @Override
        public void errorUpdated(T concept, OntologyError<T> before, OntologyError<T> current) {
            
        }

        @Override
        public void auditSetChanged() {
            
        }
    }