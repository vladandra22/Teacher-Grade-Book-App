import java.util.ArrayList;

public class Parent extends User implements Observer{
    String firstName, lastName;
    private ArrayList<Notification> notifications;
    public Parent(String firstName, String lastName) {
        super(firstName, lastName);
    }

    @Override
    public void update(Notification notification){
        System.out.println(notification);
    }
}
