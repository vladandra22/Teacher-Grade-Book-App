import java.util.*;

// Singleton implementare cu ajutorul obj
// Observer pt implementarea notificarilor
public class Catalog implements Subject{
    private ArrayList<Course> courses;
    private static Catalog obj = null;
    private ArrayList<Observer> observers;

    private Catalog(){
        courses = new ArrayList<>();
        observers = new ArrayList<>();
    }

    public static Catalog getInstance() {
        if(obj == null)
            obj = new Catalog();
        return obj;
    }


    // Adauga curs in catalog
    public void addCourse(Course course){
        courses.add(course);
    }
    // Sterge curs din catalog
    public void removeCourse(Course course){
        courses.remove(course);
    }

    public Course getCourse(String name) {
        for (Course course : courses) {
            if (course.name.equals(name)) {
                return course;
            }
        }
        return null;
    }

    public ArrayList<Course> getCourses(){
        return this.courses;
    }
    @Override
    public void addObserver(Observer observer){
        observers.add(observer);
    }

    public ArrayList<Observer> getObservers() {
        return observers;
    }

    @Override
    public void removeObserver(Observer observer){
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Grade grade){
        for(Observer obs : observers){
            Parent mother = grade.getStudent().getMother();
            Parent father = grade.getStudent().getFather();
            if(mother != null){
                Notification notifyMother = new Notification(mother, grade);
                if(mother == (Parent) obs){   // daca e parinte, il notificam
                    obs.update(notifyMother);
                }
            }
            if(father != null) {
                Notification notifyFather = new Notification(father, grade);
                if (father == (Parent) obs) {
                    obs.update(notifyFather);
                }
            }
        }
    }

    public LinkedList<Notification> getNotifications(){
        LinkedList<Notification> allNotifs = new LinkedList<>();
        for(Course c : this.courses){
            for(Grade grade : c.grades){
                Parent mother = grade.getStudent().getMother();
                Parent father = grade.getStudent().getFather();
                if(mother != null){
                    Notification notifyMother = new Notification(mother, grade);
                    allNotifs.add(notifyMother);
                }
                if(father != null) {
                    Notification notifyFather = new Notification(father, grade);
                    allNotifs.add(notifyFather);
                }
            }
        }
        return allNotifs;
    }

    @Override
    public String toString() {
        return courses.toString();
    }
}
