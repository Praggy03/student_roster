package student_roster;

import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Savedata {


        private static JFrame frame;
        public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Savedata window = new Savedata();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
	public Savedata() {
        initialize();
    }
        public static void initialize() {


            String[] columns = {"Name","Age","Gender"};
            Object[][] data = {
                    {"Jasvith","21","Male"},
                    {"Chetan","21","Male"},
                    {"Jaanu","21","Female"}
            };

            JTable jTable1 = new JTable(data, columns);
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a save file");
            int userSelection = fileChooser.showSaveDialog(frame);
            if(userSelection == JFileChooser.APPROVE_OPTION){
                File fileToSave = fileChooser.getSelectedFile();
                try {
                    FileWriter fw = new FileWriter(fileToSave);
                    BufferedWriter bw = new BufferedWriter(fw);
                    for (int i = 0; i<jTable1.getRowCount(); i++) {
                        for (int j = 0; j<jTable1.getColumnCount(); j++) {
                            bw.write(jTable1.getValueAt(i, j).toString()+",");
                        }
                        bw.newLine();
                    }
                    JOptionPane.showMessageDialog(frame, "SAVED SUCCESSFULLY","INFORMATION",JOptionPane.INFORMATION_MESSAGE);
                    bw.close();
                    fw.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "ERROR","ERROR MESSAGE",JOptionPane.ERROR_MESSAGE);
                }


            }
        }

}
