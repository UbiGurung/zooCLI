package com.company;

public class AmphibiousAnimal extends Animal {
    private float requiredLandSpace;
    private float requiredWaterSpace;

    public AmphibiousAnimal(float requiredWaterSpace, float requiredLandSpace,
                            String name, String description, String species, String habitat){
        super(name, description, species, habitat);
        this.requiredLandSpace = requiredLandSpace;
        this.requiredWaterSpace = requiredWaterSpace;
    }

    public float getRequiredWaterSpace(){
        return requiredWaterSpace;
    }

    public float getRequiredLandSpace(){
        return requiredLandSpace;
    }
}
