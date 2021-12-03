package student_roster.util;

import student_roster.load.ApplicationPage;

import javax.swing.*;
import java.awt.*;

public class Utils {

    /**
     * This method wraps the string contained by the string builder in HTML
     * This is done so that all "\n", i.e. next line characters can be replaced
     * by \<br\> and the dialog lines are displayed one after the other
     *
     * @param text is the sting builder text to be wrapped with HTML
     * @return the wrapped string
     */
    public static String wrapInHtml(StringBuilder text) {
        text.insert(0, "<html><body><p>");
        text.append("</p></body></html>");
        return text.toString().replace("\n", "<br>");
    }

    public static void displayLoadRosterDialog(String errorDialog, String title) {
        JDialog dialog = new JDialog(ApplicationPage.jFrame, title, true);
        dialog.setLayout(new FlowLayout());
        dialog.setVisible(false);
        StringBuilder builder = new StringBuilder(errorDialog);
        dialog.add(new JLabel(Utils.wrapInHtml(builder)));
        dialog.setBounds(
                new Rectangle(ApplicationPage.jFrame.getWidth() / 2 - 300, ApplicationPage.jFrame.getHeight() / 2 - 250, 600, 300)
        );
        dialog.setVisible(true);
    }


}