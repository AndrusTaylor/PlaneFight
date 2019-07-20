package com.gametemplate.Shape;

import java.awt.*;

public class Text extends com.gametemplate.Shape.Shape{
    private Font font;
    private StringBuffer str;

    public Text(String text, int x, int y, Font font){
        this.x = x;
        this.y = y;
        this.font = font;
        str = new StringBuffer();
        str.append(text);
    }

    public Text(String text, int x, int y, int size, int type){
        this.x = x;
        this.y = y;
        str = new StringBuffer();
        str.append(text);
        font = new Font("宋体", type, size);
    }

    public void setText(String text){
        str.delete(0, str.length());
        str.append(text);
    }

    public String getText(){ return str.toString(); }

    public StringBuffer getBuffer(){ return str; }

    @Override
    public Point getCenter(){ return new Point(x, y); }

    @Override
    public void draw(Graphics2D g){
        if(visiable) {
            if (font != null)
                g.setFont(font);
            g.setColor(color);
            g.drawString(str.toString(), x, y);
        }
    }
}
