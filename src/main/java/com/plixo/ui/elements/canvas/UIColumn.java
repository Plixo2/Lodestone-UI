package com.plixo.ui.elements.canvas;


import com.plixo.ui.ColorLib;
import com.plixo.ui.elements.UIElement;
import com.plixo.util.Util;
import com.plixo.util.Vector2f;

/**
 * UICanvas with scroll support and easy (vertical) layout handling
 */
public class UIColumn extends UICanvas {


    private float space = 0;
    private float percent = 0;
    public float offset = 0;
    boolean scissor = true;
    boolean scrooling = true;


    public UIColumn() {
        setColor(0);
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {

         drawDefault();

        if (isHovered(mouseX, mouseY)) {
            float dir = Math.signum(mouse.getDWheel());
            if (dir != 0) {
                float size = 30 / (getMax()-height);
                percent = percent - (size * dir);
                percent = Util.clampFloat(percent, 1, 0);
              //  Animation.animate(f -> animated = UMath.clampFloat(f, 1, 0),() -> animated,percent,0.3f);
            }
        }

        offset = 0;
        if (getMax() > height && scrooling) {
            float maxDiff = getMax() - height;
            offset = maxDiff * percent;
        }
        mouseY += offset;


        gui.pushMatrix();
        float[] mat = gui.getModelViewMatrix();
        Vector2f globalPosMIN = gui.toScreenSpace(mat,x,y);
        Vector2f globalPosMAX = gui.toScreenSpace(mat,x + width,y + height);
        if(scissor) {
            gui.createScissorBox(globalPosMIN.x, globalPosMIN.y, globalPosMAX.x, globalPosMAX.y);
            gui.activateScissor();
        }
        gui.translate(x, y - offset);
        float add = 0;
        for (UIElement element : elements) {
            element.y = add;
            element.drawScreen(mouseX - x, mouseY - y);
            add += element.height + space;
        }
        if(scissor) {
            gui.deactivateScissor();
        }
        gui.popMatrix();

        base(mouseX, mouseY);
    }

    @Override
    public void onTick() {
     //   sort();
        super.onTick();
    }


    //set right dimensions
    @Override
    public void add(UIElement element) {
        super.add(element);
        sort();
    }

    @Override
    public void remove(UIElement element) {
        super.remove(element);
        sort();
    }

    public float getMax() {
        float y = 0;
        if (elements.size() > 0) {
            UIElement element = elements.get(elements.size() - 1);
            y = element.y + element.height+space;
        }
        return y;
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY+offset, mouseButton);
    }

    @Override
    public void mouseReleased(float mouseX, float mouseY, int state) {
        super.mouseReleased(mouseX, mouseY+offset, state);
    }

    //set the right height for each element
    public void sort() {
        float y = 0;
        for (UIElement element : elements) {
            element.y = y;
            y += element.height + space;
        }
    }

    @Override
    public void pack() {
        this.height = 0;
        for (UIElement element : elements) {
            this.width = Math.max(element.x + element.width, this.width);
            this.height = Math.max(element.y + element.height, this.height);
        }
        this.height = Math.max(this.height, getMax());
    }


    public void setSpace(float space) {
        this.space = space;
    }

    public float getSpace() {
        return space;
    }

    public void setScissor(boolean scissor) {
        this.scissor = scissor;
    }

    public void setScroolingEnabled(boolean scrooling) {
        this.scrooling = scrooling;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }
}
