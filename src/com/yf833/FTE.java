package com.yf833;


public class FTE {

    public int page_number;
    public int process_number;
    public int time_added;
    public int frame_table_index;

    public boolean is_active;
    public boolean is_loaded;
    public boolean is_modified;

    public int last_used;


    public FTE(int process_id, int page_num, int cycle, int index){

        this.process_number = process_id;
        this.page_number = page_num;
        this.time_added = cycle;
        this.frame_table_index = index;


        // default values
        this.is_active = false;
        this.is_loaded = false;
        this.is_modified = false;

        this.last_used = 0;

    }


}
