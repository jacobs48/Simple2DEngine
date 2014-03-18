

package Simple2DEngine;

import java.util.Collections;
import java.util.LinkedList;

/**
 *
 * @author Michael Jacobs
 */
public class Simple2DLayer implements Comparable<Simple2DLayer> {
    
    private LinkedList<GraphicObject> gObjects;
    private float depth = 0;
    private final SortMode mode;
    private float xCoordMap = 1;
    private float yCoordMap = 1;
    private float backgroundR = 1;
    private float backgroundG = 1;
    private float backgroundB = 1;
    private boolean fillBackground = false;
    
    private static final float MIN_DEPTH_DIF = 0.0000001f; //Minimum diffrence between depth values of Graphic2D objects
    private static final float LAYER_DEPTH_DIF = 0.1f; //Difference in depth value multiplied by layerDepth

    protected Simple2DLayer(float d, SortMode m) {
        gObjects = new LinkedList<>();
        depth = d;
        mode = m;
    }
    
    public void setDepth(float d) {
        depth = d;
        Simple2DEngine.engine.updateLayersZ();
    }
    
    public void setPixelSpace(float x, float y) {
        xCoordMap = 1/x;
        yCoordMap = 1/y;
    }
    
    protected float getPixelSpaceX() {
        return xCoordMap;
    }
    
    protected float getPixelSpaceY() {
        return yCoordMap;
    }

    @Override
    public int compareTo(Simple2DLayer t) {
        return Float.compare(depth, t.getDepth());
    }
    
    protected float getDepth() {
        return depth;
    }
    
    protected void add(GraphicObject g) {
        gObjects.add(g);
        this.updateGraphicObject(g);
    }
    
    protected void remove(GraphicObject g) {
        gObjects.remove(g);
    }
    
    protected void updateAll(){
        int i;
        
        i = Simple2DEngine.layerList.indexOf(this);
        this.updateZ(i);
        
        for(GraphicObject g : gObjects) {
            this.updateGraphicObject(g);
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
        this.sort();
        
        float baseDepth = LAYER_DEPTH_DIF * i;

        for (GraphicObject g : gObjects) {
            g.g2D.Z(baseDepth);
            baseDepth += MIN_DEPTH_DIF;
        } 
    }
    
    
    protected void updateGraphicObject(GraphicObject g) {
        g.g2D.setHidden(g.hidden);
        g.g2D.setRotXOffset(g.rotXOffset);
        g.g2D.setRotYOffset(g.rotYOffset);
        g.g2D.setRotation(g.rotation);    
        g.g2D.setA((100 - g.transparency) / 100);
        
        switch (g.alignment) {
            case LEFT_UPPER:
                g.g2D.X(g.xOffset * xCoordMap);
                g.g2D.Y(Simple2DEngine.engine.getYSize() - g.g2D.getHeight() + g.yOffset * yCoordMap);
                break;
            case LEFT_LOWER:
                g.g2D.X(g.xOffset * xCoordMap);
                g.g2D.Y(g.yOffset * yCoordMap);
                break;
            case LEFT_CENTERED:
                g.g2D.X(g.xOffset * xCoordMap);
                g.g2D.Y((Simple2DEngine.engine.getYSize() / 2) - (g.g2D.getHeight() / 2) + g.yOffset * yCoordMap);
                break;
            case RIGHT_UPPER:
                g.g2D.X(Simple2DEngine.engine.getXSize() - g.g2D.getWidth() + g.xOffset * xCoordMap);
                g.g2D.Y(Simple2DEngine.engine.getYSize() - g.g2D.getHeight() + g.yOffset * yCoordMap);
                break;
            case RIGHT_LOWER:
                g.g2D.X(Simple2DEngine.engine.getXSize() - g.g2D.getWidth() + g.xOffset * xCoordMap);
                g.g2D.Y(g.yOffset * yCoordMap);
                break;
            case RIGHT_CENTERED:
                g.g2D.X(Simple2DEngine.engine.getXSize() - g.g2D.getWidth() + g.xOffset * xCoordMap);
                g.g2D.Y((Simple2DEngine.engine.getYSize() / 2) - (g.g2D.getHeight() / 2) + g.yOffset * yCoordMap);
                break;
            case TOP_CENTERED:
                g.g2D.X((Simple2DEngine.engine.getXSize() / 2) - (g.g2D.getWidth() / 2) + g.xOffset * xCoordMap);
                g.g2D.Y(Simple2DEngine.engine.getYSize() - g.g2D.getHeight()+ g.yOffset * yCoordMap);
                break;
            case BOTTOM_CENTERED:
                g.g2D.X((Simple2DEngine.engine.getXSize() / 2) - (g.g2D.getWidth() / 2) + g.xOffset * xCoordMap);
                g.g2D.Y(g.yOffset * yCoordMap);
                break;
            case CENTERED:
                g.g2D.X((Simple2DEngine.engine.getXSize() / 2) - (g.g2D.getWidth() / 2) + g.xOffset * xCoordMap);
                g.g2D.Y((Simple2DEngine.engine.getYSize() / 2) - (g.g2D.getHeight() / 2)+ g.yOffset * yCoordMap);
                break;
            case NONE:
                g.g2D.X((g.xPos + g.xOffset) * xCoordMap);
                g.g2D.Y((g.yPos + g.yOffset) * yCoordMap);
                break;
            default:
                break;
        }
    }
    
    public void destroy() {
        for(GraphicObject graphic : gObjects) {
            graphic.destroy();
        }
        Simple2DEngine.engine.removeLayer(this);
    }
    
    

}
