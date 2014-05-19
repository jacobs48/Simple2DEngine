/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Simple2DEngine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 *
 * @author Michael Jacobs
 */
class S2DBatchLoader {
    
    protected S2DBatchLoader(){
        
    }
    
    protected boolean parseFile(String fileName) {
        boolean success = true;
        String currentLine;
        BufferedReader reader;
        StringTokenizer tokenizer;
        LinkedList<String> tokenList = new LinkedList<>();
        
        try {
            reader = new BufferedReader(new FileReader(fileName));
            currentLine = reader.readLine();
            
            while(currentLine != null) {
                tokenizer = new StringTokenizer(currentLine, ",. ", false);
                int size = tokenizer.countTokens();
                String tempString;
                
                for(int i = 0; i < size; i++) {
                    tokenList.add(tokenizer.nextToken());
                }
                
                tempString = tokenList.getLast();
                
                switch(tempString.toLowerCase()) {
                    case "png":
                    case "gif":
                    case "jpg":
                    case "pam":
                    case "ppm":
                    case "sgi":
                    case "sgi_rbg":
                    case "tga":
                    case "tiff":
                        currentLine = this.loadTexture(currentLine, reader);
                        break;
                    case "txt":
                        this.parseFile(currentLine);
                        currentLine = reader.readLine();
                        break;
                    default:
                        currentLine = reader.readLine();
                        break;
                }
            }
        }
        catch(IOException e) {
            success = false;
        }
        
        return success;
    }

    protected String loadTexture(String fileName, BufferedReader reader) throws IOException {
        String lastLine = reader.readLine();
        StringTokenizer tokenizer;
        
        S2DEngine.textureLoader.loadTexture(fileName, fileName);
        
        if(lastLine != null) {
            tokenizer = new StringTokenizer(lastLine, ", ", false);
            while(tokenizer.countTokens() == 5) {
                S2DEngine.textureLoader.loadSubTexture(fileName,
                                                       tokenizer.nextToken(),
                                                       Integer.parseInt(tokenizer.nextToken()),
                                                       Integer.parseInt(tokenizer.nextToken()),
                                                       Integer.parseInt(tokenizer.nextToken()),
                                                       Integer.parseInt(tokenizer.nextToken()));

                lastLine = reader.readLine();
                if(lastLine != null) tokenizer = new StringTokenizer(lastLine, ", ", false);
            }
        }
        
        return lastLine;
    }

}
