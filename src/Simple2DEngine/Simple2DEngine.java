/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Simple2DEngine;

import javax.media.opengl.*;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.newt.event.*;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.FPSAnimator;


public class Simple2DEngine {
    
    GLProfile gp;
    GLCapabilities caps;
    GLWindow window;
    
    private int sizeX;
    private int sizeY;
    private int FPS;
    private boolean fullscreen;
    private String title;
    private Simple2DUpdater updater;
    
    private Simple2DEngine() {
        
    }

    private Simple2DEngine(Builder b) {
        sizeX = b.buildX;
        sizeY = b.buildY;
        FPS = b.buildFPS;
        title = b.title;
        fullscreen = b.fullscreen;
        updater = b.updater;
    }
    
    public void runGame() {
        GLProfile gp = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(gp);
        GLWindow window = GLWindow.create(caps);
        window.addGLEventListener(new Simple2DScene(updater, this));
        
        FPSAnimator animator = new FPSAnimator(window, FPS);
        
        window.setSize(sizeX, sizeY);
        window.setFullscreen(fullscreen);
        window.setTitle(title);
        window.setVisible(true);
        
        window.addWindowListener(new WindowAdapter() {
            public void windowDestroyNotify(WindowEvent arg0) {
                System.exit(sizeX);
            };
        });
        
        animator.start();
    }
    
    public static class Builder {
        private int buildX = 800;
        private int buildY = 600;
        private int buildFPS = 60;
        private String title = "";
        private boolean fullscreen = false;
        private Simple2DUpdater updater;
        
        public Builder(Simple2DUpdater s){
            updater = s;
        }
        
        public Builder size(int x, int y) {
            buildX = x;
            buildY = y;
            return this;
        }
        
        public Builder fullscreen(boolean f) {
            fullscreen = f;
            return this;
        }
        
        public Builder fps(int f) {
            buildFPS = f;
            return this;
        }
        
        public Builder title(String s) {
            title = s;
            return this;
        }
        
        public Simple2DEngine build() {
            return new Simple2DEngine(this);
        }
    }
}
