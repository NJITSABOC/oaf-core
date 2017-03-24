package edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.rightclickmenu;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.errorreport.AuditResult;
import edu.njit.cs.saboc.nat.generic.errorreport.AuditSet;
import java.util.Optional;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 */
public class ConceptAuditStateMenu<T extends Concept> extends JMenu {
    
    private final NATBrowserPanel<T> mainPanel;
    private final ConceptBrowserDataSource<T> dataSource;
    
    private final T concept;
    
    public ConceptAuditStateMenu(
            NATBrowserPanel<T> mainPanel, 
            ConceptBrowserDataSource<T> dataSource,
            T concept) {
        
        super("Update audit status");
        
        this.setFont(this.getFont().deriveFont(14.0f));
        
        this.mainPanel = mainPanel;
        this.dataSource = dataSource;
        this.concept = concept;
        
        JMenuItem conceptCorrectBtn = new JMenuItem("Correct");
        conceptCorrectBtn.setFont(conceptCorrectBtn.getFont().deriveFont(14.0f));
        conceptCorrectBtn.addActionListener((ae) -> {
            setCorrect();
        });

        this.add(conceptCorrectBtn);

        JMenuItem conceptErroroneousBtn = new JMenuItem("Erroneous");
        conceptErroroneousBtn.setFont(conceptErroroneousBtn.getFont().deriveFont(14.0f));
        conceptErroroneousBtn.addActionListener((ae) -> {
            setErroneous();
        });

        this.add(conceptErroroneousBtn);

        JMenuItem setUnauditedBtn = new JMenuItem("Unaudited");
        setUnauditedBtn.setFont(setUnauditedBtn.getFont().deriveFont(14.0f));
        setUnauditedBtn.addActionListener((ae) -> {
            setUnaudited();
        });
        
        this.add(setUnauditedBtn);
    }
    
    
    private void setCorrect() {
        Optional<AuditSet<T>> optAuditSet = mainPanel.getAuditDatabase().getLoadedAuditSet();
        
        if(optAuditSet.isPresent()) {
            AuditSet<T> auditSet = optAuditSet.get();
            
            Optional<AuditResult<T>> optAuditResult = auditSet.getAuditResult(concept);
            
            if(optAuditResult.isPresent()) {
                AuditResult<T> auditResult = optAuditResult.get();
                
                if(auditResult.getState() == AuditResult.State.Error) {
                    if(confirmRemoveErrors(AuditResult.State.Correct)) {
                        auditSet.updateAuditState(concept, AuditResult.State.Correct);
                    }
                } else {
                    auditSet.updateAuditState(concept, AuditResult.State.Correct);
                }
            }
        }
    }
    
    private void setUnaudited() {
        Optional<AuditSet<T>> optAuditSet = mainPanel.getAuditDatabase().getLoadedAuditSet();
        
        if(optAuditSet.isPresent()) {
            AuditSet<T> auditSet = optAuditSet.get();
            
            Optional<AuditResult<T>> optAuditResult = auditSet.getAuditResult(concept);
            
            if(optAuditResult.isPresent()) {
                AuditResult<T> auditResult = optAuditResult.get();
                
                if(auditResult.getState() == AuditResult.State.Error) {
                    if(confirmRemoveErrors(AuditResult.State.Unaudited)) {
                        auditSet.updateAuditState(concept, AuditResult.State.Unaudited);
                    }
                } else {
                    auditSet.updateAuditState(concept, AuditResult.State.Unaudited);
                }
            }
        }
    }
    
    private void setErroneous() {
        Optional<AuditSet<T>> optAuditSet = mainPanel.getAuditDatabase().getLoadedAuditSet();

        if (optAuditSet.isPresent()) {
            AuditSet<T> auditSet = optAuditSet.get();

            Optional<AuditResult<T>> optAuditResult = auditSet.getAuditResult(concept);

            if (optAuditResult.isPresent()) {
                auditSet.updateAuditState(concept, AuditResult.State.Error);
            }
        }
    }
    
    private boolean confirmRemoveErrors(AuditResult.State newState) {
        
        String message = String.format(""
                + "<html>Setting the concept as %s will remove all reported errors."
                + "<p>Are you sure you want to change the audit status of this concept?",
                newState.toString());
        
        int result = JOptionPane.showConfirmDialog(mainPanel, message, "Confirm Audit Status Change", JOptionPane.YES_NO_OPTION, 
                JOptionPane.WARNING_MESSAGE);
        
        return result == JOptionPane.YES_OPTION;
    }
}
