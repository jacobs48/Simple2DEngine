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
public class S2DAnimator {
    
    protected LinkedList<S2DAnimatedGraphic> animationList;
    
    protected S2DAnimator() {
        animationList = new LinkedList<>();
    }
    
    protected void registerAnimated(S2DAnimatedGraphic a) {
        animationList.add(a);
    }
    
    protected void removeAnimated(S2DAnimatedGraphic a) {
        animationList.remove(a);
    }
    
    protected void update(float t) {
        for(S2DAnimatedGraphic graphic : animationList) {
            graphic.updateAnimation(t);
        }
    }
    
}
