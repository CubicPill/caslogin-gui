package main;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.awt.AWTException;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;

public class MainView extends JFrame {


    private static final long serialVersionUID = 1L;
    private static JFrame frmCasAutoLogin;
    public static JTextArea textArea;
    static TrayIcon trayIcon = null;
    static SystemTray tray = null;

    public MainView() {
        initialize();
    }

    private void initialize() {
        if (SystemTray.isSupported()) {
            this.tray();
        }
        frmCasAutoLogin = new JFrame();
        frmCasAutoLogin.setIconImages(Exec.icons);
        frmCasAutoLogin.setTitle("CAS auto login: Not running");
        frmCasAutoLogin.setResizable(false);
        frmCasAutoLogin.setBounds(100, 100, 614, 360);
        frmCasAutoLogin.getContentPane().setLayout(null);
        frmCasAutoLogin.setDefaultCloseOperation(EXIT_ON_CLOSE);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 22, 608, 315);
        frmCasAutoLogin.getContentPane().add(scrollPane);

        textArea = new JTextArea();
        textArea.setEnabled(false);
        scrollPane.setViewportView(textArea);
        textArea.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 13));
        textArea.setTabSize(4);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setForeground(Color.WHITE);
        textArea.setBackground(Color.BLACK);
        OPS ops = new OPS();

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBounds(0, 0, 608, 21);
        frmCasAutoLogin.getContentPane().add(menuBar);

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

        JMenuItem mntmExit = new JMenuItem("Exit");
        mntmExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JMenuItem mntmHide = new JMenuItem("Hide");
        mntmHide.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    System.out.println("dasdsd");
                    tray.add(trayIcon);
                    frmCasAutoLogin.setVisible(false);
                    frmCasAutoLogin.dispose();
                } catch (AWTException ex) {
                    System.exit(ERROR);
                }
            }
        });
        mnOPS.add(mntmHide);
        mnOPS.add(mntmExit);

        JMenu mnSettings = new JMenu("Settings");
        menuBar.add(mnSettings);

        JMenuItem mntmAccount = new JMenuItem("Account");
        mntmAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                new LoginView();
            }
        });
        mnSettings.add(mntmAccount);

        JMenuItem mntmPreferences = new JMenuItem("Preferences");
        mntmPreferences.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new PreferencesView();
            }
        });

        mnSettings.add(mntmPreferences);

        JMenu mnHelp = new JMenu("Help");
        menuBar.add(mnHelp);

        JMenuItem mntmHelp = new JMenuItem("Help");
        mntmHelp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Nothing here :P", "Help", JOptionPane.INFORMATION_MESSAGE);

            }
        });
        mnHelp.add(mntmHelp);

        JMenuItem mntmAbout = new JMenuItem("About");
        mntmAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                JOptionPane.showMessageDialog(null,
                        "Source code availible here\nhttps://github.com/NorrisHua/caslogin-gui/", "About",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        mnHelp.add(mntmAbout);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    tray.add(trayIcon);
                    frmCasAutoLogin.setVisible(false);
                    frmCasAutoLogin.dispose();
                } catch (AWTException ex) {
                    System.exit(ERROR);
                }
            }
        });
        frmCasAutoLogin.setVisible(true);
    }

    public static void print(String r) {
        r = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss.SSS] ").format(new Date()) + r;
        textArea.append(r);
        textArea.append("\n");
        textArea.setCaretPosition(textArea.getText().length()); // auto scroll
                                                                // to the bottom
    }

    public static void printerr(String r) { // Unfinished. Print error message
                                            // in red color.
        r = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss.SSS] ").format(new Date()) + r;
        textArea.append(r);
        textArea.append("\n");
    }

    public static void setTitle(boolean running) {
        if (running) {
            frmCasAutoLogin.setTitle("CAS auto login: Running");
        } else {
            frmCasAutoLogin.setTitle("CAS auto login: Not running");
        }
    }

    void tray() {
        tray = SystemTray.getSystemTray();
        ImageIcon icon = new ImageIcon("logo.png");
        PopupMenu pop = new PopupMenu();
        MenuItem show = new MenuItem("Show Window");
        MenuItem exit = new MenuItem("Exit");
        pop.add(show);
        pop.add(exit);
        trayIcon = new TrayIcon(icon.getImage(), "caslogin-gui", pop);
        trayIcon.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) // mouse double click
                {
                    tray.remove(trayIcon);
                    frmCasAutoLogin.setExtendedState(JFrame.NORMAL);
                    frmCasAutoLogin.setVisible(true);
                    frmCasAutoLogin.toFront();
                }
            }
        });
        show.addActionListener(new ActionListener() // show window
        {
            public void actionPerformed(ActionEvent e) {
                tray.remove(trayIcon); // remove tray icon
                frmCasAutoLogin.setExtendedState(JFrame.NORMAL);
                frmCasAutoLogin.setVisible(true);
                frmCasAutoLogin.toFront();
            }
        });
        exit.addActionListener(new ActionListener() // close the program
        {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}
