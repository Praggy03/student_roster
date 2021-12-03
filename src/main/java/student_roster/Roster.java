package student_roster;

import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import com.opencsv.*;
import student_roster.actor.Student;

import javax.swing.JOptionPane;


import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Roster {

    public Roster() {
        students = new ArrayList<Student>();
    }

    public static ArrayList<Student> students;
    public static DefaultTableModel model;
    public JTable table;
    private CSVReader csvReader;
    private final String[] header = {"ID", "First Name", "Last Name", "Program and Plan", "Academic Plan", "ASURITE"};
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
            int i = 0;
            while((row = csvReader.readNext()) != null ) {
                boolean pass = addStudent(row, i);
                if ( pass ) {
                    model.addRow(row);
                    i++;
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

    private boolean addStudent(String[] data, int position) {
        Student student = new Student(data[0], data[1], data[2], data[3], data[4], data[5], position);
        if (students.stream().anyMatch(s -> s.getAsuriteId().equals(student.getAsuriteId()))) {
            return false;
        }
        students.add(student);
        return true;
    }

}