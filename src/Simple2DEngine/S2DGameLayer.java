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
public class S2DGameLayer extends S2DLayer {
    
    protected float initialX = 0;
    protected float initialY = 0;
    protected float gameSpaceCoeff;
    protected float cameraX;
    protected float cameraY;
    protected float scale = 1;

    protected S2DGameLayer(float d, SortMode m, float camX, float camY, float gSC) {
        super(d, m);
        cameraX = camX;
        cameraY = camY;
        gameSpaceCoeff = gSC;
    }
    
    protected void updateCamera(float cX, float cY) {
        cameraX = cX;
        cameraY = cY;
        this.updateAll();
    }
    
    @Override
    protected void updateGameSpace(float x) {
        gameSpaceCoeff = x;
        this.updateAll();
    }
    
    @Override
    protected float translateX(float x) {
        return ((x  - cameraX - initialX) * gameSpaceCoeff * scale);
    }
    
    @Override
    protected float translateY(float y) {
        return ((y  - cameraY - initialY) * gameSpaceCoeff * scale);
    }
    
    @Override
    protected float getScale() {
        return scale;
    }
    
    @Override
    public void setScale(float s) {
        scale = s;
    }
    
}
