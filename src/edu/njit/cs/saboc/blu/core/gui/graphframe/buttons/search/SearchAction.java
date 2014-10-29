/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.search;

import java.util.ArrayList;

/**
 *
 * @author Den
 */
public abstract class SearchAction {
    private String searchActionName;
    
    protected SearchAction(String searchActionName) {
        this.searchActionName = searchActionName;
    }
    
    public String getSearchActionName() {
        return searchActionName;
    }
    
    public abstract ArrayList<SearchButtonResult> doSearch(String term);
    
    public abstract void resultSelected(SearchButtonResult o);
}
