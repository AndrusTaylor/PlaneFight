/*
Game: PlaneFight
version: 1.0.0
author: VisualGMQ
 */
import com.gametemplate.Basic.*;

import Stages.*;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;

public class Main extends GameTemplate{
    public Main(){
        super("Plane Fight", 1200, 800, 30);
    }

    private void playBGM(){
        try {
            FileInputStream inputStream = new FileInputStream(new File("./resource/audio/bgm.wav"));
            AudioStream as = new AudioStream(inputStream);
            ContinuousAudioDataStream cas = new ContinuousAudioDataStream(as.getData());
            AudioPlayer.player.start(cas);
        }catch (Exception e){
            System.out.println("no audio");
            e.printStackTrace();
        }
    }

    @Override
    protected void init(){
        //Open DEBUG Mode
        Director.debugMode = false;
        playBGM();
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
        Stage gamestage1 = new gameStage("stage1.txt");
        Director.addStage("gameStage1", gamestage1);
        welcomStage.setNextStage("gameStage1");
        Stage gamestage2 = new gameStage("stage2.txt");
        Director.addStage("gameStage2",gamestage2);
        gamestage1.setNextStage("gameStage2");
        Stage gamefinish = new endStage();
        Director.addStage("endStage", gamefinish);
        gamestage2.setNextStage("endStage");
        Director.changeStage("welcomeStage");
    }

    public static void main(String[] args){
        Main mygame = new Main();
        mygame.run();
    }
}
