package student_roster.attendance;

import com.opencsv.CSVReader;
import student_roster.ApplicationPage;
import student_roster.actor.Attendee;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class AttendanceModel {

    public static ArrayList<Attendee> attendees;

    public AttendanceModel() {
        attendees = new ArrayList<>();
    }

    public List<Attendee> loadData(File file) {
        List<Attendee> attendees;
        CSVReader csvReader;
        try {
            csvReader = new CSVReader(new FileReader(file));
        } catch (FileNotFoundException fe) {
            errorDialog("File not found!", "File Error");
            return null;
        }

        try {
            String[] row;
            while ((row = csvReader.readNext()) != null) {
                addAttendee(row);
            }
            AttendanceMatcher attendanceMatcher = AttendanceMatcher.getAttendanceMatcher();
            attendees = attendanceMatcher.addAbsentStudents(AttendanceModel.attendees);
            attendees = attendanceMatcher.mergeLoadedAttendees(attendees);
            attendanceMatcher.loadStudentDataIntoAttendees(attendees);
        } catch (Exception e) {
            errorDialog("An error occured while processing your CSV. Please make sure the format is correct", "Fatal!");
            return null;
        }

        return attendees;
    }

    public void errorDialog(String message, String title) {
        JOptionPane.showMessageDialog(ApplicationPage.jFrame, message, title, JOptionPane.ERROR_MESSAGE);
    }

    private void addAttendee(String[] data) {
        Attendee attendee = new Attendee(data[0].replaceAll("[^a-zA-Z0-9]", ""),
                Integer.parseInt(data[1].replaceAll("[^a-zA-Z0-9]", "")));
        attendees.add(attendee);
    }

}
