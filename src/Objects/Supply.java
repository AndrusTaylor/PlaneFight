package Objects;

import com.gametemplate.Basic.Director;
import com.gametemplate.Basic.ImageStorage;
import com.gametemplate.Drawable;
import com.gametemplate.Image.GTImage;
import com.gametemplate.Shape.Rect;

import java.awt.*;
import java.util.ArrayList;

public class Supply extends com.gametemplate.Object implements Drawable {
    private GTImage image;
    private Rect collision_rect;
    private int vel_x = 4;
    private int vel_y = 4;
    private SupplyType type;

    public static void changeListener(ArrayList<Supply> s){
        Supply.listener = s;
    }

    public static void registerInstance(Supply s){
        s.setVisiable(true);
        Supply.listener.add(s);
    }

    public static void registerSupply(String filename, int x, int y, SupplyType t){
        Supply s = new Supply(filename, x, y, t);
        s.setVisiable(true);
        Supply.listener.add(s);
    }

    private static ArrayList<Supply> listener = null;

    public Supply(String imgfilename, int x, int y, SupplyType t){
        super();
        image = ImageStorage.getGTImage(imgfilename);
        image.moveTo(x, y);
        image.setVisiable(true);
        this.x = x;
        this.y = y;
        this.type = t;
        collision_rect = new Rect(0, 0, image.getWidth(), image.getHeight());
        collision_rect.setCenter(x, y);
        collision_rect.setColor(Color.ORANGE);
    }

    @Override
    public Point getCenter(){
        return image.getCenter();
    }

    public void collision(Plane plane){
        if(collision_rect.collision(plane.getCollision())){
            switch (type) {
                case BOMB:
                    plane.addBomb();
                    break;
                case LAZER:
                    plane.changeToLazer();
                    break;
            }
            destroy();
        }
    }

    //TODO write your own logic
    private void logic(){
        int canvawidth = Director.getInstance().getCanva().getWidth(),
                canvaheight = Director.getInstance().getCanva().getHeight();
        if(x+image.getWidth()>canvawidth)
            vel_x=-Math.abs(vel_x);
        if(x<0)
            vel_x=Math.abs(vel_x);
        if(y+image.getHeight()>canvaheight)
            vel_y=-Math.abs(vel_y);
        if(y<0)
            vel_y=Math.abs(vel_y);
        translation(vel_x, vel_y);
        image.moveTo(x, y);
        collision_rect.setCenter(x, y);
    }

    public void destroy(){
        Supply.listener.remove(this);
    }

    @Override
    public void draw(Graphics2D g){
        if(isVisiable()){
            logic();
            image.draw(g);
            if(Director.debugMode) {
                collision_rect.draw(g);
            }
        }
    }
}
