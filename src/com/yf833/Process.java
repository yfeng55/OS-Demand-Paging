package com.yf833;


public class Process {

    public int id;
    public double A;
    public double B;
    public double C;


    public Process(int id, double A, double B, double C){
        this.id = id;
        this.A = A;
        this.B = B;
        this.C = C;
    }



    public String toString(){

        String output = "";
        output += "process" + this.id + " ";

        return output;
    }


}
