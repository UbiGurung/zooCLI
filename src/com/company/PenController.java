package com.company;


import java.util.ArrayList;
import java.util.Map;

public class PenController extends Controller {
    private Controller controller;
    private ArrayList<Pen> activePens;
    private ArrayList<Animal> activeAnimals;
    private Map<String, ArrayList<String>> animalTypes;
    private ArrayList<Staff> activeStaff;
    private PenTypes penTypes;

    public PenController(Controller controller){
        this.controller = controller;
        this.activePens = controller.activePens;
        this.activeAnimals = controller.activeAnimals;
        this.activeStaff = controller.activeStaff;
        this.animalTypes = controller.animalTypes;
        this.penTypes = controller.penTypes;
    }

    public void addPen(){

        view.displayCustomString("Please fill in the following details for the pen\n"
                + "Length (metres):");
        float length = getFloatInput(1, 50);

        view.displayCustomString("Width (metres):");
        float width = getFloatInput(1, 50);

        String penName = addPenName();

        Pen penToAdd = addSpecificPen(length, width, penName);

        activePens.add(penToAdd);
    }

    public DryPen addDryPen(float length, float width, String penType, String penName){
        return new DryPen(false, length, width, penType, penName);
    }

    public AquariumPen addAquariumPen(float length, float width, String penType, String penName){
        view.displayCustomString("Enter the depth of the water (metres):");
        float depth = getFloatInput(1, 50);
        return new AquariumPen(depth, length, width, penType, penName);
    }

    public AmphibiousPen addMixedPen(float length, float width, String penType, String penName){
        view.displayCustomString("Enter water length (metres):");
        float waterLength = getFloatInput(1, 50);

        view.displayCustomString("Enter water width (metres):");
        float waterWidth = getFloatInput(1, 50);

        view.displayCustomString("Enter water depth (metres):");
        float waterDepth = getFloatInput(1, 50);

        return new AmphibiousPen(waterLength, waterWidth, waterDepth, length, width, penType, penName);
    }

    public AviaryPen addAviaryPen(float length, float width, String penType, String penName){
        view.displayCustomString("Enter height (metres):");
        float height = getFloatInput(1, 50);

        return new AviaryPen(height, length, width, penType, penName);
    }

    public DryPen addPettingPen(float length, float width, String penType, String penName){
        return new DryPen(true, length, width, penType, penName);
    }

    public Pen addSpecificPen(float length, float width, String penName){
        view.choosePenTypeMenu();
        int userInput = getIntInput(1,5);
        String penType = "";
        Pen penToAdd;

        switch (userInput){
            case 1:
                penType = penTypes.dry;
                penToAdd = addDryPen(length, width, penType, penName);
                break;
            case 2:
                penType = penTypes.aquarium;
                penToAdd = addAquariumPen(length, width, penType, penName);
                break;
            case 3:
                penType = penTypes.mixed;
                penToAdd = addMixedPen(length, width, penType, penName);
                break;
            case 4:
                penType = penTypes.aviary;
                penToAdd = addAviaryPen(length, width, penType, penName);
                break;
            case 5:
                penType = penTypes.pettingPen;
                penToAdd = addPettingPen(length, width, penType, penName);
                break;

            default:
                penToAdd = new DryPen(false, length, width, penType, penName);
                System.out.println("Default pen created instead because pen type could not be understood");
        }

        view.displayCustomString(penName + " has been added to the Zoo!");
        return penToAdd;
    }

    public String addPenName(){
        boolean isValid = false;
        String penName = "";

        while(!isValid){
            view.displayCustomString("Please enter name for your pen:");
            penName = getStringInput();
            if(!checkPenNameExists(penName)){
                isValid = true;
            }else{
                view.displayCustomString("That name already exists for a pen, please choose another name");
            }
        }
        return penName;
    }

    public boolean checkPenNameExists(String name){
        for (int i = 0; i < activePens.size(); i++) {
            Pen currentPen = activePens.get(i);
            String penName = currentPen.getPenName().toLowerCase();
            if(penName.equals(name.toLowerCase())){
                return true;
            }
        }
        return false;
    }
}
