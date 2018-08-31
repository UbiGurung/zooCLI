package com.company;

import java.util.ArrayList;

public class Pen {

    protected float length;
    protected float width;
    private Staff assignedStaff;
    private ArrayList<Animal> storedAnimals;
    private String penType;
    private String penName;
    private ArrayList<String> storedAnimalTypes;

    public Pen(float length, float width, String penType, String penName){
        this.length = length;
        this.width = width;
        this.penType = penType;
        this.penName = penName;
        this.storedAnimals = new ArrayList<Animal>();
        this.storedAnimalTypes = new ArrayList<String>();
    }

    public float calculateLandSpace(){
        return length * width;
    }

    public String assignStaffToPen(Staff member){
        String message = "";
        if(canStaffBeAssigned(member)){
            this.assignedStaff = member;
            member.setAssignedPenIndex(penName);
            message = member.getName() + " assigned to " + this.penName;
        } else{
            ArrayList<String> responsiblePenTypes = member.getResponsibleForPenTypes();
            String recommendedPenTypes = "";
            for (String penType : responsiblePenTypes){
                recommendedPenTypes += penType + " ";
            }
            message = member.getName() + " does not have responsibility for " +
                    this.penType
                    + " pens. \nThey can only be assigned to cannot be assigned to pen types of " + recommendedPenTypes;
        }

        return message;
    }

    public boolean canStaffBeAssigned(Staff member){
        ArrayList<String> penResponsibilityTypes = member.getResponsibleForPenTypes();
        if(penResponsibilityTypes.contains(penType)){
            return true;
        }
        return false;
    }

    public boolean checkPenHasStaff(){
        if(assignedStaff == null){
            return false;
        }else{
            return true;
        }
    }

    public String getAnimalsDescription(){
        String message = " | Animals: ";
        if(getStoredAnimals().size() > 0){
            message += getStoredAnimals().size() + " animals in this pen. ";

            ArrayList<String> animalTypes = getStoredAnimalTypes();

            if(animalTypes.size() > 0){
                message += "The types of animals in this pen are ";

                for(int i = 0; i < animalTypes.size(); i++){
                    if(i + 1 == animalTypes.size()){
                        message += "and " + animalTypes.get(i);
                    }else{
                        message += animalTypes.get(i) + ", ";

                    }
                }
            }
        }else{
            message += "No animals inside this pen.";
        }
        return message;
    }

    public String assignAnimal(Animal animal){
        return "Calculate if animal can be assigned to pen.";
    }

    public boolean canAnimalBeAssigned(Animal animal){
        return false;
    }

    public boolean canSharePenWithAssignedAnimals(Animal animal){
        ArrayList<String> existingAnimalTypes = getStoredAnimalTypes();
        ArrayList<String> possiblePenMates = animal.getPossiblePenMates();

        int compatible = 0;

        if(existingAnimalTypes.isEmpty()){
            return true;
        }

        for (int i = 0; i < existingAnimalTypes.size(); i++) {
            if(possiblePenMates.contains(existingAnimalTypes.get(i))){
                compatible++;
            }
        }

        if(existingAnimalTypes.size() == compatible){
            return true;
        }
        return false;
    }

    public boolean checkPenHasSpace(Animal animal){
        return false;
    }

    public String getPenName(){
        return this.penName;
    }

    public String getPenType() {
        return this.penType;
    }

    public ArrayList<Animal> getStoredAnimals(){
        return this.storedAnimals;
    }

    public void addAnimalToPen(Animal animal){
        this.storedAnimals.add(animal);
        this.storedAnimalTypes.add(animal.getSpecies());
        animal.setAssignedPenName(penName);
    }

    public ArrayList<String> getStoredAnimalTypes(){
        return this.storedAnimalTypes;
    }

    public String getPenDescription(){
        return "Should return pen name, type of pen, details about the animals in the pen, pen dimensions," +
                "show who is assigend to the pen";
    }

    public String getStaffName(){
        return this.assignedStaff.getName();
    }

    public void setStoredAnimals(ArrayList<Animal> animals){
        this.storedAnimals = animals;
    }

}
