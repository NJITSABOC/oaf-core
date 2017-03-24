package edu.njit.cs.saboc.nat.generic.errorreport.error;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError.Severity;
import edu.njit.cs.saboc.nat.generic.errorreport.error.child.ErroneousChildError;
import edu.njit.cs.saboc.nat.generic.errorreport.error.child.MissingChildError;
import edu.njit.cs.saboc.nat.generic.errorreport.error.child.OtherChildError;
import edu.njit.cs.saboc.nat.generic.errorreport.error.parent.ErroneousParentError;
import edu.njit.cs.saboc.nat.generic.errorreport.error.parent.MissingParentError;
import edu.njit.cs.saboc.nat.generic.errorreport.error.parent.OtherParentError;
import edu.njit.cs.saboc.nat.generic.errorreport.error.parent.RedundantParentError;
import edu.njit.cs.saboc.nat.generic.errorreport.error.parent.ReplaceParentError;
import edu.njit.cs.saboc.nat.generic.errorreport.error.semanticrel.OtherSemanticRelationshipError;
import edu.njit.cs.saboc.nat.generic.errorreport.error.semanticrel.IncorrectSemanticRelationshipError;
import edu.njit.cs.saboc.nat.generic.errorreport.error.semanticrel.MissingSemanticRelationshipError;
import edu.njit.cs.saboc.nat.generic.errorreport.error.semanticrel.RemoveSemanticRelationshipError;
import edu.njit.cs.saboc.nat.generic.errorreport.error.semanticrel.ReplaceSemanticRelationshipError;
import edu.njit.cs.saboc.nat.generic.errorreport.error.semanticrel.ReplaceTargetError;
import java.util.Collections;
import java.util.Set;
import org.json.simple.JSONObject;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 * @param <V>
 */
public class ErrorParser<T extends Concept, V extends InheritableProperty> {
    
    private class BaseParseResult {
        
        private final String comment;
        private final Severity severity;
        
        public BaseParseResult(String comment, Severity severity) {
            this.comment = comment;
            this.severity = severity;
        }
        
        public BaseParseResult(BaseParseResult result) {
            this(result.comment, result.severity);
        }
        
        public String getComment() {
            return comment;
        }
        
        public Severity getSeverity() {
            return severity;
        }
    }
    
    public static class ErrorParseException extends Exception {
        public ErrorParseException(String message) {
            super(message);
        }
    }
    
    private class BaseIncorrectParentParseResult<T extends Concept> extends BaseParseResult {
        
        private final T incorrectParent;
        
        public BaseIncorrectParentParseResult(BaseParseResult baseResult, T incorrectParent) {
            super(baseResult);
            
            this.incorrectParent = incorrectParent;
        }
        
        public T getIncorrectParent() {
            return incorrectParent;
        }
    }
    
    private class BaseIncorrectChildParseResult<T extends Concept> extends BaseParseResult {
        
        private final T incorrectChild;
        
        public BaseIncorrectChildParseResult(BaseParseResult baseResult, T incorrectChild) {
            super(baseResult);
            
            this.incorrectChild = incorrectChild;
        }
        
        public T getIncorrectChild() {
            return incorrectChild;
        }
    }
    
    private class BaseIncorrectSemanticRelationshipParseResult<T extends Concept, V extends InheritableProperty> extends BaseParseResult {
        
        private final T incorrectTarget;
        private final V incorrectRelType;
        
        BaseIncorrectSemanticRelationshipParseResult(BaseParseResult baseResult, V incorrectRelType, T incorrectTarget) {
            super(baseResult);
            
            this.incorrectRelType = incorrectRelType;
            this.incorrectTarget = incorrectTarget;
        }
        
        public T getIncorrectTarget() {
            return incorrectTarget;
        }
        
        public V getIncorrectRelType() {
            return incorrectRelType;
        }
    }
    
    private final ConceptBrowserDataSource<T> dataSource;
    
    public ErrorParser(ConceptBrowserDataSource<T> dataSource) {
        this.dataSource = dataSource;
    }
    
    public OntologyError<T> parseError(JSONObject object) throws ErrorParseException {
        String type = object.get("type").toString();
        
        if(type.equals("MissingParent")) {
            return parseMissingParentError(object);
        } else if(type.equals("OtherParentError")) {
            return parseOtherParentError(object);
        } else if(type.equals("RedundantParent")) {
            return parseRedundantParentError(object);
        } else if(type.equals("ErroneousParent")) {
            return parseErroneousParent(object);
        } else if(type.equals("ReplaceParent")) {
            return parseReplaceParentError(object);
        } else if(type.equals("OtherSemanticRelationshipError")) {
            return parseOtherSemanticRelationshipError(object);
        } else if(type.equals("RemoveSemanticRelationship")) {
            return parseRemoveSemanticRelationship(object);
        } else if(type.equals("MissingSemanticRelationship")) {
            return parseMissingSemanticRelationship(object);
        } else if(type.equals("ReplaceTargetError")) {
            return parseReplaceTarget(object);
        } else if(type.equals("OtherError")) {
            return parseOtherError(object);
        } else if(type.equals("MissingChild")) {
            return parseMissingChildError(object);
        } else if(type.equals("OtherChildError")) {
            return parseOtherChildError(object);
        } else if(type.equals("ErroneousChild")) {
            return parseErroneousChildError(object);
        }
        
        throw new ErrorParseException("Unknown error type");
    }
    
    public ErroneousParentError<T> parseErroneousParent(JSONObject object) throws ErrorParseException {
        BaseIncorrectParentParseResult<T> baseResult = getBaseIncorrectParentResult(object);

        return new ErroneousParentError(
                dataSource.getOntology(), 
                baseResult.getIncorrectParent(), 
                baseResult.getComment(),
                baseResult.getSeverity());
    }
    
    public OtherParentError<T> parseOtherParentError(JSONObject object) throws ErrorParseException {
        BaseIncorrectParentParseResult<T> baseResult = getBaseIncorrectParentResult(object);

        return new OtherParentError(
                dataSource.getOntology(), 
                baseResult.getIncorrectParent(), 
                baseResult.getComment(),
                baseResult.getSeverity());
    }
    
    public RedundantParentError<T> parseRedundantParentError(JSONObject object) throws ErrorParseException {
        BaseIncorrectParentParseResult<T> baseResult = getBaseIncorrectParentResult(object);

        return new RedundantParentError(
                dataSource.getOntology(), 
                baseResult.getIncorrectParent(), 
                baseResult.getComment(),
                baseResult.getSeverity());
    }

    public MissingParentError<T> parseMissingParentError(JSONObject object) throws ErrorParseException {
        
        BaseParseResult baseResult = getBaseParseResult(object);
        
        MissingParentError<T> error = new MissingParentError<>(dataSource.getOntology(), baseResult.getComment(), baseResult.getSeverity());
        
        if(object.containsKey("parentid")) {
            T concept = getConceptFrom(object.get("parentid").toString());
            
            error.setMissingParent(concept);
        }
        
        return error;
    }
    
    public ReplaceParentError<T> parseReplaceParentError(JSONObject object) throws ErrorParseException {
        
        BaseIncorrectParentParseResult<T> baseResult = getBaseIncorrectParentResult(object);
        
        ReplaceParentError<T> error = new ReplaceParentError<>(
                dataSource.getOntology(), 
                baseResult.getIncorrectParent(), 
                baseResult.getComment(), 
                baseResult.getSeverity());
        
        if(object.containsKey("replacementparentid")) {
            T concept = getConceptFrom(object.get("replacementparentid").toString());
            
            error.setReplacementParent(concept);
        }
        
        return error;
    }
    
    public OtherSemanticRelationshipError<T, V> parseOtherSemanticRelationshipError(JSONObject object) throws ErrorParseException {
        
        BaseIncorrectSemanticRelationshipParseResult<T, V> baseResult = this.getBaseIncorrectSemanticRelationshipResult(object);
        
        return new OtherSemanticRelationshipError<>(
                dataSource.getOntology(), 
                baseResult.getIncorrectRelType(), 
                baseResult.getIncorrectTarget(), 
                baseResult.getComment(), 
                baseResult.getSeverity());
    }
    
    public IncorrectSemanticRelationshipError<T, V> parseRemoveSemanticRelationship(JSONObject object) throws ErrorParseException {
        BaseIncorrectSemanticRelationshipParseResult<T, V> baseResult = this.getBaseIncorrectSemanticRelationshipResult(object);
        
        return new RemoveSemanticRelationshipError<>(
                dataSource.getOntology(), 
                baseResult.getIncorrectRelType(), 
                baseResult.getIncorrectTarget(), 
                baseResult.getComment(), 
                baseResult.getSeverity());
    }
    
    public MissingSemanticRelationshipError<T, V> parseMissingSemanticRelationship(JSONObject object) throws ErrorParseException {
        BaseParseResult baseResult = this.getBaseParseResult(object);
        
        MissingSemanticRelationshipError<T, V> missingRelationshipError = 
                new MissingSemanticRelationshipError<>(dataSource.getOntology(), baseResult.getComment(), baseResult.getSeverity());
        
        if(object.containsKey("missingreltypeid")) {
            missingRelationshipError.setMissingRelType(this.getPropertyFrom(object.get("missingreltypeid").toString()));
        }
        
        if(object.containsKey("missingtargetid")) {
            missingRelationshipError.setMissingRelTarget(this.getConceptFrom(object.get("missingtargetid").toString()));
        }
        
        return missingRelationshipError;
    }
    
    public ReplaceTargetError<T, V> parseReplaceTarget(JSONObject object) throws ErrorParseException {
        BaseIncorrectSemanticRelationshipParseResult<T, V> baseResult = this.getBaseIncorrectSemanticRelationshipResult(object);
        
        ReplaceTargetError<T, V> replaceTarget = new ReplaceTargetError<>(
                dataSource.getOntology(), 
                baseResult.getIncorrectRelType(), 
                baseResult.getIncorrectTarget(),
                baseResult.getComment(),
                baseResult.getSeverity());
        
        if(object.containsKey("replacementtargetid")) {
            replaceTarget.setReplacementTarget(this.getConceptFrom(object.get("replacementtargetid").toString()));
        }
        
        return replaceTarget;
    }
    
    public ReplaceSemanticRelationshipError<T, V> parseReplaceSemanticRelationship(JSONObject object) throws ErrorParseException {
        BaseIncorrectSemanticRelationshipParseResult<T, V> baseResult = this.getBaseIncorrectSemanticRelationshipResult(object);

        ReplaceSemanticRelationshipError<T, V> replaceRelError
                = new ReplaceSemanticRelationshipError(
                        dataSource.getOntology(),
                        baseResult.getIncorrectRelType(),
                        baseResult.getIncorrectTarget(),
                        baseResult.getComment(),
                        baseResult.getSeverity());
        
        if (object.containsKey("replacementtypeid")) {
            replaceRelError.setReplacementRelationshipType(this.getPropertyFrom(object.get("replacementtypeid").toString()));
        }
        
        if (object.containsKey("replacementtargetid")) {
            replaceRelError.setReplacementTarget(this.getConceptFrom(object.get("replacementtargetid").toString()));
        }
        
        return replaceRelError;
    }
    
    public OtherError<T> parseOtherError(JSONObject object) throws ErrorParseException {
        BaseParseResult baseResult = this.getBaseParseResult(object);
        
        return new OtherError<>(
                dataSource.getOntology(), 
                baseResult.getComment(), 
                baseResult.getSeverity());
    }
    
    public MissingChildError<T> parseMissingChildError(JSONObject object) throws ErrorParseException {
        BaseParseResult baseResult = this.getBaseParseResult(object);
        
        MissingChildError<T> missingChild = new MissingChildError<>(dataSource.getOntology(), baseResult.getComment(), baseResult.getSeverity());
        
        if(object.containsKey("childid")) {
            missingChild.setMissingChild(this.getConceptFrom(object.get("childid").toString()));
        }
        
        return missingChild;
    }
    
    public OtherChildError<T> parseOtherChildError(JSONObject object) throws ErrorParseException {
        BaseIncorrectChildParseResult<T> baseResult = getBaseIncorrectChildResult(object);
        
        return new OtherChildError<>(
                dataSource.getOntology(), 
                baseResult.getIncorrectChild(), 
                baseResult.getComment(), 
                baseResult.getSeverity());
    }
    
    public ErroneousChildError<T> parseErroneousChildError(JSONObject object) throws ErrorParseException {
        BaseIncorrectChildParseResult<T> baseResult = getBaseIncorrectChildResult(object);
        
        return new ErroneousChildError<>(
                dataSource.getOntology(), 
                baseResult.getIncorrectChild(), 
                baseResult.getComment(), 
                baseResult.getSeverity());
    }
    
    private BaseParseResult getBaseParseResult(JSONObject object) throws ErrorParseException {
        String comment = "";
        
        if(object.containsKey("comment")) {
            comment = object.get("comment").toString();
        }
        
        return new BaseParseResult(
                comment, 
                Severity.valueOf(object.get("severity").toString())
        );
    }
    
    private BaseIncorrectParentParseResult<T> getBaseIncorrectParentResult(JSONObject object) throws ErrorParseException {
        return new BaseIncorrectParentParseResult<>(
                getBaseParseResult(object), 
                getConceptFrom(object.get("parentid").toString())
        );
    }
    
    private BaseIncorrectChildParseResult<T> getBaseIncorrectChildResult(JSONObject object) throws ErrorParseException {
        return new BaseIncorrectChildParseResult<>(
                getBaseParseResult(object), 
                getConceptFrom(object.get("childid").toString())
        );
    }
    
    private BaseIncorrectSemanticRelationshipParseResult<T, V> getBaseIncorrectSemanticRelationshipResult(JSONObject object) throws ErrorParseException {
        return new BaseIncorrectSemanticRelationshipParseResult<>(
                getBaseParseResult(object), 
                getPropertyFrom(object.get("typeid").toString()),
                getConceptFrom(object.get("targetid").toString()));
    }
    
    private T getConceptFrom(String conceptId) throws ErrorParseException {
        Set<String> parentID = Collections.singleton(conceptId);

        Set<T> concept = dataSource.getConceptsFromIds(parentID);
        
        if(!concept.isEmpty()) {
            return concept.iterator().next();
        }

        throw new ErrorParseException(String.format("Concept with given id not found (%s)", conceptId));
    }
    
    private V getPropertyFrom(String propertyId) throws ErrorParseException {
        
        Set<? extends InheritableProperty> properties = dataSource.getPropertiesFromIds(Collections.singleton(propertyId));
        
        if(!properties.isEmpty()) {
            return (V)properties.iterator().next();
        }
        
        throw new ErrorParseException(String.format("Property with given id not found (%s)", propertyId)); 
    }
}
