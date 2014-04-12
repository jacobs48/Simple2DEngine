/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Simple2DEngine;

/**
 *
 * @author michael.jacobs.adm
 */
public class S2DAnimationFrame implements S2DQueueable {
    
    private S2DSubTexture subText;
    private float duration;
    
    protected S2DAnimationFrame(S2DSubTexture s, float d) {
        subText = s;
        duration = d;
    }

    @Override
    public float getDuration() {
        return duration;
    }
    
    @Override
    public void initialize(S2DDrawable d) {
        ((S2DAnimatedGraphic) d).updateFrame(subText);
    }
    
    @Override
    public void update(S2DDrawable d, float t) {
        
    }

    @Override
    public void terminate(S2DDrawable d) {
        
    }
    
}
