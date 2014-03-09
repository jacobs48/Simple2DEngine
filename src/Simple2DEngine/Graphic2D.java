

package Simple2DEngine;

import com.jogamp.opengl.util.texture.Texture;;
import javax.media.opengl.*;

/*
 * Graphic2D describes a 2D image as it is to be rendered by Graphic2DRenderer
 * 
 */
class Graphic2D {
    
    private Texture texture = null;
    private float xPos = 0;
    private float yPos = 0;
    private float depth = 0;
    private float width = 0;
    private float height = 0;
    private float texX0 = 0; 
    private float texX1 = 1;
    private float texY0 = 0;
    private float texY1 = 1;
    private float r = 1;
    private float g = 1;
    private float b = 1;
    private float a = 1;
    private float scale = 1;
    private GL2 gl = null;
    
    protected String textureKey;
    
    protected Graphic2D(String key) {
        texture = Simple2DEngine.gLoader.getTexture(key);
        textureKey = key;
        gl = Simple2DEngine.gl;
        width = texture.getWidth();
        height = texture.getHeight();
    }
    
    protected Graphic2D X(float x) {
        xPos = x;
        return this;
    }
    
    protected Graphic2D Y(float y) {
        yPos = y;
        return this;
    }
    
    protected Graphic2D depth(float d) {
        depth = d;
        return this;
    }
    
    protected String textureKey() {
        return textureKey;
    } 
    
    protected Graphic2D textureMap(float x0, float y0, float x1, float y1) {
        texX0 = x0;
        texX1 = x1;
        texY0 = y0;
        texY1 = y1;
        return this;
    }
    
    protected void draw() {
        gl.glColor4f(r, g, b, a);
        gl.glTexCoord2f(texX0, texY0);
        gl.glVertex3f(xPos, yPos, depth);
        gl.glTexCoord2f(texX0, texY1);
        gl.glVertex3f(xPos, yPos + height * scale, depth);
        gl.glTexCoord2f(texX1, texY1);
        gl.glVertex3f(xPos + width * scale, yPos + height * scale, depth);
        gl.glTexCoord2f(texX1, texY0);
        gl.glVertex3f(xPos + width * scale, yPos, depth);
    }
    
    /*
     * Returns float[12] containing vector coordinates of Graphic2D quad
     */
    protected float[] getVArr() {
        float [] v = new float [] {
            xPos, yPos, depth,
            xPos, yPos + height * scale, depth,
            xPos + width * scale, yPos + height * scale, depth,
            xPos + width * scale, yPos, depth 
        };
        return v;  
    }
    
    /*
     * Returns float[16] containing color values for Graphic2D quad
     */
    protected float[] getCArr() {
        float [] c = new float [] {
            r, g, b, a,
            r, g, b, a,
            r, g, b, a,
            r, g, b, a,
        };      
        return c;
    }
    
    /*
     * Returns float[8] containing texture coordinates for Graphic2D quad
     */
    protected float[] getTArr() {
        float [] t = new float [] {
            texX0, texY0,
            texX0, texY1,
            texX1, texY1,
            texX1, texY0  
        }; 
        return t;
    }
    
}
