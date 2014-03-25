/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Simple2DEngine;

import java.util.Comparator;

/**
 *
 * @author Mike
 */
abstract class S2DDrawable implements Comparable<S2DDrawable> {
    
    abstract protected float getY();
    
    abstract protected float getZ();
    
    abstract protected void updateDrawable();
    
    abstract protected void updatePolyZ(float z);
    
    abstract public S2DDrawable setLayer(S2DLayer l);
    
    abstract public void destroy();

    @Override
    public int compareTo(S2DDrawable t) {
        return Float.compare(this.getZ(), t.getZ());
    }
    
    static class ReverseYComparator implements Comparator<S2DDrawable> {

        @Override
        public int compare(S2DDrawable t, S2DDrawable t1) {
            return -1 * Float.compare(t.getY(), t1.getY());
        }
   
    }
    
}
