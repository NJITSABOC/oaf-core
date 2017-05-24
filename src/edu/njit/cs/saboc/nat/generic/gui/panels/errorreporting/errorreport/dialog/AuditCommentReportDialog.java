package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.dialog;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.AuditSetConceptCommentPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.AuditSetConceptCommentPanel.AuditCommentPanelListener;
import javax.swing.JDialog;

/**
 *
 * @author Chris O
 */
public class AuditCommentReportDialog {
    
    public static <T extends Concept> void displayAuditCommentDialog(
            NATBrowserPanel<T> mainPanel,
            T concept) {
        
        JDialog dialog = new JDialog();
        
        dialog.setModal(true);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(mainPanel.getParentFrame());
        
        AuditSetConceptCommentPanel<T> commentPanel = new AuditSetConceptCommentPanel<>(mainPanel, concept);
        
        commentPanel.addAuditSetCommentPanelListener(new AuditCommentPanelListener() {

            @Override
            public void commentSubmitted(String comment) {
                doClose();
            }

            @Override
            public void commentSubmissionCancelled() {
                doClose();
            }
            
            private void doClose() {
                dialog.setVisible(false);
                dialog.dispose();
            }
        });
        
        dialog.setTitle("Audit Set Concept Comment");
        
        dialog.add(commentPanel);
        
        dialog.setVisible(true);
    }
}
