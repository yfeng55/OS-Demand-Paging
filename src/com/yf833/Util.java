package com.yf833;


import java.util.ArrayList;

public class Util {

    // take the mod of two integers
    public static int mod(int num1, int num2){
        return (num1 + num2) % num2;
    }



    // check if processes are finished
    public static boolean processesAreFinished(ArrayList<Process> processes){

        for(Process p : processes){
            if(p.finished == false){
                return false;
            }
        }
        return true;
    }



    // check if the current processID exists in the frametable; if not, return false
    public static boolean isHit(ArrayList<FTE> frametable, Process p){

        for(FTE f : frametable){
            if(f.process_number == p.id  &&  f.page_number == p.getCurrentPage()  &&  f.is_active == true){
                return true;
            }
        }
        return false;
    }


    // check if the current processID exists in the frametable; if not, return false
    public static int getHit(ArrayList<FTE> frametable, Process p){

        for(int i=0; i<frametable.size(); i++){
            if(frametable.get(i).process_number == p.id  &&  frametable.get(i).page_number == p.getCurrentPage()  &&  frametable.get(i).is_active == true){
                return i;
            }
        }
        return -1;
    }


    // check if the frame table is full or not
    public static boolean isFull(ArrayList<FTE> frame_table, int total_pages){
        int active_pages = 0;

        for(FTE f : frame_table){
            if(f.is_active){
                active_pages++;
            }
        }

        if(active_pages < total_pages){
            return false;
        }else{
            return true;
        }
    }


    // print output info for the collection of finished processes
    public static void printOutput(ArrayList<Process> processes){

        System.out.println();

        int total_faults = 0;
        int total_residency = 0;
        int total_evictions = 0;

        for(Process p : processes){
            total_faults += p.number_of_faults;
            total_residency += p.residency_time;
            total_evictions += p.number_of_evictions;

            System.out.print("Process " + p.id + " had ");
            System.out.print(p.number_of_faults + " faults");

            if(p.number_of_evictions == 0){
                System.out.println(".\n\tWith no evictions, the average residence is undefined.");
            }else{
                System.out.println(" and " + ((double) p.residency_time / p.number_of_evictions) + " average residency.");
            }

        }

        System.out.println();
        System.out.print("The total number of faults is " + total_faults + " and the overall average residency is ");
        System.out.println(((double) total_residency / total_evictions) + ".");

    }


}











