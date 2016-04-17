package com.yf833;

import java.io.File;
import java.io.FileNotFoundException;
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
    private static String R;    // the replacement algorithm (fifo, RANDOM, LRU)
    private static int debug_level;     // the level of debugging output (1: debug; 0: no debug)

    private static int number_of_pages;
    private static Scanner rand_scanner;
    private static ArrayList<Process> processes;
    private static ArrayList<FTE> frame_table;



    public static void main(String[] args) throws FileNotFoundException {

        // (1) read command line arguments //
        readInputParams(args);
        printParams();


        // (2) initialize processes based on the job mix specified (J)
        processes = initProcesses();
        System.out.println();

        // (3) initialize the frame table with default values
        frame_table = initFrameTable();


        // (4) initialize random scanner
        rand_scanner = new Scanner(new File(RANDOM_NUMBERS_FILE));

        // (5) run the simulation
        runSimulation();

        // (6) print output
        Util.printOutput(processes);
    }



    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void runSimulation(){

        int cycle = 1;                  // time counter

        // while there are unfinished processes
        while(!Util.processesAreFinished(processes)){

            //for each process in the collection, simluate it's assigned quantum
            for(Process p : processes){
                for(int current=0; current<QUANTUM; current++) {

                    // if a process is finished, break out of the loop
                    if (p.finished == true) {
                        break;
                    }


                    // CASE 1: PAGE HIT //
                    if (Util.isHit(frame_table, p)) {

                        //get the location of the hit (index in the frame table), and set that frame to loaded
                        int hit_index = Util.getHit(frame_table, p);
                        frame_table.get(hit_index).is_loaded = true;

                        if (debug_level == 1 || debug_level == 11) {
                            System.out.print(p.id + " references word " + p.current_word + " at time " + cycle + ": ");
                            System.out.println("Hit in frame " + hit_index);
                        }

                    }
                    // CASE 2: PAGE MISS //
                    else {

                        p.number_of_faults++;

                        // CASE 2a: if the frame table is full //
                        if (Util.isFull(frame_table, number_of_pages)) {

                            //find the frame to replace using the specified replacement algorithm
                            FTE evicted_frame = null;

                            if(R.equals("lru")){
                                System.out.println("ERROR: invalid replacement algorithm selection");
                                System.exit(1);
                            }
                            else if(R.equals("fifo")){
                                evicted_frame = Replace.fifo(frame_table);
                            }
                            else if(R.equals("random")){
                                int rand_number = Integer.parseInt(rand_scanner.nextLine());
                                evicted_frame = Replace.random(frame_table, rand_number, number_of_pages);
                            }
                            else{
                                System.out.println("ERROR: invalid replacement algorithm selection");
                                System.exit(1);
                            }


                            int evicted_page = evicted_frame.page_number;
                            int evicted_frame_index = evicted_frame.frame_table_index;
                            processes.get(evicted_frame.process_number-1).number_of_evictions++;
                            processes.get(evicted_frame.process_number-1).residency_time += (cycle - evicted_frame.time_added);

                            // replace the frames
                            FTE new_frame = new FTE(p.id, p.getCurrentPage(), cycle, evicted_frame_index);
                            new_frame.is_active = true;
                            frame_table.set(evicted_frame_index, new_frame);

                            // print debug info
                            if (debug_level == 1 || debug_level == 11) {
                                System.out.print(p.id + " references word " + p.current_word + " at time " + cycle + ": ");
                                System.out.println("Fault, evicting page " + evicted_page + " of " + (evicted_frame.process_number) + " from frame " + evicted_frame_index);
                            }

                        }
                        // CASE 2b: frame table is not full //
                        else {

                            // find the highest numbered free frame
                            int highest_free_frame = frame_table.size() - 1;
                            for (int i = 0; i < frame_table.size(); i++) {
                                if (!frame_table.get(i).is_active) {
                                    highest_free_frame = i;
                                }
                            }

                            //replace free frame with new frame
                            FTE new_frame = new FTE(p.id, p.getCurrentPage(), cycle, highest_free_frame);
                            new_frame.is_active = true;
                            frame_table.set(highest_free_frame, new_frame);

                            // print debug info
                            if (debug_level == 1 || debug_level == 11) {
                                System.out.print(p.id + " references word " + p.current_word + " at time " + cycle + ": ");
                                System.out.println("Fault, using free frame " + highest_free_frame);
                            }
                        }

                    }


                    // for each process, generate a new reference //
                    int random_num = Integer.parseInt(rand_scanner.nextLine());
                    p.generateNextReference(random_num, S, N, rand_scanner);
                    if(debug_level == 11){
                        System.out.println(p.id + " uses random number: " + random_num);
                    }


                    // increment cycle counter //
                    cycle++;
                }
            }
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

            // calculate the number of pages
            number_of_pages = (int) Math.ceil((double)M / P);

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
                new_processes.add(new Process(1, 1.0, 0.0, 0.0, P, S));
                break;

            case 2:
                // create four processes, each with A=1, B=0, C=0
                for(int i=0; i<4; i++){
                    new_processes.add(new Process(i+1, 1.0, 0.0, 0.0, P, S));
                }
                break;

            case 3:
                // create four processes, each with A=0, B=0, C=0
                for(int i=0; i<4; i++){
                    new_processes.add(new Process(i+1, 0.0, 0.0, 0.0, P, S));
                }
                break;

            case 4:
                // create one process with A=0.75, B=0.25, C=0
                // create one process with A=0.75, B=0, C=0.25
                // create one process with A=0.75, B=0.125, C=0.125
                // create one process with A=0.5, B=0.125, C=0.125
                new_processes.add(new Process(1, 0.75, 0.25, 0.0, P, S));
                new_processes.add(new Process(2, 0.75, 0.0, 0.25, P, S));
                new_processes.add(new Process(3, 0.75, 0.125, 0.125, P, S));
                new_processes.add(new Process(4, 0.5, 0.125, 0.125, P, S));
                break;

        }

        return new_processes;
    }


    //initialize frame table with default FTEs
    public static ArrayList<FTE> initFrameTable(){

        ArrayList<FTE> new_frametable = new ArrayList<>();

        for(int i=0; i<number_of_pages; i++){
            // processid, page #, cycle, index
            new_frametable.add(new FTE(-1, -1, -1, -1));
        }

        return new_frametable;

    }



}
