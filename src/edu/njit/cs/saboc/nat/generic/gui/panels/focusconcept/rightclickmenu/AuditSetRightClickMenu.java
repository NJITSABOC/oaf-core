package edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.rightclickmenu;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.comparators.ConceptNameComparator;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.errorreport.AuditResult;
import edu.njit.cs.saboc.nat.generic.errorreport.AuditSet;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.dialog.AuditCommentReportDialog;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 */
public class AuditSetRightClickMenu<T extends Concept> extends AuditReportRightClickMenu<T, T> {
    
    private final NATBrowserPanel<T> mainPanel;
    private final ConceptBrowserDataSource<T> dataSource;
    
    public AuditSetRightClickMenu(
            NATBrowserPanel<T> mainPanel, 
            ConceptBrowserDataSource<T> dataSource) {
        
        this.mainPanel = mainPanel;
        this.dataSource = dataSource;
    }

    @Override
    public ArrayList<JComponent> buildRightClickMenuFor(T auditSetConcept) {
        
        ArrayList<JComponent> components = new ArrayList<>();

        JPanel namePanel = new JPanel(new BorderLayout());
        namePanel.add(Box.createHorizontalStrut(8), BorderLayout.WEST);
        namePanel.add(Box.createHorizontalStrut(8), BorderLayout.EAST);

        JLabel nameLabel = new JLabel(auditSetConcept.getName());
        nameLabel.setFont(nameLabel.getFont().deriveFont(16.0f));

        namePanel.setBackground(Color.WHITE);
        namePanel.setOpaque(true);

        namePanel.add(nameLabel, BorderLayout.CENTER);

        components.add(namePanel);
        components.add(new JSeparator());
        
        if (mainPanel.getAuditDatabase().getLoadedAuditSet().isPresent()) {
            AuditSet<T> auditSet = mainPanel.getAuditDatabase().getLoadedAuditSet().get();
        
            if(auditSet.getConcepts().contains(auditSetConcept)) {
                Optional<AuditResult<T>> optAuditResult = auditSet.getAuditResult(auditSetConcept);
                
                if(optAuditResult.isPresent()) {
                    components.add(new ConceptAuditStateMenu<>(mainPanel, dataSource, auditSetConcept));
                }
            }
            
            JMenuItem auditCommentBtn = new JMenuItem("Edit audit comment");
            auditCommentBtn.setFont(auditCommentBtn.getFont().deriveFont(14.0f));
            auditCommentBtn.addActionListener((ae) -> {
                AuditCommentReportDialog.displayAuditCommentDialog(mainPanel, dataSource, auditSetConcept);
            });

            components.add(auditCommentBtn);
            
            components.add(new JSeparator());
            
            components.add(createReportErrorsMenu(auditSet, auditSetConcept));
           
            List<OntologyError<T>> reportedErrors = auditSet.getAllReportedErrors(auditSetConcept);
                        
            if(!reportedErrors.isEmpty()) {
                components.add(new JSeparator());
                
                components.add(generateRemoveErrorMenu(auditSet, auditSetConcept, reportedErrors));
            }
            
        }

        return components; 
    }

    @Override
    public ArrayList<JComponent> buildEmptyListRightClickMenu() {        
        return new ArrayList<>();
    }
    
    private JMenu createReportErrorsMenu(AuditSet<T> auditSet, T concept) {
        
        JMenu errorReportBtn = new JMenu("Report error");
        errorReportBtn.setFont(errorReportBtn.getFont().deriveFont(14.0f));
        
        ArrayList<T> parents = new ArrayList<>(dataSource.getOntology().getConceptHierarchy().getParents(concept));
        parents.sort(new ConceptNameComparator<>());
        
        if(!parents.isEmpty()) {
            JMenu reportParentErrorBtn = new JMenu("Report error with parent");
            reportParentErrorBtn.setFont(reportParentErrorBtn.getFont().deriveFont(14.0f));
            
            parents.forEach( (parent) -> {
                reportParentErrorBtn.add(createParentErrorReportMenu(parent));
            });
            
            errorReportBtn.add(reportParentErrorBtn);
        }
        
        return errorReportBtn;
    }
    
    private JMenu createParentErrorReportMenu(T parent) {
        JMenu parentMenu = new JMenu(parent.getName());
        parentMenu.setFont(parentMenu.getFont().deriveFont(14.0f));
        
        ArrayList<JComponent> components = ParentErrorReportOptions.createParentErrorComponents(mainPanel, dataSource, parent);
        
        components.forEach( (component) -> {
            parentMenu.add(component);
        });
        
        return parentMenu;
    }
}