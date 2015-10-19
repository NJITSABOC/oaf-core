package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractEntityList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.exportabn.ExportAbNUtilities;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.entry.ImportedConceptGroupReport;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.models.GenericConceptImportReportTableModel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public abstract class AbstractAbNConceptLocationReportPanel<ABN_T extends AbstractionNetwork, 
        GROUP_T extends GenericConceptGroup, CONCEPT_T> extends AbNReportPanel<CONCEPT_T, GROUP_T, ABN_T> {
    
    private final AbstractEntityList<ImportedConceptGroupReport<GROUP_T, CONCEPT_T>> conceptReportList; 
    
    public AbstractAbNConceptLocationReportPanel(BLUConfiguration config) {
        super(config);
        
        super.setLayout(new BorderLayout());
        
        this.conceptReportList = new AbstractEntityList<ImportedConceptGroupReport<GROUP_T, CONCEPT_T>>(
                new GenericConceptImportReportTableModel<>(config)) {

            @Override
            protected String getBorderText(Optional<ArrayList<ImportedConceptGroupReport<GROUP_T, CONCEPT_T>>> entities) {
                String base = String.format("Imported %s' %s", config.getTextConfiguration().getConceptTypeName(true), 
                        config.getTextConfiguration().getGroupTypeName(true));
                
                if(entities.isPresent()) {
                    return String.format("%s (%d %s found)", base, 
                            entities.get().size(),
                            config.getTextConfiguration().getConceptTypeName(entities.get().size() != 1).toLowerCase());
                } else {
                    return base;
                }
            }
        };
        
        JPanel loadPanel = new JPanel();
        
        JLabel loadLabel = new JLabel(String.format("<html>Select a file that contains %s IDs to find where the %s are summarized in the %s.", 
                config.getTextConfiguration().getConceptTypeName(false).toLowerCase(),
                config.getTextConfiguration().getConceptTypeName(true).toLowerCase(),
                config.getTextConfiguration().getAbNTypeName(false)));
        
        loadPanel.add(loadLabel);
        loadPanel.add(Box.createHorizontalStrut(4));
        
        JButton loadBtn = new JButton(String.format("Load %s IDs from File", config.getTextConfiguration().getConceptTypeName(true)));
        loadBtn.addActionListener( (ActionEvent ae) -> {
            ArrayList<String> loadedIds = loadConceptIdentifiers();
            
            if(!loadedIds.isEmpty()) {
                ArrayList<ImportedConceptGroupReport<GROUP_T, CONCEPT_T>> report = getConceptGroups(loadedIds);
                conceptReportList.setContents(report);
            }
        });
        
        loadPanel.add(loadBtn);
        
        this.add(loadPanel, BorderLayout.NORTH);
        this.add(conceptReportList, BorderLayout.SOUTH);
    }
    
    private ArrayList<String> loadConceptIdentifiers() {
        Optional<File> idFile = ExportAbNUtilities.displayFileSelectDialog();
        
        if(idFile.isPresent()) {

            try(Scanner scanner = new Scanner(idFile.get())) {
                ArrayList<String> conceptIds = new ArrayList<>();
                
                while(scanner.hasNext()) {
                    String [] line = scanner.nextLine().split("\t");
                    
                    conceptIds.add(line[0].trim().toLowerCase());
                }
                
                return conceptIds;
                
            } catch(FileNotFoundException fnfe) {
                
            }
        }
        
        return new ArrayList<>();
    }
    
    protected abstract ArrayList<ImportedConceptGroupReport<GROUP_T, CONCEPT_T>> getConceptGroups(ArrayList<String> conceptIds);
    
    public void displayAbNReport(ABN_T abn) {
        
    }
}
