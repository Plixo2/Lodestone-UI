package com.plixo.ui.resource.resource;

import com.plixo.ui.ColorLib;
import com.plixo.ui.resource.UIReferenceHolderCanvas;

public class UIHotKey extends UIReferenceHolderCanvas<Integer> {

    boolean listen = false;

    @Override
    public void drawScreen(float mouseX, float mouseY) {

        drawDefault();
        int key = reference.getValue();
        String name = "none";
        if (key >= 0) {
            name = keyboard.getKeyName(key);

        }
        if (listen)
            name = "press a key";

        gui.drawCenteredStringWithShadow(name, this.x + this.width / 2.0F, this.y + this.height / 2.0F, ColorLib.getTextColor());

        base(mouseX, mouseY);
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {

        listen = false;
        if (isHovered(mouseX, mouseY)) {
            listen = true;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {

        if (listen) {
            if (keyCode == keyboard.ESCAPE() || keyCode == keyboard.RETURN() || keyCode == keyboard.BACK()) {
                listen = false;
                reference.setValue(-1);
                return;
            }
            reference.setValue(keyCode);
            listen = false;
        }
        super.keyTyped(typedChar, keyCode);
    }
}
