/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Simple2DEngine;

import GenericLinkedList.*;
import com.jogamp.opengl.util.GLBuffers;
import java.nio.FloatBuffer;
import javax.media.opengl.*;

public class Graphic2DRenderer {
    
    private GenericLinkedList<Graphic2D> graphicList = null;  
    private GL2 gl;
    private float [] vertices = null;
    private float [] colors = null;
    private float [] texCoord = null;
    private FloatBuffer verticeBuf = null;
    private FloatBuffer colorBuf = null;
    private FloatBuffer texBuf = null;
    private RenderMode mode;
    
    
    protected Graphic2DRenderer(GL2 gl2, RenderMode m) {
        gl = gl2;
        graphicList = new GenericLinkedList<>();
        mode = m;
    }
    
    protected int addGraphic(Graphic2D g2) {
        graphicList.add(g2);
        return graphicList.getSize() - 1;
    }
    
    protected void removeGraphic(int n) {
        graphicList.remove(n);
    }
    
    protected void draw() {
        switch (mode) {
            case IMMEDIATE:
                drawImmediate();
                break;
            case VERTEX_ARRAY:
                break;
            case VERTEX_BUFFER_OBJECT:
                break;
        }
    }
    
    private void drawImmediate() {
        int size = graphicList.getSize();
        
        gl.glClearColor(0, 0, 0, 0);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        for (int i = 0; i < size; i++) {
            graphicList.getAt(i).draw();
        }
    }
    
    protected void generateArrays() {
        int quadCount = graphicList.getSize();
        Graphic2D cur2D = null;
        float [] tempArr;
        vertices = new float[quadCount * 12];
        colors = new float [quadCount * 16];
        texCoord = new float [quadCount * 8];
        
        for (int i = 0; i < (quadCount - 1); i++) {
            cur2D = graphicList.getAt(i);
            
            tempArr = cur2D.getVArr();
            for(int n = 0; n < 12; n++) {
                vertices [i * 12 + n] = tempArr[n];
            }
            
            tempArr = cur2D.getCArr();
            for(int n = 0; n < 16; n++) {
                colors [i * 16 + n] = tempArr[n];
            }
            
            tempArr = cur2D.getTArr();
            for(int n = 0; n < 8; n++) {
                texCoord [i * 8 + n] = tempArr[n];
            }
        }
        
        verticeBuf = GLBuffers.newDirectFloatBuffer(vertices);
        colorBuf = GLBuffers.newDirectFloatBuffer(colors);
        texBuf = GLBuffers.newDirectFloatBuffer(texCoord);
        
        verticeBuf.rewind();
        colorBuf.rewind();
        texBuf.rewind();
        
        gl.glVertexPointer(3, GL.GL_FLOAT, 0, verticeBuf);
        gl.glColorPointer(4, GL.GL_FLOAT, 0, colorBuf);
        gl.glTexCoordPointer(2, GL.GL_FLOAT, 0, texBuf);
    }
    
    
    
}
