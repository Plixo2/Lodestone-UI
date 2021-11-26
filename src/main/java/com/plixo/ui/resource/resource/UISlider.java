package com.plixo.ui.resource.resource;


import com.plixo.ui.ColorLib;
import com.plixo.ui.resource.UIReferenceHolderCanvas;
import com.plixo.ui.resource.util.SimpleSlider;
import com.plixo.util.Util;

public class UISlider extends UIReferenceHolderCanvas<SimpleSlider> {

    public UISlider() {
        setColor(ColorLib.getBackground(0.3f));
    }
    @Override
    public void drawScreen(float mouseX, float mouseY) {


        drawDefault();
        float sliderLeft = x + 5;
        float sliderWidth = width - 10;
        float y = this.y + height * 0.66f;
        gui.drawRect(sliderLeft, y - 1, sliderLeft + sliderWidth, y, ColorLib.getBrighter(getColor()));
        SimpleSlider slider = reference.getValue();
        float percent = (slider.getValue() - slider.min) / (slider.max - slider.min);


        gui.drawCircle(sliderLeft + sliderWidth * percent, y - 0.5f, 3, ColorLib.getMainColor());
        double round = Math.round(slider.getValue() * 1000) / 1000d;
        gui.drawString("" + round, sliderLeft, this.y + 7, ColorLib.getTextColor());
        if (dragged) {
            float mousePercent = Util.clampFloat((mouseX - sliderLeft) / sliderWidth, 1, 0);

            slider.setValue(slider.min + (slider.max - slider.min) * mousePercent);
        }
     //   super.drawScreen(mouseX, mouseY);

        base(mouseX, mouseY);
    }

    boolean dragged = false;

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
    }
}
