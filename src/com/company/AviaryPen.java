package com.company;

public class AviaryPen extends Pen {
    private float height;

    public AviaryPen(float height, float length, float width, String penType, String penName){
        super(length, width, penType, penName);
        this.height = height;
    }

    public float calculateAirSpace(){
        return this.height * this.calculateLandSpace();
    }

    public String assignAnimal(Animal animal){
        String message = "";

        if(!canAnimalBeAssigned(animal)){
            if(!canSharePenWithAssignedAnimals(animal)){
                return animal.getName() + " cannot share this pen with the existing animals inside.";
            }
            return animal.getName() + " is inhabitable in aviary pens";
        }

        if(calculatePenHasSpace(animal) > 0){
            addAnimalToPen(animal);
            message = animal.getName() + " has been assigned to " + getPenName();
        }else{
            message = animal.getName() + " cannot fit in this aviary pen because it is full.";
        }
        return message;
    }

    public boolean canAnimalBeAssigned(Animal animal){
        boolean isValid = false;
        if((animal instanceof AviaryAnimal)){
            isValid = true;

            if(!canSharePenWithAssignedAnimals(animal)){
                isValid = false;
            }
        }
        return isValid;
    }

    public boolean checkPenHasSpace(Animal animal){
        if(calculatePenHasSpace(animal) > 0){
            return true;
        }
        return false;
    }

    public float calculatePenHasSpace(Animal animal){
        if(!canAnimalBeAssigned(animal)){
            return 0;
        }

        AviaryAnimal castedAnimal = (AviaryAnimal) animal;
        float totalAirSpace = calculateAirSpace();
        float takenAirSpace = 0f;

        for(Animal storedAnimal : this.getStoredAnimals()){
            takenAirSpace += ((AviaryAnimal) storedAnimal).getRequiredAirSpace();
        }

        takenAirSpace += castedAnimal.getRequiredAirSpace();

        return totalAirSpace - takenAirSpace ;
    }

    public float calculatePenHasSpace(){
        float totalAirSpace = calculateAirSpace();
        float takenAirSpace = 0f;

        for(Animal storedAnimal : this.getStoredAnimals()){
            takenAirSpace += ((AviaryAnimal) storedAnimal).getRequiredAirSpace();
        }

        return totalAirSpace - takenAirSpace;
    }

    public String getPenDescription(){
        String message = "Name: " + getPenName() + " | Pen Type: " + getPenType();

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
