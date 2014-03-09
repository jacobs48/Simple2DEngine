

package Simple2DEngine;

import javax.media.opengl.*;
import javax.media.opengl.glu.*;

/*
 * SimpleGLInterface implements OpenGL events, initializes OpenGL settings,
 * runs provided Simple2DInterface initialization, and runs Simple2DInterface
 * update and renders at specified framerate
 */
class SimpleGLInterface implements GLEventListener {
    
    Simple2DInterface updater;
    Simple2DEngine engine;
    GraphicLoader gLoader = null;
    Graphic2DRenderer render = null;
    RenderMode mode;
    
    //Prevents instantiation without proper parameters
    private SimpleGLInterface() {
        
    }
    
    protected SimpleGLInterface(Simple2DInterface s, Simple2DEngine e, RenderMode m) {
        updater = s;
        engine = e;
        mode = m;
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        GLU glu = new GLU();
        engine.bindGL(gl);
        gLoader = new GraphicLoader();
        render = new Graphic2DRenderer(mode);
        engine.initLoader(gLoader); //Provides initialized GraphicLoader to engine
        engine.initRenderer(render); //Provides initialized Graphic2DRenderer to engine
        
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluOrtho2D(0.0, engine.getXSize(), 0.0, engine.getYSize());
        
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP_TO_EDGE);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP_TO_EDGE);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
        
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glEnable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glTexEnvf(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);
        
        updater.init(engine); //Runs Simple2DInterface initalization
        
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        
    }

    //Display function is called at specified framerate
    @Override
    public void display(GLAutoDrawable drawable) {
        updater.update(engine); //Runs Simple2DInterface update method
        render.draw();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        
        GL2 gl = drawable.getGL().getGL2();
        GLU glu = new GLU();
        
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluOrtho2D(0.0, width, 0.0, height);   
        
        
    }
    
}
