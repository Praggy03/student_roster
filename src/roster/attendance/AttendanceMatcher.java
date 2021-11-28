package src.roster.attendance;

import src.roster.actor.Attendee;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class AttendanceMatcher {

    private static JFrame jFrame;

    public static void main(String[] args) {
        // This should be done in the main GUI class later
        new AttendanceMatcher().matchAttendance();
    }

    public void matchAttendance() {
        loadMatcherUI();
    }

    private void loadMatcherUI() {
        // Below call is just for testing purposes and attendance matcher UI should later be integrated with main GUI
        initializeFrame();
        JButton jButton = new JButton("Match attendance");
        jButton.setBounds(jFrame.getWidth()/2 - 100, jFrame.getHeight()/2 - 25, 200, 50);

        // Below is some sample data:
        List<Attendee> additionalAttendees = Arrays.asList(
                new Attendee("Jane", "Doe", "jdoe", 5),
                new Attendee("Pooja", "Kulkarni", "pkulk", 1)
        );

        jButton.addActionListener(e -> getDialog(additionalAttendees, 50).setVisible(true));

        jFrame.add(jButton);
    }

    private JDialog getDialog(List<Attendee> additionalAttendees, int studentsLoaded) {
        JDialog dialog = new JDialog(jFrame, "Matched attendance data!", true);
        dialog.setLayout(new FlowLayout());
        dialog.setVisible(false);

        StringBuilder studentReport = new StringBuilder();
        studentReport.append("Data loaded for ")
                .append(studentsLoaded)
                .append(" students in the roster\n\n");

        if(additionalAttendees != null && !additionalAttendees.isEmpty()) {
            studentReport.append(additionalAttendees.size()).append(" additional ")
                    .append(additionalAttendees.size() == 1 ? "attendee" : "attendees")
                    .append(" was found!\n\n");

            additionalAttendees.forEach(additionalAttendee -> studentReport
                    .append(additionalAttendee.getName())
                    .append(", connected for ")
                    .append(additionalAttendee.getAttendanceMinutes())
                    .append(additionalAttendee.getAttendanceMinutes() == 1 ? " minute" : " minutes")
                    .append("\n"));
        }

        dialog.add( new JLabel (wrapInHtml(studentReport)));
        dialog.setBounds(new Rectangle(400, 400, 500, 300));
        return dialog;
    }

    private void initializeFrame() {
        jFrame = new JFrame();
        jFrame.setBounds(new Rectangle(400, 400, 700, 500));
        jFrame.setLayout(null);
        jFrame.setVisible(true);
    }

    private String wrapInHtml(StringBuilder text) {
        text.insert(0, "<html><body><p>");
        text.append("</p></body></html>");
        return text.toString().replace("\n", "<br>");
    }

}
