

package Simple2DEngine;

import java.util.Collections;
import java.util.LinkedList;

/**
 *
 * @author Michael Jacobs
 */
public class S2DLayer implements Comparable<S2DLayer> {
    
    protected LinkedList<S2DDrawable> drawableList;
    protected float depth = 0;
    protected final SortMode mode;
    protected float baseZValue = 0;
    
    protected static final float MIN_DEPTH_DIF = 0.0000001f; //Minimum diffrence between depth values of Graphic2D objects
    protected static final float LAYER_DEPTH_DIF = 0.1f; //Difference in depth value multiplied by layerDepth

    protected S2DLayer(float d, SortMode m) {
        drawableList = new LinkedList<>();
        depth = d;
        mode = m;
    }
    
    public void setDepth(float d) {
        depth = d;
        S2DEngine.engine.updateLayersZ();
    }
    
    protected float translateX(float x) {
        return x;
    }
    
    protected float translateY(float y) {
        return y;
    }
    
    protected float getLayerX0() {
        return 0;
    }
    
    protected float getLayerX1() {
        return S2DEngine.engine.getWindowWidth();
    }
    
    protected float getLayerY0() {
        return 0;
    }
    
    protected float getLayerY1() {
        return S2DEngine.engine.getWindowHeight();
    }
    
    protected float getHeight() {
        return S2DEngine.engine.getWindowHeight();
    }
    
    protected float getWidth() {
        return S2DEngine.engine.getWindowWidth();
    }   
    
    protected float getScale() {
        return 1;
    }
    
    protected void updateGameSpace(float x) {
        
    }
    
    protected void updateCamera(float x, float y) {
        
    }
    
    public void setScale(float s) {
        
    }

    @Override
    public int compareTo(S2DLayer t) {
        return Float.compare(depth, t.getDepth());
    }
    
    public float getDepth() {
        return depth;
    }
    
    protected void add(S2DDrawable g) {
        drawableList.add(g);
        g.updateDrawable();
    }
    
    protected void remove(S2DDrawable g) {
        drawableList.remove(g);
    }
    
    protected void updateAll(){
        int i;
        
        i = S2DEngine.layerList.indexOf(this);
        this.updateZ(i);
        
        for(S2DDrawable g : drawableList) {
            g.updateDrawable();
        }
    }
    
    protected void sort() {
        switch(mode) {
            case DEPTH_SORTED:
                Collections.sort(drawableList);
                break;
            case Y_POSITION: 
                Collections.sort(drawableList, new S2DDrawable.ReverseYComparator());
                break;
        }
    }
    
    //Updates the depth value of all Graphic2D objects stored in S2DGraphic list
    protected void updateZ(float i) {
        float baseDepth;
        
        baseZValue = i; 
        baseDepth = LAYER_DEPTH_DIF * baseZValue;
        this.sort();
        
        for (S2DDrawable d : drawableList) {
            d.updatePolyZ(baseDepth);
            baseDepth += MIN_DEPTH_DIF;
        } 
    }
    
    protected void updateZ() {
        float baseDepth = LAYER_DEPTH_DIF * baseZValue;
        
        this.sort();
        
        for (S2DDrawable d : drawableList) {
            d.updatePolyZ(baseDepth);
            baseDepth += MIN_DEPTH_DIF;
        } 
    }
    
    public void destroy() {
        for(S2DDrawable graphic : drawableList) {
            graphic.destroy();
        }
        S2DEngine.engine.removeLayer(this);
    }
    
    

}
