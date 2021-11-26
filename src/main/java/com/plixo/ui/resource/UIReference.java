package com.plixo.ui.resource;


import com.plixo.ui.elements.UIElement;
import com.plixo.ui.elements.canvas.UICanvas;
import com.plixo.ui.elements.other.UILabel;
import com.plixo.ui.resource.resource.*;
import com.plixo.ui.resource.util.SimpleSlider;
import com.plixo.util.Color;
import com.plixo.util.Vector2f;
import com.plixo.util.Vector3f;
import com.plixo.util.reference.Reference;

import java.io.File;

public class UIReference extends UICanvas {

/*
    public <O> void display(float x, float y, float width, float height, Reference<O> reference) {
        if (reference instanceof Resource) {
            Resource<?> resource = ((Resource<O>) reference);
            display(x, y, width, height, resource.getName(), reference, resource.getDefaultClass());
        }
    }
    */

    IUIReferenceHolder referenceHolder;

    public <O> void initialize(float x, float y, float width, float height, String displayName, Reference<?> reference, Class<O> clazz) {
        setDimensions(x, y, width, height);
        clear();

        float startX = 0;
        if (displayName != null) {
            UILabel label = new UILabel();
            label.setDisplayName(displayName);

            label.setDimensions(0, 0, width * 0.4f, height);
            add(label);
            width -= label.width;
            startX = label.width;
        }


        IUIReferenceHolder<O> referenceHolder;
        if (clazz == File.class) {
            referenceHolder = (UIReferenceHolderCanvas<O>) new UIFileChooser();
        } else if (clazz == Boolean.class || clazz == boolean.class) {
            referenceHolder = (UIReferenceHolderCanvas<O>) new UIToggleButton();
        } else if (clazz == Color.class) {
            referenceHolder = (UIReferenceHolderCanvas<O>) new UIColorChooser();
        } else if (clazz == SimpleSlider.class) {
            referenceHolder = (UIReferenceHolderCanvas<O>) new UISlider();
        } else if (clazz.isEnum()) {
            referenceHolder = (UIReferenceHolderCanvas<O>) new UIEnum();
        } else if (clazz == Integer.class || clazz == int.class) {
            referenceHolder = (UIReferenceHolderCanvas<O>) new UISpinner();
        } else if (clazz == Float.class || clazz == float.class) {
            referenceHolder = (UIReferenceHolderCanvas<O>) new UIPointNumber();
        } else if (clazz == String.class) {
            referenceHolder = (UIReferenceHolderCanvas<O>) new UITextBox();
        } else if (clazz == Vector3f.class) {
            referenceHolder = (UIReferenceHolderCanvas<O>) new UIVector3();
        } else if (clazz == Vector2f.class) {
            referenceHolder = (UIReferenceHolderCanvas<O>) new UIVector2();
        } else if (clazz == Runnable.class) {
            referenceHolder = (UIReferenceHolderCanvas<O>) new UIAction();
        } else {
            referenceHolder = getOutsideReference(displayName, reference, clazz);
        }
        this.referenceHolder = referenceHolder;

        if (referenceHolder == null) {
            return;
        }

        referenceHolder.setReference((Reference<O>) reference);
        scale(referenceHolder.getUIElement(), startX, width);
        add(referenceHolder.getUIElement());
    }

    public <O> IUIReferenceHolder<O> getOutsideReference(String displayName, Reference<?> reference, Class<O> clazz) {
        return null;
    }


    public void scale(UIElement element, float x, float width) {
        element.setDimensions(x, 0, width, this.height);
    }

    public IUIReferenceHolder getReferenceObject() {
        return referenceHolder;
    }
}
