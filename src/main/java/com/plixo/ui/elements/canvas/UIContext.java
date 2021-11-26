package com.plixo.ui.elements.canvas;

public class UIContext extends UICanvas {

    public Context2 drawContext;
    public Context3 mouseClickContext;
    public Context3 mouseReleaseContext;
    public KeyContext keyContext;


    @Override
    public void drawScreen(float mouseX, float mouseY) {
        super.drawScreen(mouseX, mouseY);
        if (drawContext != null) {
            drawContext.accept(mouseX, mouseY);
        }
    }


    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        if (mouseClickContext != null)
        mouseClickContext.accept(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(float mouseX, float mouseY, int state) {
        if(mouseReleaseContext != null)
        mouseReleaseContext.accept(mouseX, mouseY, state);
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (keyContext != null)
        keyContext.accept(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
    }

    public interface KeyContext {
        public void accept(char typedChar, int keyCode);
    }

    public interface Context2 {
        public void accept(float x, float y);
    }

    public interface Context3 {
        public void accept(float x, float y, int button);
    }

}
