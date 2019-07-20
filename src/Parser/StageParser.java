package Parser;

import Objects.*;
import com.gametemplate.Basic.Director;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class StageParser{
    private HashMap<Enemy, Long> enemies = new HashMap<>();
    private HashMap<Supply, Long> supplies = new HashMap<>();
    private HashMap<BGImage, Long> images = new HashMap<>();
    private static int GAME_SPEED = 5;

    public StageParser(String filename) throws Exception{
        File file = new File(filename);
        if(!file.exists())
            System.out.println("[parser]:stage file "+filename+" not found");
        FileInputStream inputstream = new FileInputStream(file);
        Scanner scaner = new Scanner(inputstream);
        while(scaner.hasNext()){
            String head = scaner.next();
            if(head.equals("LINEAR"))
                parseLinear(scaner);
            if(head.equals("SIN"))
                parseSin(scaner);
            if(head.equals("BEHIND"))
                parseBehind(scaner);
            if(head.equals("CROSS"))
                parseCross(scaner);
            if(head.equals("BOMB"))
                parseBomb(scaner);
            if(head.equals("LAZER"))
                parseLazer(scaner);
            if(head.equals("IMAGE"))
                parseImage(scaner);
        }
    }

    public void generateMap(long timecount, int timestep){
        Set ekeySet = enemies.keySet();
        Iterator<Enemy> eit = ekeySet.iterator();
        ArrayList<Enemy> removeObj = new ArrayList<>();
        while(eit.hasNext()){
            Enemy e = eit.next();
            if(timecount-timestep<=enemies.get(e) && timecount>=enemies.get(e)) {
                removeObj.add(e);
                SimplePlane.registerInstance(e);
            }
        }
        //for concurrency safty
        for(int i=0;i<removeObj.size();i++)
            enemies.remove(removeObj.get(i));

        Set skeySet = supplies.keySet();
        ArrayList<Supply> removesup = new ArrayList<>();
        Iterator<Supply> sit = skeySet.iterator();
        while(sit.hasNext()){
            Supply s = sit.next();
            if(timecount-timestep<=supplies.get(s) && timecount>=supplies.get(s)) {
                removesup.add(s);
                Supply.registerInstance(s);
            }
        }
        //for concurrency safty
        for(int i=0;i<removesup.size();i++)
            supplies.remove(removesup.get(i));

        Set ikeySet = images.keySet();
        ArrayList<BGImage> removeImage = new ArrayList<>();
        Iterator<BGImage> iit = ikeySet.iterator();
        while(iit.hasNext()){
            BGImage m = iit.next();
            if(timecount-timestep<=images.get(m) && timecount>=images.get(m)){
                removeImage.add(m);
                BGImage.registerInstance(m);
            }
        }
        //for concurrency safty
        for(int i=0;i<removeImage.size();i++)
            images.remove(removeImage.get(i));
    }

    private void parseLinear(Scanner scaner){
        Long time = scaner.nextLong();
        int hp = scaner.nextInt();
        int nx = scaner.nextInt();
        int ny = scaner.nextInt();
        int score = scaner.nextInt();
        String img = scaner.next();
        int dieFrameCount = scaner.nextInt();
        String[] str = new String[dieFrameCount];
        for(int i=0;i<dieFrameCount;i++)
            str[i] = scaner.next();
        Point movedir = new Point(scaner.nextInt(), scaner.nextInt());
        if(Director.debugMode) {
            System.out.println("********************************");
            System.out.printf("Linear plane:\ntime:%d\nhp:%d nx:%d ny:%d\nscore:%d\nimg:%s\ndieFrame:%s\ndir:%s\n", time, hp, nx, ny, score, img, str, movedir);
        }
        SimplePlane enemy = new SimplePlane(hp, nx, ny, score, img, str, Enemy_Logic_Type.LINEAR);
        enemy.setLinearDir(movedir);
        enemies.put(enemy, time);
    }

    private void parseSin(Scanner scaner){
        Long time = scaner.nextLong();
        int hp = scaner.nextInt();
        int nx = scaner.nextInt();
        int ny = scaner.nextInt();
        int score = scaner.nextInt();
        String img = scaner.next();
        int dieFrameCount = scaner.nextInt();
        String[] str = new String[dieFrameCount];
        for(int i=0;i<dieFrameCount;i++)
            str[i] = scaner.next();
        int a = scaner.nextInt();
        int b = scaner.nextInt();
        if(Director.debugMode) {
            System.out.println("********************************");
            System.out.printf("Sin plane:\ntime:%d\nhp:%d nx:%d ny:%d\nscore:%d\nimg:%s\ndieFrame:%s\na:%d b:%d\n", time, hp, nx, ny, score, img, str, a, b);
        }
        SimplePlane enemy = new SimplePlane(hp, nx, ny, score, img, str, Enemy_Logic_Type.SIN);
        enemy.setSinAB(a, b);
        enemies.put(enemy, time);
    }

    private void parseBehind(Scanner scaner){
        Long time = scaner.nextLong();
        int hp = scaner.nextInt();
        int nx = scaner.nextInt();
        int score = scaner.nextInt();
        String img = scaner.next();
        int dieFrameCount = scaner.nextInt();
        String[] str = new String[dieFrameCount];
        for(int i=0;i<dieFrameCount;i++)
            str[i] = scaner.next();
        int vel_y = scaner.nextInt();
        if(Director.debugMode) {
            System.out.println("********************************");
            System.out.printf("Behind plane:\ntime:%d\nhp:%d nx:%d\nscore:%d\nimg:%s\ndieFrame:%s\nvel_y:%d\n", time, hp, nx, score, img, str, vel_y);
        }
        SimplePlane enemy = new SimplePlane(hp, nx, 0, score, img, str, Enemy_Logic_Type.BEHIND);
        enemy.setBehindVelY(vel_y);
        enemies.put(enemy, time);
    }

    private void parseCross(Scanner scaner){
        Long time = scaner.nextLong();
        int hp = scaner.nextInt();
        int nx = scaner.nextInt();
        int ny = scaner.nextInt();
        int score = scaner.nextInt();
        String img = scaner.next();
        int dieFrameCount = scaner.nextInt();
        String[] str = new String[dieFrameCount];
        for(int i=0;i<dieFrameCount;i++)
            str[i] = scaner.next();
        int ch_h = scaner.nextInt();
        int ch_l = scaner.nextInt();
        int ch_r = scaner.nextInt();
        if(Director.debugMode) {
            System.out.println("********************************");
            System.out.printf("Cross plane:\ntime:%d\nhp:%d nx:%d ny:%d\nscore:%d\nimg:%s\ndieFrame:%s\nch_h:%d ch_l:%d ch_r:%d\n", time, hp, nx, ny, score, img, str, ch_h, ch_l, ch_r);
        }
        SimplePlane enemy = new SimplePlane(hp, nx, ny, score, img, str, Enemy_Logic_Type.CROSS);
        enemy.setCrossParam(ch_h, ch_l, ch_r);
        enemies.put(enemy, time);
    }

    private void parseBomb(Scanner scaner){
        Long time = scaner.nextLong();
        String filename = scaner.next();
        int nx = scaner.nextInt();
        int ny = scaner.nextInt();
        if(Director.debugMode) {
            System.out.println("********************************");
            System.out.printf("Bomb:\ntime:%d\nimage:%s x:%d y:%d\n", time, filename, nx, ny);
        }
        Supply bomb = new Supply(filename, nx, ny, SupplyType.BOMB);
        supplies.put(bomb, time);
    }

    private void parseLazer(Scanner scaner){
        Long time = scaner.nextLong();
        String filename = scaner.next();
        int nx = scaner.nextInt();
        int ny = scaner.nextInt();
        if(Director.debugMode) {
            System.out.println("********************************");
            System.out.printf("Lazer:\ntime:%d\nimage:%s x:%d y:%d\n", time, filename, nx, ny);
        }
        Supply lazer = new Supply(filename, nx, ny, SupplyType.LAZER);
        supplies.put(lazer, time);
    }

    private void parseImage(Scanner scaner){
        Long time = scaner.nextLong();
        String filename = scaner.next();
        int x = scaner.nextInt();
        int y = scaner.nextInt();
        if(Director.debugMode){
            System.out.println("********************************");
            System.out.printf("Image:\ntime:%d\nimage:%s x:%d y:%d\n", time, filename, x, y);
        }
        BGImage image = new BGImage(filename, x, y, GAME_SPEED);
        images.put(image, time);
    }

    public static void main(String[] args){
        try {
            StageParser parser = new StageParser("./resource/stageInfo/stage1.txt");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
