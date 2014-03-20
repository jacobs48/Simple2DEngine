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
public class S2DGameLayer extends Simple2DLayer {
    
    protected float initialX = 0;
    protected float initialY = 0;
    protected float gameSpaceCoeff = 1;
    protected float cameraX;
    protected float cameraY;
    protected float scale = 1;

    protected S2DGameLayer(float d, SortMode m) {
        super(d, m);
    }
    
    protected S2DGameLayer(float d, SortMode m, float initX, float initY, float gSC, float camX, float camY) {
        super(d, m);
        initialX = initY;
        initialY = initY;
        gameSpaceCoeff = gSC;
        cameraX = camX;
        cameraY = camY;
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
    protected void updateAll(){
        int i;
        
        i = Simple2DEngine.layerList.indexOf(this);
        this.updateZ(i);
        
        for(GraphicObject g : gObjects) {
            g.updateGraphic();
        }
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
