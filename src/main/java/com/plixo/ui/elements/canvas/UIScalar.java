package com.plixo.ui.elements.canvas;

public class UIScalar extends UICanvas {

    float scale = 1;
    @Override
    public void drawScreen(float mouseX, float mouseY) {
        gui.pushMatrix();
        gui.scale(scale,scale);
        super.drawScreen(mouseX/scale, mouseY/scale);
        gui.popMatrix();
    }

    @Override
    public void mouseReleased(float mouseX, float mouseY, int state) {
        super.mouseReleased(mouseX/scale, mouseY/scale, state);
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        super.mouseClicked(mouseX/scale, mouseY/scale, mouseButton);
    }

    public void scaleDimensions() {
        width = getScaledWidth();
        height = getScaledHeight();
    }

    public float getScaledWidth() {
        return width/scale;
    }

    public float getScaledHeight() {
        return height/scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getScale() {
        return scale;
    }
}
