/**
 * Enum class designed to implement thd DFA demonstrated in the class. Every time arriving at a new state,
 * there will be
 * **/
enum States implements State {

    Init {
        @Override
        public State next(Input word) {

            switch(word.read()) {
                case 's':return S1;
                case 'l':lshiftRshift.add("l");return S8;
                case 'r':lshiftRshift.add("r");return S13;
                case 'm':return S19;
                case 'a':return S22;
                case 'n':return S25;
                case 'o':return S28;
                case ' ': return Init;
                case '\t': return Init;
                case '/': return Comment;
                case '\n': return Success;
                default: System.err.println("Init");return Fail;
            }
        }
        @Override
        public void stringConcat(char inputChar){
            currentOpRegList.add(String.valueOf(inputChar));
        }

    },

    Comment {
        @Override
        public State next(Input word) {
            switch(word.read()) {
                case '/': return Success;
                default: System.err.println("Comment");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    S1 {
        @Override
        public State next(Input word) {
            switch(word.read()) {
                case 't': return S2;
                case 'u': return S6;
                default: System.err.println("S1");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){
            currentOpRegList.add(String.valueOf(inputChar));
        }
    },

    S2 {
        @Override
        public State next(Input word) {
            switch(word.read()) {
                case 'o':return S3;

                default: System.err.println("S2");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){
            currentOpRegList.add(String.valueOf(inputChar));
        }
    },

    S3 {
        @Override
        public State next(Input word) {
            switch(word.read()) {
                case 'r': return S4;

                default: System.err.println("S3");return Fail;
            }
        }

        @Override
        public void stringConcat(char inputChar){
            currentOpRegList.add(String.valueOf(inputChar));
        }
    },

    S4 {
        @Override
        public State next(Input word) {
            switch(word.read()) {
                case 'e':return S5;

                default: System.err.println("S4");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){
            currentOpRegList.add(String.valueOf(inputChar));
        }
    },

    S5 {
        @Override
        public State next(Input word) {
            switch(word.read()) {
                case ' ': stack.add("store");return S5TRANSIT;
                case '\t': stack.add("store");return S5TRANSIT;
                default: System.err.println("S5");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    S5TRANSIT {
        @Override
        public State next(Input word) {
            switch(word.read()) {
                case ' ': return S5TRANSIT;
                case '\t': return S5TRANSIT;
                case 'r': return SINGLEOPSTART;
                default: System.err.println("S5TRANSIT");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    S6 {
        @Override
        public State next(Input word) {
            switch(word.read()) {
                case 'b': return S7;

                default: System.err.println("S6");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    S7 {
        @Override
        public State next(Input word) {
            switch(word.read()) {
                case ' ': stack.add("sub");return S7TRANSIT;
                case '\t': stack.add("sub");return S7TRANSIT;
                default: System.err.println("S7");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    S7TRANSIT {
        @Override
        public State next(Input word) {
            switch(word.read()) {
                case ' ': return S7TRANSIT;
                case '\t': return S7TRANSIT;
                case 'r': return DOUBLEOPSTART;
                default: System.err.println("S7TRANSIT");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    S8 {
        @Override
        public State next(Input word) {
            switch(word.read()) {
                case 'o': return S9;
                case 's': return S14;

                default: System.err.println("S8");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    S9 {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case 'a': return S10;
                default: System.err.println("S9");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    S10 {
        @Override
        public State next(Input word) {
            switch(word.read()) {
                case 'd': return S11;

                default: System.err.println("S10");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    S11 {
        @Override
        public State next(Input word) {
            switch(word.read()) {
                case ' ': stack.add("load");return LoadTransit;
                case '\t': stack.add("load");return LoadTransit;
                case 'I': return S12TRANSIT;
                default: System.err.println("S11");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    LoadTransit {
        @Override
        public State next(Input word) {
            switch(word.read()) {
                case ' ': return LoadTransit;
                case '\t': return LoadTransit;
                case 'r': return SINGLEOPSTART;

                default: System.err.println("LoadTransit");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    S12TRANSIT {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case ' ': stack.add("loadI");return S12TRANSIT2;
                case '\t': stack.add("loadI");return S12TRANSIT2;
                default: System.err.println("S12TRANSIT");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    S12TRANSIT2 {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case ' ': return S12TRANSIT2;
                case '\t': return S12TRANSIT2;

                case '0': op1.add(0);return S12TRANSIT3;
                case '1': op1.add(1);return LoadICONSTANTSTART;
                case '2': op1.add(2);return LoadICONSTANTSTART;
                case '3': op1.add(3);return LoadICONSTANTSTART;
                case '4': op1.add(4);return LoadICONSTANTSTART;
                case '5': op1.add(5);return LoadICONSTANTSTART;
                case '6': op1.add(6);return LoadICONSTANTSTART;
                case '7': op1.add(7);return LoadICONSTANTSTART;
                case '8': op1.add(8);return LoadICONSTANTSTART;
                case '9': op1.add(9);return LoadICONSTANTSTART;

                default: System.err.println("S12TRANSIT2");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },
    S12TRANSIT3 {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case ' ': return SINGLEOPTHIRD;
                case '\t': return SINGLEOPTHIRD;
                case '=': return SINGLEOPFOURTH;

                default: System.err.println("S12TRANSIT3");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },


    S13 {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case 's': return S14;
                default: System.err.println("S13");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    S14 {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case 'h': return S15;
                default: System.err.println("S14");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    S15 {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case 'i': return S16;
                default: System.err.println("S15");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    S16 {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case 'f': return S17;
                default: System.err.println("S16");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    S17 {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case 't': if(lshiftRshift.size() == 1){String tmp = lshiftRshift.remove(0) + "shift";stack.add(tmp);}return S18;
                default: System.err.println("S17");return Fail;
            }
        }

        @Override
        public void stringConcat(char inputChar){

        }
    },


    S18 {
        //End of rshift or lshift
        @Override
        public State next(Input word) {
            switch(word.read()) {
                case ' ': return S18TRANSIT;
                case '\t': return S18TRANSIT;
                default: System.err.println("S18");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },
    S18TRANSIT {
        //End of rshift
        @Override
        public State next(Input word) {
            switch(word.read()) {
                case ' ': return S18TRANSIT;
                case '\t': return S18TRANSIT;
                case 'r': return DOUBLEOPSTART;
                default: System.err.println("S18TRANSIT");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    S19 {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case 'u': return S20;
                default: System.err.println("S19");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    S20 {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case 'l': return S21;
                default: System.err.println("S20");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    S21 {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case 't': stack.add("mult");return S18;
                default: System.err.println("S21");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    S22 {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case 'd': return S23;
                default: System.err.println("S22");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    S23 {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case 'd': return S24;
                default: System.err.println("S23");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    S24 {
        @Override
        public State next(Input word) {
            switch(word.read()) {
                case ' ': stack.add("add");return S24TRANSIT;
                case '\t': stack.add("add");return S24TRANSIT;
                default: System.err.println("S24");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },
    S24TRANSIT {
        @Override
        public State next(Input word) {
            switch(word.read()) {
                case ' ': return S24TRANSIT2;
                case '\t': return S24TRANSIT2;
                case 'r': return DOUBLEOPSTART;
                default: System.err.println("S24TRANSIT");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },
    S24TRANSIT2 {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case ' ': return S24TRANSIT2;
                case '\t': return S24TRANSIT2;
                case 'r': return DOUBLEOPSTART;
                default: System.err.println("S24TRANSIT2");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },
    S25 {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case 'o': return S26;
                default: System.err.println("S25");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    S26 {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case 'p': return S27;
                default: System.err.println("S26");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    S27 {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case ' ': return S27;
                case '\t': return S27;
                case '\n': return Success;
                default: System.err.println("S27");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    S28 {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case 'u': return S29;
                default: System.err.println("S28");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    S29 {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case 't': return S30;
                default: System.err.println("S29");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    S30 {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case 'p': return S31;
                default: System.err.println("S30");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    S31 {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case 'u': return S32;
                default: System.err.println("S31");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    S32 {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case 't': return S33;
                default: System.err.println("S32");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    S33 {
        @Override
        public State next(Input word) {
            switch(word.read()) {
                case ' ': stack.add("output");return S33TRANSIT;
                case '\t': stack.add("output");return S33TRANSIT;
                default: System.err.println("S33");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },


    S33TRANSIT {
        @Override
        public State next(Input word) {
            switch(word.read()) {
                case '0': op1.add(0);return SINGLEOPSEVENTH;
                case '1': op1.add(1);return CONSTANTSTART;
                case '2': op1.add(2);return CONSTANTSTART;
                case '3': op1.add(3);return CONSTANTSTART;
                case '4': op1.add(4);return CONSTANTSTART;
                case '5': op1.add(5);return CONSTANTSTART;
                case '6': op1.add(6);return CONSTANTSTART;
                case '7': op1.add(7);return CONSTANTSTART;
                case '8': op1.add(8);return CONSTANTSTART;
                case '9': op1.add(9);return CONSTANTSTART;

                case ' ': return S33TRANSIT;
                case '\t': return S33TRANSIT;
                default: System.err.println("S33TRANSIT");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },
    //Parses constants after loadI and output
    CONSTANTSTART {
        @Override
        public State next(Input word) {
            switch(word.read()) {
                case '0': stringConcat('0');return CONSTANTSTART;
                case '1': stringConcat('1');return CONSTANTSTART;
                case '2': stringConcat('2');return CONSTANTSTART;
                case '3': stringConcat('3');return CONSTANTSTART;
                case '4': stringConcat('4');return CONSTANTSTART;
                case '5': stringConcat('5');return CONSTANTSTART;
                case '6': stringConcat('6');return CONSTANTSTART;
                case '7': stringConcat('7');return CONSTANTSTART;
                case '8': stringConcat('8');return CONSTANTSTART;
                case '9': stringConcat('9');return CONSTANTSTART;

                case '\n': return Success;
                case ' ': return SINGLEOPSEVENTH;//needs to store the number
                case '\t': return SINGLEOPSEVENTH;//needs to store the number
                case '/': return Comment;//needs to store the number
                default: System.err.println("CONSTANTSTART");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){
            op1.add(0, op1.get(0) * 10 + Character.getNumericValue(inputChar));
            op1.remove(1);
        }
    },


    LoadICONSTANTSTART {
        @Override
        public State next(Input word) {
            switch(word.read()) {
                case '0': stringConcat('0');return LoadICONSTANTSTART;
                case '1': stringConcat('1');return LoadICONSTANTSTART;
                case '2': stringConcat('2');return LoadICONSTANTSTART;
                case '3': stringConcat('3');return LoadICONSTANTSTART;
                case '4': stringConcat('4');return LoadICONSTANTSTART;
                case '5': stringConcat('5');return LoadICONSTANTSTART;
                case '6': stringConcat('6');return LoadICONSTANTSTART;
                case '7': stringConcat('7');return LoadICONSTANTSTART;
                case '8': stringConcat('8');return LoadICONSTANTSTART;
                case '9': stringConcat('9');return LoadICONSTANTSTART;


                case ' ': return SINGLEOPTHIRD;//needs to store the number
                case '\t': return SINGLEOPTHIRD;//needs to store the number
                case '=': return SINGLEOPFOURTH;

                default: System.err.println("LoadICONSTANTSTART");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){
            op1.add(0, op1.get(0) * 10 + Character.getNumericValue(inputChar));
            op1.remove(1);
        }
    },










    //Parses single-operand operations
    SINGLEOPSTART {
        @Override
        public State next(Input word) {
            switch(word.read()) {
                case '0': return SINGLEOPTRANSIT1;
                case '1': op1.add(1); return SINGLEOPSECOND;
                case '2': op1.add(2); return SINGLEOPSECOND;
                case '3': op1.add(3); return SINGLEOPSECOND;
                case '4': op1.add(4); return SINGLEOPSECOND;
                case '5': op1.add(5); return SINGLEOPSECOND;
                case '6': op1.add(6); return SINGLEOPSECOND;
                case '7': op1.add(7); return SINGLEOPSECOND;
                case '8': op1.add(8); return SINGLEOPSECOND;
                case '9': op1.add(9); return SINGLEOPSECOND;

                default: System.err.println("SINGLEOPSTART");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    SINGLEOPTRANSIT1 {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case '0': return SINGLEOPTRANSIT1;
                case ' ': op1.add(0);return SINGLEOPTHIRD;//push 0
                case '\t': op1.add(0);return SINGLEOPTHIRD;//push 0
                case '=': op1.add(0);return SINGLEOPFOURTH;//push 0

                case '1': op1.add(1);return SINGLEOPSECOND;
                case '2': op1.add(2);return SINGLEOPSECOND;
                case '3': op1.add(3);return SINGLEOPSECOND;
                case '4': op1.add(4);return SINGLEOPSECOND;
                case '5': op1.add(5);return SINGLEOPSECOND;
                case '6': op1.add(6);return SINGLEOPSECOND;
                case '7': op1.add(7);return SINGLEOPSECOND;
                case '8': op1.add(8);return SINGLEOPSECOND;
                case '9': op1.add(9);return SINGLEOPSECOND;


                default: System.err.println("SINGLEOPTRANSIT");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },


    SINGLEOPSECOND {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case '0': stringConcat('0');return SINGLEOPSECOND;

                case '1': stringConcat('1');return SINGLEOPSECOND;
                case '2': stringConcat('2');return SINGLEOPSECOND;
                case '3': stringConcat('3');return SINGLEOPSECOND;
                case '4': stringConcat('4');return SINGLEOPSECOND;
                case '5': stringConcat('5');return SINGLEOPSECOND;
                case '6': stringConcat('6');return SINGLEOPSECOND;
                case '7': stringConcat('7');return SINGLEOPSECOND;
                case '8': stringConcat('8');return SINGLEOPSECOND;
                case '9': stringConcat('9');return SINGLEOPSECOND;

                case ' ': return SINGLEOPTHIRD;case '\t': return SINGLEOPTHIRD;
                case '=': return SINGLEOPFOURTH;
                default: System.err.println("SINGLEOPSECOND");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){
            op1.add(0, op1.get(0) * 10 + Character.getNumericValue(inputChar));
            op1.remove(1);
        }
    },

    SINGLEOPTHIRD {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case '=': return SINGLEOPFOURTH;
                case ' ': return SINGLEOPTHIRD;
                case '\t': return SINGLEOPTHIRD;

                default: System.err.println("SINGLEOPTHIRD");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    SINGLEOPFOURTH {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case '>': return SINGLEOPFIFTH;

                default: System.err.println("SINGLEOPFOURTH");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    SINGLEOPFIFTH {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case ' ': return SINGLEOPFIFTH;
                case '\t': return SINGLEOPFIFTH;
                case 'r': return SINGLEOPSIXTH;
                default: System.err.println("SINGLEOPFIFTH");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    SINGLEOPSIXTH {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case '0': return SINGLEOPTRANSIT2;
                case '1': op3.add(1);return SINGLEOPEIGHTH;
                case '2': op3.add(2);return SINGLEOPEIGHTH;
                case '3': op3.add(3);return SINGLEOPEIGHTH;
                case '4': op3.add(4);return SINGLEOPEIGHTH;
                case '5': op3.add(5);return SINGLEOPEIGHTH;
                case '6': op3.add(6);return SINGLEOPEIGHTH;
                case '7': op3.add(7);return SINGLEOPEIGHTH;
                case '8': op3.add(8);return SINGLEOPEIGHTH;
                case '9': op3.add(9);return SINGLEOPEIGHTH;
                //

                default: System.err.println("SINGLEOPSIXTH");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    SINGLEOPTRANSIT2 {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case '0': return SINGLEOPTRANSIT2;
                case '1': op3.add(1);return SINGLEOPEIGHTH;
                case '2': op3.add(2);return SINGLEOPEIGHTH;
                case '3': op3.add(3);return SINGLEOPEIGHTH;
                case '4': op3.add(4);return SINGLEOPEIGHTH;
                case '5': op3.add(5);return SINGLEOPEIGHTH;
                case '6': op3.add(6);return SINGLEOPEIGHTH;
                case '7': op3.add(7);return SINGLEOPEIGHTH;
                case '8': op3.add(8);return SINGLEOPEIGHTH;
                case '9': op3.add(9);return SINGLEOPEIGHTH;

                case ' ': op3.add(0);return SINGLEOPSEVENTH;
                case '\t': op3.add(0);return SINGLEOPSEVENTH;
                case '\n': op3.add(0);return Success;
                case '/': return Comment;

                default: System.err.println("SINGLEOPTRANSIT2");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },
    SINGLEOPEIGHTH {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case ' ': return SINGLEOPSEVENTH;
                case '\t': return SINGLEOPSEVENTH;
                case '0': stringConcat('0');return SINGLEOPEIGHTH;
                case '1': stringConcat('1');return SINGLEOPEIGHTH;
                case '2': stringConcat('2');return SINGLEOPEIGHTH;
                case '3': stringConcat('3');return SINGLEOPEIGHTH;
                case '4': stringConcat('4');return SINGLEOPEIGHTH;
                case '5': stringConcat('5');return SINGLEOPEIGHTH;
                case '6': stringConcat('6');return SINGLEOPEIGHTH;
                case '7': stringConcat('7');return SINGLEOPEIGHTH;
                case '8': stringConcat('8');return SINGLEOPEIGHTH;
                case '9': stringConcat('9');return SINGLEOPEIGHTH;

                case '/': return Comment;
                case '\n': return Success;
                default: System.err.println("SINGLEOPEIGHTH");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){
            op3.add(0, op3.get(0) * 10 + Character.getNumericValue(inputChar));
            op3.remove(1);
        }
    },

    SINGLEOPSEVENTH {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case ' ': return SINGLEOPSEVENTH;
                case '\t': return SINGLEOPSEVENTH;
                case '/': return Comment;
                case '\n': return Success;
                default: System.err.println("SINGLEOPSEVENTH");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },







    //Parses double operands
    DOUBLEOPSTART {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case '0': return DOUBLEOPTRANSIT1;

                case '1': op1.add(1);return DOUBLEOPSTARTTRANSIT;
                case '2': op1.add(2);return DOUBLEOPSTARTTRANSIT;
                case '3': op1.add(3);return DOUBLEOPSTARTTRANSIT;
                case '4': op1.add(4);return DOUBLEOPSTARTTRANSIT;
                case '5': op1.add(5);return DOUBLEOPSTARTTRANSIT;
                case '6': op1.add(6);return DOUBLEOPSTARTTRANSIT;
                case '7': op1.add(7);return DOUBLEOPSTARTTRANSIT;
                case '8': op1.add(8);return DOUBLEOPSTARTTRANSIT;
                case '9': op1.add(9);return DOUBLEOPSTARTTRANSIT;

                default:  System.out.println("DOUBLEOPSTART");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    DOUBLEOPTRANSIT1 {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case '0': return DOUBLEOPTRANSIT1;

                case '1': op1.add(1);return DOUBLEOPSTARTTRANSIT;
                case '2': op1.add(2);return DOUBLEOPSTARTTRANSIT;
                case '3': op1.add(3);return DOUBLEOPSTARTTRANSIT;
                case '4': op1.add(4);return DOUBLEOPSTARTTRANSIT;
                case '5': op1.add(5);return DOUBLEOPSTARTTRANSIT;
                case '6': op1.add(6);return DOUBLEOPSTARTTRANSIT;
                case '7': op1.add(7);return DOUBLEOPSTARTTRANSIT;
                case '8': op1.add(8);return DOUBLEOPSTARTTRANSIT;
                case '9': op1.add(9);return DOUBLEOPSTARTTRANSIT;


                case ' ': op1.add(0);return DOUBLEOPSECOND;case '\t': op1.add(0);return DOUBLEOPSECOND;
                case ',': op1.add(0);return DOUBLEOPTHIRD;
                default: System.err.println("DOUBLEOPTRANSIT1");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    DOUBLEOPSTARTTRANSIT {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case '0': stringConcat('0');return DOUBLEOPSTARTTRANSIT;

                case '1': stringConcat('1');return DOUBLEOPSTARTTRANSIT;
                case '2': stringConcat('2');return DOUBLEOPSTARTTRANSIT;
                case '3': stringConcat('3');return DOUBLEOPSTARTTRANSIT;
                case '4': stringConcat('4');return DOUBLEOPSTARTTRANSIT;
                case '5': stringConcat('5');return DOUBLEOPSTARTTRANSIT;
                case '6': stringConcat('6');return DOUBLEOPSTARTTRANSIT;
                case '7': stringConcat('7');return DOUBLEOPSTARTTRANSIT;
                case '8': stringConcat('8');return DOUBLEOPSTARTTRANSIT;
                case '9': stringConcat('9');return DOUBLEOPSTARTTRANSIT;

                case ' ': return DOUBLEOPSECOND;case '\t': return DOUBLEOPSECOND;

                case ',': return DOUBLEOPTHIRD;
                default: System.err.println("DOUBLESTARTTRANSIT");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){
            op1.add(0, op1.get(0) * 10 + Character.getNumericValue(inputChar));
            op1.remove(1);
        }
    },

    DOUBLEOPSECOND {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case ' ': return DOUBLEOPSECOND;
                case '\t': return DOUBLEOPSECOND;
                case ',': return DOUBLEOPTHIRD;
                default: System.err.println("DOUBLEOPSECOND");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    DOUBLEOPTHIRD {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case 'r': return DOUBLEOPFOURTH;
                case ' ': return DOUBLEOPTHIRD;
                case '\t': return DOUBLEOPTHIRD;

                default: System.err.println("DOUBLEOPTHIRD");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    DOUBLEOPFOURTH {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case '0': return DOUBLEOPTRANSIT2;
                case '1': op2.add(1);return DOUBLEOPFOURTHTRANSIT;
                case '2': op2.add(2);return DOUBLEOPFOURTHTRANSIT;
                case '3': op2.add(3);return DOUBLEOPFOURTHTRANSIT;
                case '4': op2.add(4);return DOUBLEOPFOURTHTRANSIT;
                case '5': op2.add(5);return DOUBLEOPFOURTHTRANSIT;
                case '6': op2.add(6);return DOUBLEOPFOURTHTRANSIT;
                case '7': op2.add(7);return DOUBLEOPFOURTHTRANSIT;
                case '8': op2.add(8);return DOUBLEOPFOURTHTRANSIT;
                case '9': op2.add(9);return DOUBLEOPFOURTHTRANSIT;


                ///
                default: System.err.println("DOUBLEOPFOURTH");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    DOUBLEOPTRANSIT2 {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case '0': return DOUBLEOPTRANSIT2;
                case '1': op2.add(1);return DOUBLEOPFOURTHTRANSIT;
                case '2': op2.add(2);return DOUBLEOPFOURTHTRANSIT;
                case '3': op2.add(3);return DOUBLEOPFOURTHTRANSIT;
                case '4': op2.add(4);return DOUBLEOPFOURTHTRANSIT;
                case '5': op2.add(5);return DOUBLEOPFOURTHTRANSIT;
                case '6': op2.add(6);return DOUBLEOPFOURTHTRANSIT;
                case '7': op2.add(7);return DOUBLEOPFOURTHTRANSIT;
                case '8': op2.add(8);return DOUBLEOPFOURTHTRANSIT;
                case '9': op2.add(9);return DOUBLEOPFOURTHTRANSIT;


                case ' ': op2.add(0);return DOUBLEOPFIFTH;//push 0
                case '\t': op2.add(0);return DOUBLEOPFIFTH;//push 0
                case '=': op2.add(0);return DOUBLEOPSIXTH;
                default: System.err.println("DOUBLEOPTRANSIT2");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    DOUBLEOPFOURTHTRANSIT {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case '0': stringConcat('0');return DOUBLEOPFOURTHTRANSIT;

                case '1': stringConcat('1');return DOUBLEOPFOURTHTRANSIT;
                case '2': stringConcat('2');return DOUBLEOPFOURTHTRANSIT;
                case '3': stringConcat('3');return DOUBLEOPFOURTHTRANSIT;
                case '4': stringConcat('4');return DOUBLEOPFOURTHTRANSIT;
                case '5': stringConcat('5');return DOUBLEOPFOURTHTRANSIT;
                case '6': stringConcat('6');return DOUBLEOPFOURTHTRANSIT;
                case '7': stringConcat('7');return DOUBLEOPFOURTHTRANSIT;
                case '8': stringConcat('8');return DOUBLEOPFOURTHTRANSIT;
                case '9': stringConcat('9');return DOUBLEOPFOURTHTRANSIT;

                case ' ': return DOUBLEOPFIFTH;
                case '\t': return DOUBLEOPFIFTH;
                case '=': return DOUBLEOPSIXTH;
                default: System.err.println("DOUBLEOPFOURTHTRANSIT");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){
            op2.add(0, op2.get(0) * 10 + Character.getNumericValue(inputChar));
            op2.remove(1);
        }
    },
    DOUBLEOPFIFTH {
        @Override
        public State next(Input word) {
            switch(word.read()) {
                case '=': return DOUBLEOPSIXTH;
                case ' ': return DOUBLEOPFIFTH;
                case '\t': return DOUBLEOPFIFTH;
                default: System.out.println(word.input.charAt(word.current));System.out.println("DOUBLEOPFIFTH");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    DOUBLEOPSIXTH {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case '>': return DOUBLEOPSEVENTH;
                default: System.err.println("DOUBLEOPSIXTH");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    DOUBLEOPSEVENTH {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case ' ': return DOUBLEOPSEVENTH;case '\t': return DOUBLEOPSEVENTH;
                case 'r': return DOUBLEOPEIGHTH;
                default: System.err.println("DOUBLEOPSEVENTH");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    DOUBLEOPEIGHTH {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case '0': return DOUBLEOPTRANSIT3;
                case '1': op3.add(1);return DOUBLEOPEIGHTHTRANSIT;
                case '2': op3.add(2);return DOUBLEOPEIGHTHTRANSIT;
                case '3': op3.add(3);return DOUBLEOPEIGHTHTRANSIT;
                case '4': op3.add(4);return DOUBLEOPEIGHTHTRANSIT;
                case '5': op3.add(5);return DOUBLEOPEIGHTHTRANSIT;
                case '6': op3.add(6);return DOUBLEOPEIGHTHTRANSIT;
                case '7': op3.add(7);return DOUBLEOPEIGHTHTRANSIT;
                case '8': op3.add(8);return DOUBLEOPEIGHTHTRANSIT;
                case '9': op3.add(9);return DOUBLEOPEIGHTHTRANSIT;


                default: System.err.println("DOUBLEOPEIGHTH");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    DOUBLEOPTRANSIT3 {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case '0': return DOUBLEOPTRANSIT3;
                case '1': op3.add(1);return DOUBLEOPEIGHTHTRANSIT;
                case '2': op3.add(2);return DOUBLEOPEIGHTHTRANSIT;
                case '3': op3.add(3);return DOUBLEOPEIGHTHTRANSIT;
                case '4': op3.add(4);return DOUBLEOPEIGHTHTRANSIT;
                case '5': op3.add(5);return DOUBLEOPEIGHTHTRANSIT;
                case '6': op3.add(6);return DOUBLEOPEIGHTHTRANSIT;
                case '7': op3.add(7);return DOUBLEOPEIGHTHTRANSIT;
                case '8': op3.add(8);return DOUBLEOPEIGHTHTRANSIT;
                case '9': op3.add(9);return DOUBLEOPEIGHTHTRANSIT;


                case '\n': op3.add(0);return Success;
                case '/': op3.add(0);return Comment;
                case ' ': op3.add(0);return DOUBLEOPNINTH;case '\t': op3.add(0);return DOUBLEOPNINTH;
                default: System.err.println("DOUBLEOPTRANSIT3");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },


    DOUBLEOPEIGHTHTRANSIT {
        @Override
        public State next(Input word) {
            switch(word.read()) {

                case '0': stringConcat('0');return DOUBLEOPEIGHTHTRANSIT;
                case '1': stringConcat('1');return DOUBLEOPEIGHTHTRANSIT;
                case '2': stringConcat('2');return DOUBLEOPEIGHTHTRANSIT;
                case '3': stringConcat('3');return DOUBLEOPEIGHTHTRANSIT;
                case '4': stringConcat('4');return DOUBLEOPEIGHTHTRANSIT;
                case '5': stringConcat('5');return DOUBLEOPEIGHTHTRANSIT;
                case '6': stringConcat('6');return DOUBLEOPEIGHTHTRANSIT;
                case '7': stringConcat('7');return DOUBLEOPEIGHTHTRANSIT;
                case '8': stringConcat('8');return DOUBLEOPEIGHTHTRANSIT;
                case '9': stringConcat('9');return DOUBLEOPEIGHTHTRANSIT;


                case ' ': return DOUBLEOPNINTH;case '\t': return DOUBLEOPNINTH;
                case '/': return Comment;
                case '\n': return Success;
                default: System.err.println("DOUBLEOPEIGHTHTRANSIT");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){
            op3.add(0, op3.get(0) * 10 + Character.getNumericValue(inputChar));
            op3.remove(1);
        }
    },
    DOUBLEOPNINTH {
        @Override
        public State next(Input word) {
            switch(word.read()) {
                case ' ': return DOUBLEOPNINTH;
                case '\t': return DOUBLEOPNINTH;
                case '\n': return Success;
                case '/': return Comment;
                default: System.err.println("DOUBLEOPNINTH");return Fail;
            }
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },

    //Status enum classes.
    Success {
        @Override
        public State next(Input word) {
            return Success;
        }


        @Override
        public void stringConcat(char inputChar){

        }
    },
    Fail {
        @Override
        public State next(Input word) {
            return Fail;
        }


        @Override
        public void stringConcat(char inputChar){
            currentOpRegList.add(String.valueOf(inputChar));
        }

    }

}