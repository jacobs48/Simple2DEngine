

package Simple2DEngine;

import com.jogamp.opengl.util.texture.Texture;;
import javax.media.opengl.*;

/*
 * Graphic2D describes a 2D image as it is to be rendered by Graphic2DRenderer
 * 
 */
class Graphic2D implements Comparable<Graphic2D> {
    
    private Texture texture = null;
    private float xPos = 0;
    private float yPos = 0;
    private float z = 0;
    private float width = 0;
    private float height = 0;
    private float texX0 = 0; 
    private float texX1 = 1;
    private float texY0 = 0;
    private float texY1 = 1;
    private float rotation = 0;
    private float rotXOffset;
    private float rotYOffset;
    private float r = 1;
    private float g = 1;
    private float b = 1;
    private float a = 0;
    private float scale = 1;
    private boolean hidden = false;
    private GL2 gl = null;
    private String textureKey;
    
    protected Graphic2D(String key) {
        texture = Simple2DEngine.gLoader.getTexture(key);
        textureKey = key;
        gl = Simple2DEngine.gl;
        width = texture.getWidth();
        height = texture.getHeight();
        rotXOffset = width / 2;
        rotYOffset = height / 2;
    }
    
    protected Graphic2D X(float x) {
        xPos = x;
        return this;
    }
    
    protected Graphic2D Y(float y) {
        yPos = y;
        return this;
    }
    
    protected Graphic2D Z(float d) {
        z = d;
        return this;
    }

    protected float getZ() {
        return z;
    }

    protected Texture getTexture() {
        return texture;
    }
    
    protected String getTextureKey() {
        return textureKey;
    }
    
    protected boolean isHidden() {
        return hidden;
    }
    
    protected void setHidden(boolean h) {
        hidden = h;
    }

    protected void setTexture(Texture texture) {
        this.texture = texture;
    }

    protected float getWidth() {
        return width;
    }

    protected float getRotation() {
        return rotation;
    }

    protected Graphic2D setRotation(float rotation) {
        this.rotation = rotation;
        return this;
    }

    protected float getA() {
        return a;
    }

    protected void setA(float a) {
        this.a = a;
    }

    protected float getRotXOffset() {
        return rotXOffset;
    }

    protected void setRotXOffset(float rotXOffset) {
        this.rotXOffset = rotXOffset;
    }

    protected float getRotYOffset() {
        return rotYOffset;
    }

    protected void setRotYOffset(float rotYOffset) {
        this.rotYOffset = rotYOffset;
    }
    
    protected void setWidth(float width) {
        this.width = width;
    }

    protected float getHeight() {
        return height;
    }

    protected void setHeight(float height) {
        this.height = height;
    }

    protected float getScale() {
        return scale;
    }

    protected void setScale(float scale) {
        this.scale = scale;
    }
    
    protected String textureKey() {
        return textureKey;
    } 
    
    protected Graphic2D textureMap(float x0, float y0, float x1, float y1) {
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

    @Override
    public int compareTo(Graphic2D g) {
        return Float.compare(z, g.getZ());
    }
    
}
