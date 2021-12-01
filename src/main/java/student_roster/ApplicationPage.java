package student_roster;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;

import java.io.File;


public class ApplicationPage {

    public static JFrame jFrame;

    private JTable table;
    private JMenu fileMenu, aboutMenu;
    private JMenuBar menuBar;
    private JMenuItem loadRosterMenuItem, addAttendanceItem, saveItem, plotDataItem, teamInformation;
    private JTable rosterTable;



    public ApplicationPage() {
        loadFrame();
        createMenuBar();
    }

    private void loadFrame() {
        jFrame = new JFrame();
        jFrame.setSize(900,900);
        jFrame.setTitle("Student Roster Management");
    }

    private void createMenuBar() {

        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        aboutMenu = new JMenu("About");
        menuBar.add(aboutMenu);

        teamInformation = new JMenuItem("Team Information");
        teamInformation.addActionListener(e -> displayAbout());
        aboutMenu.add(teamInformation);

        loadRosterMenuItem = new JMenuItem("Load a Roster");
        fileMenu.add(loadRosterMenuItem);
        loadRosterMenuItem.addActionListener(e -> loadStudentRoster());


        addAttendanceItem = new JMenuItem("Add Attendance");
        fileMenu.add(addAttendanceItem);

        saveItem = new JMenuItem("Save");
        fileMenu.add(saveItem);


        plotDataItem = new JMenuItem("Plot Data");
        fileMenu.add(plotDataItem);

        jFrame.setJMenuBar(menuBar);
        jFrame.setVisible(true);
    }

    private void loadStudentRoster() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("CSV files (*.csv)", "csv");
        chooser.setFileFilter(fileFilter);
        chooser.setMultiSelectionEnabled(false);
        int approved = chooser.showOpenDialog(jFrame);
        if ( approved == JFileChooser.APPROVE_OPTION ) {
            File rosterFile = chooser.getSelectedFile();
            RosterModel rm = new RosterModel();
            rosterTable = rm.loadData(rosterFile);
            jFrame.add(table.getTableHeader(), BorderLayout.PAGE_START);
            jFrame.add(table, BorderLayout.CENTER);
        }
    }

    private void displayAbout() {
        JOptionPane.showMessageDialog(jFrame, "Abhinav Venepally \n Jasvith \n Pooja Kulkarni \n Pragadeeshkumar Rajavel ","About Team" ,JOptionPane.PLAIN_MESSAGE);
    }



}
