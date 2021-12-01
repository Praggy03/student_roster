package student_roster.attendance;

import student_roster.ApplicationPage;
import student_roster.actor.Attendee;
import student_roster.actor.Student;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AttendanceMatcher {

    private static AttendanceMatcher attendanceMatcher;

    /**
     * This method implements a factory pattern, wherein just one object
     * of Attendance Matcher will be lazily created the first time it is
     * demanded and from then onwards, this same object will be re-used
     *
     * @return an AttendanceMatcher object
     */
    public static AttendanceMatcher getAttendanceMatcher() {
        if(attendanceMatcher == null) {
            attendanceMatcher = new AttendanceMatcher();
        }
        return attendanceMatcher;
    }

    /**
     * This method will merge all attendees with same ASU ID
     *
     * @param attendees is the list of students who attended the class
     * @param date is the current date
     * @return a list of merged attendees
     */
    public List<Attendee> matchAttendance(List<Attendee> attendees, LocalDateTime date) {
        // This list will be passed to this method by Add Attendance feature
        attendees = Arrays.asList(
                new Attendee("firstName", "lastName", "fname", 10),
                new Attendee("firstName2", "lastName2", "fname2", 15),
                new Attendee("Jane", "Doe", "jdoe", 5),
                new Attendee("Pooja", "Kulkarni", "pkulka", 1),
                new Attendee("Pooja", "Kulkarni", "pkulka", 3),
                new Attendee("Pooja", "Kulkarni", "pkulka", 4),
                new Attendee("Jane", "Doe", "jdoe", 7)
        );

        // We merge all attendees that have the same ASU ID
        return mergeLoadedAttendees(attendees);

    }

    public void reportAdditionalAttendees(List<Attendee> mergedAttendees) {
        // Get all students from the static data loaded from Load Roster feature
        List<Student> allStudents = Arrays.asList(
                new Student("firstName", "lastName", "fname"),
                new Student("firstName2", "lastName2", "fname2"),
                new Student("Pooja", "Kulkarni", "pkulka")
        );

        // We use the merged list to find out the list of all students not present in the students list
        List<Attendee> additionalAttendees = getAdditionalAttendees(allStudents, mergedAttendees);

        // We then open a dialog that displays number of loaded attendees and additional attendees, if any
        displayReportDialog(additionalAttendees, mergedAttendees.size());
    }


    /**
     * This method will return a list of all attendees that were present in the attendees CSV data but not in the
     * students CSV data
     *
     * @param attendees   is the list of attendees who attended the class
     * @param allStudents is the list of all students
     * @return a reduced list of attendees that are not students for the course
     */
    private List<Attendee> getAdditionalAttendees(List<Student> allStudents, List<Attendee> attendees) {
        return attendees
                .stream()
                .filter(attendee -> !allStudents.contains(attendee))
                .collect(Collectors.toList());
    }


    /**
     * This method displays a dialog to report the attendees that are not a part of the student roster
     *
     * @param additionalAttendees is the list of students to be reported
     * @param numberOfAttendees   is the number of loaded attendees
     */
    private void displayReportDialog(List<Attendee> additionalAttendees, int numberOfAttendees) {
        JDialog dialog = new JDialog(ApplicationPage.jFrame, "Matched attendance data!", true);
        dialog.setLayout(new FlowLayout());
        dialog.setVisible(false);

        StringBuilder studentReport = new StringBuilder();
        studentReport.append("Data loaded for ")
                .append(numberOfAttendees)
                .append(" students in the roster\n\n");

        if (additionalAttendees != null && !additionalAttendees.isEmpty()) {
            studentReport.append(additionalAttendees.size()).append(" additional ")
                    .append(additionalAttendees.size() == 1 ? "attendee " : "attendees ")
                    .append(additionalAttendees.size() == 1 ? "was " : "were ")
                    .append("found!\n\n");

            additionalAttendees.forEach(additionalAttendee -> studentReport
                    .append(additionalAttendee.getName())
                    .append(", connected for ")
                    .append(additionalAttendee.getAttendanceMinutes())
                    .append(additionalAttendee.getAttendanceMinutes() == 1 ? " minute" : " minutes")
                    .append("\n"));
        } else {
            studentReport.append("No additional attendees found!");
        }

        dialog.add(new JLabel(wrapInHtml(studentReport)));
        dialog.setBounds(
                new Rectangle(ApplicationPage.jFrame.getWidth() / 2 - 250, ApplicationPage.jFrame.getHeight() / 2 - 250, 500, 300)
        );
        dialog.setVisible(true);
    }


    /**
     * This method wraps the string contained by the string builder in HTML
     * This is done so that all "\n", i.e. next line characters can be replaced
     * by \<br\> and the dialog lines are displayed one after the other
     *
     * @param text is the sting builder text to be wrapped with HTML
     * @return the wrapped string
     */
    private String wrapInHtml(StringBuilder text) {
        text.insert(0, "<html><body><p>");
        text.append("</p></body></html>");
        return text.toString().replace("\n", "<br>");
    }


    /**
     * This method merges all attendees that have the same ASURITE ID and while merging, it
     * sums up their minutes of attendance
     *
     * @param attendees is the list of attendees to be merged
     * @return a merged list
     */
    private List<Attendee> mergeLoadedAttendees(List<Attendee> attendees) {
        return attendees
                .stream()
                .collect(Collectors.toMap(
                        Attendee::getAsuriteId,
                        Function.identity(),
                        (attendee1, attendee2) -> {
                            Attendee mergedAttendee = attendee1.clone();
                            mergedAttendee.setAttendanceMinutes(attendee1.getAttendanceMinutes() + attendee2.getAttendanceMinutes());
                            return mergedAttendee;
                        })
                )
                .values()
                .stream()
                .toList();
    }

}
