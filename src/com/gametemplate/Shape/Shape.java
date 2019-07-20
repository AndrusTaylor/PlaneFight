package com.gametemplate.Shape;

import com.gametemplate.Drawable;

import java.awt.*;

public abstract class Shape extends com.gametemplate.Object implements Drawable {
    protected Color color;
    public void setColor(Color color){
        this.color = color;
    }
    public Color getColor(){
        return this.color;
    }
    //public abstract boolean collision(Shape o);
}
