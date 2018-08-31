package com.company;

public class View {
    private PenTypes penTypes;

    public View(){
        penTypes = new PenTypes();
    }

    public void displayTitle(){
        System.out.println("\n" +
                " ________  ________  ________     \n" +
                "|\\_____  \\|\\   __  \\|\\   __  \\    \n" +
                " \\|___/  /\\ \\  \\|\\  \\ \\  \\|\\  \\   \n" +
                "     /  / /\\ \\  \\\\\\  \\ \\  \\\\\\  \\  \n" +
                "    /  /_/__\\ \\  \\\\\\  \\ \\  \\\\\\  \\ \n" +
                "   |\\________\\ \\_______\\ \\_______\\\n" +
                "    \\|_______|\\|_______|\\|_______|\n" +
                "                                  \n" +
                "                                  \n" +
                "                                  \n");
    }

    public void displayMainMenu(){
        System.out.println("Choose an opttion");
        System.out.println("[0] Refresh weather details");
        System.out.println("[1] List all animals");
        System.out.println("[2] List all pens");
        System.out.println("[3] List all staff");
        System.out.println("[4] Add Pen");
        System.out.println("[5] Add Animal");
        System.out.println("[6] Add Staff");
        System.out.println("[7] Assign Animal to Pen");
        System.out.println("[8] Assign Staff to Pen");
        System.out.println("[9] Save or Load File");
        System.out.println("[10] Auto Allocate Animals");
        System.out.println("[11] Auto Allocate Staff");

    }

    public void displayCustomString(String customString){
        System.out.println(customString);
    }

    public void choosePenTypeMenu(){
        System.out.println("Choose the pen type: \n" +
                "[1] " + penTypes.dry + "\n" +
                "[2] " + penTypes.aquarium + "\n" +
                "[3] " + penTypes.mixed + "\n" +
                "[4] " + penTypes.aviary + "\n" +
                "[5] " + penTypes.pettingPen);;
    }

    public void continueMenu(){
        System.out.println("Type anything to continue");
    }

    public void displayFileStorageMenu() {
        System.out.println("Choose an opttion");
        System.out.println("[1] Save file");
        System.out.println("[2] Load File");
    }
}
