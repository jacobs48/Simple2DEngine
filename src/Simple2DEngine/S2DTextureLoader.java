

package Simple2DEngine;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import java.io.File;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import javax.media.opengl.*;

/*
 * S2DTextureLoader loads textures from image files, stores textures,
 * generates Graphic2D objects from texture keys, and removes textures
 * 
 */
class S2DTextureLoader {
    
    private GL2 gl = null;   
    private TreeMap<String, Texture> textureTree = null;
    private TreeMap<String, S2DSubTexture> subTextTree = null;
    private TreeMap<String, Float> samplerIndexTree = null;
    private boolean hasUpdated = false;
    
    protected S2DTextureLoader() {
        textureTree = new TreeMap<>();
        subTextTree = new TreeMap<>();
        samplerIndexTree = new TreeMap<>();
        gl = S2DEngine.gl;
    }
    
    /*
     * Loads texture from provided file path and stores it using specified key
     * Returns true if successfully loaded, false if unable to open file or
     * key name already exists
     */
    protected boolean loadTexture(String path, String key) {
        try {
            if (textureTree.get(key) == null) {
                Texture newText = TextureIO.newTexture(new File(path), false);
                textureTree.put(key, newText);
                subTextTree.put(key, new S2DSubTexture(newText, key));
                return true;
            }
            else return false;
        }
        catch(Exception e) {
            return false;
        }
    }
    
    protected boolean loadSubTextureF(String superKey, String subKey, float x0, float x1, float y0, float y1) {
        Texture tempText = textureTree.get(superKey);
        if (tempText == null) return false;
        else {
            S2DSubTexture tempSub = new S2DSubTexture(tempText, subKey, superKey);
            tempSub.setMapping(x0, x1, y0, y1);
            subTextTree.put(subKey, tempSub);
            hasUpdated = true;
            return true;
        }
    }
    
    protected boolean loadSubTexture(String superKey, String subKey, int x0, int x1, int y0, int y1) {
        float fX0, fX1, fY0, fY1;
        Texture tempText = textureTree.get(superKey);
        
        fX0 = ((float) x0) / tempText.getWidth();
        fX1 = ((float) x1) / tempText.getWidth();
        fY0 = ((float) y0) / tempText.getHeight();
        fY1 = ((float) y1) / tempText.getHeight();
        
        if (tempText == null) return false;
        else {
            S2DSubTexture tempSub = new S2DSubTexture(tempText, subKey, superKey);
            tempSub.setMapping(fX0, fX1, fY0, fY1);
            subTextTree.put(subKey, tempSub);
            return true;
        }
        
    }
    
   //Creates Graphic2D object from specified key
   protected S2DTexturedQuad newS2DTexturedQuad(String key) {
       if (!(subTextTree.containsKey(key))) return null;
       else {
           return new S2DTexturedQuad(key);
       }
   } 
   
   protected S2DSubTexture getTexture(String key) {
       return subTextTree.get(key);
   }
   
   //Unloads texture specified by key from memory
   protected void unloadGraphic(String key) {
       textureTree.get(key).destroy(gl);
       textureTree.remove(key);
       for(Map.Entry<String, S2DSubTexture> entry : subTextTree.entrySet()) {
           if(key.equals(entry.getValue().getSuperTextureKey())) {
               subTextTree.remove(entry.getKey());
           }
       }
   }
   
   protected boolean hasUpdated(){
       return hasUpdated;
   }
   
   protected void finishUpdate() {
       hasUpdated = false;
   }
   
   protected LinkedList<String> getSuperKeyList() {
       LinkedList<String> tempList = new LinkedList<>();
       for(Map.Entry<String, Texture> entry : textureTree.entrySet()) {
           tempList.add(entry.getKey());
       }
       return tempList;
   }
   
   protected void bindSampler(int program, int sampler, int texUnit, String key) {
       int sampleLocation = gl.glGetUniformLocation(program, key);
       
       gl.glUniform1i(sampleLocation, texUnit);
       gl.glActiveTexture(GL.GL_TEXTURE0 + texUnit);
       gl.glBindTexture(GL.GL_TEXTURE_2D, textureTree.get(key).getTextureObject());
       gl.getGL3().glBindSampler(texUnit, sampler);
       
       samplerIndexTree.put(key, (float) texUnit);
   }
   
   protected float getSamplerIndex(String key) {
       float index;
       if(samplerIndexTree.containsKey(key)) index = samplerIndexTree.get(key);
       else index = -1;
       return index;
   }
   
}
