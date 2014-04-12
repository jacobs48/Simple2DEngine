/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Simple2DEngine;

/**
 *
 * @author Mike
 */
public interface S2DQueueable {
    
    public float getDuration();
    
    public void initialize(S2DDrawable d);
    
    public void update(S2DDrawable d, float t);
    
    public void terminate(S2DDrawable d);
    
}
