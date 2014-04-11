/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Simple2DEngine;

import java.util.LinkedList;
import java.util.TreeMap;

/**
 *
 * @author michael.jacobs.adm
 */
public class S2DAnimatedGraphic extends S2DGraphic {
    
    protected TreeMap<String, S2DAnimation> animationTree;
    protected LinkedList<S2DAnimation> animationQueue;
    protected S2DSubTexture currentSubText;
    protected S2DSubTexture defaultSubText;

    protected S2DAnimatedGraphic(S2DTexturedQuad g, S2DLayer l, String d) {
        super(g, l);
        animationTree = new TreeMap<>();
        animationQueue = new LinkedList<>();
        defaultSubText = S2DEngine.textureLoader.getTexture(d);
        currentSubText = defaultSubText;
    }
    
    public boolean newAnimation(String key) {
        if (animationTree.containsKey(key)) return false;
        else {
            S2DAnimation tempAnim = new S2DAnimation(key);
            animationTree.put(key, tempAnim);
            return true;
        }
    }
    
    public void addAnimationFrame(String animationKey, String subTextKey, float time) {
        animationTree.get(animationKey).addFrame(subTextKey, time);
    }
    
    
    public void clearQueue() {
        animationQueue = new LinkedList<>();
    }
    
    public void queueAnimation(String s) {
        animationQueue.add(animationTree.get(s));
    }
    
    protected void updateAnimation(float t) {
        float remainder = 0;
        S2DAnimation curAnim = null;
        
        if (!animationQueue.isEmpty()) {
            curAnim = animationQueue.getFirst();
            curAnim.updateTime(t);
            if (curAnim.hasEnded()) {
                remainder = curAnim.remainingDuration();
                animationQueue.remove();
                if (!animationQueue.isEmpty()) {
                    curAnim = animationQueue.getFirst();
                    curAnim.updateTime(remainder);
                }
                else curAnim = null;
            }
        }
        
        if (animationQueue.isEmpty()) currentSubText = defaultSubText;
        else currentSubText = curAnim.currentSubText();
        ((S2DTexturedQuad) quad).setTexture(currentSubText);
    }   
    
}
