package com.gametemplate.Image;

import com.gametemplate.Drawable;
import java.awt.*;
import java.util.*;

public class Animation extends com.gametemplate.Object implements Drawable{
    private ArrayList<GTImage> frames;
    private int interval;
    private int framecount;
    private boolean isloop;
    private boolean isplay;
    private int intervalcount;
    private boolean visiable;
    private double scalex;
    private double scaley;

    public Animation(String[] filenames, int interval){
        commonInit(interval);
        for(int i=0;i<filenames.length;i++){
            GTImage tmp = new GTImage(filenames[i]);
            tmp.setVisiable(true);
            frames.add(tmp);
        }
    }

    public Animation(GTImage[] images, int interval){
        commonInit(interval);
        for(int i=0;i<images.length;i++) {
            frames.add(images[i]);
            images[i].setVisiable(true);
        }
    }

    private void commonInit(int interval){
        frames = new ArrayList<>();
        this.interval = interval;
        framecount = 0;
        isloop = false;
        isplay = false;
        intervalcount = 0;
        x = 0;
        y = 0;
        scalex = 1;
        scaley = 1;
    }

    @Override
    public Point getCenter(){
        if(frames.isEmpty())
            return new Point(-1, -1);
        return new Point(x + frames.get(0).getWidth()/2, y + frames.get(0).getHeight()/2);
    }

    public void setVisiable(boolean v){
        visiable = v;
    }

    public boolean getVisiable(){
        return visiable;
    }

    public Animation(ArrayList<GTImage> images, int interval){
        frames = (ArrayList<GTImage>)images.clone();
    }

    public void play(){
        isplay = true;
    }

    public double getScaleX(){ return scalex; }

    public double getScaleY(){ return scaley; }

    //animation state

    public boolean isPlay(){
        return isplay;
    }

    public int getWidth(){
        if(frames.isEmpty())
            return -1;
        return frames.get(0).getWidth();
    }

    public int getHeight(){
        if(frames.isEmpty())
            return -1;
        return frames.get(0).getHeight();
    }

    public void pause(){
        if(isplay)
            isplay = false;
    }

    public void stop(){
        if(isplay){
            isplay = false;
            framecount = 0;
        }
    }

    //override some function to apply modifies to every frames

    @Override
    public void rotate(double angle){
        super.rotate(angle);
        for(int i=0;i<frames.size();i++)
                frames.get(i).rotate(angle);
    }

    @Override
    public void moveTo(int x, int y){
        super.moveTo(x, y);
        for(int i=0;i<frames.size();i++)
            frames.get(i).moveTo(x, y);
    }

    @Override
    public void translation(int dx, int dy){
        super.translation(dx, dy);
        for(int i=0;i<frames.size();i++)
            frames.get(i).translation(dx, dy);
    }

    @Override
    public void rotateDelta(double angle){
        super.rotateDelta(angle);
        for(int i=0;i<frames.size();i++)
            frames.get(i).rotateDelta(angle);
    }

    @Override
    public void flip(boolean X, boolean Y){
        super.flip(X, Y);
        for(int i=0;i<frames.size();i++)
            frames.get(i).flip(X, Y);
    }

    public GTImage getCurrentImage(){
        if(frames.isEmpty())
            return frames.get(framecount);
        return null;
    }

    public void setScale(int x, int y){
        scaley = y;
        scalex = x;
        for(int i=0;i<frames.size();i++)
            frames.get(i).setScale(x, y);
    }

    public void setLoop(boolean loop){
        isloop = loop;
    }

    public void draw(Graphics2D g){
        if(frames.isEmpty())
            return ;
        if(isplay) {
            if(visiable)
                frames.get(framecount).draw(g);
            if(intervalcount < interval)
                intervalcount++;
            else{
                framecount++;
                intervalcount = 0;
            }
            if(framecount >= frames.size()) {
                if (!isloop)
                    isplay = false;
                framecount = 0;
            }
        }
    }
}
