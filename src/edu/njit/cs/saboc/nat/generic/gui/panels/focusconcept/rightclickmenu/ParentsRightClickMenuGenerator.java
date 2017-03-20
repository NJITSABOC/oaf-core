package edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.rightclickmenu;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.errorreport.AuditSet;
import edu.njit.cs.saboc.nat.generic.errorreport.error.parent.ParentError;
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
public class ParentsRightClickMenuGenerator<T extends Concept> extends AuditReportRightClickMenu<T> {
    
    private final NATBrowserPanel<T> mainPanel;
    private final ConceptBrowserDataSource<T> dataSource;
    
    public ParentsRightClickMenuGenerator(
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

            JMenuItem removeErroneousParentBtn = new JMenuItem("Remove parent (erroneous)");
            removeErroneousParentBtn.setFont(removeErroneousParentBtn.getFont().deriveFont(14.0f));
            removeErroneousParentBtn.addActionListener((ae) -> {
                ErrorReportDialog.displayErroneousParentDialog(mainPanel, dataSource, item);
            });

            JMenuItem removeRedundantParentBtn = new JMenuItem("Remove parent (redundant)");
            removeRedundantParentBtn.setFont(removeRedundantParentBtn.getFont().deriveFont(14.0f));
            removeRedundantParentBtn.addActionListener((ae) -> {
                ErrorReportDialog.displayRedundantParentErrorDialog(mainPanel, dataSource, item);
            });
            
            JMenuItem otherParentErrorBtn = new JMenuItem("Other error with parent");
            otherParentErrorBtn.setFont(otherParentErrorBtn.getFont().deriveFont(14.0f));
            otherParentErrorBtn.addActionListener((ae) -> {
                ErrorReportDialog.displayOtherParentErrorDialog(mainPanel, dataSource, item);
            });

            JMenuItem replaceParentBtn = new JMenuItem("Replace erroneous parent");
            replaceParentBtn.setFont(replaceParentBtn.getFont().deriveFont(14.0f));
            replaceParentBtn.addActionListener((ae) -> {
                ErrorReportDialog.displayReplaceParentDialog(mainPanel, dataSource, item);
            });
            
            JMenuItem reportMissingParentBtn = new JMenuItem("Report missing parent");
            reportMissingParentBtn.setFont(reportMissingParentBtn.getFont().deriveFont(14.0f));
            reportMissingParentBtn.addActionListener((ae) -> {
                ErrorReportDialog.displayMissingParentDialog(mainPanel, dataSource);
            });
            
            
            components.add(removeErroneousParentBtn);
            components.add(removeRedundantParentBtn);
            components.add(otherParentErrorBtn);
            components.add(replaceParentBtn);
            
            components.add(new JSeparator());

            components.add(reportMissingParentBtn);
            
            List<ParentError<T>> reportedParentErrors = auditSet.getParentErrors(focusConcept);
                        
            if(!reportedParentErrors.isEmpty()) {
                components.add(new JSeparator());
                
                components.add(generateRemoveErrorMenu(auditSet, focusConcept, reportedParentErrors));
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

            JMenuItem reportMissingParent = new JMenuItem("Report missing parent");
            reportMissingParent.addActionListener((ae) -> {
                ErrorReportDialog.displayMissingParentDialog(mainPanel, dataSource);
            });

            components.add(reportMissingParent);

            List<ParentError<T>> reportedParentErrors = auditSet.getParentErrors(focusConcept);

            if (!reportedParentErrors.isEmpty()) {
                components.add(new JSeparator());

                components.add(generateRemoveErrorMenu(auditSet, focusConcept, reportedParentErrors));
            }

            return components;
        }
        
        return new ArrayList<>();
    }
    
}
