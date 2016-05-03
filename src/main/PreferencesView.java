package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.BorderLayout;

public class PreferencesView {

    private JFrame frame;
    private JTextField textinterval_retry_connection;
    private JTextField textinterval_retry_login;
    private JTextField textinterval_check_status;
    private JTextField textmax_times_retry_login;
    private JButton btnConfirm;
    private JButton btnResetToDefault;
    private JButton btnCancel;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PreferencesView window = new PreferencesView();
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
    public PreferencesView() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setResizable(false);
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        JPanel panel = new JPanel();
        panel.setBounds(92, 31, 234, 142);
        frame.getContentPane().add(panel);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        JLabel lblRetryConnectionAfter = new JLabel("Retry connection after ");
        panel.add(lblRetryConnectionAfter);
        
        textinterval_retry_connection = new JTextField();
        panel.add(textinterval_retry_connection);
        textinterval_retry_connection.setColumns(10);
        
        JLabel lblRetryLoginAfter = new JLabel("Retry login after ");
        panel.add(lblRetryLoginAfter);
        
        textinterval_retry_login = new JTextField();
        panel.add(textinterval_retry_login);
        textinterval_retry_login.setColumns(10);
        
        JLabel lblCheckStatusEvery = new JLabel("Check status every ");
        panel.add(lblCheckStatusEvery);
        
        textinterval_check_status = new JTextField();
        panel.add(textinterval_check_status);
        textinterval_check_status.setColumns(10);
        
        JLabel lblMaximumAttempts = new JLabel("Maximum login retries");
        panel.add(lblMaximumAttempts);
        
        textmax_times_retry_login = new JTextField();
        panel.add(textmax_times_retry_login);
        textmax_times_retry_login.setColumns(10);
        
        btnConfirm = new JButton("Confirm");
        btnConfirm.setBounds(298, 219, 93, 23);
        frame.getContentPane().add(btnConfirm);
        
        btnResetToDefault = new JButton("Restore default");
        btnResetToDefault.setBounds(160, 219, 128, 23);
        frame.getContentPane().add(btnResetToDefault);
        
        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(57, 219, 93, 23);
        frame.getContentPane().add(btnCancel);
    }
}
