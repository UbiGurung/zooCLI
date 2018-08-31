package com.company;

import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import static com.company.WeatherDownloader.printWeatherDetails;

public class Controller {
    protected ArrayList<Pen> activePens;
    protected ArrayList<Animal> activeAnimals;
    protected Map<String, ArrayList<String>> animalTypes;
    protected ArrayList<Staff> activeStaff;
    protected Scanner keyboard;
    protected Pages pages;
    protected View view;
    protected PenTypes penTypes;

    public Controller(){
        keyboard = new Scanner(System.in);
        pages = new Pages();
        view = new View();
        penTypes = new PenTypes();
    }

    public Controller(Data data){
        this.activePens = data.pens;
        this.activeAnimals = data.animals;
        this.animalTypes = data.animalTypes;
        this.activeStaff = data.staff;
        keyboard = new Scanner(System.in);
        pages = new Pages();
        view = new View();
        penTypes = new PenTypes();
    }

    public void getAllPenDetails(){
        String message = "";
        int penNumber = 1;
        if(activePens.size() == 0){
            message =  "Currently no pens exist in this zoo";
        }else{
            for(Pen pen : activePens){
                message +=  "Pen #: " + penNumber + " | " + pen.getPenDescription() + "\n";
                penNumber++;
            }
        }
        view.displayCustomString(message);
    }

    public void getAllStaffDetails(){
        String message = "";
        int staffNumber = 1;
        if(activeStaff.size() == 0){
            message =  "Currently no staff exist in this zoo";
        }else{
            for(Staff staff : activeStaff){
                message +=  "Staff #: " + staffNumber + " | " + staff.getStaffDetails() + "\n";
                staffNumber++;
            }
        }
        view.displayCustomString(message);
    }

    public String mainMenuControls(){
        view.displayMainMenu();
        String chosenOption = pages.mainPage;

        int userInput = getIntInput(0, 11);

        if(userInput == 1){
            chosenOption = pages.allAnimals;
        }else if(userInput == 2){
            chosenOption = pages.allPens;
        } else if(userInput == 3){
            chosenOption = pages.allStaff;
        } else if(userInput == 4){
            chosenOption = pages.addPen;
        } else if(userInput == 5){
            chosenOption = pages.addAnimal;
        } else if(userInput == 6){
            chosenOption = pages.addStaff;
        } else if(userInput == 7){
            chosenOption = pages.assignAnimal;
        }else if(userInput == 8){
            chosenOption = pages.assignStaff;
        }else if(userInput == 9){
            chosenOption = pages.fileStorage;
        }else if(userInput == 10){
            chosenOption = pages.animalsAutoAllocate;
        }else if(userInput == 11){
            chosenOption = pages.staffAutoAllocate;
        }else if(userInput == 0){
            printWeatherDetails();
        }

        return chosenOption;
    }

    public int getIntInput(int minOption, int maxOption){
        boolean isValid = false;
        int input = 0;

        while(!isValid){
            try{
                input = Integer.parseInt(keyboard.nextLine());

                if(input >= minOption && input <= maxOption){
                    isValid = true;
                }else{
                    System.out.println("Integer input must be between " + minOption + " and " + maxOption);
                }
            }catch(Exception e){
                System.out.println("Cannot read that input type, please enter an integer");
            }
        }
        return input;
    }

    public float getFloatInput(float minOption, float maxOption){
        boolean isValid = false;
        float input = 0;

        while(!isValid){
            try{
                input = Float.parseFloat(keyboard.nextLine());
                if(input >= minOption && input <= maxOption){
                    isValid = true;
                }else{
                    System.out.println("Float input must be between " + minOption + " and " + maxOption);
                }
            }catch(Exception e){
                System.out.println("Cannot read that input type, please enter a float number");
            }
        }
        return input;
    }

    public String getStringInput(){
        boolean isValid = false;
        String userInput = "";

        while(!isValid){
            try{
                userInput = keyboard.nextLine();
                isValid = true;
            }catch(Exception e){
                System.out.println("Cannot read that input type, please enter a string");
            }
        }

        return userInput;
    }

    public void continueMenu(){
        view.continueMenu();
        keyboard.next();
    }

    public void getAllAnimalDetails(){
        String message = "";
        int animalNumber = 1;
        if(activeAnimals.size() == 0){
            message =  "Currently no animals exist in this zoo";
        }else{
            for(Animal animal : activeAnimals){
                message += animalNumber + " " + animal.getAnimalDetails() + "\n";
                animalNumber++;
            }
        }
        view.displayCustomString(message);
    }

    public Data fileStorageMenu(){
        view.displayFileStorageMenu();
        int userInput = getIntInput(1, 2);
        Data data = null;

        if(userInput == 1){
            saveZoo();
            view.displayCustomString("Zoo has been saved!");
        }else if(userInput == 2){
            data = loadZoo();
            view.displayCustomString("Zoo has been loaded!");
        }
        return data;
    }

    public void saveZoo(){
        Gson gson = new Gson();

        Data data = new Data(this.activePens, this.activeAnimals, this.animalTypes, this.activeStaff);
        data.sortPens();
        data.sortAnimals();

        Type type = new TypeToken<Data>() {}.getType();
        String jsonData = gson.toJson(data, type);

        try{
            File file = new File("saveFile.json");
            PrintWriter fileWriter = new PrintWriter(file);
            fileWriter.print(jsonData);
            fileWriter.close();
        }catch (Exception e){
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    public Data loadZoo(){
        Gson gson = new Gson();

        Data loadedData = null;

        try{
            Path filePath = Paths.get("saveFile.json");
            String jsonData = new String(Files.readAllBytes(filePath));
            loadedData = gson.fromJson(jsonData, Data.class);
            loadedData.castPens();
            loadedData.castAnimals();
            loadedData.clearStoredAnimals();
            loadedData.loadStoredCastedAnimals();
        }catch (Exception e){
            System.out.println("Error loading file: " + e.getMessage());
        }

        return loadedData;
    }
}
