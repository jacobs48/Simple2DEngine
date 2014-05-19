/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Simple2DEngine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 *
 * @author Michael Jacobs
 */
class S2DBatchLoader {
    
    protected S2DBatchLoader(){
        
    }
    
    protected boolean loadFile(String fileName) {
        boolean success = true;
        String tempString;
        String subName;
        String supName;
        BufferedReader reader;
        StringTokenizer tokenizer;
        
        try {
            reader = new BufferedReader(new FileReader(fileName));
            supName = reader.readLine();

            S2DEngine.textureLoader.loadTexture(supName, supName);
            
            tempString = reader.readLine();
            
            while(tempString != null) {
                tokenizer = new StringTokenizer(tempString, ",", false);
                subName = tokenizer.nextToken();
                
                success = S2DEngine.textureLoader.loadSubTexture(supName,
                                                                 subName,
                                                                 Integer.parseInt(tokenizer.nextToken()),
                                                                 Integer.parseInt(tokenizer.nextToken()),
                                                                 Integer.parseInt(tokenizer.nextToken()),
                                                                 Integer.parseInt(tokenizer.nextToken()));
                tempString = reader.readLine();
            }
        }
        catch (IOException e) {
            success = false;
        }
        
        return success;
    }

}
