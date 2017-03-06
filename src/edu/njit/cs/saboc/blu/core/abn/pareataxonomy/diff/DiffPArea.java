
package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.DiffNode;
import edu.njit.cs.saboc.blu.core.abn.diff.DiffNodeInstance;
import edu.njit.cs.saboc.blu.core.abn.diff.ModifiedNode;
import edu.njit.cs.saboc.blu.core.abn.diff.change.ChangeState;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashSet;
import java.util.Set;

/**
 * A diff node that 
 * 
 * @author Chris O
 */
public class DiffPArea extends PArea implements DiffNodeInstance {

    private final DiffNode diffNode;
    
    public DiffPArea(DiffNode diffNode, PArea changedPArea) {

        super(changedPArea.getHierarchy(), changedPArea.getRelationships());
        
        this.diffNode = diffNode;
    }
    
    public ChangeState getPAreaState() {
        return diffNode.getChangeDetails().getNodeState();
    }
    
    public DiffNode getDiffNode() {
        return diffNode;
    }
    
    public Set<Concept> getConcepts() {
        Set<Concept> concepts = new HashSet<>(super.getConcepts());
        

        if(getPAreaState() == ChangeState.Modified) {
            ModifiedNode modifiedNode = (ModifiedNode)getDiffNode();
            
            concepts.addAll(modifiedNode.getFromNode().getConcepts());
        }

        return concepts;
    }
}
