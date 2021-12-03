package student_roster.persistence;

import student_roster.ApplicationPage;
import student_roster.Roster;
import student_roster.actor.Attendee;
import student_roster.attendance.AttendanceMatcher;
import student_roster.attendance.LoadAttendance;
import student_roster.util.Utils;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SaveData {


    public static void initialize() {

        List<List<Attendee>> allAttendees = AttendanceMatcher.getAttendanceMatcher().getMergedAttendees();

        if (allAttendees != null && !allAttendees.isEmpty()) {
            List<String> columnsList = new ArrayList<>();
            columnsList.add("ID");
            columnsList.add("First Name");
            columnsList.add("Last Name");
            columnsList.add("Program and Plan");
            columnsList.add("Academic Plan");
            columnsList.add("ASURITE");
            int studentsSize = Roster.students.size();
            int dateSize = LoadAttendance.getAttendanceDates().size();

            columnsList.addAll(LoadAttendance.getAttendanceDates());
            String[] columns = new String[6 + dateSize];
            int index = 0;
            for (String column : columnsList) {
                columns[index] = column;
                index++;
            }
            Object[][] data = new Object[studentsSize][6 + dateSize];

            for (int i = 0; i < studentsSize; i++) {
                data[i][0] = Roster.students.get(i).getId();
                data[i][1] = Roster.students.get(i).getFirstName();
                data[i][2] = Roster.students.get(i).getLastName();
                data[i][3] = Roster.students.get(i).getProgram();
                data[i][4] = Roster.students.get(i).getLevel();
                data[i][5] = Roster.students.get(i).getAsuriteId();
            }

            int recordNumber = 0;
            for (List<Attendee> allAttendee : allAttendees) {
                for (Attendee attendee : allAttendee) {
                    data[attendee.getPosition()][recordNumber + 6] = attendee.getAttendanceMinutes();
                }
                recordNumber++;
            }

            JTable jTable1 = new JTable(data, columns);
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a save file");
            int userSelection = fileChooser.showSaveDialog(ApplicationPage.jFrame);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                try {
                    FileWriter fw = new FileWriter(fileToSave);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write("ID, First Name, Last Name, Program and Plan, Academic Plan, ASURITE, ");
                    for (String date : LoadAttendance.getAttendanceDates()) {
                        bw.write(date + ", ");
                    }
                    bw.newLine();
                    for (int i = 0; i < jTable1.getRowCount(); i++) {
                        for (int j = 0; j < jTable1.getColumnCount(); j++) {
                            System.out.println("i: " + i + ", j: " + j);
                            bw.write(jTable1.getValueAt(i, j).toString() + ",");
                        }
                        bw.newLine();
                    }
                    JOptionPane.showMessageDialog(ApplicationPage.jFrame, "SAVED SUCCESSFULLY", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
                    bw.close();
                    fw.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(ApplicationPage.jFrame, "ERROR", "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
                }


            }
        } else {
            Utils.displayLoadRosterDialog("Cannot save data without loading attendance data.\nPlease load attendance data by choosing Add Attendance option");
        }
    }

}
