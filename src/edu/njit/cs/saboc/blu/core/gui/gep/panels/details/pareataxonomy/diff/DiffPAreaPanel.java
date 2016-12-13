package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.explain.InheritablePropertyDomainChange;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.explain.InheritablePropertyHierarchyChange;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeHierarchyPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.diff.DiffSinglyRootedNodePanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.configuration.DiffPAreaTaxonomyConfiguration;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class DiffPAreaPanel extends DiffSinglyRootedNodePanel<DiffPArea> {
    
    private final DiffPAreaTaxonomyConfiguration diffConfiguration;
    
    public DiffPAreaPanel(DiffPAreaTaxonomyConfiguration configuration) {
        
        super(new DiffPAreaDetailsPanel(configuration),
                
                new NodeHierarchyPanel(
                        configuration,
                        new DiffParentPAreaTableModel(configuration),
                        new DiffChildPAreaTableModel(configuration)), 
                
                configuration);
        
        this.diffConfiguration = configuration;
    }
    
    public DiffPAreaPanel(DiffPAreaTaxonomyConfiguration configuration,  
            DiffPAreaSummaryTextFactory textFactory) {
        
        super(new DiffPAreaDetailsPanel(configuration, textFactory),
                new NodeHierarchyPanel(
                        configuration,
                        new DiffParentPAreaTableModel(configuration),
                        new DiffChildPAreaTableModel(configuration)),
                
                configuration);
        
        this.diffConfiguration = configuration;
    }


    @Override
    public void setContents(DiffPArea node) {
        super.setContents(node); 
        
        DiffPAreaTaxonomy diffTaxonomy = diffConfiguration.getPAreaTaxonomy();
        
        Set<InheritablePropertyDomainChange> domainChanges = 
                diffTaxonomy.getOntologyDifferences().getInheritablePropertyChanges().getPropertyDomainChangesFor(node.getRoot());
        
        
        System.out.println("Property Domain Changes");
        
        domainChanges.forEach( (change) -> {
            System.out.println(String.format("%s\t%s\t%s", change.getProperty().getName(), change.getModificationState(), change.getModificationType()));
        });
        
        System.out.println();
        System.out.println();
        
        Set<InheritablePropertyHierarchyChange> hierarchyChanges = 
                diffTaxonomy.getOntologyDifferences().getInheritablePropertyChanges().getPropertyHierarchyChangesFor(node.getRoot());
        
        
        hierarchyChanges.forEach( (change) -> {
            System.out.println(String.format("%s\t%s\t%s", change.getProperty().getName(), change.getParent(), change.getSubclassState()));
        });
        
        
        
    }
    
}
