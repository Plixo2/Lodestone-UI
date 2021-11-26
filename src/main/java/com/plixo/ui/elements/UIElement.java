package com.plixo.ui.elements;


import com.plixo.ui.ColorLib;
import com.plixo.ui.IMouse;
import com.plixo.ui.IRenderer;
import com.plixo.ui.IOObject;
import com.plixo.ui.IKeyboard;
import com.plixo.util.Color;
import com.plixo.util.Util;

import java.util.function.Supplier;

/**
 * Default ui class, updates
 * hoverProgress and handles simple click action with {@code actionPerformed()}
 **/
public abstract class UIElement implements IGuiEvent {

    public static IRenderer gui;
    public static IKeyboard keyboard;
    public static IMouse mouse;


    public float height, width;
    public float x, y;
    float hoverProgress = 0;
    Runnable onTick;
    Supplier<?> cursorObject;
    Runnable action;
    Runnable option;
    Runnable mouseOver;
    int alignment = 0;

    float roundness = 0;
    int color = ColorLib.getBackground(0.2f);

    float outlineWidth = 0;
    int outlineColor = 0x55000000;

    int normalHoverColor = 0x23000000;
    float hoverSpeed = 1;

    String displayName;
    String hoverName;
    long lastMs = 0;

    public UIElement() {
        setRoundness(2);
    }

    public void drawScreen(float mouseX, float mouseY) {
        base(mouseX, mouseY);
    }

    public void onTick() {
        if (onTick != null) {
            onTick.run();
        }
    }

    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY)) {
            if (mouseButton == 0) {
                if (action != null) {
                    action.run();
                }
            } else if (mouseButton == 1) {
                if (option != null) {
                    option.run();
                }
                if (cursorObject != null) {
                    Object run = cursorObject.get();
                    if (run != null) {
                        IOObject.draggedObject = run;
                    }
                }
            }
        }
    }

    public void updateHoverProgress(float mouseX, float mouseY) {
        long delta = System.currentTimeMillis() - lastMs;
        if (!isHovered(mouseX, mouseY)) {
            delta = -delta;
        }
        delta *= hoverSpeed;

        hoverProgress = (float) Util.clampDouble(hoverProgress + delta, 100, 0);
        lastMs = System.currentTimeMillis();
    }

    public void setDimensions(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void copyDimensions(UIElement element) {
        this.x = element.x;
        this.y = element.y;
        this.width = element.width;
        this.height = element.height;
    }


    public void base(float mouseX, float mouseY) {
        updateHoverProgress(mouseX, mouseY);
        if (mouseOver != null) {
            mouseOver.run();
        }
    }

    public void drawName(float mouseX, float mouseY) {
        if (hoverName != null) {
            if (isHovered(mouseX, mouseY)) {
                drawName(hoverName);
                return;
            }
        }
        if (displayName != null) {
            drawName(displayName);
        }
    }

    private void drawName(String name) {
        drawName(name,alignment);
    }

    private void drawName(String name , int alignment) {
        if (alignment == -1) {
            gui.drawStringWithShadow(name, x + 3, y + height / 2, ColorLib.getTextColor());
        } else if (alignment == 1) {
            gui.drawStringWithShadow(name, x + width - (gui.getStringWidth(name)+3), y + height / 2, ColorLib.getTextColor());
        } else {
            gui.drawCenteredStringWithShadow(name, x + width / 2, y + height / 2, ColorLib.getTextColor());
        }
    }


    public void drawDefault() {
        drawDefault(getColor());
        drawOutline();
    }

    public void drawDefault(int color) {
        gui.drawRoundedRect(x, y, x + width, y + height, getRoundness(), color);
    }
    public void drawOutline() {
        drawOutline(getOutlineColor());
    }

    public void drawOutline(int color) {
        if(getOutlineWidth() > 0.01) {
            gui.drawLinedRoundedRect(x, y, x + width, y + height, getRoundness(),color , getOutlineWidth());
        }
    }

    public void drawHoverEffect() {
        drawDefault(getHoverColor());
    }


    public boolean isHovered(float mouseX, float mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }

    public int getHoverColor() {
        return Color.interpolateColorAlpha(0x00000000, normalHoverColor, hoverProgress / 100f);
    }

    public void setDisplayName(String name) {
        this.displayName = name;
    }

    public void setHoverName(String name) {
        this.hoverName = name;
    }

    public void setRoundness(float roundness) {
        this.roundness = roundness;
    }

    public void setCursorObject(Supplier<?> getObject) {
        this.cursorObject = getObject;
    }

    public void setTickAction(Runnable runnable) {
        this.onTick = runnable;
    }

    public void setAction(Runnable runnable) {
        action = runnable;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void alignLeft() {
        alignment = -1;
    }

    public void alignMiddle() {
        alignment = 0;
    }

    public void alignRight() {
        alignment = 1;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getHoverName() {
        return hoverName;
    }

    public float getRoundness() {
        return roundness;
    }

    public int getColor() {
        return color;
    }

    public float getOutlineWidth() {
        return outlineWidth;
    }

    public void setOutlineWidth(float outlineWidth) {
        this.outlineWidth = outlineWidth;
    }

    public void setOutlineColor(int outlineColor) {
        this.outlineColor = outlineColor;
    }

    public int getOutlineColor() {
        return outlineColor;
    }

    public void setOption(Runnable option) {
        this.option = option;
    }

    public Runnable getOption() {
        return option;
    }

    public Runnable getAction() {
        return action;
    }


    public float getHoverProgress() {
        return hoverProgress;
    }

    public int getNormalHoverColor() {
        return normalHoverColor;
    }

    public void setNormalHoverColor(int normalHoverColor) {
        this.normalHoverColor = normalHoverColor;
    }

    public void setHoverSpeed(float hoverSpeed) {
        this.hoverSpeed = hoverSpeed;
    }

    public float getHoverSpeed() {
        return hoverSpeed;
    }

    public void setMouseOver(Runnable mouseOver) {
        this.mouseOver = mouseOver;
    }

    public Runnable getMouseOver() {
        return mouseOver;
    }
}
