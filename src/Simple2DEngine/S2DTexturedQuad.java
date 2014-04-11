

package Simple2DEngine;

import javax.media.opengl.*;

/*
 * Graphic2D describes a 2D image as it is to be rendered by Graphic2DRenderer
 * 
 */
class S2DTexturedQuad extends S2DQuad implements Comparable<S2DQuad> {
    
    private S2DSubTexture texture = null;
    private float texX0 = 0; 
    private float texX1 = 1;
    private float texY0 = 0;
    private float texY1 = 1;
    private boolean textured = true;
    
    protected S2DTexturedQuad(String key) {
        super(0, 0);
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
    

    protected S2DSubTexture getTexture() {
        return texture;
    }
    
    @Override
    protected String getSuperTextureKey() {
        return texture.getSuperTextureKey();
    }
    
    protected boolean isTextured() {
        return textured;
    }
    
    protected S2DTexturedQuad textureMap(float x0, float y0, float x1, float y1) {
        texX0 = x0;
        texX1 = x1;
        texY0 = y0;
        texY1 = y1;
        return this;
    }
    
    protected void draw() {
        
        gl.glLoadIdentity(); 
        if (rotation != 0) {
            gl.glTranslatef(xPos + rotXOffset, yPos + rotYOffset, 0);
            gl.glRotatef(rotation, 0, 0, 1);
            gl.glTranslatef(-(xPos + rotXOffset), -(yPos + rotYOffset), 0);
        }
        gl.glBegin(GL2.GL_QUADS);
        gl.glColor4f(r, g, b, a);
        gl.glTexCoord2f(texX0, texY0);
        gl.glVertex3f(xPos, yPos, z);
        gl.glTexCoord2f(texX0, texY1);
        gl.glVertex3f(xPos, yPos + height * scale, z);
        gl.glTexCoord2f(texX1, texY1);
        gl.glVertex3f(xPos + width * scale, yPos + height * scale, z);
        gl.glTexCoord2f(texX1, texY0);
        gl.glVertex3f(xPos + width * scale, yPos, z);
        gl.glEnd();
        
    }
    
    /*
     * Returns float[12] containing vector coordinates of Graphic2D quad
     */
    protected float[] getVArr() {
        float [] v = new float [] {
            xPos, yPos, z,
            xPos, yPos + height * scale, z,
            xPos + width * scale, yPos + height * scale, z,
            xPos + width * scale, yPos, z 
        };
        return v;  
    }
    
    /*
     * Returns float[16] containing color values for Graphic2D quad
     */
    protected float[] getCArr() {
        float [] c = new float [] {
            r, g, b, a,
            r, g, b, a,
            r, g, b, a,
            r, g, b, a,
        };      
        return c;
    }
    
    /*
     * Returns float[8] containing texture coordinates for Graphic2D quad
     */
    protected float[] getTArr() {
        float [] t = new float [] {
            texX0, texY0,
            texX0, texY1,
            texX1, texY1,
            texX1, texY0  
        }; 
        return t;
    }
}
