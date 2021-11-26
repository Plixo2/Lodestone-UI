package com.plixo.ui.visualscript;


import com.plixo.ui.elements.canvas.UICanvas;
import com.plixo.util.Util;
import com.plixo.util.Vector2f;

public class UIGrid extends UICanvas {

    float planeX, planeY;
    float zoom = 1;
    float minZoom = 0.1f;
    float maxZoom = 3;
    boolean dragging = false;
    float dragX, dragY;
    float startX, startY;
    boolean shouldDrawLines = false;
    boolean canDrag = true;

    public void setShouldDrawLines(boolean shouldDrawLines) {
        this.shouldDrawLines = shouldDrawLines;
    }


    @Override
    public void setDimensions(float x, float y, float width, float height) {
        planeX = width / 2;
        planeY = height / 2;
        super.setDimensions(x, y, width, height);
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {
        setColor(0);

        if (canDrag) {
            if (dragging) {
                planeX += (mouseX - dragX);
                planeY += (mouseY - dragY);
                dragX = mouseX;
                dragY = mouseY;
            }

            if (isHovered(mouseX, mouseY)) {
                mouse(mouseX, mouseY);
            }
        }

        float[] modelViewMatrix = gui.getModelViewMatrix();

        gui.pushMatrix();
        gui.translate(planeX, planeY);
        gui.scale(getRelative(), getRelative());

        if (shouldDrawLines) {
            drawLines();
        }
        Vector2f mouseToWorld = screenToWorld(mouseX, mouseY);
        drawScreenTranslated(mouseToWorld.x, mouseToWorld.y);
        gui.popMatrix();

    }

    public void drawScreenTranslated(float mouseX, float mouseY) {
        super.drawScreen(mouseX, mouseY);
    }

    public void mouseClickedTranslated(float mouseX, float mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void mouseReleasedTranslated(float globalMouseX, float globalMouseY, float mouseX, float mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        if (!isHovered(mouseX, mouseY)) {
            return;
        }
        if (mouseButton == 1) {
            startDragging(mouseX, mouseY);
        }
        Vector2f mouseToWorld = screenToWorld(mouseX, mouseY);
        mouseClickedTranslated(mouseToWorld.x, mouseToWorld.y, mouseButton);
    }

    public void startDragging(float mouseX, float mouseY) {
        dragging = true;
        dragX = mouseX;
        dragY = mouseY;
        startX = mouseX;
        startY = mouseY;
    }

    public boolean hasMoved(float mouseX, float mouseY) {
        return Math.abs(mouseX - startX) < 5 && Math.abs(mouseY - startY) < 5;
    }

    @Override
    public void mouseReleased(float mouseX, float mouseY, int state) {
        if (state == 1) {
            dragging = false;
            dragX = 0;
            dragY = 0;
        }
        Vector2f mouseToWorld = screenToWorld(mouseX, mouseY);
        mouseReleasedTranslated(mouseX, mouseY, mouseToWorld.x, mouseToWorld.y, state);
    }


    void mouse(float mouseX, float mouseY) {
        float dir = Math.signum(mouse.getDWheel());
        if (dir != 0) {
            Vector2f pre = screenToWorld(mouseX, mouseY);
            float minDiff = (zoom * (0.1f * dir));
            if (Math.abs(minDiff) < 0.01f) {
                minDiff = dir * 0.01f;
            }
            zoom += minDiff;
            zoom = Util.clampFloat(zoom, 1, 0);
            Vector2f post = screenToWorld(mouseX, mouseY);
            Vector2f diff = new Vector2f(pre.x - post.x, pre.y - post.y);
            planeX -= diff.x * getRelative();
            planeY -= diff.y * getRelative();
        }
    }

    void drawLines() {
        int i = 50;
        Vector2f top = screenToWorld(-i, -i);
        Vector2f bottom = screenToWorld(width + i, height + i);
        float x = top.x;
        float mX = x % i;
        float y = top.y;
        float mY = y % i;
        int color = 0x6F000000;

        for (float j = top.x; j <= bottom.x; j += i) {
            float off = j - mX;
            gui.drawLine(off, top.y, off, bottom.y, color, 1);
        }
        for (float j = top.y; j <= bottom.y; j += i) {
            float off = j - mY;
            gui.drawLine(top.x, off, bottom.x, off, color, 1);
        }

        i = 25;
        Vector2f mid = screenToWorld(0, 0);
        //UGui.drawCircle(0, 0, 3, -1);
        gui.drawLine(-i, 0, i, 0, -1, 2 * gui.getScale());
        gui.drawLine(0, -i, 0, i, -1, 2 * gui.getScale());
    }

    public float getRelative() {
        return minZoom + (maxZoom - minZoom) * zoom;
    }

    public Vector2f screenToWorld(float x, float y) {

        x -= this.planeX;
        y -= this.planeY;

        x /= getRelative();
        y /= getRelative();


        return new Vector2f(x, y);
    }

    public Vector2f worldToScreen(float x, float y) {

        x *= getRelative();
        y *= getRelative();

        x += this.planeX;
        y += this.planeY;

        return new Vector2f(x, y);
    }

    public float getZoom() {
        return zoom;
    }

    public float getMinZoom() {
        return minZoom;
    }

    public float getMaxZoom() {
        return maxZoom;
    }

    public void setMinZoom(float minZoom) {
        this.minZoom = minZoom;
    }

    public void setMaxZoom(float maxZoom) {
        this.maxZoom = maxZoom;
    }

    public void setPlaneX(float planeX) {
        this.planeX = planeX;
    }

    public void setPlaneY(float planeY) {
        this.planeY = planeY;
    }

    public float getPlaneY() {
        return planeY;
    }

    public float getPlaneX() {
        return planeX;
    }

    public void disableDragging() {
        this.canDrag = false;
    }

    public void enableDragging() {
        this.canDrag = true;
    }
}


