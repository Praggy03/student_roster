import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class gui_1 {

	private JFrame frame;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gui_1 window = new gui_1();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public gui_1() {
		initialize();
	}
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel l = new JLabel("Java Swing Sucks This is Outdated as fuck");
		l.setBounds(100,150,300,100);
		frame.add(l);
		frame.getContentPane().setLayout(null);
		JButton btnNewButton = new JButton("Save Data");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		});
		btnNewButton.setBounds(165, 116, 117, 29);
		frame.getContentPane().add(btnNewButton);
	}
}
