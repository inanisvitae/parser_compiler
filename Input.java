/**
 * The input class which reads in the next character and send it to the DFA
 * **/
class Input {
    public String input;
    public int current;
    public Input(String input) {this.input = input;}
    char read() {
        if(current < input.length()){
            return input.charAt(current++);
        }else{
            return '\n';
        }
    }
}