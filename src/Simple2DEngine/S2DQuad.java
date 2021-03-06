

package Simple2DEngine;

import java.util.Comparator;
import javax.media.opengl.*;

/*
 * Graphic2D describes a 2D image as it is to be rendered by Graphic2DRenderer
 * 
 */
class S2DQuad {
    
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
    
    protected float getCenterX() {
        return xPos + (width / 2);
    }
    
    protected float getCenterY() {
        return yPos + (height / 2);
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
        if (rotation != 0) {
            gl.glTranslatef(xPos + rotXOffset, yPos + rotYOffset, 0);
            gl.glRotatef(rotation, 0, 0, 1);
            gl.glTranslatef(-(xPos + rotXOffset), -(yPos + rotYOffset), 0);
        }
        gl.glBegin(GL2.GL_QUADS);
        gl.glColor4f(r, g, b, a);
        gl.glVertex3f(xPos, yPos, 0);
        gl.glVertex3f(xPos, yPos + height * scale, 0);
        gl.glVertex3f(xPos + width * scale, yPos + height * scale, 0);
        gl.glVertex3f(xPos + width * scale, yPos, 0);
        gl.glEnd();
        
        gl.glEnable(GL.GL_TEXTURE_2D);
    }
    
    /*
     * Returns float[12] containing vector coordinates of Graphic2D quad
     */
    protected float[] getVertexArray() {
        float [] v = new float [] {
            xPos, yPos, 0,
            xPos, yPos + height * scale, 0,
            xPos + width * scale, yPos + height * scale, 0,
            xPos + width * scale, yPos, 0 
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
            -1, -1,
            -1, -1,
            -1, -1,
            -1, -1  
        }; 
        return t;
    }
    
    protected float[] getRotArray() {
        float xCenter = xPos + (width / 2) * scale;
        float yCenter = yPos + (height / 2) * scale;
        float [] r = new float [] {
            rotation, xCenter, yCenter,
            rotation, xCenter, yCenter,
            rotation, xCenter, yCenter,
            rotation, xCenter, yCenter
        };
                
        return r;
    }
    
    protected float[] getTexIndexArray() {
        float [] i = new float [] {
            -1,
            -1,
            -1,
            -1
        };
        return i;
    }

    static class ZComparator implements Comparator<S2DQuad> {
        @Override
        public int compare(S2DQuad t, S2DQuad t1) {
            return Float.compare(t.z, t1.z);
        }
    }
    
    static class YComparator implements Comparator<S2DQuad> {

        @Override
        public int compare(S2DQuad t, S2DQuad t1) {
            return -1 * Float.compare(t.yPos, t1.yPos);
        }
   
    }
    
}
