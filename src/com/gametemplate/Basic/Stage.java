package com.gametemplate.Basic;

import com.gametemplate.Drawable;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;

public abstract class Stage {
    //To render objects
    protected ArrayList<Drawable> renderList;
    //To render effects , always draw on the renderList
    protected ArrayList<Drawable> effectList;
    //To show Components of java.swing, always show on the top
    protected ArrayList<Component> uiList;
    protected String nextStage;

    public ArrayList<Drawable> getRenderList(){return renderList;}
    public ArrayList<Drawable> getEffectList(){return effectList;}
    public ArrayList<Component> getUIList(){return uiList;}

    public Stage(){
    }

    public void setNextStage(String ns){
        nextStage = ns;
    }

    //usr will rewrite it to init the stage
    public void init(){
        renderList = new ArrayList<>();
        effectList = new ArrayList<>();
        uiList = new ArrayList<>();
    };

    public void quit(){
        renderList.clear();
        effectList.clear();
        for(int i=0;i<uiList.size();i++)
            uiList.get(i).setVisible(false);
        /*
        renderList = null;
        effectList = null;
        uiList = null;
        */
    }

    public void addToRender(Drawable image){
        renderList.add(image);
    }

    public void clearRenderList(){
        renderList.clear();
    }

    public void addToUI(Component comp){
        uiList.add(comp);
        Director.getInstance().getCanva().add(comp);
    }

    public void clearUIList(){
        uiList.clear();
    }

    public void addToEffect(Drawable image){ effectList.add(image); }

    public void clearEffectList(){ effectList.clear(); }

    public abstract void KeyPressed(KeyEvent e);
    public abstract void KeyReleased(KeyEvent e);

    public void update(Graphics2D g){
        for(int i=0;i<renderList.size();i++)
            renderList.get(i).draw(g);
        for(int j=0;j<effectList.size();j++)
            effectList.get(j).draw(g);
    }
}
