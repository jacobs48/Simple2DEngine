/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Simple2DEngine;

import static Simple2DEngine.S2DLayer.LAYER_DEPTH_DIF;
import java.util.Collections;

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
        bgQuad = new S2DQuad(w, h);
        bgQuad.X(x);
        bgQuad.Y(y);
        bgQuad.setHidden(true);
        S2DEngine.render.addGraphic(bgQuad);
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
    
    public void backgroundHidden(boolean b) {
        bgQuad.setHidden(b);
    }
    
    public void setBGColor(float r, float g, float b){
        this.r = r;
        this.g = g;
        this.b = b;
        bgQuad.setColor(r, g, b);
    }
    
    public void addStaticGraphic(S2DDrawable drawable) {
        drawable.setLayer(this);
    }
    
    public void updatePosition(float x, float y) {
        xPos = x;
        yPos = y;
        bgQuad.X(x);
        bgQuad.Y(y);
        this.updateAll();
    }
    
    public void resize(float w, float h) {
        width = w;
        height = h;
        bgQuad.setWidth(width);
        bgQuad.setHeight(height);
        this.updateAll();
    }
    
    protected void updateAll(){
        int i;
        
        i = S2DEngine.layerList.indexOf(this);
        this.updateZ(i);
        
        for(S2DDrawable drawable : gObjects) {
            drawable.updateDrawable();
        }
    }
    
    //Updates the depth value of all Graphic2D objects stored in S2DGraphic list
    protected void updateZ(float i) {
        super.updateZ(i);
        bgQuad.Z((i - 1) * LAYER_DEPTH_DIF + MIN_DEPTH_DIF);
    }
    
    protected void updateZ() {
        super.updateZ();
        bgQuad.Z((baseZValue - 1) * LAYER_DEPTH_DIF + MIN_DEPTH_DIF);
    }
    
    public void destroy() {
        super.destroy();
        S2DEngine.render.removeGraphic(bgQuad);
    }
    
}
