package MMCalc;

import java.time.LocalDate;
import java.util.Scanner;

//import javax.lang.model.util.ElementScanner14;
//on the section using cmd ln sec6 2ndto  last video running calcengine
public class Main {
    /**
     * @param args
     */
    public static void main(String[] args) {
        //user passes in cmd ln args come in param to main method and args to args param
       //List of values and operations we want to perform
       double[] lVals = {100.0d, 25.0d, 220.0d, 11.0d};
       double[] rVals = {50.0d, 85.0d, 20.0d, 12.0d};
       char[] opCodes = {'d', 'a', 's', 'm'};
       double[] results =  new double[opCodes.length]; 

            //parallel arrays
            //looping through arrays
            //switch tied to arrays hard to use in another scenario
            //Add method handles details executing any operation
            //add cmd ln support - gives 2 distinct modes: 
            //user provides correct vals it calculates and user gives invalid or no args

        if (args.length == 0) { //check length of args param, if length 0 run like normal
            for(int i = 0; i < opCodes.length; i++) {
            execute(opCodes[i], lVals[i], rVals[i]);
        }

        for(double currentRes: results)
            System.out.println(currentRes);
        
        } else if(args.length == 1 && args[0].equals("interactive"))
            executeInteractively();
        else if (args.length == 3)//if 3 then we call handlecmdln method
            handleCommandLine(args);
        else
        System.out.println("You didn't put anything, helllooo"); //anything else pass error msg
    }

        //get data frm cmd ln to pass into execute method to do work
        //each indivi.strg considered a seq. of char need to convert to approp. type 

    static void executeInteractively() {
        System.out.println("Enter an operation and two numbers");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        String[] parts = userInput.split(regex " ");
        performOperation(parts);
    }

    private static void performOperation(String[] parts){
        char opCode = opCodeFromString(parts[0]);
        if(opCode == "w")
            handleWhen(parts);
        else {

            double lVal = valueFromWord(parts[1]);
            double rVal = valueFromWord(parts[2]);
            double result = execute(opCode, lVal, rVal);
            displayResult(opCode, lVal, rVal,result);
        }
    } 

    private static void handleWhen(String[] parts) {

        LocalDate startDate = LocalDate.parse(parts[1]);
        long daysToAdd = valueFromWord(parts[2]);
        LocalDate newDate = startDate.plusDays(daysToAdd);

        String output = String.format("%s plus %d days is %s", startDate, daysToAdd, newDate);
        System.out.println(output);
    }

    private static void displayResult(char opCode, double lVal, double rVal, double result) {
        char symbol = symbolFromOpCode(opCode);
        StringBuilder builder = new StringBuilder(capacity: 20);
        builder.append(lVal);
        builder.append(" ");
        builder.append(symbol);
        builder.append(" ");

        builder.append(rVal);
        builder.append(" ");
        builder.append("=");
        builder.append(result);
        String output = String.format("%f %c %f = %.3f", lVal, symbol, rVal, result);
        System.out.println(output);
         //need to call tostring
    }

    private static char symbolFromOpCode(char opCode){
        char[] opCodes = {'a', 's', 'm', 'd'}; //ex.of parallel arr both correspond to each other
        char[] symbols = {'+', '-', '*', '/'};
        char symbol = ' ';
        for(int i = 0; i < opCodes.length; i++) {
            if(opCode == opCodes[i]){
                symbol = symbols[i];
                break;
            }
        }
        return symbol;
    }
    
    private static void handleCommandLine(String[] args) {
        char opCode = args[0].charAt(0);//converts strg val in cmdln into correct datatypes
        double lVal = Double.parseDouble(args[1]);
        double rVal = Double.parseDouble(args[2]);
        double result = execute(opCode, lVal, rVal);
        System.out.println(result); //gives code needed to process cmd line
    }
    //details is in execute method
    
    static double execute(char opCode, double lVal, double rVal){
        double result;
        switch(opCode){
            case 'a':
                result = lVal + rVal;
                break;
            case 's':
                result = lVal - rVal;
                break; 
            case 'm':
                result = lVal * rVal;
                break;
            case 'd':
                result = rVal != 0 ? lVal / rVal : 0.0d;
                break; 
            default:
                System.out.println("Eh! Try Again" + opCode);
                result = 0.0d;
                break;    
        }
        return result;  


    }

    static char opCodeFromString(String operationName){
        char opCode = operationName.charAt(0);
        return opCode;
    }

    static double valFromWord(String word) {
        String[] numOfWords = {
            "zero", "one", "two", "three", "four",
             "five", "six", "seven", "eight", "nine"
        };

        double value = -1d;
        for(int index = 0; index < numOfWords.length; index++){
            if(word.equals(numOfWords[index])) {
                value = index;
                break; //allows to jump out of the switch/loop
            }
        }

        if(value == -1d)
            value = Double.parseDouble(word); //dblwrapperclass-parse val from str.
        return value;                          //can handle numeric val now
    }

}

