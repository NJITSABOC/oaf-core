
package edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.rightclickmenu;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.rightclickmanager.EntityRightClickMenuGenerator;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.errorreport.AuditSet;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import edu.njit.cs.saboc.nat.generic.errorreport.error.child.ChildError;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.dialog.ErrorReportDialog;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class FocusConceptRightClickMenu<T extends Concept> extends EntityRightClickMenuGenerator<T> {
    
    private final NATBrowserPanel<T> mainPanel;
    private final ConceptBrowserDataSource<T> dataSource;
    
    public FocusConceptRightClickMenu(
            NATBrowserPanel<T> mainPanel, 
            ConceptBrowserDataSource<T> dataSource) {
        
        this.mainPanel = mainPanel;
        this.dataSource = dataSource;
    }

    @Override
    public ArrayList<JComponent> buildRightClickMenuFor(T item) {
        
        if (mainPanel.getAuditDatabase().getLoadedAuditSet().isPresent()) {
                 
            AuditSet<T> auditSet = mainPanel.getAuditDatabase().getLoadedAuditSet().get();
            T focusConcept = mainPanel.getFocusConceptManager().getActiveFocusConcept();
            
            ArrayList<JComponent> components = new ArrayList<>();

            
            JMenuItem auditCommentBtn = new JMenuItem("Leave audit comment");
            auditCommentBtn.setFont(auditCommentBtn.getFont().deriveFont(14.0f));
            auditCommentBtn.addActionListener((ae) -> {
                // TODO: Audit comment dialog
            });
           
            JMenuItem otherErrorBtn = new JMenuItem("Report other type of error");
            otherErrorBtn.setFont(otherErrorBtn.getFont().deriveFont(14.0f));
            otherErrorBtn.addActionListener((ae) -> {
                ErrorReportDialog.displayOtherErrorDialog(mainPanel, dataSource);
            });

            List<OntologyError<T>> reportedChildErrors = auditSet.getAllReportedErrors(focusConcept);
                        
            if(!reportedChildErrors.isEmpty()) {
                components.add(new JSeparator());
                
                components.add(generateRemoveErrorMenu(auditSet, focusConcept, reportedChildErrors));
            }
            
            return components;
        }

        return new ArrayList<>();
    }

    @Override
    public ArrayList<JComponent> buildEmptyListRightClickMenu() {        
        return new ArrayList<>();
    }
    
    private JMenu generateRemoveErrorMenu(
            AuditSet<T> auditSet, 
            T focusConcept, 
            List<OntologyError<T>> reportedChildErrors) {
  
        JMenu removeExistingErrorsMenu = new JMenu("Remove reported error(s)");
        removeExistingErrorsMenu.setFont(removeExistingErrorsMenu.getFont().deriveFont(14.0f));

        reportedChildErrors.forEach((error) -> {
            JMenuItem errorBtn = new JMenuItem(error.getSummaryText());
            errorBtn.setFont(errorBtn.getFont().deriveFont(14.0f));
            errorBtn.addActionListener((ae) -> {
                auditSet.deleteError(focusConcept, error);
            });

            removeExistingErrorsMenu.add(errorBtn);
        });
        
        return removeExistingErrorsMenu;
    }
}
