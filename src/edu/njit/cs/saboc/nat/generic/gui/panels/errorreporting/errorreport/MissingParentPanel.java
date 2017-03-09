package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.errorreport.error.MissingParentError;
import edu.njit.cs.saboc.nat.generic.gui.panels.search.SearchPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Optional;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class MissingParentPanel<T extends Concept> extends ErrorReportPanel<T, MissingParentError> {
    
    private final JRadioButton btnSearchForMissingParent;
    private final JRadioButton btnCommentOnly;
    
    private final JButton btnClearSelection;
    private final JLabel selectedMissingParentLabel;
    
    private final ErrorSeverityPanel<T> errorSeverityPanel;
    
    private final SearchPanel<T> missingParentSearchPanel;
    
    private final AuditCommentPanel<T> commentPanel;
    
    private Optional<T> missingParent = Optional.empty();
    
    private final JPanel searchForParentPanel;
    
    public MissingParentPanel(
            NATBrowserPanel<T> mainPanel, 
            ConceptBrowserDataSource<T> dataSource) {
        
        super(mainPanel, dataSource);
        
        this.setLayout(new BorderLayout());

        this.btnSearchForMissingParent = new JRadioButton("Search for Missing Parent");
        this.btnCommentOnly = new JRadioButton("Comment Only");
        
        ButtonGroup group = new ButtonGroup();
        group.add(btnSearchForMissingParent);
        group.add(btnCommentOnly);

        
            
        JPanel optionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        optionPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Error Report Type"));
        
        optionPanel.add(btnSearchForMissingParent);
        optionPanel.add(btnCommentOnly);
        
        this.add(optionPanel, BorderLayout.NORTH);
        
        this.btnClearSelection = new JButton("Clear Selection");
        this.btnClearSelection.addActionListener( (ae) -> {
            clearSelectedMissingParent();
        });
        
        
        JPanel missingParentNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        missingParentNamePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.RED), 
                "Selected Missing Parent Concept"));
        missingParentNamePanel.setOpaque(false);

        this.selectedMissingParentLabel = new JLabel();
        this.selectedMissingParentLabel.setForeground(Color.BLUE);
        this.selectedMissingParentLabel.setOpaque(false);
        this.selectedMissingParentLabel.setFont(this.selectedMissingParentLabel.getFont().deriveFont(14.0f));
        
        missingParentNamePanel.add(selectedMissingParentLabel);
        
        JPanel errorDetailsPanel = new JPanel(new BorderLayout());
        errorDetailsPanel.setOpaque(false);
        
        JPanel clearSelectionPanel = new JPanel();
        clearSelectionPanel.add(btnClearSelection);
        
        errorDetailsPanel.add(missingParentNamePanel, BorderLayout.CENTER);
        errorDetailsPanel.add(clearSelectionPanel, BorderLayout.EAST);
        
        this.commentPanel = new AuditCommentPanel<>(mainPanel, dataSource);
        this.commentPanel.setPreferredSize(new Dimension(-1, 200));
        
        this.errorSeverityPanel = new ErrorSeverityPanel<>(mainPanel, dataSource);
        

        this.missingParentSearchPanel = new SearchPanel<>(mainPanel, dataSource);
        this.missingParentSearchPanel.setBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), 
                        "Search for Missing Parent"));
        
        
        this.missingParentSearchPanel.addSearchResultSelectedListener( (result) -> {
            setSelectedMissingParent(result.getConcept());
        });
        
        
        this.searchForParentPanel = new JPanel(new BorderLayout());
        this.searchForParentPanel.add(errorDetailsPanel, BorderLayout.NORTH);
        this.searchForParentPanel.add(missingParentSearchPanel, BorderLayout.CENTER);
        
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        
        centerPanel.add(errorSeverityPanel);
        centerPanel.add(searchForParentPanel);
        centerPanel.add(commentPanel);
        
        this.add(centerPanel, BorderLayout.CENTER);
        
        
        btnSearchForMissingParent.setSelected(true);
        
        btnSearchForMissingParent.addActionListener( (ae) -> {
            this.searchForParentPanel.setVisible(true);
            
            this.revalidate();
        });
        
        btnCommentOnly.addActionListener( (ae) ->{
            this.searchForParentPanel.setVisible(false);
            
            this.revalidate();
        });
        
        clearSelectedMissingParent();
    }

    private void setSelectedMissingParent(T concept) {
        this.selectedMissingParentLabel.setText(concept.getName());
        this.missingParent = Optional.of(concept);
    }
    
    private void clearSelectedMissingParent() {
        this.selectedMissingParentLabel.setText("(not selected)");
        this.missingParent = Optional.empty();
    }
    
    @Override
    public MissingParentError getError() {
        return null;
    }

    @Override
    public void displayError(MissingParentError error) {
        
    }
    
    @Override
    public void reset() {
        clearSelectedMissingParent();
        
        this.missingParent = Optional.empty();
        this.commentPanel.reset();
    }

    @Override
    public boolean errorReady() {
        return !commentPanel.getComment().isEmpty() || missingParent.isPresent();
    }
}
