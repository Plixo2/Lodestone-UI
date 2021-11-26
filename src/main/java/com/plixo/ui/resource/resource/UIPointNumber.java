package com.plixo.ui.resource.resource;


import com.plixo.ui.ColorLib;
import com.plixo.ui.resource.UIReferenceHolderCanvas;
import com.plixo.ui.resource.resource.text.GuiTextField;
import com.plixo.util.Color;
import com.plixo.util.Util;


public class UIPointNumber extends UIReferenceHolderCanvas<Float> {
    GuiTextField field;
    boolean dragged = false;
    float lastMouseX = 0;

    public UIPointNumber() {
        setColor(ColorLib.getBackground(0.3f));
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {

        super.drawScreen(mouseX, mouseY);
        drawDefault();
        drawDefault(Color.interpolateColorAlpha(0x00000000, 0x23000000, getHoverProgress() / 100f));


        lastMouseX = mouseX;


        field.drawTextBox();


    }


    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (Character.isDigit(typedChar) || typedChar == '-' || typedChar == '.' || typedChar == 0 || typedChar == 4
                || typedChar == 8 || typedChar == 2 || typedChar == 25 || typedChar == 23)
            field.textboxKeyTyped(typedChar, keyCode);

        if(typedChar == 13) {
            String str = field.getText();
            if (Util.isNumeric(str)) {
                reference.setValue(Float.parseFloat(str));
            } else {
                reference.setValue(0f);
            }
            field.setText(reference.getValue() + "");
            field.setFocused(false);
        }
         //reference.setValue(field);
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {

        dragged = isHovered(mouseX, mouseY) && mouseButton == 0;
        if (isHovered(mouseX, mouseY)) {
            field.setFocused(true);
            field.setText(reference.getValue() + "");
        } else {
            String str = field.getText();
            if (Util.isNumeric(str)) {
                reference.setValue(Float.parseFloat(str));
            } else {
                reference.setValue(0f);
            }
            field.setText(reference.getValue() + "");
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(float mouseX, float mouseY, int state) {
        field.mouseClicked((int) mouseX, (int) mouseY, state);
        dragged = false;
    }

    //set dimensions for text field
    @Override
    public void setDimensions(float x, float y, float width, float height) {
        field = new GuiTextField(0, gui,keyboard, (int) x + 4, (int) y,
                (int) width - 8, height);
        field.setTextColor(ColorLib.getTextColor());
        field.setText(reference.getValue() + "");
        super.setDimensions(x, y, width, height);
    }


}
