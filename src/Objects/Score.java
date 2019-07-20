package Objects;

import com.gametemplate.Drawable;
import com.gametemplate.Shape.Text;

import java.awt.*;

public class Score implements Drawable {
    private Text tScore;
    private int score;

    public Score(int x, int y, Color color){
        tScore = new Text("Score:"+score, x, y, 24, Font.BOLD);
        tScore.setColor(color);
        tScore.setVisiable(true);
    }


    public void increaseScore(int s){
        score += s;
    }

    @Override
    public void draw(Graphics2D g){
        tScore.setText("Score:"+score);
        tScore.draw(g);
    }
}
