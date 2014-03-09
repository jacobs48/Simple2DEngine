

package Simple2DEngine;

/**
 * Enum that specifies rendering modes used by Simple2DEngine
 *
 * @author Michael Jacobs
 */
public enum RenderMode {

    /**
     * Immediate rendering mode
     *
     */
    IMMEDIATE,
    
    /**
     * Render through vertex arrays
     *
     */
    VERTEX_ARRAY,
    
    /**
     * Render using vertex buffer object
     *
     */
    VERTEX_BUFFER_OBJECT
}
