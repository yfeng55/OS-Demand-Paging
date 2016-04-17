package com.yf833;


public class Process {

    public int id;
    public double A;
    public double B;
    public double C;

    public int S;

    public boolean finished;
    public int current_word;
    public int current_ref_number;
    public int page_size;

    public int residency_time;
    public int number_of_faults;
    public int number_of_evictions;


    public Process(int id, double A, double B, double C, int P, int S){
        this.id = id;
        this.A = A;
        this.B = B;
        this.C = C;
        this.page_size = P;
        this.S = S;

        //set default values
        this.current_word = Util.mod(111*this.id, S);
        this.current_ref_number = 1;
        this.number_of_faults = 0;
        this.number_of_evictions = 0;
        this.residency_time = 0;
        this.finished = false;

    }



    public String toString(){

        String output = "";
        output += "process" + this.id + " ";
        output += this.current_word;

        return output;
    }


    public int getCurrentPage(){
        return current_word / page_size;
    }

    public void generateNextReference(int random_num, int process_size, int num_refs_per_process){

        double quotient = random_num / (Integer.MAX_VALUE + 1d);

        if(quotient < A){
            this.current_word = Util.mod(this.current_word + 1, process_size);
        }
        else if(quotient < (A+B)){
            this.current_word = Util.mod((this.current_word - 5 + process_size), process_size);
        }
        else if(quotient < (A+B+C)){
            this.current_word = Util.mod(this.current_word + 4, process_size);
        }
        else if(quotient >= (A+B+C)){
            this.current_word = Util.mod(random_num, process_size);
        }
        else{
            System.out.println("ERROR: problem when trying to generate the next reference");
            System.exit(1);
        }

        int new_current_ref = this.current_ref_number + 1;

        // if the new reference exceeds the number of references per process, set the current process to finished
        if(new_current_ref > num_refs_per_process){
            this.finished = true;
        }

        this.current_ref_number = new_current_ref;

    }

}
