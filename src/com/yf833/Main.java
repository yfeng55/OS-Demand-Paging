package com.yf833;

public class Main {

    private static int M;       // the machine size in words
    private static int P;       // the page size in words
    private static int S;       // the size of a process
    private static int J;       // the job mix which determines A, B, and C
    private static int N;       // the number of references for each process
    private static String R;    // the replacement algorithm (FIFO, RANDOM, LRU)

    private static int debug_level;     // the level of debugging output (1: debug; 0: no debug)



    public static void main(String[] args) {

        // (1) read command line arguments //
        readInputParams(args);
        printParams();


        // (2) initialize processes based on the job mix specified (J)



    }



    //////////////////////////////////////////////////////////////////////////////////////////////////////////////


    // read input parameters
    public static void readInputParams(String[] args){

        if(args.length == 7){
            M = Integer.parseInt(args[0]);
            P = Integer.parseInt(args[1]);
            S = Integer.parseInt(args[2]);
            J = Integer.parseInt(args[3]);
            N = Integer.parseInt(args[4]);
            R = args[5].toLowerCase();
            debug_level = Integer.parseInt(args[6]);
        }else{
            throw new IllegalArgumentException("Incorrect number of arguments; must specify M, P, S, J, N, R, debug_level");
        }

    }

    // print the input parameters
    public static void printParams(){
        System.out.println("The machine size is " + M);
        System.out.println("The page size is " + P);
        System.out.println("The process size is " + S);
        System.out.println("The job mix number is " + J);
        System.out.println("The nuber of references per process is " + N);
        System.out.println("The replacement algorithm is " + R);
        System.out.println("The level of debugging output is " + debug_level);
    }


    //initialize processes based on job mix specified
    public static void initializeProcesses(){



    }



}
