package edu.njit.cs.saboc.blu.core.gui.gep.panels.exportabn;

import SnomedShared.generic.GenericConceptGroup;
import SnomedShared.generic.GenericGroupContainer;
import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUPartitionedConfiguration;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author cro3
 */
public class GenericExportPartitionedAbNButton<CONCEPT_T, 
        GROUP_T extends GenericConceptGroup, 
        CONTAINER_T extends GenericGroupContainer> extends JButton {
    
    private final BLUPartitionedConfiguration config;
    private final PartitionedAbstractionNetwork abn;
    
    public GenericExportPartitionedAbNButton(PartitionedAbstractionNetwork abn, BLUPartitionedConfiguration config) {
        super("Export");
        
        this.abn = abn;
        this.config = config;
        
        this.addActionListener( (ActionEvent ae) -> {
            doExport();
        });
    }
    
    private void doExport() {
        Optional<File> exportFile = ExportAbNUtilities.displayFileSelectDialog();

        if (exportFile.isPresent()) {
            String fullChoice = String.format("Full Export (Hierarchy, Level, %s, %s, %s)", 
                    config.getTextConfiguration().getContainerTypeName(true), 
                    config.getTextConfiguration().getGroupTypeName(true), 
                    config.getTextConfiguration().getConceptTypeName(true));
            
            String containerChoice = String.format("%s and %s Only", 
                    config.getTextConfiguration().getContainerTypeName(true), 
                    config.getTextConfiguration().getConceptTypeName(true));
            
            String groupChoice = String.format("%s and %s Only",
                    config.getTextConfiguration().getGroupTypeName(true),
                    config.getTextConfiguration().getConceptTypeName(true));

            String[] choices = {fullChoice, containerChoice, groupChoice};

            String input = (String) JOptionPane.showInputDialog(null, "Select Export Option",
                    "Choose an Export Option",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    choices,
                    choices[0]);

            if (input.equals(choices[0])) {
                doFullExport(exportFile.get());
            } else if (input.equals(choices[1])) {
                doContainerExport(exportFile.get());
            } else if(input.equals(choices[2])) {
                doGroupExport(exportFile.get());
            } else {
                
            }
        }
    }
    
    private void doFullExport(File file) {
        try (PrintWriter writer = new PrintWriter(file)) {
            
            ArrayList<CONTAINER_T> containers = abn.getContainers();
            
            String hierarchyName = config.getTextConfiguration().getGroupName(abn.getRootGroup());
            
            containers.forEach((CONTAINER_T container) -> {
                HashSet<GROUP_T> groups = config.getDataConfiguration().getContainerGroupSet(container);
                
                int containerLevel = config.getDataConfiguration().getContainerLevel(container);
                String containerName = config.getTextConfiguration().getContainerName(container);
                
                groups.forEach( (GROUP_T group) -> {
                    String groupName = config.getTextConfiguration().getGroupName(group);
                    
                    HashSet<CONCEPT_T> concepts = config.getDataConfiguration().getGroupConceptSet(group);
                    
                    concepts.forEach( (CONCEPT_T concept) -> {
                        String conceptName = config.getTextConfiguration().getConceptName(concept);
                        
                        writer.println(String.format("%s\t%d\t%s\t%s\t%s",
                                hierarchyName, 
                                containerLevel, 
                                containerName, 
                                groupName, 
                                conceptName));
                        
                    });
                });
            });
            
        } catch(IOException ie) {
            
        }
    }
    
    private void doContainerExport(File file) {
        try (PrintWriter writer = new PrintWriter(file)) {

            ArrayList<CONTAINER_T> containers = abn.getContainers();

            containers.forEach((CONTAINER_T container) -> {
                HashSet<GROUP_T> groups = config.getDataConfiguration().getContainerGroupSet(container);

                HashSet<CONCEPT_T> concepts = new HashSet<>();
                groups.forEach( (GROUP_T group) -> {
                    concepts.addAll(config.getDataConfiguration().getGroupConceptSet(group));
                });
                
                String containerName = config.getTextConfiguration().getContainerName(container);

               
                concepts.forEach( (CONCEPT_T concept) -> {
                    writer.println(String.format("%s\t%s",
                            containerName, 
                            config.getTextConfiguration().getConceptName(concept))
                    );
                });
            });
        } catch (IOException ie) {
            
        }
    }
    
    private void doGroupExport(File file) {
        try (PrintWriter writer = new PrintWriter(file)) {
            HashMap<Integer, GROUP_T> groups = abn.getGroups();
            
            groups.values().forEach((GROUP_T group) -> {
                String groupName = config.getTextConfiguration().getGroupName(group);
                
                HashSet<CONCEPT_T> concepts = config.getDataConfiguration().getGroupConceptSet(group);
                
                concepts.forEach( (CONCEPT_T concept) -> {
                    writer.println(String.format(
                            "%s\t%s",
                            groupName,
                            config.getTextConfiguration().getConceptName(concept)));
                });
            });
        } catch (IOException ie) {
            
        }
    }
}
