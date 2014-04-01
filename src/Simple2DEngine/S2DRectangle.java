

package Simple2DEngine;

import java.util.Comparator;

/**
 * Basic 2D graphic object to be used with S2DEngine
 * Instantiate through S2DEngine.newS2DRectangle();
 *
 * @author Michael Jacobs
 */
public class S2DRectangle extends S2DDrawable implements Comparable<S2DDrawable>{
    
    protected S2DQuad quad;
    protected float r, g, b;
    
    /**
     * Used by S2DEngine.newGraphicObject() to instantiate S2DGraphic
     *
     * @param g Graphic2D object to be rendered
     * @param l Layer to add to
     */
    protected S2DRectangle(S2DQuad q, S2DLayer l) {
        super();
        r = 1;
        g = 1;
        b = 1;
        layer = l;
        quad = q;
        width = quad.getWidth();
        height = quad.getHeight();
        S2DEngine.render.addQuad(quad);
    }
    
    protected void updateDrawable() {
        quad.setHidden(hidden);
        quad.setRotXOffset(rotXOffset);
        quad.setRotYOffset(rotYOffset);
        quad.setRotation(rotation);   
        quad.setA((100 - transparency) / 100);
        quad.setScale(layer.getScale());
        
        switch (alignment) {
            case LEFT_UPPER:
                quad.X(layer.getLayerX0() + xOffset);
                quad.Y(layer.getLayerY1() - quad.getHeight() + yOffset);
                break;
            case LEFT_LOWER:
                quad.X(layer.getLayerX0() + xOffset);
                quad.Y(layer.getLayerY0() + yOffset);
                break;
            case LEFT_CENTERED:
                quad.X(layer.getLayerX0() + xOffset);
                quad.Y(layer.getLayerY0() + (layer.getHeight() / 2) - (quad.getHeight() / 2) + yOffset);
                break;
            case RIGHT_UPPER:
                quad.X(layer.getLayerX1() - quad.getWidth() + xOffset);
                quad.Y(layer.getLayerY1() - quad.getHeight() + yOffset);
                break;
            case RIGHT_LOWER:
                quad.X(layer.getLayerX1() - quad.getWidth() + xOffset);
                quad.Y(layer.getLayerY0() + yOffset);
                break;
            case RIGHT_CENTERED:
                quad.X(layer.getLayerX1() - quad.getWidth() + xOffset);
                quad.Y(layer.getLayerY0() + (layer.getHeight() / 2) - (quad.getHeight() / 2) + yOffset);
                break;
            case TOP_CENTERED:
                quad.X(layer.getLayerX0() + (layer.getWidth() / 2) - (quad.getWidth() / 2) + xOffset);
                quad.Y(layer.getLayerY1() - quad.getHeight()+ yOffset);
                break;
            case BOTTOM_CENTERED:
                quad.X(layer.getLayerX0() + (layer.getWidth() / 2) - (quad.getWidth() / 2) + xOffset);
                quad.Y(layer.getLayerY0() + yOffset);
                break;
            case CENTERED:
                quad.X(layer.getLayerX0() + (layer.getWidth() / 2) - (quad.getWidth() / 2) + xOffset);
                quad.Y(layer.getLayerY0() + (layer.getHeight() / 2) - (quad.getHeight() / 2) + yOffset);
                break;
            case FILL_STRETCHED:
                quad.X(layer.getLayerX0());
                quad.Y(layer.getLayerY0());
                quad.setHeight(layer.getHeight());
                quad.setWidth(layer.getWidth());
                break;
            case LEFT_STRETCHED:
                quad.X(layer.getLayerX0());
                quad.Y(layer.getLayerY0());
                quad.setHeight(layer.getHeight());
                break;
            case RIGHT_STRETCHED:
                quad.X(layer.getLayerX1() - quad.getWidth());
                quad.Y(layer.getLayerY0());
                quad.setHeight(layer.getHeight());
                break;
            case TOP_STRETCHED:
                quad.X(layer.getLayerX0());
                quad.Y(layer.getLayerY0());
                quad.setWidth(layer.getWidth());
                break;
            case BOTTOM_STRETCHED:
                quad.X(layer.getLayerX0());
                quad.Y(layer.getLayerY1() - quad.getHeight());
                quad.setWidth(layer.getWidth());
                break;
            case NONE:
                quad.X(layer.getLayerX0() + layer.translateX(xPos + xOffset));
                quad.Y(layer.getLayerY0() + layer.translateY(yPos + yOffset));
                break;
            default:
                break;
        }
    }
    
    
    /**
     * Sets X position of S2DRectangle
     *
     * @param x X value
     */
    public S2DRectangle X(float x) {
        xPos = x;
        if (alignment == WindowAlignment.NONE) {
            quad.X(layer.getLayerX0() + layer.translateX(xPos + xOffset));
        }
        return this;
    }
    
    /**
     * Sets Y position of S2DRectangle
     *
     * @param y Y value
     */
    public S2DRectangle Y(float y) {
        yPos = y;
        if (alignment == WindowAlignment.NONE) {
            quad.Y(layer.getLayerY0() + layer.translateY(yPos + yOffset));
        }
        
        return this;
    }
    
    public S2DRectangle setWidth(float w) {
        width = w;
        quad.setWidth(w);
        return this;
    }
    
    public S2DRectangle setHeight(float h) {
        height = h;
        quad.setHeight(h);
        return this;
    }
    
    public S2DDrawable hidden(boolean b) {
        hidden = b;
        quad.setHidden(hidden);
        return this;
    }
    
    public S2DRectangle xOffset(float x) {
        xOffset = x;
        if (alignment == WindowAlignment.NONE) {
            quad.X(layer.getLayerX0() + xPos + xOffset);
        }
        else this.updateDrawable();
        
        return this;
    }
    
    public S2DRectangle yOffset(float y) {
        yOffset = y;
        if (alignment == WindowAlignment.NONE) {
            quad.Y(layer.getLayerY0() + yPos + yOffset);
        }
        else this.updateDrawable();
        
        return this;
    }
    
    public S2DRectangle setAlignment(WindowAlignment align) {
        alignment = align;
        this.updateDrawable();
        
        return this;
    }
    
    public S2DRectangle rotate(float degrees) {
        rotation = degrees;
        rotXOffset = quad.getWidth() * layer.getScale() / 2;
        rotYOffset = quad.getHeight() * layer.getScale() / 2;
        quad.setRotXOffset(rotXOffset);
        quad.setRotYOffset(rotYOffset);
        quad.setRotation(rotation);
        
        return this;
    }
    
    public void rotate(float degrees, float xOff, float yOff) {
        rotation = degrees;
        rotXOffset = xOff * layer.getScale();
        rotYOffset = yOff * layer.getScale();
        quad.setRotXOffset(rotXOffset);
        quad.setRotYOffset(rotYOffset);
        quad.setRotation(rotation);
    }
    
    @Override
    public S2DDrawable transparency(float a) {
        transparency = a;
        quad.setA((100 - transparency) / 100);
        return this;
    }
    
    public void setColor(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
        
        quad.setColor(r, g, b);
    }
    
    public S2DRectangle setLayer(S2DLayer l) {
        layer.remove(this);
        layer = l;
        layer.add(this);
        this.updateDrawable();
        
        return this;
    }
    
    protected void updatePolyZ(float z) {
        quad.Z(z);
    }
    
    public float getY() {
        return yPos;
    }
    
    public float getX() {
        return xPos;
    }
    
    /**
     * Sets depth value of S2DRectangle
     * 
     * @param d Depth value
     */
    @Override
    public S2DDrawable Z(float d) {
        zPos = d;
        layer.updateZ();
        return this;
    }
    
    public float getZ() {
        return zPos;
    }
    
    
    /**
     * Destroys S2DRectangle
     *
     */
    public void destroy() {
        S2DEngine.render.removeQuad(quad);
        layer.remove(this);
    }   
}
