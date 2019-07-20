/*
Game: PlaneFight
version: 1.0.0
author: VisualGMQ
 */
import com.gametemplate.Basic.*;

import Stages.*;
import sun.audio.AudioPlayer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;

public class Main extends GameTemplate{
    public Main(){
        super("Plane Fight", 1200, 800, 30);
    }

    @Override
    protected void init(){
        Director.debugMode = false;
        try {
            FileInputStream inputStream = new FileInputStream(new File("./resource/audio/bgm.wav"));
            AudioPlayer.player.start(inputStream);
        }catch (Exception e){
            System.out.println("no audio");
            e.printStackTrace();
        }

    }

    @Override
    protected void eventHandle(){
        window.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                Director.getCurrentStage().KeyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                Director.getCurrentStage().KeyReleased(e);
            }
        });
    }

    @Override
    protected void imagePreload(){
        //get all image under ./resource/plane/
        File dir = new File("./resource/plane/");
        File[] files = dir.listFiles();
        for(File f: files){
            if(f.isFile())
                ImageStorage.loadImage(f.getPath());
        }
    }

    @Override
    protected void stagePreload(){
        Stage welcomStage = new welcomeStage();
        Director.addStage("welcomeStage", welcomStage);
        Stage gamestage1 = new gamestage1();
        Director.addStage("gameStage1", gamestage1);

        Director.changeStage("welcomeStage");
    }

    public static void main(String[] args){
        Main mygame = new Main();
        mygame.run();
    }
}
