package com.roster

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;



/**
 * @author Praggy03
**/

public class ApplicationPage {

    /**
     * Create new form for home
    **/
    public ApplicationPage() {
        loadPage();
    }


    public void loadPage() {
        createMenuBar();
        setTitle("Student Roster Management")

    }

    public void createMenuBar() {

        var menuBar = new JMenuBar();
        var fileMenu = new JMenu("File");
        var aboutMenu = new JMenu("About");
        var loadRosterMenuItem = new JMenuItem("Load a Roster");
        var addAttendanceItem = new JMenuItem("Add Attendance");
        var saveItem = new JMenuItem("Save");
        var plotDataItem = new JMenuItem("Plot Data");

        RosterUploadPage rp = new RosterUploadPage();
        loadRosterMenuItem.addActionListener((event) -> rp.show());

        AboutMenuDialog am = new AboutMenuDialog();
        aboutMenu.addActionListener((event) -> am.show());

        fileMenu.add(loadRosterMenuItem);
        fileMenu.add(addAttendanceItem);
        fileMenu.add(saveItem);
        fileMenu.add(plotDataItem);


    }

}

