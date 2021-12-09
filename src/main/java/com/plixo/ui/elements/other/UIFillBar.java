package com.plixo.ui.elements.other;


import com.plixo.ui.ColorLib;
import com.plixo.ui.elements.UIElement;
import com.plixo.ui.resource.util.SimpleSlider;
import com.plixo.util.Util;
import com.plixo.util.reference.Reference;


import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class UIFillBar extends UIElement {
    private static final DecimalFormat format = new DecimalFormat("0.00");
    static  {
        format.setRoundingMode(RoundingMode.HALF_UP);
    }

    boolean isDraggable = false;
    boolean drawNumber = false;

    boolean dragged = false;

    public UIFillBar() {
        setColor(ColorLib.getMainColor());
    }

    Reference<Float> reference;
    float min = 0, max = 1;

    @Override
    public void drawScreen(float mouseX, float mouseY) {

        float size = 0;
        float upperBound = x + size;
        float lowerBound = x + width - size;

        float rel = Util.clampFloat((mouseX - upperBound) / (lowerBound-upperBound), 1, 0);
        if (dragged && isDraggable) {
            reference.setValue(min + rel * (max- min));
        }

        final float value = reference.getValue();
        float percent = Util.clampFloat( (value - min) / (max- min),1,0);

        float rad = Math.min((this.width*percent), 100);
        gui.drawRoundedRect(x,y,x+width/2,y+height,rad, ColorLib.getBackground(0.2f));
        gui.drawRoundedRect(x, y, x + this.width, y+height, 100, ColorLib.getBackground(0.2f));

        final float right = upperBound + (lowerBound - upperBound) * percent;
        gui.drawRoundedRect(upperBound, y+size, right, y+height-size, 100, getColor());

        if(isHovered(mouseX,mouseY) && getDisplayName() != null) {
            gui.drawString(getDisplayName(),mouseX,mouseY, ColorLib.getTextColor());
        }

        if(drawNumber) {
            String text = format.format(value);
            if(max == 100) {
                text = Math.round(value) + "%";
            }
            float min = upperBound + 5;
            gui.drawString(text, min, y + height / 2, ColorLib.getTextColor());
        }

        super.drawScreen(mouseX, mouseY);
    }

    public void setDraggable() {
        isDraggable = true;
    }

    public void setNotDraggable() {
        isDraggable = false;
    }

    public void setDrawNumber(boolean drawNumber) {
        this.drawNumber = drawNumber;
    }

    public boolean isDrawNumber() {
        return drawNumber;
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


    public void setMax(float max) {
        this.max = max;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    public float getMin() {
        return min;
    }

    public void setReference(Reference<Float> reference) {
        this.reference = reference;
    }
}
