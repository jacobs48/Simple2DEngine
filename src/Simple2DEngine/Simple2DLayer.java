

package Simple2DEngine;

import java.util.Collections;
import java.util.LinkedList;

/**
 *
 * @author Michael Jacobs
 */
public class Simple2DLayer implements Comparable<Simple2DLayer> {
    
    private LinkedList<GraphicObject> gObjects;
    private float layerDepth = 0;
    private final SortMode mode;
    private float xCoordMap = 1;
    private float yCoordMap = 1;
    
    private static final float MIN_DEPTH_DIF = 0.0000001f; //Minimum diffrence between depth values of Graphic2D objects
    private static final float LAYER_DEPTH_DIF = 0.1f; //Difference in depth value multiplied by layerDepth

    protected Simple2DLayer(float d, SortMode m) {
        gObjects = new LinkedList<>();
        layerDepth = d;
        mode = m;
    }
    
    public void setDepth(int d) {
        layerDepth = d;
        Collections.sort(Simple2DEngine.layerList);
    }
    
    public void setCoordMapping(float x, float y) {
        xCoordMap = x;
        yCoordMap = y;
    }

    @Override
    public int compareTo(Simple2DLayer t) {
        return Float.compare(layerDepth, t.getDepth());
    }
    
    protected float getDepth() {
        return layerDepth;
    }
    
    protected void add(GraphicObject g) {
        gObjects.add(g);
    }
    
    protected void remove(GraphicObject g) {
        gObjects.remove(g);
    }
    
    protected void sort() {
        switch(mode) {
            case NONE:
                break;
            case DEPTH_SORTED:
                Collections.sort(gObjects);
                break;
            case Y_POSITION: 
                Collections.sort(gObjects, new GraphicObject.ReverseYComparator());
                break;
        }
    }
    
    //Updates the state of all Graphic2D objects stored in GraphicObject list
    protected void updateG2Ds() {
        this.sort();
        if (mode != SortMode.NONE) {
            float baseDepth = LAYER_DEPTH_DIF * layerDepth;

            for (GraphicObject g : gObjects) {
                g.getG2D().depth(baseDepth);
                baseDepth += MIN_DEPTH_DIF;
                
                g.getG2D().X(g.getX() * xCoordMap);
                g.getG2D().Y(g.getY() * yCoordMap);
            }
        }  
    }
    
    

}
