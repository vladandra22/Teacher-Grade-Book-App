public class Notification {
    private Parent parent;
    private Grade grade;
    public Notification(Parent parent, Grade grade) {
        this.parent = parent;
        this.grade = grade;
    }

    public Parent getParent() {
        return parent;
    }

    public Grade getGrade() {
        return grade;
    }

    @Override
    public String toString() {
        return "NOTIFICARE " + parent.getFirstName() + " " + parent.getLastName() + " : Copilul dvs. are o noua nota! Nota totala: " + grade.getTotal()  + ". Curs: " + grade.getCourse().toString();
    }
}
