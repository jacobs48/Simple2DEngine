/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Simple2DEngine;

import java.util.LinkedList;

/**
 *
 * @author michael.jacobs.adm
 */
class S2DAnimation {
    
    private LinkedList<S2DQueueable> frameList;
    private float duration;

    
    protected S2DAnimation() {
        frameList = new LinkedList<>();
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
}
