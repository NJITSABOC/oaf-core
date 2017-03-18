package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.errorreport.AuditSet;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import edu.njit.cs.saboc.nat.generic.gui.panels.BaseNATPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer.ErrorReportPanelInitializer;
import java.util.ArrayList;
import java.util.Optional;
import javax.swing.JOptionPane;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 * @param <V>
 */
public abstract class ErrorReportPanel<T extends Concept, V extends OntologyError<T>> 
    extends BaseNATPanel<T> {

    public interface ErrorReportPanelListener<V extends OntologyError> {
        public void errorSubmitted(V error);
        public void errorSubmissionCancelled();
    }
    
    private final ArrayList<ErrorReportPanelListener<V>> errorReportPanelListeners = new ArrayList<>();
    
    public ErrorReportPanel(
            NATBrowserPanel<T> mainPanel, 
            ConceptBrowserDataSource<T> dataSource) {
        
        super(mainPanel, dataSource);
    }
    
    public void addErrorReportPanelListener(ErrorReportPanelListener<V> errorSubmissionListener) {
        errorReportPanelListeners.add(errorSubmissionListener);
    }
    
    public void removeErrorReportPanelListener(ErrorReportPanelListener<V> errorSubmissionListener) {
        errorReportPanelListeners.remove(errorSubmissionListener);
    }
    
    public void submitError() {
        T focusConcept = getMainPanel().getFocusConceptManager().getActiveFocusConcept();
        
        V error = getError();
        
        AuditSet<T> currentAuditSet = getMainPanel().getAuditDatabase().getLoadedAuditSet().get();
        
        if(!currentAuditSet.getConcepts().contains(focusConcept)) {
            
            int option = JOptionPane.showInternalConfirmDialog(
                    this.getParent(), 
                    "<html>The current Focus Concept is not part of the current Audit Set.<br>Add Focus Concept to Audit Set and report error?", 
                    "Focus Concept Not in Audit Set", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.INFORMATION_MESSAGE);
            
            if (option == JOptionPane.NO_OPTION) {
                errorReportPanelListeners.forEach((listener) -> {
                    listener.errorSubmissionCancelled();
                });

                return;
            }
            
            currentAuditSet.addConcept(focusConcept);
        }
        
        currentAuditSet.addError(focusConcept, error);
        
        errorReportPanelListeners.forEach( (listener) -> {
            listener.errorSubmitted(error);
        });
    }
    
    public abstract Optional<? extends ErrorReportPanelInitializer> getInitializer();
    
    public abstract void reset();
    public abstract boolean errorReady();
    
    public abstract V getError();
}
