package Objects;

import com.gametemplate.Drawable;
import com.gametemplate.Object;
import com.gametemplate.Shape.Rect;

import java.awt.*;

public abstract class Enemy extends Object implements Drawable {
    protected int score;
    //in this show your enemy logic
    public abstract void logic();
    public abstract Rect getCollision();
    public abstract void collision(Plane plane);
    public abstract void destroy();
    public abstract void hpDown(int decrease);
    public int getScore(int score){ return score;}
}
