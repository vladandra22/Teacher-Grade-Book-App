import java.util.*;

public class ScoreVisitor implements Visitor{


   private HashMap<Teacher, LinkedList<Tuple<Student, String, Double>>> examScores;
   private HashMap<Assistant, LinkedList<Tuple<Student, String, Double>>> partialScores;

    private static class Tuple<T, U, V> {
        private T student;
        private U str;
        private V dbl;

        public Tuple(T student, U str, V dbl) {
            this.student = student;
            this.str = str;
            this.dbl = dbl;
        }

        public T getStudent() {
            return this.student;
        }

        public U getStr() {
            return this.str;
        }

        public V getDbl() {
            return this.dbl;
        }

        @Override
        public String toString() {
            return "Tuple {" + student +
                    ", course=" + str +
                    ", grade=" + dbl +
                    '}';
        }
    }

    public HashMap<Teacher, LinkedList<Tuple<Student, String, Double>>> getExamScores() {
        return examScores;
    }

    public HashMap<Assistant, LinkedList<Tuple<Student, String, Double>>> getPartialScores() {
        return partialScores;
    }

    public void setExamScores(HashMap<Teacher, LinkedList<Tuple<Student, String, Double>>> examScores) {
        this.examScores = examScores;
    }

    public void setPartialScores(HashMap<Assistant, LinkedList<Tuple<Student, String, Double>>> partialScores) {
        this.partialScores = partialScores;
    }

    public void addExamScore(Teacher titular, Student s, String str, Double dbl){
        if(this.examScores == null)
            this.examScores = new HashMap<>();
        if(!this.examScores.containsKey(titular)){
            this.examScores.put(titular, new LinkedList<>());
        }
        this.examScores.get(titular).add(new Tuple<>(s, str, dbl));
    }

    public void addPartialScore(Assistant asistent, Student s, String str, Double dbl){
        if(this.partialScores == null)
            this.partialScores = new HashMap<>();
        if(!this.partialScores.containsKey(asistent)){
            this.partialScores.put(asistent, new LinkedList<>());
        }
        this.partialScores.get(asistent).add(new Tuple<>(s, str, dbl));
    }

    public void visit(Assistant assistant){
        List<Tuple<Student, String, Double>> tuples = partialScores.get(assistant);
        for(Tuple<Student, String, Double> tuple : tuples){
            for(Course course : Catalog.getInstance().getCourses()){
                if(course.getName().equals(tuple.getStr())) {
                    int ok = 0;
                    for (Grade g : course.grades) {
                        Student s = tuple.getStudent();
                        if (s.equals(g.getStudent())) {
                            ok = 1;
                            g.setPartialScore(tuple.getDbl());
                            Catalog.getInstance().notifyObservers(g);
                        }
                    }
                    if (ok == 0) {
                        Grade newGrade = new Grade(tuple.getDbl(), 0.0, tuple.getStudent(), course.name);
                        course.addGrade(newGrade);
                        Catalog.getInstance().notifyObservers(newGrade);
                    }
                }
            }
        }
    }


    public void visit(Teacher titular) {
        List<Tuple<Student, String, Double>> tuples = examScores.get(titular);
        for (Tuple<Student, String, Double> tuple : tuples) {
            Course course = Catalog.getInstance().getCourse(tuple.getStr());
            int ok = 0;
            for (Grade g : course.grades) {
                Student s = tuple.getStudent();
                if (s.equals(g.getStudent())) {
                    ok = 1;
                    g.setPartialScore(tuple.getDbl());
                    Catalog.getInstance().notifyObservers(g);
                }
            }
            if (ok == 0) {
                Grade newGrade = new Grade(0.0,tuple.getDbl(), tuple.getStudent(), course.name);
                course.addGrade(newGrade);
                Catalog.getInstance().notifyObservers(newGrade);
            }
        }
    }
}

