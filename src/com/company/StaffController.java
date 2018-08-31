package com.company;


import java.util.ArrayList;
import java.util.Map;

public class StaffController extends Controller {
    protected Controller controller;
    protected ArrayList<Pen> activePens;
    protected ArrayList<Animal> activeAnimals;
    protected Map<String, ArrayList<String>> animalTypes;
    protected ArrayList<Staff> activeStaff;
    protected PenTypes penTypes;

    public StaffController(Controller controller){
        this.controller = controller;
        this.activePens = controller.activePens;
        this.activeAnimals = controller.activeAnimals;
        this.activeStaff = controller.activeStaff;
        this.animalTypes = controller.animalTypes;
        this.penTypes = controller.penTypes;
    }

    public void addStaff(){
        view.displayCustomString("Please enter the following details.");
        String name = addStaffName();

        view.displayCustomString("Description:");
        String description = getStringInput();

        view.displayCustomString("Enter the name of your Staff:");
        ArrayList<String> responsibleForPenTypes = addPenResponsibilities(name);

        Staff newStaff = new Staff(name, description, responsibleForPenTypes);

        activeStaff.add(newStaff);
        view.displayCustomString(newStaff.getName() + " has joined to the zoo!");
    }

    public String addStaffName(){
        boolean isValid = false;
        String staffName = "";

        while(!isValid){
            view.displayCustomString("Name:");
            staffName = getStringInput();
            if(!checkStaffNameExists(staffName)){
                isValid = true;
            }else{
                view.displayCustomString("Somebody already works in the zoo with that name, please provide additional details to distinguish the two.");
            }
        }
        return staffName;
    }

    public boolean checkStaffNameExists(String name){
        for (int i = 0; i < activeStaff.size(); i++) {
            Staff currentStaff = activeStaff.get(i);
            String penName = currentStaff.getName().toLowerCase();
            if(penName.equals(name.toLowerCase())){
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> addPenResponsibilities(String name){
        ArrayList<String> responsibilities = new ArrayList<String>();

        boolean exit = false;

        while(!exit){
            view.displayCustomString("Choose the types of pen your staff is responsible for: \n" +
                    "[1] Dry Pens\n" +
                    "[2] Aviaries\n" +
                    "[3] Aquariums\n" +
                    "[4] Part water, part dry pens\n" +
                    "[5] Petting Pens\n" +
                    "[0] Finish");

            int userInput = getIntInput(0, 5);

            if(userInput == 0){
                exit = true;
            }else{
                String response = assignPenResponsibilities(responsibilities, userInput);

                if(response == "exists"){
                    view.displayCustomString("This responsibility has already been added for this staff");
                }else{
                    responsibilities.add(response);
                    view.displayCustomString(response + " pen type has been assigned to " + name);
                }
            }
        }

        return responsibilities;
    }

    public String assignPenResponsibilities(ArrayList<String> responsibilities, int choice){
        String penToAdd = "";

        switch (choice){
            case 1:
                penToAdd = penTypes.dry;
                break;
            case 2:
                penToAdd = penTypes.aviary;
                break;
            case 3:
                penToAdd = penTypes.aquarium;
                break;
            case 4:
                penToAdd = penTypes.mixed;
                break;
            case 5:
                penToAdd = penTypes.pettingPen;
                break;
            default:
                penToAdd = penTypes.dry;
                view.displayCustomString("Could not determine pen type responsibility, dry pen added instead");
        }

        if(checkReponsibilityExists(responsibilities, penToAdd)){
            penToAdd = "exists";
        }

        return penToAdd;
    }

    public boolean checkReponsibilityExists(ArrayList<String> responsibilities, String penType){
        if(responsibilities.contains(penType)){
            return true;
        }
        return false;
    }

    public void assignStaffToPenMenu(){
        controller.getAllStaffDetails();
        if(activeStaff.size() > 0){
            view.displayCustomString("Please choose a staff number in order to assign them to a pen:");
            int userInputAnimal = getIntInput(1, activeStaff.size());

            Staff chosenStaff = activeStaff.get(userInputAnimal - 1);

            assignStaffToPen(chosenStaff);
        }
    }

    public String assignStaffToPen(Staff staff){
        boolean exit = false;
        String message = "";

        while(!exit){
            if(activePens.size() > 0){
                view.displayCustomString("Do you want to assign this staff to a pen?\n" +
                        "[1] Yes\n" +
                        "[2] No \n");
                int userInput = getIntInput(1,2);

                if(userInput == 1){
                    controller.getAllPenDetails();
                    view.displayCustomString("Choose the pen index number you want your animal to be assigned to:");
                    int penIndex = getIntInput(0 + 1, activePens.size());
                    Pen penToAssignTo = activePens.get(penIndex - 1);
                    if(penToAssignTo.checkPenHasStaff()){
                        view.displayCustomString("This pen already has a staff member assigned to it.");
                    }else{
                        view.displayCustomString(penToAssignTo.assignStaffToPen(staff));
                        if(staff.checkIsInsidePen()){
                            exit = true;
                        }
                    }

                }else if(userInput == 2){
                    message = staff.getName() + " has been added to the Zoo!";
                    exit = true;
                }
            }else{
                message = staff.getName() + " has been added to the Zoo!";
                exit = true;
            }
        }
        return message;
    }

    public String checkForUnassignedStaff(){
        String unassignedStaff = "";
        if(activeStaff.size() <= 0){
            return "No staff currently exist in the zoo";
        }else{
            for(Staff staff : activeStaff){
                if(!staff.checkIsInsidePen()){
                    unassignedStaff += staff.getName() + ", ";
                }
            }
        }

        if(unassignedStaff.length() > 0){
            return "These staff are not assigned to a pen: " + unassignedStaff;
        }
        return "All staff have been assigned to a pen";
    }

    public void autoAllocateStaff(){
        if(!activeStaff.isEmpty() || !activePens.isEmpty()){
            for (int i = 0; i < activeStaff.size(); i++) {
                Staff currentStaff = activeStaff.get(i);
                if(currentStaff.checkIsInsidePen()){
                    break;
                }

                ArrayList<String> penResponsibilityTypes = currentStaff.getResponsibleForPenTypes();
                for (int j = 0; j < activePens.size(); j++) {
                    Pen currentPen = activePens.get(j);
                    if(!currentPen.checkPenHasStaff()){
                        if(penResponsibilityTypes.contains(currentPen.getPenType())){
                            if(currentPen.canStaffBeAssigned(currentStaff)){
                                currentPen.assignStaffToPen(currentStaff);
                                break;
                            }
                        }
                    }
                }
            }
        }
        view.displayCustomString(checkForUnassignedStaff());
    }
}
