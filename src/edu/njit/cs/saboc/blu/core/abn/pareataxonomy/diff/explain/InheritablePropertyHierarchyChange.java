package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.explain;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Objects;

/**
 *
 * @author Chris O
 */
public class InheritablePropertyHierarchyChange extends InheritablePropertyChange {

    public static enum SubclassState {
        Added,
        Removed
    }
    
    private final Concept parent;
    
    
    private final SubclassState subclassState;

    public InheritablePropertyHierarchyChange(
            Concept child,
            Concept parent,
            InheritableProperty property,
            SubclassState subclassState,
            ChangeInheritanceType inheritanceType) {

        super(child, property, inheritanceType);
        
        this.parent = parent;
        this.subclassState = subclassState;
    }

    public Concept getParent() {
        return parent;
    }
    
    public Concept getChild() {
        return super.getAffectedConcept();
    }

    public SubclassState getSubclassState() {
        return subclassState;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        
        hash = 97 * hash + Objects.hashCode(this.parent);
        hash = 97 * hash + Objects.hashCode(this.getParent());
        hash = 97 * hash + Objects.hashCode(this.subclassState);
        
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        final InheritablePropertyHierarchyChange other = (InheritablePropertyHierarchyChange) obj;
        
        if(!this.getChild().equals(other.getChild())) {
            return false;
        }
        
        if (!Objects.equals(this.parent, other.parent)) {
            return false;
        }
        
        if (!Objects.equals(this.getProperty(), other.getProperty())) {
            return false;
        }
        
        if (this.subclassState != other.subclassState) {
            return false;
        }
        
        return true;
    }
}
