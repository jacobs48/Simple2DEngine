/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Simple2DEngine;

import java.util.TreeMap;

/**
 *
 * @author michael.jacobs.adm
 */
class S2DGraphicTemplate {
    private String name;
    private String defaultTex;
    private TreeMap<String, S2DAnimation> animationTree;
    
    protected S2DGraphicTemplate(String name, String texName) {
        this.name = name;
        defaultTex = texName;
        animationTree = new TreeMap<>();
    }
    
    protected boolean newAnimation(String key) {
        if (animationTree.containsKey(key)) return false;
        else {
            S2DAnimation tempAnim = new S2DAnimation();
            animationTree.put(key, tempAnim);
            return true;
        }
    }
    
    protected void addAnimationFrame(String animationKey, String subTextKey, float time) {
        animationTree.get(animationKey).addFrame(subTextKey, time);
    }
    
    protected S2DAnimatedGraphic buildGraphic() {
        S2DAnimatedGraphic tempGraphic = S2DEngine.engine.newS2DAnimatedGraphic(defaultTex);
        tempGraphic.addAnimationTree(animationTree);
        return tempGraphic;
    }
}
