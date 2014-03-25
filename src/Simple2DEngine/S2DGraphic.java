

package Simple2DEngine;

import java.util.Comparator;

/**
 * Basic 2D graphic object to be used with S2DEngine
 Instantiate through S2DEngine.newS2DGraphic();
 *
 * @author Michael Jacobs
 */
public class S2DGraphic extends S2DDrawable implements Comparable<S2DDrawable>{
    
    private S2DLayer layer;
    
    private S2DTexturedQuad g2D;
    private float xPos = 0;
    private float yPos = 0;
    private float zPos = 0;
    private float yOffset = 0;
    private float xOffset = 0;
    private float scale = 1;
    private boolean hidden = false;
    private float rotation = 0;
    private float rotXOffset;
    private float rotYOffset;
    private float transparency = 0;
    private WindowAlignment alignment = WindowAlignment.NONE;
    
    /**
     * Used by S2DEngine.newGraphicObject() to instantiate S2DGraphic
     *
     * @param g Graphic2D object to be rendered
     * @param l Layer to add to
     */
    protected S2DGraphic(S2DTexturedQuad g, S2DLayer l) {
        layer = l;
        g2D = g;
        S2DEngine.render.addGraphic(g2D);
    }
    
    protected void updateDrawable() {
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
            case FILL_STRETCHED:
                g2D.X(layer.getLayerX0());
                g2D.Y(layer.getLayerY0());
                g2D.setHeight(layer.getHeight());
                g2D.setWidth(layer.getWidth());
                break;
            case LEFT_STRETCHED:
                g2D.X(layer.getLayerX0());
                g2D.Y(layer.getLayerY0());
                g2D.setHeight(layer.getHeight());
                break;
            case RIGHT_STRETCHED:
                g2D.X(layer.getLayerX1() - g2D.getWidth());
                g2D.Y(layer.getLayerY0());
                g2D.setHeight(layer.getHeight());
                break;
            case TOP_STRETCHED:
                g2D.X(layer.getLayerX0());
                g2D.Y(layer.getLayerY0());
                g2D.setWidth(layer.getWidth());
                break;
            case BOTTOM_STRETCHED:
                g2D.X(layer.getLayerX0());
                g2D.Y(layer.getLayerY1() - g2D.getHeight());
                g2D.setWidth(layer.getWidth());
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
     * Sets X position of S2DGraphic
     *
     * @param x X value
     */
    public S2DGraphic X(float x) {
        xPos = x;
        if (alignment == WindowAlignment.NONE) {
            g2D.X(layer.getLayerX0() + layer.translateX(xPos + xOffset));
        }
        return this;
    }
    
    /**
     * Sets Y position of S2DGraphic
     *
     * @param y Y value
     */
    public S2DGraphic Y(float y) {
        yPos = y;
        if (alignment == WindowAlignment.NONE) {
            g2D.Y(layer.getLayerY0() + layer.translateY(yPos + yOffset));
        }
        
        return this;
    }
    
    public void hidden(boolean b) {
        hidden = b;
        g2D.setHidden(hidden);
    }
    
    public S2DGraphic xOffset(float x) {
        xOffset = x;
        if (alignment == WindowAlignment.NONE) {
            g2D.X(layer.getLayerX0() + xPos + xOffset);
        }
        else this.updateDrawable();
        
        return this;
    }
    
    public S2DGraphic yOffset(float y) {
        yOffset = y;
        if (alignment == WindowAlignment.NONE) {
            g2D.Y(layer.getLayerY0() + yPos + yOffset);
        }
        else this.updateDrawable();
        
        return this;
    }
    
    public S2DGraphic setAlignment(WindowAlignment align) {
        alignment = align;
        this.updateDrawable();
        
        return this;
    }
    
    public S2DGraphic rotate(float degrees) {
        rotation = degrees;
        rotXOffset = g2D.getWidth() * layer.getScale() / 2;
        rotYOffset = g2D.getHeight() * layer.getScale() / 2;
        g2D.setRotXOffset(rotXOffset);
        g2D.setRotYOffset(rotYOffset);
        g2D.setRotation(rotation);
        
        return this;
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
    
    public S2DGraphic setLayer(S2DLayer l) {
        layer.remove(this);
        layer = l;
        layer.add(this);
        this.updateDrawable();
        
        return this;
    }
    
    protected void updatePolyZ(float z) {
        g2D.Z(z);
    }
    
    public float getY() {
        return yPos;
    }
    
    public float getX() {
        return xPos;
    }
    
    /**
     * Sets depth value of S2DGraphic
     * 
     * @param d Depth value
     */
    public void Z(float d) {
        zPos = d;
        layer.updateZ();
    }
    
    public float getZ() {
        return zPos;
    }
    
    /**
     * Destroys S2DGraphic
     *
     */
    public void destroy() {
        S2DEngine.render.removeGraphic(g2D);
        layer.remove(this);
    }   
}
