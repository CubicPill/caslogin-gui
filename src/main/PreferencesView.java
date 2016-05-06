package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class PreferencesView {

	private JFrame frmPreferences;
	private JButton btnConfirm;
	private JButton btnResetToDefault;
	private JButton btnCancel;
	private JTextField textirc;
	private JTextField textirl;
	private JTextField textics;
	private JTextField textmtrl;
	private JTextField textURL;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PreferencesView window = new PreferencesView();
					window.frmPreferences.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PreferencesView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPreferences = new JFrame();
		frmPreferences.setTitle("Preferences");
		frmPreferences.setResizable(false);
		frmPreferences.setBounds(100, 100, 450, 300);
		frmPreferences.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frmPreferences.getContentPane().setLayout(null);

		btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String url = textURL.getText();
				String irc = textirc.getText();
				String irl = textirl.getText();
				String ics = textics.getText();
				String mtrl = textmtrl.getText();
				if (Config.validate(url, irc, irl, ics, mtrl)) {
					Config.updateConfig(url, irc, irl, ics, mtrl);
					frmPreferences.setVisible(false);
				} else {
					JOptionPane.showMessageDialog(null, "Invalid parameters.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnConfirm.setBounds(298, 219, 93, 23);
		frmPreferences.getContentPane().add(btnConfirm);

		btnResetToDefault = new JButton("Restore default");
		btnResetToDefault.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textURL.setText("http://www.v2ex.com/generate_204");
				textirc.setText("20");
				textirl.setText("10");
				textics.setText("300");
				textmtrl.setText("5");
			}
		});
		btnResetToDefault.setBounds(160, 219, 128, 23);
		frmPreferences.getContentPane().add(btnResetToDefault);

		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmPreferences.setVisible(false);
			}
		});
		btnCancel.setBounds(57, 219, 93, 23);
		frmPreferences.getContentPane().add(btnCancel);

		JLabel label = new JLabel("Retry connection after ");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(106, 67, 138, 15);
		frmPreferences.getContentPane().add(label);

		textirc = new JTextField();
		textirc.setColumns(10);
		textirc.setBounds(249, 64, 66, 21);
		frmPreferences.getContentPane().add(textirc);

		JLabel label_1 = new JLabel("Retry login after ");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setBounds(106, 98, 138, 15);
		frmPreferences.getContentPane().add(label_1);

		textirl = new JTextField();
		textirl.setColumns(10);
		textirl.setBounds(249, 95, 66, 21);
		frmPreferences.getContentPane().add(textirl);

		JLabel label_2 = new JLabel("Check status every ");
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setBounds(106, 130, 138, 15);
		frmPreferences.getContentPane().add(label_2);

		textics = new JTextField();
		textics.setColumns(10);
		textics.setBounds(249, 127, 66, 21);
		frmPreferences.getContentPane().add(textics);

		JLabel label_3 = new JLabel("Maximum login retries");
		label_3.setHorizontalAlignment(SwingConstants.RIGHT);
		label_3.setBounds(106, 161, 138, 15);
		frmPreferences.getContentPane().add(label_3);

		textmtrl = new JTextField();
		textmtrl.setColumns(10);
		textmtrl.setBounds(249, 158, 66, 21);
		frmPreferences.getContentPane().add(textmtrl);

		textURL = new JTextField();
		textURL.setBounds(95, 25, 339, 21);
		frmPreferences.getContentPane().add(textURL);
		textURL.setColumns(10);

		JLabel lblTestPage = new JLabel("Test Page");
		lblTestPage.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTestPage.setBounds(10, 28, 75, 15);
		frmPreferences.getContentPane().add(lblTestPage);

		JLabel lblSec = new JLabel("sec");
		lblSec.setBounds(325, 67, 54, 15);
		frmPreferences.getContentPane().add(lblSec);

		JLabel label_4 = new JLabel("sec");
		label_4.setBounds(325, 98, 54, 15);
		frmPreferences.getContentPane().add(label_4);

		JLabel label_5 = new JLabel("sec");
		label_5.setBounds(325, 130, 54, 15);
		frmPreferences.getContentPane().add(label_5);

		HashMap<String, String> config = Config.readConfig();
		textirc.setText(config.get("interval_retry_connection"));
		textirl.setText(config.get("interval_retry_login"));
		textics.setText(config.get("interval_check_status"));
		textmtrl.setText(config.get("max_times_retry_login"));
		textURL.setText(config.get("testUrl"));
	}
}
