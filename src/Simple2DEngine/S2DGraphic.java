

package Simple2DEngine;

import java.util.Comparator;

/**
 * Basic 2D graphic object to be used with S2DEngine
 Instantiate through S2DEngine.newS2DGraphic();
 *
 * @author Michael Jacobs
 */
public class S2DGraphic extends S2DDrawable implements Comparable<S2DDrawable>{
    
    private S2DTexturedQuad texQuad;
    
    /**
     * Used by S2DEngine.newGraphicObject() to instantiate S2DGraphic
     *
     * @param g Graphic2D object to be rendered
     * @param l Layer to add to
     */
    protected S2DGraphic(S2DTexturedQuad g, S2DLayer l) {
        super();
        layer = l;
        texQuad = g;
        S2DEngine.render.addGraphic(texQuad);
    }
    
    protected void updateDrawable() {
        texQuad.setHidden(hidden);
        texQuad.setRotXOffset(rotXOffset);
        texQuad.setRotYOffset(rotYOffset);
        texQuad.setRotation(rotation);   
        texQuad.setA((100 - transparency) / 100);
        texQuad.setScale(layer.getScale());
        
        switch (alignment) {
            case LEFT_UPPER:
                texQuad.X(layer.getLayerX0() + xOffset);
                texQuad.Y(layer.getLayerY1() - texQuad.getHeight() + yOffset);
                break;
            case LEFT_LOWER:
                texQuad.X(layer.getLayerX0() + xOffset);
                texQuad.Y(layer.getLayerY0() + yOffset);
                break;
            case LEFT_CENTERED:
                texQuad.X(layer.getLayerX0() + xOffset);
                texQuad.Y(layer.getLayerY0() + (layer.getHeight() / 2) - (texQuad.getHeight() / 2) + yOffset);
                break;
            case RIGHT_UPPER:
                texQuad.X(layer.getLayerX1() - texQuad.getWidth() + xOffset);
                texQuad.Y(layer.getLayerY1() - texQuad.getHeight() + yOffset);
                break;
            case RIGHT_LOWER:
                texQuad.X(layer.getLayerX1() - texQuad.getWidth() + xOffset);
                texQuad.Y(layer.getLayerY0() + yOffset);
                break;
            case RIGHT_CENTERED:
                texQuad.X(layer.getLayerX1() - texQuad.getWidth() + xOffset);
                texQuad.Y(layer.getLayerY0() + (layer.getHeight() / 2) - (texQuad.getHeight() / 2) + yOffset);
                break;
            case TOP_CENTERED:
                texQuad.X(layer.getLayerX0() + (layer.getWidth() / 2) - (texQuad.getWidth() / 2) + xOffset);
                texQuad.Y(layer.getLayerY1() - texQuad.getHeight()+ yOffset);
                break;
            case BOTTOM_CENTERED:
                texQuad.X(layer.getLayerX0() + (layer.getWidth() / 2) - (texQuad.getWidth() / 2) + xOffset);
                texQuad.Y(layer.getLayerY0() + yOffset);
                break;
            case CENTERED:
                texQuad.X(layer.getLayerX0() + (layer.getWidth() / 2) - (texQuad.getWidth() / 2) + xOffset);
                texQuad.Y(layer.getLayerY0() + (layer.getHeight() / 2) - (texQuad.getHeight() / 2) + yOffset);
                break;
            case FILL_STRETCHED:
                texQuad.X(layer.getLayerX0());
                texQuad.Y(layer.getLayerY0());
                texQuad.setHeight(layer.getHeight());
                texQuad.setWidth(layer.getWidth());
                break;
            case LEFT_STRETCHED:
                texQuad.X(layer.getLayerX0());
                texQuad.Y(layer.getLayerY0());
                texQuad.setHeight(layer.getHeight());
                break;
            case RIGHT_STRETCHED:
                texQuad.X(layer.getLayerX1() - texQuad.getWidth());
                texQuad.Y(layer.getLayerY0());
                texQuad.setHeight(layer.getHeight());
                break;
            case TOP_STRETCHED:
                texQuad.X(layer.getLayerX0());
                texQuad.Y(layer.getLayerY0());
                texQuad.setWidth(layer.getWidth());
                break;
            case BOTTOM_STRETCHED:
                texQuad.X(layer.getLayerX0());
                texQuad.Y(layer.getLayerY1() - texQuad.getHeight());
                texQuad.setWidth(layer.getWidth());
                break;
            case NONE:
                texQuad.X(layer.getLayerX0() + layer.translateX(xPos + xOffset));
                texQuad.Y(layer.getLayerY0() + layer.translateY(yPos + yOffset));
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
            texQuad.X(layer.getLayerX0() + layer.translateX(xPos + xOffset));
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
            texQuad.Y(layer.getLayerY0() + layer.translateY(yPos + yOffset));
        }
        
        return this;
    }
    
    public S2DDrawable hidden(boolean b) {
        hidden = b;
        texQuad.setHidden(hidden);
        return this;
    }
    
    public S2DGraphic xOffset(float x) {
        xOffset = x;
        if (alignment == WindowAlignment.NONE) {
            texQuad.X(layer.getLayerX0() + xPos + xOffset);
        }
        else this.updateDrawable();
        
        return this;
    }
    
    public S2DGraphic yOffset(float y) {
        yOffset = y;
        if (alignment == WindowAlignment.NONE) {
            texQuad.Y(layer.getLayerY0() + yPos + yOffset);
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
        rotXOffset = texQuad.getWidth() * layer.getScale() / 2;
        rotYOffset = texQuad.getHeight() * layer.getScale() / 2;
        texQuad.setRotXOffset(rotXOffset);
        texQuad.setRotYOffset(rotYOffset);
        texQuad.setRotation(rotation);
        
        return this;
    }
    
    public void rotate(float degrees, float xOff, float yOff) {
        rotation = degrees;
        rotXOffset = xOff * layer.getScale();
        rotYOffset = yOff * layer.getScale();
        texQuad.setRotXOffset(rotXOffset);
        texQuad.setRotYOffset(rotYOffset);
        texQuad.setRotation(rotation);
    }
    
    @Override
    public S2DDrawable transparency(float a) {
        transparency = a;
        texQuad.setA((100 - transparency) / 100);
        return this;
    }
    
    public S2DGraphic setLayer(S2DLayer l) {
        layer.remove(this);
        layer = l;
        layer.add(this);
        this.updateDrawable();
        
        return this;
    }
    
    protected void updatePolyZ(float z) {
        texQuad.Z(z);
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
     * Destroys S2DGraphic
     *
     */
    public void destroy() {
        S2DEngine.render.removeGraphic(texQuad);
        layer.remove(this);
    }   
}
