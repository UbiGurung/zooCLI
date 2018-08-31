package com.company;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Data {
    public ArrayList<Pen> pens;
    public ArrayList<DryPen> dryPens = new ArrayList<DryPen>();
    public ArrayList<AviaryPen> aviaryPens = new ArrayList<AviaryPen>();
    public ArrayList<AquariumPen> aquariumPens = new ArrayList<AquariumPen>();
    public ArrayList<AmphibiousPen> amphibiousPens = new ArrayList<AmphibiousPen>();

    public ArrayList<Animal> animals;
    public ArrayList<DryAnimal> dryAnimals = new ArrayList<DryAnimal>();
    public ArrayList<AviaryAnimal> aviaryAnimals = new ArrayList<AviaryAnimal>();
    public ArrayList<WaterAnimal> aquariumAnimals = new ArrayList<WaterAnimal>();
    public ArrayList<AmphibiousAnimal> amphibiousAnimals = new ArrayList<AmphibiousAnimal>();

    public Map<String, ArrayList<String>> animalTypes;
    public ArrayList<Staff> staff;

    public PenTypes penTypes;

    public Data(ArrayList<Pen> pens, ArrayList<Animal> animals,
                Map<String, ArrayList<String>> animalTypes, ArrayList<Staff> staff){
        this.pens = pens;
        this.animals = animals;
        this.animalTypes = animalTypes;
        this.staff = staff;
        this.penTypes = new PenTypes();
    }

    public void sortPens(){
        for (int i = 0; i < pens.size(); i++) {
            Pen currentPen = pens.get(i);
            if(currentPen instanceof DryPen){
                dryPens.add((DryPen) currentPen);
            }else if(currentPen instanceof AviaryPen){
                aviaryPens.add((AviaryPen) currentPen);
            }else if(currentPen instanceof  AquariumPen){
                aquariumPens.add((AquariumPen) currentPen);
            }else if(currentPen instanceof  AmphibiousPen){
                amphibiousPens.add((AmphibiousPen) currentPen);
            }
        }
    }
    public void sortAnimals(){
        for (int i = 0; i < animals.size(); i++) {
            Animal currentAnimal = animals.get(i);
            if(currentAnimal instanceof DryAnimal){
                dryAnimals.add((DryAnimal) currentAnimal);
            }else if(currentAnimal instanceof AviaryAnimal){
                aviaryAnimals.add((AviaryAnimal) currentAnimal);
            }else if(currentAnimal instanceof  WaterAnimal){
                aquariumAnimals.add((WaterAnimal) currentAnimal);
            }else if(currentAnimal instanceof  AmphibiousAnimal){
                amphibiousAnimals.add((AmphibiousAnimal) currentAnimal);
            }
        }
    }

    public void castPens() {
        ArrayList<Pen> castedPens = new ArrayList<Pen>();

        try{
            for (int i = 0; i < dryPens.size(); i++) {
                castedPens.add(dryPens.get(i));
            }

            for (int i = 0; i < aviaryPens.size() ; i++) {
                castedPens.add(aviaryPens.get(i));
            }

            for (int i = 0; i < aquariumPens.size() ; i++) {
                castedPens.add(aquariumPens.get(i));
            }

            for (int i = 0; i < amphibiousPens.size() ; i++) {
                castedPens.add(amphibiousPens.get(i));
            }
        }catch(Exception e){
            System.out.println("Error reading pens data from file. Exception: " + e.getMessage());
        }


        pens = castedPens;
    }

    public void castAnimals(){
        ArrayList<Animal> castedAnimals = new ArrayList<Animal>();

        try{
            for (int i = 0; i < dryAnimals.size(); i++) {
                castedAnimals.add(dryAnimals.get(i));
            }

            for (int i = 0; i < aviaryAnimals.size() ; i++) {
                castedAnimals.add(aviaryAnimals.get(i));
            }

            for (int i = 0; i < aquariumAnimals.size() ; i++) {
                castedAnimals.add(aquariumAnimals.get(i));
            }

            for (int i = 0; i < amphibiousAnimals.size() ; i++) {
                castedAnimals.add(amphibiousAnimals.get(i));
            }
        }catch(Exception e){
            System.out.println("Error reading animals data from file. Exception: " + e.getMessage());
        }

        animals = castedAnimals;
    }

    public void clearStoredAnimals(){
        for (int i = 0; i < pens.size(); i++) {
            Pen currentPen = pens.get(i);
            currentPen.setStoredAnimals(new ArrayList<Animal>());
        }
    }

    public void loadStoredCastedAnimals(){
        for (int i = 0; i < animals.size(); i++) {
            Animal currentAnimal = animals.get(i);
            if (currentAnimal.checkIsInsidePen()) {
                String assignedPenName = currentAnimal.getAssignedPenName();
                Pen pen = searchForPen(assignedPenName);
                if (pen != null) {
                    pen.assignAnimal(currentAnimal);
                }
            }
        }
    }

    public Pen searchForPen(String name){
        for (int i = 0; i < pens.size(); i++) {
            String penName = pens.get(i).getPenName();
            if(penName.equals(name)){
                return pens.get(i);
            }
        }
        return null;
    }
}
