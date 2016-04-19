package com.yf833;


import java.util.ArrayList;

public class Util {

    // take the mod of two integers
    public static int mod(int num1, int num2){
        return (num1 + num2) % num2;
    }



    // check if all processes are finished
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
            if(f.process_number == p.id  &&  f.page_number == p.getReferencedPage()  &&  f.is_referenced == true){
                return true;
            }
        }
        return false;
    }


    public static int getHitIndex(ArrayList<FTE> frametable, Process p){

        for(int i=0; i<frametable.size(); i++){
            if(frametable.get(i).process_number == p.id  &&  frametable.get(i).page_number == p.getReferencedPage()  &&  frametable.get(i).is_referenced == true){
                return i;
            }
        }
        return -99999;
    }


    // check if the frame table is full or not
    public static boolean isFull(ArrayList<FTE> frame_table, int total_frames){
        int total_references = 0;

        for(FTE f : frame_table){
            if(f.is_referenced){
                total_references++;
            }
        }

        if(total_references < total_frames){
            return false;
        }else{
            return true;
        }
    }


    // return the highest numbered free frame from the frame table
    public static int getHighestFreeFrame(ArrayList<FTE> frame_table){

        int highest_free = 0;

        for (int i = 0; i < frame_table.size(); i++) {
            if (!frame_table.get(i).is_referenced) {
                highest_free = i;
            }
        }

        return highest_free;
    }


    // print output info for the collection of finished processes
    public static void printOutput(ArrayList<Process> processes){

        System.out.println();

        int total_faults = 0;
        int total_residency = 0;
        int total_evictions = 0;

        for(Process p : processes){
            total_faults += p.num_faults;
            total_residency += p.residency_time_sum;
            total_evictions += p.num_evictions;

            System.out.print("Process " + p.id + " had ");
            System.out.print(p.num_faults + " faults");

            if(p.num_evictions == 0){
                System.out.println(".\n\tWith no evictions, the average residence is undefined.");
            }else{
                System.out.println(" and " + ((double) p.residency_time_sum / p.num_evictions) + " average residency.");
            }

        }

        System.out.println();
        System.out.print("The total number of faults is " + total_faults);

        if(total_evictions == 0){
            System.out.println(".\n\tWith no evictions, the overall average residence is undefined.");
        }else{
            System.out.print(" and the overall average residency is ");
            System.out.println(((double) total_residency / total_evictions) + ".");
        }



    }


}











