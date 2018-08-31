package com.company;


public class AmphibiousPen extends Pen {
    private float waterLength;
    private float waterWidth;
    private float waterDepth;

    public AmphibiousPen(float waterLength, float waterWidth, float waterDepth,
                         float length, float width, String penType, String penName){
        super(length, width, penType, penName);
        this.waterLength = waterLength;
        this.waterWidth = waterWidth;
        this.waterDepth = waterDepth;
    }

    public float calculateWaterVolume(){
        return this.waterLength * this.waterWidth * this.waterDepth;
    }

    public String assignAnimal(Animal animal){
        String message = "";

        if(!canAnimalBeAssigned(animal)){
            if(!canSharePenWithAssignedAnimals(animal)){
                return animal.getName() + " cannot share this pen with the existing animals inside.";
            }
            return animal.getName() + " is inhabitable in amphibious pens";
        }

        float[] penSpace = calculatePenHasSpace(animal);

        if(penSpace[0] > 0 && penSpace[1] > 0){
            addAnimalToPen(animal);
            message = animal.getName() + " has been assigned to " + getPenName();
        }else if(penSpace[0] > 0 && penSpace[1] <= 0){
            message = getPenName() + " does not have enough water space to fit this animal in this pen.";
        } else if(penSpace[0] <= 0 && penSpace[1] > 0){
            message = getPenName() + " does not have enough land space to fit this animal in this pen.";
        }else{
            message = animal.getName() + " cannot fit this animal inside this pen, as it is full.";
        }
        return message;
    }

    public boolean checkPenHasSpace(Animal animal){
        float[] penSpace = calculatePenHasSpace(animal);
        if(penSpace[0] > 0 && penSpace[1] > 0){
            return true;
        }
        return false;
    }

    public float[] calculatePenHasSpace(Animal animal){
        if(!canAnimalBeAssigned(animal)){
            return new float[]{0,0};
        }

        AmphibiousAnimal castedAnimal = (AmphibiousAnimal) animal;
        float totalLandSpace = calculateLandSpace();
        float totalWaterSpace = calculateWaterVolume();
        float takenLandSpace = 0f;
        float takenWaterSpace = 0f;

        for(Animal storedAnimal : this.getStoredAnimals()){
            takenLandSpace += ((AmphibiousAnimal) storedAnimal).getRequiredLandSpace();
            takenWaterSpace += ((AmphibiousAnimal) storedAnimal).getRequiredWaterSpace();
        }

        takenLandSpace += castedAnimal.getRequiredLandSpace();
        takenWaterSpace += castedAnimal.getRequiredWaterSpace();

        float landSpaceLeft = totalLandSpace - takenLandSpace;
        float waterSpaceLeft = totalWaterSpace - takenWaterSpace;

        float[] requirements = new float[]{landSpaceLeft, waterSpaceLeft};

        return requirements;
    }

    public boolean canAnimalBeAssigned(Animal animal){
        boolean isValid = false;
        if((animal instanceof AmphibiousAnimal)){
            isValid = true;

            if(!canSharePenWithAssignedAnimals(animal)){
                isValid = false;
            }
        }
        return isValid;
    }

    public float[] calculatePenHasSpace(){
        float totalLandSpace = calculateLandSpace();
        float totalWaterSpace = calculateWaterVolume();
        float takenLandSpace = 0f;
        float takenWaterSpace = 0f;

        for(Animal storedAnimal : this.getStoredAnimals()){
            takenLandSpace += ((AmphibiousAnimal) storedAnimal).getRequiredLandSpace();
            takenWaterSpace += ((AmphibiousAnimal) storedAnimal).getRequiredWaterSpace();
        }

        float landSpaceLeft = totalLandSpace - takenLandSpace;
        float waterSpaceLeft = totalWaterSpace - takenWaterSpace;

        float[] requirements = new float[]{landSpaceLeft, waterSpaceLeft};

        return requirements;
    }

    public String getPenDescription(){
        String message = "Name: " + getPenName() + " | Pen Type: " + getPenType();

        message += getAnimalsDescription();

        if(checkPenHasStaff()){
            message += " | Staff: " + getStaffName();
        }else{
            message += " | Staff: Unassigned";
        }

        float[] penSpace = calculatePenHasSpace();

        if(penSpace[0] > 0 && penSpace[1] > 0){
            message += " | Total Land: " + calculateLandSpace() +
                    " (metres squared) | Total Water: " + calculateWaterVolume() + "(metres cubed) | Remaining Land Space: "
                    + penSpace[0] + " (metres squared) | Remaining Water Space: " + penSpace[1] + " (metres cubed)";
        }else if(penSpace[0] > 0 && penSpace[1] <= 0){
            message += " | Total Land: " + calculateLandSpace() +
                    " (metres squared) | Total Water: " + calculateWaterVolume() + "(metres cubed) | Remaining Land Space: "
                    + penSpace[0] + " (metres squared) | Remaining Water Space: None";
        } else if(penSpace[0] <= 0 && penSpace[1] > 0){
            message += " | Total Land: " + calculateLandSpace() +
                    " (metres squared) | Total Water: " + calculateWaterVolume() +
                    "(metres cubed) | Remaining Land Space: None | Remaining Water Space: " + penSpace[1] + " (metres cubed)";
        }else{
            message += " | Total Land: " + calculateLandSpace() +
                    " (metres squared) | Total Water: " + calculateWaterVolume() +
                    "(metres cubed) | Remaining Land Space: None | Remaining Water Space: None";
        }
        return message;
    }
}
