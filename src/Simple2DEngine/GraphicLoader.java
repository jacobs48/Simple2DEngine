

package Simple2DEngine;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import java.io.File;
import java.util.TreeMap;
import javax.media.opengl.*;

/*
 * GraphicLoader loads textures from image files, stores textures,
 * generates Graphic2D objects from texture keys, and removes textures
 * 
 */
class GraphicLoader {
    
    GL2 gl = null;   
    TreeMap<String, Texture> textureTree = null;
    
    protected GraphicLoader() {
        textureTree = new TreeMap<>();
        gl = Simple2DEngine.gl;
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
   protected Graphic2D getGraphic2D(String key) {
       Texture tempText = textureTree.get(key);
       if (tempText == null) return null;
       else {
           return new Graphic2D(tempText).key(key);
       }
   } 
   
   //Unloads texture specified by key from memory
   protected void unloadGraphic(String key) {
       textureTree.get(key).destroy(gl);
   }
}
