package com.plixo.ui.resource;


import com.plixo.ui.elements.UIElement;
import com.plixo.ui.elements.canvas.UIColumn;
import com.plixo.util.reference.EmptyReference;
import com.plixo.util.reference.Reference;

public class UIReferenceHolderColumn<O> extends UIColumn implements IUIReferenceHolder<O> {

    public Reference<O> reference = new EmptyReference<>();

    @Override
    public void setReference(Reference<O> reference) {
            this.reference = reference;
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
