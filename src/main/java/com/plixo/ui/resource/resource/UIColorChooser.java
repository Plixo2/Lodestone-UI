package com.plixo.ui.resource.resource;

import com.plixo.ui.ColorLib;
import com.plixo.ui.elements.other.UIButton;
import com.plixo.ui.resource.UIReferenceHolderCanvas;
import com.plixo.util.Color;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.colorchooser.ColorChooserComponentFactory;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UIColorChooser extends UIReferenceHolderCanvas<Color> {


    public UIColorChooser() {
        setColor(0);
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {

        drawDefault(ColorLib.getBackground(0.3f));
        drawHoverEffect();



        gui.drawStringWithShadow(Integer.toHexString(reference.getValue().getRgba()).toUpperCase(), x + 4, y + height / 2, ColorLib.getTextColor());

       super.drawScreen(mouseX, mouseY);
    }


    //set dimensions for the choose button
    @Override
    public void setDimensions(float x, float y, float width, float height) {
        UIButton button = new UIButton();
        button.setAction(() -> {

            JFrame jFrame = new JFrame("choose wisely...");
            jFrame.setSize(750, 500);
            JColorChooser colorChooser = new JColorChooser();
            colorChooser.setColor(new java.awt.Color(reference.getValue().getRgba()));
            colorChooser.setPreviewPanel(new JPanel());

            colorChooser.setChooserPanels(new AbstractColorChooserPanel[]{ColorChooserComponentFactory.getDefaultChooserPanels()[1]});

            jFrame.getContentPane().add(colorChooser);
            jFrame.setVisible(true);

            jFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    jFrame.setVisible(false);
                    jFrame.dispose();
                    reference.setValue(new Color(colorChooser.getColor().getRGB()));
                    button.setColor(reference.getValue().getRgba());
                }
            });
        });
        button.setDisplayName(">");
        button.setRoundness(2);

        button.setColor(reference.getValue().getRgba());
        button.setTickAction(() ->  {
            button.setColor(reference.getValue().getRgba());
        });
        button.setDimensions(width - height, 0, height, height);
        clear();
        add(button);
        super.setDimensions(x, y, width, height);
    }



}
