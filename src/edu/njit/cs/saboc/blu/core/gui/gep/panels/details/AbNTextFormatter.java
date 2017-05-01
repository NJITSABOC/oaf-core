package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNTextConfiguration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AbNTextFormatter {
    
    private final AbNTextConfiguration AbNObj;
//    private HashMap<String, Boolean> hm;

    public AbNTextFormatter(AbNTextConfiguration AbNObj){
        this.AbNObj = AbNObj;
//        hm = new HashMap<String, Boolean>();
//        hm.put("<conceptTypeName>", false);
//        hm.put("<conceptTypeName count=2>", true );
//        hm.put("<propertyTypeName>", false);
//        hm.put("<propertyTypeName count=2>", true);
    }
    public String format(String text){
        if (text.length() == 0){
            return "";
        }
        
        String str = text;
//        int size = text.length();
//        //keep track of wildcard beginning and end index;
//        int begin = 0, end = 0;
//        for (int i = 0; i < size; i++) {
//            if (text.charAt(i) == '<') {
//                begin = i; 
//            }
//            if (text.charAt(i) == '>') {
//                end = i;
//                String substr = text.substring(begin, end + 1);
//                if(hm.containsKey(substr) ){
//                    str = str.replaceAll(substr, AbNObj.getOntologyEntityNameConfiguration().getConceptTypeName(hm.get(substr)).toLowerCase());
//                }
//                hm.remove(substr);
//                begin = 0;
//                end = 0;
//            }
//
//        }

        /*alternative*/
        
        str = str.replaceAll("<conceptTypeName count=(?:[2-9]|\\d\\d\\d*)>", AbNObj.getOntologyEntityNameConfiguration().getConceptTypeName(true).toLowerCase());    
        str = str.replaceAll("<conceptTypeName( count=1)?>", AbNObj.getOntologyEntityNameConfiguration().getConceptTypeName(false).toLowerCase());
        
        str = str.replaceAll("<propertyTypeName count=(?:[2-9]|\\d\\d\\d*)>", AbNObj.getOntologyEntityNameConfiguration().getPropertyTypeName(true).toLowerCase());
        str = str.replaceAll("<propertyTypeName( count=1)?>", AbNObj.getOntologyEntityNameConfiguration().getPropertyTypeName(false).toLowerCase());
        
        str = str.replaceAll("<parentConceptTypeName count=(?:[2-9]|\\d\\d\\d*)>", AbNObj.getOntologyEntityNameConfiguration().getParentConceptTypeName(true).toLowerCase());
        str = str.replaceAll("<parentConceptTypeName( count=1| count=0)?>", AbNObj.getOntologyEntityNameConfiguration().getParentConceptTypeName(false).toLowerCase());
        
        str = str.replaceAll("<childConceptTypeName count=(?:[2-9]|\\d\\d\\d*)>", AbNObj.getOntologyEntityNameConfiguration().getChildConceptTypeName(true).toLowerCase());
        str = str.replaceAll("<childConceptTypeName( count=1 )?>", AbNObj.getOntologyEntityNameConfiguration().getChildConceptTypeName(false).toLowerCase());
        
        
        str = str.replaceAll("<AbNTypeName count=(?:[2-9]|\\d\\d\\d*)>", AbNObj.getAbNTypeName(true).toLowerCase());
        str = str.replaceAll("<AbNTypeName( count=1| count=0)?>", AbNObj.getAbNTypeName(false).toLowerCase());
        
        str = str.replaceAll("<nodeTypeName count=(?:[2-9]|\\d\\d\\d*)>", AbNObj.getNodeTypeName(true).toLowerCase());
        str = str.replaceAll("<nodeTypeName( count=1| count=0)?>", AbNObj.getNodeTypeName(false).toLowerCase());
        
        return str;
    }
}
