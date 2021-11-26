package com.plixo.ui.elements.canvas;

import com.plixo.ui.elements.UIElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

public class UICollection<A> extends UIColumn {

    Collection<? extends A> copy = new ArrayList<>();
    Collection<? extends A> list = new ArrayList<>();
    Function<A, UIElement> elementSupplier;

    @Override
    public void drawScreen(float mouseX, float mouseY) {
        if (checkChange()) {
            copy.clear();
            copy = new ArrayList<>(list);
            constructElements();
        }
        super.drawScreen(mouseX, mouseY);
    }

    void constructElements() {
        if (elementSupplier == null && list != null) {
            return;
        }
        clear();
        list.forEach(a -> {
            UIElement element = elementSupplier.apply(a);
            add(element);
        });
    }

    boolean checkChange() {
        if (list.size() != copy.size()) {
            return true;
        }
        for (int i = 0; i < list.size(); i++) {
            A obj = (A) list.toArray()[i];
            A obj2 = (A) copy.toArray()[i];
            if (obj != obj2) {
                return true;
            }
        }
        return false;
    }

    public Collection<? extends A> getOriginal() {
        return list;
    }

    public void setList(Collection<? extends A> list) {
        this.list = list;
        constructElements();
    }

    public void setSupplier(Function<A, UIElement> supplier) {
        this.elementSupplier = supplier;
        constructElements();
    }

}
