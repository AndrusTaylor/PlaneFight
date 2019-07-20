package com.gametemplate.Image;

import com.gametemplate.Basic.*;
import com.gametemplate.Drawable;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.rmi.MarshalException;

public class GTImage extends com.gametemplate.Object implements Drawable {
    public Image image;
    private int width;
    private int height;
    private double scalex;
    private double scaley;

    public GTImage(String key){
        super();
        scalex = 1;
        scaley = 1;
        if(ImageStorage.hasImage(key)) {
            image = ImageStorage.getImage(key);
            ImageIcon icon = new ImageIcon(image);
            width = icon.getIconWidth();
            height = icon.getIconHeight();
            icon = null;
        }else
            System.out.println("[GTImage]:no image "+ key);
    }

    public Point getCenter(){
        return new Point(x+getWidth()/2,y+getHeight()/2);
    }

    public int getRowWidth(){
        return width;
    }

    public int getRowHeight(){
        return height;
    }

    public int getWidth(){ return new Double(width*scalex).intValue(); }

    public int getHeight(){ return new Double(height*scaley).intValue(); }

    public void setScale(int x, int y){
        scalex = x;
        scaley = y;
    }

    public void resize(float w, float h){
        scalex = w/getRowWidth();
        scaley = h/getRowHeight();
    }

    public double getScaleX(){ return scalex; }
    public double getScaleY(){ return scaley; }

    public Image getSource(){ return image; }

    public void draw(Graphics2D g){
        if(visiable) {
            AffineTransform transform = new AffineTransform();
            //TODO flip
            Point centerp = getCenter();
            double radangle = Math.toRadians(angle);
            double len = Math.sqrt(Math.pow(getWidth()/2,2)+ Math.pow(getHeight()/2,2));
            double nx =len*Math.cos(radangle),
                    ny =len*Math.sin(radangle);

            //double dx =-nx, dy =-ny;
            double dx = -getWidth()/2, dy = -getHeight()/2;
            AffineTransform selfTransform = new AffineTransform(scalex*Math.cos(radangle),scalex*Math.sin(radangle),
                                                                -scaley*Math.sin(radangle),scaley*Math.cos(radangle),
                                                                     x+dx, y+dy);
            //System.out.println(transform);
            if(transparent != 1.0f) {
                AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparent);
                g.setComposite(alphaComposite);
            }
            g.drawImage(image, selfTransform, null);
            if(transparent != 1.0f) {
                AlphaComposite reversealpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);
                g.setComposite(reversealpha);
            }
        }
    }

    //for test
    public static void main(String[] argv){
        ImageStorage.loadImage("./resource/plant_xiaojianguoqiang_01.png");
        GTImage image = new GTImage("plant_xiaojianguoqiang_01");
        image.moveTo(100, 100);
        System.out.println(image.getCenter());
    }

}
