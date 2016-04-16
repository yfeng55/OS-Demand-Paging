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



}
