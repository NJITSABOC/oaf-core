package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.cluster;

import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeHierarchyPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.BLUGenericTANConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public class ClusterHierarchyPanel<CONCEPT_T, 
        CLUSTER_T extends Cluster,
        CONFIG_T extends BLUGenericTANConfiguration> 

        extends NodeHierarchyPanel<CONCEPT_T, CLUSTER_T, CONFIG_T> {
    
    public ClusterHierarchyPanel(CONFIG_T config) {
        super(config,
                new ParentClusterTableModel(config),
                new ChildClusterTableModel(config));
    }
    
    @Override
    protected void loadParentGroupInfo(CLUSTER_T group) {
        parentModel.setContents(new ArrayList<>(group.getParentClusterInfo()));
    }

    @Override
    protected void loadChildGroupInfo(CLUSTER_T group) {
       HashSet<CLUSTER_T> childClusters = this.getConfiguration().getDataConfiguration().getTribalAbstractionNetwork().getChildGroups(group);
       
       ArrayList<CLUSTER_T> sortedChildClusters = new ArrayList<>(childClusters);
       
       Collections.sort(sortedChildClusters, new Comparator<CLUSTER_T>() {
           public int compare(CLUSTER_T a, CLUSTER_T b) {
               int aLevel = a.getPatriarchs().size();
               int bLevel = b.getPatriarchs().size();
               
               if(aLevel == bLevel) {
                   return a.getRoot().getName().compareTo(b.getRoot().getName());
               } else {
                   return aLevel - bLevel;
               }
           }
       });
       
       childModel.setContents(sortedChildClusters);
    }
}
