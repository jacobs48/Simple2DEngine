/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Simple2DEngine;

import java.util.LinkedList;
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
    
    protected void generateBatches(S2DLayer l) {
        LinkedList<S2DQuad> qList = l.getQuadList();
        if (qList.size() == 0) return;
        LinkedList<LinkedList<S2DQuad>> batchGenLists = new LinkedList<>();
        LinkedList<S2DQuad> tempList = new LinkedList<>();
        String curSuperKey = qList.getFirst().getSuperTextureKey();
        int[] bufferNames = new int[] {-1};
        
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
        
        gl.glGenBuffers(batchGenLists.size() * 3, bufferNames, 0);
        
        for(int i = 0; i < batchGenLists.size(); i++) {
            batchList.add(new S2DVertexBatch(batchGenLists.get(i), bufferNames[i * 3], bufferNames[i * 3 + 1], bufferNames[i * 3 + 2]));
        }
    }

    @Override
    protected void draw(LinkedList<S2DQuad> l) {
        
    }
}
