package com.fyp;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by asim on 5/22/16.
 */
public class CoreFunction implements ActionListener {

    private Double[][] finalMat = new Double[1][196];
    private BufferedImage img;

    public CoreFunction(BufferedImage img){

        this.img = img;
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        int height = img.getHeight();
        int width = img.getWidth();

        FileRead read = new FileRead();
        if(command.equals("compress")){
            if(height != Constants.imgSize || width != Constants.imgSize){
                JOptionPane.showMessageDialog(null, "Image cannot be compressed", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else {
                int[][] imagePixels = convertToPixels(img);
                Double[][] imageVector = convertToVector(imagePixels);
                Double[][] matrix1 = read.convertToMatrix("Theta1.txt", "theta1");
                Double[][] midMatrix = read.multiplyByMatrix(imageVector, matrix1);
                Double[][] matrix2 = read.convertToMatrix("Theta2.txt", "theta2");

                finalMat = read.multiplyByMatrix(midMatrix, matrix2);

                new PrintMyArray<Double>().printArray(finalMat);

                try {
                    convertBackCom(finalMat);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        }
        if(command.equals("decompress")){
//                if(height != 14 || width != 14){
//                    JOptionPane.showMessageDialog(null, "Image cannot be decompressed", "Error", JOptionPane.ERROR_MESSAGE);
//                }
//                else {
//                    JOptionPane.showMessageDialog(null, "Image can be decompressed", "No Error", JOptionPane.INFORMATION_MESSAGE);
//
//                }

            Double[][] matrix3 = new Double[196][500];
            matrix3 = read.convertToMatrix("Theta3.txt", "theta3");
            Double[][] midMatrix1 = new Double[1][500];
            midMatrix1 = read.multiplyByMatrix(finalMat, matrix3);
            Double[][] matrix4 = new Double[500][784];
            matrix4 =  read.convertToMatrix("Theta4.txt", "theta4");
            Double[][] decomMat = new Double[1][784];
            decomMat= read.multiplyByMatrix(midMatrix1, matrix4);
            try {
                convertBackDecom(decomMat);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void convertBackCom(Double[][] finalMat) throws IOException {
        Double[][] compressed = new Double[14][14];
        int index =0;
        for(int i=0;i<14;i++){
            for(int j=0;j<14;j++){
                compressed[i][j] = finalMat[0][index];
                index+=1;
//                System.out.print(compressed[i][j]);
//                System.out.print(" ");
            }
//            System.out.println("\n");
        }
//        returnImage(compressed);
    }

    public void convertBackDecom(Double[][] decomMat) throws IOException{
        Double[][] decom = new Double[Constants.imgSize][Constants.imgSize];
        int index =0;
        for(int i=0;i<Constants.imgSize;i++){
            for(int j=0;j<Constants.imgSize;j++){
                decom[i][j] = 255* decomMat[0][index];
                index+=1;
//                System.out.print(decom[i][j]);
//                System.out.print(" ");
            }
//            System.out.println("\n");
        }
        returnImage(decom);

    }

    public void returnImage(Double[][] compressed) throws IOException {
        BufferedImage b = new BufferedImage(compressed.length, compressed[0].length, 3);
        Graphics c = b.getGraphics();


        PrintWriter writer = new PrintWriter("the-file-name.txt", "UTF-8");
        for(int i=0; i< compressed.length; i++){
            for (int j=0; j<compressed[0].length; j++){
                c.drawString(String.valueOf(compressed[i][j]), i, j);
                writer.print(compressed[i][j] + " \t");
            }
            writer.println();
        }

        ImageIO.write(b, "jpg", new File("CustomImage.jpg"));
        System.out.println("end");
    }

    public int[][] convertToPixels(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        int[][] pixel = new int[width][height];

        for(int i = 0; i < width; i++){

            for(int j = 0; j < height; j++){

                int rgb = img.getRGB(i, j);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = (rgb & 0xFF);
                pixel[i][j] = (r + g + b) / 3;
            }
        }
        return pixel;
    }

    public Double[][] convertToVector(int[][] imagePixels){
        Double[][] imageVector = new Double[1][img.getWidth() * img.getHeight()];
        for(int i=0, k = 0;i < img.getWidth();i++){
            for (int j = 0; j < img.getHeight(); j++) {
                imageVector[0][k++] = (double) imagePixels[i][j];
            }
        }
        System.out.println("created vector of size : 1 * " + img.getHeight() * img.getWidth());
        return imageVector;
    }

    public final boolean isGreyscaleImage(PixelGrabber pg) {
        return pg.getPixels() instanceof byte[];
    }
}
