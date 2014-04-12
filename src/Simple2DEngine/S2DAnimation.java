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
    
    private LinkedList<S2DFrame> frameList;
    private LinkedList<S2DFrame> frameQueue;
    private S2DFrame currentFrame;
    private String name;
    private float currentTime;
    private float duration;
    private float durationCountdown;
    
    protected S2DAnimation(String n) {
        frameList = new LinkedList<>();
        frameQueue = new LinkedList<>();
        name = n;
        currentTime = 0;
        duration = 0;
        durationCountdown = 0;
    }
    
    protected void addFrame(String s, float t) {
        S2DSubTexture tempText = S2DEngine.textureLoader.getTexture(s);
        
        if (tempText == null) return;
        
        frameList.add(new S2DFrame(tempText, t));
        duration += t;
    }
    
    protected S2DAnimation begin() {
        currentTime = 0;
        frameQueue = new LinkedList<>();
        frameQueue.addAll(frameList);
        currentFrame = frameQueue.get(0);
        return this;
    }
    
    protected void updateTime(float t) {
        currentTime += t;
        if (frameQueue.size() != 0 && currentTime > frameQueue.get(0).getDuration()) {
            currentTime -= frameQueue.get(0).getDuration();
            frameQueue.remove(0);
            if (frameQueue.size() != 0) currentFrame = frameQueue.get(0);
        }
    }
    
    protected S2DFrame currentFrame() {
        return currentFrame;
    }
    
    protected S2DSubTexture currentSubText() {
        if (currentFrame == null) return null;
        else return currentFrame.getSubText();
    }
    
    protected String getName() {
        return name;
    }
    
    protected boolean hasEnded() {
        return (frameQueue.size() == 0);
    }
    
    protected float remainingDuration() {
        return currentTime;
    }
    
}
