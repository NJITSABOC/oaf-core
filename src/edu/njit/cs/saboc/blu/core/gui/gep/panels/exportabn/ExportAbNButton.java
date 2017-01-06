package edu.njit.cs.saboc.blu.core.gui.gep.panels.exportabn;

import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.Set;
import javax.swing.JButton;

/**
 *
 * @author Chris O
 */
public class ExportAbNButton extends JButton {
    
    private Optional<AbNConfiguration> optCurrentConfig;
        
    public ExportAbNButton() {
        super("Export");
                
        this.optCurrentConfig = Optional.empty();
        
        this.addActionListener( (ae) -> {
            doExport();
        });
    }
    
    public void initialize(AbNConfiguration config) {
        this.optCurrentConfig = Optional.of(config);
    }
    
    public void clear() {
        this.optCurrentConfig = Optional.empty();
    }
    
    protected Optional<AbNConfiguration> getCurrentConfiguration() {
        return optCurrentConfig;
    }
    
    protected void doExport() {
        
        if(!optCurrentConfig.isPresent()) {
            // TODO: error
            
            return;
        }
        
        AbNConfiguration config = optCurrentConfig.get();

        Optional<File> exportFile = ExportAbNUtilities.displayFileSelectDialog();

        if (exportFile.isPresent()) {
            doNodeExport(exportFile.get());
        }
    }
        
    protected void doNodeExport(File file) {
        
        AbNConfiguration config = optCurrentConfig.get();
        
        try (PrintWriter writer = new PrintWriter(file)) {
            
            Set<SinglyRootedNode> nodes = config.getAbstractionNetwork().getNodes();
            
            nodes.forEach((group) -> {
                String groupName = group.getName();
                
                Set<Concept> concepts = group.getConcepts();
                
                concepts.forEach( (concept) -> {
                    writer.println(String.format(
                            "%s\t%s\t%s",
                            groupName,
                            group.getRoot().getIDAsString(),
                            concept.getName(),
                            concept.getIDAsString()));
                });
            });
            
        } catch (IOException ie) {
            
        }
    }
}
