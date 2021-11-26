package com.plixo.ui.elements.canvas;



/**
 * UIElement that can be dragged (while clicking)
 */
public class UIDraggable extends UICanvas {

    public boolean isSelected = false;
    public float dragX = 0;
    public float dragY = 0;
    public boolean dragging = false;

    /**
     * for calculation the offset
     */
    @Override
    public void drawScreen(float mouseX, float mouseY) {
        super.drawScreen(mouseX, mouseY);
        if (dragging) {
            x += (mouseX - dragX);
            y += (mouseY - dragY);
            dragX = mouseX;
            dragY = mouseY;
        }
    }


    public void beginSelect(float mouseX, float mouseY, int mouseButton) {
        if(isHovered(mouseX , mouseY) && mouseButton == 0) {
            isSelected = true;
        }
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {

        if(isHovered(mouseX , mouseY) && mouseButton == 0) {
            dragging = true;
            dragX = mouseX;
            dragY = mouseY;
            isSelected = true;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(float mouseX, float mouseY, int state) {
        dragging = false;
        super.mouseReleased(mouseX,mouseY,state);
    }
}
