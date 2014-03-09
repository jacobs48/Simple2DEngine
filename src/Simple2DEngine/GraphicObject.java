

package Simple2DEngine;

/**
 * Basic 2D graphic object to be used with Simple2DEngine
 * Instantiate through Simple2DEngine.newGraphicObject();
 *
 * @author Michael Jacobs
 */
public class GraphicObject {
    
    private Graphic2D g2D;
    float xPos = 0;
    float yPos = 0;
    
    /**
     * Used by Simple2DEngine.newGraphicObject() to instantiate
     * GraphicObject
     *
     * @param g Graphic2D object to be rendered
     */
    protected GraphicObject(Graphic2D g) {
        g2D = g;
        Simple2DEngine.render.addGraphic(g2D);
    }
    
    /**
     * Sets X position of GraphicObject
     *
     * @param x X value
     */
    public void x(float x) {
        xPos = x;
        g2D.X(x);
    }
    
    /**
     * Sets Y position of GraphicObject
     *
     * @param y Y value
     */
    public void y(float y) {
        yPos = y;
        g2D.Y(y);
    }
    
}
