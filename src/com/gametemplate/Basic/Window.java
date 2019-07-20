package com.gametemplate.Basic;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    Canva canva;

    public Window(String title, int width, int height, int delaytime){
        super(title);
        this.setSize(width, height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
    }

    public void setCanva(Canva canva){
        this.canva = canva;
        this.getContentPane().add(canva);
    }

    public Canva getCanva(){
        return canva;
    }
}