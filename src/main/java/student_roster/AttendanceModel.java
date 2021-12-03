package student_roster;

import javax.swing.filechooser.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JTable;
import com.opencsv.*;
import com.opencsv.exceptions.CsvValidationException;
import student_roster.actor.Attendee;
import student_roster.actor.Student;
import student_roster.attendance.AttendanceMatcher;

import javax.swing.JOptionPane;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceModel {

    public AttendanceModel() {
        attendies = new ArrayList<>();
    }

    public static List<Attendee> attendies;
    private DefaultTableModel model;
    public JTable table;
    private CSVReader csvReader;
    private final String[] header = {"ASURITE", "Time"};
    private List<String[]> rowData;
    private String[] row;

    public List<Attendee> loadData(File file) {
        List<Attendee> mergedAttendees;
        try {
            csvReader = new CSVReader(new FileReader(file));
        } catch (FileNotFoundException fe) {
            errorDialog("File not found!", "File Error");
            return null;
        }

        model = new DefaultTableModel(header, 0);
        table = new JTable(model);
        table.setModel(model);
        try {
            while((row = csvReader.readNext()) != null ) {
                addAttendee(row);
            }
            mergedAttendees = AttendanceMatcher.getAttendanceMatcher().mergeLoadedAttendees(attendies);
            mergedAttendees = AttendanceMatcher.getAttendanceMatcher().addAdditionalAttendees(mergedAttendees);

//            mergedAttendees.forEach(mergedAttendee -> {
//                String[] attendeeArr = new String[7];
//                attendeeArr[0] = mergedAttendee.getId();
//                attendeeArr[1] = mergedAttendee.getFirstName();
//                attendeeArr[2] = mergedAttendee.getLastName();
//                attendeeArr[3] = mergedAttendee.getProgram();
//                attendeeArr[4] = mergedAttendee.getLevel();
//                attendeeArr[5] = mergedAttendee.getAsuriteId();
//                attendeeArr[6] = Integer.toString(mergedAttendee.getAttendanceMinutes());
//                model.addRow(attendeeArr);
//            });
        } catch (Exception e) {
            errorDialog("An error occured while processing your CSV", "Fatal!");
            return null;
        }

        return mergedAttendees;
    }

    public void errorDialog(String message, String title) {
        JOptionPane.showMessageDialog(ApplicationPage.jFrame, message, title, JOptionPane.ERROR_MESSAGE);
    }

    private void addAttendee(String[] data) {
        Attendee attendee = new Attendee(data[0].replaceAll("[^a-zA-Z0-9]", ""),
                Integer.parseInt(data[1].replaceAll("[^a-zA-Z0-9]", "")));
        attendies.add(attendee);
    }

}
