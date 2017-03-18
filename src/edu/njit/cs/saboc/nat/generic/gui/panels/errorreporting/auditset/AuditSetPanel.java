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
import java.io.File;
import java.util.Optional;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class AuditSetPanel<T extends Concept> extends BaseNATPanel<T> {
    
    private final AuditConceptList<T> auditConceptList;
    
    private final JButton btnCreateFromConceptList;
    private final JButton btnExportAuditSet;
    
    private final JButton btnOpenAuditSet;
    
    public AuditSetPanel(NATBrowserPanel<T> browserPanel, ConceptBrowserDataSource<T> dataSource) {
        super(browserPanel, dataSource);
        
        this.setLayout(new BorderLayout());
        
        JPanel northPanel = new JPanel();
        
        this.btnCreateFromConceptList = new JButton("Create from File");
        this.btnCreateFromConceptList.addActionListener( (ae) -> {
            createAuditSetFromFile();
        });
        
        this.btnOpenAuditSet = new JButton("Open");
        this.btnOpenAuditSet.addActionListener( (ae) -> {
            openAuditSet();
        });
        
        this.btnExportAuditSet = new JButton("Save As");
        this.btnExportAuditSet.addActionListener( (ae) -> {
            exportAuditSet();
        });
        
        northPanel.add(btnCreateFromConceptList);
        northPanel.add(btnOpenAuditSet);
        northPanel.add(btnExportAuditSet);
        
        this.add(northPanel, BorderLayout.NORTH);
                
        this.auditConceptList = new AuditConceptList<>(browserPanel, dataSource);
        
        this.add(auditConceptList, BorderLayout.CENTER);
    }
    
    private void createAuditSetFromFile() {
        Optional<File> idFile = ExportAbNUtilities.displayFileSelectDialog();
        
        if(idFile.isPresent()) {
            try {
                
                AuditSet<T> auditSet = AuditSetLoader.<T>createAuditSetFromConceptIds(idFile.get(), getDataSource());
                
                getMainPanel().getAuditDatabase().setAuditSet(auditSet);
                
                this.auditConceptList.reloadAuditSet();
                
            } catch (AuditSetLoaderException asle) {
                
            }
        }
    }
    
    private void openAuditSet() {
        Optional<File> idFile = ExportAbNUtilities.displayFileSelectDialog();

        if (idFile.isPresent()) {
            
            try {
                AuditSet<T> auditSet = AuditSetLoader.<T>createAuditSetFromJSON(idFile.get(), getDataSource());

                getMainPanel().getAuditDatabase().setAuditSet(auditSet);

                this.auditConceptList.reloadAuditSet();

            } catch (AuditSetLoaderException asle) {

            }
        }
    }
    
    private void exportAuditSet() {
        Optional<File> auditSetFile = ExportAbNUtilities.displayFileSelectDialog();

        if(auditSetFile.isPresent()) {
            if(getMainPanel().getAuditDatabase().getLoadedAuditSet().isPresent()) {
                AuditSet<T> auditSet = getMainPanel().getAuditDatabase().getLoadedAuditSet().get();
                
                auditSet.saveToFile(auditSetFile.get());
            }
        }
    }
}
