

package Simple2DEngine;

import java.util.Collections;
import java.util.LinkedList;

/**
 *
 * @author Michael Jacobs
 */
public class S2DLayer implements Comparable<S2DLayer> {
    
    protected LinkedList<S2DDrawable> drawableList;
    protected LinkedList<S2DQuad> quadList;
    protected float depth = 0;
    protected final SortMode mode;
    protected float baseZValue = 0;
    protected S2DVertexBatch vertexBatch;
    
    protected static final float MIN_DEPTH_DIF = 0.0000001f; //Minimum diffrence between depth values of Graphic2D objects
    protected static final float LAYER_DEPTH_DIF = 0.1f; //Difference in depth value multiplied by layerDepth

    protected S2DLayer(float d, SortMode m) {
        drawableList = new LinkedList<>();
        quadList = new LinkedList<>();
        depth = d;
        mode = m;
    }
    
    protected LinkedList<S2DQuad> getQuadList() {
        return quadList;
    }
    
    protected boolean contains(S2DDrawable d) {
        return drawableList.contains(d);
    }
    
    public void setDepth(float d) {
        depth = d;
        Collections.sort(S2DEngine.layerList);
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
    
    protected SortMode getSortMode() {
        return mode;
    }
    
    protected void updateGameSpace(float g) {
 
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
        quadList.add(g.getQuad());
        g.updateDrawable();
    }
    
    protected void remove(S2DDrawable g) {
        drawableList.remove(g);
        quadList.remove(g.getQuad());
    }
    
    protected void updateAll(){
        for(S2DDrawable g : drawableList) {
            g.updateDrawable();
        }
    }
    
    protected void sort() {
        switch(mode) {
            case DEPTH_SORTED:
                Collections.sort(quadList, new S2DQuad.ZComparator());
                break;
            case Y_POSITION: 
                Collections.sort(quadList, new S2DQuad.YComparator());
                break;
        }
    }
    
    protected void setBatch(S2DVertexBatch v) {
        vertexBatch = v;
    }
    
    protected boolean batchInitialized() {
        return (vertexBatch != null);
    }
    
    protected void updateBatch() {
        vertexBatch.rebuild(quadList);
    }
    
    public void destroy() {
        for(S2DDrawable graphic : drawableList) {
            graphic.destroy();
        }
        S2DEngine.engine.removeLayer(this);
        
        if (vertexBatch != null) vertexBatch.destroy();
    }
}
