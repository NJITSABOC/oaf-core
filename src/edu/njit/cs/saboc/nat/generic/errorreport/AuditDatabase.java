package edu.njit.cs.saboc.nat.generic.errorreport;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import java.util.ArrayList;
import java.util.Optional;

/**
 * A local "database" for managing Audit Sets. 
 * 
 * @author Chris O
 * @param <T>
 */
public class AuditDatabase<T extends Concept> {

    /**
     * Listener for handing changes the currently loaded
     * audit set
     */
    public interface AuditDatabaseChangeListener {
        public void auditSetChanged();
    }
    
    private Optional<AuditSet<T>> loadedAuditSet;
    
    private final NATBrowserPanel<T> mainPanel;
    private final ConceptBrowserDataSource<T> dataSource;
    
    private final ArrayList<AuditDatabaseChangeListener> changeListeners = new ArrayList<>();

    public AuditDatabase(
            NATBrowserPanel<T> mainPanel, 
            ConceptBrowserDataSource<T>  dataSource) {
        
        this.loadedAuditSet = Optional.empty();
        
        this.mainPanel = mainPanel;
        this.dataSource = dataSource;
    }
    
    public void addAuditDatabaseChangeListener(AuditDatabaseChangeListener listener) {
        changeListeners.add(listener);
    }
    
    public void removeAuditDatabaseChangeListener(AuditDatabaseChangeListener listener) {
        changeListeners.remove(listener);
    }
    
    public void setAuditSet(AuditSet<T> auditSet) {
        
        // TODO: Cleanup for an already-loaded audit set?
        
        this.loadedAuditSet = Optional.of(auditSet);
        
        auditSet.addAuditSetChangedListener(new AuditSetChangedAdapter<T>() {

            @Override
            public void auditSetChanged() {
                loadedAuditSetChanged();
            }
        });
    }
    
    public Optional<AuditSet<T>> getLoadedAuditSet() {
        return loadedAuditSet;
    }

    private void loadedAuditSetChanged() {
        changeListeners.forEach((listener) -> {
            listener.auditSetChanged();
        });
    }
}
