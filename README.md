

(1) read input from command line arguments ***

(2) write a mod helper function ***

(3) setup Process class 

(4) setup FTE (Frame Table Entry) class 
        - index: frame number 
        - content: number of pages in this frame 

(5) create a collection of processes according to the job mix number 



(6) setup scanner and random number file 




data structures:
------------------------------------------------------------
ArrayList<FTE> frame_table
    - a table of FTEs index by frame # 

ArrayList<Process> processes
    - contains the collection of processes created according to the job mix number
    



high-level procedure:
------------------------------------------------------------

address offsets:
    A: probability that a reference is current + 1
    B: probability that a reference is current - 5
    C: probability that a reference is current + 4
    (1-A-B-C): probability that a reference is to a random address location 



--> driver program generates memory references 
        - read all input 
        - simulate N memory references per program 
        - produce all output 


--> pager decides if each reference causes a page fault 
        - (when process tries to access a page that's in the virtual address space, but not loaded into memory)


