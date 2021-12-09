package com.plixo.ui.elements.canvas;


import com.plixo.ui.ColorLib;
import com.plixo.ui.elements.UIElement;
import com.plixo.ui.elements.other.UIButton;

public class UIPages extends UICanvas {

    public UIPages() {
        setColor(ColorLib.getBackground(-0.2f));
    }

    public float headHeight = 15;
    public float headWidth = 65;
    int page = 0;

    UICanvas header;
    UICanvas body;

    @Override
    public void setDimensions(float x, float y, float width, float height) {
        clear();
        int lineColor = 0;
        header = new UICanvas() {
            @Override
            public void drawScreen(float mouseX, float mouseY) {

                //   Gui.drawLinedRoundedRect(x, y, x + width, y + height, roundness,0x55FFFFFF,1);
                super.drawScreen(mouseX, mouseY);

                gui.drawLine(x, y, x + width, y,lineColor, 1);
                gui.drawLine(x, y + height, x + width, y + height,lineColor, 1);
            }
        };
        body = new UICanvas() {
            @Override
            public void drawScreen(float mouseX, float mouseY) {
                drawDefault();
                gui.drawRect(x-1 ,y-1,x,y+height+1,lineColor);
                gui.drawRect(x-1 ,y-1,x+width+1,y,lineColor);

                gui.drawRect(x+width ,y-1,x+width+1,y+height+1,lineColor);
                gui.drawRect(x-1 ,y+height,x+width+1,y+height+1,lineColor);

                gui.pushMatrix();
                gui.translate(x, y);
                //why?????????
                //why?????????
                //why?????????



                for (int i = 0; i < elements.size(); i++) {
                    if (i == page)
                        elements.get(i).drawScreen(mouseX - x, mouseY - y);
                }

                gui.popMatrix();

                page = Math.min(page, elements.size() - 1);
                super.base(mouseX, mouseY);
            }
                //why?????????
            @Override
            public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
                for (int i = 0; i < elements.size(); i++) {
                    if (i == page)
                        elements.get(i).mouseClicked(mouseX - x, mouseY - y, mouseButton);
                }
            }
            //why?????????
            @Override
            public void keyTyped(char typedChar, int keyCode) {
                for (int i = 0; i < elements.size(); i++) {
                    if (i == page)
                        elements.get(i).keyTyped(typedChar, keyCode);
                }
            }
            //why?????????
        };

        header.setDimensions(0, 0, width, headHeight);
        header.setColor(ColorLib.getBackground(0.75f));
        header.setRoundness(0);
        body.setDimensions(0, headHeight, width, height - headHeight);
        body.setColor(0);

        elements.add(body);
        elements.add(header);
        super.setDimensions(x, y, width, height);
    }

    @Override
    public void add(UIElement canvas) {

        canvas.setDimensions(0, 0, body.width, body.height);
        canvas.setRoundness(0);
        body.add(canvas);

        int id = header.elements.size();
        UIButton switchButton = new UIButton();
        switchButton.setTickAction(() -> {
            if (id == page) {
                switchButton.setColor(ColorLib.getBackground(0.2f));
            } else {
                switchButton.setColor(0);
            }
        });

        switchButton.setAction(() -> {
            this.page = id;
        });
        switchButton.setRoundness(0);
        switchButton.alignLeft();
        switchButton.setDisplayName(canvas.getDisplayName());
        switchButton.setDimensions(id * headWidth, 0, headWidth, header.height);
        header.add(switchButton);
    }

    public void setHeadHeight(float headHeight) {
        this.headHeight = headHeight;
    }

    public void setHeadWidth(float headWidth) {
        this.headWidth = headWidth;
    }
}
