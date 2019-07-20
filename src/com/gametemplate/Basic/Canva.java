package com.gametemplate.Basic;

import com.gametemplate.Drawable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Canva extends JPanel {
    public Canva(int width, int height){
        setSize(width, height);
        setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Director.getCurrentStage().update(g2d);
        g2d.dispose();
    }
}
