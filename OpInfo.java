
public class OpInfo {
    public OpX op1;
    public OpX op2;
    public OpX op3;
    public String opCode;
    public int index;

    public OpInfo(int ind, String opCode, OpX op1, OpX op2, OpX op3) {
        this.op1 = op1;
        this.op2 = op2;
        this.op3 = op3;
        this.opCode = opCode;
        this.index = ind;
    }
}
