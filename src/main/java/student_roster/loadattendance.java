package student_roster;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import com.toedter.calendar.JDateChooser;
import student_roster.actor.Attendee;


public class loadattendance {

    private static JTable attendanceTable;
    private static JScrollPane tableScrollPane;
    private static List<String> attendanceDates = new ArrayList<>();

    public static List<String> getAttendanceDates() {
        return attendanceDates;
    }

    public static void ldattendance()
    {
        JFrame frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        JDateChooser dateChooser = new JDateChooser();
        panel.add(dateChooser);

        JButton save_date = new JButton("Add CSV");
        panel.add(save_date);
        save_date.addActionListener(e -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(dateChooser.getDate());
            attendanceDates.add(date);
            // Add CSV
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("CSV files (*.csv)", "csv");
            chooser.setFileFilter(fileFilter);
            chooser.setMultiSelectionEnabled(false);
            int approved = chooser.showOpenDialog(ApplicationPage.jFrame);
            if (approved == JFileChooser.APPROVE_OPTION) {
                File attendancefile = chooser.getSelectedFile();
                AttendanceModel am = new AttendanceModel();
                List<Attendee> attendees = am.loadData(attendancefile);
                String[] minutes = new String[attendees.size()];
                attendees.forEach(attendee -> minutes[attendee.getPosition()] = String.valueOf(attendee.getAttendanceMinutes()));
                Roster.model.addColumn(date, minutes);
//                attendanceTable.setSize(1440, 1080);
//                attendanceTable.setGridColor(Color.BLACK);
//                attendanceTable.setShowGrid(true);
//                ApplicationPage.tableScrollPane.remove(ApplicationPage.rosterTable);
//                ApplicationPage.tableScrollPane.add(attendanceTable);
//                attendanceTable.setVisible(true);
//                ApplicationPage.jFrame.setSize(0,0);
//                ApplicationPage.jFrame.setSize(1440,1080);
            }

        });


    }
}
