/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Simple2DEngine;

import com.jogamp.opengl.util.texture.Texture;;
import javax.media.opengl.*;

class Graphic2D {
    
    private Texture texture = null;
    private float xPos = 0;
    private float yPos = 0;
    private float width = 0;
    private float height = 0;
    private float textX0 = 0;
    private float textX1 = 1;
    private float textY0 = 0;
    private float textY1 = 1;
    private float r = 1;
    private float g = 1;
    private float b = 1;
    private float a = 1;
    private float scale = 1;
    private GL2 gl = null;
    
    protected Graphic2D(Texture t, GL2 gl2) {
        texture = t;
        gl = gl2;
        width = texture.getWidth();
        height = texture.getHeight();
    }
    
    protected Graphic2D X(float x) {
        xPos = x;
        return this;
    }
    
    protected Graphic2D Y(float y) {
        yPos = y;
        return this;
    }
    
    protected Graphic2D textureMap(float x0, float y0, float x1, float y1) {
        textX0 = x0;
        textX1 = x1;
        textY0 = y0;
        textY1 = y1;
        return this;
    }
    
    protected void draw() {
        texture.bind(gl);
        gl.glBegin(GL2.GL_QUADS);
        gl.glColor4f(r, g, b, a);
        gl.glTexCoord2f(textX0, textY0);
        gl.glVertex3f(xPos, yPos, 0);
        gl.glTexCoord2f(textX0, textY1);
        gl.glVertex3f(xPos, yPos + height * scale, 0);
        gl.glTexCoord2f(textX1, textY1);
        gl.glVertex3f(xPos + width * scale, yPos + height * scale, 0);
        gl.glTexCoord2f(textX1, textY0);
        gl.glVertex3f(xPos + width * scale, yPos, 0);
        gl.glEnd();
    }
    
    protected float[] getVArr() {
        float [] v = new float [] {
            xPos, yPos, 0,
            xPos, yPos + height * scale, 0,
            xPos + width * scale, yPos + height * scale, 0,
            xPos + width * scale, yPos, 0 
        };
        return v;  
    }
    
    protected float[] getCArr() {
        float [] c = new float [] {
            r, g, b, a,
            r, g, b, a,
            r, g, b, a,
            r, g, b, a,
        };      
        return c;
    }
    
    protected float[] getTArr() {
        float [] t = new float [] {
            textX0, textY0,
            textX0, textY1,
            textX1, textY1,
            textX1, textY0  
        }; 
        return t;
    }
    
}
