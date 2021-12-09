package com.plixo.ui.resource.resource;


import com.plixo.ui.ColorLib;
import com.plixo.ui.resource.UIReferenceHolderCanvas;
import com.plixo.ui.resource.util.SimpleSlider;
import com.plixo.util.Util;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class UIFillBar2 extends UIReferenceHolderCanvas<SimpleSlider> {
    private static final DecimalFormat format = new DecimalFormat("0.00");

    static {
        format.setRoundingMode(RoundingMode.HALF_UP);
    }


    boolean dragged = false;

    public UIFillBar2() {
        setColor(ColorLib.getMainColor());
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {
        final SimpleSlider slider = reference.getValue();
        float size = 0;
        float upperBound = x + size;
        float lowerBound = x + width - size;

        float rel = Util.clampFloat((mouseX - upperBound) / (lowerBound - upperBound), 1, 0);
        if (dragged) {
            slider.setValue(slider.min + rel * (slider.max - slider.min));
        }

        final float value = slider.getValue();
        float percent = Util.clampFloat((value - slider.min) / (slider.max - slider.min), 1, 0);

        float rad = Math.min((this.width * percent), 100);
        gui.drawRoundedRect(x, y, x + width / 2, y + height, rad, ColorLib.getBackground(0.2f));
        gui.drawRoundedRect(x, y, x + this.width, y + height, 100, ColorLib.getBackground(0.2f));

        final float right = upperBound + (lowerBound - upperBound) * percent;
        gui.drawRoundedRect(upperBound, y + size, right, y + height - size, 100, getColor());

        if (isHovered(mouseX, mouseY) && getDisplayName() != null) {
            gui.drawString(getDisplayName(), mouseX, mouseY, ColorLib.getTextColor());
        }


        String text = format.format(value);
        if (slider.max == 100) {
            text = Math.round(value) + "%";
        }
        float min = upperBound + 5;
        gui.drawString(text, min, y + height / 2, ColorLib.getTextColor());


        super.drawScreen(mouseX, mouseY);
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
