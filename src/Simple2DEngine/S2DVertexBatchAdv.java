/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Simple2DEngine;

import com.jogamp.common.nio.Buffers;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;

/**
 *
 * @author michael.jacobs.adm
 */
public class S2DVertexBatchAdv extends S2DVertexBatch {
    
    protected ArrayList<Float> texIndexArray;
    protected int texIndexAttName;
    protected int texIndexBuffName;
    
    protected S2DVertexBatchAdv(LinkedList<S2DQuad> qList, int vName, int cName, int tName, int rotBuff, int rotAtt, int indBuff, int indAtt) {
        super(qList, vName, cName, tName, rotBuff, rotAtt);
        
        texIndexArray = new ArrayList<>();
        
        texIndexBuffName = indBuff;
        texIndexAttName = indAtt;
        
        for(S2DQuad quad : qList) {
            for (Float f : quad.getTexIndexArray()) {
                texIndexArray.add(f);
            }
        }
    }
    
    @Override
    protected void rebuild(LinkedList<S2DQuad> qList) {
        super.rebuild(qList);
        
        texIndexArray = new ArrayList<>();
        
        for(S2DQuad quad : qList) {
            for (Float f : quad.getTexIndexArray()) {
                texIndexArray.add(f);
            }
        }
    }
    
    @Override
    protected void initBuffers() {
        super.initBuffers();
        
        FloatBuffer texIndexBuffer = Buffers.newDirectFloatBuffer(texIndexArray.size());
        
        for(Float f : texIndexArray) texIndexBuffer.put(f);
        texIndexBuffer.rewind();
        
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, texIndexBuffName);
        gl.glBufferData(GL.GL_ARRAY_BUFFER, texIndexArray.size() * Buffers.SIZEOF_FLOAT, texIndexBuffer, GL.GL_DYNAMIC_DRAW);
    }
    
    @Override
    protected void draw() {
        gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL2.GL_COLOR_ARRAY);
        gl.glEnableClientState(GL2.GL_TEXTURE_COORD_ARRAY);
        gl.glEnableVertexAttribArray(rotAttName);
        gl.glEnableVertexAttribArray(texIndexAttName);
        
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vBufferName);
        gl.glVertexPointer(3, GL.GL_FLOAT, 0, 0); 
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, cBufferName);
        gl.glColorPointer(4, GL.GL_FLOAT, 0, 0);       
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, tBufferName);
        gl.glTexCoordPointer(2, GL.GL_FLOAT, 0, 0);
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, rotBufferName);
        gl.glVertexAttribPointer(rotAttName, 3, GL.GL_FLOAT, false, 0, 0);
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, texIndexBuffName);
        gl.glVertexAttribPointer(texIndexAttName, 1, GL.GL_FLOAT, false, 0, 0);
        
        gl.glDrawArrays(GL2.GL_QUADS, 0, vertexArray.size() / 3);
        
        gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL2.GL_COLOR_ARRAY);
        gl.glDisableClientState(GL2.GL_TEXTURE_COORD_ARRAY);
        gl.glDisableVertexAttribArray(rotAttName);
        gl.glDisableVertexAttribArray(texIndexAttName);
    }
    
}