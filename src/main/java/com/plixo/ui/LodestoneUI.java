package com.plixo.ui;

import com.plixo.ui.elements.UIElement;

public class LodestoneUI {

    public LodestoneUI(IRenderer renderer , IKeyboard keyboard , IMouse mouse) {
        UIElement.gui = renderer;
        UIElement.keyboard = keyboard;
        UIElement.mouse = mouse;
    }
}
