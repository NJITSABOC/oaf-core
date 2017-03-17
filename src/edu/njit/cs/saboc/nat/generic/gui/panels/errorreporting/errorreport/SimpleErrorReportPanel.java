package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport;

import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer.ErrorReportPanelInitializer;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.ErrorSubmissionPanel.ErrorSubmissionListener;
import java.awt.BorderLayout;
import java.util.Optional;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 * @param <T>
 * @param <V>
 */
public class SimpleErrorReportPanel<T extends Concept, V extends OntologyError<T>> extends ErrorReportPanel<T, V> {

    private Optional<ErrorReportPanelInitializer<V>> initializer = Optional.empty();
    
    private final ErrorDescriptionPanel<T, V> errorDescriptionPane;

    private final ErrorSeverityPanel<T> errorSeverityPanel;
    private final AuditCommentPanel<T> auditCommentPanel;
    
    private final ErrorSubmissionPanel<T> errorSubmissionPanel;

    public SimpleErrorReportPanel(
            NATBrowserPanel<T> mainPanel, 
            ConceptBrowserDataSource<T> dataSource) {
        
        super(mainPanel, dataSource);
        
        this.errorDescriptionPane = new ErrorDescriptionPanel<>(mainPanel, dataSource);
        
        this.errorSeverityPanel = new ErrorSeverityPanel<>(mainPanel, dataSource);
        
        this.auditCommentPanel = new AuditCommentPanel<>(mainPanel, dataSource);
        
        this.errorSubmissionPanel = new ErrorSubmissionPanel<>(
                mainPanel, 
                dataSource, 
                new ErrorSubmissionListener() {

                    @Override
                    public void resetClicked() {
                        reset();
                    }

                    @Override
                    public void submitClicked() {
                        
                    }
        });
        
        this.setLayout(new BorderLayout());
        
        
        this.add(errorDescriptionPane, BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(errorSeverityPanel, BorderLayout.NORTH);
        centerPanel.add(auditCommentPanel, BorderLayout.CENTER);
        
        this.add(centerPanel, BorderLayout.CENTER);
        
        this.add(errorSubmissionPanel, BorderLayout.SOUTH);
    }

    @Override
    public void reset() {
        if(initializer.isPresent()) {
            ErrorReportPanelInitializer<V> theInitializer = this.initializer.get();

            this.errorDescriptionPane.setDescription(theInitializer);
            this.errorSeverityPanel.setSeverity(theInitializer.getDefaultSeverity());
            this.auditCommentPanel.reset();
        }

    }

    @Override
    public boolean errorReady() {
        return false;
    }
    
    public void setInitializer(ErrorReportPanelInitializer<V> initializer) {
        this.initializer = Optional.of(initializer);
        
        reset();
    }
    
    public void clearInitializer() {
        this.initializer = Optional.empty();
    }
    
    @Override
    public V getError() {
        return initializer.get().generateError(
                auditCommentPanel.getComment(), 
                errorSeverityPanel.getSeverity());
    }

    @Override
    public Optional<? extends ErrorReportPanelInitializer> getInitializer() {
        return this.initializer;
    }
}
