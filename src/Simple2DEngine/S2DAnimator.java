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
    
    protected LinkedList<S2DDrawable> animationList;
    
    protected S2DAnimator() {
        animationList = new LinkedList<>();
    }
    
    protected void register(S2DDrawable a) {
        animationList.add(a);
    }
    
    protected void unregister(S2DDrawable a) {
        animationList.remove(a);
    }
    
    protected void update(float t) {
        for(S2DDrawable graphic : animationList) {
            graphic.updateAnimation(t);
        }
    }
    
}
