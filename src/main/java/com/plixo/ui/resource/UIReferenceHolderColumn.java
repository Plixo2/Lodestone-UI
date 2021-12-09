package com.plixo.ui.resource;


import com.plixo.ui.elements.UIElement;
import com.plixo.ui.elements.canvas.UIColumn;
import com.plixo.util.reference.EmptyReference;
import com.plixo.util.reference.InterfaceReference;
import com.plixo.util.reference.Reference;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class UIReferenceHolderColumn<O> extends UIColumn implements IUIReferenceHolder<O> {

    public Reference<O> reference = new EmptyReference<>();

    @Override
    public void setReference(Reference<O> reference) {
            this.reference = reference;
    }

    @Override
    public void setReference(Consumer<O> consumer, Supplier<O> supplier) {
        reference = new InterfaceReference<>(consumer,supplier);
    }

    @Override
    public Reference<O> getReference() {
        return reference;
    }

    @Override
    public UIElement getUIElement() {
        return this;
    }
}
