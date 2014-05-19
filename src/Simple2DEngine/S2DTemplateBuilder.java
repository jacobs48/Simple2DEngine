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
class S2DTemplateBuilder {
    
    TreeMap<String, S2DGraphicTemplate> templateTree;
    
    protected S2DTemplateBuilder() {
        templateTree = new TreeMap<>();
    }
    
    protected void buildTemplate(String name, String defaultTex) {
        if (!templateTree.containsKey(name)) {
            templateTree.put(name, new S2DGraphicTemplate(name, defaultTex));
        }
    }
    
    protected void newAnimation(String templName, String animName) {
        templateTree.get(templName).newAnimation(animName);
    }
    
    protected void addAnimationFrame(String templName, String animName, String subTex, float duration) {
        templateTree.get(templName).addAnimationFrame(animName, subTex, duration);
    }
    
    protected S2DAnimatedGraphic generateGraphic(String name) {
       return templateTree.get(name).buildGraphic();
    }
    
}
