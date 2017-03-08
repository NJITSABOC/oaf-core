package edu.njit.cs.saboc.nat.generic.errorreport;

import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 
 * @author Chris O
 * @param <T>
 */
public class AuditDatabase<T extends Concept> {

    public interface AuditChangeListener {
        public void auditConceptChanged();
    }
    
    private final Map<T, AuditResult<T>> auditResults = new HashMap<>();
    
    private Optional<AuditSet<T>> loadedAuditSet;
    
    private final NATBrowserPanel<T> mainPanel;
    private final ConceptBrowserDataSource<T> dataSource;
    
    private final ArrayList<AuditChangeListener> changeListeners = new ArrayList<>();

    public AuditDatabase(
            NATBrowserPanel<T> mainPanel, 
            ConceptBrowserDataSource<T>  dataSource) {
        
        this.loadedAuditSet = Optional.empty();
        
        this.mainPanel = mainPanel;
        this.dataSource = dataSource;
    }
    
    public void setAuditSet(AuditSet<T> auditSet) {
        this.loadedAuditSet = Optional.of(auditSet);
    }
    
    public Optional<AuditSet<T>> getLoadedAuditSet() {
        return loadedAuditSet;
    }

    private AuditResult createAuditResult(T concept) {
        AuditResult r = new AuditResult();
        auditResults.put(concept, r);
        
        return r;
    }
    
    private AuditResult getOrCreateAuditResult(T concept) {
        
        if(!auditResults.containsKey(concept)) {
            return createAuditResult(concept);
        } else {
            return auditResults.get(concept);
        }
        
    }
    
    public void updateAuditState(T concept) {
        AuditResult ar = getOrCreateAuditResult(concept);
        

        auditConceptChanged();
    }
    
    public void updateComment(T concept, String comment) {
        AuditResult ar = getOrCreateAuditResult(concept);

        ar.setComment(comment);
        
        auditConceptChanged();
    }
    
    public void addError(T concept, OntologyError<T> error) {
        AuditResult ar = getOrCreateAuditResult(concept);
        
        ar.addError(error);
        
        auditConceptChanged();
    }

    public void deleteAuditResult(T concept) {
        auditResults.remove(concept);

        auditConceptChanged();
    }
    
    public void deleteError(T concept, OntologyError<T> error) {
        AuditResult ar = getOrCreateAuditResult(concept);
        ar.getErrors().remove(error);
        
        auditConceptChanged();
    }
    
    public void updateError(T concept, OntologyError<T> oldError, OntologyError<T> newError) {
        
        AuditResult ar = getOrCreateAuditResult(concept);
        
        ar.addError(newError);
        ar.getErrors().remove(oldError);
        
        auditConceptChanged();
    }
    
    private void auditConceptChanged() {
        changeListeners.forEach( (listener) -> {
            listener.auditConceptChanged();
        });
    }

    public Optional<AuditResult> getAuditResult(T concept) {
        return Optional.ofNullable(auditResults.get(concept));
    }
}
