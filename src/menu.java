import java.awt.*;
import javax.swing.*;        

public class menu {

    public static void createAndShowGUI() {
        //Fenster
        JFrame frame = new JFrame("DC");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        frame.setVisible(true);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.BLACK);
        
        
        //Buttons
                
        JButton start = new JButton("Start");
        start.setBounds(200, 75, 100, 100);
        start.addActionListener(null);
        frame.add(start);
        
        JButton einst = new JButton("Einstellungen");
        einst.setBounds(200,175,100,100);
        einst.addActionListener(null);
        frame.add(einst);     
        
        JButton ende = new JButton("Ende");
        ende.setBounds(200,275,100,100);
        ende.addActionListener(null);
        frame.add(ende);             

    }

    public static void main(String[] args) {

                createAndShowGUI();
            }}