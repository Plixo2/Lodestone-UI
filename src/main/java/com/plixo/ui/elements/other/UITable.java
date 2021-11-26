package com.plixo.ui.elements.other;



import com.plixo.ui.ColorLib;
import com.plixo.ui.elements.canvas.UICanvas;

import java.util.concurrent.atomic.AtomicInteger;

public class UITable extends UICanvas {

     String[] types;
     String type;

    public UITable() {
        setColor(0);
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {

        gui.drawRoundedRect(x, y, x + width, y + height, getRoundness(), ColorLib.getBackground(0.3f));
        drawHoverEffect();

        if(type != null) {
            gui.drawString(type,x+3,y+height/2, ColorLib.getTextColor());
        }

        super.drawScreen(mouseX, mouseY);
    }

    public void setOptions(String... options) {
        this.types = options;
        type = types[0];
    }

    public String getType() {
        return type;
    }


    //set dimensions for the choose button
    @Override
    public void setDimensions(float x, float y, float width, float height) {
        UIButton button = new UIButton();
        button.setDisplayName("^");
        AtomicInteger i = new AtomicInteger();
        button.setAction(() -> {
            type = types[i.get() % types.length];
            i.addAndGet(1);
        });
        button.setRoundness(2);
        button.setDimensions(width - height, 0, height, height);
        clear();
        add(button);

        super.setDimensions(x, y, width, height);
    }


}
