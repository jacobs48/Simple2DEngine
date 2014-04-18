/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Simple2DEngine;

import java.util.LinkedList;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;

/**
 *
 * @author michael.jacobs.adm
 */
public class S2DRendererVertexBuffer extends S2DRenderer {
    
    LinkedList<S2DVertexBatch> batchList;
    GL2 gl = S2DEngine.gl;
    
    protected S2DRendererVertexBuffer() {
        batchList = new LinkedList<>();
    }
    
    @Override
    protected void initializeLayer(S2DLayer l) {
        LinkedList<S2DQuad> qList = l.getQuadList();
        if (qList.size() == 0) return;
        LinkedList<LinkedList<S2DQuad>> batchGenLists = new LinkedList<>();
        LinkedList<S2DQuad> tempList = new LinkedList<>();
        String curSuperKey = qList.getFirst().getSuperTextureKey();
        int[] bufferNames;
        
        for(S2DQuad quad : qList) {
            if (quad.getSuperTextureKey().equals(curSuperKey)) {
                tempList.add(quad);
            }
            else {
                batchGenLists.add(tempList);
                tempList = new LinkedList<>();
                tempList.add(quad);
                curSuperKey = quad.getSuperTextureKey();
            }
        }
        if(tempList.size() > 0) batchGenLists.add(tempList);
        
        bufferNames = new int[batchGenLists.size() * 3];
        
        gl.glGenBuffers(batchGenLists.size() * 3, bufferNames, 0);
        
        for(int i = 0; i < batchGenLists.size(); i++) {
            batchList.add(new S2DVertexBatch(batchGenLists.get(i),
                                             batchGenLists.get(i).get(0).getTexture(),
                                             bufferNames[i * 3],
                                             bufferNames[i * 3 + 1],
                                             bufferNames[i * 3 + 2]));
        }
    }

    @Override
    protected void draw(LinkedList<S2DLayer> l) {
        for(S2DLayer layer : l) {
            this.initializeLayer(layer);
        }
        
        gl.glClearColor(bgR, bgG, bgB, 0);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        
        for(S2DVertexBatch batch : batchList) {
            batch.initBuffers();
            batch.draw();
        }
    }
}
