

package Simple2DEngine;

import java.util.LinkedList;
import javax.media.opengl.*;

/*
 * Graphic2DRenderer handles the internal rendering for S2DEngine.
 * Contains all Graphic2D objects and renders them using rendering
 * mode selected at initialization
 */
class S2DRendererImmediate extends S2DRenderer {
    
    private LinkedList<S2DQuad> quadList = null;
    private GL2 gl;
    private String boundTexture = "";
    
    
    protected S2DRendererImmediate() {
        gl = S2DEngine.gl;
        quadList = new LinkedList<>();
    }
    
    @Override
    protected void initialize() {
        gl.glTexEnvf(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);
        gl.glEnable(GL.GL_TEXTURE_2D);
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
    
    
    @Override
    protected void draw(LinkedList<S2DLayer> layers) {   
        LinkedList<S2DQuad> qList;
        float cameraX;
        float cameraY;
   
        gl.glClearColor(bgR, bgG, bgB, 0);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        
        for(S2DLayer layer : layers) {
            qList = layer.getQuadList();
            cameraX = layer.getLayerXOrigin();
            cameraY = layer.getLayerYOrigin();
            
            gl.glMatrixMode(GL2.GL_MODELVIEW);

            
            for(S2DQuad quad : qList) {
                if (!(quad.isHidden())) {
                    if (quad.isTextured() && (!boundTexture.equals(quad.getSuperTextureKey()))) {
                        quad.getTexture().bind();
                        boundTexture = quad.getSuperTextureKey();
                        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
                        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
                    }         
                    
                    gl.glLoadIdentity();
                    gl.glTranslatef(cameraX, cameraY, 0);

                    quad.draw();
                }

            }
        }
    } 
    
}
