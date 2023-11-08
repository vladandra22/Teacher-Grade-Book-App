import javax.management.NotificationFilter;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class PanouParent  extends JFrame{
    Parent copyParent;
    JToggleButton[] buttons;
    ScoreVisitor scoreVisitor;
    PanouParent(Parent parent, ScoreVisitor scoreVisitor){
        super(parent.getFirstName() + " " + parent.getLastName());
        copyParent = parent;
        this.scoreVisitor = scoreVisitor;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(600,600));
        setLayout(new GridLayout(2,1));
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(new EmptyBorder(10, 20, 10, 10));

        LinkedList<Notification> allNotifs = Catalog.getInstance().getNotifications();
        LinkedList<Notification> thisParentNotifs = new LinkedList<>();
        for(Notification notif : allNotifs){
            if(notif.getParent().equals(parent))
                thisParentNotifs.add(notif);
        }

        String s = "<html><p>Buna ziua, domnul/doamna " + copyParent.getFirstName() + " " + copyParent.getLastName() + "!<br>Aveti " + thisParentNotifs.toArray().length + " notificari in legatura cu copilul dvs.</p></html>";
        JLabel text1 = new JLabel(s, SwingConstants.CENTER);
        text1.setFont(new Font("Calibri", Font.PLAIN, 20));
        p.add(text1);
        p.setBackground(new Color(227, 239, 250));
        add(p, BorderLayout.CENTER);



        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(new Color(227, 239, 250));
        buttons = new JToggleButton[thisParentNotifs.toArray().length];
        int i;
        for (i = 0; i < buttons.length; i++) {
            buttons[i] = new JToggleButton("Deschide!");
            buttons[i].setFont(new Font("Calibri", Font.PLAIN, 20));
            int index = i;
            buttons[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JDialog d = new JDialog();
                    JLabel l = new JLabel(thisParentNotifs.get(index).toString());
                    l.setFont(new Font("Calibri", Font.PLAIN, 18));
                    Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
                    int width = 900;
                    int height = 200;
                    d.setBounds(center.x - width / 2, center.y - height / 2, width, height);
                    d.add(l);
                    d.show();
                    buttons[index].setText("Deschis!");
                }
            });
            buttons[i].setEnabled(true);
            buttonsPanel.add(buttons[i]);
        }
        add(buttonsPanel);
        show();
        pack();
    }

}
