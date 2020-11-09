package com.mycompany.booking;

import java.util.Scanner;

public class MainMenu {

    private UserInputs inputs = new UserInputs();

    Scanner input = new Scanner(System.in);

    public MainMenu() {

        System.out.println("*** Menu ***");
        System.out.println("");
        System.out.println("Εισαγωγή Όρου Αναζήτησης: 1");
        System.out.println("Στατιστική ανάλυση: 2");

        int type = input.nextInt();
        switch (type) {
            case 1:
                inputs = inputUserData();
                break;
            case 2:
                System.out.println("Sophia");
                break;

        }

    }

    //---------------------------------------------------------------------------------------------------------------
    public UserInputs inputUserData() {

        System.out.println("Booking Hotels In Greece !!!");
        System.out.println("Destination: ");
        String destination = input.next();
        System.out.println("Check-In: ");
        String checkIn = input.next();
        System.out.println("Check-Out: ");
        String checkOut = input.next();

        UserInputs userInputs = new UserInputs();

        userInputs.setDestination(destination);
        userInputs.setCheckIn(checkIn);
        userInputs.setCheckOut(checkOut);

        System.out.println("*** Search for Hotels ***");
        return (userInputs);
    }

    public UserInputs getInputs() {
        return inputs;
    }

    public void setInputs(UserInputs inputs) {
        this.inputs = inputs;
    }

}
