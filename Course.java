import java.util.*;

//Sablon de proiectare Builder = construieste curs pas cu pas
public abstract class Course {
    public String name;
    public Snapshot backup;
    public Teacher titular;
    public HashSet<Assistant> assistants; //colectie fara duplicate
    public List<Grade> grades; // colectie ordonata
    public HashMap<String, Group> groups;
    public Strategy strategy;
    public int creditPoints;

    public Course (CourseBuilder builder){
        this.name = builder.name;
        this.titular = builder.titular;
        this.assistants = builder.assistants;
        this.grades = builder.grades;
        this.groups = builder.groups;
        this.strategy = builder.strategy;
        this.creditPoints = builder.creditPoints;
    }

    public abstract static class CourseBuilder{

        private String name;
        private Teacher titular;
        private HashSet<Assistant> assistants;
        private List<Grade> grades;
        private HashMap<String, Group> groups;
        private Strategy strategy;
        private int creditPoints;

        public CourseBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public CourseBuilder setTeacher(Teacher titular) {
            this.titular = titular;
            return this;
        }

        public CourseBuilder setAssistants(HashSet<Assistant> assistants) {
            this.assistants = assistants;
            return this;
        }

        public CourseBuilder setGrades(List<Grade> grades) {
            this.grades = grades;
            return this;
        }

        public CourseBuilder setGroups(HashMap<String, Group> groups) {
            this.groups = groups;
            return this;
        }
        public CourseBuilder setStrategy(Strategy strategy) {
            this.strategy = strategy;
            return this;
        }
        public CourseBuilder setCreditPoints(int creditPoints){
            this.creditPoints = creditPoints;
            return this;
        }

        public abstract Course build();
    }

    private class Snapshot {
        private ArrayList<Grade> grades;
        public Snapshot(){
            this.grades = new ArrayList<>();
        }

        public void setGrades(ArrayList<Grade> grades){
            this.grades = grades;
        }
        public ArrayList<Grade> getGrades(){
            return this.grades;
        }
        public Snapshot(List<Grade> grades) throws CloneNotSupportedException {
            this();
            for (Grade g : grades) {
                this.grades.add((Grade) g.clone());
            }
        }
    }


    /* Selecteaza asistentul in grupa cu ID indicat si
    daca nu exista, adauga asistentul */
    public void addAssistant(String ID, Assistant assistant){
        if(!this.assistants.contains(assistant))
            this.assistants.add(assistant);
        this.groups.get(ID).setAssistant(assistant);
    }

    // Adauga studentul in grupa cu ID indicat
    public void addStudent(String ID, Student student){
        for(String groupID : this.groups.keySet()){
            if(groupID.equals(ID))
                this.groups.get(ID).add(student);
        }
    }
    public void addGroup(Group group){
        this.groups.put(group.getID(), group);
    }

    public void addGroup(String ID, Assistant assistant){
        Group g = new Group(ID, assistant);
        this.groups.put(ID, g);
    }

    public void addGroup(String ID, Assistant assistant, Comparator<Student> comp){
        Group g = new Group(ID, assistant, comp);
        this.groups.put(ID, g);
    }

    //NU STIU DACA E BINE DACA TRB SA IAU STUDENTII DIN GRADE SAU GROUP
    public Grade getGrade(Student student){
        for(Grade g : this.grades){
            if(g.getStudent() == student)
                return g;
        }
        return null;
    }

    public List<Grade> getGrades(){
        return this.grades;
    }

    public void addGrade(Grade grade){
        this.grades.add(grade);
    }

    public ArrayList<Student> getAllStudents(){
        ArrayList<Student> students = new ArrayList<>();
        for(Group g : this.groups.values())
            for(Student s : g)
                students.add(s);
        return students;
    }

    public HashMap<Student, Grade> getAllStudentGrades(){
        HashMap<Student, Grade> studentsSituation = new HashMap<>();
        for(Grade g : this.grades){
            studentsSituation.put(g.getStudent(), g);
        }
        return studentsSituation;
    }

    // Promovare verificata cu PartialCourse si FullCourse
    public abstract ArrayList<Student> getGraduatedStudents();


    // Sablonul de proiectare Strategy pt selectarea celui mai bun student
    public Student getBestStudent(){
        return this.strategy.getBestStudent(this.grades);
    }
    @Override
    public String toString() {
        return "CURS CU: "+ '\n' +
                "{nume = " + name + '\n' +
                "titular = " + titular + '\n' +
                "asistenti = " + assistants + '\n' +
                "grades=" + grades + '\n' +
                "groups=" + groups + '\n' +
                '}';
    }

    public void makeBackup(){
        try{
            this.backup = new Snapshot(this.grades);
        }
        catch(CloneNotSupportedException e){
            e.printStackTrace();
        }
    }

    public void undo(){
        this.grades = this.backup.grades;
    }

    public Teacher getTitular() {
        return this.titular;
    }

    public String getName() {
        return this.name;
    }
}
