package org.virus.netlab;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author virus
 */
public class OUI {
    public String get(String oui) {
        try {
            String OUIs = new String(Files.readAllBytes(Paths.get("organizations.json")));
            
            JSONObject jo = new JSONObject(OUIs);
            
            if (jo.has(oui)) {
                return jo.getString(oui);
            }
        } catch (FileNotFoundException ex) {
            
        } catch (IOException ex) {
            
        }
        
        return null;
    }
    
    public ArrayList<String> getMany(String oui) {
        ArrayList<String> OUIsArray = new ArrayList<>();
        
        try {
            String OUIs = new String(Files.readAllBytes(Paths.get("organizations.json")));
            
            JSONObject jo = new JSONObject(OUIs);
            Iterator<String> keys = jo.keys();
            while (keys.hasNext()) {
                String o = keys.next();
                if (o.contains(oui)) {
                    OUIsArray.add(jo.getString(o));
                }
            }
            
            return OUIsArray;
        } catch (FileNotFoundException ex) {
            
        } catch (IOException ex) {
            
        }
        
        return OUIsArray;
    }
}