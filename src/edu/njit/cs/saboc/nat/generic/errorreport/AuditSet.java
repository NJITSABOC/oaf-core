
package edu.njit.cs.saboc.nat.generic.errorreport;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class AuditSet<T extends Concept> {
    
    private String name = "";

    private Date date;
    
    private final Set<T> concepts;

    public AuditSet() {
        this.concepts = new HashSet<>();
    }
    
    public AuditSet(String name, Set<T> concepts) {
        this(name, new Date(), concepts);
    }
    
    public AuditSet(
            String name, 
            Date date, 
            Set<T> concepts) {
        
        this.name = name;
        this.date = date;
        this.concepts = concepts;
    }

    public Set<T> getConcepts() {
        return concepts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void addConcept(T c) {
        concepts.add(c);
    }

    public void removeConcept(T c) {
        concepts.remove(c);
    }

    public int size() {
        return concepts.size();
    }
}
