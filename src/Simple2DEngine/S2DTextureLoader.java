

package Simple2DEngine;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import java.io.File;
import java.util.TreeMap;
import javax.media.opengl.*;

/*
 * S2DTextureLoader loads textures from image files, stores textures,
 * generates Graphic2D objects from texture keys, and removes textures
 * 
 */
class S2DTextureLoader {
    
    GL2 gl = null;   
    TreeMap<String, Texture> textureTree = null;
    TreeMap<String, S2DSubTexture> subTextTree = null;
    
    protected S2DTextureLoader() {
        textureTree = new TreeMap<>();
        subTextTree = new TreeMap<>();
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
    
    protected boolean loadSubTexture(String superKey, String subKey, float x0, float x1, float y0, float y1) {
        Texture tempText = textureTree.get(superKey);
        if (tempText == null) return false;
        else {
            S2DSubTexture tempSub = new S2DSubTexture(tempText, subKey, superKey);
            tempSub.setMapping(x0, x1, y0, y1);
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
   }
   
}
