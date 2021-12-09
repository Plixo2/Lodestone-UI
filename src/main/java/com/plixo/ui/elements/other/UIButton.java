package com.plixo.ui.elements.other;


import com.plixo.ui.ColorLib;
import com.plixo.ui.elements.UIElement;

/**
 * default button to interact with the UI
 **/
public class UIButton extends UIElement {

    public UIButton() {
        this.setColor(ColorLib.getMainColor());
        this.setOutlineWidth(1);
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {
        this.setOutlineColor(ColorLib.getDarker(getColor()));
        drawDefault();
        drawHoverEffect();
        drawName(mouseX, mouseY);
        super.drawScreen(mouseX, mouseY);
    }

}
