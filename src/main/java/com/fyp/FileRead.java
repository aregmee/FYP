package com.fyp;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by deepak on 5/20/16.
 */
public class FileRead {
    public static void main(String[] args) {
//        String path1 = "/Users/deepak/Desktop/Theta1.txt";
//        String path2 = "/Users/deepak/Desktop/Theta2.txt";
//        convertToMatrix(path1, "theta1");


    }

    public static Double[][] convertToMatrix(String path, String t) {
        BufferedReader br = null;

        try {

            String sCurrentLine;

            br = new BufferedReader(new FileReader(path));
            ArrayList<String> theta1 = new ArrayList<String>();

            int index = 0;

            while ((sCurrentLine = br.readLine()) != null) {
                String[] newStr = sCurrentLine.split("\\s");
                for (int i = 0; i < newStr.length; i++) {
                    if (newStr[i] != null && !newStr[i].isEmpty()) {
                        theta1.add(index, newStr[i]);
//			             System.out.println(newStr[i]);
                        index += 1;
                    }
                }
            }
            Double[][] finalTheta1 = new Double[0][0];
            if (t == "theta1") {
               finalTheta1 = new Double[784][500];
                int index2 = 0;
                for (int i = 0; i < 784; i++) {
                    for (int j = 0; j < 500; j++) {
                        String val = theta1.get(index2).trim();
                        finalTheta1[i][j] = Double.parseDouble(val);
                        index2 += 1;
//                    System.out.println(finalTheta1[i][j]);
                    }
                }


            } else if (t == "theta2") {
               finalTheta1 = new Double[500][196];
                int index2 = 0;
                for (int i = 0; i < 500; i++) {
                    for (int j = 0; j < 196; j++) {
                        String val = theta1.get(index2).trim();
                        finalTheta1[i][j] = Double.parseDouble(val);
                        index2 += 1;
//                    System.out.println(finalTheta1[i][j]);
                    }
                }
            }
            else if(t =="theta3"){
                finalTheta1 = new Double[196][500];
                int index2 = 0;
                for (int i = 0; i < 196; i++) {
                    for (int j = 0; j < 500; j++) {
                        String val = theta1.get(index2).trim();
                        finalTheta1[i][j] = Double.parseDouble(val);
                        index2 += 1;
//                    System.out.println(finalTheta1[i][j]);
                    }
                }
            }

            else if(t == "theta4") {
                finalTheta1 = new Double[500][784];
                int index2 = 0;
                for (int i = 0; i < 500; i++) {
                    for (int j = 0; j < 784; j++) {
                        String val = theta1.get(index2).trim();
                        finalTheta1[i][j] = Double.parseDouble(val);
                        index2 += 1;
//                    System.out.println(finalTheta1[i][j]);
                    }
                }
            }
            return finalTheta1;


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    public static Double[][] multiplyByMatrix(Double[][] m1, Double[][] m2) {
        int m1ColLength = m1[0].length; // m1 columns length
        int m2RowLength = m2.length;    // m2 rows length
//        Double[][] test = new Double[0][0];
        if(m1ColLength != m2RowLength) {
            System.out.println("####");
            return null; // matrix multiplication is not possible
        }
        else {
            int mRRowLength = m1.length;    // m result rows length
            int mRColLength = m2[0].length; // m result columns length
            Double[][] mResult = new Double[mRRowLength][mRColLength];
            for(int i=0;i<mRRowLength;i++) {         // rows from m1
                for(int j=0;j<mRColLength;j++) {// columns from m2
                    mResult[i][j] = 0.0;
                    for(int k=0;k<m1ColLength;k++) { // columns from m1
                        mResult[i][j] = mResult[i][j] + m1[i][k]* m2[k][j];
                    }
//                    System.out.println(mResult[i][j]);
                }
            }
//            System.out.println("-------");
            return mResult;
        }

    }


}
