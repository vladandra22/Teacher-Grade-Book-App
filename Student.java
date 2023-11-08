public class Student extends User implements Comparable {
    private Parent mother, father;

    public Student(String firstName, String lastName) {
        super(firstName, lastName); //sets user data to student
    }
    public void setMother(Parent mother){
        this.mother = mother;
    }
    public void setFather(Parent father){
        this.father = father;
    }
    public Parent getMother() {
        return this.mother;
    }

    public Parent getFather() {
        return this.father;
    }

    @Override
    public String toString() {
        return "Student " + this.getFirstName() + " " + this.getLastName() + " cu mama " + this.getMother() + " si tatal " + this.getFather();
    }

    @Override
    public int compareTo(Object o){
        Student s = (Student) o;
        if(this.equals(s)){
            return 0;
        }
        else if(this.getLastName() == s.getLastName()){
            return this.getFirstName().compareTo(s.getFirstName());
        }
        else return this.getLastName().compareTo(s.getLastName());
    }
}
