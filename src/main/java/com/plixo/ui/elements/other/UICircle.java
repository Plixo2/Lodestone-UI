package com.plixo.ui.elements.other;


import com.plixo.ui.elements.UIElement;

public class UICircle extends UIElement {

    float radius = 0;


    @Override
    public void setDimensions(float x, float y, float width, float height) {
        super.setDimensions(x, y, width, height);
        radius = height / 2;
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {

        gui.drawCircle(x + width / 2, y + height / 2, radius + 1, getHoverColor());
        gui.drawCircle(x + width / 2, y + height / 2, radius, getColor());



        base(mouseX, mouseY);

        if (getHoverName() != null && isHovered(mouseX, mouseY)) {
            gui.deactivateScissor();
            drawName(mouseX, mouseY);
            gui.activateScissor();
        }
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}

