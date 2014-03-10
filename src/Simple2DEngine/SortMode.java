/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Simple2DEngine;

/**
 * Specifies method for sorting GraphicObjects in a Simple2DLayer
 *
 * @author Michael Jacobs
 */
public enum SortMode {
    
    /**
     * Sorts items by depth value
     */
    DEPTH_SORTED,
    
    /**
     * Sorts items bottom-up by Y position
     */
    Y_POSITION,
    
    /**
     * Items are not sorted, provided depth value is used
     */
    NONE
}
