package com.company;

import java.util.ArrayList;

public class Animal {
    private String name;
    private String description;
    private boolean isInsidePen;
    private String species;
    private String habitat;
    private ArrayList<String> possiblePenMates;
    private String assignedPenIndex;

    public Animal(String name, String description,String species, String habitat){
        this.name = name;
        this.description = description;
        this.species = species;
        this.isInsidePen = false;
        this.habitat = habitat;
    }

    public String getDescription(){
        return description;
    }

    public String getAnimalDetails(){
        String message = "Name: " + getName() + " | Species: " + getSpecies();

        message += " | Description: " + getDescription() + " | Habitat: " + getHabitat();

        if(!checkIsInsidePen()){
            message += " | Pen Assignment: Unassigned";
        } else{
            message += " | Pen Assignment: " + getAssignedPenName();
        }

        return message;
    }

    public boolean checkIsInsidePen(){
        return isInsidePen;
    }

    public void toggleIsInsidePen(){
        this.isInsidePen = true;
    }

    public String getName(){
        return this.name;
    }

    public String getSpecies() {return this.species;}

    public String getHabitat() {return this.habitat;}

    public String getAssignedPenName() {return this.assignedPenIndex;}

    public void setAssignedPenName(String name){
        this.assignedPenIndex = name;
        toggleIsInsidePen();
    }

    public void setPossiblePenMates(ArrayList<String> penMates){
        this.possiblePenMates = penMates;
    }

    public void addPossiblePenMate(String penMate){
        this.possiblePenMates.add(penMate);
    }

    public ArrayList<String> getPossiblePenMates(){
        return this.possiblePenMates;
    }
}
