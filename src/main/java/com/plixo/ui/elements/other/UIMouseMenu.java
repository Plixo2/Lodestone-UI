package com.plixo.ui.elements.other;


import com.plixo.ui.ColorLib;
import com.plixo.ui.IOObject;
import com.plixo.ui.elements.UIElement;
import com.plixo.ui.elements.canvas.UIColumn;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class UIMouseMenu extends UIColumn {

    public UIMouseMenu() {
        super();
    }

    LinkedHashMap<String , ArrayList<UIButton>> options = new LinkedHashMap<>();

    public void addOption(String group,String name , Runnable action) {

        ArrayList<UIButton> buttons = options.getOrDefault(group, new ArrayList<>());


        UIButton button = new UIButton();
        button.setAction(action);
        button.setDisplayName(name);
        button.setRoundness(0);
        button.setColor(ColorLib.getMainColor());

        buttons.add(button);


        options.put(group,buttons);


       // options.put(name,action);
    }

    public void build(float mouseX , float mouseY) {
        float max = 10;
        for (ArrayList<UIButton> buttons : options.values()) {
            if(buttons.size() == 1)  {
                max = Math.max(max, gui.getStringWidth(buttons.get(0).getDisplayName()));
            }
        }
        for (String names : options.keySet()) {
            max = Math.max(max, gui.getStringWidth(names+" >"));
        }
        max += 5;

        UIElement element = new UIElement() {
        };
        element.height = 2;
        add(element);
        float y = 2;

        for (int i = 0; i < options.entrySet().size(); i++) {
            Map.Entry<String, ArrayList<UIButton>> stringArrayListEntry = new ArrayList<>(options.entrySet()).get(i);

            if(stringArrayListEntry.getValue().size() > 1 ) {
                UIButton button = new UIButton();
                button.setAction(() -> SwingUtilities.invokeLater(() -> {
                    IOObject.beginMenu();
                    for (UIButton uiButton : stringArrayListEntry.getValue()) {
                        IOObject.addMenuOption(uiButton.getDisplayName(),uiButton.getAction());
                    }
                    IOObject.showMenu(mouseX,mouseY);
                }));
                button.setDisplayName(stringArrayListEntry.getKey()+" >");
                button.setRoundness(0);
                button.setDimensions(2,0,max,12);
                button.setColor(ColorLib.getMainColor());
                add(button);
                y += 12;
            } else {
                UIButton button = stringArrayListEntry.getValue().get(0);
                button.setDimensions(2,0,max,12);
                y += 12;
                add(button);
            }

            if(stringArrayListEntry.getValue().size() > 1 && i < options.entrySet().size()-1) {
                UIButton line = new UIButton();
                line.setDimensions(2, 0, max, 1);
                line.setColor(Integer.MAX_VALUE);
                y += 1;
                add(line);
            }
        }

        setRoundness(2);
        setColor(ColorLib.getDarker(ColorLib.getDarker(ColorLib.getMainColor())));

        float height = Math.min(y+2,300);
        //Animation.animate(f -> this.height = f,1f,height,0.05f * elements.size(), Ease.OutExpo);
        setDimensions(mouseX,mouseY,max+4,height);
    }

}
