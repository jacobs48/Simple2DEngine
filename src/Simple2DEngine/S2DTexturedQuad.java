

package Simple2DEngine;

import javax.media.opengl.*;

/*
 * Graphic2D describes a 2D image as it is to be rendered by Graphic2DRenderer
 * 
 */
class S2DTexturedQuad extends S2DQuad{
    
    private S2DSubTexture texture = null;
    private float[] texCoords;
    private boolean textured = true;
    private int texRotate;
    
    protected S2DTexturedQuad(String key) {
        super(0, 0);
        texCoords = new float[] {
            0, 0,
            0, 1,
            1, 1,
            1, 0
        };
        texRotate = 0;
        texture = S2DEngine.textureLoader.getTexture(key);
        texture.setMapping(this);
        width = texture.getWidth();
        height = texture.getHeight();
        rotXOffset = width / 2;
        rotYOffset = height / 2;  
    }
    
    protected boolean setTexture(String key) {
        S2DSubTexture tempText = S2DEngine.textureLoader.getTexture(key);
        if(tempText == null) return false;
        texture = tempText;
        texture.setMapping(this);
        width = texture.getWidth();
        height = texture.getHeight();
        return true;
    }
    
    protected void setTexture(S2DSubTexture s) {
        texture = s;
        width = texture.getWidth();
        height = texture.getHeight();
        texture.setMapping(this);
    }
    

    @Override
    protected S2DSubTexture getTexture() {
        return texture;
    }
    
    @Override
    protected String getSuperTextureKey() {
        return texture.getSuperTextureKey();
    }
    
    @Override
    protected boolean isTextured() {
        return textured;
    }
    
    protected S2DTexturedQuad textureMap(float x0, float x1, float y0, float y1) {
        float[] tempCoords = new float[] {
            x0, y0,
            x0, y1,
            x1, y1,
            x1, y0
        };
        
        for(int i = 0; i < 8; i++) {
            texCoords[i] = tempCoords[(i + 2 * texRotate) % 8];
        }
        
        return this;
    }
    
    protected void rotateTex(int r) {
        float[] tempCoords = texCoords;
        texCoords = new float[8];
        texRotate = r;
        
        for(int i = 0; i < 8; i++) {
            texCoords[i] = tempCoords[(i + 2 * texRotate) % 8];
        }  
    }

    @Override
    protected void draw() { 
        if (rotation != 0) {
            gl.glTranslatef(xPos + rotXOffset, yPos + rotYOffset, 0);
            gl.glRotatef(rotation, 0, 0, 1);
            gl.glTranslatef(-(xPos + rotXOffset), -(yPos + rotYOffset), 0);
        }
        gl.glBegin(GL2.GL_QUADS);
        gl.glColor4f(r, g, b, a);
        gl.glTexCoord2f(texCoords[0], texCoords[1]);
        gl.glVertex3f(xPos, yPos, 0);
        gl.glTexCoord2f(texCoords[2], texCoords[3]);
        gl.glVertex3f(xPos, yPos + height * scale, 0);
        gl.glTexCoord2f(texCoords[4], texCoords[5]);
        gl.glVertex3f(xPos + width * scale, yPos + height * scale, 0);
        gl.glTexCoord2f(texCoords[6], texCoords[7]);
        gl.glVertex3f(xPos + width * scale, yPos, 0);
        gl.glEnd();
        
    }
    
    /*
     * Returns float[8] containing texture coordinates for Graphic2D quad
     */
    @Override
    protected float[] getTexArray() {
        return texCoords;
    }
    
    @Override
    protected float[] getTexIndexArray() {
        float index = S2DEngine.textureLoader.getSamplerIndex(this.getSuperTextureKey());
        float [] i = new float [] {
            index,
            index,
            index,
            index
        };
        return i;
    }
}
