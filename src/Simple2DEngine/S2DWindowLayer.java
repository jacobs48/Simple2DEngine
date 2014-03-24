/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Simple2DEngine;

/**
 *
 * @author michael.jacobs.adm
 */
public class S2DWindowLayer extends S2DLayer {
    
    float xPos;
    float yPos;
    float width;
    float height;
    float scale = 1;
    boolean background = false;
    S2DQuad bgQuad;
    float r, g, b;

    protected S2DWindowLayer(float d, SortMode m, float x, float y, float w, float h) {
        super(d, m);
        xPos = x;
        yPos = y;
        width = w;
        height = h;
        r = 0;
        g = 0;
        b = 0;
        //bgQuaS2DTexturedQuad S2DQuad(w, h);
        //bgQuad.setHidden(true);
    }
    
    protected float translateX(float x) {
        return x * scale;
    }
    
    protected float translateY(float y) {
        return y * scale;
    }

    protected float getLayerX0() {
        return xPos;
    }
    
    protected float getLayerX1() {
        return xPos + width;
    }
    
    protected float getLayerY0() {
        return yPos;
    }
    
    protected float getLayerY1() {
        return yPos + height;
    }
    
    public float getHeight() {
        return height;
    }
    
    public float getWidth() {
        return width;
    }   
    
    protected float getScale() {
        return scale;
    }
    
    public void backgroundEnabled(boolean b) {
        
    }
    
    public void addStaticGraphic(S2DGraphic g) {
        g.setLayer(this);
    }
    
    public void updatePosition(float x, float y) {
        xPos = x;
        yPos = y;
        this.updateAll();
    }
    
    public void resize(float w, float h) {
        width = w;
        height = h;
        this.updateAll();
    }
    
}
