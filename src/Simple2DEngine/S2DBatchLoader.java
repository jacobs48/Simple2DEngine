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
                int size;
                String tempString;
                tokenList = getTokenList(currentLine, ",. ");
                size = tokenList.size();
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
                    case "template":
                        currentLine = this.loadTemplate(tokenList.getFirst(), reader);
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
    
    protected String loadTemplate(String templateName, BufferedReader reader) throws IOException{
        String lastLine;
        String animationName;
        LinkedList<String> tokenList;
        StringTokenizer tokenizer;
        
        lastLine = reader.readLine();
        S2DEngine.engine.newS2DGraphicTemplate(templateName, lastLine);
        
        lastLine = reader.readLine();
        tokenList = getTokenList(lastLine, ".");
        
        while(tokenList.size() > 0 && tokenList.getLast().equals("animation")) {
            animationName = tokenList.getFirst();
            S2DEngine.templateBuilder.newAnimation(templateName, animationName);
            
            lastLine = reader.readLine();
            tokenizer = new StringTokenizer(lastLine, ", ", false);
            
            while(tokenizer.countTokens() == 2) {
                S2DEngine.templateBuilder.addAnimationFrame(templateName,
                                                            animationName,
                                                            tokenizer.nextToken(),
                                                            Float.parseFloat(tokenizer.nextToken()));
                
                lastLine = reader.readLine();
                if(lastLine != null) tokenizer = new StringTokenizer(lastLine, ", ", false);
            }
            
            tokenList = getTokenList(lastLine, ".");
        }
        
        return lastLine;
    }

    private LinkedList<String> getTokenList(String s, String delim) {
        LinkedList<String> tempList = new LinkedList<>();
        StringTokenizer tokenizer;
        if(s != null) {
            tokenizer = new StringTokenizer(s, delim, false);
            int size = tokenizer.countTokens();
            for(int i = 0; i < size; i++) {
                tempList.add(tokenizer.nextToken());
            }
         }
        return tempList;
    }
}
