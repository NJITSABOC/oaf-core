/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.framestate;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregatedProperty;

/**
 *
 * @author hl395
 */
public class FrameState {
    
    //auto aggregate state
    private AggregatedProperty ap;
    
    public FrameState(){
        
        this.ap = new AggregatedProperty();
    }
    
    public FrameState(AggregatedProperty ap){        
        this.ap = ap;
    }
    
    public void setAutoAggregate(AggregatedProperty ap){
        this.ap = ap;
    }
    
    public AggregatedProperty getAutoAggregateProperty(){
        return  ap;
    }
}
