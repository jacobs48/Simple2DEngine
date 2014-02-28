/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Simple2DEngine;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;


public class Simple2DScene implements GLEventListener {
    
    Simple2DUpdater updater;
    Simple2DEngine engine;
    
    private Simple2DScene() {
        
    }
    
    protected Simple2DScene(Simple2DUpdater s, Simple2DEngine e) {
        updater = s;
        engine = e;
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        updater.update(engine);
        render(drawable);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        
    }
    
    private void render(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
    }
    
}
