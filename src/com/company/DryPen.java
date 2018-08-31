package com.company;


public class DryPen extends Pen{
    private boolean isPettingPen;

    public DryPen(boolean isPettingPen, float length, float width, String penType, String penName){
        super(length, width, penType, penName);
        this.isPettingPen = isPettingPen;
    }

    public String assignAnimal(Animal animal){
        String message = "";

        if(!canAnimalBeAssigned(animal)){
            if(!canSharePenWithAssignedAnimals(animal)){
                return animal.getName() + " cannot share this pen with the existing animals inside.";
            }
            return animal.getName() + " is inhabitable in dry pens";
        }

        if(checkPenHasSpace(animal)){
            addAnimalToPen(animal);
            message = animal.getName() + " has been assigned to " + getPenName();
        }else{
            message = animal.getName() + " cannot fit in this dry pen because it is full.";
        }
        return message;
    }

    public boolean canAnimalBeAssigned(Animal animal){
        boolean isValid = false;
        if((animal instanceof DryAnimal)){
            isValid = true;

            if(!canSharePenWithAssignedAnimals(animal)){
                isValid = false;
            }
        }
        return isValid;
    }

    public float calculatePenHasSpace(Animal animal){
        if(!canAnimalBeAssigned(animal)){
            return 0;
        }

        DryAnimal castedAnimal = (DryAnimal) animal;
        float totalSpace = this.calculateLandSpace();
        float takenLandSpace = 0f;

        for(Animal storedAnimal : this.getStoredAnimals()){
            takenLandSpace += ((DryAnimal) storedAnimal).getRequiredLandSpace();
        }

        takenLandSpace += castedAnimal.getRequiredLandSpace();

        return totalSpace - takenLandSpace;
    }

    public float calculatePenHasSpace(){
        float totalSpace = this.calculateLandSpace();
        float takenLandSpace = 0f;

        for(Animal storedAnimal : this.getStoredAnimals()){
            takenLandSpace += ((DryAnimal) storedAnimal).getRequiredLandSpace();
        }

        return totalSpace - takenLandSpace;
    }

    public boolean checkPenHasSpace(Animal animal){
        if(calculatePenHasSpace(animal) > 0){
            return true;
        }
        return false;
    }

    public String getPenDescription(){
        String penType = getPenType();
        if(isPettingPen){
            penType = "Petting Pen";
        }
        String message = "Name: " + getPenName() + " | Pen Type: " + penType;

        message += getAnimalsDescription();

        if(checkPenHasStaff()){
            message += " | Staff: " + getStaffName();
        }else{
            message += " | Staff: Unassigned";
        }

        if(calculatePenHasSpace() > 0){
            message += " | Total Land: " + calculateLandSpace() + "(metres squared) | Remaining Space: " + calculatePenHasSpace() + " (metres squared)";
        }else{
            message += " | Total Land: " + calculateLandSpace() + "(metres squared) | Remaining Space: None";
        }
        return message;
    }
}
