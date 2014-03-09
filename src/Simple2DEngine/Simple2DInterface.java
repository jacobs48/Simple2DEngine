
package Simple2DEngine;

/**
 * Simple2DInterface provides an interface to Simple2DEngine for initializing
 * and updating game-state
 *
 * @author Michael Jacobs
 */
public interface Simple2DInterface {
    
    /**
     * Initialization method that runs after engine and graphics initialization
     *
     * @param engine Engine parameter provides reference for game engine object
     */
    public void init(Simple2DEngine engine);
    
    /**
     * Update method runs prior to rendering each frame at specified framerate
     *
     * @param engine Engine parameter provides reference for game engine object
     */
    public void update(Simple2DEngine engine);
    
}
