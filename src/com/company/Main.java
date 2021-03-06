package com.company;
import jdk.swing.interop.SwingInterOpUtils;
import java.util.Random;
public class Main {

    public static int t = 1;
    public static boolean stun = false;

    //////////////////////////////////////////////////////////////////////
    // Переменные героев и босса

    public static int bossHP = 2000;
    public static int bossD = 50;
    public static int [] heroesHP = {250, 250, 250, 250, 250, 200, 200, 350};
    public static int [] heroesD = {20, 20, 20, -20, 20, 20, 20, 10};
    public static String [] heroesAT = {"Physical", "Magical", "Mental", "Heal", "Tor", "thief", "Berserk", "Tank"};
    public static String bossDef = "";
    public static int tankDef;

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
        }else {
            int sum = 0;
            for (int i = 0; i < heroesHP.length; ++i)
                sum += heroesHP[i];
            if (sum <= 0) {
                System.out.println("______________________");
                System.out.println("     Boss won!");
                System.out.println("______________________");
                return true;
            }
        }
        return false;
    }

    //////////////////////////////////////////////////////////////////////
    // Танк защищает.

    public static void tank(int i) {
        if (heroesHP[7] > 0 && i != 7) {
            tankDef = bossD/5;
            heroesHP[7] -= tankDef;
            if (heroesHP[7] <= 0) {
                System.out.println(heroesAT[7] + " - DIED!");
            }
        }
    }

    //////////////////////////////////////////////////////////////////////
    // Босс бьет

    public static void bossHit(){
        if(!stun) {
            for (int i = 0; i < heroesHP.length; i++) {
                if (heroesHP[i] > 0) {
                    if(i == 6){
                        int bonus = bossD/5;
                        if(heroesHP[7] > 0){
                            int tankDef;
                            tankDef = (bossD - bonus)/5;
                            heroesHP[7] -= tankDef;
                            if (heroesHP[7] <= 0) {
                                System.out.println(heroesAT[7] + " - DIED!");
                            }
                        }
                        heroesHP[i] = heroesHP[i] - (bossD - (bonus + tankDef));
                        if (heroesHP[i] <= 0) {
                            System.out.println(heroesAT[i] + " - DIED!");
                            continue;
                        }
                        bossHP = bossHP - heroesD[i] + bonus;
                        continue;
                    }
                    if(i == 5){
                        Random r = new Random();
                        int ver = r.nextInt(5);
                        if( ver == 0){
                            System.out.println("Boss miss thief!");
                            continue;
                        }
                    }
                    tank(i);
                    heroesHP[i] = heroesHP[i] - (bossD - tankDef);
                    if (heroesHP[i] <= 0) {
                        System.out.println(heroesAT[i] + " - DIED!");
                        continue;
                    }
                }
            }
        } else stun = false;
    }

    //////////////////////////////////////////////////////////////////////
    // Герои атакуют

    public static void heroesHit(){
        for (int i = 0; i < heroesD.length; i++) {
            if(bossDef == heroesAT[i] && heroesHP[i] > 0){
                Random r = new Random();
                int coef = r.nextInt(6) + 2; // 3 4 5
                bossHP = bossHP - (heroesD[i] * coef);
                System.out.println("critical strike " + coef + "!\n" + heroesAT[i] + " attack - " + (heroesD[i] * coef) + ".");
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
    // Стан тора

    public static void stunTor(){
        Random r = new Random();
        int ver = r.nextInt(5);
        if(ver == 0 && heroesHP[4] > 0){
            stun = true;
            System.out.println("Tor successfully stun boss on next round!");
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
            stunTor();
            printStatistics();
        }

    }
}
