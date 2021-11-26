package com.plixo.ui.elements.other;


import com.plixo.ui.ColorLib;
import com.plixo.ui.elements.UIElement;

/**
 * default button to interact with the UI
 **/
public class UIButton extends UIElement {

    public UIButton() {
        this.setColor(ColorLib.getMainColor());
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {
        drawDefault();
        gui.drawLinedRoundedRect(x, y, x + width, y + height, getRoundness(), ColorLib.getDarker(getColor()), 1);
        drawHoverEffect();
        drawName(mouseX, mouseY);
        super.drawScreen(mouseX, mouseY);
    }

}
