package com.gametemplate.Shape;

import java.awt.*;

public class Circle extends com.gametemplate.Shape.Shape{
    private int r;
    public Circle(int x, int y, int r){
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public Circle(int r){
        this.x = 0;
        this.y = 0;
        this.r = r;
    }

    public Point getCenter(){
        return new Point(x, y);
    }

    public void setCenter(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setRadius(int r){
        this.r = r;
    }

    //rotation has no effect on circle
    @Override
    public void rotate(double angle){}

    public boolean collision(Shape o){
        //TODO inplement it
        return false;
    }

    @Override
    public void draw(Graphics2D g){
        g.setColor(color);
        g.drawArc(x, y, r, r, 0,360);
    }
}
