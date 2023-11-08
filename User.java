public abstract class User {
    private String firstName, lastName;
    private LoginCredentials credentials;

    public User(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
        this.credentials = credentials;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public LoginCredentials getCredentials() {
        return credentials;
    }

    public void setCredentials(LoginCredentials credentials) {
        this.credentials = credentials;
    }

    public String toString(){
        return firstName + " " + lastName;
    }
}
