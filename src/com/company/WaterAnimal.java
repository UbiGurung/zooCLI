package com.company;

public class WaterAnimal extends Animal {
    private float requiredWaterSpace;

    public WaterAnimal(float requiredWaterSpace, String name, String description, String species, String habitat){
        super(name, description, species, habitat);
        this.requiredWaterSpace = requiredWaterSpace;
    }

    public float getRequiredWaterSpace(){
        return requiredWaterSpace;
    }
}
