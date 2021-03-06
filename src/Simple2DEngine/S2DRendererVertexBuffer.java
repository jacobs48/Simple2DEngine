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
class S2DRendererVertexBuffer extends S2DRenderer {
    
    protected LinkedList<S2DVertexBatch> batchList;
    protected GL2 gl = S2DEngine.gl;
    protected int vertexShader;
    protected int fragmentShader;
    protected int shaderProgram;
    protected int[] sampler;
    protected String[] vertexSource;
    protected String[] fragmentSource;
    
    protected S2DRendererVertexBuffer() {
        batchList = new LinkedList<>();
    }
    
    @Override
    protected void initialize() {
        vertexShader = gl.glCreateShader(GL2.GL_VERTEX_SHADER);
        fragmentShader = gl.glCreateShader(GL2.GL_FRAGMENT_SHADER);
        
        vertexSource = new String[]{
                "attribute vec3 rotation;\n" +
                "float rads;\n" +
                "vec4 pos;\n" +
                "vec4 tempPos;\n" +
                "uniform float cameraX;\n" +
                "uniform float cameraY;\n" +
                "float PI = 3.14159265359;\n" +
                "void main()\n" +
                "{\n" +
                "   gl_FrontColor = gl_Color;\n" +
                "   gl_TexCoord[0] = gl_MultiTexCoord0;\n" +
                "   rads = rotation.x * PI / 180;\n" +
                "   pos = gl_Vertex;\n" +
                "   tempPos = pos;\n" +
                "   tempPos.x -= cameraX;\n" +
                "   tempPos.y -=cameraY;\n" +
                "   rotation.y -= cameraX;\n" +
                "   rotation.z -= cameraY;\n" +
                "   pos.x = cos(rads) * (tempPos.x - rotation.y) - sin(rads) * (tempPos.y - rotation.z) + rotation.y;\n" +
                "   pos.y = sin(rads) * (tempPos.x - rotation.y) + cos(rads) * (tempPos.y - rotation.z) + rotation.z;\n" +
                "   pos = gl_ProjectionMatrix * pos;\n" +
                "   gl_Position = pos;\n" +
                "}\n"
                };
        
        fragmentSource = new String[]{
                "uniform sampler2D tex;\n" +
                "void main()\n" +
                "{\n" +
                "   vec4 color;\n" +
                "   if(gl_TexCoord[0] != -1) color = texture2D(tex, gl_TexCoord[0]);\n" +
                "   else color = vec4(1.0, 1.0, 1.0, 1.0);\n" +
                "   gl_FragColor = color * gl_Color;\n" +
                "}\n"
                };
        
        gl.glShaderSource(vertexShader, 1, vertexSource, null, 0);
        gl.glCompileShader(vertexShader);
        gl.glShaderSource(fragmentShader, 1, fragmentSource, null, 0);
        gl.glCompileShader(fragmentShader);
        
        shaderProgram = gl.glCreateProgram();
        gl.glAttachShader(shaderProgram, vertexShader);
        gl.glAttachShader(shaderProgram, fragmentShader);        
        gl.glLinkProgram(shaderProgram);
        gl.glValidateProgram(shaderProgram);
        gl.glUseProgram(shaderProgram);    
    }
    
    @Override
    protected void initializeLayer(S2DLayer l) {
        LinkedList<S2DQuad> qList = l.getQuadList();
        S2DVertexBatch tempBatch;
        int[] bufferNames = new int[4];
        int rotBuffName;
        
        rotBuffName = gl.glGetAttribLocation(shaderProgram, "rotation");
        gl.glGenBuffers(4, bufferNames, 0);
        tempBatch = new S2DVertexBatch(qList,
                                         bufferNames[0],
                                         bufferNames[1],
                                         bufferNames[2],
                                         bufferNames[3],
                                         rotBuffName,
                                         shaderProgram);
        
        batchList.add(tempBatch);
        l.setBatch(tempBatch);
    }
    
    @Override
    protected void draw(LinkedList<S2DLayer> l) {
        for(S2DLayer layer : l) {
            if (!layer.batchInitialized()) this.initializeLayer(layer);
            layer.updateBatch();
        }
        
        gl.glClearColor(bgR, bgG, bgB, 0);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        
        for(S2DVertexBatch batch : batchList) {
            batch.updateVBOs();
            batch.draw();
        }
    }
}
