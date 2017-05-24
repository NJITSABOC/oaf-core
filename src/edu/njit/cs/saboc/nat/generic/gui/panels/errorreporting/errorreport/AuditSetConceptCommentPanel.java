
package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.errorreport.AuditResult;
import edu.njit.cs.saboc.nat.generic.errorreport.AuditSet;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.ErrorSubmissionPanel.ErrorSubmissionListener;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Optional;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 */
public class AuditSetConceptCommentPanel<T extends Concept> extends JPanel {
    
    public interface AuditCommentPanelListener {
        public void commentSubmitted(String comment);
        public void commentSubmissionCancelled();
    }
    
    private final NATBrowserPanel<T> mainPanel;
    
    private final AuditCommentPanel<T> commentPanel;
    
    private final ErrorSubmissionPanel<T> errorSubmissionPanel;
    
    private final T concept;

    private final ArrayList<AuditCommentPanelListener> commentReportPanelListeners = new ArrayList<>();
    
    public AuditSetConceptCommentPanel(
            NATBrowserPanel<T> mainPanel, 
            T concept) {
        
        this.mainPanel = mainPanel;
        this.concept = concept;
        
        this.commentPanel = new AuditCommentPanel<>(mainPanel);
        
        this.errorSubmissionPanel = new ErrorSubmissionPanel<>(
                mainPanel,
                new ErrorSubmissionListener() {

                    @Override
                    public void resetClicked() {
                        clearComment();
                    }

                    @Override
                    public void submitClicked() {
                        submitComment();
                    }
                });

        this.setLayout(new BorderLayout());
        
        this.add(commentPanel, BorderLayout.CENTER);
        this.add(errorSubmissionPanel, BorderLayout.SOUTH);  
        
        initialize();
    }
    
    public void addAuditSetCommentPanelListener(AuditCommentPanelListener errorSubmissionListener) {
        commentReportPanelListeners.add(errorSubmissionListener);
    }
    
    public void removeAuditSetCommentPanelListener(AuditCommentPanelListener errorSubmissionListener) {
        commentReportPanelListeners.remove(errorSubmissionListener);
    }
    
    public void initialize() {
        if(mainPanel.getAuditDatabase().getLoadedAuditSet().isPresent()) {
            AuditSet<T> auditSet = mainPanel.getAuditDatabase().getLoadedAuditSet().get();
            
            Optional<AuditResult<T>> optAuditResult = auditSet.getAuditResult(concept);
            
            if(optAuditResult.isPresent()) {
                this.commentPanel.displayComment(optAuditResult.get().getComment());
            }
        }
    }
    
    private void clearComment() {
        this.commentPanel.reset();
        
        submitComment();
    }
    
    private void submitComment() {
        
        if (mainPanel.getAuditDatabase().getLoadedAuditSet().isPresent()) {
            AuditSet<T> auditSet = mainPanel.getAuditDatabase().getLoadedAuditSet().get();

            if (!auditSet.getConcepts().contains(concept)) {

                int option = JOptionPane.showInternalConfirmDialog(
                        this.getParent(),
                        "<html>The current Focus Concept is not part of the current Audit Set.<br>Add Focus Concept to Audit Set and report error?",
                        "Focus Concept Not in Audit Set",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE);

                if (option == JOptionPane.NO_OPTION) {
                    
                    commentReportPanelListeners.forEach((listener) -> {
                        listener.commentSubmissionCancelled();
                    });

                    return;
                }

                auditSet.addConcept(concept);
            }

            String comment = commentPanel.getComment();
            
            auditSet.updateComment(concept, comment);
            
            commentReportPanelListeners.forEach((listener) -> {
                listener.commentSubmitted(comment);
            });
        }
    }
}
