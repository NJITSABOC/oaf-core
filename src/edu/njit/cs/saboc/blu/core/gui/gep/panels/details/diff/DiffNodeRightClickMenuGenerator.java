package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.change.AbNChange;
import edu.njit.cs.saboc.blu.core.abn.diff.change.concepts.ConceptAddedToNode;
import edu.njit.cs.saboc.blu.core.abn.diff.change.concepts.ConceptMovedFromNode;
import edu.njit.cs.saboc.blu.core.abn.diff.change.concepts.ConceptMovedToNode;
import edu.njit.cs.saboc.blu.core.abn.diff.change.concepts.ConceptRemovedFromNode;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.graph.nodes.AbNNodeEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNTextConfiguration;
import edu.njit.cs.saboc.blu.core.utils.rightclickmanager.EntityRightClickMenuGenerator;
import java.util.ArrayList;
import java.util.Optional;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;

/**
 *
 * @author Chris Ochs
 */
public class DiffNodeRightClickMenuGenerator extends EntityRightClickMenuGenerator<AbNChange> {
    
    private final AbNConfiguration config;
    private final AbNTextConfiguration textConfig;
    
    public DiffNodeRightClickMenuGenerator(
            AbNConfiguration config,
            AbNTextConfiguration textConfig) {
        
        this.config = config;
        this.textConfig = textConfig;
    }

    @Override
    public ArrayList<JComponent> buildRightClickMenuFor(AbNChange item) {
        
        ArrayList<JComponent> menu = new ArrayList<>();
        
        if(item instanceof ConceptMovedFromNode) {
            
            ConceptMovedFromNode conceptMovedFromNode = (ConceptMovedFromNode)item;
            
            Optional<AbNNodeEntry> optEntry = getNodeEntryFor(conceptMovedFromNode.getMovedFrom());
            
            if(optEntry.isPresent()) {
                JMenuItem menuItem = new JMenuItem(String.format("Display Previous %s", 
                    textConfig.getNodeTypeName(false)));
                
                menuItem.addActionListener( (ae) -> {
                    config.getUIConfiguration().getDisplayPanel().getAutoScroller().snapToNodeEntry(optEntry.get());
                });
                
                menu.add(menuItem);
            } else {
                menu.add(new JLabel(String.format("Previous %s currently hidden...", 
                    textConfig.getNodeTypeName(false).toLowerCase())));
            }

        } else if(item instanceof ConceptMovedToNode) {
            
            ConceptMovedToNode conceptMovedToNode = (ConceptMovedToNode)item;
            
            Optional<AbNNodeEntry> optEntry = getNodeEntryFor(conceptMovedToNode.getMovedTo());

            if (optEntry.isPresent()) {
                JMenuItem menuItem = new JMenuItem(String.format("Display Current %s",
                        textConfig.getNodeTypeName(false)));

                menuItem.addActionListener((ae) -> {
                    config.getUIConfiguration().getDisplayPanel().getAutoScroller().snapToNodeEntry(optEntry.get());
                });

                menu.add(menuItem);
            } else {
                menu.add(new JLabel(String.format("Current %s currently hidden...",
                        textConfig.getNodeTypeName(false).toLowerCase())));
            }

        } else if(item instanceof ConceptAddedToNode) {
            
            ConceptAddedToNode conceptAddedToNode = (ConceptAddedToNode)item;
            
        } else if(item instanceof ConceptRemovedFromNode) {
            
            ConceptRemovedFromNode conceptRemovedFromNode = (ConceptRemovedFromNode)item;
            
            
        }
        
        return menu;
    }

    @Override
    public ArrayList<JComponent> buildEmptyListRightClickMenu() {
        return new ArrayList<>();
    }
        
    private Optional<AbNNodeEntry> getNodeEntryFor(Node node) {
        AbNNodeEntry nodeEntry = null;
        
        if(node instanceof SinglyRootedNode) {
            nodeEntry = config.getUIConfiguration().getDisplayPanel().getGraph().getNodeEntries().get((SinglyRootedNode)node);
        } else if(node instanceof PartitionedNode) {
            nodeEntry = config.getUIConfiguration().getDisplayPanel().getGraph().getContainerEntries().get((PartitionedNode)node);
        }
        
        return Optional.of(nodeEntry);
    }
}
