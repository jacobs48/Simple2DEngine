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
    private LinkedList<S2DFrame> queue;
    private S2DFrame currentFrame;
    private String name;
    private float currentTime;
    private float duration;
    private float durationCountdown;
    
    protected S2DAnimation(String n) {
        frameList = new LinkedList<>();
        name = n;
        currentTime = 0;
        duration = 0;
        durationCountdown = 0;
    }
    
    protected void addFrame(S2DFrame f) {
        frameList.add(f);
        duration += f.getDuration();
    }
    
    protected void begin() {
        currentTime = 0;
        queue = new LinkedList<>();
        queue.addAll(frameList);
        currentFrame = queue.get(0);
    }
    
    protected void updateTime(float t) {
        currentTime += t;
        if (queue.size() != 0) {
            if (currentTime > queue.get(0).getDuration()) {
                currentTime -= queue.get(0).getDuration();
                queue.remove();
                if (queue.size() != 0) currentFrame = queue.get(0);
            }
        }
    }
    
    protected S2DFrame currentFrame() {
        return currentFrame;
    }
    
    protected String getName() {
        return name;
    }
    
}
