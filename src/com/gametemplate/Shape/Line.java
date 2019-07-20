package com.gametemplate.Shape;

import java.awt.*;

public class Line extends com.gametemplate.Shape.Shape{
    private int x2;
    private int y2;
    public Line(int x, int y, int x2, int y2){
        this.x = x;
        this.y = y;
        this.x2 = x2;
        this.y2 = y2;
    }

    public Line(){
        this.x = 0;
        this.y = 0;
        this.x2 = 0;
        this.y2 = 0;
    }

    public Point getCenter(){
        return new Point((x+x2)/2, (y+y2)/2);
    }

    public Point getP1(){
        return new Point(x, y);
    }

    public Point getP2(){
        return new Point(x2, y2);
    }

    public boolean collision(Shape o){
        //TODO implement it
        return false;
    }

    public void draw(Graphics2D g){
        g.setColor(color);
        g.drawLine(x, y, x2, y2);
    }
}
