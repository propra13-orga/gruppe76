import java.awt.*;

import javax.swing.*; 
import java.awt.event.*;

class menu extends JFrame implements ActionListener {
	 
    JButton start;
    JButton einst;
    JButton ende;
    JButton blabber;

    	
   public menu() {
     
    	//Fenster
        this.setTitle("Menü");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,500);
        this.setVisible(true);
        this.setLayout(null);
	
        // Hintergrund
        this.setContentPane(new JLabel(new ImageIcon(("img/geisthintergrund.png"))));
        this.pack();
              
        //Buttons
        start = new JButton("Start");
        start.setBounds(100, 275, 100, 50 );
        start.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent e) {
        start();
        }});
        this.add(start);
        
        einst = new JButton("Einstellungen");
        einst.setBounds(200,275,100,50);
        einst.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent e) {
        addbutton();
        }});       
        this.add(einst);
        
        
        ende = new JButton("Ende");
        ende.setBounds(300,275,100,50);
        ende.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent e) {
        System.exit(0);
        }});
        this.add(ende); 

    }


   public void addbutton()
   {
	   blabber = new JButton("Blabber");
	   blabber.setBounds(200,0,100,50);
	   this.add(blabber);
   }
   
   public void start()
   {
	   JFrame spiel = new JFrame("Spiel");
       spiel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       spiel.setSize(500,500);
       spiel.setVisible(true);
       this.setVisible(false);
   }
   
   
    public static void main(String[] args)
    {
         menu a = new menu();
         a.setVisible(true);
    }


	public void actionPerformed(ActionEvent buttons) {
	}}