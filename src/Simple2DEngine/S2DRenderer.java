

package Simple2DEngine;

import java.nio.FloatBuffer;
import java.util.Collections;
import java.util.LinkedList;
import javax.media.opengl.*;

/*
 * Graphic2DRenderer handles the internal rendering for S2DEngine.
 * Contains all Graphic2D objects and renders them using rendering
 * mode selected at initialization
 */
class S2DRenderer {
    
    private LinkedList<S2DQuad> graphicList = null;
    private GL2 gl;
    private float [] vertices = null;
    private float [] colors = null;
    private float [] texCoord = null;
    private FloatBuffer verticeBuf = null;
    private FloatBuffer colorBuf = null;
    private FloatBuffer texBuf = null;
    private RenderMode mode;
    private String boundTexture = "";
    private float backgroundR = 0;
    private float backgroundG = 0;
    private float backgroundB = 0;
    
    
    protected S2DRenderer(RenderMode m) {
        gl = S2DEngine.gl;
        graphicList = new LinkedList<>();
        mode = m;
    }
    
    protected void setBackgroundColor(float r, float g, float b) {
        backgroundR = r;
        backgroundG = g;
        backgroundB = b;
    }
    
    //Add Graphic2D object LinkedList in treeMap with provided key value
    protected void addGraphic(S2DQuad g2) {
        graphicList.add(g2);
    }
    
    //Removes Graphic2D object from renderer by key
    protected void removeGraphic(S2DQuad g2) {
        graphicList.remove(g2);
    } 
    
    //Removes entire list of texture for safely unloading texture
    protected void removeAllTex(String key) {
        for(S2DQuad graphic : graphicList) {
            if (graphic.getSuperTextureKey().equals(key)) graphicList.remove(graphic);
        }
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
        Collections.sort(graphicList);
        
        gl.glClearColor(backgroundR, backgroundG, backgroundB, 0);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        
        for(S2DQuad graphic : graphicList) {
            if (!(graphic.isHidden())) {
                if (graphic.isTextured() && (!boundTexture.equals(graphic.getSuperTextureKey()))) {
                    graphic.getTexture().bind();
                }
                
                graphic.draw();
            }
            
        }
    }
    
    //Generates vertex, color, and texture coordinate arrays, buffers and binds them to GL
    protected void generateArrays() {
        /*
        int quadCount = graphicTree.getSize();
        Graphic2D cur2D = null;
        float [] tempArr;
        vertices = new float[quadCount * 12];
        colors = new float [quadCount * 16];
        texCoord = new float [quadCount * 8];
        
        for (int i = 0; i < (quadCount - 1); i++) {
            cur2D = graphicTree.getAt(i);
            
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
        */
    }
    
    
    
}
