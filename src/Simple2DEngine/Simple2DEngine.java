
package Simple2DEngine;

import javax.media.opengl.*;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.newt.event.*;
import com.jogamp.opengl.util.FPSAnimator;

/**
 * Simple2DEngine
 * 
 * Simpl2DEngine provides interaction with 2D graphics engine.
 * Creates a window through NEWT and configures window, engine,
 * and rendering options based on user specifications and
 * provides methods for creating and modifying 2D graphic 
 * objects within engine.
 * 
 * Uses Simple2DInterface to provide methods for initializing
 * game state after engine initialiation and updating game state
 * at specified framerate.
 *
 * @author Michael Jacobs
 */
public class Simple2DEngine {
    
    
    private GLProfile profile;
    private GLCapabilities capabilities;
    private GLWindow window;
    private FPSAnimator animator;
    
    
    private int sizeX;
    private int sizeY;
    private int FPS;
    private boolean fullscreen;
    private String title;
    private Simple2DInterface updater;
    private RenderMode renderMode;
    
    /**
     * Provides static reference for the engine's GL2 object
     */
    protected static GL2 gl;
    
    /**
     * Provides static reference for the engine's Graphic2DRenderer
     */
    protected static Graphic2DRenderer render;
    
    /**
     * Provides static reference for the engine's GraphicLoader
     */
    private GraphicLoader gLoader = null;
    
    private Simple2DEngine() {
        
    }

    private Simple2DEngine(Builder b) {
        sizeX = b.buildX;
        sizeY = b.buildY;
        FPS = b.buildFPS;
        title = b.title;
        fullscreen = b.fullscreen;
        updater = b.updater;
        renderMode = b.mode;
    }
    
    /**
     * Initializes engine and begins execution of game.
     * Specified Simple2DInterface method init(Simple2DEngine e)
     * will run after engine initializes and then 
     * update(Simple2DEngine e) runs at specified framerate.
     */
    public void runGame() {
        profile = GLProfile.getDefault();
        capabilities = new GLCapabilities(profile);
        window = GLWindow.create(capabilities);
        window.addGLEventListener(new SimpleGLInterface(updater, this, renderMode));
        
        animator = new FPSAnimator(window, FPS);
        
        window.setSize(sizeX, sizeY);
        window.setFullscreen(fullscreen);
        window.setTitle(title);
        window.setVisible(true);
        
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowDestroyNotify(WindowEvent arg0) {
                System.exit(sizeX);
            };
        });
        
        animator.start();
    }
    
    //SimpleGLInterface uses to provide loader
    protected void initLoader(GraphicLoader loader) {
        gLoader = loader;
    }
    
    //SimpleGLInterface uses to provide renderer
    protected void initRenderer(Graphic2DRenderer renderer) {
        render = renderer;
    }
    
    //SimpleGLInterface uses to provide GL2 instance
    protected void bindGL(GL2 gl2) {
        gl = gl2;
    }
    
    /**
     * Returns X dimension of the engine window
     * 
     * @return X dimension of engine window
     */
    public int getXSize() {
        return sizeX;
    }
    
    /**
     * Returns Y dimension of the engine window
     * 
     * @return Y dimension of engine window
     */
    public int getYSize() {
        return sizeY;
    }
    
    /**
     * Loads specified image file into current GraphicLoader
     * and assigns it with provided key name.
     * 
     * @param path Path of file to be loaded
     * @param name Key name used to access loaded image
     * @return Returns true if successful, false if file fails to load
     */
    public boolean loadGraphic(String path, String name) {
        return gLoader.loadGraphic(path, name);
    }
    
    /**
     * Provides new instance of GraphicObject from specified texture
     *
     * @param name Key name of texture to be used by GraphicObject
     * @return GraphicObject created using specified texture
     */
    public GraphicObject newGraphicObject(String name) {
        Graphic2D g2D = gLoader.getGraphic2D(name);
        GraphicObject gO = new GraphicObject(g2D);
        return gO;
    }
    
    /**
     * Builder class used to create instance of Simple2DEngine object
     * 
     * Begin build with Builder(Simple2DInterface s) 
     * Terminate with Build()
     * 
     * Potential Attributes:
     *      size(int x, int y) - Size of window in pixels
     *      fullscreen(boolean f) - Specifies if fullscreen enabled
     *      fps(int f) - Engine framerate
     *      title(String s) - Window title
     *      renderMode(RenderMode r) - Render mode to be used by engine
     *      
     */
    public static class Builder {
        private int buildX = 800;
        private int buildY = 600;
        private int buildFPS = 60;
        private String title = "";
        private boolean fullscreen = false;
        private Simple2DInterface updater;
        private RenderMode mode = RenderMode.IMMEDIATE;
        
        /**
         * Initializes builder using specified Simple2DInterface
         *
         * @param s Simple2DInterface to be used by engine
         */
        public Builder(Simple2DInterface s){
            updater = s;
        }
        
        /**
         * Sets size attribute of engine
         *
         * @param x X dimension of window in pixels
         * @param y Y dimension of window in pixels
         * @return Builder object
         */
        public Builder size(int x, int y) {
            buildX = x;
            buildY = y;
            return this;
        }
        
        /**
         * Sets fullscreen mode of engine
         *
         * @param f True if fullscreen, false if windowed
         * @return Builder object
         */
        public Builder fullscreen(boolean f) {
            fullscreen = f;
            return this;
        }
        
        /**
         * Sets framerate of engine
         *
         * @param f Framerate to be used
         * @return Builder object
         */
        public Builder fps(int f) {
            buildFPS = f;
            return this;
        }
        
        /**
         * Sets title of engine window
         *
         * @param s Title of window
         * @return Builder object
         */
        public Builder title(String s) {
            title = s;
            return this;
        }
        
        /**
         * Sets render mode of engine
         * Available modes:
         * RenderMode.IMMEDIATE - Uses immediate rendering
         *
         * @param r Render mode
         * @return Builder object
         */
        public Builder renderMode(RenderMode r) {
            mode = r;
            return this;
        }
        
        /**
         * Completes build of Simple2DEngine
         *
         * @return Simple2DEngine with specified build parameters
         */
        public Simple2DEngine build() {
            return new Simple2DEngine(this);
        }
    }
}
