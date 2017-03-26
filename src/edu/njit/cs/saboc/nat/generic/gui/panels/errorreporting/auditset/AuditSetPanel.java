package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.auditset;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.exportabn.ExportAbNUtilities;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.errorreport.AuditSet;
import edu.njit.cs.saboc.nat.generic.errorreport.AuditSetLoader;
import edu.njit.cs.saboc.nat.generic.errorreport.AuditSetLoaderException;
import edu.njit.cs.saboc.nat.generic.gui.panels.BaseNATPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Optional;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class AuditSetPanel<T extends Concept> extends BaseNATPanel<T> {
    
    private final AuditConceptList<T> auditConceptList;
    
    private final JLabel nameLabel;
    private final JLabel lastSavedDateLabel;
    
    private final JButton btnCreateFromConceptList;
    private final JButton btnExportAuditSet;
    
    private final JButton btnOpenAuditSet;
    
    public AuditSetPanel(NATBrowserPanel<T> browserPanel, ConceptBrowserDataSource<T> dataSource) {
        super(browserPanel, dataSource);
        
        this.setLayout(new BorderLayout());
        
        JPanel managementPanel = new JPanel();
        
        this.btnCreateFromConceptList = new JButton("Create");
        this.btnCreateFromConceptList.addActionListener( (ae) -> {
            
            JPopupMenu testMenu = new JPopupMenu();
            
            JMenuItem emptyOption = new JMenuItem("New empty audit set");
            emptyOption.setFont(emptyOption.getFont().deriveFont(14.0f));
            emptyOption.addActionListener( (actionEvent) -> {
                createEmptyAuditSet();
            });
            
            testMenu.add(emptyOption);
            
            JMenuItem fileOption = new JMenuItem("From Concept ID file");
            fileOption.setFont(fileOption.getFont().deriveFont(14.0f));
            fileOption.addActionListener( (actionEvent) -> {
                
            });
            
            testMenu.add(fileOption);
            
            testMenu.show(managementPanel, btnCreateFromConceptList.getX() + 10, btnCreateFromConceptList.getY() + 10);
        });
        
        this.btnOpenAuditSet = new JButton("Open");
        this.btnOpenAuditSet.addActionListener( (ae) -> {
            openAuditSet();
        });
        
        this.btnExportAuditSet = new JButton("Save As");
        this.btnExportAuditSet.addActionListener( (ae) -> {
            exportAuditSet();
        });
        
        managementPanel.add(btnCreateFromConceptList);
        managementPanel.add(btnOpenAuditSet);
        managementPanel.add(btnExportAuditSet);
        
        
        this.nameLabel = new JLabel();
        this.lastSavedDateLabel = new JLabel();
        
        
        JPanel namePanel = new JPanel();
        namePanel.add(new JLabel("Audit Set Name: "));
        namePanel.add(nameLabel);
        
        JPanel lastSavedPanel = new JPanel();
        lastSavedPanel.add(new JLabel("Last saved: "));
        lastSavedPanel.add(lastSavedDateLabel);
        
        JPanel detailsPanel = new JPanel(new GridLayout(2, 1));
        
        detailsPanel.add(namePanel);
        detailsPanel.add(lastSavedPanel);
        
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(managementPanel, BorderLayout.NORTH);
        northPanel.add(detailsPanel, BorderLayout.CENTER);
                
        this.add(northPanel, BorderLayout.NORTH);
                
        this.auditConceptList = new AuditConceptList<>(browserPanel, dataSource);
        
        this.add(auditConceptList, BorderLayout.CENTER);
    }
    
    public void displayDetails(AuditSet<T> auditSet) {
        this.nameLabel.setText(auditSet.getName());
        
        SimpleDateFormat dateFormatter = new SimpleDateFormat();
        
        if(auditSet.getFile().isPresent()) {
            this.lastSavedDateLabel.setText(dateFormatter.format(auditSet.getLastSavedDate()));
        } else {
            this.lastSavedDateLabel.setText("[not yet saved to file]");
        }
    }
    

    private void setCurrentAuditSet(AuditSet<T> auditSet) {
        getMainPanel().getAuditDatabase().setAuditSet(auditSet);

        this.auditConceptList.reloadAuditSet();

        displayDetails(auditSet);
    }
    
    private void createEmptyAuditSet() {
        Optional<String> auditSetName = promptForAuditSetName();
        
        if(auditSetName.isPresent()) {
            AuditSet<T> auditSet = new AuditSet<>(getDataSource(), auditSetName.get(), new HashSet<>());
            
            setCurrentAuditSet(auditSet);
        }
    }
    
    
    private void createAuditSetFromFile() {
        Optional<File> idFile = ExportAbNUtilities.displayFileSelectDialog();
        
        if(idFile.isPresent()) {
            try {
                AuditSet<T> auditSet = AuditSetLoader.<T>createAuditSetFromConceptIds(idFile.get(), getDataSource());
                setCurrentAuditSet(auditSet);
            } catch (AuditSetLoaderException asle) {
 
            }
        }
    }
    
    private void openAuditSet() {
        Optional<File> idFile = ExportAbNUtilities.displayFileSelectDialog();

        if (idFile.isPresent()) {
            
            try {
                AuditSet<T> auditSet = AuditSetLoader.<T>createAuditSetFromJSON(idFile.get(), getDataSource());
                setCurrentAuditSet(auditSet);
            } catch (AuditSetLoaderException asle) {
                asle.printStackTrace();
            }
        }
    }
    
    private void exportAuditSet() {
        Optional<File> auditSetFile = ExportAbNUtilities.displayFileSelectDialog();

        if(auditSetFile.isPresent()) {
            if(getMainPanel().getAuditDatabase().getLoadedAuditSet().isPresent()) {
                AuditSet<T> auditSet = getMainPanel().getAuditDatabase().getLoadedAuditSet().get();
                
                boolean success = auditSet.saveToFile(auditSetFile.get());
                
                if(success) {
                    displayDetails(auditSet);
                } else {
                    JOptionPane.showMessageDialog(getMainPanel().getParentFrame(), 
                            "<html>An error occured when saving the Audit Set."
                                    + "<p>Click \"Save As\" and create a new file to save your work.", 
                            "Error Saving Audit Set", 
                            JOptionPane.ERROR_MESSAGE);
                }
                
                
            }
        }
    }
    
    
    private Optional<String> promptForAuditSetName() {
        
        String auditSetName = "";
        
        while (auditSetName != null && auditSetName.isEmpty()) {

            auditSetName = JOptionPane.showInputDialog(
                    getMainPanel().getParentFrame(),
                    "Enter the name of the audit set (required)",
                    "Enter Audit Set Name",
                    JOptionPane.QUESTION_MESSAGE);
        }
        
        return Optional.ofNullable(auditSetName);
    }
    
}
