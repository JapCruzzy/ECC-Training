package com.activity.two.seatwork;

import java.util.HashMap;
import java.util.Map;

public class Smartphone extends Telephone{
    String phoneNo;
    String operatingSystem;
    Map<String, String> contacts = new HashMap<>();

    public Smartphone(String phoneNo, String operatingSystem) {
        this.phoneNo = phoneNo;
        this.operatingSystem = operatingSystem;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public Map<String, String> getContacts() {
        return contacts;
    }

    public void setContacts(Map<String, String> contacts) {
        this.contacts = contacts;
    }

    public void addContact(String name, String phoneNo){
        contacts.put(name, phoneNo);
    }

    public void removeContact(String name){
        contacts.remove(name);
    }

    @Override
    public void call(String phoneNo) {
        super.call(phoneNo);
        if (phoneNo.equals(getPhoneNo())) {
            System.out.println("You cannot call yourself");
        }
    }

    public void call(String name, String phoneNo){
        if(contacts.containsKey(name)){
            System.out.println("Calling from contacts");
            call(phoneNo);
        }
    }

    public void displayContacts(){
        contacts.entrySet().stream().forEach(e -> {
            System.out.println(e.getKey() + " - " + e.getValue());
        });
    }
}
