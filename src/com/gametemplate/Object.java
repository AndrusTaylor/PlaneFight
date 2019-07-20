package com.gametemplate;

import java.awt.*;

public abstract class Object {
    protected int x;
    protected int y;
    protected double angle;
    protected boolean flipX;
    protected boolean flipY;
    protected boolean visiable;
    protected float transparent;
    public Object(int x, int y){
        this.x = x;
        this.y = y;
        init();
    }

    public Object(){
        this.x = 0;
        this.y = 0;
        init();
    }

    protected void init(){
        angle = 0;
        flipX = false;
        flipY = false;
        visiable = false;
        transparent = 1;
    }

    public void rotate(double angle){
        this.angle = angle;
    }

    public void rotateDelta(double angle){ this.angle += angle; }

    public void translation(int dx, int dy){
        this.x += dx;
        this.y += dy;
    }

    public void moveTo(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void flip(boolean X, boolean Y){
        this.flipY = Y;
        this.flipX = X;
    }

    public double getAngle(){
        return angle;
    }

    public void setVisiable(boolean isshow){
        visiable = isshow;
    }

    public boolean isVisiable(){ return visiable; }

    public abstract Point getCenter();

    public void setTransparent(float t){
        transparent = t;
    }

    public float getTransparent(){ return transparent; }
}
