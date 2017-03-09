package edu.njit.cs.saboc.nat.generic.errorreport;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import java.util.ArrayList;
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

    private void auditConceptChanged() {
        changeListeners.forEach( (listener) -> {
            listener.auditConceptChanged();
        });
    }
}
