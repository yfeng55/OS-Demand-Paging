package com.yf833;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static final String RANDOM_NUMBERS_FILE = "random-numbers.txt";
    private static final long RAND_MAX = 2147483648L;
    private static final int QUANTUM = 3;

    private static int M;       // the machine size in words
    private static int P;       // the page size in words
    private static int S;       // the size of a process
    private static int J;       // the job mix which determines A, B, and C
    private static int N;       // the number of references for each process
    private static String R;    // the replacement algorithm (FIFO, RANDOM, LRU)
    private static int debug_level;     // the level of debugging output (1: debug; 0: no debug)

    private static Scanner rand_scanner;
    private static ArrayList<Process> processes;
    private static ArrayList<FTE> frame_table;



    public static void main(String[] args) throws FileNotFoundException {

        // (1) read command line arguments //
        readInputParams(args);
        printParams();


        // (2) initialize processes based on the job mix specified (J)
        processes = initProcesses();
        System.out.println(processes.toString());


        // (3) initialize random scanner
        rand_scanner = new Scanner(new File(RANDOM_NUMBERS_FILE));


    }



    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void runSimulation(){


        // while there are unfinished processes
        while(!processes.isEmpty()){



        }


    }










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
    public static ArrayList<Process> initProcesses(){

        ArrayList<Process> new_processes = new ArrayList<>();

        // create processes according to the value of J (job mix)
        switch(J){

            case 1:
                // create one process with A=1, B=0, C=0
                new_processes.add(new Process(1, 1.0, 0.0, 0.0));
                break;

            case 2:
                // create four processes, each with A=1, B=0, C=0
                for(int i=0; i<4; i++){
                    new_processes.add(new Process(i+1, 1.0, 0.0, 0.0));
                }
                break;

            case 3:
                // create four processes, each with A=0, B=0, C=0
                for(int i=0; i<4; i++){
                    new_processes.add(new Process(i+1, 0.0, 0.0, 0.0));
                }
                break;

            case 4:
                // create one process with A=0.75, B=0.25, C=0
                // create one process with A=0.75, B=0, C=0.25
                // create one process with A=0.75, B=0.125, C=0.125
                // create one process with A=0.5, B=0.125, C=0.125
                new_processes.add(new Process(1, 0.75, 0.25, 0.0));
                new_processes.add(new Process(2, 0.75, 0.0, 0.25));
                new_processes.add(new Process(3, 0.75, 0.125, 0.125));
                new_processes.add(new Process(4, 0.5, 0.125, 0.125));
                break;

        }

        return new_processes;
    }



}
