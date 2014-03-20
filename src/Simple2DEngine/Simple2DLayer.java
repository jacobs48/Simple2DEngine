

package Simple2DEngine;

import java.util.Collections;
import java.util.LinkedList;

/**
 *
 * @author Michael Jacobs
 */
public class Simple2DLayer implements Comparable<Simple2DLayer> {
    
    protected LinkedList<GraphicObject> gObjects;
    protected float depth = 0;
    protected final SortMode mode;
    protected float baseZValue = 0;
    
    protected static final float MIN_DEPTH_DIF = 0.0000001f; //Minimum diffrence between depth values of Graphic2D objects
    protected static final float LAYER_DEPTH_DIF = 0.1f; //Difference in depth value multiplied by layerDepth

    protected Simple2DLayer(float d, SortMode m) {
        gObjects = new LinkedList<>();
        depth = d;
        mode = m;
    }
    
    public void setDepth(float d) {
        depth = d;
        Simple2DEngine.engine.updateLayersZ();
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
        return Simple2DEngine.engine.getXSize();
    }
    
    protected float getLayerY0() {
        return 0;
    }
    
    protected float getLayerY1() {
        return Simple2DEngine.engine.getYSize();
    }
    
    protected float getHeight() {
        return Simple2DEngine.engine.getYSize();
    }
    
    protected float getWidth() {
        return Simple2DEngine.engine.getXSize();
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
    public int compareTo(Simple2DLayer t) {
        return Float.compare(depth, t.getDepth());
    }
    
    public float getDepth() {
        return depth;
    }
    
    protected void add(GraphicObject g) {
        gObjects.add(g);
        g.updateGraphic();
    }
    
    protected void remove(GraphicObject g) {
        gObjects.remove(g);
    }
    
    protected void updateAll(){
        int i;
        
        i = Simple2DEngine.layerList.indexOf(this);
        this.updateZ(i);
        
        for(GraphicObject g : gObjects) {
            g.updateGraphic();
        }
    }
    
    protected void sort() {
        switch(mode) {
            case DEPTH_SORTED:
                Collections.sort(gObjects);
                break;
            case Y_POSITION: 
                Collections.sort(gObjects, new GraphicObject.ReverseYComparator());
                break;
        }
    }
    
    //Updates the depth value of all Graphic2D objects stored in GraphicObject list
    protected void updateZ(float i) {
        float baseDepth;
        
        baseZValue = i; 
        baseDepth = LAYER_DEPTH_DIF * baseZValue;
        this.sort();
        
        for (GraphicObject g : gObjects) {
            g.updateG2DZ(baseDepth);
            baseDepth += MIN_DEPTH_DIF;
        } 
    }
    
    protected void updateZ() {
        float baseDepth = LAYER_DEPTH_DIF * baseZValue;
        
        this.sort();
        
        for (GraphicObject g : gObjects) {
            g.updateG2DZ(baseDepth);
            baseDepth += MIN_DEPTH_DIF;
        } 
    }
    
    public void destroy() {
        for(GraphicObject graphic : gObjects) {
            graphic.destroy();
        }
        Simple2DEngine.engine.removeLayer(this);
    }
    
    

}
