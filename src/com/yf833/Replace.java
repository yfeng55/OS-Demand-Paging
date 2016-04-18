package com.yf833;


import java.util.ArrayList;

// replacement algorithms
public class Replace {


    // replace the frame that was added earliest
    public static FTE fifo(ArrayList<FTE> frame_table){

        int evict_index = 0;
        int earliest_time = frame_table.get(0).time_added;

        // find the frame that was added first
        int i=0;
        for(FTE f : frame_table){
            if(f.time_added < earliest_time && f.is_active == true){
                earliest_time = f.time_added;
                evict_index = i;
            }
            i++;
        }

        return frame_table.get(evict_index);
    }



    // replace a frame at random
    public static FTE random(ArrayList<FTE> frame_table, int rand_number, int num_pages){

        int rand_index = Util.mod(rand_number, num_pages);
        return frame_table.get(rand_index);

    }


    // replace the frame that was "least recently used" (frame with the lowest last_used value)
    public static FTE lru(ArrayList<FTE> frame_table){

        int least_recent_index = 0;
        int least_used_time = frame_table.get(0).last_used;

        int i=0;
        for(FTE f : frame_table){
            if(f.last_used < least_used_time){
                least_used_time = f.last_used;
                least_recent_index = i;
            }
            i++;
        }

        return frame_table.get(least_recent_index);
    }




}
