package com.plixo.ui.resource.resource;


import com.plixo.ui.ColorLib;
import com.plixo.ui.elements.other.UIButton;
import com.plixo.ui.resource.UIReferenceHolderCanvas;

public class UIEnum extends UIReferenceHolderCanvas<Enum<?>> {


    public UIEnum() {
        setColor(0);
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {

        drawDefault( ColorLib.getBackground(0.3f));
        drawHoverEffect();

        if (reference.hasValue()) {
            gui.drawString(reference.getValue().name(), x + 3, y + height / 2, ColorLib.getTextColor());
        }

        super.drawScreen(mouseX, mouseY);
    }


    //set dimensions for the choose button
    @Override
    public void setDimensions(float x, float y, float width, float height) {
        UIButton button = new UIButton();
        button.setDisplayName("<");
        button.setAction(() -> {
            Enum<?> enumType = reference.getValue();
            int index = enumType.ordinal() + 1;
            Class<? extends Enum<?>> declaringClass = enumType.getDeclaringClass();
            reference.setValue(declaringClass.getEnumConstants()[index % declaringClass.getEnumConstants().length]);
        });
        button.setRoundness(2);
        button.setDimensions(width - height, 0, height, height);
        clear();
        add(button);

        super.setDimensions(x, y, width, height);
    }


}
