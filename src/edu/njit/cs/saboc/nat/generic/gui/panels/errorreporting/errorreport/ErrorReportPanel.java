package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.errorreport.AuditSet;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import edu.njit.cs.saboc.nat.generic.gui.panels.BaseNATPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer.ErrorReportPanelInitializer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
    
    public ErrorReportPanel(NATBrowserPanel<T> mainPanel) {
        super(mainPanel);
    }
    
    public void addErrorReportPanelListener(ErrorReportPanelListener<V> errorSubmissionListener) {
        errorReportPanelListeners.add(errorSubmissionListener);
    }
    
    public void removeErrorReportPanelListener(ErrorReportPanelListener<V> errorSubmissionListener) {
        errorReportPanelListeners.remove(errorSubmissionListener);
    }
    
    public void submitError() {
        
        if(!this.getInitializer().isPresent()) {
            // TODO: error message?
            
            JOptionPane.showInternalMessageDialog(
                    this.getParent(), 
                    "<html>An error occured when submitting this error.", 
                    "Error reporting error", 
                    JOptionPane.INFORMATION_MESSAGE);
            
            return;
        }
        
        T erroneousConcept = this.getInitializer().get().getErroneousConcept();
        
        V error = getError();
        
        AuditSet<T> currentAuditSet = getMainPanel().getAuditDatabase().getLoadedAuditSet().get();
        
        if(!currentAuditSet.getConcepts().contains(erroneousConcept)) {
            
            int option = JOptionPane.showInternalConfirmDialog(
                    this.getParent(), 
                    "<html>The erroneous concept is not part of the current Audit Set.<br>Add Concept to Audit Set and report error?", 
                    "Erroneous Concept Not in Audit Set", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.INFORMATION_MESSAGE);
            
            if (option == JOptionPane.NO_OPTION) {
                errorReportPanelListeners.forEach((listener) -> {
                    listener.errorSubmissionCancelled();
                });

                return;
            }
            
            currentAuditSet.addConcept(erroneousConcept);
        }
        
        List<OntologyError<T>> allErrors = currentAuditSet.getAllReportedErrors(erroneousConcept);
        
        if (allErrors.contains(error)) {
            
            Object[] options = {
                "Report as-is",
                "Overwrite existing",
                "Cancel"};

            int option = JOptionPane.showInternalOptionDialog(
                    this.getParent(),
                    "<html>A similar error has already been reported for this concept."
                    + "<br>Please select an option.",
                    
                    "Error Already Reproted",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[1]);

            if(option == 0) {
                currentAuditSet.addError(erroneousConcept, error);
            } else if(option == 1) {
                
                List<OntologyError<T>> matchedErrors = allErrors.stream().filter( (existingError) -> {
                    return existingError.equals(error);
                }).collect(Collectors.toList());
                
                matchedErrors.forEach( (existingError) -> {
                    currentAuditSet.updateError(erroneousConcept, existingError, error);
                });
                
            } else if(option == 2) {
                errorReportPanelListeners.forEach((listener) -> {
                    listener.errorSubmissionCancelled();
                });

                return;
            }
            
        } else {
            currentAuditSet.addError(erroneousConcept, error);
        }
        
        errorReportPanelListeners.forEach( (listener) -> {
            listener.errorSubmitted(error);
        });
    }
    
    public abstract Optional<? extends ErrorReportPanelInitializer<T, V>> getInitializer();
    
    public abstract void reset();
    public abstract boolean errorReady();
    
    public abstract V getError();
}
