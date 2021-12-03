package student_roster;

import student_roster.load.ApplicationPage;

public class StudentRosterSystem {

    public StudentRosterSystem() {
        createAppPage();
    }

    public static void main(String args[]) {
        System.out.println("Initializing Application!!");
        new StudentRosterSystem();
    }

    private void createAppPage() {
        new ApplicationPage();
    }

}