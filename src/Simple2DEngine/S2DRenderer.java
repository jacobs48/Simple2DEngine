/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Simple2DEngine;

import java.util.LinkedList;

/**
 *
 * @author michael.jacobs.adm
 */
abstract class S2DRenderer {
    
    protected float bgR = 0;
    protected float bgG = 0;
    protected float bgB = 0;
    
    protected void setBGColor(float r, float g, float b) {
        bgR = r;
        bgG = g;
        bgB = b;
    }
    
    protected void initializeLayer(S2DLayer l) {
        
    }
    
    protected void updateLayer(S2DLayer l) {
        
    }
    
    abstract protected void draw(LinkedList<S2DLayer> l);
    
}
