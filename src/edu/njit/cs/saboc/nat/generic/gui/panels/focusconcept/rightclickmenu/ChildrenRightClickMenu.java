package edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.rightclickmenu;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.errorreport.AuditSet;
import edu.njit.cs.saboc.nat.generic.errorreport.error.child.ChildError;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.dialog.ErrorReportDialog;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class ChildrenRightClickMenu<T extends Concept> extends AuditReportRightClickMenu<T, T> {
    
    private final NATBrowserPanel<T> mainPanel;
    private final ConceptBrowserDataSource<T> dataSource;
    
    public ChildrenRightClickMenu(
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

            JPanel namePanel = new JPanel(new BorderLayout());
            namePanel.add(Box.createHorizontalStrut(8), BorderLayout.WEST);
            namePanel.add(Box.createHorizontalStrut(8), BorderLayout.EAST);
            
            JLabel nameLabel = new JLabel(item.getName());
            nameLabel.setFont(nameLabel.getFont().deriveFont(16.0f));
            
            namePanel.setBackground(Color.WHITE);
            namePanel.setOpaque(true);
            
            namePanel.add(nameLabel, BorderLayout.CENTER);
            
            components.add(namePanel);
            
            components.add(new JSeparator());
            

            JMenuItem removeErroneousChildBtn = new JMenuItem("Remove child (erroneous)");
            removeErroneousChildBtn.setFont(removeErroneousChildBtn.getFont().deriveFont(14.0f));
            removeErroneousChildBtn.addActionListener((ae) -> {
                ErrorReportDialog.displayErroneousChildDialog(mainPanel, dataSource, item);
            });

            
            JMenuItem otherErrorBtn = new JMenuItem("Report other error with child");
            otherErrorBtn.setFont(otherErrorBtn.getFont().deriveFont(14.0f));
            otherErrorBtn.addActionListener((ae) -> {
                ErrorReportDialog.displayOtherChildErrorDialog(mainPanel, dataSource, item);
            });
            
            
            JMenuItem missingChildBtn = new JMenuItem("Report missing child");
            missingChildBtn.setFont(missingChildBtn.getFont().deriveFont(14.0f));
            missingChildBtn.addActionListener((ae) -> {
                ErrorReportDialog.displayMissingChildDialog(mainPanel, dataSource);
            });
            
            components.add(removeErroneousChildBtn);
            components.add(otherErrorBtn);
            
            components.add(new JSeparator());
            
            components.add(missingChildBtn);

            List<ChildError<T>> reportedChildErrors = auditSet.getChildErrors(focusConcept);
                        
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
        if (mainPanel.getAuditDatabase().getLoadedAuditSet().isPresent()) {

            ArrayList<JComponent> components = new ArrayList<>();

            AuditSet<T> auditSet = mainPanel.getAuditDatabase().getLoadedAuditSet().get();
            T focusConcept = mainPanel.getFocusConceptManager().getActiveFocusConcept();

            JMenuItem reportMissingChild = new JMenuItem("Report missing child");
            reportMissingChild.setFont(reportMissingChild.getFont().deriveFont(14.0f));
            reportMissingChild.addActionListener((ae) -> {
                ErrorReportDialog.displayMissingChildDialog(mainPanel, dataSource);
            });
            
            components.add(reportMissingChild);

            List<ChildError<T>> reportedChildErrors = auditSet.getChildErrors(focusConcept);
                        
            if(!reportedChildErrors.isEmpty()) {
                components.add(new JSeparator());
                
                components.add(generateRemoveErrorMenu(auditSet, focusConcept, reportedChildErrors));
            }
            
            return components;
        }
        
        return new ArrayList<>();
    }    
}