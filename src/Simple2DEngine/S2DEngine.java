
package Simple2DEngine;

import javax.media.opengl.*;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.newt.event.*;
import com.jogamp.opengl.util.FPSAnimator;
import java.util.Collections;
import java.util.LinkedList;

/**
 * S2DEngine
 *
 * S2DEngine acts as the primary API for the 2D graphics engine.
 * Creates a window through NEWT and configures window, engine, 
 * and rendering options based on user specifications and provides
 * methods for creating and modifying 2D graphic objects within the
 * engine.
 * 
 * Uses S2DInterface to provide methods for initializing
 * game state after engine initialization and updating game state
 * at specified framerate.
 *
 * @author Michael Jacobs
 */
public class S2DEngine {
    
    
    private GLProfile profile;
    private GLCapabilities capabilities;
    private GLWindow window;
    private FPSAnimator FPSAnim;
    
    private int sizeX;
    private int sizeY;
    private int FPS;
    private boolean fullscreen;
    private String title;
    private S2DInterface updater;
    private RenderMode renderMode = RenderMode.VERTEX_ARRAY;
    private S2DLayer defaultLayer;
    
    private float cameraX = 0;
    private float cameraY = 0;
    private float gameSpaceCoeff = 1;
    private long prevTime;
    private long curTime;
    
    protected static S2DEngine engine;
    
    /**
     * Provides static reference for the engine's GL2 object
     */
    protected static GL2 gl;
    
    /**
     * Provides static reference for the engine's Graphic2DRenderer
     */
    protected static S2DRenderer render;
    
    /**
     * Provides static reference for the engine's S2DTextureLoader
     */
    protected static S2DTextureLoader textureLoader;
    
    protected static S2DAnimator animator;
    
    /**
     * Provides static reference for the engines S2DLayer list
     */
    protected static LinkedList<S2DLayer> layerList = new LinkedList<>();
    
    
    private S2DEngine() {

    }

    private S2DEngine(Builder b) {
        sizeX = b.buildX;
        sizeY = b.buildY;
        FPS = b.buildFPS;
        title = b.title;
        fullscreen = b.fullscreen;
        updater = b.updater;
        renderMode = b.mode;
        defaultLayer = new S2DLayer(-1, SortMode.DEPTH_SORTED);
        layerList.add(defaultLayer);
        engine = this;
    }
    
    /**
     * Initializes engine and begins execution of game.
     * Specified S2DInterface method init(S2DEngine e)
     * will run after engine initializes and then 
     * update(S2DEngine e) runs at specified framerate.
     */
    public void runGame() {
        profile = GLProfile.getDefault();
        capabilities = new GLCapabilities(profile);
        window = GLWindow.create(capabilities);
        window.addGLEventListener(new S2DGLInterface(updater, this));
        
        animator = new S2DAnimator();
        prevTime = System.nanoTime();
        curTime = System.nanoTime();
        
        FPSAnim = new FPSAnimator(window, FPS);
        
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
        
        FPSAnim.start();
    }
    
    protected void init(GL2 g) {
        gl = g;
        textureLoader = new S2DTextureLoader();
        switch (renderMode) {
            case IMMEDIATE:
                render = new S2DRendererImmediate();
                break;
            case VERTEX_ARRAY:
                render = new S2DRendererVertexArray();
                break;
            case VERTEX_BUFFER_OBJECT:
                render = new S2DRendererVertexBuffer();
                break;
        }
    }
    
    protected void update() {
        LinkedList<S2DQuad> quadList = new LinkedList<>();
        
        curTime = System.nanoTime();
        float timeDif = (float) ((curTime - prevTime) / 1000000000.0);
        animator.update(timeDif);
        prevTime = curTime;
        
        for(S2DLayer l : layerList) {
            quadList.addAll(l.getQuadList());
        }
        
        render.draw(layerList);
    }
    
    /**
     * Returns X dimension of the engine window
     * 
     * @return X dimension of engine window
     */
    public int getWindowWidth() {
        return sizeX;
    }
    
    /**
     * Returns Y dimension of the engine window
     * 
     * @return Y dimension of engine window
     */
    public int getWindowHeight() {
        return sizeY;
    }
    
    public void setGameSpace(float g) {
        gameSpaceCoeff = 1/g;
        for (S2DLayer layer : layerList) {
            layer.updateGameSpace(gameSpaceCoeff);
            layer.updateAll();
        }
    }
    
    public void setBGColor(float r, float g, float b) {
        render.setBGColor(r, g, b);
    }
    
    public void updateCamera(float x, float y) {
        cameraX = x;
        cameraY = y;
        for (S2DLayer layer : layerList) {
            layer.updateCamera(cameraX, cameraY);
        }
    }
    
    /**
     * Generates a new S2DLayer
     * 
     * @param depth Specifies depth of layer
     * @param m Specifies how S2DRectangles in layer should be sorted
     * @return Newly generated layer
     */
    public S2DLayer newS2DLayer(float depth, SortMode m) {
        S2DLayer tempLayer = new S2DLayer(depth, m);
        layerList.add(tempLayer);
        Collections.sort(layerList);
        this.updateLayersZ();
        return tempLayer;
    }
    
    public S2DGameLayer newS2DGameLayer(float depth, SortMode m) {
        S2DGameLayer tempLayer = new S2DGameLayer(depth, m, cameraX, cameraY, gameSpaceCoeff);
        layerList.add(tempLayer);
        Collections.sort(layerList);
        this.updateLayersZ();
        return tempLayer;
    }
    
    public S2DWindowLayer newS2DWindowLayer(float d, SortMode m, float x, float y, float w, float h) {
        S2DWindowLayer tempLayer = new S2DWindowLayer(d, m, x, y, w, h);
        layerList.add(tempLayer);
        Collections.sort(layerList);
        this.updateLayersZ();
        return tempLayer;
    }
    
    protected void removeLayer(S2DLayer layer) {
        layerList.remove(layer);
        Collections.sort(layerList);
        this.updateLayersZ();
    }
    
    /**
     * Loads specified image file into current S2DTextureLoader
     * and assigns it with provided key name. Returns false if 
     * specified key is already in use.
     * 
     * @param path Path of file to be loaded
     * @param name Key name used to access loaded image
     * @return Returns true if successful, false if file fails to load
     */
    public boolean loadTexture(String path, String name) {
        return textureLoader.loadTexture(path, name);
    }
    
    public boolean loadSubTexture(String superKey, String subKey, int x0, int x1, int y0, int y1) {
        return textureLoader.loadSubTexture(superKey, subKey, x0, x1, y0, y1);
    }
    
    public boolean loadSubTextureF(String superKey, String subKey, float x0, float x1, float y0, float y1) {
        return textureLoader.loadSubTextureF(superKey, subKey, x0, x1, y0, y1);
    }

    /**
     * Unloads texture from memory using specified key
     *
     * @param key Key value of texture to be unloaded
     */
    public void unloadTexture(String key) {
        textureLoader.unloadGraphic(key);
    }
    
    /**
     * Unloads texture and removes all graphics using
     * specified texture from renderer
     * 
     * @param key Key value of texture to be unloaded
     */
    public void unloadTextureSafe(String key) {
        textureLoader.unloadGraphic(key);
        //render.removeAllTex(key);
    }
    
    /**
     * Provides new instance of S2DRectangle from specified texture.
     * Returns null if texture doesn't exist
     *
     * @param name Key name of texture to be used by S2DRectangle
     * @return S2DRectangle created using specified texture
     */
    public S2DRectangle newS2DRectangle(float w, float h) {
        S2DQuad quad = new S2DQuad(w, h);
        S2DRectangle rect = new S2DRectangle(quad, defaultLayer);
        return rect;
    }
    
    public S2DGraphic newS2DGraphic(String name) {
        S2DTexturedQuad tQuad = textureLoader.newS2DTexturedQuad(name);
        if (tQuad == null) return null;
        S2DGraphic g = new S2DGraphic(tQuad, defaultLayer);
        return g;
    }
    
    public S2DAnimatedGraphic newS2DAnimatedGraphic(String defaultTex) {
        S2DTexturedQuad tQuad = textureLoader.newS2DTexturedQuad(defaultTex);
        if (tQuad == null) return null;
        S2DAnimatedGraphic g = new S2DAnimatedGraphic(tQuad, defaultLayer, defaultTex);
        return g;
    }
    
    protected void updateLayers() {
        this.updateLayersZ();
        for (S2DLayer layer : layerList) {
            layer.updateAll();
        }
    }
    
    protected void updateLayersZ() {
        Collections.sort(layerList);
        for (S2DLayer l : layerList) {
            l.updateZ();
        }
    }
    
    protected void updateSize(int x, int y) {
        sizeX = x;
        sizeY = y;
    }
    
    /**
     * Builder class used to create instance of S2DEngine object
     * 
     * Begin build with Builder(S2DInterface s) 
     * Terminate with Build()
     *  
     * Potential Attributes:
     * size(int x, int y) - Size of window in pixels
     * fullscreen(boolean f) - Specifies if fullscreen enabled
     * fps(int f) - Engine framerate
     * title(String s) - Window title
     * renderMode(RenderMode r) - Render mode to be used by engine
     *      
     */
    public static class Builder {
        private int buildX = 800;
        private int buildY = 600;
        private int buildFPS = 60;
        private String title = "";
        private boolean fullscreen = false;
        private S2DInterface updater;
        private RenderMode mode = RenderMode.IMMEDIATE;
        
        /**
         * Initializes builder using specified S2DInterface
         *
         * @param s S2DInterface to be used by engine
         */
        public Builder(S2DInterface s){
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
         * Completes build of S2DEngine
         *
         * @return S2DEngine with specified build parameters
         */
        public S2DEngine build() {
            return new S2DEngine(this);
        }
    }
}
