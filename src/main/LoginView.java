package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginView {

    private JFrame frame;
    private JTextField text_usn;
    private JPasswordField text_pwd;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginView window = new LoginView();
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
    public LoginView() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setResizable(false);
        frame.setBounds(100, 100, 252, 199);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        text_usn = new JTextField();
        text_usn.setBounds(122, 45, 66, 21);
        frame.getContentPane().add(text_usn);
        text_usn.setColumns(10);
        
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setBounds(45, 48, 67, 15);
        frame.getContentPane().add(lblUsername);
        
        text_pwd = new JPasswordField();
        text_pwd.setBounds(122, 70, 66, 21);
        frame.getContentPane().add(text_pwd);
        text_pwd.setColumns(10);
        
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(45, 73, 67, 15);
        frame.getContentPane().add(lblPassword);
        
        JButton btnConfirm = new JButton("Confirm");
        btnConfirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                username= text_usn.getText();
                passwd = new String(text_pwd.getPassword());
                Network.updateConfig(username, passwd);
                frame.setVisible(false);
                

            }
        });
        btnConfirm.setBounds(80, 101, 93, 23);
        frame.getContentPane().add(btnConfirm);
    }
}
