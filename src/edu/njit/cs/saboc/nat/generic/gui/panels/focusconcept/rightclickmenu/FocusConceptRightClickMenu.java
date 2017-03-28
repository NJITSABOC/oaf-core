
package edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.rightclickmenu;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.errorreport.AuditResult;
import edu.njit.cs.saboc.nat.generic.errorreport.AuditSet;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.dialog.AuditCommentReportDialog;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.dialog.ErrorReportDialog;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class FocusConceptRightClickMenu<T extends Concept> extends AuditReportRightClickMenu<T, T> {
    
    private final NATBrowserPanel<T> mainPanel;
    private final ConceptBrowserDataSource<T> dataSource;
    
    public FocusConceptRightClickMenu(
            NATBrowserPanel<T> mainPanel, 
            ConceptBrowserDataSource<T> dataSource) {
        
        this.mainPanel = mainPanel;
        this.dataSource = dataSource;
    }
    
    protected NATBrowserPanel<T> getMainPanel() {
        return mainPanel;
    }
    
    protected ConceptBrowserDataSource<T> getDataSource() {
        return dataSource;
    }

    @Override
    public ArrayList<JComponent> buildRightClickMenuFor(T item) {
        
        if (mainPanel.getAuditDatabase().getLoadedAuditSet().isPresent()) {
            AuditSet<T> auditSet = mainPanel.getAuditDatabase().getLoadedAuditSet().get();
            T focusConcept = mainPanel.getFocusConceptManager().getActiveFocusConcept();
            
            ArrayList<JComponent> components = new ArrayList<>();
            
            if(auditSet.getConcepts().contains(focusConcept)) {
                Optional<AuditResult<T>> optAuditResult = auditSet.getAuditResult(focusConcept);
                
                if(optAuditResult.isPresent()) {
                    components.add(new ConceptAuditStateMenu<>(mainPanel, dataSource, item));
                }
            }
            
            JMenuItem auditCommentBtn = new JMenuItem("Edit audit comment");
            auditCommentBtn.setFont(auditCommentBtn.getFont().deriveFont(14.0f));
            auditCommentBtn.addActionListener((ae) -> {
                 AuditCommentReportDialog.displayAuditCommentDialog(mainPanel, dataSource, item);
            });
           
            JMenuItem otherErrorBtn = new JMenuItem("Report other type of error");
            otherErrorBtn.setFont(otherErrorBtn.getFont().deriveFont(14.0f));
            otherErrorBtn.addActionListener((ae) -> {
                ErrorReportDialog.displayOtherErrorDialog(mainPanel, dataSource);
            });
            
            components.add(auditCommentBtn);
            components.add(otherErrorBtn);

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
}
