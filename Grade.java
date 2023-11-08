// De implementat Comparable si Cloneable

public class Grade implements Comparable<Grade>, Cloneable{
    private Double partialScore, examScore;
    private Student student;
    private String course;

    public Grade(Double partialScore, Double examScore, Student student, String course) {
        this.partialScore = partialScore;
        this.examScore = examScore;
        this.student = student;
        this.course = course;
    }

    // Getter
    public Double getPartialScore() {
        return partialScore;
    }

    public Double getExamScore() {
        return examScore;
    }

    public Student getStudent() {
        return student;
    }

    public String getCourse() {
        return course;
    }

    // Setter
    public void setPartialScore(Double partialScore) {
        this.partialScore = partialScore;
    }

    public void setExamScore(Double examScore) {
        this.examScore = examScore;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    //De implementat metoda pentru Cloneable
    // Metoda calcul nota:
    public Double getTotal(){
        return this.partialScore + this.examScore;
    }


    //Metoda Memento
   /* public Object clone(){

    } */
    //Comparare in functie de punctajul total
    @Override
    public int compareTo(Grade g){
        if(this.getTotal() > g.getTotal())
            return 1;
        return -1;
    }

    //Cloneable
    @Override
    public Object clone() throws CloneNotSupportedException{
        Grade clone = (Grade)super.clone();
        clone.partialScore = this.partialScore;
        clone.examScore = this.examScore;
        clone.student = new Student(this.student.getFirstName(), this.student.getLastName());
        clone.student.setFather(this.student.getFather());
        clone.student.setMother(this.student.getMother());
        clone.course = new String(this.course);
        return clone;
    }

    @Override
    public String toString() {
        return  "{partialScore=" + partialScore +
                ", examScore=" + examScore +
                ", student=" + student.getFirstName() + " " + student.getLastName() +
                ", course=" + course +
                '}';
    }
}
