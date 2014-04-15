

package Simple2DEngine;

import javax.media.opengl.*;

/*
 * Graphic2D describes a 2D image as it is to be rendered by Graphic2DRenderer
 * 
 */
class S2DQuad implements Comparable<S2DQuad> {
    
    protected float xPos = 0;
    protected float yPos = 0;
    protected float z = 0;
    protected float width = 0;
    protected float height = 0;
    protected float rotation = 0;
    protected float rotXOffset;
    protected float rotYOffset;
    protected float r = 1;
    protected float g = 1;
    protected float b = 1;
    protected float a = 1;
    protected float scale = 1;
    protected boolean hidden = false;
    protected GL2 gl = null;
    
    protected S2DQuad(float w, float h) {
        width = w;
        height = h;
        rotXOffset = width / 2;
        rotYOffset = height / 2;
        gl = S2DEngine.gl;
    }
    
    protected S2DQuad X(float x) {
        xPos = x;
        return this;
    }
    
    protected S2DQuad Y(float y) {
        yPos = y;
        return this;
    }
    
    protected S2DQuad Z(float d) {
        z = d;
        return this;
    }

    protected float getZ() {
        return z;
    }

    protected S2DSubTexture getTexture() {
        return null;
    }

    protected String getSuperTextureKey() {
        return "";
    }
    
    protected boolean isHidden() {
        return hidden;
    }
    
    protected void setHidden(boolean h) {
        hidden = h;
    }

    protected float getWidth() {
        return width;
    }

    protected float getRotation() {
        return rotation;
    }

    protected S2DQuad setRotation(float rotation) {
        this.rotation = rotation;
        return this;
    }

    protected float getA() {
        return a;
    }

    protected void setA(float a) {
        this.a = a;
    }
    
    protected void setColor(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
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
    
    protected boolean isTextured() {
        return false;
    }
    
    protected void draw() {
        
        gl.glDisable(GL.GL_TEXTURE_2D);
        gl.glLoadIdentity(); 
        if (rotation != 0) {
            gl.glTranslatef(xPos + rotXOffset, yPos + rotYOffset, 0);
            gl.glRotatef(rotation, 0, 0, 1);
            gl.glTranslatef(-(xPos + rotXOffset), -(yPos + rotYOffset), 0);
        }
        gl.glBegin(GL2.GL_QUADS);
        gl.glColor4f(r, g, b, a);
        gl.glVertex3f(xPos, yPos, z);
        gl.glVertex3f(xPos, yPos + height * scale, z);
        gl.glVertex3f(xPos + width * scale, yPos + height * scale, z);
        gl.glVertex3f(xPos + width * scale, yPos, z);
        gl.glEnd();
        
        gl.glEnable(GL.GL_TEXTURE_2D);
    }
    
    /*
     * Returns float[12] containing vector coordinates of Graphic2D quad
     */
    protected float[] getVertexArray() {
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
    protected float[] getColorArray() {
        float [] c = new float [] {
            r, g, b, a,
            r, g, b, a,
            r, g, b, a,
            r, g, b, a,
        };      
        return c;
    }
    
    protected float[] getTexArray() {
        float [] t = new float [] {
            0, 0,
            0, 0,
            0, 0,
            0, 0  
        }; 
        return t;
    }

    @Override
    public int compareTo(S2DQuad g) {
        return Float.compare(z, g.getZ());
    }
    
}
