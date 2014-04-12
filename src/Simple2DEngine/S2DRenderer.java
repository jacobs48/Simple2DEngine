

package Simple2DEngine;

import java.nio.FloatBuffer;
import java.util.Collections;
import java.util.LinkedList;
import javax.media.opengl.*;

/*
 * Graphic2DRenderer handles the internal rendering for S2DEngine.
 * Contains all Graphic2D objects and renders them using rendering
 * mode selected at initialization
 */
class S2DRenderer {
    
    private LinkedList<S2DQuad> quadList = null;
    private GL2 gl;
    private RenderMode mode;
    private String boundTexture = "";
    private float backgroundR = 0;
    private float backgroundG = 0;
    private float backgroundB = 0;
    
    
    protected S2DRenderer(RenderMode m) {
        gl = S2DEngine.gl;
        quadList = new LinkedList<>();
        mode = m;
    }
    
    protected void setBackgroundColor(float r, float g, float b) {
        backgroundR = r;
        backgroundG = g;
        backgroundB = b;
    }
    
    //Add Graphic2D object LinkedList in treeMap with provided key value
    protected void addQuad(S2DQuad q) {
        quadList.add(q);
    }
    
    //Removes Graphic2D object from renderer by key
    protected void removeQuad(S2DQuad q) {
        quadList.remove(q);
    } 
    
    //Removes entire list of texture for safely unloading texture
    protected void removeAllTex(String key) {
        for(S2DQuad quad : quadList) {
            if (quad.getSuperTextureKey().equals(key)) quadList.remove(quad);
        }
    }
    
    protected void draw() {
        switch (mode) {
            case IMMEDIATE:
                drawImmediate();
                break;
            case VERTEX_ARRAY:
                break;
            case VERTEX_BUFFER_OBJECT:
                break;
        }
    }
    
    private void drawImmediate() {   
        Collections.sort(quadList);
        
        gl.glClearColor(backgroundR, backgroundG, backgroundB, 0);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        
        for(S2DQuad quad : quadList) {
            if (!(quad.isHidden())) {
                if (quad.isTextured() && (!boundTexture.equals(quad.getSuperTextureKey()))) {
                    quad.getTexture().bind();
                    gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
                    gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
                }
                
                quad.draw();
            }
            
        }
    } 
    
}
