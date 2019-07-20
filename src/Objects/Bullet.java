package Objects;

import com.gametemplate.Basic.Director;
import com.gametemplate.Basic.ImageStorage;
import com.gametemplate.Drawable;
import com.gametemplate.Image.GTImage;
import com.gametemplate.Object;
import com.gametemplate.Shape.*;

import java.awt.*;
import java.util.ArrayList;

public class Bullet extends Object implements Drawable {

    public static void changeListener(ArrayList<Bullet> l){
        Bullet.listener = l;
    }

    public static ArrayList<Bullet> listener;

    public static Bullet registerInstance(int x,int y, BulletType type, AttachBulletType atype){
        Bullet b = new Bullet(x, y, type, atype);
        b.setVisiable(true);
        b.shoot();
        Bullet.listener.add(b);
        return b;
    }

    private GTImage image;
    private Rect collision_rect;
    private boolean isshoot;
    private static int SPEED = 20;
    private int damage;
    private BulletType type;

    public Bullet(int x, int y, BulletType type, AttachBulletType atype){
        if(type == BulletType.PLANE) {
            if(atype == AttachBulletType.NORMAL) {
                image = ImageStorage.getGTImage("bullet1");
                setDamage(1);
            }
            if(atype == AttachBulletType.LAZER) {
                image = ImageStorage.getGTImage("lazer");
                setDamage(3);
            }
        }else {
            image = ImageStorage.getGTImage("bullet2");
        }
        image.setVisiable(true);
        this.collision_rect = new Rect(0, 0, image.getWidth(), image.getHeight());
        this.collision_rect.setColor(Color.RED);
        this.x = x;
        this.y = y;
        this.isshoot = false;
        this.type = type;
        collision_rect.setCenter(x, y);
    }

    public void setDamage(int damage){
        this.damage = damage;
    }

    public void collision(ArrayList<Enemy> enemies, Plane plane){
        if(type == BulletType.PLANE) {
            for (int i = 0; i < enemies.size(); i++)
                if (collision_rect.collision(enemies.get(i).getCollision())) {
                    destroy();
                    enemies.get(i).hpDown(damage);
                }
        }else{
            if(collision_rect.collision(plane.getCollision())) {
                destroy();
                plane.destroy();
            }
        }
    }

    public void shoot(){
        isshoot = true;
    }

    public void destroy(){
        String[] images = {"burn1", "burn2"};
        BurnEffect burn = new BurnEffect(x, y, images);
        burn.setVisiable(true);
        Director.getCurrentStage().addToEffect(burn);
        Bullet.listener.remove(this);
    }

    @Override
    public Point getCenter(){
        return image.getCenter();
    }

    @Override
    public void draw(Graphics2D g){
        if(visiable){
            if(type == BulletType.PLANE)
                translation(0, -SPEED);
            else
                translation(0, SPEED);
            image.moveTo(x, y);
            if(isshoot) {
                collision_rect.setCenter(x, y);
            }
            if(Director.debugMode)
                collision_rect.draw(g);
            image.draw(g);
        }
        if(collision_rect.getTL().y + collision_rect.getHeight() + 100 < 0)
            destroy();
    }
}
