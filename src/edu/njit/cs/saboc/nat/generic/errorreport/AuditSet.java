
package edu.njit.cs.saboc.nat.generic.errorreport;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class AuditSet<T extends Concept> {
    
    private String name = "";

    private Date date;
    
    private Optional<File> file;
    
    private final Set<T> concepts;
    
    private final Map<T, AuditResult<T>> auditResults;
    
    private final ConceptBrowserDataSource<T> dataSource;

    public AuditSet(
            ConceptBrowserDataSource<T> dataSource, 
            String name,
            Set<T> concepts) {
        
        this(dataSource, 
                Optional.empty(),
                name, 
                new Date(), 
                concepts, 
                new HashMap<>());
    }
    
    public AuditSet(
            ConceptBrowserDataSource<T> dataSource,
            Optional<File> file,
            String name, 
            Date date, 
            Set<T> concepts,
            Map<T, AuditResult<T>> auditResults) {
        
        this.dataSource = dataSource;
        
        this.file = file;
        
        this.name = name;
        this.date = date;
        this.concepts = concepts;
        this.auditResults = auditResults;
    }

    public Set<T> getConcepts() {
        return concepts;
    }

    public Optional<File> getAuditSetFile() {
        return file;
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
    
    private AuditResult createAuditResult(T concept) {
        AuditResult r = new AuditResult();
        auditResults.put(concept, r);
        
        return r;
    }
    
    private AuditResult getOrCreateAuditResult(T concept) {
        
        if(!auditResults.containsKey(concept)) {
            return createAuditResult(concept);
        } else {
            return auditResults.get(concept);
        }
    }
    
    public void updateAuditState(T concept) {
        AuditResult ar = getOrCreateAuditResult(concept);
        
    }
    
    public void updateComment(T concept, String comment) {
        AuditResult ar = getOrCreateAuditResult(concept);

        ar.setComment(comment);
    }
    
    public void addError(T concept, OntologyError<T> error) {
        AuditResult ar = getOrCreateAuditResult(concept);
        
        ar.addError(error);
    }

    public void deleteAuditResult(T concept) {
        auditResults.remove(concept);
    }
    
    public void deleteError(T concept, OntologyError<T> error) {
        AuditResult ar = getOrCreateAuditResult(concept);
        
        ar.getErrors().remove(error);
    }
    
    public void updateError(T concept, OntologyError<T> oldError, OntologyError<T> newError) {
        AuditResult ar = getOrCreateAuditResult(concept);
        
        ar.addError(newError);
        ar.getErrors().remove(oldError);
    }
    
    public Optional<AuditResult> getAuditResult(T concept) {
        return Optional.ofNullable(auditResults.get(concept));
    }
    
    public void exportToJSON(File file) {
        
        if(!ensureFileExistsAndWritable(file)) {
            return;
        }
        
        JSONObject exportJSON = new JSONObject();
        
        exportJSON.put("Type", "AuditSet");
        exportJSON.put("OntologyID", dataSource.getOntologyID());
        exportJSON.put("CreationDate", Long.toString(date.getTime()));
        
        JSONArray auditSetJSON = new JSONArray();
        
        this.concepts.forEach( (concept) -> {
            JSONObject conceptInfoJSON = createConceptJSON(concept);
            JSONObject conceptAuditReportJSON = createAuditReportJSON(concept);
            
            JSONObject auditResultJSON = new JSONObject();
            auditResultJSON.put("Concept", conceptInfoJSON);
            auditResultJSON.put("ErrorReport", conceptAuditReportJSON);
            
            auditSetJSON.add(auditResultJSON);
        });
        
        exportJSON.put("AuditResult", auditSetJSON);

        try (PrintWriter out = new PrintWriter(file)) {
            out.println(JSONValue.toJSONString(auditSetJSON));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    private JSONObject createConceptJSON(T concept) {
        JSONObject conceptInfoJSON = new JSONObject();
        
        conceptInfoJSON.put("ID", concept.getIDAsString());
        
        return conceptInfoJSON;
    }
    
    private JSONObject createAuditReportJSON(T concept) {
        JSONObject auditReportJSON = new JSONObject();
        
        return auditReportJSON;
    }
    
    private boolean ensureFileExistsAndWritable(File file) {
        boolean error = false;
        
        try {
            if(!file.createNewFile()) {
                if(!file.canWrite()) {
                    error = true;
                }
            }
        } catch(Exception e) {
            error = true;
        }
        
        return !error;
    }
}
