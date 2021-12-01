package com.roster;

public class StudentRosterSystem {

    public StudentRosterSystem() {
        createAppPage();
    }

    public static void main(String args[]) {
        System.out.Println("Initializing Application!!");
        new StudentRosterSystem();
    }

    private void createAppPage() {
        new ApplicationPage();
    }

}