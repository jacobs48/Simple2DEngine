/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Simple2DEngine;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author michael.jacobs.adm
 */
public class S2DAnimation {
    
    private LinkedList<S2DQueueable> frameList;
    private String name;
    private float duration;

    
    protected S2DAnimation(String n) {
        frameList = new LinkedList<>();
        name = n;
        duration = 0;
    }
    
    protected void addFrame(String s, float t) {
        S2DSubTexture tempText = S2DEngine.textureLoader.getTexture(s);
        
        if (tempText == null) return;
        
        frameList.add(new S2DAnimationFrame(tempText, t));
        duration += t;
    }
    
    protected LinkedList<S2DQueueable> getFrames() {
        return frameList;
    }
    
    protected String getName() {
        return name;
    }

    
}
