/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Simple2DEngine;

/**
 *
 * @author Michael Jacobs
 */
class S2DRepeatAnimation implements S2DQueueable {
    
    private String animation;
    
    protected S2DRepeatAnimation(String animName) {
        animation = animName;
    }

    @Override
    public float getDuration() {
        return 0;
    }

    @Override
    public void initialize(S2DDrawable d) {
        
    }

    @Override
    public void update(S2DDrawable d, float t) {
        
    }

    @Override
    public void terminate(S2DDrawable d) {
        ((S2DAnimatedGraphic)d).queueAnimation(animation);
        ((S2DAnimatedGraphic)d).queueAnimation(this);
        
    }

}
