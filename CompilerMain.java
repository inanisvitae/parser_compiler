import java.io.*;
import java.util.*;

/**
 * The main compiler method which does the renaming and the physical register allocation.
 * **/
public class CompilerMain {

    private static List<OpInfo> table = new LinkedList<>();
    private static int k;
    private static int[] srToVr;
    private static int[] lu;
    private static int[] vrToPr;
    private static int[] prToVr;
    private static boolean[] justSpilled;
    private static int largestVr = Integer.MIN_VALUE;
    private static int largestSr = Integer.MIN_VALUE;
    private static int[] loadIArray;
    private static int[] nextUse;
    private static List<Integer> emptyPrToVr = new LinkedList<>();
    private static int count = 0;
    private static int VRName = 0;

    public static void main(String[] args){

        List<String> inputLst = new LinkedList<>();
        for (String s: args) {
            inputLst.add(s);
        }

        //When the arg number is 1, which is likely the manual print request.
        if(inputLst.size() == 1) {
            if(inputLst.get(0).equals("-h")) {
                //Prints the manual
                System.out.println("Required arguments:\n" +
                        "        k         specifies the number of register available\n" +
                        "        filename  is the pathname (absolute or relative) to the input file\n" +
                        "\n" +
                        "Optional flags:\n" +
                        "        -h        prints this message\n");

            }
        }
        if(inputLst.size() == 2) {
            if(inputLst.get(0).equals("-x")) {
                reader(inputLst.get(1));
                printRenameResult();
            }else{
                try {
                    int kl = Integer.parseInt(inputLst.get(0));
                    if(kl < 3 || kl > 64) {
                        System.out.println("The number of the registers must be within range of 3 and 64");
                    }else{
                        //Starts the allocator.
                        k = kl;
                        reader(inputLst.get(1));
                        allocate();
                    }
                } catch (NumberFormatException e) {
                    System.err.println("This is not a number.");
                }
            }
        }
    }
    /**
     * Reads in the file.
     * **/
    private static void reader(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            while((line = br.readLine()) != null) {
                boolean flag = scanner(line, count);
                if(!flag) {
                    System.err.println("An error is found.");
                    break;
                }
                count++;
            }
            rename();

        }  catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Scans the input text
     * **/
    private static boolean scanner(String inputLine, int count) {
        State s;
        Input in = new Input(inputLine);
        s = States.Init;
        States.op1.clear();
        States.op2.clear();
        States.op3.clear();
        States.lshiftRshift.clear();
        States.stack.clear();
        while(s != States.Success && s != States.Fail) {
            //Store the stacks in a long linkedlist
            s = s.next(in);
        }
        if(States.stack.size()==0){
            return true;
        }
        //Records the largest sr number;
        if(States.stack.get(0).equals("loadI")) {
            //Only updates op3 of the loadI
            if(States.op3.size() != 0) {
                if(States.op3.get(0) > largestSr) {
                    largestSr = States.op3.get(0);
                }
            }
        }
        if(!States.stack.get(0).equals("loadI") && !States.stack.get(0).equals("output")) {
            if(States.op1.size() != 0){
                if(States.op1.get(0) > largestSr) {
                    largestSr = States.op1.get(0);
                }
            }
            if(States.op2.size() != 0) {
                if(States.op2.get(0) > largestSr) {
                    largestSr = States.op2.get(0);
                }
            }
            if(States.op3.size() != 0) {
                if(States.op3.get(0) > largestSr) {
                    largestSr = States.op3.get(0);
                }
            }
        }
        if(s != States.Success) {
            System.err.println("Failed");
            return false;
        }
        OpX op1;
        OpX op2;
        OpX op3;
        //Inserts the finished object into the table.
        if(States.stack.get(0).equals("loadI")){
            op1 = new OpX(-1, States.op1.get(0), States.op1.get(0), -1, Integer.MAX_VALUE);
            op2 = new OpX(-1, -1, -1, -1, Integer.MAX_VALUE);
            op3 = new OpX(1, States.op3.get(0), -1, -1, Integer.MAX_VALUE);
        }else if(States.stack.get(0).equals("store")) {
            op1 = new OpX(1, States.op1.get(0), -1, -1, Integer.MAX_VALUE);
            op2 = new OpX(1, States.op3.get(0), -1, -1, Integer.MAX_VALUE);
            op3 = new OpX(-1, -1, -1, -1,Integer.MAX_VALUE);
        }else if(States.stack.get(0).equals("output")){
            op1 = new OpX(-1, States.op1.get(0), States.op1.get(0), -1, Integer.MAX_VALUE);
            op2 = new OpX(-1, -1, -1, -1, Integer.MAX_VALUE);
            op3 = new OpX(-1, -1, -1, -1, Integer.MAX_VALUE);
        }else if(States.stack.get(0).equals("load")) {
            op1 = new OpX(1, States.op1.get(0), -1, -1, Integer.MAX_VALUE);
            op2 = new OpX(-1, -1, -1, -1, Integer.MAX_VALUE);
            op3 = new OpX(1, States.op3.get(0), -1, -1, Integer.MAX_VALUE);
        }else{
            op1 = new OpX(1, States.op1.get(0), -1, -1, Integer.MAX_VALUE);
            op2 = new OpX(1, States.op2.get(0), -1, -1, Integer.MAX_VALUE);
            op3 = new OpX(1, States.op3.get(0), -1, -1, Integer.MAX_VALUE);
        }
        table.add(new OpInfo(count, States.stack.get(0), op1, op2, op3));

        return true;
    }

    private static void rename() {
        //Initializes the
        srToVr = new int[largestSr+1];
        lu = new int[largestSr+1];
        for(int init = 0; init < srToVr.length; init++) {
            srToVr[init] = -1;
            lu[init] = -1;
        }
        for (int i = 0; i < srToVr.length; i++) {
            srToVr[i] = -1;
            lu[i] = Integer.MAX_VALUE;
        }

        for (int i = table.size() - 1; i >= 0; i--) {
            if(table.get(i).op3.getSourceRegister() != -1 && table.get(i).op3.getRinSR() == 1) {
                table.get(i).op3 = update(table.get(i).op3, i);
                srToVr[table.get(i).op3.getSourceRegister()] = -1;
                lu[table.get(i).op3.getSourceRegister()] = Integer.MAX_VALUE;
            }
            if(table.get(i).op1.getSourceRegister() != -1 && table.get(i).op1.getRinSR() == 1) {
                table.get(i).op1 = update(table.get(i).op1, i);
            }
            if(table.get(i).op2.getSourceRegister() != -1 && table.get(i).op2.getRinSR() == 1) {
                table.get(i).op2 = update(table.get(i).op2, i);
            }
        }

    }
    /**
     * When the flag is just rename, then prints the result of the rename.
     * **/
    private static void printRenameResult() {

        for (int iter = 0; iter < table.size(); iter++) {
            OpInfo tmpOp = table.get(iter);
            if(tmpOp.opCode.equals("loadI")) {
                System.out.println(tmpOp.opCode + " " + tmpOp.op1.getSourceRegister() +
                        " =>"+
                        " r" + tmpOp.op3.getVirtualRegister());
            }else if(tmpOp.opCode.equals("output")) {
                System.out.println(tmpOp.opCode + " " + tmpOp.op1.getSourceRegister());
            }else if(tmpOp.opCode.equals("store")){
                System.out.println(tmpOp.opCode + " r" + tmpOp.op1.getVirtualRegister() +
                        " =>" +
                        " r" + tmpOp.op2.getVirtualRegister());
            }else if(tmpOp.opCode.equals("load")){
                System.out.println(tmpOp.opCode + " r" + tmpOp.op1.getVirtualRegister() +
                        " =>" +
                        " r" + tmpOp.op3.getVirtualRegister());
            }
            else {
                System.out.println(tmpOp.opCode + " r" + tmpOp.op1.getVirtualRegister() +
                        ", r" + tmpOp.op2.getVirtualRegister() +
                        " => r" + tmpOp.op3.getVirtualRegister());
            }


        }
    }
    private static OpX update(OpX op, int index) {
        if(srToVr[op.getSourceRegister()] == -1) {
            srToVr[op.getSourceRegister()] = VRName++;
        }
        if(srToVr[op.getSourceRegister()] > largestVr) {
            largestVr = srToVr[op.getSourceRegister()];
        }
        op.setVirtualRegister(srToVr[op.getSourceRegister()]);
        op.setNextUse(lu[op.getSourceRegister()]);
        lu[op.getSourceRegister()] = index;
        return op;
    }
    private static void allocate() {
        //Finds the largest number of the virtual registers.
        for(int i=0;i<table.size();i++) {
            if(table.get(i).op1.getRinSR() == 1) {
                if(table.get(i).op1.getVirtualRegister() > largestVr) {
                    largestVr = table.get(i).op1.getVirtualRegister();
                }
            }
            if(table.get(i).op2.getRinSR() == 1) {
                if(table.get(i).op2.getVirtualRegister() > largestVr) {
                    largestVr = table.get(i).op2.getVirtualRegister();
                }
            }
            if(table.get(i).op3.getRinSR() == 1) {
                if(table.get(i).op3.getVirtualRegister() > largestVr) {
                    largestVr = table.get(i).op3.getVirtualRegister();
                }
            }
        }

        //Initializes the necessary arrays. And sets the length to k - 1.
        int numActualPhysicalRegisters = k - 1;
        prToVr = new int[numActualPhysicalRegisters];
        vrToPr = new int[largestVr + 1];
        nextUse = new int[numActualPhysicalRegisters];
        justSpilled = new boolean[numActualPhysicalRegisters];
        loadIArray = new int[largestVr + 1];
        //Initialize the arrays to -1.
        for(int i = 0; i < prToVr.length; i++) {
            prToVr[i] = -1;
            emptyPrToVr.add(i);
            nextUse[i] = -1;
            justSpilled[i] = false;
        }

        for(int i = 0; i < vrToPr.length; i++) {
            vrToPr[i] = -1;
            loadIArray[i] = -1;
        }

        //Iterates over the table and frees the physical register if it will no longer be used
        for(int i = 0; i < table.size(); i++) {
            //Free any physical registers wich will no longer be used.
            for(int m = 0;m < prToVr.length;m++) {
                justSpilled[m] = false;
                if(nextUse[m] == Integer.MAX_VALUE) {
                    nextUse[m] = -1;
                    vrToPr[prToVr[m]] = -1;
                    emptyPrToVr.add(m);
                    sortEmptyPrToVr();
                }
            }

            if(table.get(i).opCode.equals("loadI")) {
                //loadI op. no code is required to spill, just store it somewhere.
                loadIArray[table.get(i).op3.getVirtualRegister()] = table.get(i).op1.getVirtualRegister();
                continue;
            }

            //If the op1 has already been processed
            if (table.get(i).op1.getRinSR() == 1) {

                OpX op1 = table.get(i).op1;

                if (vrToPr[op1.getVirtualRegister()] != -1) {
                    table.get(i).op1.setPhysicalRegister(vrToPr[op1.getVirtualRegister()]);

                    if (vrToPr[op1.getVirtualRegister()] <= k) {
                        //Means that this vr is still in physical register.
                        nextUse[vrToPr[op1.getVirtualRegister()]] = table.get(i).op1.getNextUse();
                        //prints loadI
                        loadIPrint(op1.getVirtualRegister(), vrToPr[op1.getVirtualRegister()]);
                    } else if (vrToPr[op1.getVirtualRegister()] > k) {
                        //Means the value is stored in memory
                        //Restore it.

                        int tmpMemLoc = vrToPr[op1.getVirtualRegister()];

                        int position = getPR();

                        //Sets the PR in the table
                        table.get(i).op1.setPhysicalRegister(position);

                        //Sets PRToVR table
                        prToVr[position] = op1.getVirtualRegister();

                        //Sets the VRToPR table
                        vrToPr[op1.getVirtualRegister()] = position;
                        nextUse[position] = op1.getNextUse();
                        //Prints the loadI anc the iloc.
                        loadIPrint(op1.getVirtualRegister(), vrToPr[op1.getVirtualRegister()]);
                        System.out.println("loadI " + tmpMemLoc + " => " + "r" + (k - 1));
                        System.out.println("load r" + (k - 1) + " => " + "r" + position);
                    }
                }else{
                    int position = getPR();

                    //Sets the PR in the table
                    table.get(i).op1.setPhysicalRegister(position);

                    //Sets PRToVR table
                    prToVr[position] = op1.getVirtualRegister();

                    //Sets the VRToPR table
                    vrToPr[op1.getVirtualRegister()] = position;
                    nextUse[position] = op1.getNextUse();
                    loadIPrint(op1.getVirtualRegister(), vrToPr[op1.getVirtualRegister()]);
                }
            }

            //Deal with op2
            if (table.get(i).op2.getRinSR() == 1) {

                OpX op2 = table.get(i).op2;

                if (vrToPr[op2.getVirtualRegister()] != -1) {
                    table.get(i).op2.setPhysicalRegister(vrToPr[op2.getVirtualRegister()]);

                    if (vrToPr[op2.getVirtualRegister()] <= k) {
                        nextUse[vrToPr[op2.getVirtualRegister()]] = table.get(i).op2.getNextUse();
                        loadIPrint(op2.getVirtualRegister(), vrToPr[op2.getVirtualRegister()]);
                    } else if (vrToPr[op2.getVirtualRegister()] > k) {
                        //Means the value is stored in memory
                        //Restore it.
                        int tmpMemLoc = vrToPr[op2.getVirtualRegister()];
                        int position = getPR();

                        //Sets the PR in the table
                        table.get(i).op2.setPhysicalRegister(position);

                        //Sets PRToVR table
                        prToVr[position] = op2.getVirtualRegister();

                        //Sets the VRToPR table
                        vrToPr[op2.getVirtualRegister()] = position;

                        nextUse[position] = op2.getNextUse();
                        loadIPrint(op2.getVirtualRegister(), vrToPr[op2.getVirtualRegister()]);
                        System.out.println("loadI " + tmpMemLoc + " => " + "r" + (k - 1));
                        System.out.println("load " + "r" + (k - 1) + " => " + "r" + position);
                    }
                }else{

                    int position = getPR();

                    //Sets the PR in the table
                    table.get(i).op2.setPhysicalRegister(position);

                    //Sets PRToVR table
                    prToVr[position] = table.get(i).op2.getVirtualRegister();

                    //Sets the VRToPR table
                    vrToPr[table.get(i).op2.getVirtualRegister()] = position;

                    nextUse[position] = table.get(i).op2.getNextUse();
                    loadIPrint(table.get(i).op2.getVirtualRegister(), vrToPr[table.get(i).op2.getVirtualRegister()]);
                }
            }
            //Frees the op1, if it won't be used
            if (table.get(i).op1.getNextUse() == Integer.MAX_VALUE) {
                if (table.get(i).op1.getRinSR() == 1) {
                    //Free the corresponding PR
                    int vrRegisterPosition = table.get(i).op1.getVirtualRegister();
                    int positionFreed = vrToPr[vrRegisterPosition];
                    //Frees the VRToPR
                    vrToPr[vrRegisterPosition] = -1;
                    //Frees PRToVR

                    if(positionFreed > k) {
                        //Means that it is in memory
                        vrToPr[vrRegisterPosition] = -1;
                    }else{
                        prToVr[positionFreed] = -1;
                        nextUse[positionFreed] = -1;
                        //LIFO discipline. Pushes the freed position back to the empty stack.
                        emptyPrToVr.add(positionFreed);

                        //Sorts the empty list all the time so that it returns the empty position orderly
                        sortEmptyPrToVr();
                    }
                }
            }
            //Frees the op2
            if (table.get(i).op2.getNextUse() == Integer.MAX_VALUE) {
                if (table.get(i).op2.getRinSR() == 1) {
                    //Free the corresponding PR
                    int vrRegisterPosition = table.get(i).op2.getVirtualRegister();
                    int positionFreed = vrToPr[vrRegisterPosition];
                    //Frees the VRToPR
                    if (positionFreed > k) {
                        vrToPr[vrRegisterPosition] = -1;
                    }else{
                        //Frees PRToVR
                        prToVr[positionFreed] = -1;
                        nextUse[positionFreed] = -1;
                        //LIFO discipline. Pushes the freed position back to the empty stack.
                        emptyPrToVr.add(positionFreed);
                        sortEmptyPrToVr();
                    }
                }
            }


            if (!table.get(i).opCode.equals("store") && !table.get(i).opCode.equals("output")) {
                if(vrToPr[table.get(i).op3.getVirtualRegister()] > k) {
                    //Means that it's a restore process.
                    OpX op3 = table.get(i).op3;
                    int position = getPR();
                    table.get(i).op3.setPhysicalRegister(position);
                    //Sets PRToVR table
                    prToVr[position] = op3.getVirtualRegister();
                    //Sets the VRToPR table
                    vrToPr[op3.getVirtualRegister()] = position;

                    nextUse[position] = op3.getNextUse();
                    loadIPrint(op3.getVirtualRegister(), vrToPr[op3.getVirtualRegister()]);
                }else if(vrToPr[table.get(i).op3.getVirtualRegister()] <= k) {
                    OpX op3 = table.get(i).op3;
                    int position = getPR();
                    table.get(i).op3.setPhysicalRegister(position);
                    //Sets PRToVR table
                    prToVr[position] = op3.getVirtualRegister();
                    //Sets the VRToPR table
                    vrToPr[op3.getVirtualRegister()] = position;

                    nextUse[position] = op3.getNextUse();
                    loadIPrint(op3.getVirtualRegister(), vrToPr[op3.getVirtualRegister()]);


                }
            }
            /**
             * Prints out the code.
             * **/
            if(table.get(i).opCode.equals("loadI")) {
                System.out.println("loadI " + table.get(i).op1.getVirtualRegister() +
                        " => r" + table.get(i).op3.getPhysicalRegister());
            }else if(table.get(i).opCode.equals("output")) {
                System.out.println("output " +
                        table.get(i).op1.getVirtualRegister());
            }else if(table.get(i).opCode.equals("load")) {
                System.out.println("load " + "r" +
                        table.get(i).op1.getPhysicalRegister() +
                        " => r" + table.get(i).op3.getPhysicalRegister());
            }else if(table.get(i).opCode.equals("store")) {
                System.out.println("store " + "r" +
                        table.get(i).op1.getPhysicalRegister() +
                        " => r" + table.get(i).op2.getPhysicalRegister());
            }else{
                System.out.println(table.get(i).opCode +
                        " r" + table.get(i).op1.getPhysicalRegister() +
                        ", r" + table.get(i).op2.getPhysicalRegister() +
                        " => r" + table.get(i).op3.getPhysicalRegister() +
                        " // vr" + table.get(i).op1.getVirtualRegister() + ", vr"
                         + table.get(i).op2.getVirtualRegister() + " => vr" +
                        table.get(i).op3.getVirtualRegister());
            }
        }
    }

    private static void loadIPrint(int vr, int pr) {
        if(loadIArray[vr] != -1) {
            //Means this register has a constant value.
            System.out.println("loadI " + loadIArray[vr] + " => r" + pr);
        }
    }

    private static int getPR() {
        //Obtains the physical register.
        if(emptyPrToVr.size() != 0) {
            //Means there are still physical registers available.
            //LIFO discipline.
            return emptyPrToVr.remove(0);
        }else{
            //Means the physical register has run out.
            //So find a value to spill
            int memLoc = memoryLocator();
            int positionToSpill = findFarthestNextUse();
            int vrPosition = prToVr[positionToSpill];
            prToVr[positionToSpill] = -1;
            nextUse[positionToSpill] = -1;
            justSpilled[positionToSpill] = true;
            //This value is spilled to the memory and the memory position is stored in vrToPr.
            //Finds a memory location for this register.
            vrToPr[vrPosition] = memLoc;
            System.out.println("loadI " + memLoc + " => " + "r" + (k - 1) + " //Spilling pr" + positionToSpill + " (vr" + vrPosition + ")");
            System.out.println("store " + "r" + positionToSpill + " => " + "r" + (k - 1) + " //Spilling pr" + positionToSpill + " (vr" + vrPosition + ")");
            //LIFO discipline to push the empty pr to the emptyPrTpVr stack.
            emptyPrToVr.add(0, positionToSpill);
            sortEmptyPrToVr();
            return emptyPrToVr.remove(0);
        }
    }
    /**
     * Sorts the prToVr list
     * **/
    private static void sortEmptyPrToVr() {
        Collections.sort(emptyPrToVr, new Comparator<Integer>(){
            @Override
            public int compare(Integer o1, Integer o2){
                if(o1 < o2){
                    return -1;
                }
                if(o1 > o2){
                    return 1;
                }
                return 0;
            }
        });
    }
    /**
     * Finds the farthest PR.
     * **/
    private static int findFarthestNextUse() {
        //Finds the farthest use PR.
        int farthestUse = Integer.MIN_VALUE;
        int idx = -1;
        List<Integer> maxPushList = new LinkedList<>();
        for(int i=0;i < nextUse.length;i++) {
            if(nextUse[i] >= farthestUse && nextUse[i] != Integer.MAX_VALUE && !justSpilled[i]) {
                if(farthestUse == nextUse[i]) {
                    maxPushList.add(i);
                }
                farthestUse = nextUse[i];
                idx = i;

            }
        }
        if(maxPushList.size() > 1) {
            for(int i2 = 0; i2 < maxPushList.size(); i2++) {
                if(loadIArray[prToVr[maxPushList.get(i2)]] != -1) {
                    //Means that this value is rematerializable.
                    return i2;
                }
            }
        }

        return idx;
    }
    /**
     * Memory counter which counts the memory
     * **/
    private static int memoryCounter = 0;
    /**
     * Calculates the memory, which is after 32768
     * **/
    private static int memoryLocator() {
        int mem = memoryCounter * 4 + 32768;
        //Generates a new memory location for the spill.
        memoryCounter++;
        return mem;
    }

}
















