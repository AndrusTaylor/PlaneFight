package Objects;

import com.gametemplate.Basic.Director;
import com.gametemplate.Basic.ImageStorage;
import com.gametemplate.Image.Animation;
import com.gametemplate.Image.GTImage;
import com.gametemplate.Shape.Rect;
import sun.java2d.pipe.SpanShapeRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SimplePlane extends Enemy{
    private GTImage image;
    private Rect collision_rect;
    private int hp;
    private Animation dieAni;
    private boolean isdie;
    private Point moveDir;
    private Enemy_Logic_Type logic_type;
    private int sinA;
    private int sinDy;
    private int crossHeight;
    private int crossLeft;
    private int crossRight;
    private int crossdir;
    private Timer bulletTimer;

    private static ArrayList<Enemy> listener;
    private static Score GlobalScore;

    public static void changeListener(ArrayList<Enemy> l){
        SimplePlane.listener = l;
    }

    public static void changeGlobalScore(Score s){
        SimplePlane.GlobalScore = s;
    }


    public static void registerInstance(Enemy enemy){
        enemy.setVisiable(true);
        SimplePlane.listener.add(enemy);
    }
    public static SimplePlane registerLinearEnemy(int hp, int nx, int ny, int score, String img, String[] dieimgs, Point dir){
        SimplePlane enemy = new SimplePlane(hp, nx, ny, score, img, dieimgs, Enemy_Logic_Type.LINEAR);
        enemy.setLinearDir(dir);
        enemy.setVisiable(true);
        SimplePlane.listener.add(enemy);
        return enemy;
    }

    public static SimplePlane registerSinEnemy(int hp, int nx, int ny, int score, String img, String[] dieimgs, int a, int dy){
        SimplePlane enemy = new SimplePlane(hp, nx, ny, score, img, dieimgs, Enemy_Logic_Type.SIN);
        enemy.setSinAB(a, dy);
        enemy.setVisiable(true);
        SimplePlane.listener.add(enemy);
        return enemy;
    }

    public static SimplePlane registerBehindEnemy(int hp, int nx, int score, String img, String[] dieimgs, int vel_y){
        SimplePlane enemy = new SimplePlane(hp, nx, 0, score, img, dieimgs, Enemy_Logic_Type.BEHIND);
        enemy.setBehindVelY(vel_y);
        enemy.setVisiable(true);
        SimplePlane.listener.add(enemy);
        return enemy;
    }

    public static SimplePlane registerCrossEnemy(int hp, int nx, int ny, int score,String img, String[] dieimgs, int ch, int cl, int cr){
        SimplePlane enemy = new SimplePlane(hp, nx, ny, score, img, dieimgs, Enemy_Logic_Type.CROSS);
        enemy.setCrossParam(ch, cl, cr);
        enemy.setVisiable(true);
        SimplePlane.listener.add(enemy);
        return enemy;
    }

    public SimplePlane(int hp, int nx, int ny, int score, String img, String[] dieimgs, Enemy_Logic_Type ntype){
        moveDir = null;
        image = ImageStorage.getGTImage(img);
        image.setVisiable(true);
        this.score = score;
        this.x = nx;
        this.y = ny;
        this.hp = hp;
        this.isdie = false;
        this.logic_type = ntype;
        this.sinA = 0;
        this.sinDy = 0;
        this.crossHeight = 0;
        this.crossLeft = -1;
        this.crossRight = -1;
        this.crossdir = 1;
        collision_rect = new Rect(0, 0, image.getWidth()-10, image.getHeight()-20);
        collision_rect.setCenter(x, y);
        collision_rect.setColor(Color.MAGENTA);
        dieAni = new Animation(dieimgs, 4);
        bulletTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Bullet.registerInstance(x, y+image.getHeight()/2, BulletType.ENEMY, AttachBulletType.NORMAL);
            }
        });
    }

    public void setCrossParam(int ch, int cl, int cr){
        setCrossBound(cl, cr);
        setCrossHeight(ch);
    }

    private void setCrossHeight(int ch){
        this.crossHeight = ch;
    }

    private void setCrossBound(int left, int right){
        this.crossLeft = left;
        this.crossRight = right;
    }

    public void setSinAB(int a, int dy){
        sinA = a;
        sinDy = dy;
    }

    public void setBehindVelY(int y){
        moveDir = new Point(0, y);
        this.y = Director.getInstance().getCanva().getHeight()+image.getHeight();
        image.moveTo(this.x, this.y);
        collision_rect.setCenter(this.x, this.y);
    }

    private void shoot(){
        bulletTimer.start();
    }

    public void enableShoot(){
        shoot();
    }

    public void setLinearDir(Point dir){
        moveDir = dir;
    }

    @Override
    public void destroy(){
        if(!isdie){
            dieAni.moveTo(x, y);
            dieAni.setVisiable(true);
            dieAni.play();
            isdie = true;
            collision_rect.setCenter(-100, -100);
            bulletTimer.stop();
        }
    }

    @Override
    public void hpDown(int decrease){
        hp -= decrease;
        if(hp <= 0) {
            SimplePlane.GlobalScore.increaseScore(score);
            destroy();
        }
    }

    @Override
    public Rect getCollision(){
        return collision_rect;
    }

    @Override
    public void collision(Plane plane){
        //TODO when collisied with bullet will destroy
    }

    @Override
    public Point getCenter(){
        return image.getCenter();
    }

    @Override
    public void logic(){
        switch (logic_type) {
            case LINEAR:
                collision_rect.translation(moveDir.x, moveDir.y);
                break;
            case BEHIND:
                collision_rect.translation(moveDir.x, -Math.abs(moveDir.y));
                break;
            case SIN:
                collision_rect.translation(0, sinDy);
                collision_rect.translation(new Double(sinA*Math.sin(collision_rect.getCenter().y/20.0f)).intValue(), 0);
                break;
            case CROSS:
                if(collision_rect.getCenter().y < crossHeight)
                    collision_rect.translation(0, 4);
                else{
                    if(collision_rect.getCenter().x > crossRight && crossdir == 1)
                        crossdir = -1;
                    else if(collision_rect.getCenter().x < crossLeft && crossdir == -1)
                        crossdir = 1;
                    collision_rect.translation(2*crossdir, 0);
                }
                break;
        }
        this.x = collision_rect.getCenter().x;
        this.y = collision_rect.getCenter().y;
    }

    @Override
    public void draw(Graphics2D g){
        image.moveTo(collision_rect.getCenter().x, collision_rect.getCenter().y);
        if(isVisiable() && !isdie){
            logic();
            image.draw(g);
            if(Director.debugMode) {
                collision_rect.draw(g);
            }
            if(y > Director.getInstance().getCanva().getHeight() + 300)
                destroy();
        }

        if(isdie){
            dieAni.draw(g);
            if(!dieAni.isPlay())
                SimplePlane.listener.remove(this);
        }
    }
}
