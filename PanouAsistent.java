import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

class PanouAsistent extends JFrame  implements ListSelectionListener, ActionListener{
    JList<String> lista;
    JPanel tabel, p;
    Assistant copyAsistent;
    JButton btnValidate;
    JButton btnAdd;
    JTable jt;
    int k;
    DefaultListModel<String> el;
    ScoreVisitor scoreVisitor;
    String[][] data = new String[30][30];
    PanouAsistent(Assistant asistent, ScoreVisitor scoreVisitor){
        super(asistent.getFirstName() + " " + asistent.getLastName());
        this.scoreVisitor = scoreVisitor;
        copyAsistent = asistent;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(600,600));
        setLayout(new BorderLayout());

        el = new DefaultListModel<>();
        JScrollPane scrollPane = new JScrollPane();
        for(Course course : Catalog.getInstance().getCourses()){
            for(Assistant a : course.assistants)
                if(a.equals(asistent))
                    el.addElement(course.name);
        }

        lista = new JList<>(el);
        lista.setBackground(new Color(227, 239, 250));
        lista.setFont(new Font("Calibri", Font.PLAIN, 20));
        scrollPane.setViewportView(lista);
        lista.setLayoutOrientation(JList.VERTICAL);
        lista.addListSelectionListener(this);
        scrollPane.setPreferredSize(new Dimension(150,100));
        add(scrollPane, BorderLayout.WEST);

        p = new JPanel(new GridLayout(2,1));
        tabel = new JPanel(new BorderLayout());
        JPanel buttons = new JPanel(new GridLayout(2,1));
        JLabel text = new JLabel("Buna");
        btnValidate = new JButton("Valideaza");
        btnValidate.setOpaque(true);
        btnValidate.setBorderPainted(false);
        btnValidate.setForeground(Color.white);
        btnValidate.setBackground(new Color(100, 129, 204));
        btnValidate.setFont(new Font("Calibri", Font.PLAIN, 25));
        btnValidate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog d = new JDialog();
                JLabel l = new JLabel(" Validarea a fost facuta.");
                l.setFont(new Font("Calibri", Font.PLAIN, 18));
                Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
                int width = 500;
                int height = 200;
                d.setBounds(center.x - width / 2, center.y - height / 2, width, height);
                d.add(l);
                d.show();

                scoreVisitor.visit((Assistant) asistent);
            }
        });

        btnAdd = new JButton("Adauga");
        btnAdd.addActionListener(this);
        btnAdd.setEnabled(true);
        btnAdd.setOpaque(true);
        btnAdd.setBorderPainted(false);
        btnAdd.setBackground(new Color(178, 194, 235));
        btnAdd.setFont(new Font("Calibri", Font.PLAIN, 25));
        buttons.add(btnValidate);
        buttons.add(btnAdd);


        String column[]={"Nume", "Prenume","Nota Partial","Nota Exam", "Nota total"};
        jt=new JTable(data, column);
        jt.setFont(new Font("Calibri", Font.PLAIN, 13));
        jt.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        jt.setBackground(new Color(227, 239, 250));
        JScrollPane sp=new JScrollPane();
        sp.setViewportView(jt);
        sp.setPreferredSize(new Dimension(600, 400));
        tabel.setPreferredSize(new Dimension(500,500));
        tabel.add(sp, BorderLayout.CENTER);
        p.add(tabel);
        p.add(buttons);
        add(p, BorderLayout.CENTER);

        show();
        pack();
    }
    @Override
    public void valueChanged(ListSelectionEvent e) {
        revalidate();
        if (lista.isSelectionEmpty()) {
            return;
        }
        k = 0;
        String c = lista.getSelectedValue();
        for(Course course : Catalog.getInstance().getCourses()){
            if(course.getName().equals(c)){
                for(Grade g : course.getGrades()){
                    data[k][0] = g.getStudent().getFirstName();
                    data[k][1] = g.getStudent().getLastName();
                    data[k][2] = g.getPartialScore().toString();
                    data[k][3] = g.getExamScore().toString();
                    data[k][4] = g.getTotal().toString();
                    k++;
                }
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        UserFactory userFactory = new UserFactory();
        JDialog d = new JDialog(this, "Adaugare nota");
        JDialog d2 = new JDialog(this, "Adaugare nume");
        JLabel l = new JLabel();

        String nota = JOptionPane.showInputDialog(this,
                "Adauga o noua nota");

        String username = JOptionPane.showInputDialog(this,"Nume elev:");
        double notaDouble = Double.parseDouble(nota);
        if(notaDouble >= 0 && notaDouble <= 10) {
            l.setText("   Good!");
            String c = lista.getSelectedValue();
            String lastName = username.split(" ")[username.split(" ").length-1];
            String firstName = username.substring(0, username.length() - lastName.length());
            User newStudent = userFactory.getUser("STUDENT", firstName, lastName);
            scoreVisitor.addPartialScore((Assistant)copyAsistent, (Student) newStudent, c, notaDouble);
            for (Course course : Catalog.getInstance().getCourses()) {
                if (course.getName().equals(c)) {
                    course.addStudent("321CC", (Student) newStudent);
                }
            }
        }
        else l.setText("   Not good!");
        Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        int width = 500;
        int height = 200;
        d.setBounds(center.x - width / 2, center.y - height / 2, width, height);
        d2.setBounds(center.x - width / 2, center.y - height / 2, width, height);
        l.setFont(new Font("Calibri", Font.PLAIN, 30));
        d.add(l);
        d.setMinimumSize(new Dimension(200,200));
        d.setVisible(true);

    }
}

