package Parser;

import Objects.*;
import com.gametemplate.Basic.Director;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

class ParamPackage{
    public ParamPackage(Long t, boolean s){
        time = t;
        enableShoot = s;
    }
    public Long time;
    public boolean enableShoot;
}

public class StageParser{
    private HashMap<Enemy, ParamPackage> enemies = new HashMap<>();
    private HashMap<Supply,  ParamPackage> supplies = new HashMap<>();
    private HashMap<BGImage, ParamPackage> images = new HashMap<>();
    private static int GAME_SPEED = 5;
    private String filename;

    public StageParser(String filename) throws Exception{
        this.filename = filename;
    }

    public void parse() throws Exception{
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
            if(timecount-timestep<=enemies.get(e).time && timecount>=enemies.get(e).time) {
                removeObj.add(e);
                if(enemies.get(e).enableShoot)
                    e.shoot();
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
            if(timecount-timestep<=supplies.get(s).time && timecount>=supplies.get(s).time) {
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
            if(timecount-timestep<=images.get(m).time && timecount>=images.get(m).time){
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
        boolean enableshoot = scaner.nextBoolean();
        if(Director.debugMode) {
            System.out.println("********************************");
            System.out.printf("Linear plane:\ntime:%d\nhp:%d nx:%d ny:%d\nscore:%d\nimg:%s\ndieFrame:%s\ndir:%s\nisShoot:%s\n", time, hp, nx, ny, score, img, str, movedir, enableshoot);
        }
        SimplePlane enemy = new SimplePlane(hp, nx, ny, score, img, str, Enemy_Logic_Type.LINEAR);
        enemy.setLinearDir(movedir);
        if(enableshoot)
           enemy.enableShoot();
        ParamPackage p = new ParamPackage(time, enableshoot);
        enemies.put(enemy, p);
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
        boolean enableshoot = scaner.nextBoolean();
        if(Director.debugMode) {
            System.out.println("********************************");
            System.out.printf("Sin plane:\ntime:%d\nhp:%d nx:%d ny:%d\nscore:%d\nimg:%s\ndieFrame:%s\na:%d b:%d\n", time, hp, nx, ny, score, img, str, a, b);
        }
        SimplePlane enemy = new SimplePlane(hp, nx, ny, score, img, str, Enemy_Logic_Type.SIN);
        enemy.setSinAB(a, b);
        ParamPackage p = new ParamPackage(time, enableshoot);
        enemies.put(enemy, p);
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
        boolean enableshoot = scaner.nextBoolean();
        if(Director.debugMode) {
            System.out.println("********************************");
            System.out.printf("Behind plane:\ntime:%d\nhp:%d nx:%d\nscore:%d\nimg:%s\ndieFrame:%s\nvel_y:%d\n", time, hp, nx, score, img, str, vel_y);
        }
        SimplePlane enemy = new SimplePlane(hp, nx, 0, score, img, str, Enemy_Logic_Type.BEHIND);
        enemy.setBehindVelY(vel_y);
        ParamPackage p = new ParamPackage(time, enableshoot);
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
        boolean enableshoot = scaner.nextBoolean();
        if(Director.debugMode) {
            System.out.println("********************************");
            System.out.printf("Cross plane:\ntime:%d\nhp:%d nx:%d ny:%d\nscore:%d\nimg:%s\ndieFrame:%s\nch_h:%d ch_l:%d ch_r:%d\n", time, hp, nx, ny, score, img, str, ch_h, ch_l, ch_r);
        }
        SimplePlane enemy = new SimplePlane(hp, nx, ny, score, img, str, Enemy_Logic_Type.CROSS);
        enemy.setCrossParam(ch_h, ch_l, ch_r);
        ParamPackage p = new ParamPackage(time, enableshoot);
        enemies.put(enemy, p);
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
        ParamPackage p = new ParamPackage(time, false);
        supplies.put(bomb, p);
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
        ParamPackage p = new ParamPackage(time, false);
        supplies.put(lazer, p);
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
        ParamPackage p = new ParamPackage(time, false);
        images.put(image, p);
    }

    public static void main(String[] args){
        try {
            StageParser parser = new StageParser("./resource/stageInfo/stage1.txt");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
