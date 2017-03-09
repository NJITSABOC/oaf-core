package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError.Severity;
import edu.njit.cs.saboc.nat.generic.gui.panels.BaseNATPanel;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class ErrorSeverityPanel<T extends Concept> extends BaseNATPanel<T> {
    
    private final JRadioButton btnNonCritical;
    private final JRadioButton btnModerate;
    private final JRadioButton btnSevere;
    
    private Severity selectedSeverity = Severity.NonCritical;
    
    public ErrorSeverityPanel(NATBrowserPanel<T> mainPanel, 
            ConceptBrowserDataSource<T> dataSource) {
        
        super(mainPanel, dataSource);
        
        btnNonCritical = new JRadioButton("Non-critical");
        btnModerate = new JRadioButton("Moderate");
        btnSevere = new JRadioButton("Severe");
        
        btnNonCritical.addActionListener( (ae) -> {
            selectedSeverity = Severity.NonCritical;
        });
        
        btnModerate.addActionListener( (ae) -> {
            selectedSeverity = Severity.Moderate;
        });
        
        btnSevere.addActionListener( (ae) -> {
            selectedSeverity = Severity.Severe;
        });
        
        ButtonGroup group = new ButtonGroup();
        
        group.add(btnNonCritical);
        group.add(btnModerate);
        group.add(btnSevere);
        
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        this.add(btnNonCritical);
        this.add(btnModerate);
        this.add(btnSevere);
        
        this.btnNonCritical.setSelected(true);
        
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Select Error Severity"));
        
    }
    
    public void reset() {
        this.btnNonCritical.setSelected(true);
    }
    
    public void setSeverity(Severity severity) {
        if(severity == Severity.NonCritical) {
            this.btnNonCritical.setSelected(true);
        } else if(severity == Severity.Moderate) {
            this.btnModerate.setSelected(true);
        } else {
            this.btnSevere.setSelected(true);
        }
    }
    
    public Severity getSeverity() {
        return selectedSeverity;
    }

    @Override
    protected void setFontSize(int fontSize) {
        
    }
}
