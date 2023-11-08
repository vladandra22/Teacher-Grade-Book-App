import java.util.*;

public class Group extends TreeSet<Student> {
    private String ID;
    private Assistant assistant;
    private Comparator<Student> comp;

    public Group(String ID, Assistant assistant, Comparator<Student> comp) {
        this.ID = ID;
        this.assistant = assistant;
        this.comp = comp;
    }

    public Group(String ID, Assistant assistant) {
        this.ID = ID;
        this.assistant = assistant;
        this.comp = null;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Assistant getAssistant() {
        return assistant;
    }

    public void setAssistant(Assistant assistant) {
        this.assistant = assistant;
    }
    public Group getGroup(){
        return this;
    }

    @Override
    public boolean equals(Object o){
        if(o == this) {
            return true;
        }
        if(!(o instanceof Group)) {
            return false;
        }

        Group g = (Group)o;
        return Objects.equals(this.getID(), g.getID());
    }
}