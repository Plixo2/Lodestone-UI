package com.plixo.ui.resource;


import com.plixo.ui.elements.UIElement;
import com.plixo.util.reference.Reference;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface IUIReferenceHolder<O> {

    public void setReference(Reference<O> reference);
    public void setReference(Consumer<O> consumer , Supplier<O> supplier);
    public Reference<O> getReference();
    public UIElement getUIElement();

}
