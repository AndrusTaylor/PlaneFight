package com.gametemplate.Basic;
import com.gametemplate.Image.GTImage;

import java.awt.*;
import java.util.*;

public class ImageStorage {
    public static Map<String, Image> images = new HashMap<>();
    public static void loadImage(String path){
        String lowcase = path;  //to convert lowcase? It may cause some problem when get image by name...
        int lastsplash = lowcase.lastIndexOf("/");
        String substr = lowcase.substring(lastsplash+1);
        String filename = substr.substring(0, substr.lastIndexOf("."));
        //System.out.println(filename+"\nfilepath:" + path);
        Image image = Toolkit.getDefaultToolkit().getImage(path);
        if(image == null)
            System.out.println("[ImageStorage]: no image " + path);
        else
            images.put(filename, image);
    }

    public static void loadImage(String[] files){
        for(int i=0;i<files.length;i++)
            loadImage(files[i]);
    }

    public static Image getImage(String key){
        return images.get(key);
    }

    public static GTImage getGTImage(String key){
        return new GTImage(key);
    }

    public static void cleanImage(){
        for(int i=0;i<images.size();i++)
            images.clear();
    }

    public static boolean hasImage(String key){
        return images.containsKey(key);
    }
}
