package com.gametemplate.Basic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class GameTemplate{
    protected Window window;
    protected Canva canva;
    public GameTemplate(String title, int width, int height, int delaytime){
        imagePreload();
        initGame(title, width, height, delaytime);
        stagePreload();
        eventHandle();
    }

    //user must override it to provide own logic
    protected abstract void eventHandle();

    //usr define
    protected abstract void init();

    //load all image at here
    protected abstract void imagePreload();

    //load all stage you need
    protected abstract void stagePreload();

    protected void initGame(String title, int width, int height, int delaytime){
        initWindow(title, width, height, delaytime);
        initCanva();
        window.setCanva(canva);
        Director.initDirector(window, canva);
        init();
    }

    private void initCanva(){
        canva = new Canva(window.getWidth(), window.getHeight());
        canva.requestFocus();
        canva.setBackground(Color.BLACK);
    }

    private void initWindow(String title, int width, int height, int delaytime){
        window = new Window(title ,width , height, delaytime);
        window.setVisible(true);
    }

    public void run(){
        Canva canva = window.getCanva();
        Timer timer = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.requestFocus();
                canva.repaint();
            }
        });
        timer.start();
        timer.setRepeats(true);
    }

    public static void main(String[] argv){
    }
}
