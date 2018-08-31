package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.company.WeatherDownloader.printWeatherDetails;

public class Main {

    public static void main(String[] args) {


        ArrayList<Pen> activePens = new ArrayList<Pen>();
        ArrayList<Animal> activeAnimals = new ArrayList<Animal>();
        Map<String, ArrayList<String>> animalTypes = new HashMap<String, ArrayList<String>>();
        ArrayList<Staff> activeStaff = new ArrayList<Staff>();

        View view = new View();
        Pages pages = new Pages();

        boolean exitProgram = false;
        String currentPage = pages.mainPage;
        printWeatherDetails();

        Data data = new Data(activePens, activeAnimals, animalTypes, activeStaff);

        while(!exitProgram){
            Controller controller = new Controller(data);
            PenController penController = new PenController(controller);
            AnimalController animalController = new AnimalController(controller);
            StaffController staffController = new StaffController(controller);



            view.displayTitle();

            if(currentPage == pages.mainPage){
                currentPage = controller.mainMenuControls();
            }else if(currentPage == pages.allAnimals) {
                controller.getAllAnimalDetails();
                currentPage = pages.mainPage;
            }else if(currentPage == pages.allPens){
                controller.getAllPenDetails();
                currentPage = pages.mainPage;
            }else if(currentPage == pages.allStaff){
                controller.getAllStaffDetails();
                currentPage = pages.mainPage;
            }else if(currentPage == pages.addPen){
                penController.addPen();
                currentPage = pages.mainPage;
            }else if(currentPage == pages.addAnimal){
                animalController.addAnimal();
                currentPage = pages.mainPage;
            }else if(currentPage == pages.addStaff){
                staffController.addStaff();
                currentPage = pages.mainPage;
            }else if(currentPage == pages.assignAnimal){
                animalController.assignAnimalToPenMenu();
                currentPage = pages.mainPage;
            }else if(currentPage == pages.assignStaff){
                staffController.assignStaffToPenMenu();
                currentPage = pages.mainPage;
            }else if(currentPage == pages.fileStorage){
                Data response = controller.fileStorageMenu();
                if(response != null){
                    data = response;
                }
                currentPage = pages.mainPage;
            }else if(currentPage == pages.animalsAutoAllocate){
                animalController.autoAllocateAnimals();
                currentPage = pages.mainPage;
            }else if(currentPage == pages.staffAutoAllocate){
                staffController.autoAllocateStaff();
                currentPage = pages.mainPage;
            }
            if(currentPage == pages.mainPage){
                controller.continueMenu();
            }
        }
    }
}
