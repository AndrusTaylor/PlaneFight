//TODO tools and audios(optional)
package Stages;

import Objects.*;
import Parser.StageParser;
import com.gametemplate.Basic.*;
import com.gametemplate.Image.GTImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class gamestage1 extends Stage {
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
    public void init(){
        warning = ImageStorage.getGTImage("warning");
        score = new Score(50, 50, Color.WHITE);
        addToRender(score);
        try {
            parser = new StageParser("./resource/stageInfo/stage1.txt");
        }catch (Exception e){
            e.printStackTrace();
        }
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
        enemies.clear();
        bullets.clear();
        plane = null;
    }

    @Override
    public void KeyPressed(KeyEvent e){
        plane.control_keypressed(e);
    }

    @Override
    public void KeyReleased(KeyEvent e){
        plane.control_keyreleased(e);
    }

    private boolean beyonedRange(int limit){
        return timecount < limit && timecount+flySpeed >= limit;
    }

    private void showBoss(){
        warning.moveTo(600, 400);
        addToRender(warning);
        wtimer = new Timer(500, new ActionListener() {
            private int count=0;
            @Override
            public void actionPerformed(ActionEvent e) {
                warning.setVisiable(!warning.isVisiable());
                count++;
                if(count >= 8 && count-1<8) {
                    String[] cplaneDie = {"enemy3_down1", "enemy3_down2", "enemy3_down3", "enemy3_down4", "enemy3_down5", "enemy3_down6"};
                    SimplePlane.registerCrossEnemy(100, 600, -100, 200, "enemy3_n1", cplaneDie, 200, 100, 1100);
                }
            }
        });
        wtimer.start();
    }

    /*
    private void generateMap(){
        String[] plane1Die = {"enemy1_down1", "enemy1_down2", "enemy1_down3", "enemy1_down4"};
        String[] cplaneDie = {"enemy3_down1", "enemy3_down2", "enemy3_down3", "enemy3_down4", "enemy3_down5", "enemy3_down6"};
        String[] plane2Die = {"enemy2_down1", "enemy2_down2", "enemy2_down3", "enemy2_down4"};
        String[] smallDie = {"smallenemy_down1", "smallenemy_down2", "smallenemy_down3"};
        if(beyonedRange(800)){
            SimplePlane.registerLinearEnemy(5, 100, -50, 10,"enemy1", plane1Die, new Point(0, 4));
            SimplePlane.registerLinearEnemy(5, 300, -100, 10,"enemy1", plane1Die, new Point(0, 4));
            SimplePlane.registerLinearEnemy(5, 500, -150, 10,"enemy1", plane1Die, new Point(0, 4));
            SimplePlane.registerLinearEnemy(5, 700, -150, 10,"enemy1", plane1Die, new Point(0, 4));
            SimplePlane.registerLinearEnemy(5, 900, -100, 10,"enemy1", plane1Die, new Point(0, 4));
            SimplePlane.registerLinearEnemy(5, 1100, -50, 10,"enemy1", plane1Die, new Point(0, 4));
            Supply.registerSupply("levelup", 400, 400, SupplyType.LAZER);
        }

        if(beyonedRange(3200)){
            SimplePlane.registerCrossEnemy(50, 300, -100, 50, "enemy3_n1", cplaneDie, 100, 100, 500).enableShoot();
            SimplePlane.registerCrossEnemy(50, 900, -100, 50, "enemy3_n1", cplaneDie, 100, 700, 1100).enableShoot();
            SimplePlane.registerBehindEnemy(50, 300, 50, "smallenemy", smallDie,2);
            SimplePlane.registerBehindEnemy(50, 600,  50, "smallenemy", smallDie,2);
            SimplePlane.registerBehindEnemy(50, 900,  50, "smallenemy", smallDie, 2);
        }

        if(beyonedRange(5600)){
            SimplePlane.registerLinearEnemy(5, 350, -50, 15,"enemy2", plane2Die, new Point(4, 4));
            SimplePlane.registerLinearEnemy(5, 300, -100, 15,"enemy2", plane2Die, new Point(4, 4));
            SimplePlane.registerLinearEnemy(5, 250, -150, 15,"enemy2", plane2Die, new Point(4, 4));
            SimplePlane.registerLinearEnemy(5, 950, -50, 15,"enemy2", plane2Die, new Point(-4, 4));
            SimplePlane.registerLinearEnemy(5, 1000, -100, 15,"enemy2", plane2Die, new Point(-4, 4));
            SimplePlane.registerLinearEnemy(5, 1050, -150, 15,"enemy2", plane2Die, new Point(-4, 4));
        }
        if(beyonedRange(8000))
            showBoss();
        if(beyonedRange(10000)) {
            warning.setVisiable(false);
            wtimer.stop();
        }
    }
     */

    @Override
    public void update(Graphics2D g){
        timecount+=10;
        parser.generateMap(timecount, 10);
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
    }
}