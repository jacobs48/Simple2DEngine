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
            this.updateGraphicObject(g);
        }
    }
    
    @Override
    protected void updateGraphicObject(GraphicObject g) {
        g.g2D.setHidden(g.hidden);
        g.g2D.setRotXOffset(g.rotXOffset);
        g.g2D.setRotYOffset(g.rotYOffset);
        g.g2D.setRotation(g.rotation);    
        g.g2D.setA((100 - g.transparency) / 100);
        
        switch (g.alignment) {
            case LEFT_UPPER:
                g.g2D.X(g.xOffset * gameSpaceCoeff);
                g.g2D.Y(Simple2DEngine.engine.getYSize() - g.g2D.getHeight() + g.yOffset * gameSpaceCoeff);
                break;
            case LEFT_LOWER:
                g.g2D.X(g.xOffset * gameSpaceCoeff);
                g.g2D.Y(g.yOffset * gameSpaceCoeff);
                break;
            case LEFT_CENTERED:
                g.g2D.X(g.xOffset * gameSpaceCoeff);
                g.g2D.Y((Simple2DEngine.engine.getYSize() / 2) - (g.g2D.getHeight() / 2) + g.yOffset * gameSpaceCoeff);
                break;
            case RIGHT_UPPER:
                g.g2D.X(Simple2DEngine.engine.getXSize() - g.g2D.getWidth() + g.xOffset * gameSpaceCoeff);
                g.g2D.Y(Simple2DEngine.engine.getYSize() - g.g2D.getHeight() + g.yOffset * gameSpaceCoeff);
                break;
            case RIGHT_LOWER:
                g.g2D.X(Simple2DEngine.engine.getXSize() - g.g2D.getWidth() + g.xOffset * gameSpaceCoeff);
                g.g2D.Y(g.yOffset * gameSpaceCoeff);
                break;
            case RIGHT_CENTERED:
                g.g2D.X(Simple2DEngine.engine.getXSize() - g.g2D.getWidth() + g.xOffset * gameSpaceCoeff);
                g.g2D.Y((Simple2DEngine.engine.getYSize() / 2) - (g.g2D.getHeight() / 2) + g.yOffset * gameSpaceCoeff);
                break;
            case TOP_CENTERED:
                g.g2D.X((Simple2DEngine.engine.getXSize() / 2) - (g.g2D.getWidth() / 2) + g.xOffset * gameSpaceCoeff);
                g.g2D.Y(Simple2DEngine.engine.getYSize() - g.g2D.getHeight()+ g.yOffset * gameSpaceCoeff);
                break;
            case BOTTOM_CENTERED:
                g.g2D.X((Simple2DEngine.engine.getXSize() / 2) - (g.g2D.getWidth() / 2) + g.xOffset * gameSpaceCoeff);
                g.g2D.Y(g.yOffset * gameSpaceCoeff);
                break;
            case CENTERED:
                g.g2D.X((Simple2DEngine.engine.getXSize() / 2) - (g.g2D.getWidth() / 2) + g.xOffset * gameSpaceCoeff);
                g.g2D.Y((Simple2DEngine.engine.getYSize() / 2) - (g.g2D.getHeight() / 2)+ g.yOffset * gameSpaceCoeff);
                break;
            case NONE:
                g.g2D.X((g.xPos + g.xOffset - cameraX - initialX) * gameSpaceCoeff * scale);
                g.g2D.Y((g.yPos + g.yOffset - cameraY - initialY) * gameSpaceCoeff * scale);
                g.g2D.setScale(scale);
                break;
            default:
                break;
        }
    }
    
}
