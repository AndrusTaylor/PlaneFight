//TODO tools and audios(optional)
package Stages;

import Objects.*;
import Parser.StageParser;
import com.gametemplate.Basic.*;
import com.gametemplate.Image.GTImage;
import com.gametemplate.Shape.Text;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class gameStage extends Stage {
    private Plane plane;
    public ArrayList<Enemy> enemies = new ArrayList<>();
    public ArrayList<Bullet> bullets = new ArrayList<>();
    public ArrayList<Supply> supplies = new ArrayList<>();
    public ArrayList<BGImage> bgimages = new ArrayList<>();
    private long timecount;
    private int flySpeed = 10;
    private Timer wtimer;
    private GTImage warning;
    private Score score;
    private StageParser parser;
    private boolean isend = false;

    public gameStage(String stagefile){
        super();
        enemies = new ArrayList<>();
        supplies = new ArrayList<>();
        bullets = new ArrayList<>();
        bgimages = new ArrayList<>();
        try {
            parser = new StageParser("./resource/stageInfo/" + stagefile);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void init(){
        super.init();
        enemies = new ArrayList<>();
        supplies = new ArrayList<>();
        bullets = new ArrayList<>();
        bgimages = new ArrayList<>();
        try {
            parser.parse();
        }catch (Exception e){
            System.out.println("pase failed");
            e.printStackTrace();
        }
        warning = ImageStorage.getGTImage("warning");
        score = new Score(50, 50, Color.WHITE);
        addToRender(score);
        Bullet.changeListener(bullets);
        SimplePlane.changeListener(enemies);
        Supply.changeListener(supplies);
        //this forcible pass by reference. Using array to achieve
        SimplePlane.changeGlobalScore(score);
        BGImage.changeListener(bgimages);

        plane = new Plane();
        plane.setVisiable(true);
        plane.moveTo(600, 500);
        addToRender(plane);

        this.timecount = 0;
    }

    @Override
    public void quit(){
        super.quit();
        for(int i=0;i<enemies.size();i++)
            enemies.get(i).destroy();
        for(int i=0;i<bullets.size();i++)
            bullets.get(i).destroy();
        for(int i=0;i<supplies.size();i++)
            supplies.get(i).destroy();
        for(int i=0;i<bgimages.size();i++)
            bgimages.get(i).destroy();
        enemies.clear();
        bullets.clear();
        supplies.clear();
        bgimages.clear();
        /*
        enemies = null;
        bullets = null;
        bgimages = null;
        supplies = null;
         */
        plane = null;
    }

    @Override
    public void KeyPressed(KeyEvent e){
        plane.control_keypressed(e);
        if(isend) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE)
                Director.changeStage(nextStage);
        }
    }

    @Override
    public void KeyReleased(KeyEvent e){
        plane.control_keyreleased(e);
    }

    @Override
    public void update(Graphics2D g){
        timecount+=flySpeed;
        parser.generateMap(timecount,flySpeed);
        for(int i=0;i<bgimages.size();i++)
            bgimages.get(i).draw(g);
        super.update(g);
        plane.collision(enemies);
        for(int i=0;i<bullets.size();i++){
            bullets.get(i).collision(enemies, plane);
        }
        for(int i=0;i<bullets.size();i++)
            bullets.get(i).draw(g);

        for(int i=0;i<supplies.size();i++)
            supplies.get(i).collision(plane);
        for(int i=0;i<supplies.size();i++)
            supplies.get(i).draw(g);

        for(int i=0;i<enemies.size();i++){
            enemies.get(i).draw(g);
        }
        if(enemies.isEmpty() && parser.isEmpty()) {
            isend = true;
            if(nextStage.equals("endStage"))
                Director.changeStage(nextStage);
            Text gameover = new Text("本关结束，按下空格进入下一关", 450, 350, 30, Font.BOLD);
            gameover.setColor(Color.GREEN);
            gameover.setVisiable(true);
            addToRender(gameover);
        }
    }
}
