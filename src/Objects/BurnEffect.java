package Objects;

import com.gametemplate.Basic.Director;
import com.gametemplate.Drawable;
import com.gametemplate.Image.Animation;
import com.gametemplate.Object;

import java.awt.*;
import java.util.ArrayList;

public class BurnEffect extends Object implements Drawable {
    private Animation ani;

    public BurnEffect(int x, int y, String[] aniFrames){
        setVisiable(true);
        ani = new Animation(aniFrames, 2);
        ani.moveTo(x, y);
        ani.setVisiable(true);
        ani.setLoop(false);
        ani.play();
        Director.getCurrentStage().getEffectList().add(this);
    }

    public void destroy(){
        Director.getCurrentStage().getEffectList().remove(this);
    }

    @Override
    public Point getCenter(){
        return ani.getCenter();
    }

    @Override
    public void draw(Graphics2D g){
        if(isVisiable()){
            ani.draw(g);
        }
        if(!ani.isPlay())
            destroy();
    }
}
