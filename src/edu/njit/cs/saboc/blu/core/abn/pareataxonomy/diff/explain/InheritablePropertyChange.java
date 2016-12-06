package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.explain;

import edu.njit.cs.saboc.blu.core.abn.diff.explain.DiffAbNConceptChange;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class InheritablePropertyChange extends DiffAbNConceptChange {
    
   public static enum PropertyState {
        Added,
        Removed,
        Modified,
        Unmodified
    }

    public static enum DomainModificationType {
        Added,
        Removed
    }
    
    private final PropertyState propertyState;
    
    private final InheritableProperty property;

    private final Concept domain;
    
    private final DomainModificationType domainModificationType;

    public InheritablePropertyChange(PropertyState modificationState, 
            InheritableProperty property, 
            ChangeInheritanceType domainType, 
            Concept domain, 
            DomainModificationType domainModificationType) {
        
        super(domain, domainType);
        
        this.propertyState = modificationState;
        this.property = property;
        
        this.domain = domain;
        this.domainModificationType = domainModificationType;
    }
    
    public PropertyState getModificationState() {
        return propertyState;
    }
    
    public Concept getPropertyDomain() {
        return domain;
    }
    
    public InheritableProperty getProperty() {
        return property;
    }

    public DomainModificationType getModificationType() {
        return domainModificationType;
    }
}
