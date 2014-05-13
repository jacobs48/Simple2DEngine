/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Simple2DEngine;

/**
 *
 * @author Mike
 */
abstract class S2DDrawable implements Comparable<S2DDrawable> {
    
    protected float xPos;
    protected float yPos;
    protected float zPos;
    protected float width;
    protected float height;
    protected float rotation;
    protected float scale;
    protected float transparency;
    protected float xOffset;
    protected float yOffset;
    protected float rotXOffset;
    protected float rotYOffset;
    protected boolean hidden;
    protected S2DLayer layer;
    protected WindowAlignment alignment;
    
    protected S2DDrawable() {
        xPos = 0;
        yPos = 0;
        zPos = 0;
        width = 0;
        height = 0;
        rotation = 0;
        scale = 1;
        transparency = 0;
        xOffset = 0;
        yOffset = 0;
        rotXOffset = 0;
        rotYOffset = 0;
        hidden = false;
        alignment = WindowAlignment.NONE;
    }
    
    
    abstract protected float getY();
    
    abstract protected float getZ();
    
    abstract protected void updateDrawable();
    
    abstract public S2DDrawable setLayer(S2DLayer l);
    
    abstract public void destroy();
    
    abstract public S2DDrawable X(float x);
    
    abstract public S2DDrawable Y(float y);
    
    abstract public S2DDrawable Z(float z);
    
    abstract public S2DDrawable hidden(boolean h);
    
    abstract public S2DDrawable transparency(float a);
    
    abstract public S2DDrawable rotate(float degrees);
    
    abstract protected void updateAnimation(float t);
    
    abstract protected S2DQuad getQuad();

    @Override
    public int compareTo(S2DDrawable t) {
        return Float.compare(this.getZ(), t.getZ());
    }
    
    
    
    
}
