

package Simple2DEngine;

import javax.media.opengl.*;
import javax.media.opengl.glu.*;

/*
 * S2DGLInterface implements OpenGL events, initializes OpenGL settings,
 * runs provided S2DInterface initialization, and runs S2DInterface
 * update and renders at specified framerate
 */
class S2DGLInterface implements GLEventListener {
    
    S2DInterface updater;
    S2DEngine engine;
    
    //Prevents instantiation without proper parameters
    private S2DGLInterface() {
        
    }
    
    protected S2DGLInterface(S2DInterface s, S2DEngine e) {
        updater = s;
        engine = e;
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(0.0, engine.getWindowWidth(), 0.0, engine.getWindowHeight(), -1, 1);
        
        
        gl.glEnable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        
        engine.init(gl);
        S2DEngine.render.initialize();
        
        updater.init(engine); //Runs S2DInterface initalization
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        
    }

    //Display function is called at specified framerate
    @Override
    public void display(GLAutoDrawable drawable) {
        updater.update(engine); //Runs S2DInterface update method
        engine.update();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        
        GL2 gl = drawable.getGL().getGL2();
        GLU glu = new GLU();
        
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluOrtho2D(0.0, width, 0.0, height);   
        
        engine.updateSize(width, height);
        engine.updateLayers();
    }
    
}
