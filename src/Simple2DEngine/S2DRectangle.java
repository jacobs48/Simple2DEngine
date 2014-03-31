/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Simple2DEngine;

/**
 *
 * @author Michael Jacobs
 */
public class S2DRectangle extends S2DDrawable {
    
    protected S2DQuad quad;
    protected float r = 1;
    protected float g = 1;
    protected float b = 1;
    
    protected S2DRectangle (float w, float h, S2DLayer l) {
        super();
        
        width = w;
        height = h;
        layer = l;
        
        quad = new S2DQuad(width, height);
        
        S2DEngine.render.addGraphic(quad);
    }

    @Override
    protected float getY() {
       return yPos;
    }

    @Override
    protected float getZ() {
        return zPos;
    }

    @Override
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

    @Override
    protected void updatePolyZ(float z) {
        quad.Z(z);
    }

    @Override
    public S2DDrawable setLayer(S2DLayer l) {
        layer.remove(this);
        l.add(this);
        return this;
    }

    @Override
    public void destroy() {
        layer.remove(this);
        S2DEngine.render.removeGraphic(quad);
    }

    @Override
    public S2DDrawable X(float x) {
        xPos = x;
        if (alignment == WindowAlignment.NONE) {
            quad.X(layer.getLayerX0() + layer.translateX(xPos + xOffset));
        }
        return this;
    }

    @Override
    public S2DDrawable Y(float y) {
        yPos = y;
        if (alignment == WindowAlignment.NONE) {
            quad.Y(layer.getLayerY0() + layer.translateY(yPos + yOffset));
        }
        return this;
    }

    @Override
    public S2DDrawable Z(float z) {
        zPos = z;
        layer.updateZ();
        return this;
    }

    @Override
    public S2DDrawable hidden(boolean h) {
        hidden = h;
        quad.setHidden(hidden);
        return this;
    }

    @Override
    public S2DDrawable transparency(float a) {
        transparency = a;
        quad.setA((100 - transparency) / 100);
        return this;
    }

    @Override
    public S2DDrawable rotate(float degrees) {
        rotation = degrees;
        rotXOffset = quad.getWidth() * layer.getScale() / 2;
        rotYOffset = quad.getHeight() * layer.getScale() / 2;
        quad.setRotXOffset(rotXOffset);
        quad.setRotYOffset(rotYOffset);
        quad.setRotation(rotation);
        
        return this;
    }
    
    public S2DDrawable setAlignment(WindowAlignment align) {
        alignment = align;
        this.updateDrawable();
        
        return this;
    }
    
    public void setColor(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
        
        quad.setColor(r, g, b);
    }

}
