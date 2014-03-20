

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
    
    protected S2DTextureLoader() {
        textureTree = new TreeMap<>();
        gl = S2DEngine.gl;
    }
    
    /*
     * Loads texture from provided file path and stores it using specified key
     * Returns true if successfully loaded, false if unable to open file or
     * key name already exists
     */
    protected boolean loadGraphic(String path, String key) {
        try {
            if (textureTree.get(key) == null) {
                Texture newText = TextureIO.newTexture(new File(path), false);
                textureTree.put(key, newText);
                return true;
            }
            else return false;
        }
        catch(Exception e) {
            return false;
        }
    }
    
   //Creates Graphic2D object from specified key
   protected S2DQuad newGraphic2D(String key) {
       if (!(textureTree.containsKey(key))) return null;
       else {
           return new S2DQuad(key);
       }
   } 
   
   protected Texture getTexture(String key) {
       return textureTree.get(key);
   }
   
   //Unloads texture specified by key from memory
   protected void unloadGraphic(String key) {
       textureTree.get(key).destroy(gl);
   }
   
   protected void bindTexture(String key) {
       textureTree.get(key).bind(gl);
   }
}
