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
public class ParentsRightClickMenuGenerator<T extends Concept> extends AuditReportRightClickMenu<T, T> {
    
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
            
            components.addAll(ParentErrorReportOptions.createParentErrorComponents(mainPanel, dataSource, item));

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
