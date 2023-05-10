package com.activity.two.seatwork.app;

import com.activity.two.seatwork.Smartphone;
import com.activity.two.seatwork.Telephone;

public class SeatworkTwo {

    public static void main(String[] args) {
        Telephone telephone = new Telephone();
        telephone.call("09129971771");

        Smartphone smartphone = new Smartphone("09055590127", "Android");

        System.out.println("Phone Number: " + smartphone.getPhoneNo() +"\n" +
                "Operating System: " + smartphone.getOperatingSystem());
        System.out.println();

        System.out.println("Contacts: ");
        smartphone.addContact("Pers", "091222222222");
        smartphone.addContact("Jemar", "091333333");
        smartphone.addContact("Gelo", "0915555555");
        smartphone.addContact("Jorge Ezel", "09144444444");
        smartphone.addContact("Beli", "09166666666");
        smartphone.addContact("Jamber", "0917777777");
        smartphone.addContact("Sownsown", "09188888888");

        smartphone.displayContacts();
        System.out.println();

        smartphone.removeContact("Pers");
        System.out.println("Contact removed!");
        smartphone.displayContacts();
        System.out.println();

        smartphone.call("09055590127");
        System.out.println();

        smartphone.call("Jemar", "091333333");


    }
}
