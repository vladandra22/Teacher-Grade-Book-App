import java.util.*;
public class BestPartialScore implements Strategy{
    @Override
    public Student getBestStudent(List<Grade> grades){
        Student student = null;
        Double ans = 0.0;
        for(Grade g : grades){
            if(g.getPartialScore() > ans)
                student = g.getStudent();
            ans = Math.max(g.getPartialScore(), ans);
        }
        return student;
    }
}