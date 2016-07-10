package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons;

import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.exportabn.ExportAbNUtilities;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.Set;
import javax.swing.JOptionPane;

/**
 *
 * @author Chris O
 */
public class ExportPartitionedNodeButton extends ExportButton {

    private static final String getToolTipStr(PartitionedAbNConfiguration config) {
        return String.format("Export %s's %s.",
                config.getTextConfiguration().getContainerTypeName(false),
                config.getTextConfiguration().getConceptTypeName(true));
    }
    
    private final PartitionedAbNConfiguration config;
    
    public ExportPartitionedNodeButton(PartitionedAbNConfiguration config) {
        super(ExportPartitionedNodeButton.getToolTipStr(config));
        
        this.config = config;
    }
    
    @Override
    public void exportAction() {
        Optional<File> exportFile = ExportAbNUtilities.displayFileSelectDialog();
        
        if (exportFile.isPresent()) {
            
            String firstChoice = String.format("%s ID and Name", 
                    config.getTextConfiguration().getConceptTypeName(false));
            
            String secondChoice = String.format("%s ID, %s Name, and %s", 
                    config.getTextConfiguration().getConceptTypeName(false),
                    config.getTextConfiguration().getConceptTypeName(false),
                    config.getTextConfiguration().getNodeTypeName(false));
            
            
            String[] choices = {firstChoice, secondChoice};
            
            String description = String.format("Select %s Export Option", config.getTextConfiguration().getContainerTypeName(false));
            
            String input = (String) JOptionPane.showInputDialog(null, 
                    description,
                    "Choose an Export Option",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    choices,
                    choices[0]);
            
            if (input.equals(choices[0])) {
                exportPartitionedNodeConcepts(exportFile.get());
            } else if (input.equals(choices[1])) {
                exportInternalNodes(exportFile.get());
            }
        }
    }
    
    private void exportPartitionedNodeConcepts(File file) {
        PartitionedNode node = (PartitionedNode)super.getCurrentNode().get();

        try (PrintWriter writer = new PrintWriter(file)) {
            node.getConcepts().forEach((c) -> {
                writer.println(String.format("%d\t%s", c.getID(), c.getName()));
            });
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
            
            // TODO: Show error message
        }
    }
    
    private void exportInternalNodes(File file) {
        PartitionedNode node = (PartitionedNode)super.getCurrentNode().get();
        
        Set<SinglyRootedNode> internalNodes = node.getInternalNodes();
        
        try (PrintWriter writer = new PrintWriter(file)) {
            internalNodes.forEach( (internalNode) -> {
                internalNode.getConcepts().forEach( (c) -> {
                    writer.println(String.format("%d\t%s\t%s",
                            c.getID(),
                            c.getName(),
                            String.format("%s (%d)", internalNode.getName(), internalNode.getConceptCount())));
                });
            });

        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();

            // TODO: Show error message
        }
    }
}
