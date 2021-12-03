package student_roster.attendance;

import student_roster.load.ApplicationPage;
import student_roster.load.Roster;
import student_roster.util.Utils;
import student_roster.actor.Attendee;
import student_roster.actor.Student;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This class is used to Match attendance records with student records, merge records with same ASU ID
 * and report all attendees that have no student record
 */
public class AttendanceMatcher {

    private static AttendanceMatcher attendanceMatcher;
    private static List<List<Attendee>> allMergedAttendees;

    public List<List<Attendee>> getMergedAttendees() {
        return allMergedAttendees;
    }


    /**
     * This method implements a factory pattern, wherein just one object
     * of Attendance Matcher will be lazily created the first time it is
     * demanded and from then onwards, this same object will be re-used
     *
     * @return an AttendanceMatcher object
     */
    public static AttendanceMatcher getAttendanceMatcher() {
        if (attendanceMatcher == null) {
            attendanceMatcher = new AttendanceMatcher();
            allMergedAttendees = new ArrayList<>();
        }
        return attendanceMatcher;
    }

    public List<Attendee> addAbsentStudents(List<Attendee> mergedAttendees) {
        List<Attendee> additionalAttendees = new ArrayList<>(mergedAttendees);
        for (Student student : Roster.students) {
            boolean found = false;

            for (Attendee attendee : mergedAttendees) {
                if (attendee.getAsuriteId().equals(student.getAsuriteId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                additionalAttendees.add(new Attendee(student.getAsuriteId(), 0));
            }
        }
        return additionalAttendees;
    }


    /**
     * This method merges all attendees that have the same ASURITE ID and while merging, it
     * sums up their minutes of attendance
     *
     * @param attendees is the list of attendees to be merged
     * @return a merged list
     */
    public List<Attendee> mergeLoadedAttendees(List<Attendee> attendees) {

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

    public void loadStudentDataIntoAttendees(List<Attendee> attendees) {
        for (Attendee attendee : attendees) {
            if (Roster.studentsMap.containsKey(attendee.getAsuriteId())) {
                Student student = Roster.studentsMap.get(attendee.getAsuriteId());
                attendee.setId(student.getId());
                attendee.setAsuriteId(student.getAsuriteId());
                attendee.setFirstName(student.getFirstName());
                attendee.setLastName(student.getLastName());
                attendee.setProgram(student.getProgram());
                attendee.setLevel(student.getLevel());
                attendee.setPosition(student.getPosition());
            }
        }
    }


    /**
     * This method will report all attendees that were not present in the student data
     *
     * @param attendees is the list of all students who attended the class
     */
    public List<Attendee> reportAndRemoveAdditionalAttendees(List<Attendee> attendees) {
        List<Attendee> additionalAttendees = getAdditionalAttendees(attendees);
        List<Attendee> validAttendees = new ArrayList<>();
        for (Attendee attendee: attendees) {
            if(!additionalAttendees.contains(attendee)) {
                validAttendees.add(attendee);
            }
        }
        attendees = validAttendees;
        allMergedAttendees.add(attendees);
        return additionalAttendees;
    }


    /**
     * This method will return a list of all attendees that were present in the attendees CSV data but not in the
     * students CSV data
     *
     * @param attendees is the list of attendees who attended the class
     * @return a reduced list of attendees that are not students for the course
     */
    private List<Attendee> getAdditionalAttendees(List<Attendee> attendees) {
        return attendees
                .stream()
                .filter(attendee -> !Roster.studentsMap.containsKey(attendee.getAsuriteId()))
                .collect(Collectors.toList());
    }


    /**
     * This method displays a dialog to report the attendees that are not a part of the student roster
     *
     * @param additionalAttendees is the list of students to be reported
     */
    public void displayReportDialog(List<Attendee> additionalAttendees) {
        JDialog dialog = new JDialog(ApplicationPage.jFrame, "Matched attendance data!", true);
        dialog.setLayout(new FlowLayout());
        dialog.setVisible(false);

        StringBuilder studentReport = new StringBuilder();
        studentReport.append("Data loaded for ")
                .append(Roster.students.size())
                .append(" students in the roster\n\n");

        if (additionalAttendees != null && !additionalAttendees.isEmpty()) {
            studentReport.append(additionalAttendees.size()).append(" additional ")
                    .append(additionalAttendees.size() == 1 ? "attendee " : "attendees ")
                    .append(additionalAttendees.size() == 1 ? "was " : "were ")
                    .append("found!\n\n");

            additionalAttendees.forEach(additionalAttendee -> studentReport
                    .append(additionalAttendee.getAsuriteId())
                    .append(", connected for ")
                    .append(additionalAttendee.getAttendanceMinutes())
                    .append(additionalAttendee.getAttendanceMinutes() == 1 ? " minute" : " minutes")
                    .append("\n"));
        } else {
            studentReport.append("No additional attendees found!");
        }

        dialog.add(new JLabel(Utils.wrapInHtml(studentReport)));
        dialog.setBounds(
                new Rectangle(ApplicationPage.jFrame.getWidth() / 2 - 250, ApplicationPage.jFrame.getHeight() / 2 - 250, 500, 300)
        );
        dialog.setVisible(true);
    }

}