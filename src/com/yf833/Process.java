package com.yf833;


import java.util.Scanner;

public class Process {

    public int id;

    public double A;
    public double B;
    public double C;
    public int S;                   // process size
    public int P;                   // page size

    public boolean finished;
    public int current_ref;         // the current reference for this process
    public int num_refs;            // count the number of references for this process

    public int residency_time;      // count the process' residency time
    public int num_faults;          // count the number of faults for this process
    public int num_evictions;       // count the number of evictions for this process



    public Process(int id, double A, double B, double C, int P, int S){

        this.id = id;
        this.A = A;
        this.B = B;
        this.C = C;
        this.P = P;
        this.S = S;


        //set default values
        this.current_ref = Util.mod(111*this.id, S);       // starting address

        this.num_refs = 0;
        this.num_faults = 0;
        this.num_evictions = 0;
        this.residency_time = 0;
        this.finished = false;

    }


    public String toString(){

        String output = "";
        output += "process" + this.id + " ";
        output += this.current_ref;

        return output;
    }


    // return the page that is currently being referenced
    public int getReferencedPage(){
        return current_ref / P;
    }


    // generate the next reference for this process
    public void generateNextReference(int random_num, int N, Scanner rand_scanner, long RAND_MAX){

        double y = random_num / (RAND_MAX + 1d);

        if(y < A){
            this.current_ref = Util.mod(this.current_ref + 1, this.S);
        }
        else if(y < (A+B)){
            this.current_ref = Util.mod((this.current_ref - 5 + this.S), this.S);
        }
        else if(y < (A+B+C)){
            this.current_ref = Util.mod(this.current_ref + 4, this.S);
        }
        else{
            int next_rand_num = Integer.parseInt(rand_scanner.nextLine());
            this.current_ref = Util.mod(next_rand_num, this.S);
        }

        this.num_refs++;

        // if the new reference exceeds the number of references per process, set the current process to finished
        if(this.num_refs+1 > N){
            this.finished = true;
        }


    }

}
