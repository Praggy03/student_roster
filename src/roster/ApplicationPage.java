package com.roster

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;


/**
 * @author Praggy03
**/

public class ApplicationPage {

    /**
     * Create new form for home
    **/
    public static JFrame jFrame;

    private JTable table;
    private JMenu fileMenu, aboutMenu;
    private JMenuBar menuBar;
    private JMenuItem loadRosterMenuItem, addAttendanceItem, saveItem, plotDataItem;
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

        aboutMenu = new JMenu("About");
        aboutMenu.addActionListener((event) -> displayAbout());

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
        jFrame.setVisibile(true);
    }

    private void loadStudentRoster() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("CSV files (*.csv)", "csv");
        chooser.setFileFilter(fileFilter);
        chooser.setMultiSelectionEnabled(false);
        int approved = fileChooser.showOpenDialog(frame);
        if ( approved == JFileChooser.APPROVE_OPTION ) {
            File rosterFile = filechooser.getSelectedFile();
            RosterModel rm = new RosterModel();
            rosterTable = rm.loadData(rosterFile);
            jFrame.add(table.getTableHeader(), BorderLayout.PAGE_START);
            jFrame.add(table, BorderLayout.CENTER);
        }
    }

    private void displayAbout() {
        JOptionPane.showMessageDialog(jFrame, "CSE 563 - Assignment 5 Team \n Abhinav Venepally \n Jasvith \n Pooja Kulkarni \n Pragadeeshkumar Rajavel ", JOptionPane.PLAIN_MESSAGE);
    }

}

