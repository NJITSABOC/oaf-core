/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author hl395
 */
public class AbNDerivationParserUtils {
    
    
    
    
    
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
