package com.company;

import java.util.ArrayList;

public class Staff {
    private String name;
    private String description;
    private ArrayList<String> responsibleForPen;
    private String assignedPenIndex;

    public Staff(String name, String description, ArrayList<String> responsibleForPen){
        this.name = name;
        this.description = description;
        this.responsibleForPen = responsibleForPen;
    }

    public String getName() { return this.name; }

    public String getDescription() {
        return description;
    }

    public boolean checkIsInsidePen(){
        if(assignedPenIndex != null){
            return true;
        }
        return false;
    }

    public void setAssignedPenIndex(String penName){
        this.assignedPenIndex = penName;
    }

    public String getStaffDetails(){
        String message = "Name: " + getName();

        message += " | Description: " + getDescription();

        if(responsibleForPen.size() > 0){
            message += " | Pen Type Responsibilities: " + responsibleForPen;
        }else{
            message += " | Pen Type Responsibilities: None";
        }

        if(checkIsInsidePen()){
            message += " | Pen Assignment: " + assignedPenIndex;
        }else{
            message += " | Pen Assignment: Unassigned";
        }
        return message;
    }

    public ArrayList<String> getResponsibleForPenTypes(){
        return this.responsibleForPen;
    }
}
