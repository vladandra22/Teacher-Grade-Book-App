import com.sun.source.tree.Tree;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {

       /* Catalog catalog = Catalog.getInstance();
        UserFactory userFactory = new UserFactory();
        LoginCredentials credentialsStudent = new LoginCredentials("gigel", "1234");
        LoginCredentials credentialsParent = new LoginCredentials("par", "1234");
        LoginCredentials credentialsAsistent = new LoginCredentials("ass", "1234");
        LoginCredentials credentialsTitular = new LoginCredentials("tit", "1234");
        HashMap<User, LoginCredentials> db = new HashMap<>();

        JSONParser jsonparser = new JSONParser();
        FileReader reader = new FileReader("jsontest.json");
        Object obj = jsonparser.parse(reader);
        JSONObject myJSONobj = (JSONObject) obj;

        JSONArray courseArray = (JSONArray)myJSONobj.get("courses");
        for(int i = 0; i < courseArray.size(); i++){

            JSONObject course = (JSONObject)courseArray.get(i);
            String type = (String) course.get("type");
            String strategy = (String) course.get("strategy");
            //SETEZ STRATEGIA
            Strategy myStrategy;
            if(strategy.equals("BestPartialScore"))
                myStrategy = new BestPartialScore();
            else if(strategy.equals("BestExamScore"))
                myStrategy = new BestExamScore();
            else
                myStrategy = new BestTotalScore();

            String name = (String) course.get("name");
            JSONObject titular = (JSONObject) course.get("teacher");
            String firstNameTitular = (String)titular.get("firstName");
            String lastNameTitular = (String)titular.get("lastName");

            //CREEZ TITULAR
            User myTitular = userFactory.getUser("TEACHER", firstNameTitular, lastNameTitular);
            //PUN PAROLA PRIMULUI TITULAR
            if(i == 0) db.put(myTitular, credentialsTitular);
            JSONArray assistantsArray = (JSONArray)course.get("assistants");
            //CREEZ LISTA DE ASISTENTI PT FIECARE CURS
            HashSet<Assistant> myCourseAssistantArray = new HashSet<>();
            for(int j = 0; j < assistantsArray.size(); j++){
                JSONObject assistant = (JSONObject) assistantsArray.get(j);
                String firstNameAssistant = (String) assistant.get("firstName");
                String lastNameAssistant = (String) assistant.get("lastName");
                //CREEZ ASISTENT
                User myAssistant = userFactory.getUser("ASSISTANT", firstNameAssistant, lastNameAssistant);
                // PUN PAROLA PRIMULUI ASISTENT
                myCourseAssistantArray.add((Assistant)myAssistant);

            }
            HashMap<String,Group> myGroupHM = new HashMap<>();
            JSONArray groupsArray = (JSONArray) course.get("groups");
            for(int j = 0; j < groupsArray.size(); j++){
                JSONObject group = (JSONObject)groupsArray.get(j);
                String ID = (String) group.get("ID");
                JSONObject groupAssistant = (JSONObject) group.get("assistant");
                String firstNameGroupAssistant = (String) groupAssistant.get("firstName");
                String lastNameGroupAssistant = (String) groupAssistant.get("lastName");


                //CREEZ ASISTENTUL GRUPULUI
                User myGroupAssistant = userFactory.getUser("ASSISTANT", firstNameGroupAssistant, lastNameGroupAssistant);
                if(i == 0 && j == 0) db.put(myGroupAssistant, credentialsAsistent);
                //CREEZ GRUPUL
                Group myGroup = new Group(ID, (Assistant) myGroupAssistant, new Comparator<Student>() {
                    @Override
                    public int compare(Student o1, Student o2) {
                        if (o1.equals(o2)) {
                            return 0;
                        } else if (o1.getLastName() == o2.getLastName()) {
                            return o1.getFirstName().compareTo(o2.getFirstName());
                        } else return o1.getLastName().compareTo(o2.getLastName());
                    }
                });
                myGroupHM.put(myGroup.getID(), myGroup);
                JSONArray studentsArray = (JSONArray) group.get("students");
                for(int k = 0; k < studentsArray.size(); k++){
                    JSONObject student = (JSONObject) studentsArray.get(k);
                    String firstNameStudent = (String) student.get("firstName");
                    String lastNameStudent = (String) student.get("lastName");
                    // CREEZ STUDENT
                    User myStudent = userFactory.getUser("STUDENT", firstNameStudent, lastNameStudent);
                    if(i == 0 && j == 0 && k == 0) db.put(myStudent, credentialsStudent);
                    myGroup.add((Student)myStudent);

                    boolean hasMother = false, hasFather = false;
                    if(student.containsKey("mother")) {
                        JSONObject mother = (JSONObject) student.get("mother");
                        String firstNameMother = (String) mother.get("firstName");
                        String lastNameMother = (String) mother.get("lastName");
                        //CREEZ MAMA
                        User myMother = userFactory.getUser("PARENT", firstNameMother, lastNameMother);
                        //SETEZ PARINTE
                        ((Student) myStudent).setMother((Parent) myMother);
                        //ADAUG OBSERVER
                        catalog.addObserver((Parent) myMother);
                        if(hasFather == false && i == 0 && j == 0 && k == 0)
                            db.put(myMother, credentialsParent);
                        hasMother = true;
                    }
                    if(student.containsKey("father")) {
                        JSONObject father = (JSONObject) student.get("father");
                        String firstNameFather = (String) father.get("firstName");
                        String lastNameFather = (String) father.get("lastName");
                        //CREEZ TATA
                        User myFather = userFactory.getUser("PARENT", firstNameFather, lastNameFather);
                        //SETEZ PARINTE
                        ((Student) myStudent).setFather((Parent) myFather);
                        //ADAUG OBSERVER
                        if(hasMother == false && i == 0 && j == 0 && k == 0)
                            db.put(myFather, credentialsParent);
                        catalog.addObserver((Parent) myFather);
                        hasFather = true;
                    }
                }
            }
            if(type.equals("FullCourse")){
                //CREEZ CURSUL
                FullCourse myCourse = (FullCourse) new FullCourse.FullCourseBuilder().setName(name).setTeacher((Teacher) myTitular).setAssistants(myCourseAssistantArray)
                        .setGrades(new ArrayList<>()).setGroups(myGroupHM).setStrategy(myStrategy).build();
                catalog.addCourse(myCourse);
                for(Group g : myGroupHM.values()){
                    for(Student s : g)
                        myCourse.addStudent(g.getID(), s);
                        myCourse.addAssistant(g.getID(),g.getAssistant());
                }
            }
            else{
                //CREEZ CURSUL
                PartialCourse myCourse = (PartialCourse) new PartialCourse.PartialCourseBuilder().setName(name).setTeacher((Teacher) myTitular).setAssistants(myCourseAssistantArray)
                        .setGrades(new ArrayList<>()).setGroups(myGroupHM).setStrategy(myStrategy).build();
                catalog.addCourse(myCourse);
                }
        }

        //CREEZ SCOREVISITOR
        ScoreVisitor scoreVisitor = new ScoreVisitor();
        JSONArray examScoresArray = (JSONArray) myJSONobj.get("examScores");
        for(int i = 0; i < examScoresArray.size(); i++){
            JSONObject examScore = (JSONObject) examScoresArray.get(i);
            JSONObject teacher = (JSONObject) examScore.get("teacher");

            String firstNameTeacher = (String) teacher.get("firstName");
            String lastNameTeacher = (String) teacher.get("lastName");
            //CREEZ TEACHER PT NOTA
            User myTeacher = userFactory.getUser("TEACHER", firstNameTeacher, lastNameTeacher);

            //CREEZ STUDENT
            JSONObject student = (JSONObject) examScore.get("student");
            String firstNameStudent = (String) student.get("firstName");
            String lastNameStudent = (String) student.get("lastName");
            User myStudent = userFactory.getUser("STUDENT", firstNameStudent, lastNameStudent);

            String course = (String) examScore.get("course");
            double grade = (double) examScore.get("grade");
            //CREEZ NOTA IN SCOREVISITOR
            scoreVisitor.addExamScore((Teacher) myTeacher, (Student) myStudent, course, grade);
            //scoreVisitor.visit((Teacher) myTeacher);
            ((Teacher) myTeacher).accept(scoreVisitor);

        }
        JSONArray partialScoresArray = (JSONArray) myJSONobj.get("partialScores");
        for(int i = 0; i < partialScoresArray.size(); i++){
            JSONObject partialScore = (JSONObject) partialScoresArray.get(i);
            JSONObject assistant = (JSONObject) partialScore.get("assistant");
            String firstNameAssistant = (String) assistant.get("firstName");
            String lastNameAssistant = (String) assistant.get("lastName");

            //CREEZ ASISTENT
            User myAssistant = userFactory.getUser("ASSISTANT", firstNameAssistant, lastNameAssistant);
            JSONObject student = (JSONObject) partialScore.get("student");
            String firstNameStudent = (String) student.get("firstName");
            String lastNameStudent = (String) student.get("lastName");

            //CREEZ STUDENT
            User myStudent = userFactory.getUser("STUDENT", firstNameStudent, lastNameStudent);
            String course = (String) partialScore.get("course");
            double grade = (double) partialScore.get("grade");
            scoreVisitor.addPartialScore((Assistant) myAssistant, (Student) myStudent, course, grade);
            //scoreVisitor.visit((Assistant) myAssistant);
            ((Assistant) myAssistant).accept(scoreVisitor);
        }

       LoginPage pagina = new LoginPage(db, scoreVisitor);
*/
        Catalog catalog = Catalog.getInstance();

        UserFactory userFactory = new UserFactory();
        User s1 = userFactory.getUser("STUDENT", "Andra", "Vlad");
        User s2 = userFactory.getUser("STUDENT", "Claudiu", "Mogodeanu");
        User s3 = userFactory.getUser("STUDENT", "Ana", "Popescu");
        User s4 = userFactory.getUser("STUDENT", "Ionel", "Mihail");
        User s5 = userFactory.getUser("STUDENT", "Scandura", "Lemnaru");

        User mami1 = userFactory.getUser("PARENT", "Carmen", "Vlad");
        User tati1 = userFactory.getUser("PARENT", "Adrian", "Vlad");
        User mami2 = userFactory.getUser("PARENT", "Mami", "Mogodeanu");
        User tati2 = userFactory.getUser("PARENT", "Tati", "Mogodeanu");
        User mami3 = userFactory.getUser("PARENT", "Mami", "Popescu");
        User tati3 = userFactory.getUser("PARENT", "Tati", "Popescu");
        User mami4 = userFactory.getUser("PARENT", "Doamna", "Mihail");
        User tati4 = userFactory.getUser("PARENT", "Domnul", "Mihail");
        User mami5 = userFactory.getUser("PARENT", "Tanti", "Lemnaru");
        User tati5 = userFactory.getUser("PARENT", "Nenea", "Lemnaru");

        ((Student) s1).setMother((Parent) mami1);
        ((Student) s1).setFather((Parent) tati1);
        ((Student) s2).setMother((Parent) mami2);
        ((Student) s2).setFather((Parent) tati2);
        ((Student) s3).setMother((Parent) mami3);
        ((Student) s3).setFather((Parent) tati3);
        ((Student) s4).setMother((Parent) mami4);
        ((Student) s4).setFather((Parent) tati4);
        ((Student) s5).setMother((Parent) mami5);
        ((Student) s5).setFather((Parent) tati5);

        catalog.addObserver((Parent) mami1);
        catalog.addObserver((Parent) tati1);
        catalog.addObserver((Parent) mami2);
        catalog.addObserver((Parent) tati2);
        catalog.addObserver((Parent) mami3);
        catalog.addObserver((Parent) tati3);
        catalog.addObserver((Parent) mami4);
        catalog.addObserver((Parent) tati4);
        catalog.addObserver((Parent) mami5);
        catalog.addObserver((Parent) tati5);

        User titular1 = userFactory.getUser("TEACHER", "Razvan", "Deaconescu");
        User titular2 = userFactory.getUser("TEACHER", "Carmen", "Odubasteanu");

        System.out.println(s1);


        User asistent1 = userFactory.getUser("ASSISTANT", "Edi", "Staniloiu");
        User asistent2 = userFactory.getUser("ASSISTANT", "Adrian", "Lutan");
        User asistent3 = userFactory.getUser("ASSISTANT", "Ionut", "Popescu");

        HashSet<Assistant> asistentiSO = new HashSet<>();
        asistentiSO.add((Assistant) asistent1);
        asistentiSO.add((Assistant) asistent2);
        asistentiSO.add((Assistant) asistent3);

        User asistent4 = userFactory.getUser("ASSISTANT", "Mihai", "Nan");
        User asistent5 = userFactory.getUser("ASSISTANT", "Diana", "Nucuta");
        User asistent6 = userFactory.getUser("ASSISTANT", "Alex", "Bonteanu");
        User asistent7 = userFactory.getUser("ASSISTANT", "Catalin", "Ripanu");

        HashSet<Assistant> asistentiPOO = new HashSet<>();
        asistentiPOO.add((Assistant) asistent4);
        asistentiPOO.add((Assistant) asistent5);
        asistentiPOO.add((Assistant) asistent6);
        asistentiPOO.add((Assistant) asistent7);

        Grade grade1 = new Grade(5.0, 5.0, (Student) s1, "SO");
        Grade grade2 = new Grade(5.0, 5.0, (Student) s2, "SO");
        Grade grade3 = new Grade(3.5, 2.4, (Student) s1, "POO");
        Grade grade4 = new Grade(2.0, 2.5, (Student) s2, "POO");


        List<Grade> gradesSO = new ArrayList<>();
        gradesSO.add(grade1);
        gradesSO.add(grade2);

        List<Grade> gradesPOO = new ArrayList<>();
        gradesPOO.add(grade3);
        gradesPOO.add(grade4);

        catalog.notifyObservers(grade1);

        FullCourse course1 = (FullCourse) new FullCourse.FullCourseBuilder().setName("SO").setTeacher((Teacher) titular1).setAssistants(asistentiSO)
                .setGrades(gradesSO).setGroups(new HashMap<>()).setStrategy(new BestExamScore()).build();

        PartialCourse course2 = (PartialCourse) new PartialCourse.PartialCourseBuilder().setName("POO").setTeacher((Teacher) titular2).setAssistants(asistentiPOO)
                .setGrades(gradesPOO).setGroups(new HashMap<>()).setStrategy(new BestExamScore()).build();

        course1.addGroup("1", (Assistant) asistent1, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                if (o1.equals(o2)) {
                    return 0;
                } else if (o1.getLastName() == o2.getLastName()) {
                    return o1.getFirstName().compareTo(o2.getFirstName());
                } else return o1.getLastName().compareTo(o2.getLastName());
            }
        });

        course2.addGroup("2", (Assistant) asistent4, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                if (o1.equals(o2)) {
                    return 0;
                } else if (o1.getLastName() == o2.getLastName()) {
                    return o1.getFirstName().compareTo(o2.getFirstName());
                } else return o1.getLastName().compareTo(o2.getLastName());
            }
        });
        course1.addStudent("1", (Student) s1);
        course2.addStudent("2", (Student) s1);
        course2.addStudent("2", (Student) s2);
        course2.addAssistant("2", (Assistant) asistent2);
        catalog.addCourse(course1);
        catalog.addCourse(course2);
        //catalog.removeCourse(course2);

        System.out.println(course1);
        System.out.println("Best student este: " + course1.getBestStudent());

        ScoreVisitor scoreVisitor = new ScoreVisitor();
        scoreVisitor.addExamScore((Teacher) titular1, (Student) s4, "SO", 3.3);
        scoreVisitor.addExamScore((Teacher) titular1, (Student) s5, "SO", 2.4);
        scoreVisitor.addPartialScore((Assistant) asistent1, (Student) s4, "POO", 4.5);
        scoreVisitor.addPartialScore((Assistant) asistent1, (Student) s5, "POO", 2.4);
        scoreVisitor.visit((Teacher) titular1);
        System.out.println("Actualizare teacher: " + catalog);

          scoreVisitor.visit((Assistant) asistent1);
        System.out.println("Actualizare asistent: " + catalog);

        ((Teacher) titular1).accept(scoreVisitor);
        System.out.println("TESTARE MEMENTO");
        course1.makeBackup();
        for(Student s : course1.getAllStudentGrades().keySet())
            System.out.println(s.getFirstName() + " " + s.getLastName());
        course1.addGrade(grade2);
        System.out.println("FACEM UNDO.");
        course1.undo();
        for(Student s : course1.getAllStudentGrades().keySet())
            System.out.println(s.getFirstName() + " " + s.getLastName());

        System.out.println("INCEPERE SWING");
        LoginCredentials credentialsStudent = new LoginCredentials("vladandra@yahoo.com", "1234");
        LoginCredentials credentialsParent = new LoginCredentials("vladcarmen@yahoo.com", "1234");
        LoginCredentials credentialsAsistent = new LoginCredentials("suntasistent@yahoo.com", "1234");
        LoginCredentials credentialsTitular = new LoginCredentials("sunttitular@yahoo.com", "1234");
        HashMap<User, LoginCredentials> db = new HashMap<>();
        db.put(s1, credentialsStudent);
        db.put(mami1, credentialsParent);
        db.put(asistent1, credentialsAsistent);
        db.put(titular1, credentialsTitular);
        LoginPage pagina = new LoginPage(db, scoreVisitor);
        System.out.println(course1.getAllStudents());
    }
}