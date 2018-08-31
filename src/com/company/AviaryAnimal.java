package com.company;

public class AviaryAnimal extends Animal {
    private float requiredLandSpace;
    private float requiredAirSpace;

    public AviaryAnimal(float requiredAirSpace, float requiredLandSpace, String name,
                        String description, String species, String habitat){
        super(name, description, species, habitat);
        this.requiredLandSpace = requiredLandSpace;
        this.requiredAirSpace = requiredAirSpace;
    }

    public float getRequiredAirSpace(){
        return requiredAirSpace;
    }

    public float getRequiredLandSpace(){
        return requiredLandSpace;
    }
}
