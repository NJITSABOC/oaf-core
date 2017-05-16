
package edu.njit.cs.saboc.nat.generic.errorreport;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.recentlyopenedfile.FileUtilities;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.errorreport.AuditResult.State;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import edu.njit.cs.saboc.nat.generic.errorreport.error.child.ChildError;
import edu.njit.cs.saboc.nat.generic.errorreport.error.child.IncorrectChildError;
import edu.njit.cs.saboc.nat.generic.errorreport.error.parent.IncorrectParentError;
import edu.njit.cs.saboc.nat.generic.errorreport.error.parent.ParentError;
import edu.njit.cs.saboc.nat.generic.errorreport.error.semanticrel.IncorrectSemanticRelationshipError;
import edu.njit.cs.saboc.nat.generic.errorreport.error.semanticrel.SemanticRelationshipError;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Represents a set of concepts to be audited by a domain expert.
 *  
 * @author Chris O
 * @param <T>
 */
public class AuditSet<T extends Concept> {
        
    private final String name;

    private final Date dateCreated;
    private Date lastSavedDate;
    
    private final File file;
    
    private final Set<T> concepts;
    
    private final Map<T, AuditResult<T>> auditResults;
    
    private final ConceptBrowserDataSource<T> dataSource;
    
    private final ArrayList<AuditSetChangedListener<T>> changeListeners = new ArrayList<>();

    public AuditSet(
            ConceptBrowserDataSource<T> dataSource, 
            File file,
            String name,
            Set<T> concepts) {
        
        this(dataSource, 
                file,
                name, 
                new Date(), 
                new Date(),
                concepts, 
                new HashMap<>());
    }
    
    public AuditSet(
            ConceptBrowserDataSource<T> dataSource,
            File file,
            String name, 
            Date dateCreated, 
            Date lastSavedDate,
            Set<T> concepts,
            Map<T, AuditResult<T>> auditResults) {
        
        this.dataSource = dataSource;
        
        this.file = file;
        
        this.name = name;
        this.dateCreated = dateCreated;
        this.lastSavedDate = lastSavedDate;
        this.concepts = concepts;
        this.auditResults = auditResults;
        
        this.addAuditSetChangedListener(new AuditSetChangedAdapter() {

            @Override
            public void auditSetChanged() {
                if(!saveToFile(file)) {
                    
                }
            }
        });
    }
    
    public final void addAuditSetChangedListener(AuditSetChangedListener<T> listener) {
        this.changeListeners.add(listener);
    }
    
    public final void removeAuditSetChangedListener(AuditSetChangedListener<T> listener) {
        this.changeListeners.remove(listener);
    }

    public Set<T> getConcepts() {
        return concepts;
    }

    public File getFile() {
        return file;
    }
    
    public String getName() {
        return name;
    }

    public Date getDateCreated() {
        return dateCreated;
    }
    
    public Date getLastSavedDate() {
        return lastSavedDate;
    }

    public void addConcept(T c) {
        concepts.add(c);
        
        changeListeners.forEach( (listener) -> {
            listener.conceptAddedToAuditSet(c);
        });
        
        fireAuditSetChanged();
    }

    public void removeConcept(T c) {
        concepts.remove(c);
        
        changeListeners.forEach( (listener) -> {
            listener.conceptRemovedFromAuditSet(c);
        });
        
        fireAuditSetChanged();
    }

    public int size() {
        return concepts.size();
    }
    
    private AuditResult<T> createAuditResult(T concept) {
        AuditResult r = new AuditResult();
        auditResults.put(concept, r);
        
        return r;
    }
    
    private AuditResult<T> getOrCreateAuditResult(T concept) {
        if (auditResults.containsKey(concept)) {
            return auditResults.get(concept);
        } else {
            return createAuditResult(concept);
        }
    }
    
    public void updateAuditState(T concept, State state) {
        AuditResult ar = getOrCreateAuditResult(concept);
        
        State oldState = ar.getState();
        
        ar.setAuditState(state);
        
        if(state != State.Error) {
            deleteAllErrors(concept);
        }
        
        changeListeners.forEach((listener) -> {
            listener.auditStateChanged(concept, oldState, state);
        });
        
        fireAuditSetChanged();
    }
    
    public void updateComment(T concept, String comment) {
        AuditResult ar = getOrCreateAuditResult(concept);

        ar.setComment(comment);
        
        changeListeners.forEach((listener) -> {
            listener.commentChanged(concept, comment);
        });
        
        fireAuditSetChanged();
    }
    
    public void addError(T concept, OntologyError<T> error) {
        AuditResult ar = getOrCreateAuditResult(concept);
        
        ar.addError(error);
        
        changeListeners.forEach((listener) -> {
            listener.errorAdded(concept, error);
        });
        
        fireAuditSetChanged();
    }

    public void deleteAuditResult(T concept) {
        auditResults.remove(concept);
        
        changeListeners.forEach((listener) -> {
            listener.auditResultRemoved(concept);
        });
        
        fireAuditSetChanged();
    }
    
    public void deleteError(T concept, OntologyError<T> error) {
        AuditResult ar = getOrCreateAuditResult(concept);
        
        ar.getErrors().remove(error);
        
        changeListeners.forEach((listener) -> {
            listener.errorRemoved(concept, error);
        });
        
        fireAuditSetChanged();
    }
    
    public void deleteAllErrors(T concept) {
        AuditResult ar = getOrCreateAuditResult(concept);

        ar.getErrors().clear();

        fireAuditSetChanged();
    }
    
    public void updateError(T concept, 
            OntologyError<T> oldError, 
            OntologyError<T> newError) {
        
        AuditResult ar = getOrCreateAuditResult(concept);
        
        ar.removeError(oldError);
        ar.addError(newError);
        
        changeListeners.forEach((listener) -> {
            listener.errorUpdated(concept, oldError, newError);
        });
        
        fireAuditSetChanged();
    }
    
    private void fireAuditSetChanged() {
        changeListeners.forEach((listener) -> {
            listener.auditSetChanged();
        });
    }
    
    public Optional<AuditResult<T>> getAuditResult(T concept) {
        return Optional.ofNullable(auditResults.get(concept));
    }
    
    public List<OntologyError<T>> getAllReportedErrors(T auditSetConcept) {
        Optional<AuditResult<T>> optAuditResult = getAuditResult(auditSetConcept);

        if (!optAuditResult.isPresent()) {
            return Collections.emptyList();
        }
        
        return optAuditResult.get().getErrors();
    }
    
    /**
     * Returns the list of parent errors for the given concept
     * 
     * @param auditSetConcept
     * @return 
     */
    public List<ParentError<T>> getParentErrors(T auditSetConcept) {
        Optional<AuditResult<T>> optAuditResult = getAuditResult(auditSetConcept);
        
        if(!optAuditResult.isPresent()) {
            return Collections.emptyList();
        }
        
        List<ParentError<T>> relatedErrors = optAuditResult.get().getErrors().stream().filter((error) -> {
            return (error instanceof ParentError);
        }).map((error) -> {
            return (ParentError<T>) error;
        }).collect(Collectors.toList());

        return relatedErrors;
    }
    
    /**
     * Returns the list of child errors for the given concept
     * 
     * @param auditSetConcept
     * @return 
     */
    public List<ChildError<T>> getChildErrors(T auditSetConcept) {
        Optional<AuditResult<T>> optAuditResult = getAuditResult(auditSetConcept);

        if (!optAuditResult.isPresent()) {
            return Collections.emptyList();
        }

        List<ChildError<T>> relatedErrors = optAuditResult.get().getErrors().stream().filter((error) -> {
            return (error instanceof ChildError);
        }).map((error) -> {
            return (ChildError<T>) error;
        }).collect(Collectors.toList());

        return relatedErrors;
    }
    
    /**
     * Returns the list of semantic relationship errors for the given concept
     * 
     * @param auditSetConcept
     * @return 
     */
    public List<SemanticRelationshipError<T>> getSemanticRelationshipErrors(T auditSetConcept) {
        Optional<AuditResult<T>> optAuditResult = getAuditResult(auditSetConcept);

        if (!optAuditResult.isPresent()) {
            return Collections.emptyList();
        }

        List<SemanticRelationshipError<T>> relatedErrors = optAuditResult.get().getErrors().stream().filter((error) -> {
            return (error instanceof SemanticRelationshipError);
        }).map((error) -> {
            return (SemanticRelationshipError<T>) error;
        }).collect(Collectors.toList());

        return relatedErrors;
    }
           
    /**
     * Returns the parent errors specified for the given parent concept in 
     * relation to the given auditSetConcept
     * 
     * @param auditSetConcept
     * @param parentConcept
     * 
     * @return 
     */
    public List<IncorrectParentError<T>> getRelatedParentErrors(T auditSetConcept, T parentConcept) {
        
        Optional<AuditResult<T>> optAuditResult = getAuditResult(auditSetConcept);
        
        if(!optAuditResult.isPresent()) {
            return Collections.emptyList();
        }
        
        List<IncorrectParentError<T>> relatedErrors = optAuditResult.get().getErrors().stream().filter((error) -> {

            if (error instanceof IncorrectParentError) {
                IncorrectParentError<T> parentError = (IncorrectParentError<T>) error;

                return parentError.getIncorrectParent().equals(parentConcept);
            }

            return false;
        }).map((error) -> {
            return (IncorrectParentError<T>) error;
        }).collect(Collectors.toList());

        return relatedErrors;
    }
    
    /**
     * Returns the child errors specified for the given child concept in 
     * relation to the given auditSetConcept
     * 
     * @param auditSetConcept
     * @param childConcept
     * 
     * @return 
     */
    public List<IncorrectChildError<T>> getRelatedChildErrors(T auditSetConcept, T childConcept) {
        
        Optional<AuditResult<T>> optAuditResult = getAuditResult(auditSetConcept);
        
        if(!optAuditResult.isPresent()) {
            return Collections.emptyList();
        }
        
        List<IncorrectChildError<T>> relatedErrors = optAuditResult.get().getErrors().stream().filter((error) -> {

            if (error instanceof IncorrectChildError) {
                IncorrectChildError<T> parentError = (IncorrectChildError<T>) error;

                return parentError.getErroneousChild().equals(childConcept);
            }

            return false;
        }).map((error) -> {
            return (IncorrectChildError<T>) error;
        }).collect(Collectors.toList());

        return relatedErrors;
    }
    
    /**
     * Returns the set of semantic relationship errors with the given property and target
     * in relation to the given auditSetConcept
     * 
     * @param auditSetConcept
     * @param property
     * @param target
     * @return 
     */
    public List<IncorrectSemanticRelationshipError<T, InheritableProperty>> getRelatedSemanticRelationshipErrors(
            T auditSetConcept, 
            InheritableProperty property, 
            T target) {
        
        Optional<AuditResult<T>> optAuditResult = getAuditResult(auditSetConcept);

        if (!optAuditResult.isPresent()) {
            return Collections.emptyList();
        }

        List<IncorrectSemanticRelationshipError<T, InheritableProperty>> relatedErrors = optAuditResult.get().getErrors().stream().filter((error) -> {
            
            if(error instanceof IncorrectSemanticRelationshipError) {
                IncorrectSemanticRelationshipError<T, InheritableProperty> relError = (IncorrectSemanticRelationshipError<T, InheritableProperty>)error;
                
                return relError.getRelType().equals(property) && relError.getTarget().equals(target);
            }
            
            return false;
        }).map((error) -> {
            return (IncorrectSemanticRelationshipError<T, InheritableProperty>) error;
        }).collect(Collectors.toList());

        return relatedErrors;
    }
    
    /**
     * Serializes the audit set to JSON
     * 
     * @return 
     */
    public JSONObject toJSON() {
                
        JSONObject exportJSON = new JSONObject();
        
        exportJSON.put("type", "AuditSet");
        exportJSON.put("name", name);
        exportJSON.put("ontologyid", dataSource.getOntologyID());
        exportJSON.put("creationdate", Long.toString(dateCreated.getTime()));
        exportJSON.put("lastsaveddate", Long.toString(lastSavedDate.getTime()));
        
        JSONArray auditSetJSON = new JSONArray();
        
        this.concepts.forEach( (concept) -> {
            JSONObject auditResultJSON = new JSONObject();

            auditResultJSON.put("conceptid", concept.getIDAsString());
            
            if(auditResults.containsKey(concept)) {
                auditResultJSON.put("errorreport", auditResults.get(concept).toJSON());
            }
            
            auditSetJSON.add(auditResultJSON);
        });
        
        exportJSON.put("auditresult", auditSetJSON);

        return exportJSON;
    }
    
    public boolean save() {
        return saveToFile(file);
    }
    
    /**
     * Saves the audit set to the specified file
     * 
     * @param file
     * @return 
     */
    public boolean saveToFile(File file) {      
        
        if(!FileUtilities.ensureFileExistsAndWritable(file)) {
            // Error
            
            return false;
        }
        
        try (PrintWriter out = new PrintWriter(file)) {
            this.lastSavedDate = new Date();
            
            out.println(this.toJSON());
            
            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
    }
    
    /**
     * Saves an automatically created file containing a backup of
     * the given audit set
     * 
     * @return 
     */
    public boolean saveBackupFile() {
        File auditSetFile = this.getFile();

        File dir = auditSetFile.getParentFile();

        if (dir.isDirectory()) {
            String path = dir.getAbsolutePath() + "\\AuditSet Backup\\";

            File backupDir = new File(path);

            try {
                if (!backupDir.exists()) {
                    backupDir.mkdir();
                }

                DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                Date currentDate = new Date();

                String backupFileName = String.format("%s Backup %s",
                        auditSetFile.getName().substring(0, auditSetFile.getName().length() - 4),
                        dateFormat.format(currentDate));

                String backupFilePath = String.format("%s\\%s", backupDir.getAbsolutePath(), backupFileName);

                File backupFile = new File(backupFilePath);

                if (!backupFile.exists()) {
                    backupFile.createNewFile();

                    saveToFile(backupFile);

                    return true;
                }

            } catch (SecurityException | IOException se) {
                se.printStackTrace();

                return false;
            }
        }

        return false;
    }
}
