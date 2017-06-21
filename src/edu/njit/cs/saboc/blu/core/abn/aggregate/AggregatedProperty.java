/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.njit.cs.saboc.blu.core.abn.aggregate;

/**
 *
 * @author hl395
 */
public class AggregatedProperty {
    private int bound;
    private boolean isWeighteAggregated;

    public AggregatedProperty() {
        this.bound=1;
        this.isWeighteAggregated= false;
    }

    public AggregatedProperty(int Bound, boolean isWeighted) {
        this.bound = Bound;
        this.isWeighteAggregated = isWeighted;
    }
   
    public int getBound(){
        return bound;
    }
    
    public boolean getWeighted(){
        return isWeighteAggregated;
    }
}
