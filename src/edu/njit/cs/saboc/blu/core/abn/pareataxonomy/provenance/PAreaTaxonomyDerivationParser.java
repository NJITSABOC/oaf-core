/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomyFactory;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.ConceptLocationDataFactory;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Hao Liu
 */
public class PAreaTaxonomyDerivationParser {

    public PAreaTaxonomyDerivationParser() {
    }

    public <T extends PAreaTaxonomyDerivation> T coreParser(JSONArray jsonArr, Ontology sourceOntology, PAreaTaxonomyFactory factory, ConceptLocationDataFactory conceptFactory) {

        JSONObject jsonObject = findJSONObjectByName(jsonArr, "ClassName");
        String className = (String) jsonObject.get("ClassName");
        T result = null;
        if (className.equalsIgnoreCase("SimplePAreaTaxonomyDerivation")) {
            result = (T) parseSimplePAreaTaxonomyDerivation(jsonArr, sourceOntology, factory, conceptFactory);
        } else if (className.equalsIgnoreCase("RootSubtaxonomyDerivation")) {
            result = (T) parseRootSubtaxonomyDerivation(jsonArr, sourceOntology, factory, conceptFactory);
        } else if (className.equalsIgnoreCase("RelationshipSubtaxonomyDerivation")) {
            result = (T) parseRelationshipSubtaxonomyDerivation(jsonArr, sourceOntology, factory, conceptFactory);
        } else if (className.equalsIgnoreCase("ExpandedSubtaxonomyDerivation")) {
            result = (T) parseExpandedSubtaxonomyDerivation(jsonArr, sourceOntology, factory, conceptFactory);
        } else if (className.equalsIgnoreCase("AncestorSubtaxonomyDerivation")) {
            result = (T) parseAncestorSubtaxonomyDerivation(jsonArr, sourceOntology, factory, conceptFactory);
        }else if (className.equalsIgnoreCase("AggregateRootSubtaxonomyDerivation")) {
            result = (T) parseAggregateRootSubtaxonomyDerivation(jsonArr, sourceOntology, factory, conceptFactory);
        }else if (className.equalsIgnoreCase("AggregatePAreaTaxonomyDerivation")) {
            result = (T) parseAggregatePAreaTaxonomyDerivation(jsonArr, sourceOntology, factory, conceptFactory);
        }else if (className.equalsIgnoreCase("AggregateAncestorSubtaxonomyDerivation")) {
            result = (T) parseAggregateAncestorSubtaxonomyDerivation(jsonArr, sourceOntology, factory, conceptFactory);
        }     
        
        return result;
    }

    public SimplePAreaTaxonomyDerivation parseSimplePAreaTaxonomyDerivation(JSONArray jsonArr, Ontology sourceOntology, PAreaTaxonomyFactory factory, ConceptLocationDataFactory conceptFactory) {

        Concept root = null;

        JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");

        JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");

        JSONObject rootObj = findJSONObjectByName(arr_base, "ConceptID");
        String conceptID = (String) rootObj.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(new ArrayList<String>(Arrays.asList(conceptID)));
            System.out.println("conceptFactory:  " + newRoot.toString());
            root = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
        }

        SimplePAreaTaxonomyDerivation result = new SimplePAreaTaxonomyDerivation(sourceOntology, root, factory);
        return result;
    }

    public RootSubtaxonomyDerivation parseRootSubtaxonomyDerivation(JSONArray jsonArr, Ontology sourceOntology, PAreaTaxonomyFactory factory, ConceptLocationDataFactory conceptFactory) {

        JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
        JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
        PAreaTaxonomyDerivation base = coreParser(arr_base, sourceOntology, factory, conceptFactory);

        Concept pareaRootConcept = null;

        JSONObject conceptObject = findJSONObjectByName(jsonArr, "ConceptID");
        String conceptID = (String) conceptObject.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(new ArrayList<String>(Arrays.asList(conceptID)));
            System.out.println("conceptFactory:  " + newRoot.toString());
            pareaRootConcept = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
        }

        RootSubtaxonomyDerivation result = new RootSubtaxonomyDerivation(base, pareaRootConcept);
        return result;
    }

    public RelationshipSubtaxonomyDerivation parseRelationshipSubtaxonomyDerivation(JSONArray jsonArr, Ontology sourceOntology, PAreaTaxonomyFactory factory, ConceptLocationDataFactory conceptFactory) {

        JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
        JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
        PAreaTaxonomyDerivation base = coreParser(arr_base, sourceOntology, factory, conceptFactory);
        
        //need implement getSelectedProperties by id or sth similar
        Set<InheritableProperty> selectedProperties = null;
        JSONObject propertyObject = findJSONObjectByName(jsonArr, "Properties");
        JSONArray arr_property = (JSONArray) propertyObject.get("Properties");
        RelationshipSubtaxonomyDerivation result = new RelationshipSubtaxonomyDerivation(base, selectedProperties);

        return result;
    }

    public ExpandedSubtaxonomyDerivation parseExpandedSubtaxonomyDerivation(JSONArray jsonArr, Ontology sourceOntology, PAreaTaxonomyFactory factory, ConceptLocationDataFactory conceptFactory) {
        JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
        JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
        PAreaTaxonomyDerivation base = coreParser(arr_base, sourceOntology, factory, conceptFactory);

        Concept aggregatePAreaRoot = null;

        JSONObject conceptObject = findJSONObjectByName(jsonArr, "ConceptID");
        String conceptID = (String) conceptObject.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(new ArrayList<String>(Arrays.asList(conceptID)));
            System.out.println("conceptFactory:  " + newRoot.toString());
            aggregatePAreaRoot = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
        }

        ExpandedSubtaxonomyDerivation result = new ExpandedSubtaxonomyDerivation(base, aggregatePAreaRoot);

        return result;

    }
    
    
    public AncestorSubtaxonomyDerivation parseAncestorSubtaxonomyDerivation(JSONArray jsonArr, Ontology sourceOntology, PAreaTaxonomyFactory factory, ConceptLocationDataFactory conceptFactory){
    
        JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
        JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
        PAreaTaxonomyDerivation base = coreParser(arr_base, sourceOntology, factory, conceptFactory);

        Concept pareaRootConcept = null;

        JSONObject conceptObject = findJSONObjectByName(jsonArr, "ConceptID");
        String conceptID = (String) conceptObject.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(new ArrayList<String>(Arrays.asList(conceptID)));
            System.out.println("conceptFactory:  " + newRoot.toString());
            pareaRootConcept = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
        }
        AncestorSubtaxonomyDerivation result = new AncestorSubtaxonomyDerivation(base, pareaRootConcept);
        return result;
    }

    
    public AggregateRootSubtaxonomyDerivation parseAggregateRootSubtaxonomyDerivation(JSONArray jsonArr, Ontology sourceOntology, PAreaTaxonomyFactory factory, ConceptLocationDataFactory conceptFactory){
        JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
        JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
        PAreaTaxonomyDerivation aggregateBase = coreParser(arr_base, sourceOntology, factory, conceptFactory);

        
        JSONObject boundObject = findJSONObjectByName(jsonArr, "Bound");
        int minBound = (int) boundObject.get("Bound");
        
        Concept selectedAggregatePAreaRoot = null;
        JSONObject conceptObject = findJSONObjectByName(jsonArr, "ConceptID");
        String conceptID = (String) conceptObject.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(new ArrayList<String>(Arrays.asList(conceptID)));
            System.out.println("conceptFactory:  " + newRoot.toString());
            selectedAggregatePAreaRoot = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
        }
    
    AggregateRootSubtaxonomyDerivation result = new AggregateRootSubtaxonomyDerivation(aggregateBase, minBound, selectedAggregatePAreaRoot);
    return  result;
    }
    
    
    
    public AggregatePAreaTaxonomyDerivation parseAggregatePAreaTaxonomyDerivation(JSONArray jsonArr, Ontology sourceOntology, PAreaTaxonomyFactory factory, ConceptLocationDataFactory conceptFactory) {
        JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
        JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
        PAreaTaxonomyDerivation nonAggregateSourceDerivation = coreParser(arr_base, sourceOntology, factory, conceptFactory);

        JSONObject boundObject = findJSONObjectByName(jsonArr, "Bound");
        int bound = (int) boundObject.get("Bound");

        AggregatePAreaTaxonomyDerivation result = new AggregatePAreaTaxonomyDerivation(nonAggregateSourceDerivation, bound);
        return result;
    }
            

    public AggregateAncestorSubtaxonomyDerivation parseAggregateAncestorSubtaxonomyDerivation(JSONArray jsonArr, Ontology sourceOntology, PAreaTaxonomyFactory factory, ConceptLocationDataFactory conceptFactory){
    
        JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
        JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
        PAreaTaxonomyDerivation aggregateBase = coreParser(arr_base, sourceOntology, factory, conceptFactory);

        
        JSONObject boundObject = findJSONObjectByName(jsonArr, "Bound");
        int minBound = (int) boundObject.get("Bound");
        
        Concept selectedAggregatePAreaRoot = null;
        JSONObject conceptObject = findJSONObjectByName(jsonArr, "ConceptID");
        String conceptID = (String) conceptObject.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(new ArrayList<String>(Arrays.asList(conceptID)));
            System.out.println("conceptFactory:  " + newRoot.toString());
            selectedAggregatePAreaRoot = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
        }
    
        AggregateAncestorSubtaxonomyDerivation result = new AggregateAncestorSubtaxonomyDerivation(aggregateBase, minBound, selectedAggregatePAreaRoot);
        return result;
    }
    
    public JSONObject findJSONObjectByName(JSONArray jsonArr, String name) {

        JSONObject result = new JSONObject();

        if (jsonArr.isEmpty()) {
            return result;
        }
        JSONParser parser = new JSONParser();
        for (Object o : jsonArr) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject = (JSONObject) parser.parse(o.toString());
            } catch (ParseException ex) {
                Logger.getLogger(PAreaTaxonomyDerivationParser.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (jsonObject.containsKey(name)) {
                result = jsonObject;
                break;
            }

        }

        return result;
    }

}
