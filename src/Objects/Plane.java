package Objects;

import Stages.gamestage1;
import com.gametemplate.Basic.Canva;
import com.gametemplate.Basic.Director;
import com.gametemplate.Basic.ImageStorage;
import com.gametemplate.Drawable;
import com.gametemplate.Image.Animation;
import com.gametemplate.Image.GTImage;
import com.gametemplate.Object;
import com.gametemplate.Shape.Rect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class Plane extends Object implements Drawable {
    private static int SPEED = 10;
    private boolean key_A;
    private boolean key_D;
    private boolean key_W;
    private boolean key_S;
    private boolean key_J;
    private boolean key_K;

    private boolean isdie;
    private AttachBulletType btype;
    private int bombcount;
    private GTImage bomb_image;

    private Timer addBulletTimer;

    private Animation ani;
    private Animation dieAni;
    private Rect collision_rect;

    public Plane(){
        super();
        String frames[] = {"plane1", "plane2"};
        ani = new Animation(frames, 4);
        ani.setLoop(true);
        ani.setVisiable(true);
        ani.play();
        String dieframes[] = {"planeDestroy1", "planeDestroy2", "planeDestroy3"};
        dieAni = new Animation(dieframes, 5);
        collision_rect = new Rect(0 ,0 , ani.getWidth()/3, ani.getHeight()/2);
        collision_rect.setCenter(x, y);
        collision_rect.setColor(Color.GREEN);
        this.isdie = false;
        this.bombcount = 3;

        setAttachBulletType(AttachBulletType.NORMAL);

        bomb_image = ImageStorage.getGTImage("bomb");
        bomb_image.setVisiable(true);


        int interval = 90;
        switch (btype) {
            case NORMAL:
                interval = 90;
                break;
            case LAZER:
                interval = 30;
        }
        addBulletTimer = new Timer(interval, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Bullet.registerInstance(x, y-ani.getHeight()/2, BulletType.PLANE, btype);
            }
        });
    }

    public void setAttachBulletType(AttachBulletType t){
        btype = t;
    }

    public void control_keypressed(KeyEvent e){
        switch(e.getKeyCode()) {
            case KeyEvent.VK_A:
                key_A = true;
                break;
            case KeyEvent.VK_D:
                key_D = true;
                break;
            case KeyEvent.VK_W:
                key_W = true;
                break;
            case KeyEvent.VK_S:
                key_S = true;
                break;
            case KeyEvent.VK_J:
                key_J = true;
                break;
            case KeyEvent.VK_K:
                if(bombcount!=0 && !key_K){
                    key_K = true;
                    bombcount--;
                    bombAll();
                }
                break;
        }
    }

    private void bombAll(){
        //Java supports downcast!
        for(int i=0;i<10;i++) {
            String[] images = {"bomb_brust1", "bomb_brust2", "bomb_brust3"};
            Animation ani = new Animation(images, 2);
            Random rand = new Random();
            int bombX = rand.nextInt(900)+100,
                    bombY = rand.nextInt(500)+100;
            ani.moveTo(bombX, bombY);
            ani.setLoop(false);
            ani.setVisiable(true);
            ani.play();
            Director.getCurrentStage().addToEffect(ani);
        }
        ArrayList<Enemy> enemies = ((gamestage1)Director.getCurrentStage()).enemies;
        for(int i=0;i<enemies.size();i++){
            enemies.get(i).hpDown(50);
        }
    }

    public void control_keyreleased(KeyEvent e){
        switch(e.getKeyCode()) {
            case KeyEvent.VK_A:
                key_A = false;
                break;
            case KeyEvent.VK_D:
                key_D = false;
                break;
            case KeyEvent.VK_W:
                key_W = false;
                break;
            case KeyEvent.VK_S:
                key_S = false;
                break;
            case KeyEvent.VK_J:
                key_J = false;
                break;
            case KeyEvent.VK_K:
                key_K = false;
                break;
        }
    }

    public void addBomb(){
        bombcount++;
    }

    public void changeToLazer(){
        btype = AttachBulletType.LAZER;
    }

    public void destroy(){
        if(!isdie) {
            isdie = true;
            dieAni.moveTo(x, y);
            dieAni.setVisiable(true);
            dieAni.play();
            this.addBulletTimer.stop();
            JButton retryButton = new JButton("游戏结束（点击这个按钮重来）");
            retryButton.setVisible(true);
            retryButton.setBounds(500, 400, 200, 100);
            Director.getCurrentStage().addToUI(retryButton);
            retryButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Director.getCurrentStage().quit();
                    Director.getCurrentStage().init();
                    retryButton.setVisible(false);
                }
            });
        }
    }

    public void collision(ArrayList<Enemy> enemies){
        if(isVisiable() && !isdie) {
            for (int i = 0; i < enemies.size(); i++)
                if (collision_rect.collision(enemies.get(i).getCollision())) {
                    destroy();
                    enemies.get(i).destroy();
                }
        }
    }

    public Rect getCollision(){
        return collision_rect;
    }

    public void control(){
        if(key_A)
            x -= SPEED;
        if(key_D)
            x += SPEED;
        if(key_W)
            y -= SPEED;
        if(key_S)
            y += SPEED;
        if(isVisiable() && !isdie){
            if(key_J)
                addBulletTimer.start();
            else
                addBulletTimer.stop();
        }

        collision_rect.setCenter(x, y);

        //border collision
        if( collision_rect.getTL().getX() < 0 ) {
            collision_rect.setCenter(collision_rect.getWidth()/2, collision_rect.getCenter().y);
            x = collision_rect.getCenter().x;
            y = collision_rect.getCenter().y;
        }
        Canva canva = Director.getInstance().getCanva();
        if(collision_rect.getTL().x + collision_rect.getWidth() > canva.getWidth()) {
            collision_rect.setCenter(canva.getWidth() - collision_rect.getWidth()/2, collision_rect.getCenter().y);
            x = collision_rect.getCenter().x;
            y = collision_rect.getCenter().y;
        }
        if(collision_rect.getTL().y < 0) {
            collision_rect.setCenter(collision_rect.getCenter().x, collision_rect.getHeight()/2);
            x = collision_rect.getCenter().x;
            y = collision_rect.getCenter().y;
        }
        if(collision_rect.getTL().y + collision_rect.getHeight() > canva.getHeight()) {
            collision_rect.setCenter(collision_rect.getCenter().x, canva.getHeight() - collision_rect.getHeight()/2);
            x = collision_rect.getCenter().x;
            y = collision_rect.getCenter().y;
        }

        ani.moveTo(x, y);
    }

    private void drawBombs(Graphics2D g){
        for(int i=0;i<bombcount;i++){
            bomb_image.moveTo(i*30+50, 700);
            bomb_image.draw(g);
        }
    }

    @Override
    public Point getCenter(){
        return new Point(-1, -1);
    }

    public void draw(Graphics2D g){
        control();
        if(visiable && !isdie) {
                ani.draw(g);
            if(Director.debugMode) {
                collision_rect.draw(g);
            }
        }
        if(isdie) {
            dieAni.draw(g);
            if(dieAni.isPlay())
                setVisiable(false);
        }
        drawBombs(g);
    }
}