package edu.njit.cs.saboc.nat.generic.gui.filterable.list.renderer;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.errorreport.AuditResult;
import edu.njit.cs.saboc.nat.generic.errorreport.AuditSet;
import java.awt.Color;
import java.util.Optional;
import javax.swing.JLabel;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 */
public class AuditSetConceptRenderer<T extends Concept> extends DetailsConceptRenderer<T> {
    
    private final JLabel auditStatusLabel;
    
    public AuditSetConceptRenderer(NATBrowserPanel<T> mainPanel, 
            ConceptBrowserDataSource<T> dataSource) {
        
        super(mainPanel, dataSource);
        
        this.auditStatusLabel = new JLabel();
        
        super.addDetailsLabel(auditStatusLabel);
    }

    @Override
    public void showDetailsFor(Filterable<T> filterableEntry) {
        super.showDetailsFor(filterableEntry);
        
        this.auditStatusLabel.setForeground(Color.BLACK);
        this.getConceptRenderer().getConceptNameLabel().setForeground(Color.BLACK);
        
        T concept = filterableEntry.getObject();
        
        if(getMainPanel().getAuditDatabase().getLoadedAuditSet().isPresent()) {
            AuditSet<T> auditSet = getMainPanel().getAuditDatabase().getLoadedAuditSet().get();
            
            Optional<AuditResult<T>> optAuditResult = auditSet.getAuditResult(concept);
            
            if(optAuditResult.isPresent()) {
                AuditResult<T> auditResult = optAuditResult.get();
                
                if(auditResult.getState() == AuditResult.State.Correct) {
                    
                    Color color = new Color(6, 159, 50);
                    
                    this.getConceptRenderer().getConceptNameLabel().setForeground(color);
                    
                    auditStatusLabel.setForeground(color);
                    auditStatusLabel.setText("Correct");
                    
                } else if (auditResult.getState() == AuditResult.State.Error) {
                    this.getConceptRenderer().getConceptNameLabel().setForeground(Color.RED);
                    
                    auditStatusLabel.setForeground(Color.RED);
                    auditStatusLabel.setText(
                            String.format("Erroneous - %d error(s) reported", 
                                    auditResult.getErrors().size()));
                    
                } else {
                    
                    if(auditResult.getComment().isEmpty()) {
                        auditStatusLabel.setText("Unaudited");
                    } else {
                        Color color = new Color(73, 87, 213);
                        
                        this.getConceptRenderer().getConceptNameLabel().setForeground(color);

                        auditStatusLabel.setForeground(color);
                        auditStatusLabel.setText("Audit comment");
                    }
                }
                
            } else {
                auditStatusLabel.setText("Unaudited");
            }
        }
    }
}
