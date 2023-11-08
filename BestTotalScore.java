import java.util.*;
public class BestTotalScore implements Strategy{
    @Override
    public Student getBestStudent(List<Grade> grades){
        Student student = null;
        Double ans = 0.0;
        for(Grade g : grades){
            if(g.getTotal() > ans)
                student = g.getStudent();
            ans = Math.max(g.getTotal(), ans);
        }
        return student;
    }
}