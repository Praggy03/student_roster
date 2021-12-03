package student_roster;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;
import com.toedter.calendar.JDateChooser;



public class loadattendance {

    private static JTable attendanceTable;
    private static JScrollPane tableScrollPane;
    private static List<SimpleDateFormat> attendanceDates;

    public static List<SimpleDateFormat> getAttendanceDates() {
        return attendanceDates;
    }

    public static void ldattendance()
    {
        JFrame frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        JDateChooser dateChooser = new JDateChooser();
        panel.add(dateChooser);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(dateChooser.getDate());
        attendanceDates.add(sdf);

        JButton save_date = new JButton("Add CSV");
        save_date.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // Add CSV
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("CSV files (*.csv)", "csv");
                chooser.setFileFilter(fileFilter);
                chooser.setMultiSelectionEnabled(false);
                int approved = chooser.showOpenDialog(ApplicationPage.jFrame);
                if (approved == JFileChooser.APPROVE_OPTION) {
                    File attendancefile = chooser.getSelectedFile();
                    AttendanceModel am = new AttendanceModel();
                    attendanceTable = am.loadData(attendancefile);
                    attendanceTable.setSize(1440, 1080);
                    attendanceTable.setGridColor(Color.BLACK);
                    attendanceTable.setShowGrid(true);
                    tableScrollPane = new JScrollPane(attendanceTable);
                    tableScrollPane.setVisible(true);
                    tableScrollPane.setSize(1440, 1080);
                    ApplicationPage.jFrame.add(tableScrollPane, BorderLayout.PAGE_START);
                    ApplicationPage.jFrame.setSize(0, 0);
                    ApplicationPage.jFrame.setSize(1440, 1080);
                    attendanceTable.setVisible(true);

                }

            }
        });
        panel.add(save_date);


    }
}
