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
 * @param <O>
 * @param <C>
 * @param <P>
 * @param <A>
 */
public class AbNDerivationCoreParser<O extends Ontology, C extends ConceptLocationDataFactory, P extends PropertyLocationDataFactory, A extends AbNDerivationFactoryTesting> {

    private O sourceOntology;
    private C conceptFactory;
    private P propertyFactory;
    private A testing;

    public AbNDerivationCoreParser() {
    }

    public AbNDerivationCoreParser(O ontology, C conceptFactory, P propertyFactory, A testing) {
        this.sourceOntology = ontology;
        this.conceptFactory = conceptFactory;
        this.propertyFactory = propertyFactory;
        this.testing = testing;

    }

    public <T extends AbNDerivation> T coreParser(JSONArray jsonArr) throws Exception {

        JSONObject jsonObject = findJSONObjectByName(jsonArr, "ClassName");
        String className = (String) jsonObject.get("ClassName");
        T result = null;

        if (className.equalsIgnoreCase("SimplePAreaTaxonomyDerivation")) {
            result = (T) parseSimplePAreaTaxonomyDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("RootSubtaxonomyDerivation")) {
            result = (T) parseRootSubtaxonomyDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("RelationshipSubtaxonomyDerivation")) {
            result = (T) parseRelationshipSubtaxonomyDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("ExpandedSubtaxonomyDerivation")) {
            result = (T) parseExpandedSubtaxonomyDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("AncestorSubtaxonomyDerivation")) {
            result = (T) parseAncestorSubtaxonomyDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("AggregateRootSubtaxonomyDerivation")) {
            result = (T) parseAggregateRootSubtaxonomyDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("AggregatePAreaTaxonomyDerivation")) {
            result = (T) parseAggregatePAreaTaxonomyDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("AggregateAncestorSubtaxonomyDerivation")) {
            result = (T) parseAggregateAncestorSubtaxonomyDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("SimpleDisjointAbNDerivation")) {
            result = (T) parseSimpleDisjointAbNDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("SubsetDisjointAbNDerivation")) {
            result = (T) parseSubsetDisjointAbNDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("OverlappingNodeDisjointAbNDerivation")) {
            result = (T) parseOverlappingNodeDisjointAbNDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("ExpandedDisjointAbNDerivation")) {
            result = (T) parseExpandedDisjointAbNDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("AncestorDisjointAbNDerivation")) {
            result = (T) parseAncestorDisjointAbNDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("AggregateDisjointAbNDerivation")) {
            result = (T) parseAggregateDisjointAbNDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("AggregateAncestorTANDerivation")) {
            result = (T) parseAggregateAncestorTANDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("SimpleClusterTANDerivation")) {
            result = (T) parseSimpleClusterTANDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("RootSubTANDerivation")) {
            result = (T) parseRootSubTANDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("ExpandedSubTANDerivation")) {
            result = (T) parseExpandedSubTANDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("AncestorSubTANDerivation")) {
            result = (T) parseAncestorSubTANDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("AggregateTANDerivation")) {
            result = (T) parseAggregateTANDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("AggregateRootSubTANDerivation")) {
            result = (T) parseAggregateRootSubTANDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("AggregateAncestorSubTANDerivation")) {
            result = (T) parseAggregateAncestorSubTANDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("TANFromPartitionedNodeDerivation")) {
            result = (T) parseTANFromPartitionedNodeDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("TANFromSinglyRootedNodeDerivation")) {
            result = (T) parseTANFromSinglyRootedNodeDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        }

        return result;

    }

    public <T extends AbNDerivation> T coreParser(JSONArray jsonArr, O sourceOntology, C conceptFactory, P propertyFactory, A testing) {

        JSONObject jsonObject = findJSONObjectByName(jsonArr, "ClassName");
        String className = (String) jsonObject.get("ClassName");
        T result = null;

        if (className.equalsIgnoreCase("SimplePAreaTaxonomyDerivation")) {
            result = (T) parseSimplePAreaTaxonomyDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("RootSubtaxonomyDerivation")) {
            result = (T) parseRootSubtaxonomyDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("RelationshipSubtaxonomyDerivation")) {
            result = (T) parseRelationshipSubtaxonomyDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("ExpandedSubtaxonomyDerivation")) {
            result = (T) parseExpandedSubtaxonomyDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("AncestorSubtaxonomyDerivation")) {
            result = (T) parseAncestorSubtaxonomyDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("AggregateRootSubtaxonomyDerivation")) {
            result = (T) parseAggregateRootSubtaxonomyDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("AggregatePAreaTaxonomyDerivation")) {
            result = (T) parseAggregatePAreaTaxonomyDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("AggregateAncestorSubtaxonomyDerivation")) {
            result = (T) parseAggregateAncestorSubtaxonomyDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("SimpleDisjointAbNDerivation")) {
            result = (T) parseSimpleDisjointAbNDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("SubsetDisjointAbNDerivation")) {
            result = (T) parseSubsetDisjointAbNDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("OverlappingNodeDisjointAbNDerivation")) {
            result = (T) parseOverlappingNodeDisjointAbNDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("ExpandedDisjointAbNDerivation")) {
            result = (T) parseExpandedDisjointAbNDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("AncestorDisjointAbNDerivation")) {
            result = (T) parseAncestorDisjointAbNDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("AggregateDisjointAbNDerivation")) {
            result = (T) parseAggregateDisjointAbNDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("AggregateAncestorTANDerivation")) {
            result = (T) parseAggregateAncestorTANDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("SimpleClusterTANDerivation")) {
            result = (T) parseSimpleClusterTANDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("RootSubTANDerivation")) {
            result = (T) parseRootSubTANDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("ExpandedSubTANDerivation")) {
            result = (T) parseExpandedSubTANDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("AncestorSubTANDerivation")) {
            result = (T) parseAncestorSubTANDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("AggregateTANDerivation")) {
            result = (T) parseAggregateTANDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("AggregateRootSubTANDerivation")) {
            result = (T) parseAggregateRootSubTANDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("AggregateAncestorSubTANDerivation")) {
            result = (T) parseAggregateAncestorSubTANDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("TANFromPartitionedNodeDerivation")) {
            result = (T) parseTANFromPartitionedNodeDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("TANFromSinglyRootedNodeDerivation")) {
            result = (T) parseTANFromSinglyRootedNodeDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        }

        return result;

    }

    // parea parser
    public SimplePAreaTaxonomyDerivation parseSimplePAreaTaxonomyDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing) {

        Concept root = null;

        JSONObject rootObj = findJSONObjectByName(jsonArr, "ConceptID");
        String conceptID = (String) rootObj.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(new ArrayList<String>(Arrays.asList(conceptID)));
            System.out.println("conceptFactory:  " + newRoot.toString());
            root = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
        }

        SimplePAreaTaxonomyDerivation result = new SimplePAreaTaxonomyDerivation(sourceOntology, root, testing.getPAreaTaxonomyFactory());
        return result;
    }

    public RootSubtaxonomyDerivation parseRootSubtaxonomyDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing) {

        JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
        JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
        PAreaTaxonomyDerivation base = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);

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

    public RelationshipSubtaxonomyDerivation parseRelationshipSubtaxonomyDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing) {

        JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
        JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
        PAreaTaxonomyDerivation base = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);

        //implement getSelectedProperties by id need testing on this.
        Set<InheritableProperty> selectedProperties = null;
        JSONObject propertyObject = findJSONObjectByName(jsonArr, "Properties");
        ArrayList<String> arr_property = (ArrayList<String>) propertyObject.get("Properties");
        selectedProperties = propertyFactory.getPropertiesFromIds(arr_property);

        RelationshipSubtaxonomyDerivation result = new RelationshipSubtaxonomyDerivation(base, selectedProperties);
        return result;
    }

    public ExpandedSubtaxonomyDerivation parseExpandedSubtaxonomyDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing) {
        JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
        JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
        PAreaTaxonomyDerivation base = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);

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

    public AncestorSubtaxonomyDerivation parseAncestorSubtaxonomyDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing) {

        JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
        JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
        PAreaTaxonomyDerivation base = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);

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

    public AggregateRootSubtaxonomyDerivation parseAggregateRootSubtaxonomyDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing) {
        JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
        JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
        PAreaTaxonomyDerivation aggregateBase = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);

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
        return result;
    }

    public AggregatePAreaTaxonomyDerivation parseAggregatePAreaTaxonomyDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing) {
        JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
        JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
        PAreaTaxonomyDerivation nonAggregateSourceDerivation = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);

        JSONObject boundObject = findJSONObjectByName(jsonArr, "Bound");
        int bound = (int) boundObject.get("Bound");

        AggregatePAreaTaxonomyDerivation result = new AggregatePAreaTaxonomyDerivation(nonAggregateSourceDerivation, bound);
        return result;
    }

    public AggregateAncestorSubtaxonomyDerivation parseAggregateAncestorSubtaxonomyDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing) {

        JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
        JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
        PAreaTaxonomyDerivation aggregateBase = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);

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

    //disjoint parser
    public SimpleDisjointAbNDerivation parseSimpleDisjointAbNDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing) {

        JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
        JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
        DisjointAbNDerivation parentAbNDerivation = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);

        Set<Concept> root = null;

        JSONObject rootObj = findJSONObjectByName(jsonArr, "RootIDs");

        ArrayList<String> conceptIDs = (ArrayList<String>) rootObj.get("RootIDs");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(conceptIDs);
            System.out.println("conceptFactory:  " + newRoot.toString());
            root = newRoot;
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
        }
        SimpleDisjointAbNDerivation result = new SimpleDisjointAbNDerivation(testing.getDisjointPAreaAbNFactory(), parentAbNDerivation, root);
        return result;
    }

    public SubsetDisjointAbNDerivation parseSubsetDisjointAbNDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing) {

        JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
        JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
        DisjointAbNDerivation sourceDisjointAbNDerivation = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);

        //how to deserialize this??
        Set<SinglyRootedNode> subsSet = null;

        JSONObject rootObj = findJSONObjectByName(jsonArr, "RootNodeNames");

        ArrayList<String> rootNodeNames = (ArrayList<String>) rootObj.get("RootNodeNames");

        rootNodeNames.forEach((String name) -> {
            try {
                subsSet.addAll(sourceDisjointAbNDerivation.getAbstractionNetwork().searchNodes(name));
            } catch (Exception e) {
                System.err.println("Cannot find node: " + name + " Fail at getting node from sourceDisjointAbNDerivation!!!");
            }
        });

        SubsetDisjointAbNDerivation result = new SubsetDisjointAbNDerivation(sourceDisjointAbNDerivation, subsSet);
        return result;
    }

    public OverlappingNodeDisjointAbNDerivation parseOverlappingNodeDisjointAbNDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing) {

        JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
        JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
        DisjointAbNDerivation sourceDisjointAbNDerivation = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);

        //how to deserialize this??
        SinglyRootedNode overlappingNode = null;

        JSONObject rootObj = findJSONObjectByName(jsonArr, "NodeName");

        String nodeName = (String) rootObj.get("NodeName");

        try {
            overlappingNode = (SinglyRootedNode) sourceDisjointAbNDerivation.getAbstractionNetwork().searchNodes(nodeName).iterator().next();
        } catch (Exception e) {
            System.err.println("Cannot find node: " + nodeName + " Fail at getting node from sourceDisjointAbNDerivation!!!");
        }

        OverlappingNodeDisjointAbNDerivation result = new OverlappingNodeDisjointAbNDerivation(sourceDisjointAbNDerivation, overlappingNode);
        return result;
    }

    public ExpandedDisjointAbNDerivation parseExpandedDisjointAbNDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing) {

        JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
        JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
        DisjointAbNDerivation sourceDisjointAbNDerivation = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);

        Concept root = null;

        JSONObject rootObj = findJSONObjectByName(jsonArr, "ConceptID");

        ArrayList<String> conceptIDs = (ArrayList<String>) rootObj.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(conceptIDs);
            System.out.println("conceptFactory:  " + newRoot.toString());
            root = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
        }

        ExpandedDisjointAbNDerivation result = new ExpandedDisjointAbNDerivation(sourceDisjointAbNDerivation, root);
        return result;
    }

    public AncestorDisjointAbNDerivation parseAncestorDisjointAbNDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing) {

        JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
        JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
        DisjointAbNDerivation sourceDisjointAbNDerivation = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);

        Concept disjointNodeRoot = null;

        JSONObject rootObj = findJSONObjectByName(jsonArr, "ConceptID");

        ArrayList<String> conceptIDs = (ArrayList<String>) rootObj.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(conceptIDs);
            System.out.println("conceptFactory:  " + newRoot.toString());
            disjointNodeRoot = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
        }

        AncestorDisjointAbNDerivation result = new AncestorDisjointAbNDerivation(sourceDisjointAbNDerivation, disjointNodeRoot);
        return result;
    }

    public AggregateDisjointAbNDerivation parseAggregateDisjointAbNDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing) {

        JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
        JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
        DisjointAbNDerivation nonAggregateDerivation = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);

        JSONObject boundObject = findJSONObjectByName(jsonArr, "Bound");
        int aggregateBound = (int) boundObject.get("Bound");

        AggregateDisjointAbNDerivation result = new AggregateDisjointAbNDerivation(nonAggregateDerivation, aggregateBound);
        return result;
    }

    public AggregateAncestorDisjointAbNDerivation parseAggregateAncestorTANDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing) {

        JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
        JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
        DisjointAbNDerivation aggregateBase = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);

        JSONObject boundObject = findJSONObjectByName(jsonArr, "Bound");
        int minBound = (int) boundObject.get("Bound");

        Concept selectedAggregatePAreaRoot = null;

        JSONObject rootObj = findJSONObjectByName(jsonArr, "ConceptID");

        ArrayList<String> conceptIDs = (ArrayList<String>) rootObj.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(conceptIDs);
            System.out.println("conceptFactory:  " + newRoot.toString());
            selectedAggregatePAreaRoot = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
        }

        AggregateAncestorDisjointAbNDerivation result = new AggregateAncestorDisjointAbNDerivation(aggregateBase, minBound, selectedAggregatePAreaRoot);
        return result;

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
        }

        SimpleClusterTANDerivation result = new SimpleClusterTANDerivation(root, sourceOntology, testing.getTANFactory());
        return result;
    }

    public RootSubTANDerivation parseRootSubTANDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing) {

        JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
        JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
        ClusterTANDerivation base = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);

        Concept clusterRootConcept = null;

        JSONObject conceptObject = findJSONObjectByName(jsonArr, "ConceptID");
        String conceptID = (String) conceptObject.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(new ArrayList<String>(Arrays.asList(conceptID)));
            System.out.println("conceptFactory:  " + newRoot.toString());
            clusterRootConcept = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
        }

        RootSubTANDerivation result = new RootSubTANDerivation(base, clusterRootConcept);
        return result;
    }

    public ExpandedSubTANDerivation parseExpandedSubTANDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing) {

        JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
        JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
        ClusterTANDerivation base = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);

        Concept aggregateClusterRoot = null;

        JSONObject conceptObject = findJSONObjectByName(jsonArr, "ConceptID");
        String conceptID = (String) conceptObject.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(new ArrayList<String>(Arrays.asList(conceptID)));
            System.out.println("conceptFactory:  " + newRoot.toString());
            aggregateClusterRoot = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
        }

        ExpandedSubTANDerivation result = new ExpandedSubTANDerivation(base, aggregateClusterRoot);
        return result;
    }

    public AncestorSubTANDerivation parseAncestorSubTANDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing) {

        JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
        JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
        ClusterTANDerivation base = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);

        Concept clusterRootConcept = null;

        JSONObject conceptObject = findJSONObjectByName(jsonArr, "ConceptID");
        String conceptID = (String) conceptObject.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(new ArrayList<String>(Arrays.asList(conceptID)));
            System.out.println("conceptFactory:  " + newRoot.toString());
            clusterRootConcept = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
        }

        AncestorSubTANDerivation result = new AncestorSubTANDerivation(base, clusterRootConcept);
        return result;
    }

    public AggregateTANDerivation parseAggregateTANDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing) {

        JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
        JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
        ClusterTANDerivation base = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);

        JSONObject boundObject = findJSONObjectByName(jsonArr, "Bound");
        int bound = (int) boundObject.get("Bound");

        AggregateTANDerivation result = new AggregateTANDerivation(base, bound);
        return result;
    }

    public AggregateRootSubTANDerivation parseAggregateRootSubTANDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing) {

        JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
        JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
        ClusterTANDerivation aggregateBase = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);

        JSONObject boundObject = findJSONObjectByName(jsonArr, "Bound");
        int minBound = (int) boundObject.get("Bound");

        Concept selectedAggregateClusterRoot = null;
        JSONObject conceptObject = findJSONObjectByName(jsonArr, "ConceptID");
        String conceptID = (String) conceptObject.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(new ArrayList<String>(Arrays.asList(conceptID)));
            System.out.println("conceptFactory:  " + newRoot.toString());
            selectedAggregateClusterRoot = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
        }

        AggregateRootSubTANDerivation result = new AggregateRootSubTANDerivation(aggregateBase, minBound, selectedAggregateClusterRoot);
        return result;
    }

    public AggregateAncestorSubTANDerivation parseAggregateAncestorSubTANDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing) {

        JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
        JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
        ClusterTANDerivation aggregateBase = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);

        JSONObject boundObject = findJSONObjectByName(jsonArr, "Bound");
        int minBound = (int) boundObject.get("Bound");

        Concept selectedAggregateClusterRoot = null;
        JSONObject conceptObject = findJSONObjectByName(jsonArr, "ConceptID");
        String conceptID = (String) conceptObject.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(new ArrayList<String>(Arrays.asList(conceptID)));
            System.out.println("conceptFactory:  " + newRoot.toString());
            selectedAggregateClusterRoot = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
        }

        AggregateAncestorSubTANDerivation result = new AggregateAncestorSubTANDerivation(aggregateBase, minBound, selectedAggregateClusterRoot);
        return result;
    }

    public TANFromPartitionedNodeDerivation parseTANFromPartitionedNodeDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing) {

        // how to deserialize this??
        AbNDerivation parentAbNDerivation = null;
        JSONObject jsonObject = findJSONObjectByName(jsonArr, "ParentDerivation");
        JSONArray arr_base = (JSONArray) jsonObject.get("ParentDerivation");
        parentAbNDerivation = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);

        // how to deserialize parentAbNderivation and node???
        PartitionedNode node = null;
        JSONObject nodeObj = findJSONObjectByName(jsonArr, "NodeName");
        String nodeName = (String) nodeObj.get("NodeName");

        try {
            node = (PartitionedNode) parentAbNDerivation.getAbstractionNetwork().searchNodes(nodeName).iterator().next();
        } catch (Exception e) {
            System.err.println("Cannot find node: " + nodeName + " Fail at getting node from sourceDisjointAbNDerivation!!!");
        }

        TANFromPartitionedNodeDerivation result = new TANFromPartitionedNodeDerivation(parentAbNDerivation, testing.getTANFactory(), node);
        return result;

    }

    public TANFromSinglyRootedNodeDerivation parseTANFromSinglyRootedNodeDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing) {

        // how to deserialize this??
        AbNDerivation parentAbNDerivation = null;
        JSONObject jsonObject = findJSONObjectByName(jsonArr, "ParentDerivation");
        JSONArray arr_base = (JSONArray) jsonObject.get("ParentDerivation");
        parentAbNDerivation = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);

        Concept nodeRoot = null;
        JSONObject conceptObject = findJSONObjectByName(jsonArr, "ConceptID");
        String conceptID = (String) conceptObject.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(new ArrayList<String>(Arrays.asList(conceptID)));
            System.out.println("conceptFactory:  " + newRoot.toString());
            nodeRoot = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
        }

        TANFromSinglyRootedNodeDerivation result = new TANFromSinglyRootedNodeDerivation(parentAbNDerivation, testing.getTANFactory(), nodeRoot);
        return result;
    }

    public static JSONObject findJSONObjectByName(JSONArray jsonArr, String name) {

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
                Logger.getLogger(AbNDerivationCoreParser.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (jsonObject.containsKey(name)) {
                result = jsonObject;
                break;
            }

        }

        return result;
    }
}
