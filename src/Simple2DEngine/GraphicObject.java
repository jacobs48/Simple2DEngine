

package Simple2DEngine;

import java.util.Comparator;

/**
 * Basic 2D graphic object to be used with Simple2DEngine
 * Instantiate through Simple2DEngine.newGraphicObject();
 *
 * @author Michael Jacobs
 */
public class GraphicObject implements Comparable<GraphicObject>{
    
    private Graphic2D g2D;
    private Simple2DLayer layer;
    private float xPos = 0;
    private float yPos = 0;
    private float depth = 0;
    private String texKey;
    
    /**
     * Used by Simple2DEngine.newGraphicObject() to instantiate
     * GraphicObject
     *
     * @param g Graphic2D object to be rendered
     */
    protected GraphicObject(Graphic2D g, Simple2DLayer l) {
        layer = l;
        g2D = g;
        Simple2DEngine.render.addGraphic(g2D.textureKey(), g2D);
    }
    
    /**
     * Sets X position of GraphicObject
     *
     * @param x X value
     */
    public void X(float x) {
        xPos = x;
    }
    
    /**
     * Sets Y position of GraphicObject
     *
     * @param y Y value
     */
    public void Y(float y) {
        yPos = y;
    }
    
    public GraphicObject setLayer(Simple2DLayer l) {
        layer.remove(this);
        layer = l;
        layer.add(this);
        return this;
    }
    
    public float getY() {
        return yPos;
    }
    
    public float getX() {
        return xPos;
    }
    
    protected Graphic2D getG2D() {
        return g2D;
    }
    
    /**
     * Sets depth value of GraphicObject
     * 
     * @param d Depth value
     */
    public void depth(float d) {
        depth = d;
        g2D.depth(d);
    }
    
    /**
     * Destroys GraphicObject
     *
     */
    public void destroy() {
        Simple2DEngine.render.removeGraphic(g2D);
    }

    /**
     * Implements Comparable method compareTo using depth value
     *
     * @param t GraphicObject to compareTo
     * @return 0 if equal, less than 0 if less than, greater than 0 if greater than
     */
    @Override
    public int compareTo(GraphicObject t) {
        return Float.compare(depth, t.getDepth());
    }
    
    protected float getDepth() {
        return depth;
    }
    
    static class ReverseYComparator implements Comparator<GraphicObject> {

        @Override
        public int compare(GraphicObject t, GraphicObject t1) {
            return -1 * Float.compare(t.getY(), t1.getY());
        }
   
    }
    
}
