package Stages;

import com.gametemplate.Basic.*;
import com.gametemplate.Shape.*;

import java.awt.*;
import java.awt.event.KeyEvent;

public class endStage extends Stage{
    public endStage(){
        super();
        init();
    }

    public void init(){
        super.init();
        Text gameover = new Text("游戏结束，恭喜通关", 450, 350, 30, Font.PLAIN);
        gameover.setColor(Color.lightGray);
        gameover.setVisiable(true);
        Text authors = new Text("作者:VisualGMQ   关卡设计:小虎", 400, 450, 24, Font.ITALIC);
        authors.setColor(Color.YELLOW);
        authors.setVisiable(true);
        addToRender(gameover);
        addToRender(authors);
    }

    @Override
    public void KeyPressed(KeyEvent e){
        Director.changeStage("welcomeStage");
    }

    @Override
    public void KeyReleased(KeyEvent e){

    }
}