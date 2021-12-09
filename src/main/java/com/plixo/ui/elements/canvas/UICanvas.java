package com.plixo.ui.elements.canvas;


import com.plixo.ui.elements.UIElement;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * for other {@code UIElement} inside a Element...
 * great for backgrounds and easy drawing, input and layout handling
 **/
public class UICanvas extends UIElement {

    //for removing while looping
    public CopyOnWriteArrayList<UIElement> elements = new CopyOnWriteArrayList<>();
    UIElement lastElement;


    //add a elements
    public void add(UIElement element) {
        lastElement = element;
        elements.add(element);
    }

    //remove a element
    public void remove(UIElement element) {
        elements.remove(element);
    }

    //clears the mod
    public void clear() {
        lastElement = null;
        elements.clear();
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {

        drawDefault();

        gui.pushMatrix();
        gui.translate(x, y);
        for (UIElement element : elements) {
            element.drawScreen(mouseX - x, mouseY - y);
        }
        gui.popMatrix();



        super.drawScreen(mouseX, mouseY);
    }


    //returns the list
    public CopyOnWriteArrayList<UIElement> getList() {
        return elements;
    }

    //checks if canvas has UIElements
    public boolean hasValues() {
        return elements.size() > 0;
    }

    //inherited input
    @Override
    public void keyTyped(char typedChar, int keyCode) {
        for (UIElement element : elements) {
            element.keyTyped(typedChar, keyCode);
        }
        super.keyTyped(typedChar, keyCode);
    }



    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {

        for (UIElement element : elements) {
            element.mouseClicked(mouseX - x, mouseY - y, mouseButton);
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(float mouseX, float mouseY, int state) {
        for (UIElement element : elements) {
            element.mouseReleased(mouseX - x, mouseY - y, state);
        }
        super.mouseReleased(mouseX, mouseY, state);
    }

    //inherited tick
    @Override
    public void onTick() {
        for (UIElement element : elements) {
            element.onTick();
        }
        super.onTick();
    }


    public void pack() {
        this.width = 0;
        this.height = 0;
        for (UIElement element : elements) {
            this.width = Math.max(element.x+element.width,this.width);
            this.height = Math.max(element.y+element.height,this.height);
        }
    }
}
