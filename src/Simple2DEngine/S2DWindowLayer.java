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
    S2DRectangle bgRect;
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
        bgRect = new S2DRectangle(width, height, this);
        bgRect.setLayer(this);
        bgRect.setAlignment(WindowAlignment.FILL_STRETCHED);
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
        bgRect.hidden(b);
    }
    
    public void setBGColor(float r, float g, float b){
        this.r = r;
        this.g = g;
        this.b = b;
        bgRect.setColor(r, g, b);
    }
    
    public void addStaticGraphic(S2DDrawable drawable) {
        drawable.setLayer(this);
    }
    
    public void updatePosition(float x, float y) {
        xPos = x;
        yPos = y;
        bgRect.X(xPos);
        bgRect.Y(yPos);
        this.updateAll();
    }
    
    public void resize(float w, float h) {
        width = w;
        height = h;
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
    @Override
    protected void updateZ(float i) {
        float baseDepth;
        
        baseZValue = i; 
        baseDepth = LAYER_DEPTH_DIF * baseZValue;
        
        gObjects.remove(bgRect);
        this.sort();
        gObjects.addFirst(bgRect);
        
        for (S2DDrawable d : gObjects) {
            d.updatePolyZ(baseDepth);
            baseDepth += MIN_DEPTH_DIF;
        } 
    }
    
    @Override
    protected void updateZ() {
        float baseDepth = LAYER_DEPTH_DIF * baseZValue;
        
        gObjects.remove(bgRect);
        
        this.sort();
        
        gObjects.addFirst(bgRect);
        
        for (S2DDrawable d : gObjects) {
            d.updatePolyZ(baseDepth);
            baseDepth += MIN_DEPTH_DIF;
        } 
        
        
    }
    
    @Override
    public void destroy() {
        super.destroy();
        bgRect.destroy();
    }
    
}
