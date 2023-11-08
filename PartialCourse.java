// Implementeaza metoda abstracta
// Consideram toti studentii cu total>=5 promovati

import java.util.*;

// Pt. a putea seta campurile Course folosim sablonul Builder
// !! Implementare clasa interna CourseBuilder extinsa cu PartialCourseBuilder si FullCourseBuilder
public class PartialCourse extends Course{

    public PartialCourse(PartialCourseBuilder builder){
        super(builder);
    }
    public static class PartialCourseBuilder extends Course.CourseBuilder {
        @Override
        public PartialCourse build() {
            return new PartialCourse(this);
        }
    }
    public ArrayList<Student> getGraduatedStudents(){
        ArrayList<Student> students = new ArrayList<>();
        for(Grade g : grades){
            double total = g.getPartialScore() + g.getExamScore();
            if(total >= 5)
                students.add(g.getStudent());
        }
        return students;
    }


}
