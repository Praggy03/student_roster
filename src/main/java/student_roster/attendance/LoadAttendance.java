package student_roster.attendance;

import com.toedter.calendar.JDateChooser;
import student_roster.ApplicationPage;
import student_roster.Roster;
import student_roster.util.Utils;
import student_roster.actor.Attendee;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class LoadAttendance {

    private static List<String> attendanceDates = new ArrayList<>();

    public static List<String> getAttendanceDates() {
        return attendanceDates;
    }

    public static void loadAttendance() {
        if(Roster.students != null && !Roster.students.isEmpty()) {
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
                    File attendanceFile = chooser.getSelectedFile();
                    AttendanceModel am = new AttendanceModel();
                    List<Attendee> attendees = am.loadData(attendanceFile);
                    AttendanceMatcher attendanceMatcher = AttendanceMatcher.getAttendanceMatcher();
                    List<Attendee> additionalAttendees = attendanceMatcher.reportAndRemoveAdditionalAttendees(attendees);
                    String[] minutes = new String[attendees.size()];
                    attendees.forEach(attendee -> minutes[attendee.getPosition()] = String.valueOf(attendee.getAttendanceMinutes()));
                    Roster.model.addColumn(date, minutes);
                    attendanceMatcher.displayReportDialog(additionalAttendees, attendees.size());
                }

            });

        } else {
            Utils.displayLoadRosterDialog("Cannot load attendance without loading student data.\nPlease load student data by choosing Load a Roster option");
        }
    }

}
