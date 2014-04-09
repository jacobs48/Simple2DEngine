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
public class S2DFrame {
    
    private S2DSubTexture subText;
    private float duration;
    
    protected S2DFrame(S2DSubTexture s, float d) {
        subText = s;
        duration = d;
    }
    
    protected float getDuration(){
        return duration;
    }
    
    protected S2DSubTexture getSubText() {
        return subText;
    }
    
}
