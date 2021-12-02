package student_roster;

import javax.swing.filechooser.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JTable;
import com.opencsv.*;
import com.opencsv.exceptions.CsvValidationException;
import javax.swing.JOptionPane;
import java.io.*;

public class AttendanceModel {


    private DefaultTableModel model;
    public JTable table;
    private CSVReader csvReader;
    private Object[] header;
    private String[] row;

    public JTable loadData(File file) {

        try {
            csvReader = new CSVReader(new FileReader(file));
        } catch (FileNotFoundException fe) {
            errorDialog("File not found!", "File Error");
            return null;
        }

        try {
            header = (String[]) csvReader.readNext();
        } catch (CsvValidationException ce) {
            errorDialog("Invalid csv file uploaded", "Invalid CSV");
            return null;
        } catch (IOException ie) {
            errorDialog("I/O Exception", "IO Error");
            return null;
        }

        model = new DefaultTableModel();

        try {
            while((row = csvReader.readNext()) != null) {
                model.addRow(row);
            }
        } catch (Exception e) {
            errorDialog("An error occured while processing your CSV", "Fatal!");
            return null;
        }

        table = new JTable(model);
        table.setModel(model);
        return table;
    }

    public void errorDialog(String message, String title) {
        JOptionPane.showMessageDialog(ApplicationPage.jFrame, message, title, JOptionPane.ERROR_MESSAGE);
    }

}
