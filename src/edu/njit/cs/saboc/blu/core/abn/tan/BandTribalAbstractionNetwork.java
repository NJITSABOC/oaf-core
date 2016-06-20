/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.njit.cs.saboc.blu.core.abn.tan;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.NodeHierarchy;
import java.util.Set;

/**
 *
 * @author Den
 */
public class BandTribalAbstractionNetwork extends AbstractionNetwork<Band> {
    public BandTribalAbstractionNetwork(NodeHierarchy<Band> bandHierarchy) {
        super(bandHierarchy);
    }
    
    public NodeHierarchy<Band> getBandHierarchy(){
        return super.getNodeHierarchy();
    }
    
    public Set<Band> getBands() {
        return super.getNodes();
    }
}