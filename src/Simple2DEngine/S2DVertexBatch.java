/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Simple2DEngine;

import com.jogamp.common.nio.Buffers;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;

/**
 *
 * @author michael.jacobs.adm
 */
class S2DVertexBatch {
    
    protected ArrayList<S2DQuad> quadList;
    protected LinkedList<Integer> updateList;
    protected int updateIndex = -1;
    protected GL2 gl;
    protected int shaderProgram;
    
    protected int vBufferName;
    protected int cBufferName;
    protected int tBufferName;
    protected int rotBufferName;
    protected int rotAttName;
    protected int bufferSize;
    
    protected float cameraX;
    protected int camXUniformLocation;
    protected float cameraY;
    protected int camYUniformLocation;
    
    protected S2DVertexBatch(LinkedList<S2DQuad> qList, int vName, int cName, int tName, int rotBuff, int rotAtt, int shader) {
        updateList = new LinkedList<>();
        quadList = new ArrayList<>();
        quadList.ensureCapacity(qList.size());
        quadList.addAll(qList);
        gl = S2DEngine.gl;
        bufferSize = 0;
              
        vBufferName = vName;
        cBufferName = cName;
        tBufferName = tName;
        rotBufferName = rotBuff;
        rotAttName = rotAtt;
        
        cameraX = 0;
        cameraY = 0;
        
        camXUniformLocation = gl.glGetUniformLocation(shader, "cameraX");
        camYUniformLocation = gl.glGetUniformLocation(shader, "cameraY");
    }
    
    protected void insert(S2DQuad quad, int i) {    
        quadList.add(i, quad);
        
        if(updateIndex == -1 || i < updateIndex) updateIndex = i;
    }
    
    protected void remove(int i) {
        quadList.remove(i);
        
        if(updateIndex == -1 || i < updateIndex) updateIndex = i;
    }
    
    protected void update(S2DQuad quad, int i) {
        quadList.set(i, quad);
        
        updateList.add(i);
    }
    
    protected void setCamera(float x, float y) {
        cameraX = x;
        cameraY = y;
    }
    
    protected void updateShaderLocations(int shader) {
        camXUniformLocation = gl.glGetUniformLocation(shader, "cameraX");
        camYUniformLocation = gl.glGetUniformLocation(shader, "cameraY");
    }
    
    protected void rebuild(LinkedList<S2DQuad> qList) {
        quadList.clear();
        quadList.ensureCapacity(qList.size());
        quadList.addAll(qList);

        updateIndex = 0;
    }
    
    protected void initBuffers() {
        int size = quadList.size();
        
        FloatBuffer vBuff = Buffers.newDirectFloatBuffer(size * 12);
        FloatBuffer cBuff = Buffers.newDirectFloatBuffer(size * 16);
        FloatBuffer tBuff = Buffers.newDirectFloatBuffer(size * 8);
        FloatBuffer rBuff = Buffers.newDirectFloatBuffer(size * 12);
        
        for(S2DQuad quad : quadList) {
            vBuff.put(quad.getVertexArray());
            cBuff.put(quad.getColorArray());
            tBuff.put(quad.getTexArray());
            rBuff.put(quad.getRotArray());
        }
        
        vBuff.rewind();
        cBuff.rewind();
        tBuff.rewind();
        rBuff.rewind();
        
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vBufferName);
        gl.glBufferData(GL.GL_ARRAY_BUFFER, size * 12 * Buffers.SIZEOF_FLOAT * 2, null, GL.GL_DYNAMIC_DRAW);
        gl.glBufferSubData(GL.GL_ARRAY_BUFFER, 0, size * 12 * Buffers.SIZEOF_FLOAT, vBuff);
        
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, cBufferName);
        gl.glBufferData(GL.GL_ARRAY_BUFFER, size * 16 * Buffers.SIZEOF_FLOAT * 2, null, GL.GL_DYNAMIC_DRAW);
        gl.glBufferSubData(GL.GL_ARRAY_BUFFER, 0, size * 16 * Buffers.SIZEOF_FLOAT, cBuff);
        
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, tBufferName);
        gl.glBufferData(GL.GL_ARRAY_BUFFER, size * 8 * Buffers.SIZEOF_FLOAT * 2, null, GL.GL_DYNAMIC_DRAW); 
        gl.glBufferSubData(GL.GL_ARRAY_BUFFER, 0, size * 8 * Buffers.SIZEOF_FLOAT, tBuff);
        
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, rotBufferName);
        gl.glBufferData(GL.GL_ARRAY_BUFFER, size * 12 * Buffers.SIZEOF_FLOAT * 2, null, GL.GL_DYNAMIC_DRAW);
        gl.glBufferSubData(GL.GL_ARRAY_BUFFER, 0, size * 12 * Buffers.SIZEOF_FLOAT, rBuff);
        
        bufferSize = size * 2;
    }
    
    protected void updateVBOs() {
        if(quadList.size() >= bufferSize || quadList.size() < (bufferSize / 4)) {
            initBuffers();
        }
        else {
            int begin = 0;
            int end = 0;
            
            Collections.sort(updateList);

            if(updateList.size() > 0) {
                begin = updateList.getFirst();
                end = updateList.getFirst();
            }

            for(Integer i : updateList) {
                if (i - end > 1 && begin < updateIndex) {
                    updateVBORange(begin, end - begin);
                    begin = i;
                }
                else {
                    end = i;
                }
            }

            if(updateIndex != -1) {
                updateVBORange(updateIndex, quadList.size() - updateIndex);
            }

            updateList.clear();
            updateIndex = -1;
        }
    }
    
    protected void updateVBORange(int index, int length) {
        FloatBuffer vBuff = Buffers.newDirectFloatBuffer(length * 12);
        FloatBuffer cBuff = Buffers.newDirectFloatBuffer(length * 16);
        FloatBuffer tBuff = Buffers.newDirectFloatBuffer(length * 8);
        FloatBuffer rBuff = Buffers.newDirectFloatBuffer(length * 12);
        
        for(int i = 0; i < length; i++) vBuff.put(quadList.get(index + i).getVertexArray());
        for(int i = 0; i < length; i++) cBuff.put(quadList.get(index + i).getColorArray());
        for(int i = 0; i < length; i++) tBuff.put(quadList.get(index + i).getTexArray());
        for(int i = 0; i < length; i++) rBuff.put(quadList.get(index + i).getRotArray());
        
        vBuff.rewind();
        cBuff.rewind();
        tBuff.rewind();
        rBuff.rewind();
        
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vBufferName);
        gl.glBufferSubData(GL.GL_ARRAY_BUFFER, index * 12 * Buffers.SIZEOF_FLOAT, length * 12 * Buffers.SIZEOF_FLOAT, vBuff); 
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, cBufferName);
        gl.glBufferSubData(GL.GL_ARRAY_BUFFER, index * 16 * Buffers.SIZEOF_FLOAT, length * 16 * Buffers.SIZEOF_FLOAT, cBuff);
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, tBufferName);
        gl.glBufferSubData(GL.GL_ARRAY_BUFFER, index * 8 * Buffers.SIZEOF_FLOAT, length * 8 * Buffers.SIZEOF_FLOAT, tBuff);
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, rotBufferName);
        gl.glBufferSubData(GL.GL_ARRAY_BUFFER, index * 12 * Buffers.SIZEOF_FLOAT, length * 12 * Buffers.SIZEOF_FLOAT, rBuff);
    }
    
    protected void draw() {
        String superKey = "";
        int prevIndex = 0;
        
        if(quadList.isEmpty()) return;
        
        gl.glUniform1f(camXUniformLocation, cameraX);
        gl.glUniform1f(camYUniformLocation, cameraY);
        
        gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL2.GL_COLOR_ARRAY);
        gl.glEnableClientState(GL2.GL_TEXTURE_COORD_ARRAY);
        gl.glEnableVertexAttribArray(rotAttName);
        
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vBufferName);
        gl.glVertexPointer(3, GL.GL_FLOAT, 0, 0); 
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, cBufferName);
        gl.glColorPointer(4, GL.GL_FLOAT, 0, 0);       
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, tBufferName);
        gl.glTexCoordPointer(2, GL.GL_FLOAT, 0, 0);
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, rotBufferName);
        gl.glVertexAttribPointer(rotAttName, 3, GL.GL_FLOAT, false, 0, 0);
        
        if(quadList.size() > 0) superKey = quadList.get(0).getSuperTextureKey();
        
        for(int i = 0; i < quadList.size() + 1; i++) {
            if(i == quadList.size() || !quadList.get(i).getSuperTextureKey().equals(superKey)) {
                S2DQuad tempQuad = quadList.get(prevIndex);
                if(tempQuad.isTextured()) {
                    tempQuad.getTexture().bind();
                    gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
                    gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
                }
                gl.glDrawArrays(GL2.GL_QUADS, prevIndex * 4, (i - prevIndex) * 4);
                    
                prevIndex = i;
                if(i < quadList.size()) superKey = quadList.get(i).getSuperTextureKey();
            }
        }
        
        gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL2.GL_COLOR_ARRAY);
        gl.glDisableClientState(GL2.GL_TEXTURE_COORD_ARRAY);
        gl.glDisableVertexAttribArray(rotAttName);
    } 
    
    protected void destroy() {
        int buffers[] = new int[] {
          vBufferName, cBufferName, tBufferName, rotBufferName  
        };
        
        gl.glDeleteBuffers(4, buffers, 0);
    }
}
