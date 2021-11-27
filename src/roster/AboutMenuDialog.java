package roster;

import javax.swing.JOptionPane;

public class AboutMenuDialog extends JDialog {

    public AboutMenuDialog() {}

    public void show() {

        JFrame frame = new JFrame("About: Team Information");
        JOptionPane.showMessageDialog(frame, "CSE 563 - Assignment 5 Team \n Abhinav Venepally \n Jasvith \n Pooja Kulkarni \n Pragadeeshkumar Rajavel ", JOptionPane.PLAIN_MESSAGE);
    }

}