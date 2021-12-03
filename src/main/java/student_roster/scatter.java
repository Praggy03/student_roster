package student_roster;

import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;
import java.nio.*;

import com.opencsv.CSVReader;

import org.jfree.chart.ui.*;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.plot.PlotOrientation;
import student_roster.actor.Attendee;
import student_roster.attendance.AttendanceMatcher;


public class scatter {


    static Integer[][][] calculatePercentageArray(JTable a, int count, int columnLength){
        int total=75;
        Integer counter[][][] = new Integer[columnLength-4][count][2];

        for(int f=0 ; f<columnLength-4 ; f++) {

            for(int i=0 ; i<a.getRowCount() ; i++) {

                int percent = Integer.parseInt((String) a.getValueAt(i,4+f))*100/total;
                if(percent>100){
                    percent = 100;
                }

                counter[f][i][0] = percent;
                if(counter[f][i][1]==null){
                    counter[f][i][1]=0;
                }
                counter[f][i][1]++;
            }
        }

        System.out.println("Counter array is : "+Arrays.deepToString(counter));
        return counter;
    }

    // Function which merges counts of each attendance percentage
    static Integer[][][] mergeCountArray(Integer[][][] a, int count, int columnLength){
        Integer finalArray[][][] = new Integer[columnLength-4][count][2];

        //Merging counts of each percentage
        for(int f=0 ; f<columnLength-4 ; f++) {

            int mergeCount = 0;
            for(int j=0;j<count;j++){
                mergeCount = 0;
                for(int k=0;k<count;k++){
                    if(a[f][j][0]==a[f][k][0]){
                        mergeCount++;
                    }
                }
                finalArray[f][j][0] = a[f][j][0];
                finalArray[f][j][1] = mergeCount;
            }
        }
        System.out.println("Final Array is : "+Arrays.deepToString(finalArray));
        return finalArray;
    }



    public static void scatterPlot() {

        // Get Merged Attendee List from AttendanceMatcher.java
         List<Attendee> newList = AttendanceMatcher.getAttendanceMatcher().getMergedAttendees();
         System.out.println("newList"+newList);

        //CSV Reader
        String path = "src/RosterAttendance.csv";
        String line = "";
        String[] values = {};

        List<List<String>> records = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                values = line.split(",");
                records.add(Arrays.asList(values));
            }
            System.out.println(records);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }//End of CSV Reader



        int total = 75; //total time of each class

        JButton btnPlotData = new JButton("Plot Data");
        String[] columns = {"Last Name","Program","Level", "ASURITE","Nov10","Nov15","Nov21","Dec1"};
        Object[][] data = {
                {"Spade","CS","Grad","1222232332","78","22","21","68"},
                {"Martinez","CS","Grad","1522232332","60","72","21","68"},
                {"Martinez","CS","Grad","1522232332","60","72","21","68"},
                {"Bob","CS","Grad","1222332332","10","72","21","68"},
                {"Martinez","CS","Grad","1522232332","20","62","21","68"},
                {"Martinez","CS","Grad","1522232332","20","42","21","68"},
                {"Martinez","CS","Grad","1522232332","20","42","21","68"}
        };

        JTable jTable1 = new JTable(data, columns);
        int count = data.length;
        int columnLength = data[0].length;

        Integer counter[][][] = new Integer[columnLength-4][count][2];
        Integer finalArray[][][] = new Integer[columnLength-4][count][2];

        counter = calculatePercentageArray(jTable1, count, columnLength); //Call function which calculates percentage and makes the array

        finalArray = mergeCountArray(counter, count, columnLength); //Call function which merges count array


        // Work on load roaster + attendance combined CSV....plot
        XYSeriesCollection dataset = new XYSeriesCollection();
        String key[] = new String[columnLength-4];

        for(int f=0 ; f<columnLength-4 ; f++) {
            if(f==0) {
                XYSeries series1 = new XYSeries(columns[f + 4]);
                for (int l = 0; l < count; l++) {
                    series1.add(finalArray[f][l][0], finalArray[f][l][1]);
                }
                dataset.addSeries(series1);
            }
            if(f==1) {
                XYSeries series2 = new XYSeries(columns[f + 4]);
                for (int l = 0; l < count; l++) {
                    series2.add(finalArray[f][l][0], finalArray[f][l][1]);
                }
                dataset.addSeries(series2);
            }
            if(f==2) {
                XYSeries series3 = new XYSeries(columns[f + 4]);
                for (int l = 0; l < count; l++) {
                    series3.add(finalArray[f][l][0], finalArray[f][l][1]);
                }
                dataset.addSeries(series3);
            }
            if(f==3) {
                XYSeries series4 = new XYSeries(columns[f + 4]);
                for (int l = 0; l < count; l++) {
                    series4.add(finalArray[f][l][0], finalArray[f][l][1]);
                }
                dataset.addSeries(series4);
            }
            if(f==4) {
                XYSeries series5 = new XYSeries(columns[f + 4]);
                for (int l = 0; l < count; l++) {
                    series5.add(finalArray[f][l][0], finalArray[f][l][1]);
                }
                dataset.addSeries(series5);
            }
            if(f==5) {
                XYSeries series6 = new XYSeries(columns[f + 4]);
                for (int l = 0; l < count; l++) {
                    series6.add(finalArray[f][l][0], finalArray[f][l][1]);
                }
                dataset.addSeries(series6);
            }
            if(f==6) {
                XYSeries series7 = new XYSeries(columns[f + 4]);
                for (int l = 0; l < count; l++) {
                    series7.add(finalArray[f][l][0], finalArray[f][l][1]);
                }
                dataset.addSeries(series7);
            }
            if(f==7) {
                XYSeries series8 = new XYSeries(columns[f + 4]);
                for (int l = 0; l < count; l++) {
                    series8.add(finalArray[f][l][0], finalArray[f][l][1]);
                }
                dataset.addSeries(series8);
            }
            if(f==8) {
                XYSeries series9 = new XYSeries(columns[f + 4]);
                for (int l = 0; l < count; l++) {
                    series9.add(finalArray[f][l][0], finalArray[f][l][1]);
                }
                dataset.addSeries(series9);
            }
            if(f==10) {
                XYSeries series10 = new XYSeries(columns[f + 4]);
                for (int l = 0; l < count; l++) {
                    series10.add(finalArray[f][l][0], finalArray[f][l][1]);
                }
                dataset.addSeries(series10);
            }
        }


        JFreeChart scatterPlot = ChartFactory.createScatterPlot(
                "Attendance Scatter Plot", // Chart title
                "Percentage", // X-Axis Label
                "Count", // Y-Axis Label
                dataset // Dataset for the Chart
        );

        //ChartUtilities.saveChartAsPNG(new File("C://Users/Abhinav/scatterplot.png"), scatterPlot, 600, 400);


        ChartFrame frame2= new ChartFrame("Plot Chart", scatterPlot);
        frame2.setVisible(true);
        frame2.setSize(800, 400);
        frame2.setTitle("Attendence Statistics - Scatter Plot");
        frame2.setResizable(false);
        frame2.setLocationRelativeTo(null);
        //frame2.setLayout( new FlowLayout() );

    }
}
