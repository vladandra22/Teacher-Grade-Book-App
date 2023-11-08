import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginPage extends JFrame implements ActionListener {
    private JLabel title;
    private JTextField email;
    private JPasswordField password;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JButton loginButton;
    ScoreVisitor scoreVisitor;
    User currentUser;
    HashMap<User, LoginCredentials> cpyDataBase;
    public LoginPage( HashMap<User, LoginCredentials> dataBase, ScoreVisitor scoreVisitor){
        super("Login");
        cpyDataBase = dataBase;
        this.scoreVisitor = scoreVisitor;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(200, 200));
        getContentPane().setBackground(new Color(227, 239, 250));
        setLayout(new BorderLayout());

        this.title = new JLabel("<html><p><br>Catalogul meu electronic<br><br></p></html>");
        email = new JTextField();
        password = new JPasswordField();
        emailLabel = new JLabel("Email: ");
        passwordLabel = new JLabel("Password: ");
        loginButton = new JButton("Login");

        this.title.setFont(new Font("Calibri", Font.BOLD, 20));
        email.setColumns(30);
        password.setColumns(30);

        JPanel emailPanel = new JPanel();
        emailPanel.add(emailLabel);
        emailPanel.add(email);

        JPanel passwordPanel = new JPanel();
        passwordPanel.add(passwordLabel);
        passwordPanel.add(password);

        JPanel loginPanel = new JPanel(new GridLayout(2, 1));
        loginPanel.add(emailPanel);
        loginPanel.add(passwordPanel);

        loginButton.addActionListener(this);

        add(this.title, BorderLayout.NORTH);
        add(loginPanel, BorderLayout.CENTER);
        add(loginButton, BorderLayout.SOUTH);

        this.email.addActionListener(this);
        this.password.addActionListener(this);

        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width  - getSize().width) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);
        show();
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        LoginCredentials currCredentials = new LoginCredentials();
        currCredentials.setEmail(this.email.getText());
        currCredentials.setPassword(this.password.getText());

           /* UserFactory userFactory = new UserFactory();
            currentUser = userFactory.getUser("","","");*/
        boolean ok = false;
        for (Map.Entry<User, LoginCredentials> entry : cpyDataBase.entrySet()) {
            if (entry.getValue().getEmail().equals(this.email.getText()) &&
                    entry.getValue().getPassword().equals(this.password.getText())) {
                ok = true;
                if (entry.getKey().getClass().toString().equals("class Student")) {
                    PanouStudent panel = new PanouStudent((Student) entry.getKey());
                    setVisible(false);
                    dispose();

                } else if (entry.getKey().getClass().toString().equals("class Parent")) {
                    PanouParent panel = new PanouParent((Parent) entry.getKey(), scoreVisitor);
                    setVisible(false);
                    dispose();
                } else if (entry.getKey().getClass().toString().equals("class Assistant")) {
                    PanouAsistent panel = new PanouAsistent((Assistant) entry.getKey(), scoreVisitor);
                    setVisible(false);
                    dispose();
                } else if (entry.getKey().getClass().toString().equals("class Teacher")) {
                    PanouTeacher panel = new PanouTeacher((Teacher) entry.getKey(), scoreVisitor);
                    setVisible(false);
                    dispose();
                } else
                    this.title.setText("This user does not exist!");
            }
            if(!ok)
                this.title.setText("This user does not exist!");

        }
    }

}