package com.plixo.ui.resource.resource;

import com.plixo.ui.ColorLib;
import com.plixo.ui.resource.UIReferenceHolderCanvas;

public class UIAction extends UIReferenceHolderCanvas<Runnable> {

    boolean buttonDown = false;

    public UIAction() {
        setColor(ColorLib.getBackground(0.3f));
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {

        drawDefault();
        gui.drawCenteredStringWithShadow("Run!", this.x + width/2, this.y + this.height / 2.0F + (buttonDown ? 0.5f : 0),
                buttonDown ? ColorLib.getDarker(ColorLib.getDarker(ColorLib.getTextColor())) : ColorLib.getTextColor());

        super.base(mouseX, mouseY);
    }

    @Override
    public void mouseReleased(float mouseX, float mouseY, int state) {
        buttonDown = false;
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {

        if(isHovered(mouseX, mouseY) && mouseButton == 0) {
            buttonDown = true;
            if(reference.hasValue()) {
                try {
                    final Runnable value = reference.getValue();
                    if(value != null) {
                       value.run();
                   }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }


}
