package com.company;
import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
	final String hello = "Привет,";
	String world = " мир!";
	String helloWorld = hello + world;
	System.out.print("Добро пожаловать!\nВведите свое имя: ");
	Scanner in = new Scanner(System.in);
	final String name = in.next();
	System.out.println("Здраствуйте, " + name + ".\nРада с вами познакомится.");
	System.out.println(helloWorld);
	System.out.println("final string hello - " + hello);
    }
}
