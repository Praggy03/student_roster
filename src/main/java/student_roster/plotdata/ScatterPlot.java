package student_roster.plotdata;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import student_roster.actor.Attendee;
import student_roster.attendance.AttendanceMatcher;
import student_roster.attendance.LoadAttendance;
import student_roster.load.Roster;
import student_roster.util.Utils;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ScatterPlot {


    static Integer[][][] calculatePercentageArray(JTable a, int count, int columnLength) {
        int total = 75;
        Integer[][][] counter = new Integer[columnLength - 4][count][2];

        for (int f = 0; f < columnLength - 4; f++) {

            for (int i = 0; i < a.getRowCount(); i++) {
                String str = a.getValueAt(i, 4 + f).toString();
                int percent = Integer.parseInt(str) * 100 / total;
                if (percent > 100) {
                    percent = 100;
                }

                counter[f][i][0] = percent;
                if (counter[f][i][1] == null) {
                    counter[f][i][1] = 0;
                }
                counter[f][i][1]++;
            }
        }

        return counter;
    }

    // Function which merges counts of each attendance percentage
    static Integer[][][] mergeCountArray(Integer[][][] a, int count, int columnLength) {
        Integer[][][] finalArray = new Integer[columnLength - 4][count][2];

        //Merging counts of each percentage
        for (int f = 0; f < columnLength - 4; f++) {

            int mergeCount = 0;
            for (int j = 0; j < count; j++) {
                mergeCount = 0;
                for (int k = 0; k < count; k++) {
                    if (Objects.equals(a[f][j][0], a[f][k][0])) {
                        mergeCount++;
                    }
                }
                finalArray[f][j][0] = a[f][j][0];
                finalArray[f][j][1] = mergeCount;
            }
        }
        return finalArray;
    }


    public static void scatterPlot() {

        if (Roster.students != null && !Roster.students.isEmpty()) {
            List<List<Attendee>> allAttendees = AttendanceMatcher.getAttendanceMatcher().getMergedAttendees();

            if (allAttendees != null && !allAttendees.isEmpty()) {

                List<String> columnsList = new ArrayList<>();
                columnsList.add("Last Name");
                columnsList.add("Program and Plan");
                columnsList.add("Academic Plan");
                columnsList.add("ASURITE");
                int studentsSize = Roster.students.size();
                int dateSize = LoadAttendance.getAttendanceDates().size();

                columnsList.addAll(LoadAttendance.getAttendanceDates());
                String[] columns = new String[4 + dateSize];
                int index = 0;
                for (String column : columnsList) {
                    columns[index] = column;
                    index++;
                }


                Object[][] data = new Object[studentsSize][4 + dateSize];

                for (int i = 0; i < studentsSize; i++) {
                    data[i][0] = Roster.students.get(i).getLastName();
                    data[i][1] = Roster.students.get(i).getProgram();
                    data[i][2] = Roster.students.get(i).getLevel();
                    data[i][3] = Roster.students.get(i).getAsuriteId();
                }

                int recordNumber = 0;
                for (List<Attendee> allAttendee : allAttendees) {
                    for (Attendee attendee : allAttendee) {
                        data[attendee.getPosition()][recordNumber + 4] = attendee.getAttendanceMinutes();
                    }
                    recordNumber++;
                }

                JTable jTable1 = new JTable(data, columns);
                int count = data.length;
                int columnLength = data[0].length;

                Integer[][][] counter = calculatePercentageArray(jTable1, count, columnLength); //Call function which calculates percentage and makes the array;
                Integer[][][] finalArray = mergeCountArray(counter, count, columnLength); //Call function which merges count array


                // Work on load roaster + attendance combined CSV....plot
                XYSeriesCollection dataset = new XYSeriesCollection();

                for (int f = 0; f < columnLength - 4; f++) {
                    XYSeries series1 = new XYSeries(columns[f + 4]);
                    for (int l = 0; l < count; l++) {
                        series1.add(finalArray[f][l][0], finalArray[f][l][1]);
                    }
                    try {
                        dataset.addSeries(series1);
                    } catch (IllegalArgumentException e) {
                        Utils.displayLoadRosterDialog("Attendance can only be plotted if distinct dates are selected.\n You must have loaded attendance two or more times for the same date!\nPlease restart the application and load correct attendance", "Operation denied");
                        return;
                    }
                }

                JFreeChart scatterPlot = ChartFactory.createScatterPlot(
                        "Attendance Scatter Plot", // Chart title
                        "Percentage", // X-Axis Label
                        "Count", // Y-Axis Label
                        dataset // Dataset for the Chart
                );


                ChartFrame frame2 = new ChartFrame("Plot Chart", scatterPlot);
                frame2.setVisible(true);
                frame2.setSize(800, 400);
                frame2.setTitle("Attendence Statistics - Scatter Plot");
                frame2.setResizable(false);
                frame2.setLocationRelativeTo(null);

            } else {
                Utils.displayLoadRosterDialog("Cannot plot data without loading attendance data.\nPlease load attendance data by choosing Load attendance option", "Operation denied");
            }
        } else {
            Utils.displayLoadRosterDialog("Cannot load attendance without loading student data.\nPlease load student data by choosing Load a Roster option", "Operation denied");
        }
    }
}
