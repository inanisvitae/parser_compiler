import java.util.LinkedList;
import java.util.List;

interface State {
    /**
     * State interface defines the method and the necessary lists used in the project.
     * **/
    public List<String> stack = new LinkedList<>();
    public List<Integer> op1 = new LinkedList<>();
    public List<Integer> op2 = new LinkedList<>();
    public List<Integer> op3 = new LinkedList<>();
    public List<String> lshiftRshift = new LinkedList<>();
    public List<String> currentOpRegList = new LinkedList<>();
    public State next(Input word);
    /**
     * Concatenate the input characters.
     * **/
    public void stringConcat(char inputChar);
}