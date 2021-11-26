package com.plixo.ui.resource.resource;


import com.plixo.ui.ColorLib;
import com.plixo.ui.elements.other.UIButton;
import com.plixo.ui.resource.UIReferenceHolderCanvas;
import com.plixo.util.FileUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

/**
 * for displaying and choosing a file with {@code JFileChooser}
 **/
public class UIFileChooser extends UIReferenceHolderCanvas<File> {


    JFrame lastFrame;


    public UIFileChooser() {
        setColor(0);
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {

        drawDefault(ColorLib.getBackground(0.3f));
        drawHoverEffect();


        if (reference.hasValue()) {
            String toStr = reference.getValue().getName();

            String newStr = toStr;
            if (!toStr.equals(newStr)) {
                newStr += "...";
            }

            gui.drawString(newStr, x + 4, y + height / 2, ColorLib.getTextColor());
        }
        super.drawScreen(mouseX, mouseY);
    }


    //set dimensions for the choose button
    @Override
    public void setDimensions(float x, float y, float width, float height) {
        UIFileChooser instance = this;
        UIButton button = new UIButton();
        button.setAction(() -> {
            if (lastFrame != null && lastFrame.isVisible()) {
                return;
            }
            JFrame frame = new JFrame("choose wisely...");
            lastFrame = frame;
            frame.setSize(600, 500);
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            JFileChooser chooser = new JFileChooser();


            chooser.setCurrentDirectory(FileUtil.getFolderFromName(""));
            frame.add(chooser, BorderLayout.CENTER);

            Runnable closeFrame = () -> {
                frame.setVisible(false);
                frame.dispose();
            };
            chooser.addActionListener((ActionEvent e) -> {
                if (e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
                    closeFrame.run();
                    reference.setValue(chooser.getSelectedFile());
                } else if (e.getActionCommand().equals(JFileChooser.CANCEL_SELECTION)) {
                    closeFrame.run();
                    reference.setValue(null);
                }
            });
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    closeFrame.run();
                }
            });
            frame.setVisible(true);
        });
        button.setDisplayName(">");
        button.setRoundness(2);
        button.setDimensions(width - height, 0, height, height);
        clear();
        add(button);
        super.setDimensions(x, y, width, height);
    }

}
