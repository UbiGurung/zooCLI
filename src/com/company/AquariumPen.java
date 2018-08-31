package com.company;

public class AquariumPen extends Pen {
    private float depth;

    public AquariumPen(float depth, float length, float width, String penType, String penName){
        super(length, width, penType, penName);
        this.depth = depth;
    }

    public float calculateWaterVolume(){
        return this.depth * this.calculateLandSpace();
    }

    public String assignAnimal(Animal animal){
        String message = "";

        if(!canAnimalBeAssigned(animal)){
            if(!canSharePenWithAssignedAnimals(animal)){
                return animal.getName() + " cannot share this pen with the existing animals inside.";
            }
            return animal.getName() + " is inhabitable in aquariums";
        }

        if(calculatePenHasSpace(animal) > 0){
            addAnimalToPen(animal);
            message = animal.getName() + " has been assigned to " + getPenName();
        }else{
            message = animal.getName() + " cannot fit in this aquarium because it is full.";
        }
        return message;
    }

    public boolean canAnimalBeAssigned(Animal animal){
        boolean isValid = false;
        if((animal instanceof WaterAnimal)){
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

        WaterAnimal castedAnimal = (WaterAnimal) animal;
        float totalSpace = calculateWaterVolume();
        float takenSpace = 0f;

        for(Animal storedAnimal : this.getStoredAnimals()){
            takenSpace += ((WaterAnimal) storedAnimal).getRequiredWaterSpace();
        }

        takenSpace += castedAnimal.getRequiredWaterSpace();

        return totalSpace - takenSpace;
    }

    public boolean checkPenHasSpace(Animal animal){
        if(calculatePenHasSpace(animal) > 0){
            return true;
        }
        return false;
    }

    public float calculatePenHasSpace(){
        float totalSpace = calculateWaterVolume();
        float takenSpace = 0f;

        for(Animal storedAnimal : this.getStoredAnimals()){
            takenSpace += ((WaterAnimal) storedAnimal).getRequiredWaterSpace();
        }

        return totalSpace - takenSpace;
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
            message += " | Total Water: " + calculateWaterVolume() + " (metres cubed) | Remaining Space: " + calculatePenHasSpace() + " (metres cubed)";
        }else{
            message += " | Total Water: " + calculateWaterVolume() + " (metres cubed) | Remaining Space: None";
        }
        return message;
    }
}
