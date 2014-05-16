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
    
    protected int sampler[];
    
    protected S2DRendererVBOAdvanced() {
        super();
        
        sampler = new int[] {-1};
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
                                         texIndexAttName,
                                         shaderProgram);
        
        batchList.add(tempBatch);
        l.setBatch(tempBatch);
    }
    
    protected void regenShaders() {
        String samplerList;
        String ifList = "";
        LinkedList<String> keyList = S2DEngine.textureLoader.getSuperKeyList();
        
        samplerList = "uniform sampler2D texSampler[" + keyList.size() + "];\n";
        
        for(int i = 0; i < keyList.size(); i++) {
            ifList += "   if(varTexIndex == " + i +") color = texture2D(texSampler[" + i + "], gl_TexCoord[0]);\n";
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
        gl.glUseProgram(shaderProgram); 
        
        for(int i = 0; i < keyList.size(); i++) {
            int samplerLocation = gl.glGetUniformLocation(shaderProgram, "texSampler[" + i + "]");
       
            gl.glUniform1i(samplerLocation, i);
            gl.glActiveTexture(GL.GL_TEXTURE0 + i);
            gl.glBindTexture(GL.GL_TEXTURE_2D, S2DEngine.textureLoader.getTexUnit(keyList.get(i)));
            gl.getGL3().glBindSampler(i, sampler[0]);

            S2DEngine.textureLoader.setSampler(keyList.get(i), i);
        }
        
        S2DEngine.textureLoader.finishUpdate();
        
        for(S2DVertexBatch batch : batchList) batch.updateShaderLocations(shaderProgram);
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
                "vec4 tempPos;\n" +
                "uniform float cameraX;\n" +
                "uniform float cameraY;\n" +
                "float PI = 3.14159265359;\n" +
                "void main()\n" +
                "{\n" +
                "   gl_FrontColor = gl_Color;\n" +
                "   gl_TexCoord[0] = gl_MultiTexCoord0;\n" +
                "   varTexIndex = textureIndex;\n" + 
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

        gl.getGL3().glGenSamplers(1, sampler, 0);
        gl.getGL3().glSamplerParameteri(sampler[0], GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP_TO_EDGE);
        gl.getGL3().glSamplerParameteri(sampler[0], GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP_TO_EDGE);
        gl.getGL3().glSamplerParameteri(sampler[0], GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
        gl.getGL3().glSamplerParameteri(sampler[0], GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
    }
    
    @Override
    protected void draw(LinkedList<S2DLayer> l) {
        if (S2DEngine.textureLoader.hasUpdated()) regenShaders();
        super.draw(l);
    }

}
