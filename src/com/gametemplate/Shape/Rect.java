package com.gametemplate.Shape;

import java.awt.*;

public class Rect extends com.gametemplate.Shape.Shape{
    private int w;
    private int h;

    public Rect(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public Rect() {
        this.x = 0;
        this.y = 0;
        this.w = 0;
        this.h = 0;
    }

    public void resize(int w, int h){
        this.w = w;
        this.h = h;
    }

    @Override
    public Point getCenter() {
        return new Point(this.x+this.w/2, this.y+this.h/2);
    }

    public void setCenter(int x, int y){
        this.x = x-this.w/2;
        this.y = y-this.h/2;
    }

    public Point getTL(){
        return new Point(this.x, this.y);
    }

    public int getWidth(){ return w; }

    public int getHeight(){ return h; }

    public boolean collision(Rect r){
        if(y+h<r.y || y>r.y+r.h)
            return false;
        else if(x > r.x+r.w || x+w<r.x)
            return false;
        else
            return true;
    }

    public void draw(Graphics2D g){
        g.setColor(color);
        g.drawRect(x, y, w, h);
    }
}
