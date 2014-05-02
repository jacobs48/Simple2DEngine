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
public class S2DRendererVBOAdvanced extends S2DRendererVertexBuffer{
    
    protected S2DRendererVBOAdvanced() {
        super();
    }
    
    @Override
    protected void initializeLayer(S2DLayer l) {
        LinkedList<S2DQuad> qList = l.getQuadList();
        S2DVertexBatch tempBatch;
        int[] bufferNames = new int[5];
        int rotAttName;
        int texIndexAttName;
        
        rotAttName = gl.glGetAttribLocation(shaderProgram, "rotation");
        texIndexAttName = gl.glGetAttribLocation(shaderProgram, "textureIndex");
        gl.glGenBuffers(5, bufferNames, 0);
        tempBatch = new S2DVertexBatchAdv(qList,
                                         bufferNames[0],
                                         bufferNames[1],
                                         bufferNames[2],
                                         bufferNames[3],
                                         rotAttName,
                                         bufferNames[4],
                                         texIndexAttName);
        
        batchList.add(tempBatch);
        l.setBatch(tempBatch);
    }
    
    protected void regenShaders() {
        String samplerList = "";
        String ifList = "";
        LinkedList<String> keyList = S2DEngine.textureLoader.getSuperKeyList();
        
        for(int i = 0; i < keyList.size(); i++) {
            samplerList += "uniform sampler2D " + keyList.get(i) + ";\n";
            ifList += "  if(varTexIndex == " + i +") color = texture2D(" + keyList.get(i) + ", gl_TexCoord[0]);\n";
        }
        
        fragmentSource = new String[]{
            samplerList +
            "varying float varTexIndex;\n" +
            "void main()\n" +
            "{\n" +
            "   vec4 color;\n" +
            ifList +
            "   if(varTexIndex == -1) color = vec4(1.0, 1.0, 1.0, 1.0);\n" +
            "   gl_FragColor = color * gl_Color;\n" +
            "}\n"
        };
        
        gl.glShaderSource(fragmentShader, 1, fragmentSource, null, 0);
        gl.glCompileShader(fragmentShader);
        gl.glAttachShader(shaderProgram, fragmentShader);        
        gl.glLinkProgram(shaderProgram);
        gl.glValidateProgram(shaderProgram);
        gl.glUseProgram(shaderProgram); 
        
        
        for (String key : keyList) {
            S2DEngine.textureLoader.bindSampler(shaderProgram, 1, key);
        }
        S2DEngine.textureLoader.finishUpdate();
    }
    
    @Override
    protected void initialize() {
        vertexShader = gl.glCreateShader(GL2.GL_VERTEX_SHADER);
        fragmentShader = gl.glCreateShader(GL2.GL_FRAGMENT_SHADER);
        
        vertexSource = new String[]{
                "attribute vec3 rotation;\n" +
                "attribute float textureIndex;\n" +
                "varying float varTexIndex;\n" +
                "float rads;\n" +
                "vec4 pos;\n" +
                "float PI = 3.14159265359;\n" +
                "void main()\n" +
                "{\n" +
                "   gl_FrontColor = gl_Color;\n" +
                "   gl_TexCoord[0] = gl_MultiTexCoord0;\n" +
                "   varTexIndex = textureIndex;\n" + 
                "   rads = rotation.x * PI / 180;\n" +
                "   pos = gl_Vertex;\n" +
                "   pos.x = cos(rads) * (gl_Vertex.x - rotation.y) - sin(rads) * (gl_Vertex.y - rotation.z) + rotation.y;\n" +
                "   pos.y = sin(rads) * (gl_Vertex.x - rotation.y) + cos(rads) * (gl_Vertex.y - rotation.z) + rotation.z;\n" +
                "   pos = gl_ProjectionMatrix * pos;\n" +
                "   gl_Position = pos;\n" +
                "}\n"
                };
        
        fragmentSource = new String[]{
                "varying float varTexIndex;\n" +
                "void main()\n" +
                "{\n" +
                "   vec4 color;\n" +
                "   if(gl_TexCoord[0] == -1) color = vec4(1.0, 1.0, 1.0, 1.0);\n" +
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
    protected void draw(LinkedList<S2DLayer> l) {
        if (S2DEngine.textureLoader.hasUpdated()) regenShaders();
        for(S2DLayer layer : l) {
            if (!layer.batchInitialized()) this.initializeLayer(layer);
            layer.updateBatch();
        }
        
        gl.glClearColor(bgR, bgG, bgB, 0);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        
        for(S2DVertexBatch batch : batchList) {
            batch.initBuffers();
            batch.draw();
        }
    }
}
