package com.plixo.ui.elements.other;


import com.plixo.ui.ColorLib;
import com.plixo.ui.elements.UIElement;
import com.plixo.util.Util;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class UIScrollBar extends UIElement {

    float draggableHeight = 10;
    boolean dragged = false;
    Consumer<Float> consumer;
    Supplier<Float> supplier;

    @Override
    public void drawScreen(float mouseX, float mouseY) {

        float upperBound = y + draggableHeight;
        float lowerBound = y + height - draggableHeight;

        float rel = Util.clamp01((mouseY - upperBound) / (lowerBound-upperBound));
        if (dragged) {
            if (consumer != null) {
                consumer.accept(rel);
            }
        } else {
            if(isHovered(mouseX, mouseY)) {
                if (consumer != null) {
                    consumer.accept( Util.clamp01(supplier.get()+Math.signum(mouse.getDWheel())*-0.05f));
                }
            }
        }


        gui.drawRoundedRect(x, y, x + this.width, y + height, 100, getColor());


        float percent = 0;
        if (supplier != null) {
            percent = Util.clampFloat(supplier.get(),1,0);
        }

        float cur = upperBound + (lowerBound - upperBound) * percent;
        gui.drawRoundedRect(x, cur - draggableHeight, x + this.width, cur + draggableHeight, 100, ColorLib.getMainColor());

        base(mouseX, mouseY);
    }

    public void setReturnPercent(Consumer<Float> consumer) {
        this.consumer = consumer;
    }

    public void setSupplier(Supplier<Float> supplier) {
        this.supplier = supplier;
    }

    public void setPercent(float percent) {
        consumer.accept(percent);
    }

    public float getPercent() {
        return supplier.get();
    }

    public void setDragerHeight(float draggableHeight) {
        this.draggableHeight = draggableHeight;
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
