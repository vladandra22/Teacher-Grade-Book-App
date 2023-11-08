import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

class PanouStudent extends JFrame implements ListSelectionListener{
    JList<String> lista;
    Student copyStudent;
    JTextField titular;
    JTextField grupa;
    JTextField asistent;
    JTextField notaExam;
    JTextField notaPartial;
    JTextField notaTotal;
    DefaultListModel<String> asistenti;
    JButton btn;
    DefaultListModel<String> el;
    PanouStudent(Student student){
        super(student.getFirstName()+ " " + student.getLastName());
        copyStudent = student;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(600,700));
        setLayout(new BorderLayout());

        Color myBlue = new Color(227, 239, 250);
        Font veryBigFont = new Font("Calibri", Font.BOLD, 30);
        Font bigFont = new Font("Calibri", Font.BOLD, 20);
        Font mediumFont = new Font("Calibri", Font.BOLD, 14);

        el = new DefaultListModel<>();
        JScrollPane scrollPane = new JScrollPane();
        for(Course course : Catalog.getInstance().getCourses()){
            System.out.println(course.name);
            for(Student s : course.getAllStudents()) {
                System.out.println(s);
                if (s.getFirstName().equals(copyStudent.getFirstName()) && s.getLastName().equals(copyStudent.getLastName()))
                    el.addElement(course.name);
            }
        }

        lista = new JList<>(el);
        lista.setFont(bigFont);
        scrollPane.setViewportView(lista);
        lista.setLayoutOrientation(JList.VERTICAL);
        lista.addListSelectionListener(this);
        scrollPane.setPreferredSize(new Dimension(150,100));
        add(scrollPane, BorderLayout.WEST);

        JPanel p = new JPanel(new FlowLayout());
        p.setPreferredSize(new Dimension(500, 500));


        JLabel hello = new JLabel("<html><p><br>Buna ziua, " + student.getFirstName() + " " + student.getLastName() + "! </p><br></html>");
        JLabel lblTitular = new JLabel("Titular: \n");
        JLabel lblGrupa = new JLabel("Grupa: \n");
        JLabel lblAsistent = new JLabel("Asistent student: \n");
        JLabel lblNotaPartial = new JLabel("Nota partiala: \n");
        JLabel lblNotaExam = new JLabel("Nota examen: \n");
        JLabel lblNotaTotal = new JLabel("Nota totala: \n");
        JLabel lblAsistentiTotal = new JLabel("Asistentii cursului: \n");

        hello.setFont(veryBigFont);
        hello.setBackground(myBlue);
        JPanel helloPanel = new JPanel(new BorderLayout(20,20));
        JPanel titularPanel = new JPanel();
        JPanel grupaPanel = new JPanel();
        JPanel asistentPanel = new JPanel();
        JPanel notaPartialPanel = new JPanel();
        JPanel notaExamPanel = new JPanel();
        JPanel notaTotalPanel = new JPanel();

        JScrollPane scrollPaneAsistenti = new JScrollPane();
        JPanel asistentiTotalPanel = new JPanel(new BorderLayout(20,20));


        titular = new JTextField();
        titular.setColumns(20);
        grupa = new JTextField();
        grupa.setColumns(20);
        asistent = new JTextField();
        asistent.setColumns(20);
        notaExam = new JTextField();
        notaExam.setColumns(20);
        notaPartial = new JTextField();
        notaPartial.setColumns(20);
        notaTotal = new JTextField();
        notaTotal.setColumns(20);
        asistenti = new DefaultListModel<>();
        JList<String> lista2 = new JList<>(asistenti);

        helloPanel.setBackground(myBlue);
        helloPanel.add(hello);
        stylePanel(titularPanel, lblTitular, titular, myBlue, mediumFont);
        stylePanel(grupaPanel, lblGrupa, grupa, myBlue, mediumFont);
        stylePanel(asistentPanel, lblAsistent, asistent, myBlue, mediumFont);
        stylePanel(notaExamPanel, lblNotaExam, notaExam, myBlue, mediumFont);
        stylePanel(notaPartialPanel, lblNotaPartial, notaPartial, myBlue, mediumFont);
        stylePanel(notaTotalPanel, lblNotaTotal, notaTotal, myBlue, mediumFont);

        lblAsistentiTotal.setFont(bigFont);
        lista2.setFont(mediumFont);
        asistentiTotalPanel.add(lblAsistentiTotal,BorderLayout.NORTH);
        scrollPaneAsistenti.setViewportView(lista2);
        scrollPaneAsistenti.setFont(mediumFont);
        asistentiTotalPanel.add(scrollPaneAsistenti, BorderLayout.CENTER);
        asistentiTotalPanel.setBackground(new Color(227, 239, 250));



        p.add(helloPanel);
        p.add(titularPanel);
        p.add(grupaPanel);
        p.add(asistentPanel);
        p.add(notaExamPanel);
        p.add(notaPartialPanel);
        p.add(notaTotalPanel);
        p.add(asistentiTotalPanel);
        p.setBackground(new Color(227, 239, 250));
        add(p, BorderLayout.CENTER);

        show();
        pack();
    }
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (lista.isSelectionEmpty()) {
            titular.setText("");
            grupa.setText("");
            asistent.setText("");
            notaTotal.setText("");
            notaExam.setText("");
            notaPartial.setText("");
            return;
        }

        String c = lista.getSelectedValue();
        for (Course course : Catalog.getInstance().getCourses()) {
            if (course.getName().equals(c)) {
                asistenti.removeAllElements();
                titular.setText(course.titular.toString());
                for (Grade g : course.getGrades()) {
                    if (g.getStudent().getFirstName().equals(copyStudent.getFirstName()) &&
                            g.getStudent().getLastName().equals(copyStudent.getLastName())) {
                        notaExam.setText(g.getExamScore().toString());
                        notaPartial.setText(g.getPartialScore().toString());
                        notaTotal.setText(g.getTotal().toString());
                    }
                }
                    for (Assistant a : course.assistants)
                        asistenti.addElement(a.getFirstName() + " " + a.getLastName());
            }
                for (Group g : course.groups.values()) {
                    if (g.contains(copyStudent)) {
                        grupa.setText(g.getID());
                        asistent.setText(g.getAssistant().toString());
                    }
                }
            }
        }
        public void stylePanel (JPanel panel, JLabel lbl, JTextField text, Color color, Font font){
            panel.add(lbl);
            panel.add(text);
            panel.setBackground(color);
            for (int i = 0; i < panel.getComponentCount(); i++)
                panel.getComponent(i).setFont(font);
        }
}
