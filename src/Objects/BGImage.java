package Objects;

import com.gametemplate.Basic.Director;
import com.gametemplate.Basic.ImageStorage;
import com.gametemplate.Drawable;
import com.gametemplate.Image.GTImage;
import com.gametemplate.Object;

import java.awt.*;
import java.util.ArrayList;

public class BGImage extends Object implements Drawable {
    private GTImage image;
    private int vel_y;
    private static ArrayList<BGImage> listener;

    public static void changeListener(ArrayList<BGImage> l){
        listener = l;
    }

    public static void registerBGImage(String filename, int x, int y, int vel_y){
        BGImage image = new BGImage(filename, x, y, vel_y);
        image.setVisiable(true);
        listener.add(image);
    }

    public static void registerInstance(BGImage instance){
        instance.setVisiable(true);
        listener.add(instance);
    }

    public BGImage(String filename, int x, int y, int vel_y){
        super();
        image = ImageStorage.getGTImage(filename);
        image.setVisiable(true);
        image.moveTo(x, y);
        this.x = x;
        this.y = y;
        this.vel_y = vel_y;
    }

    @Override
    public Point getCenter(){
        return image.getCenter();
    }

    public void destroy(){
            Director.getCurrentStage().getRenderList().remove(this);
    }

    @Override
    public void draw(Graphics2D g){
        if(isVisiable()){
            image.translation(0, Math.abs(vel_y));
            x = image.getCenter().x;
            y = image.getCenter().y;
            image.draw(g);
        }
        if(image.getCenter().y > Director.getInstance().getCanva().getHeight()+image.getHeight()*1.5)
            destroy();
    }
}
