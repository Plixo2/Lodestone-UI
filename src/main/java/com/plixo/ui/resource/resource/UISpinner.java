package com.plixo.ui.resource.resource;

import com.plixo.ui.ColorLib;
import com.plixo.ui.elements.other.UIButton;
import com.plixo.ui.resource.UIReferenceHolderCanvas;
import com.plixo.ui.resource.resource.text.GuiTextField;
import com.plixo.util.Util;



public class UISpinner extends UIReferenceHolderCanvas<Integer> {

    GuiTextField field;

    public UISpinner() {
        setColor(0);
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {


       drawDefault( ColorLib.getBackground(0.3f));
       drawHoverEffect();


        field.drawTextBox();



        super.drawScreen(mouseX, mouseY);
    }




    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (Character.isDigit(typedChar) || keyCode == keyboard.BACK()) {
            field.textboxKeyTyped(typedChar, keyCode);
        }
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        field.mouseClicked((int) ( mouseX-x), (int) (mouseY-y), mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if(isHovered(mouseX,mouseY)) {
            field.setFocused(true);
            field.setText(reference.getValue()+"");
        } else {
            String str = field.getText();
            if(Util.isNumeric(str)) {
                reference.setValue(Integer.parseInt(str));
            } else {
                reference.setValue(0);
            }

        }

    }


    //set dimensions of + and - button
    @Override
    public void setDimensions(float x, float y, float width, float height) {

        UIButton up = new UIButton();
        up.setAction(() -> {

            reference.setValue(reference.getValue()+1);
        });

        UIButton down = new UIButton();
        down.setAction(() ->  {
            reference.setValue(reference.getValue()-1);
        });

        up.setDisplayName("+");
        up.setRoundness(getRoundness());
        up.setDimensions(width - height, 0, height, height / 2);

        down.setDisplayName("-");
        down.setRoundness(getRoundness());
        down.setDimensions(width - height, height / 2, height, height / 2);

        clear();
        add(up);
        add(down);


        field = new GuiTextField(0,gui,keyboard, (int) x + 4, (int) y, (int) (width - height)-4,
                (int) height);
        field.setTextColor(ColorLib.getTextColor());
        field.setMaxStringLength(100);
        field.setText(reference.getValue()+"");
        super.setDimensions(x, y, width, height);
    }


}
