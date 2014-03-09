

package Simple2DEngine;

import GenericBinarySearchTree.*;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import java.io.File;
import javax.media.opengl.*;

class GraphicLoader {
    
    GL2 gl = null;   
    GenericBinarySearchTree<String, Texture> textureTree = null;
    
    protected GraphicLoader() {
        textureTree = new GenericBinarySearchTree<>();
        gl = Simple2DEngine.gl;
    }
    
    protected boolean loadGraphic(String path, String name) {
        try {
            if (textureTree.get(name) == null) {
                Texture newText = TextureIO.newTexture(new File(path), false);
                textureTree.set(name, newText);
                return true;
            }
            else return false;
        }
        catch(Exception e) {
            return false;
        }
    }
    
   protected Graphic2D getGraphic2D(String name) {
       Texture tempText = textureTree.get(name);
       if (tempText == null) return null;
       else return new Graphic2D(tempText);
   }
   
   protected Graphic2D getGraphic2D(String path, String name) {
       if (textureTree.get(name) != null) {
           return this.getGraphic2D(name);
       }
       else if (this.loadGraphic(path, name)) {
           return getGraphic2D(name);
       } 
       else {
           return null;
       }
   }
    
}
