package com.fyp;

/**
 * Created by asim on 5/22/16.
 */
public class PrintMyArray<T> {

    public void printArray(T[][] a){

        for(int i = 0; i < a.length; i++){

            for(int j = 0; j < a[i].length; j++){

                System.out.printf(a[i][j] + " ");
            }
            System.out.println();
        }
    }
}
