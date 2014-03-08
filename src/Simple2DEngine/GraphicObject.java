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
public class GraphicObject {
    
    private Graphic2D g2D;
    float xPos = 0;
    float yPos = 0;
    Graphic2DRenderer render;
    
    protected GraphicObject(Graphic2D g, Graphic2DRenderer r) {
        g2D = g;
        render = r;
        r.addGraphic(g2D);
    }
    
    public void x(float x) {
        xPos = x;
        g2D.X(x);
    }
    
    public void y(float y) {
        yPos = y;
        g2D.Y(y);
    }
    
}
