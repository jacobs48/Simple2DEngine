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
class S2DVertexBatch {
    
    protected LinkedList<S2DQuad> quadList;
    protected ArrayList<Float> vertexArray;
    protected ArrayList<Float> colorArray;
    protected ArrayList<Float> texCoordArray;
    protected ArrayList<Float> rotateArray;
    protected ArrayList<Boolean> vertexDif;
    protected GL2 gl;
    protected int vBufferName;
    protected int cBufferName;
    protected int tBufferName;
    protected int rotBufferName;
    protected int rotAttName;
    
    protected S2DVertexBatch(LinkedList<S2DQuad> qList, int vName, int cName, int tName, int rotBuff, int rotAtt) {
        vertexArray = new ArrayList<>();
        colorArray = new ArrayList<>();
        texCoordArray = new ArrayList<>();
        vertexDif = new ArrayList<>();
        rotateArray = new ArrayList<>();
        quadList = qList;
        gl = S2DEngine.gl;
              
        vBufferName = vName;
        cBufferName = cName;
        tBufferName = tName;
        rotBufferName = rotBuff;
        rotAttName = rotAtt;
        
        for(S2DQuad quad : qList) {
            for(Float f : quad.getVertexArray()) {
                vertexArray.add(f);
            }
            
            for(Float f : quad.getColorArray()) {
                colorArray.add(f);
            }
            
            for(Float f : quad.getTexArray()) {
                texCoordArray.add(f);
            }
            
            for(Float f : quad.getRotArray()) {
                rotateArray.add(f);
            }
            
            vertexDif.add(Boolean.FALSE);
        }
        
    }
    
    protected void insert(S2DQuad quad, int i) {
        float[] f = quad.getVertexArray();
        
        for(int j = 0; j < 12; j++) {
            vertexArray.add(i * 12 + j, f[j]);
        }
        
        f = quad.getColorArray();
        for(int j = 0; j < 16; j++) {
            colorArray.add(i * 16 + j, f[j]);
        }
        
        f = quad.getTexArray();
        for(int j = 0; j < 8; j++) {
            texCoordArray.add(i * 8 + j, f[j]);
        }
        
        rotateArray.add(i * 3, quad.getCenterY());
        rotateArray.add(i * 3, quad.getCenterX());
        rotateArray.add(i * 3, quad.getRotation());
        
        for(int j = i; j < vertexDif.size(); j++) {
            vertexDif.set(j, Boolean.TRUE);
        }
        vertexDif.add(Boolean.TRUE);
    }
    
    protected void remove(int i) {
        for(int j = 0; j < 12; j++) {
            vertexArray.remove(i);
        }
        
        for(int j = 0; j < 16; j++) {
            colorArray.remove(i);
        }
        
        for(int j = 0; j < 8; j++) {
            texCoordArray.remove(i);
        }
        
        rotateArray.remove(i);
        rotateArray.remove(i);
        rotateArray.remove(i);
        
        for(int j = i; j < vertexDif.size(); j++) {
            vertexDif.set(j, Boolean.TRUE);
        }
        
        vertexDif.remove(vertexDif.size() - 1);
    }
    
    protected void update(S2DQuad quad, int i) {
        float[] f = quad.getVertexArray();
        
        for(int j = 0; j < 12; j++) {
            vertexArray.set(i * 12 + j, f[j]);
        }
        
        f = quad.getColorArray();
        for(int j = 0; j < 16; j++) {
            colorArray.set(i * 16 + j, f[j]);
        }
        
        f = quad.getTexArray();
        for(int j = 0; j < 8; j++) {
            texCoordArray.set(i * 8 + j, f[j]);
        }
        
        rotateArray.set(i, quad.getRotation());
        rotateArray.set(i + 1, quad.getCenterX());
        rotateArray.set(i + 2, quad.getCenterY());
        
        vertexDif.set(i, Boolean.TRUE);
    }
    
    protected void rebuild(LinkedList<S2DQuad> qList) {
        vertexArray = new ArrayList<>();
        colorArray = new ArrayList<>();
        texCoordArray = new ArrayList<>();
        vertexDif = new ArrayList<>();
        rotateArray = new ArrayList<>();
        quadList = qList;
        
        for(S2DQuad quad : qList) {
            for(Float f : quad.getVertexArray()) {
                vertexArray.add(f);
            }
            
            for(Float f : quad.getColorArray()) {
                colorArray.add(f);
            }
            
            for(Float f : quad.getTexArray()) {
                texCoordArray.add(f);
            }
            
            for(Float f : quad.getRotArray()) {
                rotateArray.add(f);
            }
            
            vertexDif.add(Boolean.FALSE);
        }
    }
    
    protected int size() {
        return vertexDif.size();
    }
    
    protected void initBuffers() {
        FloatBuffer vBuff = Buffers.newDirectFloatBuffer(vertexArray.size());
        FloatBuffer cBuff = Buffers.newDirectFloatBuffer(colorArray.size());
        FloatBuffer tBuff = Buffers.newDirectFloatBuffer(texCoordArray.size());
        FloatBuffer rotBuff = Buffers.newDirectFloatBuffer(rotateArray.size());
        
        for(Float f : vertexArray) vBuff.put(f);
        for(Float f : colorArray) cBuff.put(f);
        for(Float f : texCoordArray) tBuff.put(f);
        for(Float f: rotateArray) rotBuff.put(f);
        
        vBuff.rewind();
        cBuff.rewind();
        tBuff.rewind();
        rotBuff.rewind();
        
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vBufferName);
        gl.glBufferData(GL.GL_ARRAY_BUFFER, vertexArray.size() * Buffers.SIZEOF_FLOAT, vBuff, GL.GL_DYNAMIC_DRAW);
        
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, cBufferName);
        gl.glBufferData(GL.GL_ARRAY_BUFFER, colorArray.size() * Buffers.SIZEOF_FLOAT, cBuff, GL.GL_DYNAMIC_DRAW);
        
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, tBufferName);
        gl.glBufferData(GL.GL_ARRAY_BUFFER, texCoordArray.size() * Buffers.SIZEOF_FLOAT, tBuff, GL.GL_DYNAMIC_DRAW);
        
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, rotBufferName);
        gl.glBufferData(GL.GL_ARRAY_BUFFER, rotateArray.size() * Buffers.SIZEOF_FLOAT, rotBuff, GL.GL_DYNAMIC_DRAW);
    }
    
    protected void draw() {
        String superKey = "";
        int prevIndex = 0;
        
        if(quadList.size() == 0) return;
        
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
        
        if(quadList.size() > 0) superKey = quadList.getFirst().getSuperTextureKey();
        
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
    
    protected int getSize() {
        return vertexDif.size();
    }
    
}
