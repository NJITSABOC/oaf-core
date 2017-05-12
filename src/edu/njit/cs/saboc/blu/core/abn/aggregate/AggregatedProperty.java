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
    private boolean weightedAggregated;

    public AggregatedProperty() {
        this.bound=1;
        this.weightedAggregated= false;
    }

    public AggregatedProperty(int Bound, boolean Weighted) {
        this.bound = Bound;
        this.weightedAggregated = Weighted;
    }
   
    public int getBound(){
        return bound;
    }
    
    public boolean getWeighted(){
        return weightedAggregated;
    }
}
