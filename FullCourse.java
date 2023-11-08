import java.util.*;

// Pt. a putea seta campurile Course folosim sablonul Builder
// !! Implementare clasa interna CourseBuilder extinsa cu PartialCourseBuilder si FullCourseBuilder
public class FullCourse extends Course{

    public FullCourse(FullCourseBuilder builder){
        super(builder);
    }
    public static class FullCourseBuilder extends Course.CourseBuilder {
        @Override
        public FullCourse build() {
            return new FullCourse(this);
        }
    }
    public ArrayList<Student> getGraduatedStudents(){
        ArrayList<Student> students = new ArrayList<>();
        for(Grade g : grades){
            if(g.getPartialScore() >= 3 && g.getExamScore() >= 2)
                students.add(g.getStudent());
        }
        return students;
    }


}
