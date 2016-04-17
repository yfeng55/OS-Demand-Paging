package com.yf833;


import java.util.ArrayList;

// replacement algorithms
public class Replace {


    // replace the frame that was added earliest
    public static FTE FIFO(ArrayList<FTE> frame_table){

        int evict_index = 0;
        int earliest_index = frame_table.get(0).time_added;

        // find the frame that was added first
        int i=0;
        for(FTE f : frame_table){
            if(f.time_added < earliest_index && f.is_active == true){
                earliest_index = f.time_added;
                evict_index = i;
            }
            i++;
        }

        return frame_table.get(evict_index);
    }



//    public static FTE LRU(ArrayList<FTE> frame_table){
//
//        // Divides all frames into four classes (pools A, B, C, & D)
//        ArrayList<FTE> poolA = new ArrayList<>();     // Not referenced, not modified.
//        ArrayList<FTE> poolB = new ArrayList<>();     // Not referenced, modified.
//        ArrayList<FTE> poolC = new ArrayList<>();     // Referenced, not modified.
//        ArrayList<FTE> poolD = new ArrayList<>();     // Referenced, modified.
//
//        for(FTE f : frame_table){
//            if(f.is_loaded){
//                if(f.is_modified){
//                    poolD.add(f);
//                }
//                else{
//                    poolC.add(f);
//                }
//            }
//            else{
//                if(f.is_modified){
//                    poolB.add(f);
//                }
//                else{
//                    poolA.add(f);
//                }
//            }
//        }
//
//        // find a frame to evict
//
//    }




}
