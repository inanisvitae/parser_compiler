import java.util.LinkedList;
import java.util.List;

public class RegistersCollection {
    private int size;

    List<Integer> name = new LinkedList<>();

    List<Integer> next = new LinkedList<>();

    List<Integer> free = new LinkedList<>();

    List<Integer> stack = new LinkedList<>();

    int stackTop;

    public void RegistersCollection(int numOfRegisters) {

    }

    public boolean add(int num) {
        if(name.size() == size) {
            return false;
        }else{
            name.add(0, num);
            free.add(0, -1);
            stackTop = num;
//            next.add(num);
            return true;
        }
    }

    public boolean pop() {
        if(name.size() == 0) {
            //The stack is already empty.
            return false;
        }else{
            //The stack is not empty.

            return true;
        }
    }

}
