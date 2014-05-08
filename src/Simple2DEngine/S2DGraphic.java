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
public class S2DGraphic extends S2DRectangle implements Comparable<S2DDrawable> {
    
    protected float texX0;
    protected float texX1;
    protected float texY0;
    protected float texY1;

    protected S2DGraphic(S2DTexturedQuad g, S2DLayer l) {
        super(g, l);
    }
    
    public void rotateTexture(int rotation) {
        ((S2DTexturedQuad)quad).rotateTex(rotation);
    }
    
    public String getTextureKey() {
        return quad.getSuperTextureKey();
    }
    
    public boolean setTexture(String key) {
        return ((S2DTexturedQuad)quad).setTexture(key);
    }
    
}
