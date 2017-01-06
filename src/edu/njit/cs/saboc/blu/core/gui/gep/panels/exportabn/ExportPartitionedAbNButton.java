package edu.njit.cs.saboc.blu.core.gui.gep.panels.exportabn;

import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.swing.JOptionPane;

/**
 *
 * @author cro3
 */
public class ExportPartitionedAbNButton extends ExportAbNButton {
    
    public ExportPartitionedAbNButton() {

    }

    protected void doExport() {
        
        if(!super.getCurrentConfiguration().isPresent()) {
            // TODO: error
            
            return;
        }
        
        PartitionedAbNConfiguration config = (PartitionedAbNConfiguration)super.getCurrentConfiguration().get();

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
                doPartitionNodeExport(exportFile.get());
            } else if(input.equals(choices[2])) {
                super.doNodeExport(exportFile.get());
            } else {
                
            }
        }
    }
    
    private void doFullExport(File file) {
        PartitionedAbNConfiguration config = (PartitionedAbNConfiguration)super.getCurrentConfiguration().get();
        
        try (PrintWriter writer = new PrintWriter(file)) {
            
            Set<PartitionedNode> containers = config.getAbstractionNetwork().getBaseAbstractionNetwork().getNodes();
            
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
    
    private void doPartitionNodeExport(File file) {
        PartitionedAbNConfiguration config = (PartitionedAbNConfiguration)super.getCurrentConfiguration().get();
        
        try (PrintWriter writer = new PrintWriter(file)) {

            Set<PartitionedNode> containers = config.getAbstractionNetwork().getBaseAbstractionNetwork().getNodes();

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
}
