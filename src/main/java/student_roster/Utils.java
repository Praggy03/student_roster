package com.roster;

import java.io.*;

import com.opencsv.*;

public class Utils {

    String row1;
    Vector<Vector<String>> rowsData = new Vector<Vector<String>>();
    Vector<String> vectorRows = new Vector<String>();
    Vector<String> vectorHeaders = new Vector<String>();
    String[] headers;


    public static Pair<Vector<String>, Vector<Vector<String>>> readCsv(File file) {
        try {
            FileReader fileRead = new FileReader(file);
            BufferReader buffRead = new BufferReader(fr);

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
            System.out.pritln("Unknown error while reading input file"+e.getMessage());
        }
        return new Pair<Vector<String>, Vector<Vector<String>>>(vectorHeaders, rowsData);
    }

}