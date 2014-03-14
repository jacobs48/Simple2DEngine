

package Simple2DEngine;

import java.util.Comparator;

/**
 * Basic 2D graphic object to be used with Simple2DEngine
 * Instantiate through Simple2DEngine.newGraphicObject();
 *
 * @author Michael Jacobs
 */
public class GraphicObject implements Comparable<GraphicObject>{
    
    private Simple2DLayer layer;
    
    protected Graphic2D g2D;
    protected float xPos = 0;
    protected float yPos = 0;
    protected float zPos = 0;
    protected float yOffset = 0;
    protected float xOffset = 0;
    protected float scale = 1;
    protected boolean hidden = false;
    protected float rotation = 0;
    protected float rotXOffset;
    protected float rotYOffset;
    protected float transparency = 0;
    protected WindowAlignment alignment = WindowAlignment.NONE;
    
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
        layer.updateGraphicObject(this);
    }
    
    /**
     * Sets Y position of GraphicObject
     *
     * @param y Y value
     */
    public void Y(float y) {
        yPos = y;
        layer.updateGraphicObject(this);
    }
    
    public void hidden(boolean b) {
        hidden = b;
        layer.updateGraphicObject(this);
    }
    
    public void xOffset(float x) {
        xOffset = x;
        layer.updateGraphicObject(this);
    }
    
    public void yOffset(float y) {
        yOffset = y;
        layer.updateGraphicObject(this);
    }
    
    public void setAlignment(WindowAlignment align) {
        alignment = align;
        layer.updateGraphicObject(this);
    }
    
    public void rotate(float degrees) {
        rotation = degrees;
        rotXOffset = g2D.getWidth() / 2;
        rotYOffset = g2D.getHeight() / 2;
        layer.updateGraphicObject(this);
    }
    
    public void rotate(float degrees, float xOff, float yOff) {
        rotation = degrees;
        rotXOffset = xOff;
        rotYOffset = yOff;
    }
    
    public void transparency(float a) {
        transparency = a;
        layer.updateGraphicObject(this);
    }
    
    public GraphicObject setLayer(Simple2DLayer l) {
        layer.remove(this);
        layer = l;
        layer.add(this);
        layer.updateGraphicObject(this);
        
        return this;
    }
    
    public float getY() {
        return yPos;
    }
    
    public float getX() {
        return xPos;
    }
    
    /**
     * Sets depth value of GraphicObject
     * 
     * @param d Depth value
     */
    public void Z(float d) {
        zPos = d;
        layer.updateGraphicObject(this);
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
        return Float.compare(zPos, t.getZ());
    }
    
    protected float getZ() {
        return zPos;
    }
    
    static class ReverseYComparator implements Comparator<GraphicObject> {

        @Override
        public int compare(GraphicObject t, GraphicObject t1) {
            return -1 * Float.compare(t.getY(), t1.getY());
        }
   
    }
    
}
