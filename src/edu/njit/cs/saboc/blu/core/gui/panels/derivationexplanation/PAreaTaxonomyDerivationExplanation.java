package edu.njit.cs.saboc.blu.core.gui.panels.derivationexplanation;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.core.gui.iconmanager.ImageManager;

/**
 *
 * @author Chris O
 */
public class PAreaTaxonomyDerivationExplanation extends AbNDerivationExplanation {
    
    
    public PAreaTaxonomyDerivationExplanation(PAreaTaxonomyConfiguration config) {
        super(config);
        
        super.addDerivationExplanationEntry(new AbNDerivationExplanationEntry(
                ImageManager.getImageManager().getDerivationImage("PAreaExample1.png"), 
                
                "Partial-area taxonomies summarize sets of concepts that are modeled "
                        + "with the same types of semantic relationships. "
                        + ""
                        + "For example, Meat Pizza and Pepperoni Pizza both have a Has Topping relationship."));
        
        super.addDerivationExplanationEntry(new AbNDerivationExplanationEntry(
                ImageManager.getImageManager().getDerivationImage("PAreaExample2.png"), 
                
                "Partial-area taxonomies are based on the source concepts of a relationship; target concepts are disregarded."));
        
        super.addDerivationExplanationEntry(new AbNDerivationExplanationEntry(
                ImageManager.getImageManager().getDerivationImage("PAreaExample3.png"), 
                
                "Sets of concepts that have the exact same types of semantic relationships are identified."));
        
        super.addDerivationExplanationEntry(new AbNDerivationExplanationEntry(
                ImageManager.getImageManager().getDerivationImage("PAreaExample4.png"), 
                
                "Subhierarchies of concepts that are modeled with the exact same types of semantic relationships are summarized "
                        + "by areas and partial-areas in a partial-area taxonomy."));
        
        super.addDerivationExplanationEntry(new AbNDerivationExplanationEntry(
                ImageManager.getImageManager().getDerivationImage("PAreaExample5.png"), 
                
                "There may be different types of semantic relationships in the same hierarchy. "
                        + ""
                        + "For example, this subhierarchy has both Has Crust and Has Topping semantic relationships."));
        
        super.addDerivationExplanationEntry(new AbNDerivationExplanationEntry(
                ImageManager.getImageManager().getDerivationImage("PAreaExample6.png"), 
                
                "Again, target concepts are disregarded and sets of concepts that are modeled with the same types of "
                        + "semantic relationships are identified."));
        
        super.addDerivationExplanationEntry(new AbNDerivationExplanationEntry(
                ImageManager.getImageManager().getDerivationImage("PAreaExample7.png"), 
                
                "Multiple areas with the same number of semantic relationship types may exist."));
        
        super.addDerivationExplanationEntry(new AbNDerivationExplanationEntry(
                ImageManager.getImageManager().getDerivationImage("PAreaExample8.png"), 
                
                "Concepts are often defined with multiple types of semantic relationships. For example, "
                        + "New York Style Pepperoni Pizza has both a Has Crust relationships and a Has Topping relationship."));
        
        
        super.addDerivationExplanationEntry(new AbNDerivationExplanationEntry(
                ImageManager.getImageManager().getDerivationImage("PAreaExample9.png"), 
                
                "New York Style Pepperoni Pizza is in a different area than its parent concepts, since it is modeled with an additional type of "
                        + "semantic relationship relative to each parent."));
    }
}
