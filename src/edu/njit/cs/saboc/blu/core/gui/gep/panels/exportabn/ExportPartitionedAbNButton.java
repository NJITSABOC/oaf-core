package edu.njit.cs.saboc.blu.core.gui.gep.panels.exportabn;

import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author cro3
 */
public class ExportPartitionedAbNButton extends JButton {
    
    private final PartitionedAbNConfiguration config;
    private final PartitionedAbstractionNetwork abn;
    public ExportPartitionedAbNButton(
            PartitionedAbstractionNetwork abn, 
            PartitionedAbNConfiguration config) {
        
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
            String fullChoice = String.format("Full Export (Hierarchy, Level, %s, %s, %s, %s Unique Identifiers)", 
                    config.getTextConfiguration().getContainerTypeName(true), 
                    config.getTextConfiguration().getNodeTypeName(true), 
                    config.getTextConfiguration().getConceptTypeName(true),
                    config.getTextConfiguration().getConceptTypeName(false));
            
            String containerChoice = String.format("%s and %s Only", 
                    config.getTextConfiguration().getContainerTypeName(true), 
                    config.getTextConfiguration().getConceptTypeName(true));
            
            String groupChoice = String.format("%s and %s Only",
                    config.getTextConfiguration().getNodeTypeName(true),
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
            
            Set<PartitionedNode> containers = abn.getBaseAbstractionNetwork().getNodes();
            
            containers.forEach((container) -> {
                Set<SinglyRootedNode> groups = container.getInternalNodes();
                
                int containerLevel = config.getPartitionedNodeLevel(container);
                
                String containerName = container.getName();
                
                groups.forEach( (group) -> {
                    String groupName = String.format("%s (%d)", group.getName(), group.getConceptCount());
                    
                    Set<Concept> concepts = group.getConcepts();
                    
                    concepts.forEach( (concept) -> {
                        String conceptName = concept.getName();
                        
                        writer.println(String.format("%d\t%s\t%s\t%s\t%s\t%s",
                                containerLevel, 
                                containerName, 
                                groupName, 
                                group.getRoot().getIDAsString(),
                                conceptName,
                                concept.getIDAsString()));
                    });
                });
            });
            
        } catch(IOException ie) {
            
        }
    }
    
    private void doContainerExport(File file) {
        try (PrintWriter writer = new PrintWriter(file)) {

            Set<PartitionedNode> containers = abn.getBaseAbstractionNetwork().getNodes();

            containers.forEach((container) -> {
                Set<SinglyRootedNode> groups = container.getInternalNodes();

                Set<Concept> concepts = new HashSet<>();
                groups.forEach( (group) -> {
                    concepts.addAll(group.getConcepts());
                });
                
                String containerName = container.getName();

               
                concepts.forEach( (concept) -> {
                    writer.println(String.format("%s\t%s\t%s",
                            containerName, 
                            concept.getName(),
                            concept.getIDAsString())
                    );
                });
            });
        } catch (IOException ie) {
            
        }
    }
    
    private void doGroupExport(File file) {
        try (PrintWriter writer = new PrintWriter(file)) {
            Set<SinglyRootedNode> groups = abn.getNodes();
            
            groups.forEach((group) -> {
                String groupName = group.getName();
                
                Set<Concept> concepts = group.getConcepts();
                
                concepts.forEach( (concept) -> {
                    writer.println(String.format(
                            "%s\t%s\t%s",
                            groupName,
                            concept.getName(),
                            concept.getIDAsString()));
                });
            });
        } catch (IOException ie) {
            
        }
    }
}
