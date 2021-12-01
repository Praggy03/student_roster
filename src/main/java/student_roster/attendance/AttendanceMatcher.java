package student_roster.attendance;

import student_roster.ApplicationPage;
import student_roster.actor.Attendee;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AttendanceMatcher {

    public void matchAttendance(List<Attendee> attendees, LocalDateTime date) {
//        attendees = Arrays.asList(
//                new Attendee("Jane", "Doe", "jdoe", 5),
//                new Attendee("Pooja", "Kulkarni", "pkulka", 1),
//                new Attendee("Pooja", "Kulkarni", "pkulka", 3),
//                new Attendee("Pooja", "Kulkarni", "pkulka", 4),
//                new Attendee("Jane", "Doe", "jdoe", 7)
//        );
        List<Attendee> mergedAttendees = mergeLoadedAttendees(attendees);
        if(mergedAttendees != null && !mergedAttendees.isEmpty()) {
            displayReportDialog(mergedAttendees, 50).setVisible(true);
        }
    }

    private JDialog displayReportDialog(List<Attendee> additionalAttendees, int studentsLoaded) {
        JDialog dialog = new JDialog(ApplicationPage.jFrame, "Matched attendance data!", true);
        dialog.setLayout(new FlowLayout());
        dialog.setVisible(false);

        StringBuilder studentReport = new StringBuilder();
        studentReport.append("Data loaded for ")
                .append(studentsLoaded)
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
        }

        dialog.add(new JLabel(wrapInHtml(studentReport)));
        dialog.setBounds(
                new Rectangle(ApplicationPage.jFrame.getWidth()/2 - 250, ApplicationPage.jFrame.getHeight()/2 - 250, 500, 300)
        );

        return dialog;
    }

    private String wrapInHtml(StringBuilder text) {
        text.insert(0, "<html><body><p>");
        text.append("</p></body></html>");
        return text.toString().replace("\n", "<br>");
    }

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
