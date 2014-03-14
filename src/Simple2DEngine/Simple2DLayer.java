

package Simple2DEngine;

import java.util.Collections;
import java.util.LinkedList;

/**
 *
 * @author Michael Jacobs
 */
public class Simple2DLayer implements Comparable<Simple2DLayer> {
    
    private LinkedList<GraphicObject> gObjects;
    private float layerOrder = 0;
    private final SortMode mode;
    private float xCoordMap = 1;
    private float yCoordMap = 1;
    
    private static final float MIN_DEPTH_DIF = 0.0000001f; //Minimum diffrence between depth values of Graphic2D objects
    private static final float LAYER_DEPTH_DIF = 0.1f; //Difference in depth value multiplied by layerDepth

    protected Simple2DLayer(float order, SortMode m) {
        gObjects = new LinkedList<>();
        layerOrder = order;
        mode = m;
    }
    
    public void setOrder(int order) {
        layerOrder = order;
        Collections.sort(Simple2DEngine.layerList);
    }
    
    public void setCoordMapping(float x, float y) {
        xCoordMap = x;
        yCoordMap = y;
    }
    
    protected float getCoordMapX() {
        return xCoordMap;
    }
    
    protected float getCoordMapY() {
        return yCoordMap;
    }

    @Override
    public int compareTo(Simple2DLayer t) {
        return Float.compare(layerOrder, t.getDepth());
    }
    
    protected float getDepth() {
        return layerOrder;
    }
    
    protected void add(GraphicObject g) {
        gObjects.add(g);
        this.updateGraphicObject(g);
    }
    
    protected void remove(GraphicObject g) {
        gObjects.remove(g);
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
    
    //Updates the state of all Graphic2D objects stored in GraphicObject list
    protected void updateZ() {
        this.sort();
        
        float baseDepth = LAYER_DEPTH_DIF * layerOrder;

        for (GraphicObject g : gObjects) {
            g.g2D.depth(baseDepth);
            baseDepth += MIN_DEPTH_DIF;
        } 
    }
    
    
    protected void updateGraphicObject(GraphicObject g) {
        g.g2D.hidden = g.hidden;
        
        if (g.alignment == WindowAlignment.NONE){
            g.g2D.X((g.xPos + g.xOffset) * xCoordMap);
            g.g2D.Y((g.yPos + g.yOffset) * yCoordMap);
        }
        
        g.g2D.setRotXOffset(g.rotXOffset);
        g.g2D.setRotYOffset(g.rotYOffset);
        g.g2D.setRotation(g.rotation);
        
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
            default:
                break;
        }
    }
    
    

}
