package com.activity.two.seatwork;

public class Telephone implements Phone{
    @Override
    public void call(String phoneNo) {
        System.out.println("Calling: " + phoneNo);
    }
}
