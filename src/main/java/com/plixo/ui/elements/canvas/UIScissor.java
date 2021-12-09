package com.plixo.ui.elements.canvas;

import com.plixo.ui.elements.UIElement;
import com.plixo.util.Vector2f;

public class UIScissor extends UICanvas {

    boolean enabled = true;
    @Override
    public void drawScreen(float mouseX, float mouseY) {
        gui.pushMatrix();
        float[] mat = gui.getModelViewMatrix();
        Vector2f globalPosMIN = gui.toScreenSpace(mat,x,y);
        Vector2f globalPosMAX = gui.toScreenSpace(mat,x + width,y + height);
        if(enabled) {
            gui.createScissorBox(globalPosMIN.x, globalPosMIN.y, globalPosMAX.x, globalPosMAX.y);
            gui.activateScissor();
        }
        super.drawScreen(mouseX, mouseY);
        if(enabled) {
            gui.deactivateScissor();
        }
        gui.popMatrix();
    }


    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
