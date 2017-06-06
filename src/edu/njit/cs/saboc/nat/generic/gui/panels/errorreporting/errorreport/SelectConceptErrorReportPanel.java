package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.ErrorSubmissionPanel.ErrorSubmissionListener;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer.ErrorReportPanelInitializer;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer.MissingConceptInitializer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Optional;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 * @param <V>
 */
public class SelectConceptErrorReportPanel<T extends Concept, V extends OntologyError<T>> extends ErrorReportPanel<T, V> {
    
    private Optional<MissingConceptInitializer<T, V>> initializer = Optional.empty();
    
    private final JRadioButton btnSearchForMissingParent;
    private final JRadioButton btnCommentOnly;

    private final ErrorDescriptionPanel<T, V> errorDescriptionPanel;
    
    private final ErrorSeverityPanel<T> errorSeverityPanel;

    private final SelectConceptPanel<T> selectConceptPanel;
    
    private final AuditCommentPanel<T> commentPanel;
    
    private final ErrorSubmissionPanel<T> errorSubmissionPanel;
        
    public SelectConceptErrorReportPanel(NATBrowserPanel<T> mainPanel) {
        
        super(mainPanel);
        
        this.setLayout(new BorderLayout());

        this.btnSearchForMissingParent = new JRadioButton("Search for Concept");
        this.btnCommentOnly = new JRadioButton("Comment Only");
        
        ButtonGroup group = new ButtonGroup();
        group.add(btnSearchForMissingParent);
        group.add(btnCommentOnly);
            
        JPanel optionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        optionPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Report Type"));
        
        optionPanel.add(btnSearchForMissingParent);
        optionPanel.add(btnCommentOnly);
        
        this.errorDescriptionPanel = new ErrorDescriptionPanel<>(mainPanel);
        
        this.errorSeverityPanel = new ErrorSeverityPanel<>(mainPanel);
        
        this.commentPanel = new AuditCommentPanel<>(mainPanel);
        this.commentPanel.setPreferredSize(new Dimension(-1, 200));
                
        this.selectConceptPanel = new SelectConceptPanel<>(mainPanel);
        
        this.errorSubmissionPanel = new ErrorSubmissionPanel<>(
                mainPanel,
                new ErrorSubmissionListener() {

                    @Override
                    public void resetClicked() {
                        reset();
                    }

                    @Override
                    public void submitClicked() {
                        submitError();
                    }
                });

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(errorDescriptionPanel, BorderLayout.NORTH);
        northPanel.add(optionPanel, BorderLayout.CENTER);
        
        this.add(northPanel, BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        
        centerPanel.add(errorSeverityPanel);
        centerPanel.add(selectConceptPanel);
        centerPanel.add(commentPanel);
            
        this.add(centerPanel, BorderLayout.CENTER);
        
        this.add(errorSubmissionPanel, BorderLayout.SOUTH);
        
        btnSearchForMissingParent.setSelected(true);
        
        btnSearchForMissingParent.addActionListener( (ae) -> {
            this.selectConceptPanel.setVisible(true);
            
            this.selectConceptPanel.clearSelectedConcept();
            
            this.revalidate();
        });
        
        btnCommentOnly.addActionListener( (ae) ->{
            this.selectConceptPanel.setVisible(false);
            
            this.selectConceptPanel.clearSelectedConcept();
            
            this.revalidate();
        });
    }
    
    public void setInitializer(MissingConceptInitializer<T, V> initializer) {
        this.initializer = Optional.of(initializer);
        
        reset();
    }
    
    public void clearInitializer() {
        this.initializer = Optional.empty();
    }

    @Override
    public V getError() {
        
        MissingConceptInitializer<T, V> theInitializer = this.initializer.get();
        
        if(this.selectConceptPanel.getSelectedConcept().isPresent()) {
            return theInitializer.generateError(
                    commentPanel.getComment(),
                    errorSeverityPanel.getSeverity(), 
                    selectConceptPanel.getSelectedConcept().get());
        } else {
            return theInitializer.generateError(commentPanel.getComment(), errorSeverityPanel.getSeverity());
        }
    }

    @Override
    public void reset() {
        if(initializer.isPresent()) {
            this.errorDescriptionPanel.setDescription(initializer.get());
            this.errorSeverityPanel.setSeverity(initializer.get().getDefaultSeverity());
            
            this.selectConceptPanel.clearSelectedConcept();
            this.commentPanel.reset();
        }
    }

    @Override
    public boolean errorReady() {
        return true;
    }

    @Override
    public Optional<? extends ErrorReportPanelInitializer<T, V>> getInitializer() {
        return this.initializer;
    }
}
