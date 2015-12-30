
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.cluster;

import SnomedShared.Concept;
import edu.njit.cs.saboc.blu.core.abn.GenericParentGroupInfo;
import edu.njit.cs.saboc.blu.core.abn.tan.nodes.GenericCluster;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.BLUAbstractParentGroupTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.BLUGenericTANConfiguration;


/**
 *
 * @author Chris O
 */
public class ParentClusterTableModel<
        CONCEPT_T,
        CLUSTER_T extends GenericCluster> extends BLUAbstractParentGroupTableModel<CONCEPT_T, CLUSTER_T, GenericParentGroupInfo<CONCEPT_T, CLUSTER_T>> {

    private final BLUGenericTANConfiguration config;
    
    public ParentClusterTableModel(BLUGenericTANConfiguration config) {
        super(new String[] {
            String.format("Parent %s", config.getTextConfiguration().getConceptTypeName(false)),
            String.format("Parent %s ID", config.getTextConfiguration().getConceptTypeName(false)), 
            "Parent Cluster", 
            String.format("# %s in Parent Cluster", config.getTextConfiguration().getConceptTypeName(true)), 
            "Band"
        });
        
        this.config = config;
    }

    @Override
    protected Object[] createRow(GenericParentGroupInfo<CONCEPT_T, CLUSTER_T> item) {
       
        return new Object[] {
            config.getTextConfiguration().getConceptName(item.getParentConcept()),
            config.getTextConfiguration().getConceptUniqueIdentifier(item.getParentConcept()),
            item.getParentGroup().getRoot().getName(),
            item.getParentGroup().getConceptCount(),
            config.getTextConfiguration().getGroupsContainerName(item.getParentGroup())
        };
    }
}
