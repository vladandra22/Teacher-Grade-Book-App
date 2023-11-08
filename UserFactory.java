// Sablon de proiectare Factory
public class UserFactory {
    public User user;

    public User getUser(String userType, String firstName, String lastName) {
        if(userType.equals("STUDENT"))
            return new Student(firstName, lastName);
        else if(userType.equals("TEACHER"))
            return new Teacher(firstName, lastName);
        else if(userType.equals("ASSISTANT"))
            return new Assistant(firstName, lastName);
        else if(userType.equals("PARENT"))
            return new Parent(firstName, lastName);
        return null;
    }
}
