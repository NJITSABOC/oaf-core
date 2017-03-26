package edu.njit.cs.saboc.nat.generic.data;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor.DescendantsVisitor;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.nat.generic.errorreport.AuditResult;
import edu.njit.cs.saboc.nat.generic.errorreport.AuditSet;
import edu.njit.cs.saboc.nat.generic.errorreport.error.ErrorParser;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Defines implementation-specific functionality that must be implemented by every instance
 * of the NAT
 * 
 * @author Chris O
 * @param <T>
 */
public abstract class ConceptBrowserDataSource<T extends Concept> {

    private final Ontology<T> ontology;
    private final Map<T, Integer> descendantCount;
    
    public ConceptBrowserDataSource(Ontology<T> ontology) {
        this.ontology = ontology;
        
        Hierarchy<T> conceptHierarchy = ontology.getConceptHierarchy();
        
        DescendantsVisitor<T> descendantsVisitor = new DescendantsVisitor<>(conceptHierarchy);
        
        conceptHierarchy.topologicalUp(conceptHierarchy.getLeaves(), descendantsVisitor);
        
        this.descendantCount = descendantsVisitor.getDescendantCounts();
    }
    
    public Ontology<T> getOntology() {
        return ontology;
    }
    
    public int getDescendantCount(T concept) {
        return descendantCount.get(concept);
    }
    
    public String getFocusConceptText(T concept) {
        return String.format("<html><font face='Arial' size = '5'><b>%s</b><br>%s", 
                concept.getName(), 
                concept.getIDAsString());
    }
    
    public String getFocusConceptText(AuditSet<T> auditSet, T concept) {
        
        return String.format("<html><font face='Arial' size = '5'><b>%s</b><br>%s<br>%s", 
                concept.getName(), 
                concept.getIDAsString(),
                getStyledAuditStatusText(auditSet, concept));
    }
    
    public String getStyledAuditStatusText(AuditSet<T> auditSet, T concept) {
        if(auditSet.getConcepts().contains(concept)) {
            Optional<AuditResult<T>> optAuditResult = auditSet.getAuditResult(concept);
            
            if(optAuditResult.isPresent()) {
                AuditResult.State auditState = optAuditResult.get().getState();
                
                String base = "<b>Audit Status: <font color = '%s'>%s</font></b>";
                
                if(auditState == AuditResult.State.Unaudited) {
                    return String.format(base, "black", "Unaudited");
                } else if(auditState == AuditResult.State.Correct) {
                    return String.format(base, "green", "Correct");
                } else {
                    List<OntologyError<T>> errors = auditSet.getAllReportedErrors(concept);
                    
                    return String.format(base, "red", String.format("Erroneous - %d Error(s) reported", errors.size()));
                }
            }
        }
        
        return "";
    }
    
    public String getConceptToolTipText(T concept) {
        return String.format("<html><font size = '5'>%s</font>", concept.getName());
    }
    
    public String getAuditConceptToolTipText(AuditSet<T> auditSet, T concept) {
        
        String result = this.getConceptToolTipText(concept);

        if (auditSet.getConcepts().contains(concept)) {

            Optional<AuditResult<T>> optAuditResult = auditSet.getAuditResult(concept);

            String auditStatus = this.getStyledAuditStatusText(auditSet, concept);

            result += "<br>";
            result += auditStatus;

            if (optAuditResult.isPresent()) {
                AuditResult<T> auditResult = optAuditResult.get();

                if (!auditResult.getComment().isEmpty()) {
                    result += "<p><p>";
                    result += String.format("<b>Audit comment: </b> <div align = 'justify'>%s</div>", auditResult.getComment());
                }

                ArrayList<OntologyError<T>> errors = auditResult.getErrors();

                if (!errors.isEmpty()) {
                    result += String.format("<p><p><font size = '3'><b>"
                            + "Reported Errors (%d)</b></font><br>", errors.size());

                    result += "<ul>";
                    
                    for (OntologyError<T> error : errors) {
                        result += String.format("<li>%s</li>", error.getStyledText());
                    }
                    
                    result += "</ul>";
                }
            }
        }
        
        return result;
    }
    
    public ErrorParser<T, InheritableProperty> getErrorParser() {
        return new ErrorParser<>(this);
    }
    
    public abstract String getOntologyID();
    
    public abstract ArrayList<NATConceptSearchResult<T>> searchStarting(String str);
    public abstract ArrayList<NATConceptSearchResult<T>> searchExact(String str);
    public abstract ArrayList<NATConceptSearchResult<T>> searchAnywhere(String str);
    public abstract ArrayList<NATConceptSearchResult<T>> searchID(String str);
    
    public abstract ArrayList<InheritableProperty> getAvailableProperties();
    
    public abstract Set<T> getConceptsFromIds(Set<String> id);
    public abstract Set<? extends InheritableProperty>  getPropertiesFromIds(Set<String> ids);
}
