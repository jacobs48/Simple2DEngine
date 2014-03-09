

package Simple2DEngine;

import com.jogamp.opengl.util.GLBuffers;
import java.nio.FloatBuffer;
import java.util.LinkedList;
import java.util.TreeMap;
import javax.media.opengl.*;
import java.util.Map;

/*
 * Graphic2DRenderer handles the internal rendering for Simple2DEngine.
 * Contains all Graphic2D objects and renders them using rendering
 * mode selected at initialization
 */
class Graphic2DRenderer {
    
    private TreeMap<String, LinkedList<Graphic2D>> graphicTree = null;  
    private GL2 gl;
    private float [] vertices = null;
    private float [] colors = null;
    private float [] texCoord = null;
    private FloatBuffer verticeBuf = null;
    private FloatBuffer colorBuf = null;
    private FloatBuffer texBuf = null;
    private RenderMode mode;
    
    
    protected Graphic2DRenderer(RenderMode m) {
        gl = Simple2DEngine.gl;
        graphicTree = new TreeMap<>();
        mode = m;
    }
    
    //Add Graphic2D object LinkedList in treeMap with provided key value
    protected void addGraphic(String key, Graphic2D g2) {
        LinkedList<Graphic2D> tempList;
        if (graphicTree.containsKey(key)) {
            tempList = graphicTree.get(key);
            tempList.add(g2);
        }
        else {
            tempList = new LinkedList<>();
            tempList.add(g2);
            graphicTree.put(key, tempList);
        }
    }
    
    //Removes Graphic2D object from renderer by key
    protected void removeGraphic(Graphic2D g2) {
        graphicTree.get(g2.textureKey).remove(g2);
    } 
    
    //Removes entire list of texture for safely unloading texture
    protected void removeTexList(String key) {
        graphicTree.remove(key);
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
        LinkedList<Graphic2D> curList;
        
        gl.glClearColor(0, 0, 0, 0);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        
        for (Map.Entry<String, LinkedList<Graphic2D>> entry : graphicTree.entrySet()) {
            curList = entry.getValue();
            for(Graphic2D graphic : curList) {
                Simple2DEngine.gLoader.bindTexture(entry.getKey());
                gl.glBegin(GL2.GL_QUADS);
                graphic.draw();
                gl.glEnd();
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
