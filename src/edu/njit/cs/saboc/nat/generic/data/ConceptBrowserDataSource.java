package edu.njit.cs.saboc.nat.generic.data;

import java.util.ArrayList;

/**
 *
 * @author Chris O
 */
public interface ConceptBrowserDataSource {

    public ArrayList<BrowserConcept> getConceptParents(BrowserConcept concept);
    
    public ArrayList<BrowserConcept> getConceptChildren(BrowserConcept concept);
    
    public ArrayList<String> getConceptSynonyms(BrowserConcept concept);
    
    public ArrayList<BrowserConcept> getConceptSiblings(BrowserConcept concept);
    
    public ArrayList<BrowserConcept> getAllAncestorsAsList(BrowserConcept concept);
    
    public ArrayList<BrowserConcept> getAllDescendantsAsList(BrowserConcept concept);
    
    public ArrayList<ArrayList<BrowserConcept>> getAllPathsToConcept(BrowserConcept concept);
    
    public ArrayList<BrowserRelationship> getOutgoingLateralRelationships(BrowserConcept concept);
   
    public ArrayList<BrowserSearchResult> searchExact(String term);

    public ArrayList<BrowserSearchResult> searchAnywhere(String term);

    public ArrayList<BrowserSearchResult> searchStarting(String term);
    
    public BrowserConcept getRoot();
    
    public String getConceptName(BrowserConcept concept);
            
    public String getConceptId(BrowserConcept concept);
    
    public BrowserConcept getConceptFromId(String id);
    
    public String getRelationshipName(BrowserRelationship relationship);
    
    public BrowserConcept getRelationshipTarget(BrowserRelationship relationship);
    
    /*
                case CONCEPTREL:
                    result = dataSource.getOutgoingLateralRelationships(concept);
                    break;
                case CONCEPT:
                    result = dataSource.getConceptFromId(concept.getId());
                    break;
                case PARTIALAREA:
                    result = dataSource.getSummaryOfPAreasContainingConcept(concept);
                    break;
                case TRIBALAN:
                    result = ((SCTLocalDataSource)dataSource).getSummaryOfClustersContainingConcept(concept);
                    break;
                    
                case HIERARCHYMETRICS:
                    result = ((SCTLocalDataSource)dataSource).getHierarchyMetrics(concept);
                    break;
    */

}
