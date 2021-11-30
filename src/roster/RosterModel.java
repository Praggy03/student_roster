package com.roster;

import javax.swing.filechooser.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


import java.io.*;

import com.opencsv.*;

public class RosterModel {

    private DefaultTableModel model;
    public JTable table;

    public JTable loadData(File file) {
        Pair<Vector<String>, Vector<Vector<String>>> csv = Utils.readCSV(file);
        model = new DefaultTableModel();
        model.setDataVector(csv.getValue(), csv.getKey());
        table = new JTable(model);
    }

}