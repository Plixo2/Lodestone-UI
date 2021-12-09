package com.plixo.ui.elements.other;

import com.plixo.ui.elements.UIElement;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class UIUpdatable<O> extends UIElement {

    Consumer<O> onChange = null;
    Supplier<O> getObject = null;
    O copy = null;
    @Override
    public void drawScreen(float mouseX, float mouseY) {
       if(onChange != null) {
           final O o = getObject.get();
           if(copy != o) {
               this.copy = o;
               onChange.accept(o);
           }
       }
        super.drawScreen(mouseX, mouseY);
    }

    public void setGetObject(Supplier<O> getObject) {
        this.getObject = getObject;
    }


    public void setOnChange(Consumer<O> onChange) {
        this.onChange = onChange;
    }

    public Consumer<O> getOnChange() {
        return onChange;
    }
}
