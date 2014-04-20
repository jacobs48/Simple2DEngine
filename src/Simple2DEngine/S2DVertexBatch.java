/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Simple2DEngine;

import com.jogamp.common.nio.Buffers;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;

/**
 *
 * @author michael.jacobs.adm
 */
class S2DVertexBatch {
    
    private ArrayList<Float> vertexArray;
    private ArrayList<Float> colorArray;
    private ArrayList<Float> texCoordArray;
    private ArrayList<Integer> texNameArray;
    private ArrayList<Boolean> vertexDif;
    private GL2 gl;
    private int vBufferName;
    private int cBufferName;
    private int tBufferName;
    private int texTargetName;
    
    protected S2DVertexBatch(LinkedList<S2DQuad> qList, int v, int c, int t, int texName) {
        vertexArray = new ArrayList<>();
        colorArray = new ArrayList<>();
        texCoordArray = new ArrayList<>();
        vertexDif = new ArrayList<>();
        texNameArray = new ArrayList<>();
        gl = S2DEngine.gl;
        
        texTargetName = texName;
              
        vBufferName = v;
        cBufferName = c;
        tBufferName = t;
        
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
            
            if(quad.getTexture() != null) {
                texNameArray.add(quad.getTexture().getTexTarget());
            }
            else {
                texNameArray.add(-1);
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
        
        texNameArray.add(i, quad.getTexture().getTexTarget());
        
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
        
        texNameArray.remove(i);
        
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
        
        texNameArray.set(i, quad.getTexture().getTexTarget());
        
        vertexDif.set(i, Boolean.TRUE);
    }
    
    protected void rebuild(LinkedList<S2DQuad> qList) {
        vertexArray = new ArrayList<>();
        colorArray = new ArrayList<>();
        texCoordArray = new ArrayList<>();
        vertexDif = new ArrayList<>();
        texNameArray = new ArrayList<>();
        
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
            
            if(quad.isTextured()) texNameArray.add(quad.getTexture().getTexTarget());
            
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
        IntBuffer tNameBuff = Buffers.newDirectIntBuffer(texNameArray.size());
        
        for(Float f : vertexArray) {
            vBuff.put(f);
        }
        
        for(Float f : colorArray) {
            cBuff.put(f);
        }
        
        for(Float f : texCoordArray) {
            tBuff.put(f);
        }
        
        for(Integer i: texNameArray) {
            tNameBuff.put(i);
        }
        
        vBuff.rewind();
        cBuff.rewind();
        tBuff.rewind();
        tNameBuff.rewind();
        
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vBufferName);
        gl.glBufferData(GL.GL_ARRAY_BUFFER, vertexArray.size() * Buffers.SIZEOF_FLOAT, vBuff, GL.GL_DYNAMIC_DRAW);
        
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, cBufferName);
        gl.glBufferData(GL.GL_ARRAY_BUFFER, colorArray.size() * Buffers.SIZEOF_FLOAT, cBuff, GL.GL_DYNAMIC_DRAW);
        
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, tBufferName);
        gl.glBufferData(GL.GL_ARRAY_BUFFER, texCoordArray.size() * Buffers.SIZEOF_FLOAT, tBuff, GL.GL_DYNAMIC_DRAW);
        
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, texTargetName);
        gl.glBufferData(GL.GL_ARRAY_BUFFER, texNameArray.size() * Buffers.SIZEOF_INT, tNameBuff, GL.GL_DYNAMIC_DRAW);
    }
    
    protected void draw() {
        gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL2.GL_COLOR_ARRAY);
        gl.glEnableClientState(GL2.GL_TEXTURE_COORD_ARRAY);
        
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vBufferName);
        gl.glVertexPointer(3, GL.GL_FLOAT, 0, 0);       
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, cBufferName);
        gl.glColorPointer(4, GL.GL_FLOAT, 0, 0);       
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, tBufferName);
        gl.glTexCoordPointer(2, GL.GL_FLOAT, 0, 0);
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, texTargetName);
        gl.glVertexAttribPointer(vBufferName, vBufferName, tBufferName, true, vBufferName, tBufferName);
        
        gl.glDrawArrays(GL2.GL_QUADS, 0, vertexArray.size());
        
        gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL2.GL_COLOR_ARRAY);
        gl.glDisableClientState(GL2.GL_TEXTURE_COORD_ARRAY);
    }
    
    protected int getSize() {
        return vertexDif.size();
    }
    
}
