import java.util.*;
public class BestExamScore implements Strategy{
    @Override
    public Student getBestStudent(List<Grade> grades){
        Student student = null;
        Double ans = 0.0;
        for(Grade g : grades){
            if(g.getExamScore() > ans)
                student = g.getStudent();
            ans = Math.max(g.getExamScore(), ans);
        }
        return student;
    }
}
