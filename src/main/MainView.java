package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Font;

public class MainView {

	private static JFrame frmCasAutoLogin;
	public static JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new MainView();
					frmCasAutoLogin.setVisible(true);
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
		frmCasAutoLogin = new JFrame();
		frmCasAutoLogin.setTitle("CAS auto login: Not running");
		frmCasAutoLogin.setResizable(false);
		frmCasAutoLogin.setBounds(100, 100, 614, 363);
		frmCasAutoLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCasAutoLogin.getContentPane().setLayout(null);

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
}
