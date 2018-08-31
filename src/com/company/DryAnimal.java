package com.company;

public class DryAnimal extends Animal{
    private float requiredLandSpace;

    public DryAnimal(float requiredLandSpace, String name, String description, String species, String habitat){
        super(name, description, species, habitat);
        this.requiredLandSpace = requiredLandSpace;
    }

    public float getRequiredLandSpace(){
        return requiredLandSpace;
    }
}
