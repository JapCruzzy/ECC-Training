package com.activity.one.seatwork;

import com.activity.one.seatwork.util.ClassC;

import java.util.Arrays;

public class MainClass {

    public static void main(String[] args) {
        System.out.println(showClassA());
        System.out.println(showClassB());
        System.out.println(result());

        if(showClassA()){
            System.out.println("True");
        } else {
            System.out.println("False");
        }
    }
    private static Boolean showClassA(){
        ClassA a = new ClassA();
        return a.bool;
    }
    private static String showClassB(){
        ClassB b = new ClassB();
        return Arrays.stream(b.days).toList().toString();
    }
    private static String result(){
        return ClassC.pass(1).toString();
    }
}
