/**
 * Data structure for operand after parsed.
 * **/
public class OpX {
    private final int hasRinSR;
    private int sourceRegister;
    private int virtualRegister;
    private int physicalRegister;
    private int nextUse = -1;
    /**
     * Constructor for the operand.
     * **/
    public OpX(int hasRinSR, int sourceRegister, int virtualRegister, int physicalRegister, int nextUse){

        this.hasRinSR = hasRinSR;
        this.sourceRegister = sourceRegister;
        this.virtualRegister = virtualRegister;
        this.physicalRegister = physicalRegister;
        this.nextUse = nextUse;
    }
    /**
     * Setters and getters for the op.
     *
     * **/
    public int getRinSR() {return this.hasRinSR;}

    public int getSourceRegister() {
        return this.sourceRegister;
    }

    public int getVirtualRegister() {
        return this.virtualRegister;
    }

    public int getPhysicalRegister() {
        return this.physicalRegister;
    }

    public int getNextUse() {
        return this.nextUse;
    }

    public void setSourceRegister(int sourceRegister) { this.sourceRegister = sourceRegister;}

    public void setVirtualRegister(int virtualRegister) {this.virtualRegister = virtualRegister;}

    public void setPhysicalRegister(int physicalRegister) {this.physicalRegister = physicalRegister;}

    public void setNextUse(int nextUse) {this.nextUse = nextUse;}
}
