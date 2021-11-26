package com.plixo.ui.resource.resource;


import com.plixo.ui.ColorLib;
import com.plixo.ui.resource.UIReferenceHolderCanvas;
import com.plixo.util.Color;

/**
 * for editing and displaying a boolean in the UI
 * with different names for the two states
 **/
public class UIToggleButton extends UIReferenceHolderCanvas<Boolean> {


    String strFalse = "False";
    String strTrue = "True";
    public UIToggleButton() {
        setColor(ColorLib.getBackground(0.3f));
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {


        int textColor = ColorLib.red();
        if (reference.getValue()) {
            textColor = ColorLib.green();
        }

        drawDefault();
        //Display state as Text
        final float v = height * 0.05f;
        final float add = v - ((getHoverProgress() / 100f) * height * 0.1f);
        gui.drawCenteredString(reference.getValue() ? strTrue : strFalse, x + width / 2, y + height / 2 - add, textColor);

        super.base(mouseX, mouseY);
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY)) {
            reference.setValue(!reference.getValue());
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }



    //set different display texts
    public void setYesNo(String yes, String no) {
        this.strTrue = yes;
        this.strFalse = no;
    }
}
