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
    protected S2DSubTexture defaultSubText;

    protected S2DAnimatedGraphic(S2DTexturedQuad g, S2DLayer l, String d) {
        super(g, l);
        animationTree = new TreeMap<>();
        defaultSubText = S2DEngine.textureLoader.getTexture(d);
    }
    
    public boolean newAnimation(String key) {
        if (animationTree.containsKey(key)) return false;
        else {
            S2DAnimation tempAnim = new S2DAnimation();
            animationTree.put(key, tempAnim);
            return true;
        }
    }
    
    public void addAnimationFrame(String animationKey, String subTextKey, float time) {
        animationTree.get(animationKey).addFrame(subTextKey, time);
    }
    
    
    public void clearQueue() {
        animationQueue.clear();
        S2DEngine.animator.unregister(this);
    }
    
    public void playAnimation(String s) {
        this.clearQueue();
        S2DEngine.animator.register(this);
        animationQueue.addAll(animationTree.get(s).getFrames());
        animationQueue.getFirst().initialize(this);
    }
    
    public void queueAnimation(String s) {
        if (animationQueue.size() == 0) {
            S2DEngine.animator.register(this);
            animationQueue.addAll(animationTree.get(s).getFrames());
            animationQueue.getFirst().initialize(this);
        }
        else {
            animationQueue.addAll(animationTree.get(s).getFrames());
        }
    }
    
    @Override
    protected void updateAnimation(float t) {
        super.updateAnimation(t);
        if(animationQueue.size() == 0) {
            updateFrame(defaultSubText);
        }
    }
    
    protected void updateFrame(S2DSubTexture s) {
        ((S2DTexturedQuad) quad).setTexture(s);
    }
    
}
