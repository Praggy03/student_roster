package student_roster;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import com.opencsv.*;

public class Utils {

    String row1;
    List<List<String>> rowsData = new ArrayList<>();
    List<String> vectorRows = new ArrayList<>();
    List<String> vectorHeaders = new ArrayList<>();
    String[] headers;

    class Result {
        List<String> headers;
        List<List<String>> data;
    }


    public Result readCsv(File file) {
        try {
            FileReader fileRead = new FileReader(file);
            BufferedReader buffRead = new BufferedReader(fileRead);

            String row1 = buffRead.readLine().trim();

            if (row1 != null) {
                headers = row1.split(",");
                vectorHeaders = new Vector<String>();
                for(int i=0; i<headers.length;i++) {
                    vectorHeaders.add(headers[i]);
                }
            }

            Object[] dataRows = buffRead.lines().toArray();
            for (int i = 0; i < dataRows.length; i++) {
                String line = dataRows[i].toString().trim();
                String[] dataRow = line.split(",");
                vectorRows = new Vector<String>();
                for (int j =0; j < dataRow.length; j++) {
                    vectorRows.add(dataRow[j]);
                }
                rowsData.add(vectorRows);
            }

        } catch (IOException io) {
            System.out.println("Error in input file:"+ io.getMessage());
        } catch (Exception e) {
            System.out.println("Unknown error while reading input file"+e.getMessage());
        }
        var r = new Result();
        r.headers = vectorHeaders;
        r.data = rowsData;

        return r;
    }

}