/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Simple2DEngine;

import com.jogamp.common.nio.Buffers;
import java.nio.FloatBuffer;
import java.util.LinkedList;
import javax.media.opengl.*;

/**
 *
 * @author michael.jacobs.adm
 */
class S2DRendererVertexArray extends S2DRenderer {
    
    private LinkedList<S2DQuad> quadList;
    private GL2 gl;
    private FloatBuffer vertexBuffer;
    private FloatBuffer colorBuffer;
    private FloatBuffer texBuffer;
    
    protected S2DRendererVertexArray() {
        gl = S2DEngine.gl;
        quadList = new LinkedList<>();
    }
    
    @Override
    protected void initialize() {
        gl.glTexEnvf(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);
        gl.glEnable(GL.GL_TEXTURE_2D);
    }
    
    @Override
    protected void draw(LinkedList<S2DLayer> l) {
        LinkedList<S2DQuad> list = new LinkedList<>();
        
        for(S2DLayer layer : l) {
            list.addAll(layer.getQuadList());
        }
        
        String superKey = list.getFirst().getSuperTextureKey();
        float lastRotation = list.getFirst().getRotation();
        int lastDrawnIndex = 0;
        
        gl.glClearColor(bgR, bgG, bgB, 0);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        
        for(int i = 0; i < list.size() + 1; i++) {
            if (i == list.size() || !list.get(i).getSuperTextureKey().equals(superKey) || list.get(i).getRotation() != lastRotation) {
                S2DQuad tempQuad = list.get(lastDrawnIndex);
                vertexBuffer = Buffers.newDirectFloatBuffer((i - lastDrawnIndex) * 12);
                colorBuffer = Buffers.newDirectFloatBuffer((i - lastDrawnIndex) * 16);
                texBuffer = Buffers.newDirectFloatBuffer((i - lastDrawnIndex) * 8);
                
                for(int j = lastDrawnIndex; j < i; j++) {
                    vertexBuffer.put(list.get(j).getVertexArray());
                    colorBuffer.put(list.get(j).getColorArray());
                    texBuffer.put(list.get(j).getTexArray());
                }
                
                vertexBuffer.rewind();
                colorBuffer.rewind();
                texBuffer.rewind();
                
                if(tempQuad.isTextured()) {
                    gl.glEnable(GL.GL_TEXTURE_2D);
                    tempQuad.getTexture().bind();
                    gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
                    gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
                }
                else {
                    gl.glDisable(GL.GL_TEXTURE_2D);
                }
                
                gl.glLoadIdentity();
                if(tempQuad.getRotation() != 0) {
                    gl.glTranslatef(tempQuad.xPos + tempQuad.rotXOffset, tempQuad.yPos + tempQuad.rotYOffset, 0);
                    gl.glRotatef(tempQuad.rotation, 0, 0, 1);
                    gl.glTranslatef(-(tempQuad.xPos + tempQuad.rotXOffset), -(tempQuad.yPos + tempQuad.rotYOffset), 0);
                }
                
                
                
                gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
                gl.glEnableClientState(GL2.GL_COLOR_ARRAY);
                gl.glEnableClientState(GL2.GL_TEXTURE_COORD_ARRAY);
                
                gl.glVertexPointer(3, GL.GL_FLOAT, 0, vertexBuffer);
                gl.glColorPointer(4, GL.GL_FLOAT, 0, colorBuffer);
                gl.glTexCoordPointer(2, GL.GL_FLOAT, 0, texBuffer);
                
                gl.glDrawArrays(GL2.GL_QUADS, 0, (i - lastDrawnIndex) * 4);
                
                gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
                gl.glDisableClientState(GL2.GL_COLOR_ARRAY);
                gl.glDisableClientState(GL2.GL_TEXTURE_COORD_ARRAY);
                
                lastDrawnIndex = i;
                if(i < list.size()) superKey = list.get(i).getSuperTextureKey();
                if(i < list.size()) lastRotation = list.get(i).getRotation();
            }
        }
    }
    
    
}
