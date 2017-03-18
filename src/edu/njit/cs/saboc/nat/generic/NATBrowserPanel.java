package edu.njit.cs.saboc.nat.generic;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.errorreport.AuditDatabase;
import edu.njit.cs.saboc.nat.generic.gui.layout.NATLayout;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The main panel of the NAT. Also maintains a reference to the 
 * current data source, the focus concept manager for this instance
 * of the NAT, and the audit data base for the current instance of the NAT.
 * 
 * @author Chris O
 * 
 * @param <T>
 */
public class NATBrowserPanel<T extends Concept> extends JPanel {

    private final ConceptBrowserDataSource<T> dataSource;

    private final FocusConceptManager<T> focusConceptManager;
    
    private final JFrame parentFrame;
     
    private final NATLayout<T> layout;
    
    private final AuditDatabase<T> auditDatabase;
    
    public NATBrowserPanel(
            JFrame parentFrame, 
            ConceptBrowserDataSource<T> dataSource, 
            NATLayout layout) {
        
        this.setLayout(new BorderLayout());
               
        this.dataSource = dataSource;
        
        this.parentFrame = parentFrame;
        
        this.layout = layout;
        
        this.add(layout, BorderLayout.CENTER);    
        
        this.focusConceptManager = new FocusConceptManager<>(this, dataSource);
        
        this.auditDatabase = new AuditDatabase<>(this, dataSource);
        
        auditDatabase.addAuditDatabaseChangeListener( () -> {
            focusConceptManager.refresh();
        });
        
        layout.createLayout(this);
        
        this.revalidate();
        this.repaint();
    }
    
    public ConceptBrowserDataSource<T> getDataSource() {
        return dataSource;
    }
    
    public FocusConceptManager<T> getFocusConceptManager() {
        return focusConceptManager;
    }
    
    public AuditDatabase<T> getAuditDatabase() {
        return auditDatabase;
    }

    public void navigateTo(T c) {
        focusConceptManager.navigateTo(c);
    }

    public JFrame getParentFrame() {
        return parentFrame;
    }
    
    public NATLayout<T> getNATLayout() {
        return layout;
    }
}
