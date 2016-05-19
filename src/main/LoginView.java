package main;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.awt.event.ActionEvent;

public class LoginView {

    private JFrame frame;
    private JTextField text_usn;
    private JPasswordField text_pwd;

    public LoginView() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setIconImages(Exec.icons);
        frame.setResizable(false);
        frame.setBounds(100, 100, 252, 199);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
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
                String username = text_usn.getText();
                String passwd = new String(text_pwd.getPassword());
                if (Config.updateConfig(username, passwd))
                    frame.setVisible(false);

            }
        });
        btnConfirm.setBounds(80, 101, 93, 23);
        frame.getContentPane().add(btnConfirm);

        HashMap<String, String> config = Config.readConfig();
        text_usn.setText(config.get("username"));
        text_pwd.setText(config.get("password"));
        frame.setVisible(true);
    }
}
