/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

import edu.njit.cs.saboc.blu.core.abn.disjoint.provenance.AggregateAncestorDisjointAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.disjoint.provenance.AggregateDisjointAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.disjoint.provenance.AncestorDisjointAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.disjoint.provenance.DisjointAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.disjoint.provenance.ExpandedDisjointAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.disjoint.provenance.OverlappingNodeDisjointAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.disjoint.provenance.SimpleDisjointAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.disjoint.provenance.SubsetDisjointAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.AggregateAncestorSubtaxonomyDerivation;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.AggregatePAreaTaxonomyDerivation;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.AggregateRootSubtaxonomyDerivation;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.AncestorSubtaxonomyDerivation;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.ExpandedSubtaxonomyDerivation;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.PAreaTaxonomyDerivation;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.RelationshipSubtaxonomyDerivation;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.RootSubtaxonomyDerivation;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.SimplePAreaTaxonomyDerivation;
import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.AggregateAncestorSubTANDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.AggregateRootSubTANDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.AggregateTANDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.AncestorSubTANDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.ClusterTANDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.ExpandedSubTANDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.RootSubTANDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.SimpleClusterTANDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.TANFromPartitionedNodeDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.TANFromSinglyRootedNodeDerivation;
import edu.njit.cs.saboc.blu.core.abn.targetbased.provenance.AggregateTargetAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.targetbased.provenance.ExpandedTargetAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.targetbased.provenance.TargetAbNDerivation;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.ConceptLocationDataFactory;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.PropertyLocationDataFactory;
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
 * @author hl395
 * 
 * @param <O>
 * @param <C>
 * @param <P>
 * @param <A>
 */
public class AbNDerivationCoreParser<
        O extends Ontology, 
        C extends ConceptLocationDataFactory, 
        P extends PropertyLocationDataFactory, 
        A extends AbNDerivationFactoryTesting> {
    
    
    public static class AbNParseException extends Exception {
        public AbNParseException(String message) {
            super(message);
        }
    }

    private final O sourceOntology;
    private final C conceptFactory;
    private final P propertyFactory;
    private final A testing;

    public AbNDerivationCoreParser(O ontology, C conceptFactory, P propertyFactory, A testing) {
        this.sourceOntology = ontology;
        this.conceptFactory = conceptFactory;
        this.propertyFactory = propertyFactory;
        this.testing = testing;
    }

    public AbNDerivation coreParser(JSONObject obj) throws AbNParseException {

        if(!obj.containsKey("ClassName")) {
            // TODO: Error
        }
        
        String className = obj.get("ClassName").toString();

        if (className.equalsIgnoreCase("SimplePAreaTaxonomyDerivation")) {
            return parseSimplePAreaTaxonomyDerivation(obj);
        } else if (className.equalsIgnoreCase("RootSubtaxonomyDerivation")) {
            return parseRootSubtaxonomyDerivation(obj);
        } else if (className.equalsIgnoreCase("RelationshipSubtaxonomyDerivation")) {
            return parseRelationshipSubtaxonomyDerivation(obj);
        } else if (className.equalsIgnoreCase("ExpandedSubtaxonomyDerivation")) {
            return parseExpandedSubtaxonomyDerivation(obj);
        } else if (className.equalsIgnoreCase("AncestorSubtaxonomyDerivation")) {
            return parseAncestorSubtaxonomyDerivation(obj);
        } else if (className.equalsIgnoreCase("AggregateRootSubtaxonomyDerivation")) {
            return parseAggregateRootSubtaxonomyDerivation(obj);
        } else if (className.equalsIgnoreCase("AggregatePAreaTaxonomyDerivation")) {
            return parseAggregatePAreaTaxonomyDerivation(obj);
        } else if (className.equalsIgnoreCase("AggregateAncestorSubtaxonomyDerivation")) {
            return parseAggregateAncestorSubtaxonomyDerivation(obj);
        } else if (className.equalsIgnoreCase("SimpleDisjointAbNDerivation")) {
            return parseSimpleDisjointAbNDerivation(obj);
        } else if (className.equalsIgnoreCase("SubsetDisjointAbNDerivation")) {
            return parseSubsetDisjointAbNDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("OverlappingNodeDisjointAbNDerivation")) {
            return parseOverlappingNodeDisjointAbNDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("ExpandedDisjointAbNDerivation")) {
            return parseExpandedDisjointAbNDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("AncestorDisjointAbNDerivation")) {
            return parseAncestorDisjointAbNDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("AggregateDisjointAbNDerivation")) {
            return parseAggregateDisjointAbNDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("AggregateAncestorTANDerivation")) {
            return parseAggregateAncestorTANDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("SimpleClusterTANDerivation")) {
            return parseSimpleClusterTANDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("RootSubTANDerivation")) {
            return parseRootSubTANDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("ExpandedSubTANDerivation")) {
            return parseExpandedSubTANDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("AncestorSubTANDerivation")) {
            return parseAncestorSubTANDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("AggregateTANDerivation")) {
            return parseAggregateTANDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("AggregateRootSubTANDerivation")) {
            return parseAggregateRootSubTANDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("AggregateAncestorSubTANDerivation")) {
            return parseAggregateAncestorSubTANDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("TANFromPartitionedNodeDerivation")) {
            return parseTANFromPartitionedNodeDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("TANFromSinglyRootedNodeDerivation")) {
            return parseTANFromSinglyRootedNodeDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("TargetAbNDerivation")) {
            return parseTargetAbNDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing, baseDerivation);
        } else if (className.equalsIgnoreCase("ExpandedTargetAbNDerivation")) {
            return parseExpandedTargetAbNDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing, baseDerivation);
        } else if (className.equalsIgnoreCase("AggregateTargetAbNDerivation")) {
            return parseAggregateTargetAbNDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing, baseDerivation);
        }
        
        return null;
    }

    public SimplePAreaTaxonomyDerivation parseSimplePAreaTaxonomyDerivation(JSONObject obj) throws AbNParseException {
        
        if(!obj.containsKey("ConceptID")) {
            // TODO: Error
        }

        String conceptID = obj.get("ConceptID").toString();
        
        Set<Concept> root = conceptFactory.getConceptsFromIds(new ArrayList<>(Arrays.asList(conceptID)));
        
        if(root.isEmpty()) {
            // TODO: Error
        }

        SimplePAreaTaxonomyDerivation result = new SimplePAreaTaxonomyDerivation(
                sourceOntology, 
                root.iterator().next(), 
                testing.getPAreaTaxonomyFactory());
        
        return result;
    }

    public RootSubtaxonomyDerivation parseRootSubtaxonomyDerivation(JSONObject obj) throws AbNParseException {
        
        if(!obj.containsKey("BaseDerivation")) {
            // TODO: Error
        }
        
        if(!obj.containsKey("ConceptID")) {
            // TODO: Error
        }
        
        PAreaTaxonomyDerivation base = (PAreaTaxonomyDerivation)this.coreParser((JSONObject)obj.get("BaseDerivation"));

        String conceptID = obj.get("ConceptID").toString();
        
        Set<Concept> root = conceptFactory.getConceptsFromIds(new ArrayList<>(Arrays.asList(conceptID)));
        
        if(root.isEmpty()) {
            // TODO: Error
        }
        
        return new RootSubtaxonomyDerivation(base, root.iterator().next());
    }

    public RelationshipSubtaxonomyDerivation parseRelationshipSubtaxonomyDerivation(JSONObject obj) throws AbNParseException {
        
        if(!obj.containsKey("BaseDerivation")) {
            // TODO: Error
        }
        
        if(!obj.containsKey("PropertyIDs")) {
            // TODO: Error
        }
        
        PAreaTaxonomyDerivation base = (PAreaTaxonomyDerivation)this.coreParser((JSONObject)obj.get("BaseDerivation"));

        ArrayList<String> propertyIds = new ArrayList<>();
        JSONArray propertyIdsJSON = (JSONArray)obj.get("PropertyIDs");
        
        propertyIdsJSON.forEach( (propertyIdStr) -> {
            propertyIds.add(propertyIdStr.toString());
        });
        
        if(propertyIds.isEmpty()) {
            // TODO: Error
        }

        Set<InheritableProperty> selectedProperties = propertyFactory.getPropertiesFromIds(propertyIds);
        
        if(selectedProperties.isEmpty()) {
            // TODO: Error
        }

        return new RelationshipSubtaxonomyDerivation(base, selectedProperties);
    }

    public ExpandedSubtaxonomyDerivation parseExpandedSubtaxonomyDerivation(JSONObject obj) throws AbNParseException {
        
        if(!obj.containsKey("BaseDerivation")) {
            // TODO: Error
        }
        
        if(!obj.containsKey("ConceptID")) {
            // TODO: Error
        }
        
        PAreaTaxonomyDerivation base = (PAreaTaxonomyDerivation)this.coreParser((JSONObject)obj.get("BaseDerivation"));

        String conceptID = obj.get("ConceptID").toString();
        
        Set<Concept> root = conceptFactory.getConceptsFromIds(new ArrayList<>(Arrays.asList(conceptID)));
        
        if(root.isEmpty()) {
            // TODO: Error
        }
        
        return new ExpandedSubtaxonomyDerivation(base, root.iterator().next());
    }

    public AncestorSubtaxonomyDerivation parseAncestorSubtaxonomyDerivation(JSONObject obj) throws AbNParseException {
        
        if(!obj.containsKey("BaseDerivation")) {
            // TODO: Error
        }
        
        if(!obj.containsKey("ConceptID")) {
            // TODO: Error
        }
        
        PAreaTaxonomyDerivation base = (PAreaTaxonomyDerivation)this.coreParser((JSONObject)obj.get("BaseDerivation"));

        String conceptID = obj.get("ConceptID").toString();
        
        Set<Concept> root = conceptFactory.getConceptsFromIds(new ArrayList<>(Arrays.asList(conceptID)));
        
        if(root.isEmpty()) {
            // TODO: Error
        }
        
        return new AncestorSubtaxonomyDerivation(base, root.iterator().next());
    }

    public AggregateRootSubtaxonomyDerivation parseAggregateRootSubtaxonomyDerivation(JSONObject obj) throws AbNParseException {

        if(!obj.containsKey("BaseDerivation")) {
            // TODO: Error
        }
        
        if(!obj.containsKey("ConceptID")) {
            // TODO: Error
        }
        
        if(!obj.containsKey("Bound")) {
            // TODO: Error
        }
        
        PAreaTaxonomyDerivation base = (PAreaTaxonomyDerivation)this.coreParser((JSONObject)obj.get("BaseDerivation"));

        String conceptID = obj.get("ConceptID").toString();
        
        Set<Concept> root = conceptFactory.getConceptsFromIds(new ArrayList<>(Arrays.asList(conceptID)));
        
        if(root.isEmpty()) {
            // TODO: Error
        }
        
        try {
            int minBound = Integer.parseInt(obj.get("Bound").toString());
            
            return new AggregateRootSubtaxonomyDerivation(base, minBound, root.iterator().next());
        } catch (NumberFormatException nfe) {
            throw new AbNParseException("Incorrectly formatted aggregate bound");
        }
    }

    public AggregatePAreaTaxonomyDerivation parseAggregatePAreaTaxonomyDerivation(JSONObject obj) throws AbNParseException {
        
        if(!obj.containsKey("BaseDerivation")) {
            // TODO: Error
        }

        if(!obj.containsKey("Bound")) {
            // TODO: Error
        }
        
        PAreaTaxonomyDerivation base = (PAreaTaxonomyDerivation)this.coreParser((JSONObject)obj.get("BaseDerivation"));
        
        try {
            int minBound = Integer.parseInt(obj.get("Bound").toString());
            
            return new AggregatePAreaTaxonomyDerivation(base, minBound);
        } catch (NumberFormatException nfe) {
            throw new AbNParseException("Incorrectly formatted aggregate bound");
        }
    }

    public AggregateAncestorSubtaxonomyDerivation parseAggregateAncestorSubtaxonomyDerivation(JSONObject obj) throws AbNParseException {
        
        if(!obj.containsKey("BaseDerivation")) {
            // TODO: Error
        }
        
        if(!obj.containsKey("ConceptID")) {
            // TODO: Error
        }
        
        if(!obj.containsKey("Bound")) {
            // TODO: Error
        }
        
        PAreaTaxonomyDerivation base = (PAreaTaxonomyDerivation)this.coreParser((JSONObject)obj.get("BaseDerivation"));

        String conceptID = obj.get("ConceptID").toString();
        
        Set<Concept> root = conceptFactory.getConceptsFromIds(new ArrayList<>(Arrays.asList(conceptID)));
        
        if(root.isEmpty()) {
            // TODO: Error
        }
        
        try {
            int minBound = Integer.parseInt(obj.get("Bound").toString());
            
            return new AggregateAncestorSubtaxonomyDerivation(base, minBound, root.iterator().next());
        } catch (NumberFormatException nfe) {
            throw new AbNParseException("Incorrectly formatted aggregate bound");
        }
    }

    public SimpleDisjointAbNDerivation parseSimpleDisjointAbNDerivation(JSONObject obj) throws AbNParseException {
        
        if(!obj.containsKey("BaseDerivation")) {
            // TODO: Error
        }
        
        if(!obj.containsKey("RootIDs")) {
            // TODO: Error
        }
        
        AbNDerivation base = this.coreParser((JSONObject)obj.get("BaseDerivation"));
        
        JSONArray rootIdsJSON = (JSONArray)obj.get("RootIDs");
        
        ArrayList<String> rootIdStrs = new ArrayList<>();
        rootIdsJSON.forEach( (rootIdJSON) -> {
            rootIdStrs.add(rootIdJSON.toString());
        });
        
        Set<Concept> roots = conceptFactory.getConceptsFromIds(rootIdStrs);
                
        if(roots.isEmpty()) {
            // TODO: Error
        }
        
        if(roots.size() != rootIdStrs.size()) {
            // TODO: Warning?
        }

        return new SimpleDisjointAbNDerivation(testing.getDisjointPAreaAbNFactory(), base, roots);
    }

    public  SubsetDisjointAbNDerivation parseSubsetDisjointAbNDerivation(JSONObject obj) throws AbNParseException {
       
        if(!obj.containsKey("BaseDerivation")) {
            // TODO: Error
        }

        DisjointAbNDerivation sourceDisjointAbNDerivation = (DisjointAbNDerivation)this.coreParser((JSONObject)obj.get("BaseDerivation"));
        
        Set<SinglyRootedNode> subsSet = null;
        JSONObject rootObj = findJSONObjectByName(jsonArr, "RootNodeNames");
        ArrayList<String> rootNodeNames = (ArrayList<String>) rootObj.get("RootNodeNames");

        for (String name : rootNodeNames) {
            try {
                subsSet.addAll(sourceDisjointAbNDerivation.getAbstractionNetwork().searchNodes(name));
            } catch (Exception e) {
                System.err.println("Cannot find node: " + name + " Fail at getting node from sourceDisjointAbNDerivation!!!");
            }
        }

        SubsetDisjointAbNDerivation result = new SubsetDisjointAbNDerivation(sourceDisjointAbNDerivation, subsSet);
        return result;
    }

    public <T extends AbNDerivation> OverlappingNodeDisjointAbNDerivation parseOverlappingNodeDisjointAbNDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing, T... baseDerivation) {
        DisjointAbNDerivation sourceDisjointAbNDerivation = null;
        if (baseDerivation == null) {
            JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
            JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
            sourceDisjointAbNDerivation = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);
        } else {
            sourceDisjointAbNDerivation = (DisjointAbNDerivation) baseDerivation[0];
        }

        //how to deserialize this??
        SinglyRootedNode overlappingNode = null;
        JSONObject rootObj = findJSONObjectByName(jsonArr, "NodeName");
        String nodeName = (String) rootObj.get("NodeName");

        try {
            overlappingNode = (SinglyRootedNode) sourceDisjointAbNDerivation.getAbstractionNetwork().searchNodes(nodeName).iterator().next();
        } catch (Exception e) {
            System.err.println("Cannot find node: " + nodeName + " Fail at getting node from sourceDisjointAbNDerivation!!!");
        }
        return new OverlappingNodeDisjointAbNDerivation(sourceDisjointAbNDerivation, overlappingNode);
    }

    public <T extends AbNDerivation> ExpandedDisjointAbNDerivation parseExpandedDisjointAbNDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing, T... baseDerivation) {
        DisjointAbNDerivation sourceDisjointAbNDerivation = null;
        if (baseDerivation == null) {
            JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
            JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
            sourceDisjointAbNDerivation = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);
        } else {
            sourceDisjointAbNDerivation = (DisjointAbNDerivation) baseDerivation[0];
        }

        Concept root = null;

        JSONObject rootObj = findJSONObjectByName(jsonArr, "ConceptID");

        ArrayList<String> conceptIDs = (ArrayList<String>) rootObj.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(conceptIDs);
            System.out.println("conceptFactory:  " + newRoot.toString());
            root = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
            e.printStackTrace();
        }

        return new ExpandedDisjointAbNDerivation(sourceDisjointAbNDerivation, root);
    }

    public <T extends AbNDerivation> AncestorDisjointAbNDerivation parseAncestorDisjointAbNDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing, T... baseDerivation) {
        DisjointAbNDerivation sourceDisjointAbNDerivation = null;
        if (baseDerivation == null) {
            JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
            JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
            sourceDisjointAbNDerivation = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);
        } else {
            sourceDisjointAbNDerivation = (DisjointAbNDerivation) baseDerivation[0];
        }

        Concept disjointNodeRoot = null;

        JSONObject rootObj = findJSONObjectByName(jsonArr, "ConceptID");

        ArrayList<String> conceptIDs = (ArrayList<String>) rootObj.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(conceptIDs);
            System.out.println("conceptFactory:  " + newRoot.toString());
            disjointNodeRoot = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
            e.printStackTrace();
        }

        return new AncestorDisjointAbNDerivation(sourceDisjointAbNDerivation, disjointNodeRoot);
    }

    public <T extends AbNDerivation> AggregateDisjointAbNDerivation parseAggregateDisjointAbNDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing, T... baseDerivation) {
        DisjointAbNDerivation nonAggregateDerivation = null;
        if (baseDerivation == null) {
            JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
            JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
            nonAggregateDerivation = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);
        } else {
            nonAggregateDerivation = (DisjointAbNDerivation) baseDerivation[0];
        }

        JSONObject boundObject = findJSONObjectByName(jsonArr, "Bound");
        int aggregateBound = ((Long) boundObject.get("Bound")).intValue();

        return new AggregateDisjointAbNDerivation(nonAggregateDerivation, aggregateBound);
    }

    public <T extends AbNDerivation> AggregateAncestorDisjointAbNDerivation parseAggregateAncestorTANDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing, T... baseDerivation) {
        DisjointAbNDerivation aggregateBase = null;
        if (baseDerivation == null) {
            JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
            JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
            aggregateBase = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);
        } else {
            aggregateBase = (DisjointAbNDerivation) baseDerivation[0];
        }

        JSONObject boundObject = findJSONObjectByName(jsonArr, "Bound");
        int minBound = ((Long) boundObject.get("Bound")).intValue();

        Concept selectedAggregatePAreaRoot = null;
        JSONObject rootObj = findJSONObjectByName(jsonArr, "ConceptID");
        ArrayList<String> conceptIDs = (ArrayList<String>) rootObj.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(conceptIDs);
            System.out.println("conceptFactory:  " + newRoot.toString());
            selectedAggregatePAreaRoot = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
            e.printStackTrace();
        }

        return new AggregateAncestorDisjointAbNDerivation(aggregateBase, minBound, selectedAggregatePAreaRoot);
    }

    //Tan parser
    public SimpleClusterTANDerivation parseSimpleClusterTANDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing) {

        Set<Concept> root = null;

        JSONObject rootObj = findJSONObjectByName(jsonArr, "ConceptIDs");

        ArrayList<String> conceptIDs = (ArrayList<String>) rootObj.get("ConceptIDs");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(conceptIDs);
            System.out.println("conceptFactory:  " + newRoot.toString());
            root = newRoot;
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
            e.printStackTrace();
        }

        return new SimpleClusterTANDerivation(root, sourceOntology, testing.getTANFactory());
    }

    public <T extends AbNDerivation> RootSubTANDerivation parseRootSubTANDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing, T... baseDerivation) {
        ClusterTANDerivation base = null;
        if (baseDerivation == null) {
            JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
            JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
            base = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);
        } else {
            base = (ClusterTANDerivation) baseDerivation[0];
        }

        Concept clusterRootConcept = null;

        JSONObject conceptObject = findJSONObjectByName(jsonArr, "ConceptID");
        String conceptID = (String) conceptObject.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(new ArrayList<String>(Arrays.asList(conceptID)));
            System.out.println("conceptFactory:  " + newRoot.toString());
            clusterRootConcept = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
            e.printStackTrace();
        }

        return new RootSubTANDerivation(base, clusterRootConcept);
    }

    public <T extends AbNDerivation> ExpandedSubTANDerivation parseExpandedSubTANDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing, T... baseDerivation) {
        ClusterTANDerivation base = null;
        if (baseDerivation == null) {
            JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
            JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
            base = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);
        } else {
            base = (ClusterTANDerivation) baseDerivation[0];
        }

        Concept aggregateClusterRoot = null;

        JSONObject conceptObject = findJSONObjectByName(jsonArr, "ConceptID");
        String conceptID = (String) conceptObject.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(new ArrayList<String>(Arrays.asList(conceptID)));
            System.out.println("conceptFactory:  " + newRoot.toString());
            aggregateClusterRoot = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
            e.printStackTrace();
        }
        return new ExpandedSubTANDerivation(base, aggregateClusterRoot);
    }

    public <T extends AbNDerivation> AncestorSubTANDerivation parseAncestorSubTANDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing, T... baseDerivation) {
        ClusterTANDerivation base = null;
        if (baseDerivation == null) {
            JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
            JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
            base = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);
        } else {
            base = (ClusterTANDerivation) baseDerivation[0];
        }

        Concept clusterRootConcept = null;

        JSONObject conceptObject = findJSONObjectByName(jsonArr, "ConceptID");
        String conceptID = (String) conceptObject.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(new ArrayList<String>(Arrays.asList(conceptID)));
            System.out.println("conceptFactory:  " + newRoot.toString());
            clusterRootConcept = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
            e.printStackTrace();
        }

        return new AncestorSubTANDerivation(base, clusterRootConcept);
    }

    public <T extends AbNDerivation> AggregateTANDerivation parseAggregateTANDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing, T... baseDerivation) {
        ClusterTANDerivation base = null;
        if (baseDerivation == null || baseDerivation.length == 0) {
            JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
            JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
            base = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);
        } else {
            System.out.println("base: " + baseDerivation.toString());
            base = (ClusterTANDerivation) baseDerivation[0];
        }

        JSONObject boundObject = findJSONObjectByName(jsonArr, "Bound");
        int bound = ((Long) boundObject.get("Bound")).intValue();

        return new AggregateTANDerivation(base, bound);
    }

    public <T extends AbNDerivation> AggregateRootSubTANDerivation parseAggregateRootSubTANDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing, T... baseDerivation) {
        ClusterTANDerivation aggregateBase = null;
        if (baseDerivation == null) {
            JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
            JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
            aggregateBase = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);
        } else {
            aggregateBase = (ClusterTANDerivation) baseDerivation[0];
        }

        JSONObject boundObject = findJSONObjectByName(jsonArr, "Bound");
        int minBound = ((Long) boundObject.get("Bound")).intValue();

        Concept selectedAggregateClusterRoot = null;
        JSONObject conceptObject = findJSONObjectByName(jsonArr, "ConceptID");
        String conceptID = (String) conceptObject.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(new ArrayList<String>(Arrays.asList(conceptID)));
            System.out.println("conceptFactory:  " + newRoot.toString());
            selectedAggregateClusterRoot = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
            e.printStackTrace();
        }

        return new AggregateRootSubTANDerivation(aggregateBase, minBound, selectedAggregateClusterRoot);
    }

    public <T extends AbNDerivation> AggregateAncestorSubTANDerivation parseAggregateAncestorSubTANDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing, T... baseDerivation) {
        ClusterTANDerivation aggregateBase = null;
        if (baseDerivation == null) {
            JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
            JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
            aggregateBase = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);
        } else {
            aggregateBase = (ClusterTANDerivation) baseDerivation[0];
        }

        JSONObject boundObject = findJSONObjectByName(jsonArr, "Bound");
        int minBound = ((Long) boundObject.get("Bound")).intValue();

        Concept selectedAggregateClusterRoot = null;
        JSONObject conceptObject = findJSONObjectByName(jsonArr, "ConceptID");
        String conceptID = (String) conceptObject.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(new ArrayList<String>(Arrays.asList(conceptID)));
            System.out.println("conceptFactory:  " + newRoot.toString());
            selectedAggregateClusterRoot = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
            e.printStackTrace();
        }

        return new AggregateAncestorSubTANDerivation(aggregateBase, minBound, selectedAggregateClusterRoot);
    }

    public <T extends AbNDerivation> TANFromPartitionedNodeDerivation parseTANFromPartitionedNodeDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing, T... baseDerivation) {

        // how to deserialize this??
        AbNDerivation parentAbNDerivation = null;
        if (baseDerivation == null) {
            JSONObject jsonObject = findJSONObjectByName(jsonArr, "ParentDerivation");
            JSONArray arr_base = (JSONArray) jsonObject.get("ParentDerivation");
            parentAbNDerivation = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);
        } else {
            parentAbNDerivation = baseDerivation[0];
        }

        // how to deserialize parentAbNderivation and node???
        PartitionedNode node = null;
        JSONObject nodeObj = findJSONObjectByName(jsonArr, "NodeName");
        String nodeName = (String) nodeObj.get("NodeName");

        try {
            node = (PartitionedNode) parentAbNDerivation.getAbstractionNetwork().searchNodes(nodeName).iterator().next();
        } catch (Exception e) {
            System.err.println("Cannot find node: " + nodeName + " Fail at getting node from sourceDisjointAbNDerivation!!!");
        }

        return new TANFromPartitionedNodeDerivation(parentAbNDerivation, testing.getTANFactory(), node);
    }

    public <T extends AbNDerivation> TANFromSinglyRootedNodeDerivation parseTANFromSinglyRootedNodeDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing, T... baseDerivation) {

        // how to deserialize this??
        AbNDerivation parentAbNDerivation = null;
        if (baseDerivation == null) {
            JSONObject jsonObject = findJSONObjectByName(jsonArr, "ParentDerivation");
            JSONArray arr_base = (JSONArray) jsonObject.get("ParentDerivation");
            parentAbNDerivation = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);
        } else {
            parentAbNDerivation = baseDerivation[0];
        }

        Concept nodeRoot = null;
        JSONObject conceptObject = findJSONObjectByName(jsonArr, "ConceptID");
        String conceptID = (String) conceptObject.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(new ArrayList<String>(Arrays.asList(conceptID)));
            System.out.println("conceptFactory:  " + newRoot.toString());
            nodeRoot = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
            e.printStackTrace();
        }

        return new TANFromSinglyRootedNodeDerivation(parentAbNDerivation, testing.getTANFactory(), nodeRoot);
    }

    //Target Parser
    public <T extends AbNDerivation> TargetAbNDerivation parseTargetAbNDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing, T... baseDerivation) {

        Concept sourceHierarchyRoot = null;
        JSONObject sourceObject = findJSONObjectByName(jsonArr, "SourceRootID");
        String sourceConceptID = (String) sourceObject.get("SourceRootID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(new ArrayList<String>(Arrays.asList(sourceConceptID)));
            System.out.println("conceptFactory:  " + newRoot.toString());
            sourceHierarchyRoot = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
            e.printStackTrace();
        }

        InheritableProperty selectedProperty = null;
        JSONObject propertyObject = findJSONObjectByName(jsonArr, "PropertyID");
        String propertyID = (String) propertyObject.get("PropertyID");
        Set<InheritableProperty> foundProperty = propertyFactory.getPropertiesFromIds(new ArrayList<String>(Arrays.asList(propertyID)));
        selectedProperty = foundProperty.stream().findAny().get();

        Concept targetHierarchyRoot = null;
        JSONObject targetObject = findJSONObjectByName(jsonArr, "TargetRootID");
        String targetConceptID = (String) targetObject.get("TargetRootID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(new ArrayList<String>(Arrays.asList(targetConceptID)));
            System.out.println("conceptFactory:  " + newRoot.toString());
            targetHierarchyRoot = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
            e.printStackTrace();
        }

        return new TargetAbNDerivation(sourceOntology, testing.getTargetAbNFactory(), sourceHierarchyRoot, selectedProperty, targetHierarchyRoot);
    }

    public <T extends AbNDerivation> ExpandedTargetAbNDerivation parseExpandedTargetAbNDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing, T... baseDerivation) {

        TargetAbNDerivation base = null;
        if (baseDerivation == null) {
            JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
            JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
            base = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);
        } else {
            base = (TargetAbNDerivation) baseDerivation[0];
        }

        Concept aggregateTargetGroupRoot = null;
        JSONObject conceptObject = findJSONObjectByName(jsonArr, "ConceptID");
        String conceptID = (String) conceptObject.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(new ArrayList<String>(Arrays.asList(conceptID)));
            System.out.println("conceptFactory:  " + newRoot.toString());
            aggregateTargetGroupRoot = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
            e.printStackTrace();
        }

        return new ExpandedTargetAbNDerivation(base, aggregateTargetGroupRoot);
    }

    public <T extends AbNDerivation> AggregateTargetAbNDerivation parseAggregateTargetAbNDerivation(
            JSONArray jsonArr, 
            Ontology sourceOntology, 
            ConceptLocationDataFactory conceptFactory, 
            PropertyLocationDataFactory propertyFactory, 
            AbNDerivationFactoryTesting testing, 
            T... baseDerivation) {
        
        TargetAbNDerivation nonAggregateSource = null;
        
        if (baseDerivation == null) {
            JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
            JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
            
            nonAggregateSource = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);
        } else {
            nonAggregateSource = (TargetAbNDerivation) baseDerivation[0];
        }

        JSONObject boundObject = findJSONObjectByName(jsonArr, "Bound");
        int bound = ((Long) boundObject.get("Bound")).intValue();

        return new AggregateTargetAbNDerivation(nonAggregateSource, bound);
    }
}
