package student_roster;

import javax.swing.filechooser.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JTable;
import com.opencsv.*;
import com.opencsv.exceptions.CsvValidationException;
import student_roster.actor.Attendee;
import student_roster.actor.Student;

import javax.swing.JOptionPane;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceModel {

    public AttendanceModel() {
        attendies = new ArrayList<Student>();
    }

    public static ArrayList<Student> attendies;
    private DefaultTableModel model;
    public JTable table;
    private CSVReader csvReader;
    private final String[] header = {"ASURITE", "Time"};
    private List<String[]> rowData;
    private String[] row;

    public JTable loadData(File file) {
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
                boolean pass = addAttendee(row);
                if ( pass ) {
                    model.addRow(row);
                }
            }
        } catch (Exception e) {
            errorDialog("An error occured while processing your CSV", "Fatal!");
            return null;
        }

        System.out.println(table);
        return table;
    }

    public void errorDialog(String message, String title) {
        JOptionPane.showMessageDialog(ApplicationPage.jFrame, message, title, JOptionPane.ERROR_MESSAGE);
    }

    private boolean addAttendee(String[] data) {
        Attendee attendee = new Attendee(data[0], Integer.parseInt(data[1]));
        attendies.add(attendee);
        return true;
    }

}
