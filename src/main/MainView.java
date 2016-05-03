package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JTextField;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.Box;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;

public class MainView {

    private JFrame frame;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainView window = new MainView();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public MainView() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setResizable(false);
        frame.setBounds(100, 100, 450, 363);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBounds(0, 0, 434, 21);
        frame.getContentPane().add(menuBar);
        
        JMenu mnOPS = new JMenu("OPS");
        menuBar.add(mnOPS);
        
        JMenuItem mntmStart = new JMenuItem("Start");
        mnOPS.add(mntmStart);
        
        JMenuItem mntmCheckNow = new JMenuItem("Check Now");
        mnOPS.add(mntmCheckNow);
        
        JMenuItem mntmPause = new JMenuItem("Pause");
        mnOPS.add(mntmPause);
        
        JMenuItem mntmExit = new JMenuItem("Exit");
        mnOPS.add(mntmExit);
        
        JMenu mnSettings = new JMenu("Settings");
        menuBar.add(mnSettings);
        
        JMenuItem mntmAccount = new JMenuItem("Account");
        mntmAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String args[]={};
                LoginView.main(args);
            }
        });
        mnSettings.add(mntmAccount);
        
        JMenuItem mntmPreferences = new JMenuItem("Preferences");
        mntmPreferences.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String args[]={};
                PreferencesView.main(args);
            }
        });
        mnSettings.add(mntmPreferences);
        
        JMenu mnHelp = new JMenu("Help");
        menuBar.add(mnHelp);
        
        JMenuItem mntmHelp = new JMenuItem("Help");
        mnHelp.add(mntmHelp);
        
        JMenuItem mntmAbout = new JMenuItem("About");
        mnHelp.add(mntmAbout);
        
        JTextPane textPane = new JTextPane();
        textPane.setBackground(Color.BLACK);
        textPane.setForeground(Color.WHITE);
        textPane.setBounds(0, 22, 427, 315);
        frame.getContentPane().add(textPane);
        
        JScrollBar scrollBar = new JScrollBar();
        scrollBar.setBounds(427, 22, 17, 315);
        frame.getContentPane().add(scrollBar);
    }
}
