package com.plixo.ui.elements.other;


import com.plixo.ui.ColorLib;
import com.plixo.ui.elements.UIElement;
import com.plixo.ui.resource.util.SimpleSlider;
import com.plixo.util.Util;

public class UIFillBar extends UIElement {

    boolean isDraggable = false;

    boolean dragged = false;

    public UIFillBar() {
        setColor(ColorLib.getMainColor());
    }

    SimpleSlider simpleSlider;

    public void setSimpleSlider(SimpleSlider simpleSlider) {
        this.simpleSlider = simpleSlider;
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {

        float size = 5;
        float upperBound = x + size;
        float lowerBound = x + width - size;

        float rel = Util.clampFloat((mouseX - upperBound) / (lowerBound-upperBound), 1, 0);
        if (dragged && isDraggable) {
            simpleSlider.setValue(simpleSlider.min + rel * (simpleSlider.max- simpleSlider.min));
        }

        float percent = Util.clampFloat( (simpleSlider.getValue() - simpleSlider.min) / (simpleSlider.max- simpleSlider.min),1,0);

        float rad = Math.min((this.width*percent), 100);
        gui.drawRoundedRect(x,y,x+width/2,y+height,rad, ColorLib.getBackground(0.2f));
        gui.drawRoundedRect(x, y, x + this.width, y+height, 100, ColorLib.getBackground(0.2f));

        gui.drawRoundedRect(upperBound, y+size, upperBound+(lowerBound-upperBound)*percent, y+height-size, 100, getColor());

        if(isHovered(mouseX,mouseY) && getDisplayName() != null) {
            gui.drawString(getDisplayName(),mouseX,mouseY, ColorLib.getTextColor());
        }

        super.drawScreen(mouseX, mouseY);
    }

    public void setDraggable() {
        isDraggable = true;
    }

    public void setNotDraggable() {
        isDraggable = false;
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY) && mouseButton == 0) {
            dragged = true;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(float mouseX, float mouseY, int state) {
        dragged = false;
        super.mouseReleased(mouseX, mouseY, state);
    }

}
