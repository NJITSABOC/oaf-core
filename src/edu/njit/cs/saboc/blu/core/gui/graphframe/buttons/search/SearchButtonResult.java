/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.search;

/**
 *
 * @author Den
 */
public class SearchButtonResult {
    private String resultStr;
    
    private Object result;
    
    public SearchButtonResult(String resultStr, Object result) {
        this.resultStr = resultStr;
        this.result = result;
    }
    
    public String toString() {
        return resultStr;
    }
    
    public Object getResult() {
        return result;
    }
}
