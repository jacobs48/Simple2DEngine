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
    
    abstract protected void setBGColor(float r, float g, float b);
    
    abstract protected void draw(LinkedList<S2DQuad> l);
    
}
