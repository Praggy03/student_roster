package student_roster;

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