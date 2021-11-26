package com.plixo.ui.resource;


import com.plixo.ui.elements.UIElement;
import com.plixo.util.reference.Reference;

public interface IUIReferenceHolder<O> {

    public void setReference(Reference<O> reference);
    public Reference<O> getReference();
    public UIElement getUIElement();

}
