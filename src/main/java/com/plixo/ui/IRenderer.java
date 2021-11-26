package com.plixo.ui;

import com.plixo.util.Vector2f;

public interface IRenderer {



    public  void drawLinedRect(float left, float top, float right, float bottom, int color, float width);

    public  void drawLinedRoundedRect(float left, float top, float right, float bottom, float radius, int color, float width);

    public  void drawLine(float left, float top, float right, float bottom, int color, float width);

    public  void drawRect(float left, float top, float right, float bottom, int color);
    public  void drawCircle(float x, float y, float radius, int color);

    public  void drawOval(float x, float y, float width, float height, int color);
    public  void drawRoundedRect(float left, float top, float right, float bottom, float radius, int color) ;

    public  void drawCircle(float x, float y, float radius, int color, int from, int to);

    public void circleLinedSection(float x, float y, float radius, int color, int from, int to, float width);

    public void pushMatrix();
    public void popMatrix();
    public void translate(float x , float y);
    public void scale(float x , float y);

    public  void activateScissor();
    public  void deactivateScissor();
    public  void createScissorBox(float x, float y, float x2, float y2);
    public  float[] getModelViewMatrix();
    public Vector2f toScreenSpace(float[] mat, float x, float y);


    public  void drawCenteredString(String text, float x, float y, int color);
    public  void drawCenteredStringWithShadow(String text, float x, float y, int color);
    public  void drawString(String text, float x, float y, int color);
    public  void drawStringWithShadow(String text, float x, float y, int color);

    public  float getStringWidth(String text);
    public String trimStringToWidth(String text, float width, boolean reverse);

    public  float getScale();

}
