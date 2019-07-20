package com.gametemplate.Basic;

import java.util.HashMap;

public class Director {
    private Window window = null;
    private Canva canva = null;
    private static HashMap<String, Stage> stageList = new HashMap<>();
    private static Stage currentStage = null;
    private static Director director = null;
    public static boolean debugMode = false;

    private Director(Window nwindow, Canva ncanva){
        this.window = nwindow;
        this.canva = ncanva;
    }

    public static Stage getCurrentStage(){
        return currentStage;
    }

    public static void addStage(String name, Stage stage){
        stageList.put(name, stage);
    }

    public static void removeStage(String name){
        stageList.remove(name);
    }

    public static void initDirector(Window nwindow, Canva ncanva) {
        if(director == null) {
            director = new Director(nwindow, ncanva);
        }
    }

    public static void changeStage(String name){
        if(stageList.containsKey(name)){
            if(currentStage != null)
                currentStage.quit();
            currentStage = stageList.get(name);
            currentStage.init();
        }
    }

    public static Director getInstance(){
        return director;
    }

    public Window getWindow(){
        return window;
    }

    public Canva getCanva(){
        return canva;
    }
}