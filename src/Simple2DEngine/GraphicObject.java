

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
     * @param l Layer to add to
     */
    protected GraphicObject(Graphic2D g, Simple2DLayer l) {
        layer = l;
        g2D = g;
        Simple2DEngine.render.addGraphic(g2D);
    }
    
    protected void updateGraphic() {
        g2D.setHidden(hidden);
        g2D.setRotXOffset(rotXOffset);
        g2D.setRotYOffset(rotYOffset);
        g2D.setRotation(rotation);    
        g2D.setA((100 - transparency) / 100);
        g2D.setScale(layer.getScale());
        
        switch (alignment) {
            case LEFT_UPPER:
                g2D.X(layer.getLayerX0() + xOffset);
                g2D.Y(layer.getLayerY1() - g2D.getHeight() + yOffset);
                break;
            case LEFT_LOWER:
                g2D.X(layer.getLayerX0() + xOffset);
                g2D.Y(layer.getLayerY0() + yOffset);
                break;
            case LEFT_CENTERED:
                g2D.X(layer.getLayerX0() + xOffset);
                g2D.Y(layer.getLayerY0() + (layer.getHeight() / 2) - (g2D.getHeight() / 2) + yOffset);
                break;
            case RIGHT_UPPER:
                g2D.X(layer.getLayerX1() - g2D.getWidth() + xOffset);
                g2D.Y(layer.getLayerY1() - g2D.getHeight() + yOffset);
                break;
            case RIGHT_LOWER:
                g2D.X(layer.getLayerX1() - g2D.getWidth() + xOffset);
                g2D.Y(layer.getLayerY0() + yOffset);
                break;
            case RIGHT_CENTERED:
                g2D.X(layer.getLayerX1() - g2D.getWidth() + xOffset);
                g2D.Y(layer.getLayerY0() + (layer.getHeight() / 2) - (g2D.getHeight() / 2) + yOffset);
                break;
            case TOP_CENTERED:
                g2D.X(layer.getLayerX0() + (layer.getWidth() / 2) - (g2D.getWidth() / 2) + xOffset);
                g2D.Y(layer.getLayerY1() - g2D.getHeight()+ yOffset);
                break;
            case BOTTOM_CENTERED:
                g2D.X(layer.getLayerX0() + (layer.getWidth() / 2) - (g2D.getWidth() / 2) + xOffset);
                g2D.Y(layer.getLayerY0() + yOffset);
                break;
            case CENTERED:
                g2D.X(layer.getLayerX0() + (layer.getWidth() / 2) - (g2D.getWidth() / 2) + xOffset);
                g2D.Y(layer.getLayerY0() + (layer.getHeight() / 2) - (g2D.getHeight() / 2) + yOffset);
                break;
            case NONE:
                g2D.X(layer.getLayerX0() + layer.translateX(xPos + xOffset));
                g2D.Y(layer.getLayerY0() + layer.translateY(yPos + yOffset));
                break;
            default:
                break;
        }
    }
    
    
    /**
     * Sets X position of GraphicObject
     *
     * @param x X value
     */
    public void X(float x) {
        xPos = x;
        if (alignment == WindowAlignment.NONE) {
            g2D.X(layer.getLayerX0() + layer.translateX(xPos + xOffset));
        }
    }
    
    /**
     * Sets Y position of GraphicObject
     *
     * @param y Y value
     */
    public void Y(float y) {
        yPos = y;
        if (alignment == WindowAlignment.NONE) {
            g2D.Y(layer.getLayerY0() + layer.translateY(yPos + yOffset));
        }
    }
    
    public void hidden(boolean b) {
        hidden = b;
        g2D.setHidden(hidden);
    }
    
    public void xOffset(float x) {
        xOffset = x;
        if (alignment == WindowAlignment.NONE) {
            g2D.X(layer.getLayerX0() + xPos + xOffset);
        }
        else this.updateGraphic();
    }
    
    public void yOffset(float y) {
        yOffset = y;
        if (alignment == WindowAlignment.NONE) {
            g2D.Y(layer.getLayerY0() + yPos + yOffset);
        }
        else this.updateGraphic();
    }
    
    public void setAlignment(WindowAlignment align) {
        alignment = align;
        this.updateGraphic();
    }
    
    public void rotate(float degrees) {
        rotation = degrees;
        rotXOffset = g2D.getWidth() * layer.getScale() / 2;
        rotYOffset = g2D.getHeight() * layer.getScale() / 2;
        g2D.setRotXOffset(rotXOffset);
        g2D.setRotYOffset(rotYOffset);
        g2D.setRotation(rotation);
    }
    
    public void rotate(float degrees, float xOff, float yOff) {
        rotation = degrees;
        rotXOffset = xOff * layer.getScale();
        rotYOffset = yOff * layer.getScale();
        g2D.setRotXOffset(rotXOffset);
        g2D.setRotYOffset(rotYOffset);
        g2D.setRotation(rotation);
    }
    
    public void transparency(float a) {
        transparency = a;
        g2D.setA((100 - transparency) / 100);
    }
    
    public GraphicObject setLayer(Simple2DLayer lyr) {
        layer.remove(this);
        layer = lyr;
        layer.add(this);
        this.updateGraphic();
        
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
        layer.updateZ();
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
    
    public float getZ() {
        return zPos;
    }
    
    static class ReverseYComparator implements Comparator<GraphicObject> {

        @Override
        public int compare(GraphicObject t, GraphicObject t1) {
            return -1 * Float.compare(t.getY(), t1.getY());
        }
   
    }
    
}
