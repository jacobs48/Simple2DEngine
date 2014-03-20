
package Simple2DEngine;

/**
 * S2DInterface provides an interface to S2DEngine for initializing
 and updating game-state
 *
 * @author Michael Jacobs
 */
public interface S2DInterface {
    
    /**
     * Initialization method that runs after engine and graphics initialization
     *
     * @param engine Engine parameter provides reference for game engine object
     */
    public void init(S2DEngine engine);
    
    /**
     * Update method runs prior to rendering each frame at specified framerate
     *
     * @param engine Engine parameter provides reference for game engine object
     */
    public void update(S2DEngine engine);
    
}
