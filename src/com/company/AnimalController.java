package com.company;

import java.util.ArrayList;
import java.util.Map;

public class AnimalController extends Controller {
    private Controller controller;
    private ArrayList<Pen> activePens;
    private ArrayList<Animal> activeAnimals;
    private Map<String, ArrayList<String>> animalTypes;
    private ArrayList<Staff> activeStaff;
    private PenTypes penTypes;

    public AnimalController(Controller controller){
        this.controller = controller;
        this.activePens = controller.activePens;
        this.activeAnimals = controller.activeAnimals;
        this.activeStaff = controller.activeStaff;
        this.animalTypes = controller.animalTypes;
        this.penTypes = controller.penTypes;
    }

    public void addAnimal(){
        view.displayCustomString("Please enter details for your animal\n" +
                "Name:");
        String name = getStringInput();

        view.displayCustomString("Description:");
        String description = getStringInput();

        view.displayCustomString("Species:");
        String species = chooseAnimalType();

        ArrayList<String> possiblePenMates = createPossiblePenMates(species);
        animalTypes.put(species, possiblePenMates);

        Animal newAnimal = chooseAnimalHabitat(name,description, species);
        newAnimal.setPossiblePenMates(possiblePenMates);

        view.displayCustomString(assignAnimalToPen(newAnimal));

        activeAnimals.add(newAnimal);
    }

    public Animal chooseAnimalHabitat(String name, String description, String species){
        view.displayCustomString("Choose the habitat for your animal:\n" +
                "[1] " + penTypes.dry + "\n" +
                "[2] " + penTypes.aquarium + "\n" +
                "[3] " + penTypes.mixed + "\n" +
                "[4] " + penTypes.aviary + "\n");

        int habitatChoice = getIntInput(1, 4);
        String habitat = "";
        Animal newAnimal;


        switch(habitatChoice){
            case 1:
                habitat = penTypes.dry;
                newAnimal = addDryAnimal(name, description, species, habitat);
                break;
            case 2:
                habitat = penTypes.aquarium;
                newAnimal = addWaterAnimal(name, description, species, habitat);
                break;
            case 3:
                habitat = penTypes.mixed;
                newAnimal = addAmphibiousAnimal(name, description, species, habitat);
                break;
            case 4:
                habitat = penTypes.aviary;
                newAnimal = addAviaryAnimal(name, description, species, habitat);
                break;
            default:
                newAnimal = addDryAnimal(name, description, species, habitat);
                System.out.println("Default pen created instead because animal habitat could not be understood");;
        }
        return newAnimal;
    }

    public DryAnimal addDryAnimal(String name, String description, String species, String habitat){
        view.displayCustomString("Enter the required land space for this animal (metres squared):");
        float requirements = getFloatInput(1, 50);
        return new DryAnimal(requirements, name, description, species, habitat);
    }

    public WaterAnimal addWaterAnimal(String name, String description, String species, String habitat){
        view.displayCustomString("Enter the required water space for this animal (metres cubed):");
        float requiredWaterSpace = getFloatInput(1, 50);
        return new WaterAnimal(requiredWaterSpace, name, description, species, habitat);
    }

    public AmphibiousAnimal addAmphibiousAnimal(String name, String description, String species, String habitat){
        view.displayCustomString("Enter the required land space for this animal (metres squared):");
        float landRequirements = getFloatInput(1, 50);

        view.displayCustomString("Enter the required water space for this animal (metres cubed):");
        float waterRequirements = getFloatInput(1, 50);
        return new AmphibiousAnimal(waterRequirements, landRequirements, name, description, species, habitat);
    }

    public AviaryAnimal addAviaryAnimal(String name, String description, String species, String habitat){
        view.displayCustomString("Enter the required Land space for this animal (metres):");
        float landRequirements = getFloatInput(1, 50);

        view.displayCustomString("Enter the required air space for this animal (metres):");
        float heightRequirements = getFloatInput(1, 50);
        return new AviaryAnimal(heightRequirements, landRequirements, name, description, species, habitat);
    }

    public void assignAnimalToPenMenu(){
        controller.getAllAnimalDetails();
        if(activeAnimals.size() > 0){
            view.displayCustomString("Please choose an animal number in order to assign them to a pen:");

            int userInputAnimal = getIntInput(1, activeAnimals.size());

            Animal chosenAnimal = activeAnimals.get(userInputAnimal - 1);

            assignAnimalToPen(chosenAnimal);
        }
    }

    public String assignAnimalToPen(Animal animal){
        boolean exit = false;
        String message = "";

        while(!exit){
            if(activePens.size() > 0){
                view.displayCustomString("Do you want to assign this animal to a pen?\n" +
                        "[1] Yes\n" +
                        "[2] No \n");
                int userInput = getIntInput(1,2);

                if(userInput == 1){
                    controller.getAllPenDetails();
                    view.displayCustomString("Choose the pen index number you want your animal to be assigned to:");
                    int penIndex = getIntInput(0 + 1, activePens.size());
                    Pen penToAssignTo = activePens.get(penIndex - 1);
                    view.displayCustomString(penToAssignTo.assignAnimal(animal));
                    if(animal.checkIsInsidePen()){
                       exit = true;
                    }
                }else if(userInput == 2){
                    message = animal.getName() + " has been added to the Zoo!";
                    exit = true;
                }
            }else{
                message = animal.getName() + " has been added to the Zoo!";
                exit = true;
            }
        }
        return message;
    }

    public String chooseAnimalType(){
        int chosenOption = 0;
        int option = 0;
        ArrayList<String> keys = new ArrayList<String>();
        String chosenType = "";
        if(animalTypes.size() > 0){
            view.displayCustomString("Choose the type of your animal");
            for(String key : animalTypes.keySet()){
                option++;
                keys.add(key);
                view.displayCustomString("[" + option + "] " + key);
            }


            view.displayCustomString("[" + (animalTypes.size() + 1) + "] New Type");

            chosenOption = getIntInput(1, animalTypes.size() + 1);

            if(chosenOption == animalTypes.size() + 1){
                chosenType = createNewAnimalType();
            }else{
                chosenType = getAnimalType(keys, chosenOption - 1);
            }
        } else{
            chosenType = createNewAnimalType();
        }

        return chosenType;
    }

    public String getAnimalType(ArrayList<String> keys, int chosenOption){
        String chosenAnimalType = "";
        boolean isValid = false;

        while(!isValid) {
            try {
                chosenAnimalType = keys.get(chosenOption);
                isValid = true;
            } catch (Exception e) {
                view.displayCustomString("Failed getting that option, please try again");
                keyboard.next();
            }
        }
        return chosenAnimalType;
    }

    public String createNewAnimalType(){
        view.displayCustomString("Please enter the new type of animal:");
        String animalType = getStringInput();


        return animalType;
    }

    public ArrayList<String> createPossiblePenMates(String currentType){
        int chosenOption = 0;
        ArrayList<String> keys = new ArrayList<String>();
        String chosenKey = "";
        int option = 1;
        ArrayList<String> chosenTypes = new ArrayList<String>();
        chosenTypes.add(currentType);
        boolean exit = false;

        while(!exit){
            if(animalTypes.size() > 0){
                view.displayCustomString("Please choose the possible pen mates for this animal type");
                for(String key : animalTypes.keySet()){
                        if(currentType != key){
                            keys.add(key);
                            if(!chosenTypes.contains(key)){
                                view.displayCustomString("[" + option + "] " + key);
                            }else{
                                view.displayCustomString("[" + option + "] " + key + "(Already Assigned)");
                            }

                            option++;
                        }
                }
                view.displayCustomString("[0] Finish");
                chosenOption = getIntInput(0, animalTypes.size());
                if(chosenOption == 0){
                    exit = true;
                }else{
                    chosenKey = keys.get(chosenOption - 1);
                    if(!chosenTypes.contains(chosenKey)) {
                        chosenTypes.add(chosenKey);
                    }else{
                        chosenTypes.remove(chosenKey);
                    }
                }
            }else{
                exit = true;
            }
            option = 1;
        }

        if(!chosenTypes.isEmpty()){
            updatePenMates(chosenTypes, currentType);
        }
        return chosenTypes;
    }

    public void updatePenMates(ArrayList<String> chosenSpecies, String newPenMate){
        for (int i = 0; i < chosenSpecies.size(); i++) {
            for (int j = 0; j < activeAnimals.size(); j++) {
                Animal currentAnimal = activeAnimals.get(j);

                if(chosenSpecies.contains(currentAnimal.getSpecies())){
                    currentAnimal.addPossiblePenMate(newPenMate);
                }
            }
        }
    }

    public String checkForUnassignedAnimals(){
        String unassignedAnimals = "";
        if(activeAnimals.size() <= 0){
            return "No animals currently exist in the zoo";
        }else{
            for(Animal animal : activeAnimals){
                if(!animal.checkIsInsidePen()){
                    unassignedAnimals += animal.getName() + ", ";
                }
            }
        }

        if(unassignedAnimals.length() > 0){
            return "These animals are not assigned to a pen: " + unassignedAnimals;
        }
        return "All animals have been assigned to a pen";
    }

    public void autoAllocateAnimals(){
        if(!activeAnimals.isEmpty() || !activePens.isEmpty()){
            for (int i = 0; i < activeAnimals.size(); i++) {
                Animal currentAnimal = activeAnimals.get(i);
                if(currentAnimal.checkIsInsidePen()){
                    break;
                }

                String habitat = currentAnimal.getHabitat();
                for (int j = 0; j < activePens.size(); j++) {
                    Pen currentPen = activePens.get(j);
                    if(currentPen.getPenType().equals(habitat)){
                        if(currentPen.checkPenHasSpace(currentAnimal)){
                            currentPen.assignAnimal(currentAnimal);
                            break;
                        }
                    }
                }
            }
        }
        view.displayCustomString(checkForUnassignedAnimals());
    }
}
