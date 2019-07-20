package Stages;

import com.gametemplate.Basic.Director;
import com.gametemplate.Basic.Stage;
import com.gametemplate.Image.Animation;
import com.gametemplate.Shape.Text;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.Timer;

public class welcomeStage extends Stage {
    public Text title;
    public Text blinkText;
    public Animation flyani;
    public Timer blinkTimer;
    public void init(){
        title = new Text("飞机大战", 100, 100, 100, Font.PLAIN);
        title.setColor(Color.CYAN);
        title.moveTo(400, 200);
        title.setVisiable(true);

        blinkText = new Text("按下空格键开始游戏", 470, 650, 30, Font.BOLD);
        blinkText.setColor(Color.BLUE);
        blinkText.setVisiable(true);
        blinkTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                blinkText.setVisiable(!blinkText.isVisiable());
            }
        });
        blinkTimer.start();

        String[] frames={"plane1", "plane2"};
        flyani = new Animation(frames, 4);
        flyani.moveTo(600, 500);
        flyani.setVisiable(true);
        flyani.setLoop(true);
        flyani.play();
        addToRender(flyani);
        addToRender(title);
        addToRender(blinkText);
    }

    @Override
    public void KeyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_SPACE)
            Director.changeStage("gameStage1");
    }

    @Override
    public void KeyReleased(KeyEvent e){
    }
}
