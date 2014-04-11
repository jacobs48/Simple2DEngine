/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Simple2DEngine;

import com.jogamp.opengl.util.texture.Texture;

/**
 *
 * @author Michael Jacobs
 */
public class S2DSubTexture {
    
    private Texture texture;
    private String textureKey;
    private String superTextureKey;
    private float texX0 = 0;
    private float texX1 = 1;
    private float texY0 = 0;
    private float texY1 = 1;
    
    protected S2DSubTexture(Texture t, String k) {
        texture = t;
        textureKey = k;
        superTextureKey = k;
    }
    
    protected S2DSubTexture(Texture t, String k, String sK) {
        texture = t;
        textureKey = k;
        superTextureKey = sK;
    }
    
    protected void setMapping(float x0, float x1, float y0, float y1) {
        texX0 = x0;
        texX1 = x1;
        texY0 = y0;
        texY1 = y1;
    }
    
    protected String getTextureKey() {
        return textureKey;
    }
    
    protected String getSuperTextureKey() {
        return superTextureKey;
    }
    
    protected S2DSubTexture setMapping(S2DTexturedQuad q) {
        q.textureMap(texX0, texY0, texX1, texY1);
        return this;
    }
    
    protected void bind() {
        texture.bind(S2DEngine.gl);
    }
    
    protected float getWidth() {
        return ((float)texture.getWidth()) * (texX1 - texX0);
    }
    
    protected float getHeight() {
        return ((float)texture.getHeight()) * (texY1 - texY0);
    }

}
