package com.company;
import jdk.swing.interop.SwingInterOpUtils;
import java.util.Random;
public class Main {

    public static int t = 1;
    public static boolean stun;

    //////////////////////////////////////////////////////////////////////
    // Переменные героев и босса

    public static int bossHP = 700;
    public static int bossD = 50;
    public static int [] heroesHP = {250, 250, 250, 250, 250};
    public static int [] heroesD = {20, 20, 20, -20, 20};
    public static String [] heroesAT = {"Physical", "Magical", "Mental", "Heal", "Tor"};
    public static String bossDef = "";

    //////////////////////////////////////////////////////////////////////
    // Защита босса

    public static void changeBossDef(){
        Random r = new Random();
        int randomIndex = r.nextInt(heroesAT.length);
        while(randomIndex == 3){
            randomIndex = r.nextInt(heroesAT.length);
        }
        bossDef = heroesAT[randomIndex];
        System.out.println("______________________");
        System.out.println("Defense boss = " + bossDef);
        System.out.println("______________________");
    }

    //////////////////////////////////////////////////////////////////////
    // Игра закончена?

    public static boolean isFinished(){
        if(bossHP <= 0){
            System.out.println("______________________");
            System.out.println("     Heroes won!");
            System.out.println("______________________");
            return true;
        }
        if(heroesHP[0] <= 0 && heroesHP[1] <= 0 && heroesHP[2] <= 0) {
            System.out.println("______________________");
            System.out.println("     Boss won!");
            System.out.println("______________________");
            return true;
        }
        return false;
    }

    //////////////////////////////////////////////////////////////////////
    // Босс бьет

    public static void bossHit(){
        for (int i = 0; i < heroesHP.length; i++) {
            if(heroesHP[i] > 0) {
                heroesHP[i] = heroesHP[i] - bossD;
                if(heroesHP[i] <= 0){
                    System.out.println(heroesAT[i] + " - DIED!");
                }
            }
        }
    }

    //////////////////////////////////////////////////////////////////////
    // Герои атакуют

    public static void heroesHit(){
        for (int i = 0; i < heroesD.length; i++) {
            if(bossDef == heroesAT[i] && heroesHP[i] > 0){
                Random r = new Random();
                int coef = r.nextInt(6) + 2; // 3 4 5
                bossHP = bossHP - (heroesD[i] * coef);
                System.out.println("critical strike " + coef + "!\n" + heroesAT[i] + " atack - " + (heroesD[i] * coef) + ".");
            } else if(heroesHP[i] > 0){
                if ( i == 3){
                    for (int j = 0; j < heroesAT.length; j++) {
                        if ( j == 3){
                            continue;
                        }
                        heroesHP[j] = heroesHP[j] - heroesD[3];
                    }
                    continue;
                }
                bossHP = bossHP - heroesD[i];
            }
        }
    }

    //////////////////////////////////////////////////////////////////////
    // Статистика

    public static void printStatistics(){
        System.out.println("______________________");
        System.out.println("     Round:" + t);
        System.out.println("______________________");
        for (int i = 0; i < heroesAT.length; i++) {
            System.out.println(heroesAT[i] + " HP" + heroesHP[i] );
        }
        System.out.println("Boss HP" + bossHP);
        t = t + 1;
    }

    //////////////////////////////////////////////////////////////////////
    // Битва - раунды

    public static void main(String[] args) {
        printStatistics();
        while (!isFinished()){
            changeBossDef();
            bossHit();
            heroesHit();
            printStatistics();
        }

    }
}
