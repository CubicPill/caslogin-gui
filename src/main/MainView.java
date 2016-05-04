package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.event.ActionListener;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Font;

public class MainView {

    private JFrame frame;
    public static JTextArea textArea;

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
        frame.setBounds(100, 100, 614, 363);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 22, 608, 315);
        frame.getContentPane().add(scrollPane);

        textArea = new JTextArea();
        textArea.setEnabled(false);
        scrollPane.setViewportView(textArea);
        textArea.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 13));
        textArea.setTabSize(4);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setForeground(Color.WHITE);
        textArea.setBackground(Color.BLACK);
        OPS ops = new OPS();

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBounds(0, 0, 608, 21);
        frame.getContentPane().add(menuBar);

        JMenu mnOPS = new JMenu("OPS");
        menuBar.add(mnOPS);

        JMenuItem mntmStart = new JMenuItem("Start");
        mntmStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                ops.opsStart();
            }
        });
        mnOPS.add(mntmStart);

        JMenuItem mntmCheckNow = new JMenuItem("Check Now");
        mntmCheckNow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ops.opsCheckNow();
            }
        });
        mnOPS.add(mntmCheckNow);

        JMenuItem mntmPause = new JMenuItem("Pause");
        mntmPause.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ops.opsPause();
            }
        });
        mnOPS.add(mntmPause);

        JMenuItem mntmExit = new JMenuItem("Exit");
        mntmExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        mnOPS.add(mntmExit);

        JMenu mnSettings = new JMenu("Settings");
        menuBar.add(mnSettings);

        JMenuItem mntmAccount = new JMenuItem("Account");
        mntmAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String args[] = {};
                LoginView.main(args);
            }
        });
        mnSettings.add(mntmAccount);

        JMenuItem mntmPreferences = new JMenuItem("Preferences");
        mntmPreferences.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String args[] = {};
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

    }

    public static void print(String r) {
        r = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss.SSS] ").format(new Date()) + r;
        textArea.append(r);
        textArea.append("\n");
    }

}
